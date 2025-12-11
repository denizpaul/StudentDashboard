# Lint Quick Reference

## 🚀 Quick Commands

```bash
# Run lint check (debug)
./gradlew lintDebug

# Run lint check (release)
./gradlew lintRelease

# Auto-fix issues
./gradlew lintFix

# Update baseline
./gradlew lintDebug -PupdateLintBaseline
```

## 📊 Reports Location

```
app/build/reports/lint/
├── lint-report.html     ← Open in browser
├── lint-report.xml      ← Machine readable
└── lint-report.sarif    ← CI/CD integration
```

## ⚙️ Configuration Files

```
app/
├── build.gradle.kts     ← Main lint config
├── lint.xml             ← Custom rules
└── lint-baseline.xml    ← Suppressed issues (auto-generated)
```

## 🎯 Common Gradle Tasks

| Task | Description |
|------|-------------|
| `lintDebug` | Check debug variant |
| `lintRelease` | Check release variant |
| `lint` | Check all variants |
| `lintFix` | Auto-fix some issues |
| `lintReport` | Generate reports only |
| `cleanLintDebug` | Clean lint outputs |

## 🔧 Lint Configuration

```kotlin
// app/build.gradle.kts
lint {
    abortOnError = false         // Don't fail build (for now)
    warningsAsErrors = false     // Warnings won't fail
    checkReleaseBuilds = true    // Check releases
    htmlReport = true            // Generate HTML
    xmlReport = true             // Generate XML
    sarifReport = true           // Generate SARIF
}
```

## 📋 Issue Categories

| Category | Severity | Examples |
|----------|----------|----------|
| **Correctness** | ❗ Error | Missing permissions, Compose errors |
| **Security** | 🔒 Error/Warning | Hardcoded debug, Unsafe exports |
| **Performance** | ⚡ Warning | Draw allocations, Overdraw |
| **Accessibility** | ♿ Warning | Missing content descriptions |
| **i18n** | 🌍 Error | Hardcoded text, Missing translations |
| **Compose** | 🎨 Warning/Error | Missing modifiers, Wrong injection |

## 🚫 Suppress Issues

```kotlin
// Function level
@SuppressLint("HardcodedText")
fun myFunction() { }

// File level
@file:Suppress("UnusedImport")

// Inline
@Suppress("DEPRECATION")
val value = deprecatedApi()
```

## ✅ Pre-Commit Checklist

```bash
# 1. Run lint
./gradlew lintDebug

# 2. Open report
open app/build/reports/lint/lint-report.html

# 3. Fix errors
# ... make fixes ...

# 4. Run again to verify
./gradlew lintDebug

# 5. Commit if clean
git commit -m "Fix: lint issues"
```

## 📈 Baseline Workflow

```bash
# Initial setup - create baseline
./gradlew lintDebug -PupdateLintBaseline

# Commit baseline
git add app/lint-baseline.xml
git commit -m "Add lint baseline"

# After fixing issues - update baseline
./gradlew lintDebug -PupdateLintBaseline
git add app/lint-baseline.xml
git commit -m "Update lint baseline"
```

## 🎨 Common Compose Issues

### Missing Modifier
```kotlin
// ❌ Bad
@Composable
fun MyComponent() { }

// ✅ Good
@Composable
fun MyComponent(modifier: Modifier = Modifier) { }
```

### Hardcoded Text
```kotlin
// ❌ Bad
Text("Hello")

// ✅ Good
Text(stringResource(R.string.hello))
```

### Missing Content Description
```kotlin
// ❌ Bad
Icon(Icons.Default.Menu, contentDescription = null)

// ✅ Good
Icon(Icons.Default.Menu, contentDescription = "Menu")
```

## 🔍 View Reports

```bash
# HTML (best for humans)
open app/build/reports/lint/lint-report.html

# XML (for scripts)
cat app/build/reports/lint/lint-report.xml

# Console output
cat app/stdout
```

## 🎯 CI/CD Integration

### GitHub Actions
```yaml
- run: ./gradlew lintDebug
- uses: github/super-linter@v4
  with:
    sarif_file: app/build/reports/lint/lint-report.sarif
```

### Enable Strict Mode (when ready)
```kotlin
lint {
    abortOnError = true  // Fail build on errors
}
```

---

**Quick Help**: `./gradlew tasks --group=verification`

