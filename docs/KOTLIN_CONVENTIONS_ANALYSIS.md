# Kotlin Coding Conventions Analysis

**Analysis Date:** December 10, 2025  
**Scope:** Comprehensive review of all Kotlin code against official Kotlin Coding Conventions  
**Reference:** `.github/instructions/KOTLIN_CODING_CONVENTIONS.instructions.md`

---

## Executive Summary

The project demonstrates **strong adherence** to Kotlin coding conventions with excellent naming, immutability patterns, and code organization. However, there are **important gaps** in documentation (KDoc), explicit visibility modifiers, and explicit type declarations for library-style code.

**Overall Kotlin Conventions Score:** 7.5/10

**Key Strengths:**
- ✅ Excellent naming conventions (camelCase, PascalCase)
- ✅ Proper use of immutability (val, immutable collections)
- ✅ Clean package structure
- ✅ Proper backing properties with underscore
- ✅ Good use of sealed classes and data classes
- ✅ Idiomatic Kotlin patterns

**Key Improvement Areas:**
- ❌ Missing KDoc for public APIs
- ❌ Missing explicit visibility modifiers
- ❌ Missing explicit return types (library convention)
- ⚠️ Inconsistent trailing commas
- ⚠️ Some files could use better organization

---

## I. What We're Doing Right ✅

### 1. Naming Conventions (Rules N1-N7)

**Status:** ✅ **EXCELLENT**

#### Package Names (N1)
```kotlin
// ✅ All lowercase, properly structured
package com.example.monashapp
package com.example.monashapp.dashboard.presentation
package com.example.monashapp.dashboard.domain.usecase
package com.example.monashapp.core.model.dashboard
```

**Compliance:** ✅ Rule N1 - All packages use lowercase

---

#### Classes and Objects (N2)
```kotlin
// ✅ Upper Camel Case
class DashboardViewModel
class DashboardRepositoryImpl
object DashboardModule
sealed class DashboardItem
data class DashboardData
enum class HeaderType
```

**Compliance:** ✅ Rule N2 - Proper PascalCase for all types

---

#### Functions and Properties (N3)
```kotlin
// ✅ camelCase for functions
fun observeDashboard(): Flow<DashboardData>
fun onEvent(event: DashboardUiEvent)
fun toDomain(): DashboardData

// ✅ camelCase for properties
val uiState: StateFlow<DashboardUiState>
val greeting: String
val sections: List<DashboardSection>
```

**Compliance:** ✅ Rule N3 - Consistent camelCase

---

#### Backing Properties (N6)
```kotlin
// DashboardViewModel.kt
private val _uiState = MutableStateFlow(DashboardUiState())  // ✅ Underscore prefix
val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

// FakeDashboardRepositoryForUI.kt
private val _dashboardData = MutableStateFlow<LocalDashboardPayload?>(null)  // ✅ Underscore prefix
```

**Why This Is Good:**
- Clear distinction between mutable private and immutable public
- Follows Kotlin conventions exactly
- Prevents external mutation

**Compliance:** ✅ Rule N6 - Perfect backing property pattern

---

### 2. Immutability Preference (Rule I1)

**Status:** ✅ **EXCELLENT**

```kotlin
// DashboardUiState.kt
data class DashboardUiState(
    val greeting: String = "",  // ✅ val (immutable)
    val sections: List<DashboardSection> = emptyList(),  // ✅ Immutable collection interface
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

// DashboardModels.kt
data class DashboardData(
    val greeting: String,  // ✅ val
    val sections: List<DashboardSection>  // ✅ Immutable collection
)

data class DashboardSection(
    val header: String,  // ✅ All properties are val
    val headerType: HeaderType,
    val date: String? = null,
    val items: List<DashboardItem>
)
```

**Why This Is Good:**
- Thread-safe by design
- Predictable state changes
- Easy to reason about
- Compose optimizes for immutability

**Compliance:** ✅ Rule I1 - Excellent immutability pattern

---

### 3. Sealed Classes for Type Hierarchies

**Status:** ✅ **EXCELLENT**

```kotlin
// DashboardUiEvent.kt
sealed class DashboardUiEvent {
    data object OnRefresh : DashboardUiEvent()  // ✅ Modern Kotlin data object
}

// DashboardModels.kt
sealed class DashboardItem {
    data class Session(
        val id: String,
        val iconColor: String,
        // ...
    ) : DashboardItem()

    data class Task(
        val id: String,
        val iconColor: String,
        // ...
    ) : DashboardItem()

    data class Parking(
        val id: String,
        val title: String,
        // ...
    ) : DashboardItem()
}
```

**Why This Is Good:**
- Exhaustive when expressions
- Type-safe polymorphism
- Clear domain modeling
- Using modern `data object` (Kotlin 1.9+)

**Compliance:** ✅ Idiomatic Kotlin best practice

---

### 4. Extension Functions Placement (Rule O4)

**Status:** ✅ **GOOD**

```kotlin
// DashboardMappers.kt - Context-specific extensions together
fun LocalDashboardPayload.toDomain(): DashboardData = DashboardData(
    greeting = greeting,
    sections = sections.map { it.toDomain() }
)

fun LocalDashboardSection.toDomain(): DashboardSection = DashboardSection(
    header = header,
    headerType = when (headerType.lowercase()) {
        "date" -> HeaderType.DATE
        "section" -> HeaderType.SECTION
        else -> HeaderType.SECTION
    },
    date = date,
    items = items.map { it.toDomain() }
)

// DashboardUiState.kt - Mapper extension
fun DashboardData.toUiState(): DashboardUiState = DashboardUiState(
    greeting = greeting,
    sections = sections
)
```

**Why This Is Good:**
- Related extensions grouped together
- Mappers in dedicated mapper file
- Easy to find and maintain

**Compliance:** ✅ Rule O4 - Proper extension organization

---

### 5. File Naming (Rules O2, O3)

**Status:** ✅ **EXCELLENT**

```
✅ Single class/interface:
DashboardViewModel.kt → class DashboardViewModel
DashboardRepository.kt → interface DashboardRepository
DashboardRepositoryImpl.kt → class DashboardRepositoryImpl
MonashApplication.kt → class MonashApplication

✅ Multiple classes:
DashboardModels.kt → Contains DashboardData, DashboardSection, DashboardItem, etc.
PreviewAnnotations.kt → Contains multiple @Preview annotations

✅ Top-level functions:
DashboardMappers.kt → Contains multiple toDomain() extension functions
```

**Compliance:** ✅ Rules O2, O3 - Consistent file naming

---

### 6. Expression Body for Simple Functions (Rule I6)

**Status:** ✅ **GOOD**

```kotlin
// DashboardRepositoryImpl.kt
override fun observeDashboard(): Flow<DashboardData> =
    localDataSource.observeDashboard().map { it.toDomain() }  // ✅ Expression body

// GetDashboardDataUseCase.kt
open operator fun invoke(): Flow<DashboardData> = repository.observeDashboard()  // ✅ Expression body

// DashboardMappers.kt - All mapper functions use expression body
fun LocalDashboardPayload.toDomain(): DashboardData = DashboardData(/* ... */)
fun LocalDashboardSection.toDomain(): DashboardSection = DashboardSection(/* ... */)
```

**Why This Is Good:**
- More concise
- Clear intent (returns a value)
- Idiomatic Kotlin

**Compliance:** ✅ Rule I6 - Proper use of expression bodies

---

### 7. Proper Use of Data Classes

**Status:** ✅ **EXCELLENT**

```kotlin
// All model classes are data classes
data class DashboardData(val greeting: String, val sections: List<DashboardSection>)
data class DashboardSection(/* ... */)
data class DashboardUiState(/* ... */)
data class ParkingBadge(/* ... */)

// Event hierarchy uses data object (modern Kotlin)
sealed class DashboardUiEvent {
    data object OnRefresh : DashboardUiEvent()
}
```

**Why This Is Good:**
- Auto-generated equals(), hashCode(), copy()
- Immutable by default
- Perfect for value objects
- Using data object for singletons

**Compliance:** ✅ Idiomatic Kotlin

---

### 8. Dependency Injection with Hilt

**Status:** ✅ **GOOD**

```kotlin
// DashboardModule.kt
@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {  // ✅ Module is an object

    @Provides
    @Singleton
    fun provideLocalDataSource(): DashboardLocalDataSource = FakeDashboardLocalDataSource()

    @Provides
    @Singleton
    fun provideRepository(localDataSource: DashboardLocalDataSource): DashboardRepository =
        DashboardRepositoryImpl(localDataSource)
}

// MonashApplication.kt
@HiltAndroidApp
class MonashApplication : Application()  // ✅ Proper Hilt setup

// DashboardViewModel.kt
@HiltViewModel
class DashboardViewModel @Inject constructor(  // ✅ Constructor injection
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModel()
```

**Why This Is Good:**
- Constructor injection (testable)
- Singleton scoping
- Proper module structure

**Compliance:** ✅ Android/Hilt best practices

---

## II. Critical Gaps & Improvements Needed ❌

### 1. Missing KDoc for Public APIs (Rules D1, D2, L3)

**Status:** ❌ **CRITICAL - HIGH PRIORITY**

**Current State:**
```kotlin
// DashboardRepository.kt - NO KDOC
interface DashboardRepository {
    fun observeDashboard(): Flow<DashboardData>
}

// GetDashboardDataUseCase.kt - NO KDOC
open class GetDashboardDataUseCase(
    private val repository: DashboardRepository
) {
    open operator fun invoke(): Flow<DashboardData> = repository.observeDashboard()
}

// DashboardViewModel.kt - NO KDOC
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    fun onEvent(event: DashboardUiEvent) { /* ... */ }
}

// DashboardModels.kt - NO KDOC
data class DashboardData(
    val greeting: String,
    val sections: List<DashboardSection>
)
```

**The Problem:**
- Public APIs have no documentation
- New developers don't know how to use these classes
- No context for domain models
- Violates library development conventions (L3)

**Recommended Fix:**
```kotlin
// DashboardRepository.kt
/**
 * Repository for dashboard data.
 *
 * This repository provides access to the student dashboard information,
 * including upcoming sessions, tasks, and parking availability.
 */
interface DashboardRepository {
    /**
     * Observes dashboard data changes.
     *
     * Emits a new [DashboardData] whenever the dashboard content changes.
     * The flow is hot and keeps emitting until cancelled.
     *
     * @return A flow of [DashboardData] representing the current dashboard state
     */
    fun observeDashboard(): Flow<DashboardData>
}

// GetDashboardDataUseCase.kt
/**
 * Use case for retrieving dashboard data.
 *
 * This use case coordinates fetching dashboard information from the repository
 * and provides it to the presentation layer. It serves as the single entry point
 * for dashboard data in the domain layer.
 *
 * @property repository The dashboard repository providing data access
 */
open class GetDashboardDataUseCase(
    private val repository: DashboardRepository
) {
    /**
     * Retrieves dashboard data as a flow.
     *
     * @return A flow of [DashboardData] that emits whenever dashboard content changes
     */
    open operator fun invoke(): Flow<DashboardData> = repository.observeDashboard()
}

// DashboardViewModel.kt
/**
 * ViewModel for the dashboard screen.
 *
 * Manages the UI state for the student dashboard, including upcoming sessions,
 * tasks, and parking availability. Handles user events like refresh actions.
 *
 * The ViewModel exposes [uiState] as an immutable [StateFlow] and processes
 * user interactions through [onEvent].
 *
 * @property getDashboardData Use case for retrieving dashboard data
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    
    /**
     * Current UI state of the dashboard.
     *
     * Collect this flow in the UI layer to observe dashboard data changes.
     * The state includes greeting, sections, loading state, and error messages.
     */
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        observeDashboard()
    }

    /**
     * Handles user events from the dashboard UI.
     *
     * @param event The user event to process (e.g., [DashboardUiEvent.OnRefresh])
     */
    fun onEvent(event: DashboardUiEvent) {
        when (event) {
            DashboardUiEvent.OnRefresh -> observeDashboard()
        }
    }

    private fun observeDashboard() {
        viewModelScope.launch {
            getDashboardData()
                .collect { data ->
                    _uiState.value = data.toUiState()
                }
        }
    }
}

// DashboardModels.kt
/**
 * Domain-level representation of the complete dashboard.
 *
 * Contains all information displayed on the student dashboard,
 * organized into sections with different types of items.
 *
 * @property greeting Personalized greeting for the user (e.g., "Hey, Kier")
 * @property sections List of dashboard sections, each containing items to display
 */
data class DashboardData(
    val greeting: String,
    val sections: List<DashboardSection>
)

/**
 * A section of the dashboard containing related items.
 *
 * Sections group dashboard items by date or category (e.g., "Today", "Parking availability").
 *
 * @property header Display text for the section header
 * @property headerType Type of section (DATE for chronological, SECTION for categorical)
 * @property date Optional date string for date-based sections
 * @property items List of items to display in this section
 */
data class DashboardSection(
    val header: String,
    val headerType: HeaderType,
    val date: String? = null,
    val items: List<DashboardItem>
)

/**
 * Represents different types of items that can appear in the dashboard.
 *
 * The dashboard supports three types of items:
 * - [Session]: Scheduled classes or lectures
 * - [Task]: Assignments and quizzes
 * - [Parking]: Available parking spots
 */
sealed class DashboardItem {
    /**
     * A scheduled session (class, lecture, tutorial).
     *
     * @property id Unique identifier
     * @property iconColor Color code for the session indicator
     * @property startTime Display time for session start (e.g., "09:00")
     * @property endTime Display time for session end (e.g., "10:30")
     * @property startDateTime ISO 8601 datetime for session start
     * @property endDateTime ISO 8601 datetime for session end
     * @property title Session title (e.g., "FIT2099: Studio Workshop")
     * @property subtitle Location or description
     */
    data class Session(
        val id: String,
        val iconColor: String,
        val startTime: String,
        val endTime: String,
        val startDateTime: String,
        val endDateTime: String,
        val title: String,
        val subtitle: String
    ) : DashboardItem()

    /**
     * A task or assignment.
     *
     * @property id Unique identifier
     * @property iconColor Color code for the task indicator
     * @property time Due time display (e.g., "17:00")
     * @property dateTime ISO 8601 datetime for due date
     * @property title Task title (e.g., "MTK1000: Weekly quizzes")
     * @property subtitle Status description
     * @property status Current submission status
     */
    data class Task(
        val id: String,
        val iconColor: String,
        val time: String,
        val dateTime: String,
        val title: String,
        val subtitle: String,
        val status: TaskStatus
    ) : DashboardItem()

    /**
     * Parking availability information.
     *
     * @property id Unique identifier
     * @property title Parking lot name (e.g., "Clayton • North multi-level")
     * @property badges List of permit types and available spots
     * @property lastUpdated Display text for last update time
     */
    data class Parking(
        val id: String,
        val title: String,
        val badges: List<ParkingBadge>,
        val lastUpdated: String
    ) : DashboardItem()
}
```

**Impact:**
- **Effort:** Medium (3-4 hours for all public APIs)
- **Value:** HIGH (essential for maintainability and onboarding)
- **Users Affected:** All developers using the codebase

**Compliance:** ❌ Rules D1, D2, L3 violated

---

### 2. Missing Explicit Visibility Modifiers (Rule L1)

**Status:** ❌ **HIGH PRIORITY**

**Current State:**
```kotlin
// DashboardRepository.kt - Missing public
interface DashboardRepository {  // ❌ Implicit public
    fun observeDashboard(): Flow<DashboardData>  // ❌ Implicit public
}

// GetDashboardDataUseCase.kt - Missing public  
open class GetDashboardDataUseCase(  // ❌ Implicit public
    private val repository: DashboardRepository  // ✅ Explicit private
) {
    open operator fun invoke(): Flow<DashboardData> = repository.observeDashboard()  // ❌ Implicit public
}

// DashboardViewModel.kt
@HiltViewModel
class DashboardViewModel @Inject constructor(  // ❌ Implicit public
    private val getDashboardData: GetDashboardDataUseCase  // ✅ Explicit private
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())  // ✅ Explicit private
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()  // ❌ Implicit public

    fun onEvent(event: DashboardUiEvent) { /* ... */ }  // ❌ Implicit public
}

// DashboardModels.kt - All implicit public
data class DashboardData(  // ❌ Implicit public
    val greeting: String,
    val sections: List<DashboardSection>
)
```

**The Problem:**
- Unclear which APIs are public vs. internal
- Risk of accidental API exposure
- Violates library development conventions (L1)
- Makes refactoring harder (unclear public contract)

**Recommended Fix:**
```kotlin
// DashboardRepository.kt
public interface DashboardRepository {  // ✅ Explicit public
    public fun observeDashboard(): Flow<DashboardData>  // ✅ Explicit public
}

// GetDashboardDataUseCase.kt
public open class GetDashboardDataUseCase(  // ✅ Explicit public
    private val repository: DashboardRepository
) {
    public open operator fun invoke(): Flow<DashboardData> = repository.observeDashboard()  // ✅ Explicit
}

// DashboardViewModel.kt
@HiltViewModel
public class DashboardViewModel @Inject constructor(  // ✅ Explicit public
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    public val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()  // ✅ Explicit public

    public fun onEvent(event: DashboardUiEvent) { /* ... */ }  // ✅ Explicit public
    
    private fun observeDashboard() { /* ... */ }  // ✅ Explicit private
}

// DashboardModels.kt
public data class DashboardData(  // ✅ Explicit public
    val greeting: String,
    val sections: List<DashboardSection>
)

public data class DashboardSection(  // ✅ Explicit public
    val header: String,
    val headerType: HeaderType,
    val date: String? = null,
    val items: List<DashboardItem>
)

public sealed class DashboardItem {  // ✅ Explicit public
    public data class Session(/* ... */) : DashboardItem()
    public data class Task(/* ... */) : DashboardItem()
    public data class Parking(/* ... */) : DashboardItem()
}
```

**Alternative - Use `internal` for Module-Private:**
```kotlin
// If only used within the app module
internal interface DashboardRepository {  // ✅ Explicit internal
    internal fun observeDashboard(): Flow<DashboardData>
}

internal class DashboardRepositoryImpl(  // ✅ Explicit internal
    private val localDataSource: DashboardLocalDataSource
) : DashboardRepository {
    override fun observeDashboard(): Flow<DashboardData> =
        localDataSource.observeDashboard().map { it.toDomain() }
}
```

**Impact:**
- **Effort:** Low-Medium (2-3 hours, can be automated with IDE)
- **Value:** HIGH (clarity and API safety)
- **Priority:** HIGH

**Compliance:** ❌ Rule L1 violated

---

### 3. Missing Explicit Return Types (Rule L2)

**Status:** ❌ **MEDIUM-HIGH PRIORITY**

**Current State:**
```kotlin
// DashboardMappers.kt - Missing explicit types
fun LocalDashboardPayload.toDomain() = DashboardData(  // ❌ Type inferred
    greeting = greeting,
    sections = sections.map { it.toDomain() }
)

fun LocalDashboardSection.toDomain() = DashboardSection(  // ❌ Type inferred
    header = header,
    headerType = when (headerType.lowercase()) {
        "date" -> HeaderType.DATE
        "section" -> HeaderType.SECTION
        else -> HeaderType.SECTION
    },
    date = date,
    items = items.map { it.toDomain() }
)
```

**The Problem:**
- Return type can change accidentally if implementation changes
- Harder to understand function contract at a glance
- Violates library development conventions (L2)
- IDE might not catch breaking changes

**Recommended Fix:**
```kotlin
// DashboardMappers.kt
public fun LocalDashboardPayload.toDomain(): DashboardData = DashboardData(  // ✅ Explicit type
    greeting = greeting,
    sections = sections.map { it.toDomain() }
)

public fun LocalDashboardSection.toDomain(): DashboardSection = DashboardSection(  // ✅ Explicit type
    header = header,
    headerType = when (headerType.lowercase()) {
        "date" -> HeaderType.DATE
        "section" -> HeaderType.SECTION
        else -> HeaderType.SECTION
    },
    date = date,
    items = items.map { it.toDomain() }
)

public fun LocalDashboardItem.toDomain(): DashboardItem = when (this) {  // ✅ Explicit type
    is LocalDashboardItem.LocalSession -> DashboardItem.Session(/* ... */)
    is LocalDashboardItem.LocalTask -> DashboardItem.Task(/* ... */)
    is LocalDashboardItem.LocalParking -> DashboardItem.Parking(/* ... */)
}

public fun LocalParkingBadge.toDomain(): ParkingBadge = ParkingBadge(  // ✅ Explicit type
    label = label,
    value = value,
    color = color
)
```

**Impact:**
- **Effort:** Low (1 hour, mostly already correct)
- **Value:** MEDIUM (type safety and clarity)
- **Priority:** MEDIUM

**Compliance:** ❌ Rule L2 violated

---

### 4. Inconsistent Trailing Commas (Rule F11)

**Status:** ⚠️ **LOW-MEDIUM PRIORITY**

**Current State:**
```kotlin
// DashboardModels.kt - No trailing commas
data class DashboardSection(
    val header: String,
    val headerType: HeaderType,
    val date: String? = null,
    val items: List<DashboardItem>  // ❌ No trailing comma
)

sealed class DashboardItem {
    data class Session(
        val id: String,
        val iconColor: String,
        val startTime: String,
        val endTime: String,
        val startDateTime: String,
        val endDateTime: String,
        val title: String,
        val subtitle: String  // ❌ No trailing comma
    ) : DashboardItem()
}

// Some files have them, some don't - inconsistent
```

**The Problem:**
- Inconsistent style across codebase
- Adding new parameters causes noisy diffs
- Harder to reorder parameters

**Recommended Fix:**
```kotlin
// DashboardModels.kt
public data class DashboardSection(
    val header: String,
    val headerType: HeaderType,
    val date: String? = null,
    val items: List<DashboardItem>,  // ✅ Trailing comma
)

public sealed class DashboardItem {
    public data class Session(
        val id: String,
        val iconColor: String,
        val startTime: String,
        val endTime: String,
        val startDateTime: String,
        val endDateTime: String,
        val title: String,
        val subtitle: String,  // ✅ Trailing comma
    ) : DashboardItem()
}

// DashboardViewModel.kt
@HiltViewModel
public class DashboardViewModel @Inject constructor(
    private val getDashboardData: GetDashboardDataUseCase,  // ✅ Trailing comma
) : ViewModel() {
    // ...
}
```

**Benefits:**
- ✅ Cleaner git diffs when adding parameters
- ✅ Easier to reorder parameters
- ✅ Consistent style

**Impact:**
- **Effort:** Low (1 hour, can be automated)
- **Value:** LOW-MEDIUM (code quality)
- **Priority:** LOW

**Compliance:** ⚠️ Rule F11 partially followed

---

### 5. Missing Constants (Rule N4)

**Status:** ℹ️ **LOW PRIORITY**

**Current State:**
```kotlin
// No const val declarations found in the codebase
// Some values that could be constants are inline
```

**Potential Constants:**
```kotlin
// DashboardScreen.kt - Magic numbers should be constants
Card(
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)  // Magic number
)
```

**Recommended Fix:**
```kotlin
// DashboardTokens.kt or DashboardConstants.kt
public object DashboardConstants {
    public const val CARD_ELEVATION_DP: Int = 1
    public const val DEFAULT_ANIMATION_DURATION_MS: Int = 300
}

// Usage
Card(
    elevation = CardDefaults.cardElevation(
        defaultElevation = DashboardConstants.CARD_ELEVATION_DP.dp
    )
)
```

**Note:** Currently using `DashboardSpacing` object which is good. No critical constants missing.

**Impact:**
- **Effort:** Low (if needed)
- **Value:** LOW (mostly handled by DashboardSpacing)
- **Priority:** LOW

**Compliance:** ℹ️ Rule N4 - Mostly N/A (no constants needed yet)

---

## III. Minor Improvements ⚠️

### 1. Class Layout Order (Rule O5)

**Status:** ⚠️ **MEDIUM PRIORITY**

**Current State:**
```kotlin
// DashboardViewModel.kt - Good order
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModel() {

    // 1. Properties ✅
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    // 2. Init block ✅
    init {
        observeDashboard()
    }

    // 3. Public methods ✅
    fun onEvent(event: DashboardUiEvent) { /* ... */ }

    // 4. Private methods ✅
    private fun observeDashboard() { /* ... */ }
    
    // ✅ Good order
}
```

**Recommended Order (for consistency):**
1. Properties (public first, then private)
2. Init blocks
3. Secondary constructors (if any)
4. Public methods
5. Private methods
6. Companion object (if any)

**Current Status:** Already following good order in most files

**Compliance:** ✅ Rule O5 mostly satisfied

---

### 2. Boolean Parameter Naming (Rule I5)

**Status:** ⚠️ **LOW PRIORITY**

**Current State:**
```kotlin
// SectionTitle.kt
@Composable
fun SectionTitle(
    title: String,
    showDivider: Boolean = false,  // ✅ Good - clear name
    modifier: Modifier = Modifier
)
```

**Why This Is Good:**
- Boolean parameter has clear name (`showDivider`)
- Has default value
- Intent is obvious

**Compliance:** ✅ Rule I5 satisfied

---

## IV. Summary & Priority Action Plan

### Immediate Actions (High Priority - This Sprint)

| Priority | Issue | Files Affected | Effort | Impact |
|----------|-------|----------------|--------|--------|
| 🔴 HIGH | Add KDoc to public APIs | All domain/presentation classes | 3-4 hours | CRITICAL |
| 🔴 HIGH | Add explicit visibility modifiers | All public classes/functions | 2-3 hours | HIGH |
| 🟡 MEDIUM | Add explicit return types | Mapper functions | 1 hour | MEDIUM |

**Total Effort:** ~6-8 hours  
**Total Value:** Makes codebase professional and maintainable

---

### Next Sprint (Medium Priority)

| Priority | Issue | Files Affected | Effort | Impact |
|----------|-------|----------------|--------|--------|
| 🟡 MEDIUM | Add trailing commas | All data classes | 1 hour | LOW-MEDIUM |
| 🟢 LOW | Document internal implementation | Private functions/classes | 2 hours | LOW |

**Total Effort:** ~3 hours

---

## V. Code Examples - Before/After

### Example 1: DashboardRepository with Full Documentation

**Before:**
```kotlin
package com.example.monashapp.dashboard.domain.repository

import com.example.monashapp.core.model.dashboard.DashboardData
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun observeDashboard(): Flow<DashboardData>
}
```

**After:**
```kotlin
package com.example.monashapp.dashboard.domain.repository

import com.example.monashapp.core.model.dashboard.DashboardData
import kotlinx.coroutines.flow.Flow

/**
 * Repository for dashboard data.
 *
 * This repository provides access to the student dashboard information,
 * including upcoming sessions, tasks, and parking availability.
 *
 * The repository abstracts the data source and provides a clean domain API
 * for the presentation layer to consume.
 */
public interface DashboardRepository {
    /**
     * Observes dashboard data changes.
     *
     * Emits a new [DashboardData] whenever the dashboard content changes.
     * The flow is hot and keeps emitting until cancelled.
     *
     * @return A flow of [DashboardData] representing the current dashboard state
     */
    public fun observeDashboard(): Flow<DashboardData>
}
```

---

### Example 2: DashboardViewModel with Documentation

**Before:**
```kotlin
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        observeDashboard()
    }

    fun onEvent(event: DashboardUiEvent) {
        when (event) {
            DashboardUiEvent.OnRefresh -> observeDashboard()
        }
    }

    private fun observeDashboard() {
        viewModelScope.launch {
            getDashboardData()
                .collect { data ->
                    _uiState.value = data.toUiState()
                }
        }
    }
}
```

**After:**
```kotlin
/**
 * ViewModel for the dashboard screen.
 *
 * Manages the UI state for the student dashboard, including upcoming sessions,
 * tasks, and parking availability. Handles user events like refresh actions.
 *
 * The ViewModel exposes [uiState] as an immutable [StateFlow] and processes
 * user interactions through [onEvent].
 *
 * Example usage:
 * ```
 * @Composable
 * fun DashboardRoute(viewModel: DashboardViewModel = hiltViewModel()) {
 *     val uiState by viewModel.uiState.collectAsStateWithLifecycle()
 *     DashboardScreen(
 *         uiState = uiState,
 *         onEvent = viewModel::onEvent
 *     )
 * }
 * ```
 *
 * @property getDashboardData Use case for retrieving dashboard data
 */
@HiltViewModel
public class DashboardViewModel @Inject constructor(
    private val getDashboardData: GetDashboardDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    
    /**
     * Current UI state of the dashboard.
     *
     * Collect this flow in the UI layer to observe dashboard data changes.
     * The state includes greeting, sections, loading state, and error messages.
     */
    public val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        observeDashboard()
    }

    /**
     * Handles user events from the dashboard UI.
     *
     * Currently supports:
     * - [DashboardUiEvent.OnRefresh]: Refreshes dashboard data
     *
     * @param event The user event to process
     */
    public fun onEvent(event: DashboardUiEvent) {
        when (event) {
            DashboardUiEvent.OnRefresh -> observeDashboard()
        }
    }

    /**
     * Observes dashboard data and updates UI state.
     *
     * Launches a coroutine in [viewModelScope] to collect dashboard data
     * from the use case and map it to UI state.
     */
    private fun observeDashboard() {
        viewModelScope.launch {
            getDashboardData()
                .collect { data ->
                    _uiState.value = data.toUiState()
                }
        }
    }
}
```

---

### Example 3: Mappers with Explicit Types

**Before:**
```kotlin
fun LocalDashboardPayload.toDomain() = DashboardData(
    greeting = greeting,
    sections = sections.map { it.toDomain() }
)

fun LocalDashboardSection.toDomain() = DashboardSection(
    header = header,
    headerType = when (headerType.lowercase()) {
        "date" -> HeaderType.DATE
        "section" -> HeaderType.SECTION
        else -> HeaderType.SECTION
    },
    date = date,
    items = items.map { it.toDomain() }
)
```

**After:**
```kotlin
/**
 * Maps [LocalDashboardPayload] to domain [DashboardData].
 *
 * Converts the data layer representation to the domain model,
 * transforming all nested sections and items.
 *
 * @receiver The local payload to convert
 * @return Domain-level dashboard data
 */
public fun LocalDashboardPayload.toDomain(): DashboardData = DashboardData(
    greeting = greeting,
    sections = sections.map { it.toDomain() },
)

/**
 * Maps [LocalDashboardSection] to domain [DashboardSection].
 *
 * Converts section header type from string to enum and
 * transforms all nested items.
 *
 * @receiver The local section to convert
 * @return Domain-level dashboard section
 */
public fun LocalDashboardSection.toDomain(): DashboardSection = DashboardSection(
    header = header,
    headerType = when (headerType.lowercase()) {
        "date" -> HeaderType.DATE
        "section" -> HeaderType.SECTION
        else -> HeaderType.SECTION
    },
    date = date,
    items = items.map { it.toDomain() },
)
```

---

## VI. Compliance Summary Matrix

| Rule | Description | Status | Priority | Effort |
|------|-------------|--------|----------|--------|
| **O1** | Directory Structure | ✅ **EXCELLENT** | - | - |
| **O2** | File Naming (Single Class) | ✅ **EXCELLENT** | - | - |
| **O3** | File Naming (Multiple) | ✅ **EXCELLENT** | - | - |
| **O4** | Extension Function Placement | ✅ **GOOD** | - | - |
| **O5** | Class Layout Order | ✅ **GOOD** | - | - |
| **O6** | Overload Layout | ✅ **GOOD** | - | - |
| **N1** | Package Names | ✅ **EXCELLENT** | - | - |
| **N2** | Classes and Objects | ✅ **EXCELLENT** | - | - |
| **N3** | Functions & Properties | ✅ **EXCELLENT** | - | - |
| **N4** | Constants | ℹ️ N/A | Low | - |
| **N5** | Mutable Properties | ✅ **GOOD** | - | - |
| **N6** | Backing Properties | ✅ **EXCELLENT** | - | - |
| **N7** | Acronyms | ✅ **GOOD** | - | - |
| **F1-F12** | Formatting | ✅ **GOOD** | - | - |
| **I1** | Immutability | ✅ **EXCELLENT** | - | - |
| **I2** | Default Parameters | ✅ **GOOD** | - | - |
| **I3** | Lambda Naming | ✅ **GOOD** | - | - |
| **I4** | Avoid Redundancy | ✅ **GOOD** | - | - |
| **I5** | Named Arguments | ✅ **GOOD** | - | - |
| **I6** | Expression Bodies | ✅ **EXCELLENT** | - | - |
| **I7** | Functions vs Properties | ✅ **GOOD** | - | - |
| **I8** | Range Loops | ℹ️ N/A | - | - |
| **D1-D3** | KDoc | ❌ **MISSING** | 🔴 High | 3-4 hours |
| **L1** | Explicit Visibility | ❌ **MISSING** | 🔴 High | 2-3 hours |
| **L2** | Explicit Types | ⚠️ **PARTIAL** | 🟡 Medium | 1 hour |
| **L3** | KDoc for Public | ❌ **MISSING** | 🔴 High | (same as D1) |

---

## VII. Automated Fixes

Some issues can be automatically fixed using IDE:

### IntelliJ IDEA / Android Studio Settings

1. **Add Trailing Commas:**
   - Settings → Editor → Code Style → Kotlin
   - Other → Use trailing comma: YES

2. **Add Explicit Visibility:**
   - Analyze → Code Cleanup
   - Configure profile to add explicit modifiers

3. **Add Missing KDoc:**
   - Use "Generate KDoc" intention (Alt+Enter on class/function)
   - Then manually enhance with description

---

## VIII. Testing Conventions (Bonus Analysis)

**Current Test Naming:** ✅ **EXCELLENT**

```kotlin
// DashboardViewModelTest.kt
@Test
fun initialState_isEmpty() { /* ... */ }  // ✅ Good naming

@Test
fun observeDashboard_emitsData_updatesUiState() { /* ... */ }  // ✅ Descriptive

@Test
fun onRefreshEvent_retriggersDataObservation() { /* ... */ }  // ✅ Clear intent
```

**Why This Is Good:**
- Clear "what_when_then" pattern
- Easy to understand test purpose
- Follows Kotlin naming conventions

---

## IX. Next Steps

1. **This Sprint (6-8 hours):**
   - Add KDoc to all public domain/presentation APIs
   - Add explicit `public`/`private` visibility modifiers
   - Add explicit return types to mapper functions
   - Review and test documentation

2. **Next Sprint (3 hours):**
   - Add trailing commas project-wide
   - Document internal implementation details
   - Set up IDE code style profile

3. **Continuous:**
   - Enforce conventions in code review
   - Update this document as patterns evolve
   - Consider ktlint/detekt for automation

---

## X. References

- [Kotlin Coding Conventions Instructions](.github/instructions/KOTLIN_CODING_CONVENTIONS.instructions.md)
- [Official Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
- [Kotlin API Guidelines](https://kotlinlang.org/docs/api-guidelines-introduction.html)
- [Android Kotlin Style Guide](https://developer.android.com/kotlin/style-guide)

---

**Document Version:** 1.0  
**Last Updated:** December 10, 2025  
**Next Review:** After implementing KDoc and visibility modifiers

