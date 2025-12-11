# Jetpack Compose Accessibility Analysis

**Analysis Date:** December 10, 2025  
**Scope:** Comprehensive review of Compose code against Accessibility Best Practices  
**Reference:** `.github/instructions/ACCESSIBILITY_BEST_PRACTICES.instructions.md`

---

## Executive Summary

The project uses **Material 3 Foundation APIs** which provide good baseline accessibility, but **lacks explicit accessibility enhancements** for custom components and complex layouts. While the visual design is clean and the preview testing includes font scaling, there are **significant gaps** in semantic annotations, content descriptions, and assistive technology support.

**Overall Accessibility Score:** 4/10

**Critical Gaps:**
- ❌ No semantic merging for complex components
- ❌ Missing content descriptions for icons
- ❌ No heading markers for section navigation
- ❌ Missing touch target size enforcement
- ❌ No traversal order customization
- ❌ No custom accessibility actions

**Strengths:**
- ✅ Using Material 3 Foundation APIs (R1)
- ✅ Preview testing with font scales (R22)
- ✅ Responsive text scaling support

---

## I. Current State - What We're Doing Right ✅

### 1. Foundation and Material APIs (Rule R1)

**Status:** ✅ **GOOD**

**Evidence:**
```kotlin
// All components use Material 3 APIs
@Composable
fun EventCell(/*...*/) {
    Row(/*...*/) {  // ✅ Foundation API
        Text(/*...*/)  // ✅ Material API with built-in semantics
        Icon(/*...*/)  // ✅ Material API
    }
}

@Composable
fun SmallCell(/*...*/) {
    Row(/*...*/) {  // ✅ Foundation API
        Text(/*...*/)  // ✅ Material API
    }
}
```

**Why This Helps:**
- Material components have built-in accessibility semantics
- `Text` automatically exposes text content to screen readers
- Foundation layouts respect semantic boundaries

**Compliance:** ✅ Rule R1 satisfied

---

### 2. Font Scale Testing in Previews (Rule R22)

**Status:** ✅ **EXCELLENT**

**Evidence:**
```kotlin
// PreviewAnnotations.kt
@Preview(name = "Normal Font", group = "Font Scale", showBackground = true, fontScale = 1.0f)
@Preview(name = "Large Font", group = "Font Scale", showBackground = true, fontScale = 1.5f)
@Preview(name = "Extra Large Font", group = "Font Scale", showBackground = true, fontScale = 2.0f)
annotation class PreviewFontScales

// EventCell.kt
@Preview(
    name = "EventCell - Accessibility",
    group = "EventCell - Edge Cases",
    showBackground = true,
    fontScale = 1.5f  // ✅ Testing accessibility
)
@Composable
private fun EventCellAccessibilityPreview() {
    MonashTheme {
        val dashboardColors = MaterialTheme.dashboardColors
        EventCell(
            icon = EventIcon.DurationLine(dashboardColors.sessionClassIndicator),
            time = EventTime.Range("10.30am", "1.30pm"),
            title = "FIT2001: Tutorial",
            subtitle = "S4, 13 College Walk, Clayton"
        )
    }
}
```

**Why This Helps:**
- Catches text clipping issues early
- Ensures layouts adapt to user font preferences
- Tests readability for vision-impaired users

**Compliance:** ✅ Rule R22 satisfied

---

### 3. Scalable Typography

**Status:** ✅ **GOOD**

**Evidence:**
```kotlin
// All text uses sp units from MaterialTheme.typography
Text(
    text = title,
    style = MaterialTheme.typography.titleSmall,  // ✅ Scalable typography
    color = MaterialTheme.colorScheme.onSurface
)

Text(
    text = state.greeting,
    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),  // ✅ sp-based
    color = MaterialTheme.colorScheme.onBackground
)
```

**Why This Helps:**
- Text automatically scales with system font settings
- Supports users with vision impairments
- No hardcoded text sizes

**Compliance:** ✅ Rule R20 partially satisfied

---

## II. Critical Accessibility Gaps ❌

### 1. Missing Semantic Merging for Complex Components (Rule R12)

**Status:** ❌ **CRITICAL - HIGH PRIORITY**

**Current State:**
```kotlin
// EventCell.kt - NOT semantically merged
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),  // ❌ No semantic merging
        verticalAlignment = Alignment.Top
    ) {
        // Icon section
        Box(/*...*/) { /* Decorative color indicator */ }
        
        // Time section
        Column(/*...*/) {
            Text(text = time.startTime, /*...*/)  // Announced separately
            Text(text = "-${time.endTime}", /*...*/)  // Announced separately
        }
        
        // Content section
        Column(/*...*/) {
            Text(text = title, /*...*/)  // Announced separately
            subtitle?.let { Text(text = it, /*...*/) }  // Announced separately
        }
    }
}
```

**The Problem:**
- Screen reader announces **4-5 separate elements** instead of one logical event
- User hears: "10.30am" → "-1.30pm" → "FIT2001: Tutorial" → "S4, 13 College Walk, Clayton"
- Confusing and disjointed experience
- Violates "logical entity" principle

**Recommended Fix:**
```kotlin
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {  // ✅ Merge into single announcement
                // Optionally customize the announcement
                contentDescription = buildString {
                    when (time) {
                        is EventTime.Single -> append("${time.time}, ")
                        is EventTime.Range -> append("${time.startTime} to ${time.endTime}, ")
                    }
                    append(title)
                    subtitle?.let { append(", $it") }
                }
            },
        verticalAlignment = Alignment.Top
    ) {
        // Icon section - hide from accessibility
        Box(
            modifier = Modifier
                .width(DashboardSpacing.indicatorCircle)
                .semantics { hideFromAccessibility() },  // ✅ Purely decorative
            contentAlignment = Alignment.Center
        ) {
            // ... existing icon code
        }
        
        // ... rest of layout
    }
}
```

**Screen Reader Output:**
- **Before:** "10.30am" → "-1.30pm" → "FIT2001: Tutorial" → "S4, 13 College Walk, Clayton"
- **After:** "10.30am to 1.30pm, FIT2001: Tutorial, S4, 13 College Walk, Clayton"

**Impact:**
- **Effort:** Medium (1-2 hours for all components)
- **Value:** CRITICAL (makes app usable with screen readers)
- **Users Affected:** Blind and vision-impaired users

**Compliance:** ❌ Rule R12 violated

---

### 2. Missing Content Descriptions for Icons (Rule R7, R8)

**Status:** ❌ **CRITICAL - HIGH PRIORITY**

**Current State:**
```kotlin
// EventCell.kt
icon.iconRes?.let { iconRes ->
    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = null,  // ❌ Should describe purpose or be explicitly null
        modifier = Modifier.size(12.dp),
        tint = Color.White
    )
}
```

**The Problem:**
- Icon has `contentDescription = null` but it's **informative**, not decorative
- The task icon conveys meaning (assignment vs. class vs. task)
- Should describe what the icon represents
- Currently, screen reader users miss this context

**Recommended Fix:**
```kotlin
// EventCell.kt - Add icon type parameter
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconDescription: String? = null,  // ✅ Add description parameter
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {
                contentDescription = buildString {
                    iconDescription?.let { append("$it, ") }  // ✅ Include icon meaning
                    when (time) {
                        is EventTime.Single -> append("${time.time}, ")
                        is EventTime.Range -> append("${time.startTime} to ${time.endTime}, ")
                    }
                    append(title)
                    subtitle?.let { append(", $it") }
                }
            },
        // ...
    )
}

// DashboardScreen.kt - Usage
@Composable
private fun SessionItemCell(item: DashboardItem.Session) {
    // ...
    EventCell(
        icon = EventIcon.DurationLine(color),
        time = EventTime.Range(item.startTime, item.endTime),
        title = item.title,
        subtitle = item.subtitle,
        iconDescription = "Class session"  // ✅ Describe the icon
    )
}

@Composable
private fun TaskItemCell(item: DashboardItem.Task) {
    // ...
    EventCell(
        icon = EventIcon.TaskCircle(color, iconRes = R.drawable.ic_task),
        time = EventTime.Single(item.time),
        title = item.title,
        subtitle = item.subtitle,
        iconDescription = "Task"  // ✅ Describe the icon
    )
}
```

**Alternative - Use Localized Strings:**
```kotlin
// strings.xml
<string name="cd_class_session">Class session</string>
<string name="cd_task">Task</string>
<string name="cd_assignment">Assignment</string>

// DashboardScreen.kt
EventCell(
    // ...
    iconDescription = stringResource(R.string.cd_class_session)  // ✅ Localized
)
```

**Impact:**
- **Effort:** Low-Medium (2 hours)
- **Value:** HIGH (provides context to screen reader users)
- **Users Affected:** Blind users relying on icon semantics

**Compliance:** ❌ Rules R7, R8 violated

---

### 3. Missing Heading Markers for Navigation (Rule R9)

**Status:** ❌ **HIGH PRIORITY**

**Current State:**
```kotlin
// DashboardScreen.kt
item {
    Text(
        text = state.greeting,  // ❌ Should be marked as heading
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Start
    )
}

// CardTile.kt
@Composable
fun CardTile(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,  // ❌ Section header should be marked as heading
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
        modifier = modifier.fillMaxWidth()
    )
}

// SectionTitle.kt
@Composable
fun SectionTitle(
    title: String,
    showDivider: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // ...
        Text(
            text = title,  // ❌ Section title should be marked as heading
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            // ...
        )
    }
}
```

**The Problem:**
- Screen reader users can't jump between sections quickly
- "Jump to next heading" gesture doesn't work
- Forces users to listen to entire content linearly
- Missing quick navigation capability

**Recommended Fix:**
```kotlin
// DashboardScreen.kt
item {
    Text(
        text = state.greeting,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Start,
        modifier = Modifier.semantics { heading() }  // ✅ Mark as heading (H1 equivalent)
    )
}

// CardTile.kt
@Composable
fun CardTile(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
        modifier = modifier
            .fillMaxWidth()
            .semantics { heading() }  // ✅ Mark as section heading (H2 equivalent)
    )
}

// SectionTitle.kt
@Composable
fun SectionTitle(
    title: String,
    showDivider: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // ... divider
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(
                    start = DashboardSpacing.smallSpacing,
                    top = if (showDivider) 0.dp else DashboardSpacing.indicatorGap
                )
                .semantics { heading() }  // ✅ Mark as subsection heading (H3 equivalent)
        )
    }
}
```

**Benefits:**
- ✅ Users can jump between "Today", "Parking availability", etc.
- ✅ Faster navigation for screen reader users
- ✅ Better content structure understanding

**Impact:**
- **Effort:** Low (30 minutes)
- **Value:** HIGH (major navigation improvement)
- **Users Affected:** All screen reader users

**Compliance:** ❌ Rule R9 violated

---

### 4. Missing Touch Target Size Enforcement (Rule R4)

**Status:** ⚠️ **MEDIUM-HIGH PRIORITY**

**Current State:**
```kotlin
// SmallCell.kt - DataPointBadge
@Composable
private fun DataPointBadge(dataPoint: DataPoint) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        // Circular badge with label
        Text(
            text = dataPoint.label,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp  // ❌ Very small - 10sp badge
            ),
            modifier = Modifier
                .background(dataPoint.color, CircleShape)
                .padding(6.dp)  // ❌ Total size ~24dp (below 48dp minimum)
        )
        
        // Value - also small
        Text(
            text = dataPoint.value.toString(),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
```

**The Problem:**
- Badge is only ~24dp in size (minimum is 48dp)
- If these become clickable in the future, they'll be hard to tap
- Users with motor impairments will struggle
- Violates Material Design accessibility guidelines

**Note:** Currently NOT clickable, so **not critical yet**. But should be prepared for future interactivity.

**Recommended Fix (if clickable):**
```kotlin
@Composable
private fun DataPointBadge(
    dataPoint: DataPoint,
    onClick: (() -> Unit)? = null  // Optional click handler
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .then(
                if (onClick != null) {
                    Modifier
                        .clickable(onClick = onClick)
                        .sizeIn(minWidth = 48.dp, minHeight = 48.dp)  // ✅ Enforce minimum
                } else {
                    Modifier
                }
            )
    ) {
        // ... existing badge content
    }
}
```

**Impact:**
- **Effort:** Low (only if making interactive)
- **Value:** Medium (future-proofing)
- **Priority:** Low (not currently interactive)

**Compliance:** ℹ️ Rule R4 N/A (not interactive), but should be considered

---

### 5. Missing Traversal Order Customization (Rule R16, R17)

**Status:** ⚠️ **MEDIUM PRIORITY**

**Current State:**
```kotlin
// EventCell.kt - Complex horizontal layout
@Composable
fun EventCell(/*...*/) {
    Row(
        modifier = modifier.fillMaxWidth(),  // ❌ No traversal grouping
        verticalAlignment = Alignment.Top
    ) {
        Box(/*...*/) { /* Icon */ }
        Column(/*...*/) { /* Time */ }
        Column(/*...*/) { /* Title + Subtitle */ }
    }
}
```

**The Problem:**
- Default traversal order may not match visual layout
- In horizontal layouts, screen readers might read vertically first
- For EventCell: might read all icons first, then all times, then all titles
- Confusing for users who expect left-to-right reading

**Recommended Fix:**
```kotlin
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {  // ✅ Already fixed with merging
                // When merged, traversal order is automatic
                contentDescription = buildString {
                    when (time) {
                        is EventTime.Single -> append("${time.time}, ")
                        is EventTime.Range -> append("${time.startTime} to ${time.endTime}, ")
                    }
                    append(title)
                    subtitle?.let { append(", $it") }
                }
            },
        verticalAlignment = Alignment.Top
    ) {
        // ... content
    }
}
```

**Note:** Semantic merging (Fix #1) already solves this issue. No additional work needed if merging is implemented.

**Impact:**
- **Effort:** N/A (solved by semantic merging)
- **Value:** Medium
- **Users Affected:** Screen reader users

**Compliance:** ⚠️ Rule R16, R17 will be satisfied by implementing R12

---

### 6. Missing State Descriptions (Rule R11)

**Status:** ⚠️ **MEDIUM PRIORITY**

**Current State:**
```kotlin
// DashboardScreen.kt - Task items have status
@Composable
private fun TaskItemCell(item: DashboardItem.Task) {
    val color = try {
        val androidColor = android.graphics.Color.parseColor(item.iconColor)
        Color(androidColor)
    } catch (_: Exception) {
        MaterialTheme.dashboardColors.taskBadge
    }

    EventCell(
        icon = EventIcon.TaskCircle(color, iconRes = R.drawable.ic_task),
        time = EventTime.Single(item.time),
        title = item.title,
        subtitle = item.subtitle  // ❌ Status shown visually but not announced prominently
    )
}
```

**The Problem:**
- Task status ("Not submitted", "Submitted") is just in subtitle
- Not announced as STATE information
- Screen readers don't emphasize the status
- Users might miss important deadlines

**Recommended Fix:**
```kotlin
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    stateDescription: String? = null,  // ✅ Add state parameter
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {
                contentDescription = buildString {
                    when (time) {
                        is EventTime.Single -> append("${time.time}, ")
                        is EventTime.Range -> append("${time.startTime} to ${time.endTime}, ")
                    }
                    append(title)
                    subtitle?.let { append(", $it") }
                }
                stateDescription?.let { this.stateDescription = it }  // ✅ Add state
            },
        // ...
    )
}

// Usage
@Composable
private fun TaskItemCell(item: DashboardItem.Task) {
    val statusDescription = when (item.status) {
        TaskStatus.PENDING -> "Not submitted"
        TaskStatus.SUBMITTED -> "Submitted"
        TaskStatus.OVERDUE -> "Overdue"
    }
    
    EventCell(
        // ...
        subtitle = item.subtitle,
        stateDescription = statusDescription  // ✅ Announce as state
    )
}
```

**Impact:**
- **Effort:** Low (1 hour)
- **Value:** Medium (helps users track task status)
- **Users Affected:** Screen reader users managing tasks

**Compliance:** ⚠️ Rule R11 partially violated

---

### 7. Missing Click Action Labels (Rule R6)

**Status:** ℹ️ **LOW PRIORITY (Future)**

**Current State:**
- No interactive elements currently (no buttons, no clickable items)
- Future enhancement: clicking on events to see details

**Recommended for Future:**
```kotlin
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onClick: (() -> Unit)? = null,  // Future: make clickable
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier
                        .clickable(
                            onClickLabel = "View event details",  // ✅ Custom label
                            onClick = onClick
                        )
                        .sizeIn(minHeight = 48.dp)  // ✅ Touch target
                } else {
                    Modifier
                }
            )
            .semantics(mergeDescendants = true) {
                // ...
            },
        // ...
    )
}
```

**Impact:**
- **Effort:** Low (when implementing)
- **Value:** Medium (when interactive)
- **Priority:** Future

**Compliance:** ℹ️ Rule R6 N/A (not currently interactive)

---

### 8. No Custom Accessibility Actions (Rule R15)

**Status:** ℹ️ **LOW PRIORITY (Future)**

**Future Enhancement:**
- Swipe to dismiss tasks
- Long-press for quick actions
- Multiple actions per event

**Example for Future:**
```kotlin
@Composable
fun EventCell(
    // ...
    onMarkComplete: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {
                contentDescription = "..." // Main announcement
                
                // ✅ Add custom actions for accessibility menu
                customActions = buildList {
                    onMarkComplete?.let {
                        add(CustomAccessibilityAction("Mark as complete") { 
                            it()
                            true
                        })
                    }
                    onDelete?.let {
                        add(CustomAccessibilityAction("Delete event") {
                            it()
                            true
                        })
                    }
                }
            },
        // ...
    )
}
```

**Impact:**
- **Effort:** Low (when implementing)
- **Value:** High (when features added)
- **Priority:** Future

**Compliance:** ℹ️ Rule R15 N/A (no complex gestures yet)

---

## III. Summary & Priority Action Plan

### Immediate Actions (Critical - This Sprint)

| Priority | Issue | Files Affected | Effort | Impact | Users Affected |
|----------|-------|----------------|--------|--------|----------------|
| 🔴 CRITICAL | Add semantic merging to EventCell | `EventCell.kt` | 1 hour | CRITICAL | All screen reader users |
| 🔴 CRITICAL | Add semantic merging to SmallCell | `SmallCell.kt` | 30 min | CRITICAL | All screen reader users |
| 🔴 CRITICAL | Add heading markers | `DashboardScreen.kt`, `CardTile.kt`, `SectionTitle.kt` | 30 min | HIGH | All screen reader users |
| 🔴 HIGH | Add icon descriptions | `EventCell.kt`, `DashboardScreen.kt` | 1 hour | HIGH | Blind users |

**Total Effort:** ~3 hours  
**Total Value:** Makes app actually usable with screen readers

---

### Next Sprint (High Priority)

| Priority | Issue | Files Affected | Effort | Impact | Users Affected |
|----------|-------|----------------|--------|--------|----------------|
| 🟡 HIGH | Add state descriptions for tasks | `EventCell.kt`, `DashboardScreen.kt` | 1 hour | MEDIUM | Screen reader users tracking tasks |
| 🟡 MEDIUM | Hide decorative elements | `EventCell.kt`, `DashboardScreen.kt` | 30 min | MEDIUM | Reduces noise for screen readers |
| 🟡 MEDIUM | Test with TalkBack | All screens | 2 hours | HIGH | Validation |

**Total Effort:** ~3.5 hours

---

### Future Enhancements (When Adding Interactivity)

| Priority | Issue | Files Affected | Effort | Impact |
|----------|-------|----------------|--------|--------|
| 🟢 LOW | Enforce touch target sizes | All interactive components | 1 hour | MEDIUM |
| 🟢 LOW | Add click action labels | All clickable components | 1 hour | MEDIUM |
| 🟢 LOW | Add custom accessibility actions | EventCell, SmallCell | 2 hours | HIGH |

---

## IV. Code Implementation Examples

### Example 1: EventCell with Full Accessibility

**Before:**
```kotlin
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),  // ❌ No accessibility
        verticalAlignment = Alignment.Top
    ) {
        Box(/*...*/) { /* Icon */ }  // ❌ Not hidden
        Column(/*...*/) { /* Time */ }  // ❌ Separate announcement
        Column(/*...*/) { /* Title */ }  // ❌ Separate announcement
    }
}
```

**After:**
```kotlin
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconDescription: String? = null,
    stateDescription: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {  // ✅ Merge all children
                contentDescription = buildString {
                    // Include icon meaning
                    iconDescription?.let { append("$it, ") }
                    
                    // Include time
                    when (time) {
                        is EventTime.Single -> append("${time.time}, ")
                        is EventTime.Range -> append("${time.startTime} to ${time.endTime}, ")
                    }
                    
                    // Include title
                    append(title)
                    
                    // Include subtitle/location
                    subtitle?.let { append(", $it") }
                }
                
                // Add state if provided
                stateDescription?.let { this.stateDescription = it }
            },
        verticalAlignment = Alignment.Top
    ) {
        // Icon section - hide from accessibility since it's decorative
        Box(
            modifier = Modifier
                .width(DashboardSpacing.indicatorCircle)
                .semantics { hideFromAccessibility() },  // ✅ Hide decorative
            contentAlignment = Alignment.Center
        ) {
            when (icon) {
                is EventIcon.DurationLine -> {
                    Spacer(
                        modifier = Modifier
                            .width(DashboardSpacing.indicatorWidth)
                            .height(DashboardSpacing.indicatorHeight)
                            .clip(CircleShape)
                            .background(icon.color)
                    )
                }
                is EventIcon.TaskCircle -> {
                    Box(
                        modifier = Modifier.size(DashboardSpacing.indicatorCircle),
                        contentAlignment = Alignment.Center
                    ) {
                        Spacer(
                            modifier = Modifier
                                .size(DashboardSpacing.indicatorCircle)
                                .clip(CircleShape)
                                .background(icon.color)
                        )
                        
                        icon.iconRes?.let { iconRes ->
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = null,  // ✅ Parent handles description
                                modifier = Modifier.size(12.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.width(DashboardSpacing.indicatorGap))
        
        // Time section
        Column(modifier = Modifier.width(52.dp)) {
            when (time) {
                is EventTime.Single -> {
                    Text(
                        text = time.time,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                is EventTime.Range -> {
                    Text(
                        text = time.startTime,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "-${time.endTime}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        // Content section
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = subtitleColor,
                    modifier = Modifier.padding(top = DashboardSpacing.tinySpacing)
                )
            }
        }
    }
}
```

**Usage:**
```kotlin
// DashboardScreen.kt
@Composable
private fun SessionItemCell(item: DashboardItem.Session) {
    val color = parseColorSafe(item.iconColor) 
        ?: MaterialTheme.dashboardColors.sessionClassIndicator
    
    EventCell(
        icon = EventIcon.DurationLine(color),
        time = EventTime.Range(item.startTime, item.endTime),
        title = item.title,
        subtitle = item.subtitle,
        iconDescription = stringResource(R.string.cd_class_session)  // ✅ "Class session"
    )
}

@Composable
private fun TaskItemCell(item: DashboardItem.Task) {
    val color = parseColorSafe(item.iconColor)
        ?: MaterialTheme.dashboardColors.taskBadge
    
    val statusDescription = when (item.status) {
        TaskStatus.PENDING -> stringResource(R.string.status_not_submitted)
        TaskStatus.SUBMITTED -> stringResource(R.string.status_submitted)
        TaskStatus.OVERDUE -> stringResource(R.string.status_overdue)
    }
    
    EventCell(
        icon = EventIcon.TaskCircle(color, iconRes = R.drawable.ic_task),
        time = EventTime.Single(item.time),
        title = item.title,
        subtitle = item.subtitle,
        iconDescription = stringResource(R.string.cd_task),  // ✅ "Task"
        stateDescription = statusDescription  // ✅ Task status
    )
}
```

**Screen Reader Announcement:**
- **Before:** "10.30am" [pause] "-1.30pm" [pause] "FIT2001: Tutorial" [pause] "S4, 13 College Walk, Clayton"
- **After:** "Class session, 10.30am to 1.30pm, FIT2001: Tutorial, S4, 13 College Walk, Clayton"

---

### Example 2: SmallCell with Semantic Merging

**Before:**
```kotlin
@Composable
fun SmallCell(
    title: String,
    dataPoints: List<DataPoint>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = DashboardSpacing.smallSpacing),  // ❌ No semantics
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, /*...*/)  // ❌ Announced separately
        
        Row(/*...*/) {
            dataPoints.forEach { dataPoint ->
                DataPointBadge(dataPoint = dataPoint)  // ❌ Each badge announced separately
            }
        }
    }
}
```

**After:**
```kotlin
@Composable
fun SmallCell(
    title: String,
    dataPoints: List<DataPoint>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = DashboardSpacing.smallSpacing)
            .semantics(mergeDescendants = true) {  // ✅ Merge into one announcement
                contentDescription = buildString {
                    append(title)
                    if (dataPoints.isNotEmpty()) {
                        append(", ")
                        dataPoints.forEachIndexed { index, dataPoint ->
                            // Expand badge meaning
                            val colorName = when (dataPoint.label) {
                                "B" -> "Blue"
                                "R" -> "Red"
                                "G" -> "Green"
                                else -> dataPoint.label
                            }
                            append("$colorName ${dataPoint.value}")
                            if (index < dataPoints.lastIndex) append(", ")
                        }
                        append(" spots available")
                    }
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title on left
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // Data points on right - hide decorative badges from individual announcement
        Row(
            horizontalArrangement = Arrangement.spacedBy(DashboardSpacing.indicatorGap),
            modifier = Modifier.semantics { hideFromAccessibility() }  // ✅ Hide since parent describes
        ) {
            dataPoints.forEach { dataPoint ->
                DataPointBadge(dataPoint = dataPoint)
            }
        }
    }
}
```

**Screen Reader Announcement:**
- **Before:** "North multi-level" [pause] "B" [pause] "12" [pause] "R" [pause] "5"
- **After:** "North multi-level, Blue 12, Red 5 spots available"

---

### Example 3: Heading Markers

**Before:**
```kotlin
// DashboardScreen.kt
item {
    Text(
        text = state.greeting,  // ❌ No heading marker
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onBackground
    )
}

// CardTile.kt
@Composable
fun CardTile(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,  // ❌ No heading marker
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
        modifier = modifier.fillMaxWidth()
    )
}
```

**After:**
```kotlin
// DashboardScreen.kt
item {
    Text(
        text = state.greeting,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.semantics { heading() }  // ✅ H1 - Main screen title
    )
}

// CardTile.kt
@Composable
fun CardTile(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
        modifier = modifier
            .fillMaxWidth()
            .semantics { heading() }  // ✅ H2 - Date section heading
    )
}

// SectionTitle.kt
@Composable
fun SectionTitle(
    title: String,
    showDivider: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (showDivider) {
            Spacer(
                modifier = Modifier
                    .height(DashboardSpacing.dividerThickness)
                    .fillMaxWidth()
                    .background(MaterialTheme.dashboardColors.divider)
                    .semantics { hideFromAccessibility() }  // ✅ Hide decorative divider
            )
            Spacer(modifier = Modifier.height(DashboardSpacing.indicatorGap))
        }
        
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(
                    start = DashboardSpacing.smallSpacing,
                    top = if (showDivider) 0.dp else DashboardSpacing.indicatorGap
                )
                .semantics { heading() }  // ✅ H3 - Subsection heading
        )
    }
}
```

**Benefits:**
- ✅ Screen reader users can use "Next Heading" gesture
- ✅ Quick navigation: "Hey, Kier" → "Today" → "Parking availability"
- ✅ Better content hierarchy understanding

---

## V. Testing Strategy

### Manual Testing with TalkBack

1. **Enable TalkBack:**
   ```
   Settings → Accessibility → TalkBack → Turn On
   ```

2. **Test Navigation:**
   - Swipe right through all elements
   - Verify announcements are clear and concise
   - Use "Next Heading" gesture (swipe down then right)
   - Verify headings are announced

3. **Test Content:**
   - Listen to EventCell announcements
   - Verify SmallCell parking info is clear
   - Check section transitions make sense

4. **Test Font Scaling:**
   ```
   Settings → Display → Font Size → Largest
   ```
   - Verify no text clipping
   - Verify layouts adapt properly

---

### Automated Testing

```kotlin
// ComponentTests.kt - Add semantic tests
@Test
fun eventCell_hasCorrectSemantics() {
    composeTestRule.setContent {
        MonashTheme {
            EventCell(
                icon = EventIcon.DurationLine(Color.Blue),
                time = EventTime.Range("10:00", "11:00"),
                title = "Test Event",
                subtitle = "Test Location",
                iconDescription = "Class session"
            )
        }
    }
    
    // Verify merged semantics
    composeTestRule
        .onNode(hasContentDescription("Class session, 10:00 to 11:00, Test Event, Test Location"))
        .assertExists()
}

@Test
fun cardTile_isMarkedAsHeading() {
    composeTestRule.setContent {
        MonashTheme {
            CardTile(title = "Today")
        }
    }
    
    // Verify heading semantics
    composeTestRule
        .onNode(hasText("Today") and SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading))
        .assertExists()
}

@Test
fun smallCell_hasDescriptiveSemantics() {
    composeTestRule.setContent {
        MonashTheme {
            SmallCell(
                title = "North Parking",
                dataPoints = listOf(
                    DataPoint("B", 12, Color.Blue),
                    DataPoint("R", 5, Color.Red)
                )
            )
        }
    }
    
    // Verify descriptive announcement
    composeTestRule
        .onNode(hasContentDescription("North Parking, Blue 12, Red 5 spots available"))
        .assertExists()
}
```

---

## VI. Required String Resources

Add to `strings.xml`:

```xml
<!-- Accessibility Content Descriptions -->
<string name="cd_class_session">Class session</string>
<string name="cd_task">Task</string>
<string name="cd_assignment">Assignment</string>

<!-- Task Status Descriptions -->
<string name="status_not_submitted">Not submitted</string>
<string name="status_submitted">Submitted</string>
<string name="status_overdue">Overdue</string>

<!-- Parking Badge Labels -->
<string name="parking_blue_badge">Blue permit</string>
<string name="parking_red_badge">Red permit</string>
<string name="parking_green_badge">Green permit</string>
<string name="parking_spots_available">spots available</string>

<!-- Accessibility Labels -->
<string name="label_dashboard">Dashboard</string>
<string name="label_upcoming_events">Upcoming events</string>
<string name="label_parking_availability">Parking availability</string>
```

---

## VII. Compliance Summary

| Rule | Description | Status | Priority | Effort |
|------|-------------|--------|----------|--------|
| R1 | Foundation/Material APIs | ✅ **GOOD** | - | - |
| R2 | Understand Semantics Tree | ⚠️ Needs learning | Low | - |
| R3 | Inspect Semantics Trees | ❌ Not done | Low | 30 min |
| R4 | Touch Target Size (48dp) | ℹ️ N/A (not interactive) | Future | - |
| R5 | Centralize Clickable | ℹ️ N/A (not interactive) | Future | - |
| R6 | Custom Click Labels | ℹ️ N/A (not interactive) | Future | - |
| R7 | Icon Descriptions | ❌ **CRITICAL** | 🔴 High | 1 hour |
| R8 | Null for Decorative | ❌ **CRITICAL** | 🔴 High | 30 min |
| R9 | Heading Markers | ❌ **CRITICAL** | 🔴 High | 30 min |
| R10 | Live Regions | ℹ️ N/A (no dynamic updates) | Future | - |
| R11 | State Descriptions | ⚠️ Partial | 🟡 Medium | 1 hour |
| R12 | Semantic Merging | ❌ **CRITICAL** | 🔴 High | 2 hours |
| R13 | Hide Decorative | ⚠️ Needed | 🟡 Medium | 30 min |
| R14 | clearAndSetSemantics | ℹ️ N/A (using merging) | Low | - |
| R15 | Custom Actions | ℹ️ N/A (no gestures) | Future | - |
| R16 | Traversal Groups | ⚠️ Solved by R12 | - | - |
| R17 | Traversal Index | ⚠️ Solved by R12 | - | - |
| R18 | Pinch-to-Zoom | ℹ️ N/A (standard UI) | Low | - |
| R19 | Scaling Strategy | ⚠️ Using font scale | - | - |
| R20 | Test All Scales | ✅ **GOOD** | - | - |
| R21 | UI Check Mode | ❌ Not used | Low | Testing |
| R22 | Preview Font Scales | ✅ **EXCELLENT** | - | - |

---

## VIII. Next Steps

1. **This Sprint (6 hours total):**
   - Implement semantic merging for EventCell (1 hour)
   - Implement semantic merging for SmallCell (30 min)
   - Add heading markers to all titles (30 min)
   - Add icon descriptions (1 hour)
   - Hide decorative elements (30 min)
   - Test with TalkBack (2 hours)

2. **Next Sprint (3 hours):**
   - Add state descriptions for tasks (1 hour)
   - Add string resources (30 min)
   - Write automated semantic tests (1.5 hours)

3. **Future (when adding interactivity):**
   - Touch target enforcement
   - Click action labels
   - Custom accessibility actions

---

## IX. References

- [Accessibility Best Practices Instructions](.github/instructions/ACCESSIBILITY_BEST_PRACTICES.instructions.md)
- [Compose Accessibility Official Docs](https://developer.android.com/jetpack/compose/accessibility)
- [Material Design Accessibility](https://m3.material.io/foundations/accessible-design/overview)
- [TalkBack Testing Guide](https://support.google.com/accessibility/android/answer/6283677)

---

**Document Version:** 1.0  
**Last Updated:** December 10, 2025  
**Next Review:** After implementing critical fixes and TalkBack testing

