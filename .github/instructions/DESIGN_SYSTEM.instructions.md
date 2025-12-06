Material Design 3 (M3), also known as Material You or M3 Expressive, is the next evolution of Material Design implemented in Jetpack Compose. These guidelines detail the principles, theming, components, and best practices for building Compose-based Android applications using Material 3.

## I. M3 Setup and Core Theming Principles

### A. Dependency and Initialization
1.  **Dependency:** To begin using Material 3, add the Compose Material 3 dependency to your `build.gradle` files:
    `implementation "androidx.compose.material3:material3: $material3_version"`
2.  **Experimental APIs:** If you use certain newer M3 APIs, you must explicitly **opt-in** using the `@ExperimentalMaterial3Api` annotation at the function or file level.
3.  **Theming Structure:** An M3 theme is composed of three core subsystems: **color scheme, typography, and shapes**. Changes made to these values are automatically reflected across all M3 components used in the application.
4.  **MaterialTheme Composable:** Implement the theme using the M3 `MaterialTheme` composable, defining the necessary parameters:
    ```kotlin
    MaterialTheme(
        colorScheme = /* ... */,
        typography = /* ... */,
        shapes = /* ... */
    ) {
        // M3 app content
    }
    ```

### B. Color Scheme Guidelines
The foundation of an M3 color scheme is a set of five key colors, each relating to a tonal palette of 13 tones used by M3 components.

1.  **Generation:** While manual creation is possible, use the **Material Theme Builder tool** to generate a `ColorScheme` based on brand source colors, including exporting the necessary Compose theming code (`Color.kt` and `Theme.kt`).
2.  **Light/Dark Support:** Use `isSystemInDarkTheme()` to define and switch between custom light and dark color schemes based on the system setting.
3.  **Dynamic Color (Material You):**
    *   Dynamic color, where an algorithm derives custom colors from the user’s wallpaper, is a key personalization feature of Material You.
    *   This feature is available on **Android 12 (API level S) and above**.
    *   If dynamic color is available, use builder functions like `dynamicLightColorScheme(LocalContext.current)` or `dynamicDarkColorScheme(LocalContext.current)` to set the color scheme; otherwise, fall back to a custom `ColorScheme`.

4.  **Color Usage and Roles (`MaterialTheme.colorScheme`):**
    *   **Primary:** The base color, used for high-emphasis components like prominent buttons, active states, and elevated surfaces.
    *   **Secondary:** Used for less prominent components, such as filter chips, expanding color expression.
    *   **Tertiary:** Used for contrasting accents to balance primary and secondary colors.
    *   **Emphasis:** To ensure adequate contrast and emphasis, use "on-color" combinations, such as `onPrimary` on top of `primary`, or `onPrimaryContainer` on top of `primaryContainer`.

### C. Typography Guidelines
M3 uses a simplified type scale with five main groups: display, headline, title, body, and label, each available in large, medium, and small sizes.

1.  **Typography Class:** Use the M3 `Typography` class, which offers defaults for each style, allowing developers to omit parameters they do not intend to customize.
2.  **Customization:** Customize text styles by changing properties like `fontWeight`, `fontFamily`, and `letterSpacing`.
3.  **Usage:** Retrieve text styles within Composables using **`MaterialTheme.typography`** (e.g., `MaterialTheme.typography.titleLarge`).

### D. Shape Guidelines
Shapes direct attention and communicate brand identity. The M3 shape scale supports a range of roundedness.

1.  **Shape Scale:** The `Shapes` class supports five sizes: **Extra Small, Small, Medium, Large, and Extra Large**.
2.  **Definition:** Use the M3 `Shapes` class to define and override default rounding values (e.g., using `RoundedCornerShape(4.dp)`).
3.  **Application:** Shapes can be passed to `MaterialTheme` to apply globally or customized on a per-component basis. Basic shapes like `RectangleShape` (no radius) and `CircleShape` (fully circled edges) are also available.

## II. M3 Design and Component Application

### A. Elevation and Emphasis
1.  **Elevation:** M3 represents elevation primarily through **tonal color overlays** (tonal elevation), in addition to traditional shadows. Increasing tonal elevation uses a more prominent tone.
2.  **Surface Composable:** The M3 `Surface` composable supports both `tonalElevation` and `shadowElevation` parameters. In dark themes, the elevation overlays are tonal color overlays derived from the primary color slot.
3.  **Emphasis:** Emphasis is achieved using variations of color and its "on-color" combinations (`surface`, `surface-variant`, `background` used with their `on-surface` variants) or by using different font weights for text.

### B. Material Components
M3 provides diverse versions of standard components, categorized by the level of emphasis required.

1.  **Emphasis Hierarchy:**
    *   **Highest Emphasis:** Extended floating action button.
    *   **High Emphasis:** Filled button.
    *   **Low Emphasis:** Text button.
2.  **Customization:** Components expose flexible APIs via default objects (e.g., `CardDefaults`) to customize their colors and elevation (e.g., `cardColors`, `cardElevation`).

### C. Adaptive and Navigation Components
Material guidelines provide several navigation components tailored for different device sizes to enhance user experience, ergonomics, and reachability.

1.  **`NavigationBar` (Bottom Navigation):** Used for **compact devices** (phones) targeting five or fewer destinations.
2.  **`NavigationRail`:** Used for **small-to-medium size tablets** or phones in landscape mode to improve ergonomics.
3.  **`NavigationDrawer` (Permanent or Modal):** Used for **medium-to-large size tablets** where there is ample space to show detail.

## III. Adaptive Design and Architecture Integration

M3 and modern Android architecture emphasize building adaptive UIs that handle various form factors and configuration changes.

1.  **Adaptive Layouts:** **Implement adaptive canonical layouts** to optimize the user experience on multiple device types (phones, tablets, foldables, ChromeOS).
2.  **Composable Design:** Build UI components that are **reusable and composable** to naturally support adaptive design, allowing components to be rearranged to fit various screen sizes without major refactoring.
3.  **State Management:** Design the architecture to preserve UI state across configuration changes, such as display resizing, folding, and orientation changes, to provide a seamless user experience.
4.  **State Holders:** State holders, such as `ViewModel` objects, should expose UI state that **adapts dynamically** to different window size classes. Components like `NavigationSuiteScaffold` can use this adapting UI state to automatically switch navigation patterns (e.g., between a `NavigationBar`, `NavigationRail`, or `NavigationDrawer`) based on screen space.

## IV. Accessibility and System Features

### A. Accessibility Standards
M3 components incorporate built-in accessibility standards to support inclusive product design.

1.  **Color Accessibility:** The dynamic color system and tonal palettes are designed to meet accessibility standards for color contrast. Always use the appropriate color roles (e.g., **`on-primary` on `primary`**) to ensure accessible contrast, and avoid mismatching container and on-colors.
2.  **Typography Accessibility:** The M3 type scale provides a dynamic framework of size categories that scale appropriately across devices, enhancing readability.

### B. System UI Integration (Android 12+)
M3 benefits from system-level visual style changes on Android 12 and above, often requiring no extra work in Compose.

1.  **Ripple Effect:** The M3 ripple now uses a subtle sparkle effect when surfaces are pressed, available on Android 12 and above.
2.  **Overscroll:** Scrolling containers (like `LazyColumn` or `LazyRow`) use a stretch effect at the edge, which is enabled by default in Compose Foundation 1.1.0 and above, regardless of the API level.