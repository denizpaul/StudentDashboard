## Comprehensive Rules and Examples for Kotlin Coding Conventions and Best Practices

These conventions ensure code is scalable, consistent, and easy to maintain, drawing heavily from the official Kotlin Style Guide and Jetpack Compose best practices that rely on these conventions,.

---

### I. Source Code Organization

| Rule | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **O1: Directory Structure** | In pure Kotlin projects, the directory structure should follow the package structure, omitting the common root package. (On JVM projects, use the Java directory structure where each file corresponds to its package statement). | **DO:** Code in `org.example.kotlin.network.socket` should be in the `network/socket` subdirectory. |
| **O2: File Naming (Single Class/Interface)** | If a file contains a single class or interface (of any type), the file name must match the class name, appended with `.kt`. | **DO:** `class DeclarationProcessor` should be in `DeclarationProcessor.kt`,. |
| **O3: File Naming (Multiple/Top-Level)** | If a file contains multiple classes or only top-level declarations, choose a name describing the content using **Upper Camel Case**. Avoid meaningless words like `Util`. | **DO:** `ProcessDeclarations.kt`. |
| **O4: Extension Function Placement** | Place extension functions relevant to *all* clients in the same file as the class being extended. Place context-specific extensions next to the client code. Avoid creating files solely to hold all extensions of one class. | **DO:** `String.capitalize()` extensions in `String.kt`. |
| **O5: Class Layout Order** | Organize class contents logically: **Property declarations/initializer blocks** $\rightarrow$ **Secondary constructors** $\rightarrow$ **Method declarations** $\rightarrow$ **Companion object**. Group related methods together. | **DO:** Put getters/setters near their respective properties, and `init` blocks first. |
| **O6: Overload and Interface Layout** | Always place overloads next to each other. When implementing an interface, keep the implementing members in the same order as the interface members. | **DO:** `fun log(message: String)` followed immediately by `fun log(message: String, level: Int)`. |

### II. Naming Rules

| Rule | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **N1: Packages** | Package names must be **all lowercase** with no underscores. Multi-word names should be concatenated or use camel case (though generally discouraged). | **DO:** `org.example.project` or `org.example.myProject`. |
| **N2: Classes and Objects** | Use **Upper Camel Case**. | **DO:** `open class DeclarationProcessor`, `object EmptyDeclarationProcessor`. |
| **N3: Functions, Properties, Local Variables** | Start with a lowercase letter and use **camel case** (no underscores). | **DO:** `fun processDeclarations()`, `var declarationCount = 1`. |
| **N4: Constants (Screaming Snake Case)** | Use **all uppercase, underscore-separated names** (Screaming Snake Case) for constants (`const val`) and top-level/object `val` properties holding deeply immutable data. | **DO:** `const val MAX_COUNT = 8`. |
| **N5: Mutable/Behavioral Properties** | Use **camel case** for top-level or object properties that hold mutable data or objects with behavior. | **DO:** `val mutableCollection: MutableSet<String> = HashSet()`. |
| **N6: Backing Properties** | Use an underscore (`_`) prefix for private properties that serve as backing fields for a public property. | **DO:** `private val _elementList = mutableListOf<Element>()` for `val elementList: List<Element>`. |
| **N7: Acronyms** | Use uppercase for two-letter acronyms (e.g., `IOStream`). Capitalize only the first letter for longer acronyms (e.g., `XmlFormatter` or `HttpInputStream`). | **DO:** `IOStream`, `XmlFormatter`. |

### III. Formatting and Syntax

| Rule | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **F1: Indentation and Tabs** | Use **four spaces** for indentation. **Do not use tabs**. | |
| **F2: Curly Braces (Java Style)** | Place the opening brace at the end of the line where the construct begins, and the closing brace on a separate line, aligned horizontally with the opening construct. | **DO:** `if (elements != null) { for (element in elements) { // ... } }`. |
| **F3: Horizontal Spacing** | Put spaces around binary operators (`a + b`). Do not use spaces around unary operators (`a++`) or the range operator (`0..i`). Never put a space around `.` or `?.` (`foo.bar()`, `foo?.bar()`). | **DON'T:** `if(a!=b)` (missing space around `!=`). |
| **F4: Spacing and Parentheses/Brackets** | Do not put a space before an opening parenthesis in a method declaration or call, and never put a space after `(` or `[` or before `]` or `)`. | **DO:** `fun foo(x: Int) { ... }`, `foo(1)`. |
| **F5: Colon Spacing (Types)** | Do not put a space before `:` when it separates a declaration and its type. Always put a space after `:`,. | **DO:** `val x: Int = 1`. **DON'T:** `val x :Int = 1`. |
| **F6: Colon Spacing (Supertype/Delegation)** | Put a space before `:` when it separates a type and a supertype, delegates to a constructor, or follows the `object` keyword. | **DO:** `class FooImpl : Foo()`. |
| **F7: Function Signature Wrapping** | If the function signature doesn't fit on a single line, use four spaces for parameter indentation, placing each parameter on a separate line. The closing parenthesis should be on a new line. | **DO:** `fun longMethodName( argument: ArgumentType, argument2: AnotherArgumentType, ): ReturnType { // body }`. |
| **F8: Expression Body Formatting** | If a function uses an expression body that wraps across lines, place the `=` sign on the first line and indent the body by four spaces. | **DO:** `fun f(...) = veryLongFunctionCallWithManyWords(` $\rightarrow$ `andLongParametersToo())`. |
| **F9: Chained Calls** | When wrapping chained calls (long modifier chains), put the `.` character or the `?.` operator on the next line, with a single (four-space) indent. | **DO:** `val anchor = owner?.firstChild!! .siblings(forward = true) .dropWhile { ... }`. |
| **F10: Lambda Formatting** | Use spaces around curly braces and the arrow (`->`) that separates parameters from the body. Pass a single lambda outside parentheses when possible. | **DO:** `list.filter { it > 10 }`. |
| **F11: Trailing Commas** | The use of trailing commas after the last item in a series (at the declaration site) is encouraged for cleaner version-control diffs and easier reordering,. | **DO:** `val colors = listOf("red", "green", "blue", )`. |
| **F12: Modifiers Order** | If multiple modifiers are present, they must be consistently ordered (e.g., `public/private` $\rightarrow$ `expect/actual` $\rightarrow$ `final/open/abstract/sealed/const` $\rightarrow$ `external`, etc.). | **DO:** `@Named("Foo") private val foo: Foo` (Annotation before modifier). |

### IV. Idiomatic Kotlin Best Practices

| Rule | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **I1: Immutability Preference** | Prefer using **immutable data**. Declare local variables and properties as `val` unless they must be modified. Use immutable collection interfaces (`List`, `Set`, `Map`). | **DO:** `val allowedValues = listOf("a", "b", "c")`. |
| **I2: Default Parameters over Overloads** | Prefer declaring functions with **default parameter values** over declaring overloaded functions to reduce boilerplate. | **DO:** `fun foo(a: String = "a") { /*...*/ }`. **DON'T:** Overloading with `fun foo() = foo("a")`. |
| **I3: Lambda Parameter Naming** | In short, non-nested lambdas, use the implicit `it` convention. In nested lambdas, always declare parameters explicitly. | **DO:** `list.filter { it > 10 }`,. |
| **I4: Avoid Redundancy** | Omit optional constructs: semicolons, the `Unit` return type, and curly braces when inserting a simple variable into a string template,. | **DO:** `println("Value is $name")`. **DON'T:** `println("Value is ${name}")`. |
| **I5: Named Arguments** | Use named arguments when a method takes multiple parameters of the same primitive type, or for Boolean parameters, unless the meaning is absolutely clear from context. | **DO:** `drawSquare(x = 10, y = 10, width = 100, height = 100, fill = true)`. |
| **I6: Conditional Expressions** | Prefer using the **expression form** of `try`, `if`, and `when` so they directly return a value. | **DO:** `return if (x) foo() else bar()`. |
| **I7: Functions vs. Properties** | Prefer a property over a function when the underlying calculation does not throw, is cheap to calculate, and returns the same result if the object state hasn't changed. | **DO:** `val isEmpty: Boolean get() = size == 0`,. |
| **I8: Range Loops** | Use the open-ended range operator `..<` for loops. | **DO:** `for (i in 0..<n)`. **DON'T:** `for (i in 0..n - 1)`. |

### V. Documentation (KDoc)

Documentation for components should follow JetBrains' ktdoc guidelines and syntax.

| Rule | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **D1: Basic Structure** | Start with a one-liner paragraph summarizing the component, followed by detailed paragraphs, usage examples (`@sample` tag or inline ktdoc), and `@see` tags. | |
| **D2: Parameter Tags (Inline Preference)** | Generally avoid explicit `@param` and `@return` tags. Instead, incorporate the description of parameters and return values directly into the documentation comment, and link to parameters using brackets (`[paramname]`). | **DO:** `/** * Returns the absolute value of the given [number]. */`. **DON'T:** Using separate `@param number...` and `@return...` tags if a concise description suffices. |
| **D3: Multiline Format** | For longer documentation comments, place the opening `/**` on a separate line and begin each subsequent line with an asterisk. | **DO:** `/** * This is a documentation comment * on multiple lines. */`. |

### VI. Library Development Conventions

For Jetpack Compose framework development (MUST) and external library development (SHOULD/MUST), additional rules ensure API stability,,.

| Rule | Description | Example (DO/DON'T) |
| :--- | :--- | :--- |
| **L1: Explicit Visibility** | Always explicitly specify member visibility (e.g., `public`, `private`) to prevent accidental exposure of public API. | **DO:** `public fun myPublicApi()` |
| **L2: Explicit Types** | Always explicitly specify function return types and property types to ensure type stability when implementation changes. | **DO:** `fun calculateValue(): Float { ... }`. **DON'T:** `fun calculateValue() { ... }` (relying on type inference). |
| **L3: KDoc for Public Members** | Provide KDoc comments for all public members, except for overrides that do not require new documentation. | **DO:** `/** Material Design badge box. ... */ @Composable fun BadgedBox(...)`. |

***

This guide reflects the necessary balance between general Kotlin style principles and specific conventions tailored for the Jetpack Compose and Android ecosystems, such as strict parameter ordering and modifier placement,.