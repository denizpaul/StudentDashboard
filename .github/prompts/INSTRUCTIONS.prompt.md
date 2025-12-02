# How to Create Custom Instructions for GitHub Copilot

## Overview

GitHub Copilot allows you to provide custom instructions that guide how it generates code suggestions. These instructions help Copilot understand your project's specific requirements, coding standards, architecture patterns, and design systems.

---

## 📁 Repository Instructions File

### File Location and Naming

Create a file named `.github/copilot-instructions.md` in the root of your repository:

```
your-project/
├── .github/
│   └── copilot-instructions.md
├── app/
├── gradle/
└── README.md
```

### File Format

- **Format**: Markdown (.md)
- **Location**: `.github/copilot-instructions.md`
- **Scope**: Applies to entire repository
- **Visibility**: Private to repository contributors

---

## 🎯 What to Include in Instructions

### 1. Project Context

```markdown
# Project: [Your Project Name]

## Overview
Brief description of what the application does and its purpose.

## Tech Stack
- Language: Kotlin
- UI Framework: Jetpack Compose / XML Layouts
- Architecture: MVVM / MVI / Clean Architecture
- Build System: Gradle with Kotlin DSL
```

### 2. Architecture Guidelines

```markdown
## Architecture Patterns

### Follow these architectural principles:
- [Reference to architecture documentation]
- Package structure requirements
- Dependency injection patterns
- State management approach
- Navigation patterns
```

### 3. Design System References

```markdown
## Design System

### Material Design 3 Compliance
- Reference: `.github/MATERIAL_DESIGN_3_GUIDE.md`
- NEVER use hardcoded colors
- ALWAYS use design tokens from the design system
- Reference theme attributes only

### Figma Integration
- Query Figma Dev Mode MCP before implementing any component
- Map Figma specifications to design system tokens
- Ensure pixel-perfect accuracy
```

### 4. Coding Standards

```markdown
## Coding Standards

### Kotlin Style Guide
- Follow official Kotlin coding conventions
- Use meaningful variable names
- Prefer immutability
- Use data classes for models

### File Organization
- One class per file
- Group related functionality
- Use proper package structure

### Naming Conventions
- ViewModels: `[Feature]ViewModel`
- Repositories: `[Feature]Repository`
- Fragments: `[Feature]Fragment`
```

### 5. Testing Requirements

```markdown
## Testing

### Unit Tests
- Required for all ViewModels
- Required for all Repositories
- Minimum 70% code coverage
- Use JUnit and Mockito

### Test Naming
- Pattern: `should[ExpectedBehavior]When[Condition]`
- Example: `shouldLoadScheduleWhenViewModelInitialized`
```

### 6. Resource Guidelines

```markdown
## Resources

### String Resources
- NEVER hardcode strings in layouts or code
- All user-facing text must be in strings.xml
- Use descriptive resource names

### Dimension Resources
- NEVER hardcode dimensions
- Use design system tokens from dimens.xml
- Reference: `@dimen/spacing_md`

### Color Resources
- NEVER hardcode colors
- Use theme attributes: `?attr/colorPrimary`
- Reference design system color tokens
```

### 7. Accessibility Requirements

```markdown
## Accessibility

### Required for All Components
- Content descriptions for all images and icons
- Minimum touch target size: 48dp
- Support for text scaling (use sp units)
- Proper focus order
- Semantic markup for screen readers
```

### 8. Prohibited Practices

```markdown
## DO NOT

- Use hardcoded colors in layouts
- Use hardcoded dimensions in layouts
- Use hardcoded strings in layouts
- Create arbitrary spacing values
- Use deprecated APIs
- Ignore lint warnings
- Use `!!` (double bang operator) without null checks
```

---

## 🚀 Creating Instructions Step-by-Step

### Step 1: Create the Directory Structure

1. Open your project in Android Studio or IDE
2. Navigate to project root
3. Create `.github` directory if it doesn't exist
4. Create `copilot-instructions.md` file inside `.github`

### Step 2: Add Your Project-Specific Instructions

1. Copy your architecture guidelines
2. Add design system references
3. Include coding standards
4. Specify testing requirements
5. Add accessibility requirements

### Step 3: Reference External Documentation

If you have separate documentation files:

```markdown
## Reference Documents

### Primary References
- Architecture: `.github/COPILOT_INSTRUCTIONS.md`
- Design System: `.github/MATERIAL_DESIGN_3_GUIDE.md`
- Implementation Guide: `.github/IMPLEMENTATION_GUIDE.md`

### When Generating Code
1. Consult the design system guide for tokens
2. Follow architecture patterns from copilot instructions
3. Query Figma Dev Mode for component specifications
4. Verify against implementation guidelines
```

### Step 4: Add Examples and Patterns

```markdown
## Code Examples

### Correct Pattern
```kotlin
// Good: Using design system tokens
android:padding="@dimen/spacing_md"
android:textAppearance="?attr/textAppearanceTitleMedium"
android:background="?attr/colorSurface"
```

### Incorrect Pattern
```kotlin
// Bad: Hardcoded values
android:padding="16dp"  // DON'T DO THIS
android:textSize="16sp"  // DON'T DO THIS
android:background="#FFFFFF"  // DON'T DO THIS
```
```

### Step 5: Commit and Push

```bash
git add .github/copilot-instructions.md
git commit -m "Add GitHub Copilot custom instructions"
git push origin main
```

---

## 🔧 Using Instructions in Different IDEs

### Android Studio / IntelliJ IDEA

1. **Enable GitHub Copilot**
    - Go to: `File` → `Settings` → `Plugins`
    - Search for "GitHub Copilot"
    - Install and restart

2. **Copilot Reads Instructions Automatically**
    - Once `.github/copilot-instructions.md` exists
    - Copilot will reference it for all suggestions
    - No additional configuration needed

3. **Verify Instructions are Active**
    - Open a Kotlin file
    - Start typing a comment describing what you want
    - Copilot suggestions should follow your guidelines

### VS Code

1. **Install GitHub Copilot Extension**
    - Open Extensions (Ctrl+Shift+X)
    - Search "GitHub Copilot"
    - Install

2. **Instructions Load Automatically**
    - Open workspace with `.github/copilot-instructions.md`
    - Copilot will reference the file

3. **View Copilot Status**
    - Check Copilot icon in bottom status bar
    - Should show as active

---

## 📝 Example: Complete Instructions File

### For Monash University Challenge

```markdown
# Monash University Dashboard - GitHub Copilot Instructions

## Project Overview
Single-activity Android application implementing a student dashboard with schedule, assignments, and parking information display.

## Tech Stack
- Language: Kotlin (100%)
- UI: XML Layouts with ViewBinding
- Architecture: MVVM
- Design: Material Design 3
- Min SDK: 26
- Target SDK: 34

## Architecture Requirements

### Package Structure
```
app/
├── data/
│   ├── model/
│   └── repository/
├── ui/
│   ├── viewmodel/
│   └── components/
└── util/
```

### Follow MVVM Pattern
- ViewModels handle business logic
- Repositories handle data access
- UI observes ViewModel state
- Use LiveData or StateFlow for state management

## Design System Compliance

### Material Design 3 Tokens
Reference: `.github/MATERIAL_DESIGN_3_GUIDE.md`

**CRITICAL RULES:**
- NEVER use hardcoded colors - use `?attr/colorPrimary` format
- NEVER use hardcoded dimensions - use `@dimen/spacing_md` format
- ALWAYS use TextAppearance styles for typography
- ALWAYS reference design system tokens

### Figma Integration
- Query Figma Dev Mode MCP for all component specifications
- Map Figma specs to design system tokens
- Ensure pixel-perfect accuracy

## Code Generation Guidelines

### When Generating Layouts
1. Query Figma Dev Mode for specifications
2. Use design system tokens exclusively
3. Apply Material 3 components
4. Add content descriptions for accessibility

### When Generating ViewModels
1. Extend ViewModel class
2. Use LiveData/StateFlow for state
3. Implement proper lifecycle handling
4. Include unit test structure

### When Generating Repositories
1. Create interface and implementation
2. Use suspend functions for async operations
3. Return sealed classes or Result types
4. Include unit test structure

## Testing Requirements

### Unit Tests
- Every ViewModel must have tests
- Every Repository must have tests
- Minimum 70% code coverage
- Use JUnit 4 and Mockito

### Test Naming Convention
```kotlin
@Test
fun `should load schedule when viewmodel initialized`() {
    // Test implementation
}
```

## Resource Management

### Strings
```xml
<!-- All text must be in strings.xml -->
<string name="greeting_message">Hey, %1$s</string>
<string name="schedule_card_description">Class schedule for %1$s</string>
```

### Dimensions
```xml
<!-- Use design system tokens -->
android:padding="@dimen/spacing_md"
android:layout_margin="@dimen/spacing_lg"
```

### Colors
```xml
<!-- Use theme attributes -->
android:textColor="?attr/colorOnSurface"
android:background="?attr/colorSurface"
```

## Accessibility

### Required for Every Component
- Content descriptions: `android:contentDescription="@string/..."`
- Minimum touch targets: 48dp
- Proper text scaling support
- Semantic structure

## Prohibited Practices

### NEVER
- Hardcode colors: `android:background="#FFFFFF"`
- Hardcode dimensions: `android:padding="16dp"`
- Hardcode strings: `android:text="Hello"`
- Use `!!` without null checks
- Ignore lint warnings
- Use deprecated APIs

### ALWAYS
- Reference design system tokens
- Use string resources
- Add content descriptions
- Follow architecture patterns
- Write unit tests
- Handle null safety properly

## Code Style

### Kotlin Conventions
- Use `val` over `var` when possible
- Prefer immutable data structures
- Use data classes for models
- Use sealed classes for states
- Meaningful variable names

### Formatting
- 4 spaces for indentation
- 100 character line limit
- Organize imports
- Remove unused imports

## Documentation

### Code Comments
- Comment complex logic only
- Reference design system tokens in comments
- Document deviations from patterns
- Include TODO for incomplete items

### Class Headers
```kotlin
/**
 * ViewModel for the Dashboard screen.
 * Manages schedule, assignments, and parking data.
 */
class DashboardViewModel : ViewModel() {
    // Implementation
}
```

## Git Commit Messages

### Format
```
<type>(<scope>): <subject>

<body>
```

### Types
- feat: New feature
- fix: Bug fix
- refactor: Code refactoring
- test: Adding tests
- docs: Documentation
- style: Formatting changes

### Examples
```
feat(dashboard): implement schedule card component

- Add schedule card layout
- Apply design system tokens
- Add accessibility features
```

## AI Code Generation Validation

### When AI Generates Code
1. Verify design system token usage
2. Check architecture pattern compliance
3. Ensure accessibility features present
4. Run tests to verify functionality
5. Review for lint warnings

### Before Accepting Suggestion
- [ ] Uses design system tokens only
- [ ] Follows architecture patterns
- [ ] Includes accessibility features
- [ ] Has unit tests (if applicable)
- [ ] No hardcoded values
- [ ] No lint warnings

## Reference Documents
- Architecture Guide: `.github/COPILOT_INSTRUCTIONS.md`
- Design System: `.github/MATERIAL_DESIGN_3_GUIDE.md`
- Implementation Guide: `.github/IMPLEMENTATION_GUIDE.md`
```

---

## 💡 Tips for Effective Instructions

### Be Specific
- Provide concrete examples
- Show both correct and incorrect patterns
- Include actual code snippets

### Be Consistent
- Use consistent terminology
- Maintain same structure throughout
- Reference the same standards

### Be Comprehensive
- Cover all aspects of development
- Include testing requirements
- Specify accessibility standards

### Be Clear About Priorities
- Mark critical rules with CRITICAL, NEVER, ALWAYS
- Use emphasis for important points
- Create clear hierarchies

### Update Regularly
- Add new patterns as discovered
- Update when architecture changes
- Refine based on code review feedback

---

## 🔍 Verifying Instructions Work

### Test Your Instructions

1. **Create a Test File**
   - Open new Kotlin file
   - Add comment: `// Create a schedule card following design system`
   - Check if Copilot suggestions use design tokens

2. **Verify Pattern Compliance**
   - Request ViewModel generation
   - Check if it follows specified architecture
   - Verify testing structure is included

3. **Check Resource References**
   - Request layout generation
   - Verify no hardcoded values
   - Check design system token usage

### Iterate and Improve

- Review Copilot suggestions against guidelines
- Add missing patterns to instructions
- Clarify ambiguous requirements
- Add more examples where needed

---

## 📚 Additional Resources

### Official Documentation
- [GitHub Copilot Documentation](https://docs.github.com/en/copilot)
- [Custom Instructions Guide](https://docs.github.com/en/copilot/how-tos/configure-custom-instructions)

### Best Practices
- Keep instructions under 2000 lines
- Use Markdown formatting for readability
- Organize with clear headers
- Include table of contents for long files

### Common Pitfalls
- Instructions too vague
- Contradictory requirements
- Missing critical examples
- Not updating as project evolves

---

## ✅ Checklist: Instructions File Setup

- [ ] Created `.github` directory
- [ ] Created `copilot-instructions.md` file
- [ ] Added project overview
- [ ] Specified tech stack
- [ ] Included architecture guidelines
- [ ] Referenced design system
- [ ] Added coding standards
- [ ] Specified testing requirements
- [ ] Included accessibility requirements
- [ ] Listed prohibited practices
- [ ] Added code examples
- [ ] Referenced external documentation
- [ ] Committed and pushed to repository
- [ ] Tested with Copilot suggestions
- [ ] Verified instructions are followed

---

## 🎯 Quick Start Template

### Minimal Instructions File

```markdown
# [Project Name] - Copilot Instructions

## Architecture
- Pattern: [MVVM/MVI/Clean]
- Language: Kotlin

## Design System
- Reference: `.github/MATERIAL_DESIGN_3_GUIDE.md`
- NO hardcoded colors or dimensions
- USE design system tokens only

## Critical Rules
- NEVER hardcode values
- ALWAYS use design tokens
- ALWAYS add accessibility features
- ALWAYS write unit tests

## References
- Architecture: `.github/COPILOT_INSTRUCTIONS.md`
- Design: `.github/MATERIAL_DESIGN_3_GUIDE.md`
```

Save this as `.github/copilot-instructions.md` and expand as needed!

---

**Remember**: Good instructions lead to better code suggestions. Invest time in creating comprehensive, clear instructions for the best results! 🚀