# Copilot Instructions — Design System (Dummy)

> Placeholder overview of your design system guardrails for Copilot. Swap in your actual tokens and component names later.

## Foundation
- Typography: use `MonashSans` families; avoid mixing weights without design sign-off.
- Color palette: primary = `@color/purple_500`, secondary = `@color/teal_200`, neutrals from `colors.xml` only.
- Spacing scale: multiples of 4dp; grid layouts align to 8dp baseline.

## Components
1. **Buttons**: prefer Material3 `Button` variants; custom shapes must reference `shapeMedium`.
2. **Cards**: elevation tokens `elevations.medium` by default; never exceed 8dp without designer approval.
3. **Lists**: if items are tappable, ensure `minimum touch target >= 48dp`.
4. **Icons**: use `VectorAsset`s from `drawable/`; avoid raster imports unless performance-tested.

## Behaviors
- Motions: standard durations 100ms (micro), 250ms (default), 400ms (emphasis).
- Dark mode: verify contrast pairs defined in `values-night/colors.xml`.
- Localization: reserve 30% extra width for translated strings.

## Implementation Notes
- Centralize component variations via `ui/components/` helpers so Copilot can reuse them.
- Document any new token in a `tokens.yaml` file before coding.
- Drop TODO tags like `TODO(ds):` when the design team must review the change.

---
*Dummy content—replace with your actual design system playbook.*

