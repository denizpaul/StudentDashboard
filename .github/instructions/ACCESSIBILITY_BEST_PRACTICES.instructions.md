Building an accessible Compose application requires understanding how the framework translates visual components into semantic information consumed by assistive technologies like screen readers. Below is a comprehensive guide to accessibility standards and rules in Jetpack Compose.

---

## Comprehensive Guide to Accessibility Standards in Compose

### I. Foundational Rules and API Defaults

These rules govern how to leverage Compose's built-in support for accessibility and fundamental semantics.

| Rule/Standard | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **R1. Prioritize Foundation and Material APIs** | Always rely on established Compose UI, Foundation, and Material APIs (`Text`, `Button`, `Modifier.clickable`) as they implement accessible practices and built-in semantics by default, minimizing manual work,. | **DO:** Use a Material `Checkbox(checked = true, onCheckedChange = {})` which internally handles accessibility and touch targets,. **DON'T:** Use low-level APIs like `Layout` or `Modifier.pointerInput` without manually handling semantics, as these lack defaults. |
| **R2. Understand the Semantics Tree** | Accessibility services use the **Semantics Tree** (which runs parallel to your composition) to understand the meaning and role of UI elements,. When developing custom low-level components, you must manually provide semantic information. | **DO:** When analyzing a `Button` containing an `Icon` and `Text`, understand that they are merged into a single semantic node for accessibility services,. |
| **R3. Inspect Semantics Trees** | Use the Layout Inspector or testing APIs to inspect both the **merged** and **unmerged** semantics trees to verify custom semantics and merging/clearing logic are applied correctly,. | **DO:** Use `composeTestRule.onRoot(useUnmergedTree = true).printToLog("MY TAG")` in tests to log the unmerged tree. |

### II. Interactive Elements and Touch Targets

Ensuring users can easily interact with controls is crucial for motor accessibility.

| Rule/Standard | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **R4. Enforce Minimum Touch Target Size (48dp)** | Any interactive element must meet a minimum size of **48dp** (density-independent pixels) in both width and height, following Material Design accessibility guidelines. If a component is visually smaller, Compose expands the touch target outside its boundaries, risking overlap with adjacent elements. | **DON'T:** Rely on implied touch target expansion: `Box(Modifier.clickable{...}).size(1.dp)`. **DO:** Explicitly guarantee minimum size using the `sizeIn` modifier: `Modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp)`. |
| **R5. Centralize Clickable Behavior** | When working with selection controls (`Switch`, `RadioButton`, `Checkbox`), lift the interactive behavior to the parent container (`Row` or `Column`) using `Modifier.toggleable` or `Modifier.selectable`, and set the nested control's click callback to null. | **DO:** Wrap the controls: `Row(Modifier.toggleable(value = checked, role = Role.Checkbox, onValueChange = {...})) { Text("Option"); Checkbox(checked = checked, onCheckedChange = null) }`. |
| **R6. Customize Click/Action Labels** | Provide specific labels for interactive elements (via `onClickLabel`, `onLongClickLabel`) to give accessibility services better context than the default hint ("Double tap to activate"). | **DO:** `Modifier.clickable(onClickLabel = "Open this article") { openArticle() }`. This allows TalkBack to announce: "Double tap to open this article". |

### III. Content Description and Contextual Semantics

These rules ensure non-text elements and structural elements are correctly communicated to screen readers.

| Rule/Standard | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **R7. Provide Descriptions for Informative Graphics** | For images or icons that convey meaning, actions, or state, provide a textual description using the `contentDescription` parameter. Use a localized string,. | **DO:** `Icon(imageVector = Icons.Filled.Share, contentDescription = stringResource(R.string.label_share))`. |
| **R8. Use Null Descriptions for Decorative Elements** | Set the `contentDescription` parameter to **null** for graphic elements that are purely decorative, ensuring they are ignored by accessibility services,. | **DO:** `Image(painter = image, contentDescription = null)`. |
| **R9. Identify Headings for Navigation** | For screens with rich, long text content (articles, news), mark section titles as headings to enable users to navigate quickly between sections using accessibility services. | **DO:** `Text(text = "Section Title", modifier = Modifier.semantics { heading() })`. |
| **R10. Use Live Regions for Dynamic Updates** | Mark components that dynamically update and require user attention (like alerts, pop-ups, or `Snackbar`) with `liveRegion` semantics. Use `Polite` mode for non-critical changes and reserve `Assertive` for time-sensitive, crucial content,. | **DO (Polite):** `PopupAlert(modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })`. |
| **R11. Convey State and Error Information** | Use specific semantics properties (`stateDescription`, `error`, `progressBarRangeInfo`) to provide detailed, expanded context about the component's status, especially for custom inputs or progress trackers. | **DO (Error):** `Error(modifier = Modifier.semantics { error("Please add both email and password") })`. **DO (State):** Set `stateDescription = if (selected) "Subscribed" else "Not subscribed"` before defining the component as toggleable. |

### IV. Semantic Customization and Hierarchy Control

These rules detail when and how to manually adapt the semantic tree to achieve correct grouping and flow.

| Rule/Standard | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **R12. Merge Descendants for Logical Entities** | Use **Semantics Merging** (`Modifier.semantics(mergeDescendants = true)`) when multiple visual children constitute a single logical element (like a custom card displaying a user's avatar, name, and date). The children's semantic data is merged into the parent node, announced as one entity,. | **DO:** For a complex list item: `Row(Modifier.semantics(mergeDescendants = true)) { Icon(...); Text(...) }`. This treats the row contents as a single focusable item. |
| **R13. Hide Decorative Semantics** | Use `hideFromAccessibility` to hide elements that are purely decorative, visually redundant (like a repeated watermark), or don't need to be announced. This preserves the semantics node for other tooling (like testing) but hides it from accessibility services. | **DO:** `Text("•", modifier = Modifier.semantics { hideFromAccessibility() })` for a decorative separator. |
| **R14. Overwrite Semantics with `clearAndSetSemantics`** | Use `Modifier.clearAndSetSemantics` when the default merging or existing semantics must be completely overridden or cleared, or when creating a custom interactive component that is not built on foundation layers. This replaces all previous semantics of the element and its descendants. | **DO:** When defining a custom toggle component (`FavoriteToggle`), use `clearAndSetSemantics` to apply the necessary role, state, and actions to the parent container, regardless of the child elements. |
| **R15. Define Custom Actions for Complex Gestures** | Use `customActions` to make complex interactions (like swipe to dismiss, or multiple actions hidden behind an item) accessible via the accessibility service menu (Voice Access, Switch Access),. When custom actions are defined, manually clear the original children's semantics using `clearAndSetSemantics`. | **DO:** Link a swipe gesture to an action label: `Modifier.semantics { customActions = listOf(CustomAccessibilityAction(label = "Remove article", action = { ... })) }`. |

### V. Traversal Order Customization

The traversal order (the sequence in which elements are focused by screen readers) is crucial for screen readability and coherence.

| Rule/Standard | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **R16. Group Elements for Traversal (`isTraversalGroup`)** | Use `Modifier.semantics { isTraversalGroup = true }` on a parent (`Row`, `Column`) when children elements should be read together as a logical block before the focus moves outside the block,. This is necessary for non-standard layouts like horizontal lists or multi-column data. | **DO:** For two side-by-side components (`CardBox`), set `isTraversalGroup = true` on each `CardBox` parent to ensure all content in the first box is read before moving to the second box. |
| **R17. Customize Order with `traversalIndex`** | Use the float property `traversalIndex` in conjunction with `isTraversalGroup` on child elements to manually customize their reading order within the defined group,. Elements with lower index values are read first. | **DO:** For a custom clock face (`CircularLayout` with `isTraversalGroup = true`), assign incremental indices to each number: `Box(modifier = Modifier.semantics { this.traversalIndex = value.toFloat() })` to enforce clockwise reading,. |

### VI. User-Scalable Content

Users must be able to scale content (text and UI) to fit their vision needs without breaking the layout.

| Rule/Standard | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **R18. Support Pinch-to-Zoom Gestures** | Implement pinch-to-zoom to allow users to adjust content size, eliminating the need for horizontal panning (the "zig-zag" motion) when text scales and reflows,. Use `Modifier.transformable` as a gesture detector to update the scale state. | **DO:** Use `Modifier.transformable` and bind the `zoomChange` to update a `scaleFactor` state variable. |
| **R19. Choose Scaling Strategy (Density vs. Font)** | **Density Scaling** scales *all* elements (text, components, padding) proportionally and is preferred for structured layouts like feeds to preserve visual hierarchy,. **Font Scaling** affects *only text elements* and is suitable for reading-intensive content where fixed container size is required,. | **DO (Density Scaling):** Create scaled `Density`: `Density(currentDensity.density * scaleFactor.floatValue, currentDensity.fontScale)`. **DO (Font Scaling):** Create scaled `Density`: `Density(currentDensity.density, currentDensity.fontScale * scaleFactor.floatValue)`. |
| **R20. Build and Test for All Scales** | Design components to be adaptive across various scales. Test your UI against both custom in-app scaling and system-wide font settings to ensure layouts adapt correctly without clipping or overlapping. | **DO:** Test components to function correctly within typical scale factor ranges (~0.75x to ~3.5x),. |

### VII. Tooling for Accessibility Verification

Use Android Studio's integrated tools to check for accessibility issues quickly.

| Rule/Standard | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **R21. Use UI Check Mode in Previews** | Use the Android Studio Preview feature's **UI Check mode** to verify layouts and check for accessibility problems, such as poor color contrast ratios, directly in the design view. | **DO:** Click the Menu icon next to a preview title and select "UI Check mode" to audit the UI,. |
| **R22. Test Multiple Accessibility Scenarios** | Leverage `@Preview` annotations with parameters like `fontScale` or custom multipreview templates to test combinations of accessibility settings quickly,. | **DO:** Define a multi-preview annotation that tests font scaling: `@FontScalePreviews` combines several `@Preview` annotations with different `fontScale` values. |