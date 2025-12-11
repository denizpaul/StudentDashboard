# Jetpack Compose Best Practices Analysis

**Analysis Date:** December 10, 2025  
**Scope:** Comprehensive review of all Compose code against official Jetpack Compose Best Practices  
**Reference:** `.github/instructions/COMPOSE_BEST_PRACTICES.instructions.md`

---

## Executive Summary

The project demonstrates **strong adherence** to Compose best practices with excellent state management, preview implementation, and component architecture. The code quality is high with consistent patterns and proper separation of concerns.

**Overall Score:** 8.5/10

**Key Strengths:**
- ✅ Excellent stateless composable pattern
- ✅ Outstanding preview coverage with custom annotations
- ✅ Proper ViewModel decoupling
- ✅ Well-organized component structure

**Key Improvement Areas:**
- ⚠️ Missing event handling wiring
- ⚠️ LazyColumn using forEach instead of items()
- ⚠️ Missing LocalInspectionMode for preview safety
- ⚠️ Color parsing without remember optimization

---

## I. What We're Doing Right ✅

### 1. Stateless Composable Pattern (Best Practice I.1, I.2, I.3)

**Status:** ✅ **EXCELLENT**

**Files:** All UI components

**What's Working:**
```kotlin
// DashboardRoute.kt - Stateful container
@Composable
fun DashboardRoute(viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(uiState = uiState)
}

// DashboardScreen.kt - Stateless presentation
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,  // ✅ Receives state as parameter
    modifier: Modifier = Modifier
) {
    DashboardContent(state = uiState, modifier = modifier)
}

// EventCell.kt - Pure presentation component
@Composable
fun EventCell(
    icon: EventIcon,      // ✅ All parameters explicit
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    // Pure rendering logic only
}
```

**Why This Matters:**
- Components are reusable across different contexts
- Easy to preview without ViewModels
- Testable in isolation
- Follows unidirectional data flow

---

### 2. Custom Multipreview Annotations (Best Practice III.4)

**Status:** ✅ **EXCELLENT**

**File:** `ui/preview/PreviewAnnotations.kt`

**What's Working:**
```kotlin
@Preview(
    name = "Light Mode",
    group = "Theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    group = "Theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class PreviewLightDark

@Preview(name = "Normal Font", group = "Font Scale", showBackground = true, fontScale = 1.0f)
@Preview(name = "Large Font", group = "Font Scale", showBackground = true, fontScale = 1.5f)
@Preview(name = "Extra Large Font", group = "Font Scale", showBackground = true, fontScale = 2.0f)
annotation class PreviewFontScales

@Preview(name = "Landscape", group = "Orientation", showBackground = true, widthDp = 640, heightDp = 360)
annotation class PreviewLandscape
```

**Usage Example:**
```kotlin
@PreviewLightDark  // ✅ One annotation = 2 previews (light + dark)
@Composable
private fun DashboardScreenPopulatedPreview() {
    MonashTheme { DashboardScreen(uiState = previewDashboardState) }
}
```

**Benefits:**
- Reduces boilerplate significantly
- Consistent preview coverage across all components
- Easy to add new preview scenarios project-wide
- Aligns with Compose 1.6.0+ built-in patterns

---

### 3. PreviewParameter with Providers (Best Practice III.1, III.2)

**Status:** ✅ **EXCELLENT**

**Files:** `DashboardToolbar.kt`, `CardTile.kt`, `SectionTitle.kt`

**What's Working:**
```kotlin
// DashboardToolbar.kt
private class ToolbarTitleProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "Hey, Kier",
        "Good morning, Alex",
        "Welcome back, Jennifer",
        "Hi, A"  // Short name edge case
    )
}

@Preview(name = "Toolbar - Left Aligned (Default)", group = "DashboardToolbar", showBackground = true)
@Preview(name = "Toolbar - Left Aligned Dark", group = "DashboardToolbar", 
         uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DashboardToolbarPreview(
    @PreviewParameter(ToolbarTitleProvider::class) title: String  // ✅ Automatic iteration
) {
    MonashTheme { Surface { DashboardToolbar(title = title) } }
}
```

**Benefits:**
- One preview function generates multiple previews (4 in this case)
- Easy to add new test cases to the provider
- Reduces duplicate preview code
- Tests edge cases systematically

---

### 4. Component Sticker Sheet Organization (Best Practice I.6)

**Status:** ✅ **EXCELLENT**

**All Component Files**

**What's Working:**
```kotlin
// EventCell.kt - Organized preview groups
@Preview(name = "EventCell - Class with Range", group = "EventCell", showBackground = true)
@Preview(name = "EventCell - Task Single Time", group = "EventCell", showBackground = true)
@Preview(name = "EventCell - Assignment", group = "EventCell", showBackground = true)
@Preview(name = "EventCell - Dark Mode", group = "EventCell", showBackground = true)
@Preview(name = "EventCell - Long Text", group = "EventCell - Edge Cases", showBackground = true)

// SmallCell.kt - Multiple states covered
@Preview(name = "SmallCell - Parking", group = "SmallCell", showBackground = true)
@Preview(name = "SmallCell - Zero Values", group = "SmallCell", showBackground = true)
@Preview(name = "SmallCell - High Numbers", group = "SmallCell - Variants", showBackground = true)
@Preview(name = "SmallCell - Long Title", group = "SmallCell - Variants", showBackground = true)
```

**Benefits:**
- Visual documentation of all component states
- Grouped by component type for easy navigation
- Sub-groups for variants and edge cases
- Preview panel becomes a living style guide

---

### 5. Proper Modifier Pattern (Kotlin Convention F12)

**Status:** ✅ **EXCELLENT**

**All Components**

**What's Working:**
```kotlin
@Composable
fun SmallCell(
    title: String,
    dataPoints: List<DataPoint>,
    modifier: Modifier = Modifier  // ✅ Always last parameter, always has default
) {
    Row(
        modifier = modifier           // ✅ Applied to root element
            .fillMaxWidth()
            .padding(vertical = DashboardSpacing.smallSpacing),
        // ...
    )
}

@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier  // ✅ Last parameter
) {
    Row(modifier = modifier.fillMaxWidth(), /* ... */) { /* ... */ }
}
```

**Benefits:**
- Consistent API across all components
- Callers can customize layout without modifying component
- Follows official Compose guidelines
- Easy to chain modifiers in correct order

---

### 6. Lifecycle-Aware State Collection (Best Practice I.3)

**Status:** ✅ **EXCELLENT**

**File:** `DashboardRoute.kt`

**What's Working:**
```kotlin
@Composable
fun DashboardRoute(viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()  // ✅ Lifecycle-aware
    DashboardScreen(uiState = uiState)
}
```

**Why This Matters:**
- Stops collecting when UI is not visible
- Prevents memory leaks
- Conserves system resources
- Better than plain `collectAsState()` for production code

---

### 7. Immutable Data Classes (Kotlin Convention I.1)

**Status:** ✅ **EXCELLENT**

**File:** `DashboardUiState.kt`

**What's Working:**
```kotlin
data class DashboardUiState(
    val greeting: String = "",  // ✅ All properties are val (immutable)
    val sections: List<DashboardSection> = emptyList(),  // ✅ Immutable collection interface
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
```

**Benefits:**
- Predictable state changes
- Thread-safe by design
- Easy to reason about
- Compose can optimize recomposition

---

### 8. Sealed Classes for Events (Kotlin Convention N2)

**Status:** ✅ **EXCELLENT**

**File:** `DashboardUiEvent.kt`

**What's Working:**
```kotlin
sealed class DashboardUiEvent {
    data object OnRefresh : DashboardUiEvent()  // ✅ Modern Kotlin data object
}
```

**Benefits:**
- Exhaustive when expressions
- Type-safe event handling
- Easy to add new events
- Clear intent

---

### 9. Consistent Preview Coverage

**Status:** ✅ **EXCELLENT**

**All Component Files**

**Coverage Includes:**
- ✅ Light and dark themes
- ✅ Different font scales (accessibility)
- ✅ Various screen sizes
- ✅ Edge cases (long text, zero values, etc.)
- ✅ Component state variations

**Example from SmallCell.kt:**
```kotlin
@Preview(name = "SmallCell - Parking", group = "SmallCell", showBackground = true)
@Preview(name = "SmallCell - Zero Values", group = "SmallCell", showBackground = true)
@Preview(name = "SmallCell - High Numbers", group = "SmallCell - Variants", showBackground = true)
@Preview(name = "SmallCell - Dark Mode", group = "SmallCell", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "SmallCell - Long Title", group = "SmallCell - Variants", widthDp = 320)
@Preview(name = "SmallCell - Accessibility", group = "SmallCell - Variants", fontScale = 1.5f)
```

---

## II. Areas for Improvement ⚠️

### 1. Missing Event Handling Wiring

**Status:** ⚠️ **HIGH PRIORITY**

**Files:** `DashboardRoute.kt`, `DashboardScreen.kt`

**Current State:**
```kotlin
// DashboardRoute.kt
@Composable
fun DashboardRoute(viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    DashboardScreen(
        uiState = uiState
        // ❌ Missing: onEvent parameter not passed
    )
}

// DashboardScreen.kt
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    modifier: Modifier = Modifier
    // ❌ Missing: onEvent: (DashboardUiEvent) -> Unit
)
```

**The Problem:**
- `DashboardViewModel` has `onEvent(event: DashboardUiEvent)` method
- `DashboardUiEvent.OnRefresh` exists but can't be triggered
- Violates unidirectional data flow pattern
- No way for UI to communicate user actions to ViewModel

**Recommended Fix:**
```kotlin
// DashboardRoute.kt
@Composable
fun DashboardRoute(viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    DashboardScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent  // ✅ Wire event handler
    )
}

// DashboardScreen.kt
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit = {},  // ✅ Add with default for previews
    modifier: Modifier = Modifier
) {
    DashboardContent(
        state = uiState,
        onEvent = onEvent,  // ✅ Pass down to children
        modifier = modifier
    )
}

// Usage in UI:
// SwipeRefresh { onEvent(DashboardUiEvent.OnRefresh) }
// Button(onClick = { onEvent(DashboardUiEvent.OnItemClick(id)) })
```

**Impact:**
- **Effort:** Low (15 minutes)
- **Value:** High (enables interactivity)
- **Risk:** Low (backward compatible with default parameter)

---

### 2. LazyColumn Using forEach Instead of items()

**Status:** ⚠️ **HIGH PRIORITY - PERFORMANCE**

**File:** `DashboardScreen.kt`

**Current State:**
```kotlin
@Composable
private fun DashboardContent(state: DashboardUiState, modifier: Modifier = Modifier) {
    LazyColumn(/*...*/) {
        item { Text(text = state.greeting, /*...*/) }
        
        state.sections.forEach { section ->  // ❌ ANTI-PATTERN
            when (section.headerType) {
                HeaderType.DATE -> {
                    item {  // Each forEach iteration creates a single item
                        Card {
                            Column {
                                CardTile(title = section.header)
                                section.items.forEachIndexed { index, item ->  // ❌ Nested forEach
                                    when (item) {
                                        is DashboardItem.Session -> SessionItemCell(item)
                                        is DashboardItem.Task -> TaskItemCell(item)
                                    }
                                }
                            }
                        }
                    }
                }
                HeaderType.SECTION -> { /* ... */ }
            }
        }
    }
}
```

**The Problem:**
1. **No stable keys** - Compose can't identify which items changed
2. **Poor performance** - All items recompose when list changes
3. **No animations** - Item additions/removals can't be animated
4. **Violates Compose best practices** - Should use `items()` with keys

**Recommended Fix (Option 1 - Simple):**
```kotlin
@Composable
private fun DashboardContent(state: DashboardUiState, modifier: Modifier = Modifier) {
    LazyColumn(/*...*/) {
        item(key = "greeting") {  // ✅ Provide stable key
            Text(text = state.greeting, /*...*/)
        }
        
        items(
            items = state.sections,
            key = { section -> section.header }  // ✅ Stable key for efficient updates
        ) { section ->
            when (section.headerType) {
                HeaderType.DATE -> {
                    Card {
                        Column {
                            CardTile(title = section.header)
                            section.items.forEach { item ->  // OK for nested items within a card
                                when (item) {
                                    is DashboardItem.Session -> SessionItemCell(item)
                                    is DashboardItem.Task -> TaskItemCell(item)
                                    else -> {}
                                }
                            }
                        }
                    }
                }
                HeaderType.SECTION -> { /* ... */ }
            }
        }
    }
}
```

**Recommended Fix (Option 2 - Better Performance):**
```kotlin
// Create a flattened list model in DashboardUiState.kt
data class DashboardUiState(
    val greeting: String = "",
    val sections: List<DashboardSection> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    // ✅ Computed property for LazyColumn
    val dashboardItems: List<DashboardListItem> by lazy {
        buildList {
            add(DashboardListItem.Greeting(greeting))
            sections.forEach { section ->
                when (section.headerType) {
                    HeaderType.DATE -> add(DashboardListItem.DateCard(section))
                    HeaderType.SECTION -> {
                        add(DashboardListItem.SectionHeader(section.header))
                        add(DashboardListItem.SectionCard(section))
                    }
                }
            }
        }
    }
}

sealed class DashboardListItem {
    data class Greeting(val text: String) : DashboardListItem()
    data class DateCard(val section: DashboardSection) : DashboardListItem()
    data class SectionHeader(val title: String) : DashboardListItem()
    data class SectionCard(val section: DashboardSection) : DashboardListItem()
}

// Then in DashboardScreen.kt
@Composable
private fun DashboardContent(state: DashboardUiState, modifier: Modifier = Modifier) {
    LazyColumn(/*...*/) {
        items(
            items = state.dashboardItems,
            key = { item -> 
                when (item) {
                    is DashboardListItem.Greeting -> "greeting"
                    is DashboardListItem.DateCard -> "date_${item.section.header}"
                    is DashboardListItem.SectionHeader -> "header_${item.title}"
                    is DashboardListItem.SectionCard -> "card_${item.section.header}"
                }
            }
        ) { item ->
            when (item) {
                is DashboardListItem.Greeting -> Text(item.text, /*...*/)
                is DashboardListItem.DateCard -> DateCardComposable(item.section)
                is DashboardListItem.SectionHeader -> SectionTitle(item.title)
                is DashboardListItem.SectionCard -> SectionCardComposable(item.section)
            }
        }
    }
}
```

**Impact:**
- **Effort:** Medium (1-2 hours for Option 2)
- **Value:** High (significant performance improvement)
- **Benefits:**
  - ✅ Efficient recomposition (only changed items)
  - ✅ Supports animations
  - ✅ Better scrolling performance
  - ✅ Follows Compose best practices

---

### 3. Missing LocalInspectionMode for Preview Safety

**Status:** ⚠️ **MEDIUM PRIORITY**

**Files:** `DashboardScreen.kt` (SessionItemCell, TaskItemCell, ParkingItemCell)

**Current State:**
```kotlin
@Composable
private fun SessionItemCell(item: DashboardItem.Session) {
    val color = try {
        val androidColor = android.graphics.Color.parseColor(item.iconColor)  // ❌ May fail in preview
        Color(androidColor)
    } catch (_: Exception) {
        MaterialTheme.dashboardColors.sessionClassIndicator
    }
    // ...
}
```

**The Problem:**
- Previews run in Layoutlib (limited Android framework)
- `android.graphics.Color.parseColor()` may behave unpredictably
- Using try-catch is a workaround, not best practice
- Best Practice Rule I.5 recommends `LocalInspectionMode`

**Recommended Fix:**
```kotlin
@Composable
private fun SessionItemCell(item: DashboardItem.Session) {
    val isPreview = LocalInspectionMode.current
    
    val color = if (isPreview) {
        // ✅ Use safe fallback in preview
        MaterialTheme.dashboardColors.sessionClassIndicator
    } else {
        // ✅ Parse color only at runtime
        try {
            Color(android.graphics.Color.parseColor(item.iconColor))
        } catch (_: Exception) {
            MaterialTheme.dashboardColors.sessionClassIndicator
        }
    }
    
    EventCell(
        icon = EventIcon.DurationLine(color),
        time = EventTime.Range(item.startTime, item.endTime),
        title = item.title,
        subtitle = item.subtitle
    )
}
```

**Better Alternative - Move to ViewModel Layer:**
```kotlin
// In mapper or ViewModel
fun DashboardItem.Session.toUiModel() = SessionUiModel(
    // ...
    iconColor = parseColorSafe(iconColor) ?: DashboardColors.sessionIndicator
)

private fun parseColorSafe(colorString: String): Color? = try {
    Color(android.graphics.Color.parseColor(colorString))
} catch (_: Exception) {
    null
}
```

**Impact:**
- **Effort:** Low (30 minutes)
- **Value:** Medium (safer previews, cleaner code)
- **Benefits:**
  - ✅ Explicit preview detection
  - ✅ More predictable preview behavior
  - ✅ Follows Compose best practices

---

### 4. Missing remember for Expensive Calculations

**Status:** ⚠️ **MEDIUM PRIORITY - PERFORMANCE**

**Files:** `DashboardScreen.kt` (all item cells)

**Current State:**
```kotlin
@Composable
private fun ParkingItemCell(item: DashboardItem.Parking) {
    val dataPoints = item.badges.map { badge ->  // ❌ Recalculated on every recomposition
        val color = try {
            val androidColor = android.graphics.Color.parseColor(badge.color)
            Color(androidColor)
        } catch (_: Exception) {
            MaterialTheme.colorScheme.primary
        }
        DataPoint(
            label = badge.label,
            value = badge.value,
            color = color
        )
    }
    
    SmallCell(title = item.title, dataPoints = dataPoints)
}
```

**The Problem:**
- `map` and color parsing happen on **every recomposition**
- Wasted CPU cycles for same input
- Not leveraging Compose's optimization

**Recommended Fix:**
```kotlin
@Composable
private fun ParkingItemCell(item: DashboardItem.Parking) {
    val dataPoints = remember(item.badges) {  // ✅ Only recalculate when badges change
        item.badges.map { badge ->
            val color = try {
                Color(android.graphics.Color.parseColor(badge.color))
            } catch (_: Exception) {
                Color.Unspecified
            }
            DataPoint(
                label = badge.label,
                value = badge.value,
                color = color
            )
        }
    }
    
    SmallCell(title = item.title, dataPoints = dataPoints)
}
```

**Even Better - Move to Mapper:**
```kotlin
// In DashboardUiState mapper or ViewModel
fun DashboardItem.Parking.toDataPoints(): List<DataPoint> {
    return badges.map { badge ->
        DataPoint(
            label = badge.label,
            value = badge.value,
            color = parseColorSafe(badge.color) ?: Color.Unspecified
        )
    }
}

// Then in composable
@Composable
private fun ParkingItemCell(item: DashboardItem.Parking) {
    SmallCell(
        title = item.title,
        dataPoints = item.dataPoints  // ✅ Already computed in ViewModel layer
    )
}
```

**Impact:**
- **Effort:** Low-Medium (1 hour to apply everywhere)
- **Value:** Medium (performance optimization)
- **Benefits:**
  - ✅ Reduces recomposition work
  - ✅ Cleaner separation (data transformation in ViewModel)
  - ✅ Better performance with large lists

---

### 5. Preview Data Organization

**Status:** ℹ️ **LOW PRIORITY - MAINTAINABILITY**

**File:** `DashboardScreen.kt`

**Current State:**
```kotlin
@Preview(name = "Dashboard - Light", showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    // ❌ ~60 lines of inline preview data
    private val previewDashboardState = DashboardUiState(
        greeting = "Hey, Kier",
        sections = listOf(
            DashboardSection(
                header = "Today • Tue, 10 March",
                headerType = HeaderType.DATE,
                date = "Tue, 10 March",
                items = listOf(
                    DashboardItem.Session(/* ... */),
                    DashboardItem.Session(/* ... */),
                    DashboardItem.Task(/* ... */)
                )
            ),
            // ... more sections
        )
    )
}

private val previewEmptyDashboardState = DashboardUiState(/* ... */)  // ❌ Duplicate data

@PreviewLightDark
@Composable
private fun DashboardScreenPopulatedPreview() {  // ❌ Can't reuse easily
    MonashTheme { DashboardScreen(uiState = previewDashboardState) }
}
```

**The Problem:**
- Preview data duplicated across multiple functions
- Hard to maintain consistency
- Can't reuse in UI tests
- Violates DRY principle

**Recommended Fix:**
```kotlin
// Create: dashboard/ui/preview/DashboardPreviewData.kt
object DashboardPreviewData {
    val populated = DashboardUiState(
        greeting = "Hey, Kier",
        sections = listOf(
            DashboardSection(
                header = "Today • Tue, 10 March",
                headerType = HeaderType.DATE,
                date = "Tue, 10 March",
                items = listOf(
                    DashboardItem.Session(
                        id = "session-1",
                        iconColor = "#6750A4",
                        startTime = "09:00",
                        endTime = "10:30",
                        startDateTime = "2024-03-10T09:00:00",
                        endDateTime = "2024-03-10T10:30:00",
                        title = "FIT2099: Studio Workshop",
                        subtitle = "Building inclusive data-driven apps"
                    ),
                    // ... more items
                )
            ),
            // ... more sections
        )
    )
    
    val empty = DashboardUiState(
        greeting = "Hey, Kier",
        sections = emptyList()
    )
    
    val loading = DashboardUiState(
        greeting = "Hey, Kier",
        sections = emptyList(),
        isLoading = true
    )
    
    val error = DashboardUiState(
        greeting = "Hey, Kier",
        sections = emptyList(),
        errorMessage = "Unable to load dashboard data"
    )
}

// Option 1: Use directly in previews
@PreviewLightDark
@Composable
private fun DashboardScreenPopulatedPreview() {
    MonashTheme {
        DashboardScreen(uiState = DashboardPreviewData.populated)  // ✅ Clean reference
    }
}

// Option 2: Use with PreviewParameterProvider
class DashboardStateProvider : PreviewParameterProvider<DashboardUiState> {
    override val values = sequenceOf(
        DashboardPreviewData.populated,
        DashboardPreviewData.empty,
        DashboardPreviewData.loading,
        DashboardPreviewData.error
    )
}

@Preview(name = "Dashboard States", group = "Dashboard", showBackground = true)
@Composable
private fun DashboardScreenPreview(
    @PreviewParameter(DashboardStateProvider::class) state: DashboardUiState
) {
    MonashTheme {
        DashboardScreen(uiState = state)  // ✅ Generates 4 previews automatically
    }
}
```

**Impact:**
- **Effort:** Low (1 hour)
- **Value:** Medium (maintainability)
- **Benefits:**
  - ✅ Single source of truth
  - ✅ Reusable in UI tests (already done in ComponentTests.kt)
  - ✅ Easier to add new states
  - ✅ Follows Best Practice III.1-2

---

### 6. File Organization - Large Screen File

**Status:** ℹ️ **LOW PRIORITY - MAINTAINABILITY**

**File:** `DashboardScreen.kt` (~300 lines)

**Current Structure:**
```
DashboardScreen.kt
├── DashboardScreen (public)
├── DashboardContent (private)
├── SessionItemCell (private)
├── TaskItemCell (private)
├── ParkingItemCell (private)
├── Preview data declarations
└── 5+ Preview functions
```

**The Problem:**
- Single file has multiple responsibilities
- Harder to navigate
- Helper composables can't be tested independently
- Mixes presentation logic with preview logic

**Recommended Structure:**
```
dashboard/ui/
├── DashboardScreen.kt           (Main screen + DashboardContent)
├── DashboardItemCells.kt        (SessionItemCell, TaskItemCell, ParkingItemCell)
└── preview/
    └── DashboardPreviewData.kt  (Preview data objects)
```

**Example Split:**
```kotlin
// DashboardScreen.kt (keep main screen)
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit = {},
    modifier: Modifier = Modifier
) {
    DashboardContent(state = uiState, onEvent = onEvent, modifier = modifier)
}

@Composable
private fun DashboardContent(
    state: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(/*...*/) {
        items(state.sections, key = { it.header }) { section ->
            when (section.headerType) {
                HeaderType.DATE -> DateCardComposable(section)
                HeaderType.SECTION -> SectionCardComposable(section)
            }
        }
    }
}

// Previews
@PreviewLightDark
@Composable
private fun DashboardScreenPreview() {
    MonashTheme { DashboardScreen(uiState = DashboardPreviewData.populated) }
}

// ===== NEW FILE: DashboardItemCells.kt =====
@Composable
internal fun SessionItemCell(item: DashboardItem.Session) {
    val color = remember(item.iconColor) {
        parseColorSafe(item.iconColor) ?: Color.Unspecified
    }
    EventCell(/* ... */)
}

@Composable
internal fun TaskItemCell(item: DashboardItem.Task) {
    val color = remember(item.iconColor) {
        parseColorSafe(item.iconColor) ?: Color.Unspecified
    }
    EventCell(/* ... */)
}

@Composable
internal fun ParkingItemCell(item: DashboardItem.Parking) {
    val dataPoints = remember(item.badges) {
        item.badges.map { /* ... */ }
    }
    SmallCell(/* ... */)
}

private fun parseColorSafe(colorString: String): Color? = try {
    Color(android.graphics.Color.parseColor(colorString))
} catch (_: Exception) {
    null
}
```

**Impact:**
- **Effort:** Medium (2 hours)
- **Value:** Low-Medium (cleaner structure)
- **Benefits:**
  - ✅ Easier to navigate
  - ✅ Helper composables can be tested independently
  - ✅ Follows single responsibility principle
  - ✅ Better for team collaboration

---

## III. Summary & Action Plan

### Immediate Actions (This Sprint)

| Priority | Improvement | Files | Effort | Impact |
|----------|-------------|-------|--------|--------|
| 🔴 High | Add event handling wiring | `DashboardRoute.kt`, `DashboardScreen.kt` | 15 min | High |
| 🔴 High | Replace forEach with items() in LazyColumn | `DashboardScreen.kt` | 1-2 hours | High |
| 🟡 Medium | Add LocalInspectionMode checks | `DashboardScreen.kt` | 30 min | Medium |
| 🟡 Medium | Add remember for calculations | All item cells | 1 hour | Medium |

### Next Sprint

| Priority | Improvement | Files | Effort | Impact |
|----------|-------------|-------|--------|--------|
| 🟢 Low | Extract preview data to providers | `DashboardScreen.kt` | 1 hour | Medium |
| 🟢 Low | Split large screen file | `DashboardScreen.kt` | 2 hours | Low-Medium |

### Already Excellent (Keep Doing!)

1. ✅ Stateless composable pattern
2. ✅ Custom multipreview annotations
3. ✅ PreviewParameter with providers
4. ✅ Component sticker sheet organization
5. ✅ Proper modifier pattern
6. ✅ Lifecycle-aware state collection
7. ✅ Immutable data classes
8. ✅ Sealed classes for events
9. ✅ Comprehensive preview coverage

---

## IV. Code Examples Reference

### Example 1: Event Handling (Before/After)

**Before:**
```kotlin
@Composable
fun DashboardRoute(viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(uiState = uiState)  // ❌ No event handling
}

@Composable
fun DashboardScreen(uiState: DashboardUiState, modifier: Modifier = Modifier) {
    // ❌ Can't trigger refresh or other events
}
```

**After:**
```kotlin
@Composable
fun DashboardRoute(viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent  // ✅ Wire events
    )
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit = {},  // ✅ Default for previews
    modifier: Modifier = Modifier
) {
    // ✅ Can now: onEvent(DashboardUiEvent.OnRefresh)
}
```

### Example 2: LazyColumn items() (Before/After)

**Before:**
```kotlin
LazyColumn {
    state.sections.forEach { section ->  // ❌ No keys, inefficient
        item {
            Card { /* ... */ }
        }
    }
}
```

**After:**
```kotlin
LazyColumn {
    items(
        items = state.sections,
        key = { it.header }  // ✅ Stable key
    ) { section ->
        Card { /* ... */ }
    }
}
```

### Example 3: LocalInspectionMode (Before/After)

**Before:**
```kotlin
@Composable
private fun SessionItemCell(item: DashboardItem.Session) {
    val color = try {  // ❌ Try-catch workaround
        Color(android.graphics.Color.parseColor(item.iconColor))
    } catch (_: Exception) {
        MaterialTheme.dashboardColors.sessionClassIndicator
    }
}
```

**After:**
```kotlin
@Composable
private fun SessionItemCell(item: DashboardItem.Session) {
    val isPreview = LocalInspectionMode.current
    
    val color = if (isPreview) {  // ✅ Explicit preview handling
        MaterialTheme.dashboardColors.sessionClassIndicator
    } else {
        try {
            Color(android.graphics.Color.parseColor(item.iconColor))
        } catch (_: Exception) {
            MaterialTheme.dashboardColors.sessionClassIndicator
        }
    }
}
```

---

## V. References

- [Compose Best Practices Instructions](.github/instructions/COMPOSE_BEST_PRACTICES.instructions.md)
- [Kotlin Coding Conventions](.github/instructions/KOTLIN_CODING_CONVENTIONS.instructions.md)
- [Official Compose Guidelines](https://developer.android.com/jetpack/compose/mental-model)
- [State and Jetpack Compose](https://developer.android.com/jetpack/compose/state)
- [Lists and grids in Compose](https://developer.android.com/jetpack/compose/lists)

---

**Document Version:** 1.0  
**Last Updated:** December 10, 2025  
**Next Review:** After implementing high-priority improvements

