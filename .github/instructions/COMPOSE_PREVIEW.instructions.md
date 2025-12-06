The following is a comprehensive list of best practices and API support for writing better Jetpack Compose previews, drawn directly from the sources.

---

## I. Structural Best Practices and Data Handling

These practices ensure previews are reliable, fast, and independent of the complex application runtime environment.

1.  **Decouple from ViewModels:** Avoid directly instantiating `ViewModels` within previews, as they may have complex dependencies or trigger unsupported operations, such as network calls.
2.  **Create Stateless Composables:** If a Composable relies on a `ViewModel`, create a separate Composable variation that accepts each necessary parameter (data and events) as **individual arguments** instead of depending on the `ViewModel`. This practice ensures the composables are reusable, easier to test, and able to be previewed.
3.  **Define Screens by State:** To leverage the `@Preview` annotation effectively, define your screens based on the **state they receive as input** and the events they output.
4.  **Work Around Runtime Limitations:** Since previews run outside of a device using a ported Android framework called Layoutlib, they have limitations, including **no network access** and **no file access**.
5.  **Use `LocalInspectionMode` for Placeholders:** Read from `LocalInspectionMode.current` to determine if the composable is being rendered in a preview. If it evaluates to `true`, **customize the preview** by showing placeholder content, such as sample data or a placeholder image, instead of attempting limited operations like network requests.
6.  **Component Sticker Sheets:** Use multiple `@Preview` functions within a single file to create a visual documentation library—a "component sticker sheet"—that showcases all standard UI components (buttons, cards, dialogues) and their different states, locales, or themes. Use the grouping feature to keep this sheet organized by component type.

## II. API Support for Preview Configuration (`@Preview` Parameters)

The `@Preview` annotation allows extensive configuration to test UI appearance under various conditions. You can inspect all available parameters by clicking the `@Preview` annotation in Android Studio.

| Parameter | Function and Details | Citation |
| :--- | :--- | :--- |
| `widthDp`, `heightDp` | Manually set the dimensions of the preview. Values are interpreted as DP and do not require adding `.dp`. | |
| `device` | Define configurations for different devices. You can use device IDs (e.g., `id:pixel_4`) or custom specs (e.g., `spec:width=px,height=px,dpi=int…`). | |
| `locale` | Test different user locales, such as `"fr-rFR"`. | |
| `showBackground` | Set to `true` to display a background color behind the Composable. | |
| `backgroundColor` | Set the background color; must be an **ARGB Long** (e.g., `0xFF00FF00`). | |
| `showSystemUi` | Set to `true` to display the system status and action bars inside the preview. | |
| `uiMode` | Change the preview's UI behavior using `Configuration.UI_*` constants, allowing testing of features like **Night Mode**. | |
| `wallpaper` | Used to switch wallpapers to see how the UI reacts to **dynamic color** (requires Compose 1.4.0+). | |
| `fontScale` | Adjusts the text scaling factor. | |
| `apiLevel` | Specifies the Android API level for rendering. | |
| `name`, `group` | Used for organizational purposes within the Preview panel. | |

## III. Advanced Preview Patterns

These patterns reduce boilerplate and allow for comprehensive testing of data and combinations.

1.  **Using `@PreviewParameter` for Large Data Sets:** To pass a large data set or preview multiple states easily, add a parameter to the preview function annotated with **`@PreviewParameter`**.
2.  **Define a Provider Class:** The provider must implement `PreviewParameterProvider<T>` and expose the sample data as a **sequence** (e.g., `sequenceOf()`). The system renders one preview for each data element in the sequence.
3.  **Limit Rendered Previews:** If necessary, use the optional `limit` parameter within `@PreviewParameter` to restrict the number of previews rendered from the data sequence.
4.  **Create Custom Multipreview Annotations:** Define a custom annotation class that itself contains multiple `@Preview` annotations with different configurations (e.g., two different font scales or locales). Applying this single custom annotation to a Composable will render all the variants defined inside it.
5.  **Combining Annotations:** You can combine multiple custom multipreview annotations and normal `@Preview` annotations, but note that **each annotation acts independently** and does not automatically show every possible combination of all settings.
6.  **Use Built-in Multipreview Templates:** For Compose 1.6.0-alpha01+, use built-in APIs like `@PreviewScreenSizes`, `@PreviewFontScales`, `@PreviewLightDark`, and `@PreviewDynamicColors` to preview common scenarios with a single annotation.

## IV. Android Studio Preview Modes and Workflow Support

Android Studio offers several features to streamline development and testing using previews:

1.  **Interactive Mode:** Allows real-time interaction (clicking buttons, typing in text fields) within the Preview window to test click handlers and UI reactions without deploying the full app.
2.  **Animation Preview:** Used to scroll through, loop, and inspect every single frame of slick animations for fine-tuning micro-interactions.
3.  **UI Check Mode:** Audits and verifies layouts, checking for accessibility issues like contrast ratios.
4.  **Run Preview:** Deploys a specific `@Preview` directly to a connected device or emulator as a new `Activity`, sharing the same context and permissions of the project app. This is useful for testing specific screen sizes or device features. (Note: Arguments applied to the `@Preview` annotation, like `widthDp` or `locale`, are **not applied** when running the preview.)
5.  **Grouping:** Previews for the same composable are automatically grouped under headers, allowing you to easily **collapse and uncollapse** them to keep the Preview panel organized.
6.  **Code Navigation:** Double-click on a Composable in the preview to jump directly to the corresponding line of code in the editor.
7.  **Focus Mode:** Recommended for focusing on one preview at a time to save rendering resources.
8.  **Zoom to Selection:** Use right-click functionality to perfectly frame a specific component you are interested in.
9.  **Copy Render:** Every rendered preview can be copied as an image by right-clicking it.