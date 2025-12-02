# Copilot General Instructions (Dummy)

> This is placeholder guidance you can copy into Copilot when ready. Replace with your actual policies later.

## Purpose
- Summarize how you expect Copilot to assist across the project.
- Clarify conventions, coding styles, and tools everyone should follow.

## Quick Expectations
1. Keep pull requests small, testable, and clearly described.
2. Use Kotlin for Android features unless a module explicitly requires Java.
3. Reference `app/src/main/res/values/` for naming conventions (colors, strings, etc.).
4. Favor Jetpack Compose for new UI and only touch legacy XML when necessary.

## Workflow Tips
- Always run `./gradlew lint test` before asking Copilot for fixes.
- Include TODO comments with issue links when deferring work.
- Provide Copilot with code samples plus any related error output for faster context.

## Review Checklist
- Tests cover happy path plus at least one edge case.
- Accessibility: TalkBack labels, color contrast, and tap targets.
- Performance: prefer lazy collections and memoization where appropriate.

## Communication
a. Tell Copilot whether a change is exploratory, production-ready, or a spike.
b. Highlight blockers early so suggestions can target the obstacle.
c. State the desired level of detail (e.g., diff-ready code vs. high-level plan).

---
*Replace this dummy content with your real instructions when finalized.*

