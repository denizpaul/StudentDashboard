# Repository Copilot Instructions (Template)

## Project Snapshot
- Feature scope: single-screen dashboard app built with Jetpack Compose and MVVM
- Data: local-only sources; no networking until we document it here
- Goal: keep architecture clean so additional features can plug in later

## Architecture & Coding Guardrails
- Compose UI must stay stateless; ViewModels expose immutable `StateFlow`
- Use a thin domain layer (use case + repository interface) even if implementations are local fakes
- Coroutines only inside data/presentation layers; UI uses `collectAsStateWithLifecycle`
- No hardcoded strings/colors/dimensions—pull from resources or tokens when they exist
- Prefer constructor injection; if Hilt/Koin gets added, document modules here before using them

## Testing Expectations
- Every ViewModel and repository should have at least one happy-path unit test stub
- Compose UI should have preview or simple screenshot tests once UI stabilizes
- Use `kotlinx-coroutines-test` for dispatcher control; keep test data builders in `core/testdata`

## Feature-Specific References
- Design tokens and theming rules live in `.github/instructions/DESIGN_SYSTEM.instructions.md`
- Figma MCP workflow requirements live in `.github/instructions/FIGMA_DEV_MODE.instructions.md`
- Update those files before diverging from the baseline visual spec

## TODO Checklist (replace as the project matures)
- [ ] Document actual data models and local data source shape
- [ ] Add navigation guidance if we introduce additional screens
- [ ] Capture performance/accessibility rules once defined
