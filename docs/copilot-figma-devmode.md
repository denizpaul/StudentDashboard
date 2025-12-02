# Copilot Instructions — Figma Dev Mode & MCP (Dummy)

> Use this as a template when wiring Copilot to Figma Dev Mode + Model Context Protocol (MCP). Replace with real instructions later.

## Access & Setup
- Authentication: Copilot gets a short-lived Figma access token generated via `figma-cli auth token`.
- Share links: always include the Dev Mode deep link (e.g., `https://www.figma.com/file/...?...node-id=123-456`).
- File naming: follow `Feature_Area / Screen_Name` so MCP lookups stay consistent.

## Working With Dev Mode
1. When requesting specs, specify frame names plus variant (mobile/tablet/desktop).
2. Ask Copilot to pull redlines (spacing, typography, colors) via MCP before coding.
3. If the design changed mid-PR, tag `@design` and re-sync token values.

## MCP Commands (Dummy Examples)
- `figma.inspect node-id=123:456`: fetch measurements and style metadata.
- `figma.export node-id=123:789 format=svg`: return optimized vector for Android.
- `figma.comments list node-id=123:456`: surface outstanding design feedback.

## Best Practices
- Keep a checksum (`design-spec-version`) in PR descriptions to confirm alignment.
- Capture screenshots or GIFs showing real device parity when deviations occur.
- If MCP fails, fall back to manual specs but flag the gap in the TODO list.

---
*Dummy placeholder—substitute with your actual Figma Dev Mode + MCP workflow.*

