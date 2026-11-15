#!/usr/bin/env python3
"""Weather Mate visual evaluator v0.5.

- Accepts screenshots with different pixel dimensions.
- Applies relative crop to reference and candidate.
- Fits both images into a common canvas without stretching.
- Computes content-aware regional SSIM (penalizing missing components/foreground elements).
- Evaluates visual gate against threshold and exits with code 1 when strict mode is active.

Region boxes use normalized coordinates: [x1, y1, x2, y2], values from 0.0 to 1.0.
"""

import argparse
import json
import sys
from pathlib import Path

import numpy as np
from PIL import Image, ImageOps
from skimage.metrics import structural_similarity


def load_rgb(path):
    return Image.open(path).convert("RGB")


def validate_fraction(value, name):
    value = float(value)
    if not 0.0 <= value < 1.0:
        raise ValueError(f"{name} must be between 0.0 (inclusive) and 1.0 (exclusive)")
    return value


def apply_relative_crop(image, crop_cfg):
    width, height = image.size
    top = validate_fraction(crop_cfg.get("top", 0.0), "crop.top")
    bottom = validate_fraction(crop_cfg.get("bottom", 0.0), "crop.bottom")
    left = validate_fraction(crop_cfg.get("left", 0.0), "crop.left")
    right = validate_fraction(crop_cfg.get("right", 0.0), "crop.right")

    x1 = round(width * left)
    y1 = round(height * top)
    x2 = round(width * (1.0 - right))
    y2 = round(height * (1.0 - bottom))

    if x2 <= x1 or y2 <= y1:
        raise ValueError("Crop removes the entire image; reduce crop percentages.")

    return image.crop((x1, y1, x2, y2))


def fit_without_stretching(image, target_size, background=(255, 255, 255)):
    """Resize to fit target_size while preserving aspect ratio; pad remaining area."""
    target_w, target_h = target_size
    fitted = ImageOps.contain(
        image,
        (target_w, target_h),
        method=Image.Resampling.LANCZOS,
    )
    canvas = Image.new("RGB", target_size, background)
    x = (target_w - fitted.width) // 2
    y = (target_h - fitted.height) // 2
    canvas.paste(fitted, (x, y))
    return canvas


def ssim_score(image_a, image_b):
    a = np.asarray(image_a)
    b = np.asarray(image_b)
    return float(
        structural_similarity(
            a, b, channel_axis=2, data_range=255
        )
    )


def normalized_box_crop(image, box):
    if len(box) != 4:
        raise ValueError(f"Region box must contain 4 values, got: {box}")
    x1, y1, x2, y2 = [float(v) for v in box]
    if not (0 <= x1 < x2 <= 1 and 0 <= y1 < y2 <= 1):
        raise ValueError(
            f"Invalid normalized region box {box}; expected "
            "[x1, y1, x2, y2] between 0 and 1."
        )

    width, height = image.size
    left = max(0, min(width - 1, int(round(x1 * width))))
    top = max(0, min(height - 1, int(round(y1 * height))))
    right = max(left + 1, min(width, int(round(x2 * width))))
    bottom = max(top + 1, min(height, int(round(y2 * height))))
    return image.crop((left, top, right, bottom))


def foreground_coverage(image, threshold=18):
    """Estimate non-background pixel coverage using the median border colour."""
    arr = np.asarray(image).astype(np.int16)
    h, w = arr.shape[:2]
    border = np.concatenate(
        [arr[0, :, :], arr[-1, :, :], arr[:, 0, :], arr[:, -1, :]],
        axis=0,
    )
    background = np.median(border, axis=0)
    distance = np.sqrt(np.sum((arr - background) ** 2, axis=2))
    return float(np.mean(distance > float(threshold)))


def main():
    parser = argparse.ArgumentParser(description="Weather Mate visual evaluator v0.5")
    parser.add_argument("--reference", required=True)
    parser.add_argument("--candidate", required=True)
    parser.add_argument("--config", default="config.json")
    parser.add_argument("--json-out", default="report.json")
    parser.add_argument("--md-out", default="report.md")
    parser.add_argument(
        "--strict",
        action="store_true",
        help="Exit with status code 1 if visual gate fails",
    )
    args = parser.parse_args()

    cfg = json.loads(Path(args.config).read_text(encoding="utf-8"))

    normalization = cfg.get("normalization", {})
    crop_cfg = cfg.get("crop", {})
    comparison = cfg.get("comparison", {})

    if crop_cfg.get("mode", "relative") != "relative":
        raise ValueError("v0.5 supports crop.mode='relative' only.")

    reference = apply_relative_crop(load_rgb(args.reference), crop_cfg)
    candidate = apply_relative_crop(load_rgb(args.candidate), crop_cfg)

    ref_ratio = reference.width / reference.height
    cand_ratio = candidate.width / candidate.height
    ratio_difference = abs(cand_ratio - ref_ratio) / ref_ratio

    tolerance = float(normalization.get("aspect_ratio_tolerance", 0.03))
    if tolerance < 0:
        raise ValueError("normalization.aspect_ratio_tolerance cannot be negative.")

    target_w = int(normalization.get("width", 472))
    target_h = int(normalization.get("height", 1024))
    if target_w < 1 or target_h < 1:
        raise ValueError("Normalization width and height must be positive.")

    preserve = bool(normalization.get("preserve_aspect_ratio", True))
    if not preserve:
        raise ValueError("v0.5 requires preserve_aspect_ratio=true.")

    target_size = (target_w, target_h)
    ref_norm = fit_without_stretching(reference, target_size)
    cand_norm = fit_without_stretching(candidate, target_size)

    raw_global_score = ssim_score(ref_norm, cand_norm)

    regions_cfg = cfg.get("regions", [])
    regions = []
    weighted_effective_sum = 0.0
    total_weight = 0.0
    region_threshold = float(
        comparison.get("region_threshold", cfg.get("region_threshold", 0.70))
    )
    coverage_gate = bool(comparison.get("missing_component_gate", True))
    coverage_min_ratio = float(comparison.get("minimum_coverage_ratio", 0.45))
    coverage_threshold = float(comparison.get("coverage_pixel_threshold", 18))

    for region in regions_cfg:
        name = region["name"]
        box = region["box"]
        weight = float(region.get("weight", 1.0))

        ref_region = normalized_box_crop(ref_norm, box)
        cand_region = normalized_box_crop(cand_norm, box)

        raw_ssim = ssim_score(ref_region, cand_region)
        ref_coverage = foreground_coverage(ref_region, coverage_threshold)
        cand_coverage = foreground_coverage(cand_region, coverage_threshold)

        if ref_coverage <= 0.0001:
            coverage_ratio = 1.0 if cand_coverage <= 0.0001 else 0.0
        else:
            coverage_ratio = cand_coverage / ref_coverage

        coverage_ratio_capped = min(1.0, coverage_ratio)
        component_suspected_missing = (
            coverage_gate
            and ref_coverage >= 0.01
            and coverage_ratio < coverage_min_ratio
        )

        # Content-aware penalty: scale raw SSIM by content coverage ratio if reference contains content
        if ref_coverage >= 0.01:
            effective_ssim = raw_ssim * coverage_ratio_capped
        else:
            effective_ssim = raw_ssim

        region_passed = (
            effective_ssim >= region_threshold
            and not component_suspected_missing
        )

        regions.append({
            "name": name,
            "raw_ssim": round(raw_ssim, 4),
            "effective_ssim": round(effective_ssim, 4),
            "weight": weight,
            "reference_foreground_coverage": round(ref_coverage, 4),
            "candidate_foreground_coverage": round(cand_coverage, 4),
            "coverage_ratio": round(coverage_ratio_capped, 4),
            "component_suspected_missing": component_suspected_missing,
            "region_passed": region_passed,
        })

        weighted_effective_sum += effective_ssim * weight
        total_weight += weight

    weighted_effective_score = (
        weighted_effective_sum / total_weight if total_weight > 0 else raw_global_score
    )

    global_threshold = float(
        comparison.get("global_threshold", cfg.get("global_threshold", 0.80))
    )
    ratio_mismatch = ratio_difference > tolerance
    failed_regions = [r["name"] for r in regions if not r["region_passed"]]

    checks = {
        "global_ssim_passed": weighted_effective_score >= global_threshold,
        "aspect_ratio_passed": not ratio_mismatch,
        "regions_passed": len(failed_regions) == 0,
    }
    visual_gate_passed = all(checks.values())

    result = {
        "version": "0.5",
        "raw_global_ssim": round(raw_global_score, 4),
        "content_aware_weighted_ssim": round(weighted_effective_score, 4),
        "threshold": global_threshold,
        "visual_gate_passed": visual_gate_passed,
        "status": "PASS" if visual_gate_passed else "FAIL",
        "checks": checks,
        "failed_regions": failed_regions,
        "regions": regions,
        "note": "Visual gate enforces content coverage and structural similarity against reference screenshot.",
    }

    Path(args.json_out).write_text(
        json.dumps(result, indent=2, ensure_ascii=False),
        encoding="utf-8",
    )

    lines = [
        "# Relatório visual — Weather Mate",
        f"- Versão: **{result['version']}**",
        f"- SSIM global bruto: **{raw_global_score:.3f}**",
        f"- SSIM ponderado ajustado ao conteúdo: **{weighted_effective_score:.3f}**",
        f"- Threshold global: **{global_threshold:.3f}**",
        f"- Estado: **{result['status']}**",
        "",
        "| Região | SSIM Bruto | SSIM Efetivo | Cobertura Ref. | Cobertura Cand. | Componente em Falta | Resultado |",
        "|---|---:|---:|---:|---:|---|---|",
    ]

    for region in regions:
        lines.append(
            f"| {region['name']} | {region['raw_ssim']:.3f} | {region['effective_ssim']:.3f} | "
            f"{region['reference_foreground_coverage']:.3f} | "
            f"{region['candidate_foreground_coverage']:.3f} | "
            f"{'SIM' if region['component_suspected_missing'] else 'NÃO'} | "
            f"{'PASS' if region['region_passed'] else 'FAIL'} |"
        )

    lines.extend(["", f"> {result['note']}"])
    Path(args.md_out).write_text("\n".join(lines), encoding="utf-8")

    print(json.dumps(result, indent=2, ensure_ascii=False))

    strict_mode = args.strict or bool(comparison.get("strict_exit", False))
    if strict_mode and not visual_gate_passed:
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
