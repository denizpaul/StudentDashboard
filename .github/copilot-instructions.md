# GitHub Copilot Instructions — Repository-wide

> Purpose: Project-level instructions for GitHub Copilot to follow when generating code or suggestions.
> Replace placeholders (bracketed values) with project-specific details.

---

## Project at-a-glance

- Project: [Your Project Name]
- Short description: Briefly describe the app and its primary goals.
- Maintainers / Code owners: [team@example.com]
- Tech stack: Kotlin, Gradle (Kotlin DSL), Android (XML layouts or Jetpack Compose), Material Design 3

---

## How Copilot should use these instructions

- Scope: Applies to all files in this repository unless a file-level instruction overrides it.
- Tone: Concise, actionable, and conservative — prefer explicit questions when unsure.
- Safety: Never attempt to access secrets. If a credential or token is required, instruct the user to obtain it from maintainers.
- Verification: Generated code should use design system tokens, follow architecture patterns, include accessibility attributes, and avoid hardcoded resources.

---

## 1 — Generic (Repository-wide) Rules

1. Use project architecture patterns:
   - Prefer MVVM / MVI / Clean (state which one in project header).
   - ViewModels handle logic; Repositories handle data access; UI observes state from ViewModel.
2. Resources:
   - NEVER hardcode strings; use `strings.xml`.
   - NEVER hardcode dimensions; use `@dimen/...` tokens.
   - NEVER hardcode colors; use theme attributes (e.g., `?attr/colorPrimary`) or design tokens.
3. Kotlin style:
   - Prefer `val` over `var` when possible.
   - Use data classes for plain models.
   - Avoid `!!` operator; handle nullability explicitly.
4. Tests:
   - Add unit tests for ViewModels and Repositories.
   - Follow naming convention: `should[Expected]When[Condition]`.
   - Aim for minimum 70% coverage on feature modules.
5. Accessibility:
   - Add `android:contentDescription` for images/icons.
   - Support text scaling with `sp` and responsive layouts.
   - Touch target minimum 48dp.
6. Commit messages:
   - Format: `type(scope): subject` (e.g., `feat(dashboard): add schedule card`).

---

## 2 — Design System (Feature-specific)

Purpose: Ensure UI code uses the canonical design tokens and components.

Rules and expectations:
- ALWAYS use design system tokens for spacing, color, and typography.
  - Spacing example: `@dimen/spacing_md`, `@dimen/spacing_lg`.
  - Typography: use `TextAppearance` or theme text styles, not hardcoded text sizes.
  - Colors: use `?attr/colorPrimary`, `?attr/colorOnSurface`, etc.
- NEVER use hardcoded hex colors or dp values in layouts or styles.
- Use the project's component library (if available) instead of creating new UI primitives.
- Accessibility is part of the design system: ensure contrast, focus states, and semantics match the spec.

Examples (correct vs incorrect):

Correct:
- android:padding="@dimen/spacing_md"
- android:background="?attr/colorSurface"

Incorrect:
- android:padding="16dp"
- android:background="#FFFFFF"

Design System Checklist for generated UI code:
- [ ] Uses design tokens for spacing/colors/typography
- [ ] Uses provided components when applicable
- [ ] Includes accessibility attributes
- [ ] Avoids hardcoded values

---

## 3 — Figma Dev Mode / MCP Usage (Feature-specific)

Purpose: When generating UI from design, Copilot must prefer authoritative data from Figma Dev Mode / MCP exports.

Workflow Copilot should follow:
1. Ask the developer to export the required component or tokens from Figma Dev Mode or MCP if not already present in the repo.
2. Prefer values from exported token files (JSON or tokens) under `design/` or `design/tokens/`.
3. Map Figma tokens to Android resources: colors -> `colors.xml` / theme attrs, spacing -> `dimens.xml`, typography -> `styles.xml` / `textAppearance`.
4. Provide a small code snippet that demonstrates how to apply the token (layout snippet or style snippet).

MCP and export guidance for developers (to include in PRs):
- Include the source Figma file and export timestamp in the PR description.
- Commit exported tokens to `design/tokens/` and reference their path in the PR.

Example prompt Copilot can ask when unsure:
- "Please attach the Figma Dev Mode MCP export for the component or the token JSON so I can map tokens to Android resources." 

---

## 4 — Code Generation Guidelines

When asked to generate code, Copilot should:
1. Confirm the target (layout, ViewModel, repository, unit test).
2. Use design tokens for values.
3. Follow the repository's naming conventions.
4. Include resource placeholders in `strings.xml` and reference them in layouts.
5. Add basic unit tests for new ViewModel/repository code.
6. Provide short, focused diffs and explain what files to modify.

Small example for a layout snippet:

- Layout should reference dimen and color tokens and include content description.

---

## 5 — Testing & Validation

- Generated code should include at least one unit test for any business logic introduced.
- Run unit tests locally after generation and fix obvious compile/test failures before accepting suggested changes.

---

## 6 — Prohibited Practices

- NEVER hardcode colors, dimensions, or strings in generated code.
- NEVER use deprecated APIs without a clear migration plan.
- NEVER leak secrets or credentials.
- NEVER add large generated assets into source; prefer dedicated asset pipelines.

---

## 7 — How to Use / Quick Start

1. Add this file to the repository root at `.github/copilot-instructions.md` (done).
2. When asking Copilot to generate code, include context: target file path, architecture pattern, and whether you want an accompanying unit test.
3. If the change touches UI, include or attach the Figma MCP export or token file.

Quick example prompt to use in editor:

"Generate an XML layout for a schedule card using the Design System tokens in `@dimen/spacing_md`, theme colors, and include content descriptions; also create a ViewModel with a basic unit test using JUnit." 

---

## 8 — References and Links

- Design tokens directory: `design/tokens/` (recommendation)
- Design spec (Figma): [link-to-figma-file]
- Architecture guide: `.github/IMPLEMENTATION_GUIDE.md`

---

## 9 — Maintenance

- Keep instructions up to date when architecture or design system changes.
- Add examples for new patterns introduced by the team.
- Review suggestions by Copilot and refine instructions when you see repeated incorrect suggestions.

---

If you want, I can:
- Generate a smaller minimal version of this file (one-page quick rules),
- Split the Design System and Figma Dev Mode sections into separate files under `.github/` or `docs/`.

