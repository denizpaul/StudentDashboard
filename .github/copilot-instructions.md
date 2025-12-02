# Copilot Instructions – Android Dashboard App

This document defines how GitHub Copilot should generate and refactor code for this project.

The project is a **single-screen Android app** built with:
- Kotlin
- Jetpack Compose
- MVVM + Clean Architecture
- No remote APIs (local-only data source)
- A **dashboard screen** with a **top app bar / toolbar** and **no bottom navigation bar**

Copilot should follow these rules when suggesting or modifying code.

---

## 1. High-Level Architecture

### 1.1 Layers

The app follows a **simplified Clean Architecture** with these layers:

1. **UI Layer (Compose)**
    - Pure composables (stateless where possible).
    - Displays data from the `ViewModel` via `UiState`.
    - Sends user events back to the `ViewModel`.

2. **Presentation Layer (MVVM)**
    - `ViewModel` for the single **DashboardScreen**.
    - Holds UI state as `StateFlow<DashboardUiState>`.
    - Contains minimal logic: orchestrates use cases / repositories, maps domain models to UI models.

3. **Domain Layer**
    - Use case(s) encapsulating business logic (even if simple).
    - Domain models (optional in this small app; can reuse core models where appropriate).
    - Interfaces for repositories if abstraction makes sense.

4. **Data Layer (Local-only)**
    - Local data source(s): in-memory, Room, Preference/DataStore, or local JSON/assets.
    - Repository implementation that exposes data to domain/presentation.
    - No network, no Retrofit.

5. **Core / Shared**
    - `core:model` for shared model classes.
    - `core:common` for utilities (e.g., `Result`, dispatcher providers).
    - `core:ui` & `core:designsystem` for shared composables and design tokens if/when needed.

---

## 2. Modules & Project Structure

Even though this app is small, organize code in a way that scales.

For a **single-module MVP** (start simple):

```text
app/
  src/main/java/com/example/dashboard/
    core/
      common/
      model/
    data/
      repository/
      local/
      mapper/
    domain/
      usecase/
      repository/
    dashboard/           // feature: dashboard
      presentation/
      ui/
    di/
```

Later, this can evolve into separate modules:

- `:app` – entry point, scaffolding, navigation (only single screen here)
- `:core:model` – shared models
- `:core:common` – shared utilities
- `:core:data` – repositories & data sources
- `:core:domain` – use cases & repository interfaces
- `:feature:dashboard` – Dashboard feature (ViewModel + UI)

For this project, Copilot should:

- Keep **feature-specific code** under `dashboard/` packages.
- Keep **cross-cutting concerns** under `core/`.

### 2.1 Dashboard Feature Packages

```text
app/src/main/java/com/example/dashboard/dashboard/
  presentation/
    DashboardViewModel.kt
    DashboardUiState.kt
    DashboardUiEvent.kt (optional)
  ui/
    DashboardScreen.kt
    DashboardContent.kt
    components/
      DashboardCard.kt
      MetricRow.kt
```

---

## 3. UI & Compose Best Practices

### 3.1 Single Dashboard Screen

- The app has:
    - One `MainActivity`.
    - One main composable, e.g. `DashboardRoute()` → `DashboardScreen()`.

**MainActivity:**

- Set content with `setContent { AppTheme { DashboardRoute() } }`.
- No bottom navigation, no NavHost. Navigation can be added later if needed.

**DashboardRoute composable:**

- Responsible for:
    - Creating / getting the `DashboardViewModel` (e.g., `hiltViewModel()` or `viewModel()`).
    - Collecting `UiState` (`collectAsStateWithLifecycle` when using lifecycle-runtime-compose).
    - Passing `state` and event callbacks down to `DashboardScreen`.

### 3.2 Stateless vs Stateful Composables

Copilot should:

- Make `DashboardScreen` **stateless**:
    - Parameters: `uiState: DashboardUiState`, `onEvent: (DashboardUiEvent) -> Unit`.
- Use smaller stateless composables in `components/` folder:
    - Example: `DashboardToolbar`, `DashboardMetricCard`, `DashboardSection`.

**Do NOT:**

- Inject repositories or use cases into composables.
- Launch coroutines in UI composables directly (beyond simple side effects with `LaunchedEffect`).

### 3.3 Toolbar / TopAppBar

- Use **Material 3** `TopAppBar` or `CenterAlignedTopAppBar`.
- The toolbar is part of `DashboardScreen`, e.g.:

    - A `Scaffold` with:
        - `topBar = { DashboardToolbar(...) }`
        - `content = { DashboardContent(...) }`

### 3.4 State Handling in Compose

- `DashboardUiState` should be a **data class** containing all fields needed to render the dashboard.
- Exposed from `ViewModel` as `StateFlow<DashboardUiState>`.
- In composables, collect using `collectAsStateWithLifecycle()` (preferred) or `collectAsState()`.

**Example pattern:**

```kotlin
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
DashboardScreen(
    uiState = uiState,
    onEvent = viewModel::onEvent
)
```

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
