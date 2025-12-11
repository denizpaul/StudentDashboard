# Quick Start Guide: Viewing Compose Previews

## 🚀 Getting Started (30 seconds)

### Step 1: Open a Component File
Navigate to any of these files in Android Studio:
- `DashboardToolbar.kt`
- `TodaySessionCard.kt`
- `UpcomingTasksCard.kt`
- `ParkingAvailabilityList.kt`
- `DashboardScreen.kt`
- `ComponentStickerSheet.kt` ⭐ **Start here for full gallery**

### Step 2: Enable Preview Panel
1. Click **"Split"** or **"Design"** mode in the top-right corner of the editor
2. The Preview panel will appear on the right side
3. All `@Preview` functions in the file will render automatically

### Step 3: Browse Previews
- Scroll through the Preview panel to see all variations
- Click on group headers to expand/collapse related previews
- Use the toolbar buttons to zoom, refresh, or interact

---

## 📱 Preview Modes

### 🖼️ Static Preview (Default)
Just open the file and see rendered previews - no interaction needed.

### 🖱️ Interactive Mode
**What it does**: Lets you click buttons, type in fields, scroll - all within the preview!

**How to enable**:
1. Click the **"Interactive Mode"** icon in the Preview toolbar (looks like a cursor/touch icon)
2. Interact with the preview like it's a real device
3. Click again to exit

**Use case**: Test click handlers, animations, and user flows without deploying

### 📱 Run on Device
**What it does**: Deploys a specific preview to your connected device/emulator

**How to run**:
1. Right-click on any `@Preview` function in the code
2. Select **"Run 'PreviewName'"**
3. The preview launches as a standalone Activity on your device

**Use case**: Test on real hardware, check performance, test device-specific features

---

## 🎨 Component Sticker Sheet (Recommended Starting Point)

**File**: `ComponentStickerSheet.kt`

This file contains a **visual documentation library** showing all dashboard components together.

**What you'll see**:
- ✅ All components in one place
- ✅ Multiple state variations
- ✅ Edge cases (empty lists, long text)
- ✅ Light & Dark themes
- ✅ Component galleries organized by type

**Preview groups in this file**:
- `ComponentStickerSheet` - Complete gallery
- `Component Gallery` - Individual component showcases
- `Edge Cases` - Boundary conditions

---

## 📋 Preview Organization

All previews are organized into **groups** for easy navigation:

### By Component:
- **DashboardToolbar** - Greeting variations, themes, text sizes
- **TodaySessionCard** - Session types, content variations
- **TodaySessionCard - Categories** - CLASS vs ASSIGNMENT
- **UpcomingTasksCard** - Task lists, statuses
- **UpcomingTasksCard - States** - Single, multiple, empty
- **ParkingAvailabilityList** - Availability scenarios
- **ParkingAvailabilityList - States** - Full, partial, high availability
- **DashboardScreen** - Complete screens
- **DashboardScreen - States** - Typical, busy, light days
- **DashboardScreen - Screen Sizes** - Phone, tablet, small screens
- **DashboardScreen - Orientations** - Portrait, landscape

---

## 🔍 What to Look For

### ✅ Design System Compliance
Check that components use:
- Design tokens (spacing, colors)
- Typography styles
- Proper theming

### ✅ Accessibility
Look for:
- Large font previews (1.5x scale)
- Proper contrast in dark mode
- Touch target sizes (min 48dp)

### ✅ Responsive Layout
Verify:
- Small screen (320dp) compatibility
- Tablet layout (800dp)
- Landscape orientation

### ✅ Edge Cases
Test:
- Empty data states
- Very long text
- Extreme values (0, 999+)

---

## 🛠️ Toolbar Actions

In the Preview panel toolbar, you'll find:

| Icon | Action | Shortcut |
|------|--------|----------|
| 🔄 | Refresh Previews | - |
| 🔍 | Zoom In/Out | - |
| 📱 | Interactive Mode | - |
| 🎨 | UI Check (Accessibility) | - |
| 🎬 | Animation Inspector | - |
| ⚙️ | Preview Settings | - |

---

## 💡 Pro Tips

### Tip 1: Use Groups to Stay Organized
Click group headers to collapse previews you're not currently working on. This:
- Reduces rendering load
- Makes navigation easier
- Speeds up Android Studio

### Tip 2: Focus Mode
Right-click a preview and select **"Show Only This Preview"** to focus on one variation.

### Tip 3: Copy Preview as Image
Right-click any preview → **"Copy Image"** → Paste into documentation, design reviews, or bug reports.

### Tip 4: Compare Light/Dark
Many components have both light and dark previews next to each other - perfect for verifying theme consistency.

### Tip 5: Use PreviewParameters
Files with `@PreviewParameter` show multiple data variations automatically. Look for `PreviewParameterProvider` classes in each file.

---

## 📦 Preview Files Quick Reference

| File | What You'll See | Preview Count |
|------|----------------|---------------|
| `ComponentStickerSheet.kt` | All components gallery | 7+ |
| `DashboardScreen.kt` | Complete dashboard screen | 10+ |
| `DashboardToolbar.kt` | Toolbar variations | 6+ |
| `TodaySessionCard.kt` | Session cards (CLASS/ASSIGNMENT) | 8+ |
| `UpcomingTasksCard.kt` | Task lists and states | 9+ |
| `ParkingAvailabilityList.kt` | Parking availability | 9+ |

**Total: 49+ preview variations**

---

## 🎯 Common Use Cases

### "I want to see all components at once"
→ Open `ComponentStickerSheet.kt` and view the `ComponentStickerSheet` preview

### "I want to test dark mode"
→ Any component file - look for previews named "Dark" or with `UI_MODE_NIGHT_YES`

### "I want to test on a small phone"
→ Open `DashboardScreen.kt` - look for "Small Screen" preview (320dp width)

### "I want to test accessibility"
→ Any component file - look for "Accessibility (Large Font)" previews

### "I want to see edge cases"
→ Open `ComponentStickerSheet.kt` - look for "Edge Cases" group

### "I want to test a specific component state"
→ Open the component file - look for "States" group

---

## ⚠️ Troubleshooting

### Previews not showing?
1. **Build the project first**: Menu → Build → Make Project
2. **Refresh previews**: Click refresh icon in Preview toolbar
3. **Check for errors**: Look at the bottom of the Preview panel

### Preview shows an error?
- Read the error message in the Preview panel
- Check that all dependencies are imported
- Verify data classes are defined
- Try **Build → Clean Project** then rebuild

### Previews are slow?
- Collapse groups you're not using
- Use "Show Only This Preview" to focus
- Close other Android Studio windows/tabs
- Increase Android Studio memory (Help → Edit Custom VM Options)

### Theme not applied?
- Check that preview is wrapped in `MonashTheme { }`
- Verify Material3 dependencies are in `build.gradle.kts`

---

## 📚 More Resources

- **Full Documentation**: `docs/COMPOSE_PREVIEWS.md`
- **Implementation Summary**: `docs/PREVIEW_IMPLEMENTATION_SUMMARY.md`
- **Best Practices**: `.github/instructions/COMPOSE_PREVIEW.instructions.md`
- **Custom Annotations**: `ui/preview/PreviewAnnotations.kt`

---

## 🎓 Learning Path

1. **Beginner**: Open `ComponentStickerSheet.kt` and browse the gallery
2. **Intermediate**: Open individual component files and explore grouped previews
3. **Advanced**: Create your own previews using `@PreviewParameter` and custom annotations
4. **Expert**: Build component sticker sheets for new features

---

**Happy Previewing! 🎨**

*Last Updated: December 6, 2025*

