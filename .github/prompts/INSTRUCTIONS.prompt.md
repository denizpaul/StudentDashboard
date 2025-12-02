# INSTRUCTIONS.prompt.md (DUMMY)

> NOTE: This is a placeholder/dummy file. Replace the sections below with the real Copilot prompt instructions when ready.

---

## Purpose

This file contains short, copyable instruction templates for Copilot support and related design/tooling workflows. There are three sections below:

1. General Copilot instructions
2. Design System instructions (for the design system document)
3. Figma Dev Mode / MCP usage notes

Replace the dummy content with your final guidance.

---

## 1) General Copilot Instructions (DUMMY)

- Repo: <REPLACE WITH PROJECT NAME>
- Maintainers: <LIST MAINTAINERS / TEAM>
- Goal: Provide a short description of what Copilot should know about this repo, its goals, and common tasks.

Suggested content to include:
- How to run the project locally (build command, run command, platform-specific notes).
- Where important files live (e.g., `app/`, `src/`, `docs/`).
- Coding style / linting rules to respect.
- Authentication or secrets: "Do not attempt to access or expose secrets; refer to maintainers for credentials."
- Common tasks Copilot should help with (e.g., add feature, fix bug, write tests, refactor).
- Expected tone and safety: concise, actionable, avoid speculation; link to code owners when unsure.

Example starter prompt for Copilot users:
"You are assisting with <PROJECT>. Provide step-by-step instructions to implement X, include file paths and a short code patch when appropriate. Ask clarifying questions only if necessary."

---

## 2) Design System Instructions (DUMMY)

- Document name: `copilot-design-system.md` (create/replace in `docs/` or `.github/` as appropriate).
- Purpose: Describe components, tokens, spacing, colors, and usage patterns that Copilot should reference when implementing UI work.

Suggested content to include:
- Link or path to the design system source (Figma file or local tokens file).
- Naming conventions for components and tokens.
- Examples: small code snippets showing how to use components (e.g., Button, Card) and token names for spacing/colors.
- Accessibility notes to always check (contrast, focus states, keyboard navigation).
- Testing requirements for UI changes (visual regression, unit tests for component logic).

Placeholder section (to replace):
- Component catalog: Button, TextField, Card, Modal — describe props and behavior.
- Tokens: color.primary, spacing.sm, typography.h1 — map to usage examples.

---

## 3) Figma Dev Mode / MCP Usage (DUMMY)

- Document name: `copilot-figma-devmode.md` (or add this section into an existing figma/ux docs folder).
- Purpose: Quick reference for using Figma Dev Mode and the MCP (Model/Component Package) export workflow.

Suggested content to include:
- How to open Figma Dev Mode and enable the MCP export plugin.
- Steps to export components or tokens: select frame → Dev Mode → Export → choose format (JSON, tokens), or use MCP plugin to generate tokens.
- Naming rules for exported assets and tokens (consistent prefixes, e.g., `ds/` or `brand/`).
- Where to commit exported artifacts in the repo (e.g., `design/tokens/` or `app/src/main/res/values/`).
- Example MCP usage flow:
  1. Open Figma file and switch to Dev Mode.
  2. Run MCP export for the component set.
  3. Download JSON/Tokens and place into `design/` or a dedicated tokens directory.
  4. Create a PR with the exported tokens and update the design system doc.

Placeholder tips:
- Include screenshots or short gifs in the real doc to clarify each step.
- Note plugin/version used and the author of the export step.

---

## TODO / Next steps

- Replace each DUMMY section with the real content.
- Optionally split sections into separate files: `copilot-general.md`, `copilot-design-system.md`, `copilot-figma-devmode.md` for clearer maintenance.
- Add maintainers and example prompts tailored for your workflow.

---

> This file was created as a dummy template. Copy and paste the sections into the final files and update paths and commands as needed.

