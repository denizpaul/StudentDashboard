# LLM Development Guidelines for MonashApp

**Version:** 1.0  
**Last Updated:** December 11, 2025  
**Purpose:** Standard operating procedures for AI assistants working on this codebase

---

## 🎯 Core Principles

1. **Follow Established Standards** - Always adhere to project conventions
2. **Minimize Breaking Changes** - Keep modifications focused and minimal
3. **Ask Before Major Refactoring** - Get approval for significant architectural changes
4. **Verify Your Work** - Check compilation, tests, and lint after changes
5. **Document Everything** - Update docs when modifying behavior

---

## 📚 Step 1: Review Best Practices & Instructions

**BEFORE making any code changes, review these files:**

### Required Reading (Always Check First)

1. **`.github/copilot-instructions.md`**
   - Project architecture (MVVM, Clean Architecture)
   - Kotlin style guide
   - Jetpack Compose patterns
   - Design system tokens
   - Testing conventions

2. **`.github/instructions/` folder:**
   - `ACCESSIBILITY_BEST_PRACTICES.instructions.md` - For UI components
   - `COMPOSE_BEST_PRACTICES.instructions.md` - For Compose code
   - `DESIGN_SYSTEM.instructions.md` - For styling and tokens
   - `KOTLIN_CODING_CONVENTIONS.instructions.md` - For Kotlin code style
   - `FIGMA_DEV_MODE.instructions.md` - When working with designs

### Key Questions to Answer

- [ ] What architecture pattern does this project use?
- [ ] Are there design system tokens I should use instead of hardcoded values?
- [ ] What are the accessibility requirements for UI components?
- [ ] What are the naming conventions for this type of code?
- [ ] Are there existing patterns I should follow?

---

## 🔍 Step 2: Analyze the Request

### Categorize the Change

**Minor Changes** (Proceed with implementation):
- Bug fixes (< 10 lines)
- Documentation updates
- Adding missing imports
- Fixing typos or formatting
- Adding KDoc comments
- Small refactoring (renaming variables)

**Medium Changes** (Provide plan, then implement):
- New features (10-50 lines)
- Adding new components
- Modifying existing component behavior
- Adding accessibility improvements
- Performance optimizations

**Major Changes** (MUST get confirmation):
- Architecture changes (50+ lines)
- Breaking API changes
- Database schema changes
- Dependency updates
- Moving files or restructuring packages
- Removing existing functionality
- Changing data models

### Impact Assessment

Ask yourself:
1. **How many files will be modified?**
   - 1-2 files: Minor
   - 3-5 files: Medium
   - 6+ files: Major

2. **Will this break existing code?**
   - No: Proceed
   - Maybe: Provide plan
   - Yes: MUST confirm

3. **Does this change public APIs?**
   - No: Proceed with care
   - Yes: MUST confirm

---

## 📋 Step 3: For Medium/Major Changes - Present Plan

**Format:**
```markdown
## Proposed Changes

### Summary
[Brief description of what and why]

### Files to be Modified
1. `path/to/file1.kt` - [What will change]
2. `path/to/file2.kt` - [What will change]

### Impact Analysis
- **Breaking Changes:** Yes/No
- **Tests Affected:** [List tests that need updating]
- **Dependencies:** [Any new dependencies needed]
- **Migration Required:** Yes/No

### Implementation Steps
1. Step 1
2. Step 2
3. Step 3

### Risks
- Risk 1
- Risk 2

### Rollback Plan
[How to undo if something goes wrong]

**Shall I proceed with this plan?**
```

---

## 💻 Step 4: Implementation Best Practices

### Code Quality Checklist

- [ ] **Use Design System Tokens**
  - ❌ `padding(16.dp)`
  - ✅ `padding(DashboardSpacing.cardPadding)`
  - ❌ `Color(0xFF6750A4)`
  - ✅ `MaterialTheme.colorScheme.primary`

- [ ] **Follow Kotlin Conventions**
  - Use `val` over `var` when possible
  - Use `data class` for models
  - Add explicit visibility modifiers (`public`, `private`)
  - Add KDoc for public APIs

- [ ] **Compose Best Practices**
  - Keep composables stateless
  - Use `semantics` for accessibility
  - Mark headings with `semantics { heading() }`
  - Provide `contentDescription` for images/icons

- [ ] **Accessibility Requirements**
  - Add `semantics(mergeDescendants = true)` for complex components
  - Support large fonts with flexible layouts
  - Provide meaningful content descriptions
  - Ensure 48dp minimum touch targets (when interactive)

- [ ] **Testing Patterns**
  - Follow `should[Expected]When[Condition]` naming
  - Test edge cases
  - Mock external dependencies

### Code Organization

```kotlin
// File structure order:
// 1. Package declaration
// 2. Imports (grouped and sorted)
// 3. KDoc (for public classes)
// 4. Class declaration
// 5. Properties (public, then private)
// 6. Init blocks
// 7. Public methods
// 8. Private methods
// 9. Companion object

/**
 * Brief description of what this does.
 *
 * @param paramName Description
 * @return Description (if applicable)
 */
@Composable
fun MyComponent(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier // Always last with default
) {
    // Implementation
}
```

---

## ✅ Step 5: Verification (Critical!)

### After Implementation, ALWAYS Check:

**Ask user before running these:**
```
Should I run verification checks?
- [ ] Compilation check (./gradlew compileDebugKotlin)
- [ ] Lint check (./gradlew lintDebug)
- [ ] Unit tests (./gradlew testDebugUnitTest)
- [ ] Build APK (./gradlew assembleDebug)
```

### Verification Steps

1. **Compilation Check**
   ```bash
   cd /path/to/project && ./gradlew compileDebugKotlin
   ```
   - ✅ Must pass with no errors
   - ⚠️ Warnings are acceptable but should be noted

2. **Lint Check**
   ```bash
   ./gradlew lintDebug --continue
   ```
   - Review any new lint errors
   - Fix critical issues
   - Document acceptable warnings

3. **Unit Tests**
   ```bash
   ./gradlew testDebugUnitTest
   ```
   - All existing tests must pass
   - Add new tests for new functionality
   - Update tests if behavior changed

4. **Check for Errors**
   ```kotlin
   // Use IDE error checking
   get_errors(filePaths: [modified files])
   ```

### What to Report

```markdown
## Verification Results

### Compilation
- Status: ✅ SUCCESS / ❌ FAILED
- Errors: [List any errors]
- Warnings: [Note important warnings]

### Lint
- Status: ✅ PASS / ⚠️ WARNINGS / ❌ FAILED
- New Issues: [List new lint issues]
- Fixed Issues: [List fixed issues]

### Tests
- Status: ✅ ALL PASS / ❌ FAILURES
- Tests Run: X passed, Y failed
- Failed Tests: [List failures with reasons]

### Build
- Status: ✅ SUCCESS / ❌ FAILED
- APK Generated: Yes/No
- Build Time: X seconds
```

---

## 🚨 Error Handling

### If Compilation Fails

1. **Don't panic** - Review error messages carefully
2. **Check imports** - Missing or incorrect imports?
3. **Verify syntax** - Kotlin syntax correct?
4. **Check types** - Type mismatches?
5. **Ask for help** - Explain error and proposed fix

### If Tests Fail

1. **Understand why** - What behavior changed?
2. **Determine if expected** - Is this a breaking change?
3. **Fix or update** - Either fix code or update tests
4. **Verify fix** - Re-run tests
5. **Document** - Note test changes in PR description

### If Lint Fails

1. **Review violations** - Are they valid concerns?
2. **Fix critical issues** - Security, bugs, crashes
3. **Document suppressions** - Explain why if suppressing
4. **Update baseline** - If intentional (rare)

---

## 📝 Step 6: Documentation

### Always Update

- [ ] **KDoc comments** - For new public APIs
- [ ] **README.md** - If user-facing changes
- [ ] **CHANGELOG** - For significant changes
- [ ] **Architecture docs** - If patterns change
- [ ] **Test documentation** - If test patterns change

### Documentation Template

```kotlin
/**
 * Brief one-line description.
 *
 * Detailed explanation of what this does, when to use it,
 * and any important considerations.
 *
 * Example usage:
 * ```
 * MyComponent(
 *     title = "Hello",
 *     subtitle = "World"
 * )
 * ```
 *
 * @param title The main heading text
 * @param subtitle Optional secondary text
 * @param modifier Modifier to be applied to the component
 */
```

---

## 🎯 Common Scenarios

### Scenario 1: Adding a New Composable Component

**Checklist:**
- [ ] Review `COMPOSE_BEST_PRACTICES.instructions.md`
- [ ] Review `ACCESSIBILITY_BEST_PRACTICES.instructions.md`
- [ ] Review `DESIGN_SYSTEM.instructions.md`
- [ ] Use design tokens (no hardcoded values)
- [ ] Add KDoc comments
- [ ] Make it stateless (parameters only)
- [ ] Add `modifier: Modifier = Modifier` as last parameter
- [ ] Add accessibility semantics
- [ ] Create previews with `@Preview`
- [ ] Test with `fontScale = 1.5f`
- [ ] Verify compilation
- [ ] Add unit tests

### Scenario 2: Modifying Existing Component

**Checklist:**
- [ ] Understand current behavior
- [ ] Check if breaking change
- [ ] If breaking, get confirmation
- [ ] Maintain backward compatibility if possible
- [ ] Update all usages
- [ ] Update tests
- [ ] Update documentation
- [ ] Verify no regressions

### Scenario 3: Fixing Accessibility Issue

**Checklist:**
- [ ] Review `ACCESSIBILITY_BEST_PRACTICES.instructions.md`
- [ ] Identify accessibility rule violated
- [ ] Implement minimal fix
- [ ] Test with TalkBack (manual or note needed)
- [ ] Verify large font support
- [ ] Update accessibility documentation
- [ ] Add test cases if possible

### Scenario 4: Refactoring Code

**Checklist:**
- [ ] Present plan first (MUST for large refactoring)
- [ ] Ensure no behavior changes
- [ ] Maintain API compatibility
- [ ] Update all references
- [ ] Run all tests
- [ ] Check lint
- [ ] Verify no performance regression
- [ ] Document changes

---

## 🔒 Red Flags (Always Confirm First)

**STOP and ask before:**

- ❌ Changing data class structure (breaks serialization)
- ❌ Modifying @Composable function signatures (breaks callers)
- ❌ Removing public functions/classes (breaking change)
- ❌ Changing ViewModel state structure (breaks UI)
- ❌ Modifying repository interfaces (breaks implementations)
- ❌ Updating major dependencies (compatibility risk)
- ❌ Changing database schema (migration needed)
- ❌ Modifying build.gradle significantly
- ❌ Removing or renaming files (breaks imports)
- ❌ Changing package structure (breaks references)

---

## 📊 Change Summary Template

After completing work, provide:

```markdown
## Changes Summary

### Files Modified
- `path/to/file1.kt` - Added semantic merging
- `path/to/file2.kt` - Updated heading markers

### Lines Changed
- Added: X lines
- Removed: Y lines
- Modified: Z files

### Verification
- ✅ Compilation: SUCCESS
- ✅ Lint: PASS
- ✅ Tests: ALL PASS
- ✅ Build: SUCCESS

### Testing Required
- [ ] Manual TalkBack testing
- [ ] Large font testing
- [ ] UI regression testing

### Documentation Updated
- [x] KDoc comments added
- [x] README updated
- [ ] Architecture docs updated

### Breaking Changes
None / [List breaking changes]

### Migration Required
No / [Describe migration steps]
```

---

## 🎓 Learning Resources

### Project-Specific
- `.github/copilot-instructions.md` - Project overview
- `.github/instructions/*.md` - Detailed guidelines
- `docs/` - Architecture and implementation docs

### External
- [Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
- [Compose Guidelines](https://developer.android.com/jetpack/compose/mental-model)
- [Accessibility Guide](https://developer.android.com/guide/topics/ui/accessibility)
- [Material Design 3](https://m3.material.io/)

---

## 💡 Pro Tips

1. **When in doubt, ask** - Better to confirm than break things
2. **Start small** - Implement incrementally, verify often
3. **Follow patterns** - Look at existing code for examples
4. **Test as you go** - Don't wait until the end
5. **Document reasoning** - Explain why, not just what
6. **Be conservative** - Minimal changes are safer
7. **Think accessibility** - Always consider screen readers
8. **Use design tokens** - Never hardcode values
9. **Write tests** - Future you will thank present you
10. **Keep it simple** - Complex solutions often hide bugs

---

## ✅ Final Checklist Before Submitting

- [ ] All instructions reviewed
- [ ] Plan approved (if major change)
- [ ] Code follows conventions
- [ ] Design tokens used
- [ ] Accessibility considered
- [ ] Compilation successful
- [ ] Lint passed
- [ ] Tests passing
- [ ] Documentation updated
- [ ] Breaking changes documented
- [ ] Verification results provided
- [ ] PR description prepared

---

**Remember:** Quality over speed. A working, accessible, maintainable solution is always better than a rushed one.

---

**Questions?** Refer to:
- Project maintainers: [team@example.com]
- Architecture docs: `/docs/`
- Instructions: `/.github/instructions/`

