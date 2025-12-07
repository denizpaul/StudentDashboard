# Dashboard UI Tests - Espresso & Compose

## Overview

Comprehensive UI automation tests for the Dashboard feature using Jetpack Compose Testing and Espresso framework.

---

## 📁 Test Structure

```
androidTest/
└── com/example/monashapp/dashboard/
    ├── data/
    │   ├── mock/
    │   │   └── MockDashboardData.kt          # Mock data scenarios
    │   └── fake/
    │       └── FakeDashboardRepositoryForUI.kt # Fake repository for testing
    ├── ui/
    │   ├── DashboardScreenTest.kt             # Main screen tests (30+ tests)
    │   ├── components/
    │   │   └── ComponentTests.kt              # Component-level tests
    │   └── robot/
    │       └── DashboardRobot.kt              # Robot pattern for readability
```

---

## 🧪 Test Coverage

### 1. Dashboard Screen Tests (30+ tests)
**File**: `DashboardScreenTest.kt`

#### Categories:

**Greeting Tests**
- ✅ Displays greeting correctly
- ✅ Different greetings for different users

**Section Tests**
- ✅ Date section headers
- ✅ Section type headers
- ✅ Multiple sections display

**Session Tests**
- ✅ Session card display
- ✅ Session time ranges
- ✅ Session details (location, title)
- ✅ Multiple sessions

**Task Tests**
- ✅ Task card display
- ✅ Submitted status
- ✅ Pending status
- ✅ Multiple tasks

**Parking Tests**
- ✅ Parking zone display
- ✅ Badge counts (blue/red permits)
- ✅ Zero spots handling
- ✅ Multiple parking zones

**Empty State Tests**
- ✅ Empty dashboard displays greeting
- ✅ No sections when empty

**Mixed Content Tests**
- ✅ Sessions and tasks together
- ✅ Busy day with multiple items

**Scrolling Tests**
- ✅ Scroll to bottom content
- ✅ Scroll through busy day

**Scenario Tests**
- ✅ Sessions-only scenario
- ✅ Tasks-only scenario
- ✅ Parking-only scenario

---

### 2. Component Tests
**File**: `ComponentTests.kt`

#### Components Tested:

**EventCell**
- ✅ Single time display
- ✅ Time range display
- ✅ With/without subtitle
- ✅ Long titles

**SmallCell (Parking)**
- ✅ Title and badges display
- ✅ Zero values
- ✅ Multiple badges

**CardTile**
- ✅ Title display
- ✅ Long titles

**SectionTitle**
- ✅ With divider
- ✅ Without divider

---

## 🤖 Robot Pattern

The tests use the **Robot Pattern** for improved readability and maintainability.

### Example Usage:

```kotlin
@Test
fun dashboardScreen_displaysSessionCard() {
    // Given
    fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
    setContent()

    // Then
    composeTestRule.dashboardRobot {
        assertSessionCardIsComplete(
            title = "FIT2001: Tutorial",
            subtitle = "S4, 13 College Walk, Clayton",
            startTime = "10.30am",
            endTime = "1.30pm"
        )
    }
}
```

### Benefits:
- ✅ **Readable** - Tests read like specifications
- ✅ **Maintainable** - UI changes only require robot updates
- ✅ **Reusable** - Common assertions shared across tests
- ✅ **Type-safe** - Compile-time checks

---

## 📊 Mock Data Scenarios

**File**: `MockDashboardData.kt`

### Available Scenarios:

1. **Complete Dashboard** - All item types (sessions, tasks, parking)
2. **Sessions Only** - Multiple class sessions
3. **Tasks Only** - Assignments and quizzes
4. **Parking Only** - Parking availability
5. **Empty Dashboard** - No content
6. **Full Parking** - Zero spots available
7. **Busy Day** - Many items scheduled

### Usage:

```kotlin
fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
fakeRepository.setMockData(MockDashboardData.getSessionsOnlyDashboard())
fakeRepository.setMockData(MockDashboardData.getEmptyDashboard())
```

---

## 🚀 Running Tests

### Run All UI Tests:
```bash
./gradlew connectedAndroidTest
```

### Run Specific Test Class:
```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.monashapp.dashboard.ui.DashboardScreenTest
```

### Run Single Test:
```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.monashapp.dashboard.ui.DashboardScreenTest#dashboardScreen_displaysGreeting
```

### Run from Android Studio:
1. Right-click on test file
2. Select "Run 'DashboardScreenTest'"
3. Or click the green arrow next to individual test

---

## 🏗️ Test Architecture

### Dependency Injection

Tests use **Fake** implementations instead of mocks:

```kotlin
FakeDashboardRepositoryForUI
  ↓
GetDashboardDataUseCase
  ↓
DashboardViewModel
  ↓
DashboardScreen
```

### Benefits:
- ✅ Fast execution (no network/database)
- ✅ Deterministic results
- ✅ Easy to control scenarios
- ✅ Isolated from external dependencies

---

## 📋 Test Naming Convention

All tests follow:
```
feature_scenario_expectedResult
```

Examples:
- `dashboardScreen_displaysGreeting`
- `dashboardScreen_displaysSessionCard`
- `eventCell_displaysSingleTimeCorrectly`
- `smallCell_displaysZeroValues`

---

## ✅ Best Practices Followed

1. **AAA Pattern** - Arrange, Act, Assert
2. **Robot Pattern** - Readable DSL for UI interactions
3. **Mock Data** - Isolated, predictable scenarios
4. **Descriptive Names** - Clear test intent
5. **Single Responsibility** - One assertion per test
6. **Fast Execution** - No real dependencies
7. **Comprehensive Coverage** - Happy paths and edge cases

---

## 🔧 Configuration

### Dependencies Required:

```kotlin
androidTestImplementation("androidx.compose.ui:ui-test-junit4")
androidTestImplementation("androidx.test.ext:junit:1.1.5")
androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
debugImplementation("androidx.compose.ui:ui-test-manifest")
```

### Test Runner:

```kotlin
android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}
```

---

## 📈 Test Metrics

- **Total Tests**: 30+
- **Code Coverage**: Dashboard UI layer
- **Execution Time**: ~30-60 seconds (on emulator)
- **Test Isolation**: 100% (no shared state)

---

## 🎯 What's Tested

### ✅ Covered:
- UI component rendering
- Data display accuracy
- Multiple scenarios (sessions, tasks, parking)
- Empty states
- Scrolling behavior
- Component isolation

### ⏭️ Future Additions:
- User interaction tests (pull-to-refresh)
- Navigation tests
- Error state handling
- Loading states
- Accessibility tests
- Screenshot tests

---

## 🐛 Debugging Tests

### View Test Results:
```bash
./gradlew connectedAndroidTest --info
```

### Enable Test Logging:
Add to `gradle.properties`:
```
android.testInstrumentationRunnerArguments.notAnnotation=androidx.test.filters.FlakyTest
```

### Troubleshooting:
- **Test not found**: Check package names match
- **Compose not found**: Ensure `debugImplementation` for test-manifest
- **Timeout**: Increase timeout in robot: `composeTestRule.waitForIdle()`

---

## 📚 References

- [Compose Testing Guide](https://developer.android.com/jetpack/compose/testing)
- [Espresso Documentation](https://developer.android.com/training/testing/espresso)
- [Robot Pattern](https://jakewharton.com/testing-robots/)

---

**Last Updated**: December 7, 2025  
**Test Suite Status**: ✅ Ready for Execution

