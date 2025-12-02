# Figma MCP Integration Rules - Jetpack Compose Android

These rules define how to translate Figma inputs into Jetpack Compose code for this project and must be followed for every Figma-driven change.

## Required Flow (Do Not Skip)

1. **Get Design Context** - Run get_design_context to fetch the structured representation for the exact node(s).
2. **Handle Large Responses** - If the response is too large or truncated, run get_metadata to get the high-level node map and then re-fetch only the required node(s) with get_design_context.
3. **Get Visual Reference** - Run get_screenshot for a visual reference of the node variant being implemented.
4. **Download Assets** - After obtaining get_design_context and get_screenshot, download any required assets (icons, images, etc.).
5. **Translate to Compose** - Convert the Figma output into Jetpack Compose code using this project's conventions and design system.
6. **Validate Against Figma** - Verify 1:1 visual and behavioral parity with Figma before marking complete.