# Compose Preview Documentation

This document provides an overview of all Compose previews implemented in the MonashApp project, following best practices from the Compose Preview instructions.

## Overview

All components in this project include comprehensive previews that cover:
- ✅ Light and Dark themes
- ✅ Different screen sizes (phone, tablet, small screens)
- ✅ Accessibility testing (large fonts)
- ✅ Edge cases and various states
- ✅ Component parameter variations using `@PreviewParameter`

## Preview Structure

### Component Files with Previews

#### 1. **DashboardToolbar.kt**
Location: `app/src/main/java/com/example/monashapp/dashboard/ui/components/DashboardToolbar.kt`

**Previews included:**
- Multiple greeting variations using `PreviewParameterProvider`
- Light/Dark mode testing
- Long text scenarios
- Accessibility (large font) testing
- Narrow screen testing

**Preview groups:**
- `DashboardToolbar` - Main preview group

#### 2. **TodaySessionCard.kt**
Location: `app/src/main/java/com/example/monashapp/dashboard/ui/components/TodaySessionCard.kt`

**Previews included:**
- Both session categories (CLASS and ASSIGNMENT)
- Light/Dark mode for all variations
- Long text content testing
- Accessibility (large font) testing
- Small screen compatibility
- Multiple session scenarios via `PreviewParameterProvider`

**Preview groups:**
- `TodaySessionCard` - Main preview group
- `TodaySessionCard - Categories` - Category-specific previews

#### 3. **UpcomingTasksCard.kt**
Location: `app/src/main/java/com/example/monashapp/dashboard/ui/components/UpcomingTasksCard.kt`

**Previews included:**
- Single task, multiple tasks, and empty list states
- Different task statuses (NOT_SUBMITTED, SUBMITTED)
- Light/Dark mode testing
- Long text scenarios
- Accessibility (large font) testing
- Multiple scenarios via `PreviewParameterProvider`

**Preview groups:**
- `UpcomingTasksCard` - Main preview group
- `UpcomingTasksCard - States` - State-specific previews

#### 4. **ParkingAvailabilityList.kt**
Location: `app/src/main/java/com/example/monashapp/dashboard/ui/components/ParkingAvailabilityList.kt`

**Previews included:**
- Various availability scenarios (typical, all full, high availability)
- Light/Dark mode testing
- Long zone names
- Accessibility (large font) testing
- Small screen testing
- Multiple parking lists via `PreviewParameterProvider`

**Preview groups:**
- `ParkingAvailabilityList` - Main preview group
- `ParkingAvailabilityList - States` - State-specific previews

#### 5. **DashboardScreen.kt**
Location: `app/src/main/java/com/example/monashapp/dashboard/ui/DashboardScreen.kt`

**Previews included:**
- Complete screen in light/dark modes
- Different day types (typical, busy, light)
- Multiple screen sizes (phone, small phone, tablet)
- Landscape orientation
- Accessibility (large font) testing
- State variations via `PreviewParameterProvider`

**Preview groups:**
- `DashboardScreen` - Main preview group
- `DashboardScreen - States` - State-specific previews
- `DashboardScreen - Screen Sizes` - Size variations
- `DashboardScreen - Orientations` - Orientation testing

## Custom Preview Annotations

Location: `app/src/main/java/com/example/monashapp/ui/preview/PreviewAnnotations.kt`

These reusable multi-preview annotations reduce boilerplate:

### `@PreviewLightDark`
Automatically generates both light and dark mode previews.

```kotlin
@PreviewLightDark
@Composable
fun MyComponent() { /* ... */ }
```

### `@PreviewFontScales`
Tests three font scales: Normal (1.0), Large (1.5), Extra Large (2.0).

```kotlin
@PreviewFontScales
@Composable
fun MyComponent() { /* ... */ }
```

### `@PreviewScreenSizes`
Tests three screen sizes: Pixel 5, Small Phone (320dp), Tablet (800dp).

```kotlin
@PreviewScreenSizes
@Composable
fun MyComponent() { /* ... */ }
```

### `@PreviewComplete`
Comprehensive testing: 4 previews combining light/dark modes with normal/large fonts.

```kotlin
@PreviewComplete
@Composable
fun MyComponent() { /* ... */ }
```

### `@PreviewLandscape`
Tests landscape orientation (640x360dp).

```kotlin
@PreviewLandscape
@Composable
fun MyComponent() { /* ... */ }
```

## Component Sticker Sheet

Location: `app/src/main/java/com/example/monashapp/dashboard/ui/components/ComponentStickerSheet.kt`

A visual documentation library showcasing all dashboard components in various states. This serves as a "design system gallery" where designers and developers can quickly see all component variations.

**Included galleries:**
- `ComponentStickerSheet` - All components together
- `SessionTypesGallery` - All session type variations
- `TaskStatusGallery` - All task status variations
- `ParkingStatesGallery` - All parking availability states
- `EmptyStatesPreview` - Edge cases with empty data
- `LongContentPreview` - Edge cases with long text

## Best Practices Applied

### 1. **Stateless Composables**
All preview composables are stateless and accept parameters directly, avoiding ViewModel dependencies.

### 2. **PreviewParameterProvider**
Used to test multiple data variations efficiently:
- `ToolbarTitleProvider` - Different greeting messages
- `SessionProvider` - Various session types and content
- `TaskListProvider` - Different task list scenarios
- `ParkingListProvider` - Various parking availability states
- `DashboardStateProvider` - Complete dashboard state variations

### 3. **Organized Grouping**
Previews are grouped logically using the `group` parameter for easy navigation in Android Studio.

### 4. **Theme Consistency**
All previews are wrapped in `MonashTheme { }` to ensure design tokens are applied.

### 5. **Accessibility Testing**
Large font scale (1.5f) previews ensure components handle text scaling properly.

### 6. **Edge Case Coverage**
- Empty lists
- Very long text
- Narrow screens (320dp)
- High numbers (e.g., 150 parking spots)
- Zero availability

### 7. **Multi-preview Annotations**
Custom annotations reduce boilerplate and ensure consistency across all components.

## How to View Previews

### In Android Studio:

1. **Open any component file** with previews
2. **Switch to Split or Design mode** in the top-right corner
3. **Use the Preview panel** on the right side
4. **Navigate using groups** - Click on group headers to collapse/expand

### Interactive Mode:

1. Click the **"Interactive Mode"** icon in the Preview toolbar
2. Interact with buttons, text fields, etc. directly in the preview
3. Test click handlers without deploying the app

### Run Preview on Device:

1. Right-click on any `@Preview` function
2. Select **"Run 'PreviewName'"**
3. The preview will deploy to your connected device/emulator

## Preview Naming Conventions

- **Light/Dark**: Indicates theme variant
- **Accessibility**: Large font scale testing
- **Small Screen**: Testing on narrow devices (320dp)
- **Tablet**: Testing on large screens (800dp+)
- **Landscape**: Orientation testing
- **States**: Different data/UI states
- **Edge Cases**: Unusual or boundary conditions

## Adding New Previews

When creating new components, follow this checklist:

- [ ] Add basic `@Preview` with light theme
- [ ] Add dark theme preview using `@PreviewLightDark` or separate `@Preview` with `uiMode = UI_MODE_NIGHT_YES`
- [ ] Add accessibility preview with `fontScale = 1.5f`
- [ ] Add edge cases (empty, long text, etc.)
- [ ] Use `@PreviewParameter` for data variations if applicable
- [ ] Group related previews using the `group` parameter
- [ ] Add to ComponentStickerSheet if it's a reusable component
- [ ] Wrap in `MonashTheme { Surface { ... } }` for proper theming

## Common Preview Parameters

| Parameter | Purpose | Example |
|-----------|---------|---------|
| `name` | Display name in Preview panel | `"Light Mode"` |
| `group` | Organize related previews | `"DashboardScreen"` |
| `showBackground` | Show background color | `true` |
| `backgroundColor` | Set background color | `0xFFFFFFFF` |
| `uiMode` | Test UI modes (night mode) | `Configuration.UI_MODE_NIGHT_YES` |
| `fontScale` | Test text scaling | `1.5f` |
| `widthDp` / `heightDp` | Set dimensions | `320` |
| `device` | Use device spec | `"id:pixel_5"` |

## Resources

- **Compose Preview Instructions**: `.github/instructions/COMPOSE_PREVIEW.instructions.md`
- **Preview Annotations**: `app/src/main/java/com/example/monashapp/ui/preview/PreviewAnnotations.kt`
- **Component Gallery**: `app/src/main/java/com/example/monashapp/dashboard/ui/components/ComponentStickerSheet.kt`

## Troubleshooting

### Previews not rendering?
- Ensure Android Studio is using the latest version
- Try **Build > Refresh Previews** or **Invalidate Caches / Restart**
- Check that the preview function is `@Composable` and marked with `@Preview`

### Theme not applied?
- Wrap preview content in `MonashTheme { }`
- Ensure Material3 dependencies are included

### Data not showing?
- Check that preview data providers implement `PreviewParameterProvider<T>` correctly
- Verify the `sequenceOf()` contains valid data

---

**Last Updated**: December 6, 2025
**Maintained By**: MonashApp Development Team

