# GitHub Copilot Instructions — Repository-wide

> Purpose: Project-level instructions for GitHub Copilot to follow when generating code or suggestions.
> Replace placeholders (bracketed values) with project-specific details.
The project is a **single-screen Android app** built with:
- Kotlin
- Jetpack Compose
## Project at-a-glance
- No remote APIs (local-only data source)
- Project: [Your Project Name]
- Short description: Briefly describe the app and its primary goals.
- Maintainers / Code owners: [team@example.com]
- Tech stack: Kotlin, Gradle (Kotlin DSL), Android (XML layouts or Jetpack Compose), Material Design 3
---

## 1. High-Level Architecture
## How Copilot should use these instructions
### 1.1 Layers
- Scope: Applies to all files in this repository unless a file-level instruction overrides it.
- Tone: Concise, actionable, and conservative — prefer explicit questions when unsure.
- Safety: Never attempt to access secrets. If a credential or token is required, instruct the user to obtain it from maintainers.
- Verification: Generated code should use design system tokens, follow architecture patterns, include accessibility attributes, and avoid hardcoded resources.
    - Pure composables (stateless where possible).
    - Displays data from the `ViewModel` via `UiState`.
    - Sends user events back to the `ViewModel`.
## 1 — Generic (Repository-wide) Rules
2. **Presentation Layer (MVVM)**
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
## 2. Modules & Project Structure

Even though this app is small, organize code in a way that scales.
## 2 — Design System (Feature-specific)
For a **single-module MVP** (start simple):
Purpose: Ensure UI code uses the canonical design tokens and components.
```text
Rules and expectations:
- ALWAYS use design system tokens for spacing, color, and typography.
  - Spacing example: `@dimen/spacing_md`, `@dimen/spacing_lg`.
  - Typography: use `TextAppearance` or theme text styles, not hardcoded text sizes.
  - Colors: use `?attr/colorPrimary`, `?attr/colorOnSurface`, etc.
- NEVER use hardcoded hex colors or dp values in layouts or styles.
- Use the project's component library (if available) instead of creating new UI primitives.
- Accessibility is part of the design system: ensure contrast, focus states, and semantics match the spec.
      mapper/
Examples (correct vs incorrect):
      usecase/
Correct:
- android:padding="@dimen/spacing_md"
- android:background="?attr/colorSurface"
      ui/
Incorrect:
- android:padding="16dp"
- android:background="#FFFFFF"
Later, this can evolve into separate modules:
Design System Checklist for generated UI code:
- [ ] Uses design tokens for spacing/colors/typography
- [ ] Uses provided components when applicable
- [ ] Includes accessibility attributes
- [ ] Avoids hardcoded values
- `:core:domain` – use cases & repository interfaces
- `:feature:dashboard` – Dashboard feature (ViewModel + UI)

## 3 — Figma Dev Mode / MCP Usage (Feature-specific)

Purpose: When generating UI from design, Copilot must prefer authoritative data from Figma Dev Mode / MCP exports.
- Keep **cross-cutting concerns** under `core/`.
Workflow Copilot should follow:
1. Ask the developer to export the required component or tokens from Figma Dev Mode or MCP if not already present in the repo.
2. Prefer values from exported token files (JSON or tokens) under `design/` or `design/tokens/`.
3. Map Figma tokens to Android resources: colors -> `colors.xml` / theme attrs, spacing -> `dimens.xml`, typography -> `styles.xml` / `textAppearance`.
4. Provide a small code snippet that demonstrates how to apply the token (layout snippet or style snippet).
  presentation/
MCP and export guidance for developers (to include in PRs):
- Include the source Figma file and export timestamp in the PR description.
- Commit exported tokens to `design/tokens/` and reference their path in the PR.
  ui/
Example prompt Copilot can ask when unsure:
- "Please attach the Figma Dev Mode MCP export for the component or the token JSON so I can map tokens to Android resources." 
    components/
      DashboardCard.kt
      MetricRow.kt
## 4 — Code Generation Guidelines

When asked to generate code, Copilot should:
1. Confirm the target (layout, ViewModel, repository, unit test).
2. Use design tokens for values.
3. Follow the repository's naming conventions.
4. Include resource placeholders in `strings.xml` and reference them in layouts.
5. Add basic unit tests for new ViewModel/repository code.
6. Provide short, focused diffs and explain what files to modify.
    - One `MainActivity`.
Small example for a layout snippet:

- Layout should reference dimen and color tokens and include content description.

- Set content with `setContent { AppTheme { DashboardRoute() } }`.
- No bottom navigation, no NavHost. Navigation can be added later if needed.
## 5 — Testing & Validation
**DashboardRoute composable:**
- Generated code should include at least one unit test for any business logic introduced.
- Run unit tests locally after generation and fix obvious compile/test failures before accepting suggested changes.
    - Creating / getting the `DashboardViewModel` (e.g., `hiltViewModel()` or `viewModel()`).
    - Collecting `UiState` (`collectAsStateWithLifecycle` when using lifecycle-runtime-compose).
    - Passing `state` and event callbacks down to `DashboardScreen`.
## 6 — Prohibited Practices
### 3.2 Stateless vs Stateful Composables
- NEVER hardcode colors, dimensions, or strings in generated code.
- NEVER use deprecated APIs without a clear migration plan.
- NEVER leak secrets or credentials.
- NEVER add large generated assets into source; prefer dedicated asset pipelines.
    - Parameters: `uiState: DashboardUiState`, `onEvent: (DashboardUiEvent) -> Unit`.
- Use smaller stateless composables in `components/` folder:
    - Example: `DashboardToolbar`, `DashboardMetricCard`, `DashboardSection`.
## 7 — How to Use / Quick Start
**Do NOT:**
1. Add this file to the repository root at `.github/copilot-instructions.md` (done).
2. When asking Copilot to generate code, include context: target file path, architecture pattern, and whether you want an accompanying unit test.
3. If the change touches UI, include or attach the Figma MCP export or token file.

Quick example prompt to use in editor:

"Generate an XML layout for a schedule card using the Design System tokens in `@dimen/spacing_md`, theme colors, and include content descriptions; also create a ViewModel with a basic unit test using JUnit." 
- The toolbar is part of `DashboardScreen`, e.g.:

    - A `Scaffold` with:
## 8 — References and Links
        - `content = { DashboardContent(...) }`
- Design tokens directory: `design/tokens/` (recommendation)
- Design spec (Figma): [link-to-figma-file]
- Architecture guide: `.github/IMPLEMENTATION_GUIDE.md`
- `DashboardUiState` should be a **data class** containing all fields needed to render the dashboard.
- Exposed from `ViewModel` as `StateFlow<DashboardUiState>`.
- In composables, collect using `collectAsStateWithLifecycle()` (preferred) or `collectAsState()`.
## 9 — Maintenance
**Example pattern:**
- Keep instructions up to date when architecture or design system changes.
- Add examples for new patterns introduced by the team.
- Review suggestions by Copilot and refine instructions when you see repeated incorrect suggestions.
DashboardScreen(
    uiState = uiState,
    onEvent = viewModel::onEvent
If you want, I can:
- Generate a smaller minimal version of this file (one-page quick rules),
- Split the Design System and Figma Dev Mode sections into separate files under `.github/` or `docs/`.
---

## 4. Presentation Layer: ViewModel & State

### 4.1 ViewModel Rules

Copilot should:

- Place `DashboardViewModel` in `dashboard/presentation/`.
- Use `ViewModel` (AndroidX) and `viewModelScope`.
- Expose **immutable** state:

  ```kotlin
  private val _uiState = MutableStateFlow(DashboardUiState())
  val uiState: StateFlow<DashboardUiState> = _uiState
  ```

- Handle:
    - Data loading from local repository/use case.
    - UI events (e.g., refresh, filter changes).

### 4.2 UiState & Events

**UiState:**

- Use `data class DashboardUiState(...)` to represent the entire screen.
- Should be immutable.
- Add fields such as:
    - Dashboard metrics
    - Loading flags
    - Error message (if necessary)

**Events (optional, recommended):**

- Use `sealed class DashboardUiEvent` for user actions, e.g.:
    - `OnRefreshClicked`
    - `OnItemSelected(id: String)`

The `ViewModel` provides `fun onEvent(event: DashboardUiEvent)` and updates state accordingly.

---

## 5. Domain Layer Rules

Even for a local-only app, keep a thin domain layer to remain consistent and scalable.

### 5.1 Use Cases

- Place use cases in `domain/usecase/`.
- Each use case is a small class with a single responsibility, e.g.:
    - `GetDashboardDataUseCase`
    - `RefreshDashboardDataUseCase` (if needed)

**Patterns:**

- Expose them as `suspend operator fun invoke(...)` or `operator fun invoke(): Flow<...>`.

### 5.2 Repository Interfaces

- In `domain/repository/`, define interfaces like:

  ```kotlin
  interface DashboardRepository {
      fun observeDashboardData(): Flow<DashboardData>
  }
  ```

- The **implementation** lives in `data/repository/`.

For a very simple project, if domain layer is overkill, Copilot may reuse `core/model` and repository interfaces directly in `data`, but prefer keeping domain separate to stay aligned with clean architecture.

---

## 6. Data Layer Rules (Local-Only)

### 6.1 Local Data Source

Since there are **no remote APIs**, the data layer should:

- Use one of:
    - In-memory data source.
    - Room database.
    - DataStore or local JSON file.

Copilot should:

- Keep local data sources in `data/local/`.
    - Example: `DashboardLocalDataSource`.

### 6.2 Repositories

- Implement repository interfaces from `domain` in `data/repository/`.
- Repositories transform local entities to domain models if needed, using mappers.

**Example:**

```kotlin
class DashboardRepositoryImpl(
    private val localDataSource: DashboardLocalDataSource
) : DashboardRepository {
    override fun observeDashboardData(): Flow<DashboardData> =
        localDataSource.observeDashboardData()
}
```

### 6.3 Mappers

- Place mapping extensions in `data/mapper/`.
- Mappers convert between:
    - Local entity → Domain model
    - Domain model → UI model (optional; can also be in `presentation` or `ui`).

**Rules:**

- Mappers must be **pure**, no side-effects.
- Use extension functions: `fun LocalDashboardEntity.toDomain(): DashboardData`.

---

## 7. Kotlin & Coroutines Best Practices

### 7.1 Immutability

Copilot should:

- Prefer `val` over `var`.
- Use immutable collections (`List`, `Map`, `Set`).
- Make `UiState` and domain models immutable data classes.

### 7.2 Coroutines & Flow

- No `GlobalScope`.
- Use:
    - `viewModelScope` in ViewModel.
    - `Flow` for async streams.
    - `StateFlow` for UI state.
- Avoid starting coroutines inside composables except via side-effect APIs (`LaunchedEffect`).

**Exception handling:**

- In small apps, errors can be:
    - Caught in ViewModel.
    - Represented as part of `UiState` (`errorMessage: String?`).

---

## 8. Dependency Injection

For this small app, DI is optional, but if Hilt is used:

- Mark `Application` with `@HiltAndroidApp`.
- Provide `DashboardRepository` implementation in a Hilt module inside `di/`.
- Inject use cases and repositories into the `ViewModel` via constructor injection.

**Copilot should:**

- Prefer constructor injection in `ViewModel` and `Repository`.
- Avoid service locators or static singletons.

---

## 9. Testing Strategy

### 9.1 Unit Tests

Copilot should generate tests for:

- `DashboardViewModel`
    - Initial state.
    - State changes when data is loaded.
    - State changes for user events (e.g. refresh click).

- Use cases
    - Business logic is correct (even if simple).

- Repositories
    - Local data source interaction.
    - Mapping logic.

### 9.2 Test Setup

- Use `kotlinx-coroutines-test` for `TestDispatcher`, `runTest`.
- Use `MockK` or simple fakes for dependencies.
- For now, UI tests are optional but recommended:
    - Use `ComposeTestRule` to test `DashboardScreen` with fake `UiState`.

---

## 10. Coding Style & Conventions

### 10.1 Names

- Classes: `PascalCase` (e.g., `DashboardViewModel`, `GetDashboardDataUseCase`).
- Functions & properties: `camelCase`.
- Constants: `UPPER_SNAKE_CASE`.
- Packages: `lowercase.without.underscores`.

### 10.2 Documentation

- Add KDoc to:
    - Public use cases.
    - Repository interfaces.
    - Public composables (`DashboardScreen`, `DashboardRoute`).

### 10.3 Logging

- Keep logging minimal.
- For now, no heavy logging or analytics layer is required.

---

## 11. What Copilot Should Avoid

- Introducing navigation graphs or multiple screens unless explicitly requested.
- Adding Retrofit, OkHttp, or network code (this project has **no APIs**).
- Adding Room or DataStore automatically unless the user explicitly wants persistence.
- Mixing concerns:
    - No database/IO code in ViewModels.
    - No business logic in composables.
    - No direct UI manipulation in data layer.

---

## 12. Summary

This project implements a **single-dashboard-screen** Android app using **Compose + MVVM + Clean Architecture**, with **local-only data** and **no bottom navigation**.

Copilot should:

- Keep the architecture modular and scalable even if the app is small.
- Ensure separation of concerns between UI, presentation, domain, and data layers.
- Use best practices for Kotlin, coroutines, and Compose as described above.
- Avoid adding unnecessary complexity (no network, no multi-screen navigation) unless requested.
