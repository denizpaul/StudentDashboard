# Accessibility Testing Quick Guide

**Last Updated:** December 11, 2025  
**Related:** See `ACCESSIBILITY_IMPROVEMENTS_IMPLEMENTED.md` for full implementation details

---

## Quick Testing Checklist

### ✅ Before You Start
- [ ] Build the app successfully
- [ ] Install on a physical Android device (recommended) or emulator with API 24+
- [ ] Familiarize yourself with TalkBack gestures

---

## Test 1: Enable TalkBack

**Steps:**
1. Open **Settings** → **Accessibility** → **TalkBack**
2. Toggle **On**
3. Tutorial will appear - complete it or skip
4. Open the MonashApp

**Expected:** TalkBack announces when you touch elements

---

## Test 2: Screen Reader Announcements

### Test EventCell (Class Session)

**Action:** Touch a class session item (e.g., "FIT2099: Studio Workshop")

**Expected Announcement:**
```
"Class session, 9:00 AM to 10:30 AM, FIT2099: Studio Workshop, Building inclusive data-driven apps"
```

**What to verify:**
- ✅ Announces as ONE item (not 4-5 separate elements)
- ✅ Includes icon type ("Class session")
- ✅ Includes time range ("9:00 AM to 10:30 AM")
- ✅ Includes title and subtitle

### Test EventCell (Task)

**Action:** Touch a task item (e.g., "MTK1000: Weekly quizzes")

**Expected Announcement:**
```
"Task, 5:00 PM, MTK1000: Weekly quizzes, Due today"
```

**What to verify:**
- ✅ Announces as ONE item
- ✅ Includes icon type ("Task")
- ✅ Includes due time
- ✅ Includes status in subtitle

### Test SmallCell (Parking)

**Action:** Touch parking info (e.g., "Clayton • North multi-level")

**Expected Announcement:**
```
"Clayton • North multi-level, Blue 12, Red 5 spots available"
```

**What to verify:**
- ✅ Announces as ONE item (not separate B, 12, R, 5)
- ✅ Expands single letters to full words ("Blue" not "B")
- ✅ Includes context ("spots available")

---

## Test 3: Heading Navigation

**TalkBack Gesture:** Swipe down then right (or use heading navigation controls)

**Action:** Navigate through headings

**Expected Order:**
1. "Hey, Kier" (Main greeting - H1)
2. "Today • Tue, 10 March" (Date header - H2)
3. "Parking availability" (Section header - H3)

**What to verify:**
- ✅ Can jump between headings quickly
- ✅ Skips non-heading elements
- ✅ All major sections are reachable

---

## Test 4: Large Font Scaling

**Steps:**
1. Disable TalkBack (not needed for this test)
2. Open **Settings** → **Display** → **Font size**
3. Set to **Largest**
4. Open MonashApp

### Areas to Check:

#### SmallCell (Parking Info)
**Expected:**
- ✅ Title wraps to multiple lines if needed
- ✅ NO overlap between title and badges
- ✅ Badges stay on the right side
- ✅ All text is readable

#### EventCell
**Expected:**
- ✅ Title and subtitle wrap properly
- ✅ Time column remains readable
- ✅ No text clipping
- ✅ Vertical spacing adjusts automatically

#### Cards
**Expected:**
- ✅ Card headers are readable
- ✅ Content inside cards doesn't overflow
- ✅ Proper spacing maintained

---

## Test 5: Different Font Scales (Preview)

**For Developers:** Check the preview panel in Android Studio

**Previews to verify:**
1. `EventCellAccessibilityPreview` (fontScale = 1.5f)
2. `SmallCellAccessibilityPreview` (fontScale = 1.5f)
3. `DashboardScreenAccessibilityPreview` (fontScale = 1.5f)
4. Custom `@PreviewFontScales` annotations

**What to verify:**
- ✅ No text clipping in previews
- ✅ Layout adapts gracefully
- ✅ All content remains visible

---

## Test 6: Swipe Exploration

**TalkBack Gesture:** Swipe right to go to next element

**Action:** Start from top of screen, swipe right repeatedly

**Expected Flow:**
1. "Hey, Kier" (greeting)
2. "Today • Tue, 10 March" (date header)
3. "Class session, 9:00 AM to 10:30 AM, FIT2099..." (first event)
4. "Class session, 11:00 AM to 12:00 PM, LAW1010..." (second event)
5. "Task, 5:00 PM, MTK1000..." (task)
6. "Parking availability" (section header)
7. "Clayton • North multi-level, Blue 12, Red 5 spots available" (parking)

**What to verify:**
- ✅ Logical reading order
- ✅ Each EventCell is one item (not fragmented)
- ✅ No duplicate announcements
- ✅ Clear distinction between different items

---

## Common Issues & Solutions

### Issue: TalkBack announces multiple fragments for EventCell

**Problem:** "10:30 AM" → "-1:30 PM" → "FIT2099" → "Tutorial"

**Solution:** 
- Verify `semantics(mergeDescendants = true)` is present in EventCell
- Check imports include `androidx.compose.ui.semantics.semantics`
- Rebuild the app

### Issue: Can't navigate by headings

**Problem:** "Next Heading" gesture does nothing

**Solution:**
- Verify `semantics { heading() }` is present on CardTile, SectionTitle, and greeting
- Check imports include `androidx.compose.ui.semantics.heading`
- Make sure gesture is correct: swipe down then right

### Issue: Icon types not announced

**Problem:** Only hears time and title, no "Class session" or "Task"

**Solution:**
- Verify `iconDescription` parameter is passed in DashboardScreen
- Check SessionItemCell passes `iconDescription = "Class session"`
- Check TaskItemCell passes `iconDescription = "Task"`

### Issue: Parking badges announced as "B", "R" instead of "Blue", "Red"

**Problem:** Screen reader reads single letters

**Solution:**
- Verify SmallCell `contentDescription` has the when expression for label expansion
- Check the buildString logic in SmallCell semantics

### Issue: Text overlaps with large fonts

**Problem:** Parking title overlaps with badges

**Solution:**
- Verify SmallCell title Text has `Modifier.weight(1f, fill = false)`
- Check padding is added between title and badges
- Test with largest font size setting

---

## TalkBack Gestures Reference

| Gesture | Action |
|---------|--------|
| **Single tap** | Highlight item (doesn't activate) |
| **Double tap** | Activate highlighted item |
| **Swipe right** | Next item |
| **Swipe left** | Previous item |
| **Swipe down then right** | Next heading |
| **Swipe down then left** | Previous heading |
| **Swipe up then down** | Read from top |
| **Two finger swipe down** | Read all from current position |

---

## Success Criteria

### Minimum Requirements (Must Pass):

- ✅ EventCell announces as single logical unit
- ✅ Heading navigation works (can jump between sections)
- ✅ Icon types are announced clearly
- ✅ Parking info is meaningful (no single letters)
- ✅ No text clipping at largest font size
- ✅ No overlap between elements at largest font size

### Nice to Have (Should Pass):

- ✅ Announcements are concise and clear
- ✅ Reading order is logical and intuitive
- ✅ All interactive elements are reachable
- ✅ Layout adapts smoothly to font scaling

---

## Reporting Issues

If you find accessibility issues:

1. **Document the issue:**
   - What component (EventCell, SmallCell, etc.)
   - Expected announcement vs. actual announcement
   - Steps to reproduce
   - Screenshot if applicable

2. **Check against:**
   - `ACCESSIBILITY_ANALYSIS.md` - Known issues
   - `ACCESSIBILITY_IMPROVEMENTS_IMPLEMENTED.md` - What was fixed

3. **Create issue with:**
   - Title: "Accessibility: [Component] - [Brief description]"
   - Labels: "accessibility", "a11y"
   - Priority based on impact (P0 for blocking, P1 for high, P2 for medium)

---

## Next Testing Phase

After passing these tests, consider:

1. **Automated Testing:**
   - Add semantics tests using `composeTestRule`
   - Verify content descriptions programmatically
   - Test with different states (empty, loading, error)

2. **Real User Testing:**
   - Test with actual screen reader users
   - Observe real usage patterns
   - Gather feedback on announcement clarity

3. **Advanced Features:**
   - Test with voice control (Voice Access)
   - Test with switch control
   - Test with BrailleBack

---

**Remember:** Accessibility is not a checkbox - it's an ongoing commitment to inclusive design!

