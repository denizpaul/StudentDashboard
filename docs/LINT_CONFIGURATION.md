# Lint Configuration Guide

## Overview

This project is configured with Android Lint to automatically check for code quality, performance, security, and accessibility issues.

---

## 📋 Quick Start

### Run Lint Checks

```bash
# Check debug variant
./gradlew lintDebug

# Check release variant
./gradlew lintRelease

# Check all variants
./gradlew lint
```

### View Reports

After running lint, reports are generated at:
- **HTML Report**: `app/build/reports/lint/lint-report.html`
- **XML Report**: `app/build/reports/lint/lint-report.xml`
- **SARIF Report**: `app/build/reports/lint/lint-report.sarif` (for CI/CD)

---

## 🎯 Gradle Tasks

### Available Lint Tasks

```bash
# List all lint tasks
./gradlew tasks --group=verification

# Main lint tasks:
./gradlew lintDebug              # Lint check for debug build
./gradlew lintRelease            # Lint check for release build
./gradlew lint                   # Lint check for all variants
./gradlew lintFix                # Auto-fix some lint issues
./gradlew lintReport             # Generate reports only
```

### Clean Lint Outputs

```bash
./gradlew cleanLintDebug
./gradlew cleanLintRelease
```

---

## ⚙️ Configuration

### Lint Configuration Files

1. **`app/build.gradle.kts`** - Main lint configuration
   - Report formats (HTML, XML, SARIF, Text)
   - Severity settings
   - Output locations
   - Build behavior

2. **`app/lint.xml`** - Custom lint rules
   - Issue severity overrides
   - Specific checks to enable/disable
   - Path-based ignores
   - Category-specific rules

3. **`app/lint-baseline.xml`** - Baseline file (auto-generated)
   - Suppresses existing issues
   - Allows incremental improvement
   - Update with: `./gradlew lintDebug -PupdateLintBaseline`

---

## 🔧 Current Configuration

### Build Behavior

```kotlin
lint {
    abortOnError = false          // Don't fail build (for now)
    warningsAsErrors = false      // Warnings won't fail build
    checkReleaseBuilds = true     // Check release builds
    checkAllWarnings = true       // Enable all warning checks
}
```

### Report Formats

- ✅ **HTML** - Human-readable, viewable in browser
- ✅ **XML** - Machine-readable, parseable
- ✅ **SARIF** - GitHub/GitLab integration
- ✅ **Text** - Console output

### Disabled Checks

Currently disabled (can be re-enabled):
- `ObsoleteLintCustomCheck` - Custom lint check compatibility
- `GradleDependency` - Dependency version warnings

---

## 📊 Lint Categories

### 1. Correctness ❗
Issues that likely cause bugs:
- `MissingPermission` - Required permissions not declared
- `InvalidPackage` - Invalid package structure
- Compose-specific errors

**Severity**: Error

### 2. Security 🔒
Security vulnerabilities:
- `HardcodedDebugMode` - Debug mode enabled in production
- `SetJavaScriptEnabled` - JavaScript enabled without safety
- `ExportedReceiver` - Unsafe exported components

**Severity**: Error/Warning

### 3. Performance ⚡
Performance issues:
- `DrawAllocation` - Allocations in draw/layout
- `RecyclerView` - RecyclerView issues
- `Overdraw` - Rendering overdraw

**Severity**: Warning

### 4. Accessibility ♿
Accessibility problems:
- `ContentDescription` - Missing content descriptions
- `LabelFor` - Missing label associations
- `ClickableViewAccessibility` - Touch target issues

**Severity**: Warning

### 5. Internationalization 🌍
i18n issues:
- `HardcodedText` - Hardcoded strings (should use strings.xml)
- `MissingTranslation` - Missing translations

**Severity**: Error

### 6. Compose-Specific 🎨
Jetpack Compose issues:
- `ComposeViewModelInjection` - Incorrect ViewModel injection
- `ComposeModifierMissing` - Missing modifiers
- `ComposeContentEmitterReturningValues` - Incorrect @Composable

**Severity**: Warning/Error

---

## 🚀 CI/CD Integration

### GitHub Actions

```yaml
- name: Run Lint
  run: ./gradlew lintDebug

- name: Upload Lint Reports
  uses: actions/upload-artifact@v3
  if: always()
  with:
    name: lint-reports
    path: |
      app/build/reports/lint/
      
- name: Annotate PR with Lint Results
  uses: github/super-linter@v4
  with:
    sarif_file: app/build/reports/lint/lint-report.sarif
```

### GitLab CI

```yaml
lint:
  stage: test
  script:
    - ./gradlew lintDebug
  artifacts:
    when: always
    reports:
      codequality: app/build/reports/lint/lint-report.sarif
    paths:
      - app/build/reports/lint/
```

---

## 🎯 Best Practices

### 1. Run Lint Before Committing

```bash
# Add to pre-commit hook
./gradlew lintDebug
```

### 2. Fix Issues Incrementally

Use baseline to suppress existing issues:
```bash
# Create/update baseline
./gradlew lintDebug -PupdateLintBaseline

# This creates app/lint-baseline.xml
# Commit this file to track progress
```

### 3. Don't Ignore Real Issues

Avoid broad `@SuppressLint` annotations:
```kotlin
// ❌ Bad - Suppresses everything
@SuppressLint("all")
fun myFunction() { }

// ✅ Good - Suppresses specific issue with reason
@SuppressLint("HardcodedText") // TODO: Extract to strings.xml
fun myFunction() { }
```

### 4. Review Reports Regularly

- HTML report shows all issues visually
- Group by severity and fix errors first
- Address warnings incrementally

### 5. Enable abortOnError When Ready

Once major issues are fixed:
```kotlin
lint {
    abortOnError = true  // Fail build on errors
}
```

---

## 🔍 Common Issues and Fixes

### HardcodedText

**Issue**: Strings hardcoded in code/layouts

**Fix**:
```kotlin
// ❌ Bad
Text("Hello World")

// ✅ Good
Text(stringResource(R.string.greeting))
```

### ContentDescription

**Issue**: Missing content descriptions for accessibility

**Fix**:
```kotlin
// ❌ Bad
Icon(Icons.Default.Menu, contentDescription = null)

// ✅ Good
Icon(Icons.Default.Menu, contentDescription = "Open menu")
```

### ComposeModifierMissing

**Issue**: Composable missing modifier parameter

**Fix**:
```kotlin
// ❌ Bad
@Composable
fun MyButton() { }

// ✅ Good
@Composable
fun MyButton(modifier: Modifier = Modifier) { }
```

### UnusedResources

**Issue**: Unused resources in project

**Fix**:
```bash
# Remove unused resources
./gradlew lintFix

# Or manually delete from res/ folders
```

---

## 📈 Baseline Management

### What is a Baseline?

A baseline file suppresses existing lint issues, allowing you to:
- Start enforcing lint on new code
- Fix issues incrementally
- Track progress over time

### Update Baseline

```bash
# Regenerate baseline with current issues
./gradlew lintDebug -PupdateLintBaseline

# This updates app/lint-baseline.xml
```

### When to Update

- After fixing a batch of issues
- When adding new lint checks
- Before major releases

---

## 🛠️ Customization

### Add Custom Lint Checks

Edit `app/lint.xml`:

```xml
<!-- Make a warning an error -->
<issue id="UnusedResources" severity="error" />

<!-- Ignore specific paths -->
<issue id="HardcodedText">
    <ignore path="**/test/**" />
    <ignore path="**/preview/**" />
</issue>

<!-- Disable a check entirely -->
<issue id="SomeCheck" severity="ignore" />
```

### Suppress Inline

```kotlin
// Suppress for function
@SuppressLint("HardcodedText")
fun myFunction() { }

// Suppress for expression
@Suppress("DEPRECATION")
val value = deprecatedApi()
```

---

## 📚 Resources

- [Android Lint Documentation](https://developer.android.com/studio/write/lint)
- [Lint Check Reference](https://googlesamples.github.io/android-custom-lint-rules/checks/index.html)
- [Compose Lint Checks](https://slackhq.github.io/compose-lints/)

---

## ✅ Quick Checklist

Before committing code:

- [ ] Run `./gradlew lintDebug`
- [ ] Check HTML report for new issues
- [ ] Fix all errors
- [ ] Address critical warnings
- [ ] Update baseline if needed
- [ ] Commit changes

---

**Last Updated**: December 7, 2025  
**Lint Version**: Android Gradle Plugin 8.5.0  
**Status**: ✅ Configured and Working

