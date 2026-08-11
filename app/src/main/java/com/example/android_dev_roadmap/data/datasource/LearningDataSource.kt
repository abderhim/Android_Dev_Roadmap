package com.example.android_dev_roadmap.data.datasource

import com.example.android_dev_roadmap.domain.model.Difficulty
import com.example.android_dev_roadmap.domain.model.Lesson
import com.example.android_dev_roadmap.domain.model.Question
import com.example.android_dev_roadmap.domain.model.Topic

object LearningDataSource {
    val topics: List<Topic> by lazy { buildTopics() }

    private fun buildTopics(): List<Topic> =
        listOf(
            buildComposeFoundationsTopic(),
            buildAdvancedComposeTopic(),
            buildCoroutinesTopic(),
            buildFlowsTopic(),
            buildCleanArchitectureTopic(),
            buildMvvmTopic(),
            buildDependencyInjectionTopic(),
            buildRoomDatabaseTopic(),
            buildTestingTopic(),
        )

    // ─────────────────────────────────────────────
    // TOPIC 1 — Jetpack Compose Foundations
    // ─────────────────────────────────────────────
    private fun buildComposeFoundationsTopic() =
        Topic(
            id = "compose_foundations",
            title = "Jetpack Compose",
            description = "Master the modern declarative UI toolkit for Android. Build beautiful UIs with less code.",
            emoji = "🎨",
            colorHex = "#1565C0",
            secondaryColorHex = "#42A5F5",
            difficulty = Difficulty.BEGINNER,
            estimatedMinutes = 45,
            lessons =
                listOf(
                    Lesson(
                        id = "compose_1",
                        topicId = "compose_foundations",
                        order = 1,
                        title = "Composable Functions & The Compose Mental Model",
                        summary = "Understand what a Composable is and how Compose differs from the View system.",
                        content =
                            """
## What is Jetpack Compose?

Jetpack Compose is Android's modern, **declarative UI toolkit**. Instead of mutating a View tree imperatively (calling `textView.setText(...)`), you *describe* what the UI should look like for any given state — and Compose handles the rest.

## Composable Functions

A **Composable** is any function annotated with `@Composable`. It can emit UI elements, call other Composables, and read state. The runtime calls your Composables whenever it needs to know what the UI looks like.

```kotlin
@Composable
fun Greeting(name: String) {
    Text(text = "Hello, ${"$"}name!")
}
```

## Recomposition

When state changes, Compose **recomposes** — it re-calls the affected Composables with the new data. Only the parts of the UI that depend on changed state are recomposed (this is called *smart recomposition*).

## Key rules for Composables

1. **Idempotent** — calling the same Composable with the same inputs must produce the same output.
2. **Side-effect free** — don't do work that modifies application state outside of Composable scope (use effect APIs instead).
3. **Fast** — avoid expensive computations inside Composables; they can be called frequently.
                            """.trimIndent(),
                        codeExample =
                            """
@Composable
fun ProfileCard(
    name: String,
    bio: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = bio,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Usage
@Preview
@Composable
fun ProfileCardPreview() {
    MaterialTheme {
        ProfileCard(
            name = "Ada Lovelace",
            bio = "The first programmer."
        )
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "@Composable functions describe UI — they don't return Views",
                                "Recomposition only rebuilds what changed — it's efficient",
                                "Composables must be idempotent and side-effect free",
                                "Always provide a Modifier parameter for flexibility",
                                "Use MaterialTheme for consistent styling",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_c1_1",
                                    lessonId = "compose_1",
                                    text = "What does recomposition mean in Jetpack Compose?",
                                    options =
                                        listOf(
                                            "Rebuilding the entire Activity",
                                            "Re-calling Composable functions when their state changes",
                                            "Restarting the ViewModel",
                                            "Recreating the Fragment",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Recomposition re-calls only the Composables whose inputs have changed, updating the UI efficiently.",
                                ),
                                Question(
                                    id = "q_c1_2",
                                    lessonId = "compose_1",
                                    text = "Which rule must Composable functions follow?",
                                    options =
                                        listOf(
                                            "They must return a View object",
                                            "They must be declared inside an Activity",
                                            "They must be idempotent",
                                            "They must suspend",
                                        ),
                                    correctIndex = 2,
                                    explanation = "Composables must be idempotent — same inputs must always produce the same UI output.",
                                ),
                                Question(
                                    id = "q_c1_3",
                                    lessonId = "compose_1",
                                    text = "What is the best practice when declaring a Composable that others might reuse?",
                                    options =
                                        listOf(
                                            "Hard-code padding inside the function",
                                            "Accept a Modifier parameter with a default of Modifier",
                                            "Always use fillMaxSize()",
                                            "Pass Context as a parameter",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Accepting a Modifier parameter lets callers customize layout behavior without breaking encapsulation.",
                                ),
                                Question(
                                    id = "q_c1_4",
                                    lessonId = "compose_1",
                                    text = "Which annotation is required for a function to emit UI in Compose?",
                                    options = listOf("@UI", "@Layout", "@Composable", "@View"),
                                    correctIndex = 2,
                                    explanation = "The @Composable annotation tells the Kotlin compiler that the function is intended to convert data into UI.",
                                ),
                                Question(
                                    id = "q_c1_5",
                                    lessonId = "compose_1",
                                    text = "What happens to the previous UI state when a Composable is called again with new data?",
                                    options =
                                        listOf(
                                            "It is manually removed from the screen",
                                            "The entire screen is cleared and redrawn",
                                            "Compose compares the new state with the old and updates only the differences",
                                            "The old state is saved in the database",
                                        ),
                                    correctIndex = 2,
                                    explanation = "This is the core of Compose's efficiency: it calculates the 'delta' and only updates what's necessary.",
                                ),
                                Question(
                                    id = "q_c1_6",
                                    lessonId = "compose_1",
                                    text = "Why should Composables be side-effect free?",
                                    options =
                                        listOf(
                                            "Because they can be executed in any order",
                                            "Because they run on the main thread",
                                            "Because they are anonymous functions",
                                            "Because they return Unit",
                                        ),
                                    correctIndex = 0,
                                    explanation = "Compose might execute Composables in parallel or in a different order than they appear. Side effects would lead to unpredictable behavior.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "compose_2",
                        topicId = "compose_foundations",
                        order = 2,
                        title = "Layouts: Column, Row, Box & Lazy Lists",
                        summary = "Build complex layouts with Compose's powerful layout primitives.",
                        content =
                            """
## Layout Composables

Compose provides three core layout containers:

### Column
Arranges children **vertically** (like a vertical LinearLayout).
```kotlin
Column(
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) { /* children */ }
```

### Row
Arranges children **horizontally**.
```kotlin
Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) { /* children */ }
```

### Box
Stacks children **on top of each other** (like FrameLayout).
```kotlin
Box(contentAlignment = Alignment.Center) {
    Image(...)
    // Overlaid badge
    Badge(modifier = Modifier.align(Alignment.TopEnd))
}
```

## Lazy Layouts — Efficient Lists

`LazyColumn` and `LazyRow` only compose visible items, making them very efficient for long lists.

```kotlin
LazyColumn {
    item { Header() }
    items(myList) { item -> ItemRow(item) }
    item { Footer() }
}
```

### LazyGrid
For grid layouts, use `LazyVerticalGrid`:
```kotlin
LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = 150.dp)
) {
    items(photos) { photo -> PhotoCard(photo) }
}
```
                            """.trimIndent(),
                        codeExample =
                            """
@Composable
fun DashboardLayout(items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = {}) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lazy list
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items, key = { it }) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Column = vertical, Row = horizontal, Box = stacked",
                                "Use Arrangement and Alignment to control child positioning",
                                "LazyColumn/LazyRow only compose visible items — use them for lists",
                                "Provide a stable `key` in lazy lists to help Compose track items",
                                "LazyVerticalGrid handles grid layouts with adaptive column sizes",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_c2_1",
                                    lessonId = "compose_2",
                                    text = "Which layout composable stacks children on top of each other?",
                                    options = listOf("Column", "Row", "Box", "LazyColumn"),
                                    correctIndex = 2,
                                    explanation = "Box stacks its children — the last child is drawn on top, similar to FrameLayout.",
                                ),
                                Question(
                                    id = "q_c2_2",
                                    lessonId = "compose_2",
                                    text = "Why should you provide a `key` in LazyColumn items?",
                                    options =
                                        listOf(
                                            "It is required syntax",
                                            "It helps Compose track items across recompositions for smooth animations",
                                            "It sets the item's background color",
                                            "It filters the list",
                                        ),
                                    correctIndex = 1,
                                    explanation = "A stable key lets Compose track item identity, enabling correct animations and avoiding unnecessary recompositions.",
                                ),
                                Question(
                                    id = "q_c2_3",
                                    lessonId = "compose_2",
                                    text = "What is the main advantage of LazyColumn over Column?",
                                    options =
                                        listOf(
                                            "LazyColumn supports horizontal scrolling",
                                            "LazyColumn only composes items currently visible on screen",
                                            "LazyColumn is faster to type",
                                            "LazyColumn supports nested scrolling",
                                        ),
                                    correctIndex = 1,
                                    explanation = "LazyColumn only composes and lays out visible items, making it ideal for long or infinite lists.",
                                ),
                                Question(
                                    id = "q_c2_4",
                                    lessonId = "compose_2",
                                    text = "How do you align a child at the center-end of a Box?",
                                    options =
                                        listOf(
                                            "horizontalAlignment = Alignment.End",
                                            "modifier = Modifier.align(Alignment.CenterEnd)",
                                            "contentAlignment = Alignment.End",
                                            "textAlign = TextAlign.Right",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Inside a Box, you use the 'align' modifier on children to position them relative to the Box's boundaries.",
                                ),
                                Question(
                                    id = "q_c2_5",
                                    lessonId = "compose_2",
                                    text = "Which parameter in a Column controls the vertical spacing between children?",
                                    options =
                                        listOf(
                                            "verticalAlignment",
                                            "horizontalAlignment",
                                            "verticalArrangement",
                                            "padding",
                                        ),
                                    correctIndex = 2,
                                    explanation = "verticalArrangement (e.g., Arrangement.spacedBy(8.dp)) is used to define how children are distributed vertically.",
                                ),
                                Question(
                                    id = "q_c2_6",
                                    lessonId = "compose_2",
                                    text = "What is the equivalent of a RecyclerView in Jetpack Compose?",
                                    options = listOf("ScrollView", "LazyColumn", "ListView", "Column"),
                                    correctIndex = 1,
                                    explanation = "LazyColumn provides the same 'recycling' efficiency as RecyclerView by only composing visible items.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "compose_3",
                        topicId = "compose_foundations",
                        order = 3,
                        title = "State & State Hoisting",
                        summary = "Master how Compose handles state and learn the state hoisting pattern.",
                        content =
                            """
## State in Compose

UI state drives your UI. In Compose, you observe state and the UI updates automatically.

### remember & mutableStateOf

`remember` stores a value across recompositions. Combined with `mutableStateOf`, it creates **observable state**:

```kotlin
var count by remember { mutableStateOf(0) }
```

When `count` changes, any Composable reading it will recompose.

### rememberSaveable

Survives configuration changes (screen rotation):
```kotlin
var text by rememberSaveable { mutableStateOf("") }
```

## State Hoisting

State hoisting means **moving state up** to the lowest common ancestor that needs it. This makes Composables stateless and reusable:

> "Stateless Composables are easier to test, preview, and reuse."

### The pattern:
- Replace `var state by remember { mutableStateOf(...) }` inside the Composable with **parameters**
- Pass a `value` parameter (state) and an `onValueChange` callback (event)
- The *caller* owns the state

```kotlin
// ❌ Stateful (hard to test / reuse)
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) { Text("${"$"}count") }
}

// ✅ Stateless (hoisted)
@Composable
fun Counter(count: Int, onIncrement: () -> Unit) {
    Button(onClick = onIncrement) { Text("${"$"}count") }
}
```
                            """.trimIndent(),
                        codeExample =
                            """
// Stateless text field — fully controlled
@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Search topics...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        }
    )
}

// Screen owns the state
@Composable
fun SearchScreen() {
    var query by rememberSaveable { mutableStateOf("") }
    val results = remember(query) {
        allTopics.filter { it.contains(query, ignoreCase = true) }
    }

    Column {
        SearchField(query = query, onQueryChange = { query = it })
        LazyColumn {
            items(results) { Text(it) }
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "remember stores values across recompositions",
                                "rememberSaveable also survives configuration changes",
                                "State hoisting: move state up, pass value + callback down",
                                "Stateless Composables are easier to test and reuse",
                                "Use remember(key) to recompute expensive values only when the key changes",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_c3_1",
                                    lessonId = "compose_3",
                                    text = "What does `rememberSaveable` do differently from `remember`?",
                                    options =
                                        listOf(
                                            "It saves to a database",
                                            "It survives process death and configuration changes",
                                            "It makes state immutable",
                                            "It shares state across Composables",
                                        ),
                                    correctIndex = 1,
                                    explanation = "rememberSaveable saves state to the Bundle, so it survives configuration changes like screen rotation.",
                                ),
                                Question(
                                    id = "q_c3_2",
                                    lessonId = "compose_3",
                                    text = "What is the state hoisting pattern?",
                                    options =
                                        listOf(
                                            "Moving state into a global singleton",
                                            "Lifting state to the lowest common ancestor and passing it down as parameters",
                                            "Saving state in SharedPreferences",
                                            "Using StateFlow in every Composable",
                                        ),
                                    correctIndex = 1,
                                    explanation = "State hoisting moves state up to the caller, making the Composable stateless and reusable.",
                                ),
                                Question(
                                    id = "q_c3_3",
                                    lessonId = "compose_3",
                                    text = "Which is the CORRECT way to hoist state?",
                                    options =
                                        listOf(
                                            "Pass the MutableState object directly to the child",
                                            "Pass a value parameter and an onValueChange callback",
                                            "Use a global variable",
                                            "Declare state inside the child Composable",
                                        ),
                                    correctIndex = 1,
                                    explanation = "The hoisting pattern uses a value (for display) + event callback (for changes) — the caller owns the mutable state.",
                                ),
                                Question(
                                    id = "q_c3_4",
                                    lessonId = "compose_3",
                                    text = "Why should we prefer stateless Composables?",
                                    options =
                                        listOf(
                                            "They are faster to compile",
                                            "They are easier to test, preview, and reuse",
                                            "They don't use any memory",
                                            "They are required for navigation",
                                        ),
                                    correctIndex = 1,
                                    explanation = "By hoisting state, you can pass any value (even mock data) into a Composable, making it highly flexible.",
                                ),
                                Question(
                                    id = "q_c3_5",
                                    lessonId = "compose_3",
                                    text = "What happens if you use `remember` but not `mutableStateOf`?",
                                    options =
                                        listOf(
                                            "The value will not survive recomposition",
                                            "The value will survive recomposition, but changes to it won't trigger a new recomposition",
                                            "The app will crash",
                                            "Nothing happens",
                                        ),
                                    correctIndex = 1,
                                    explanation = "remember keeps the object instance, but it's the mutableStateOf wrapper that tells Compose to 'observe' and recompose when the value changes.",
                                ),
                                Question(
                                    id = "q_c3_6",
                                    lessonId = "compose_3",
                                    text = "What is 'Unidirectional Data Flow' in the context of Compose state?",
                                    options =
                                        listOf(
                                            "Data moves in a circle",
                                            "State flows down, events flow up",
                                            "Events flow down, state flows up",
                                            "Data only flows from the database to the UI",
                                        ),
                                    correctIndex = 1,
                                    explanation = "UDF means the parent provides the state (down) and the child triggers events (up) to ask for changes.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "compose_4",
                        topicId = "compose_foundations",
                        order = 4,
                        title = "Modifiers — The Power Chain",
                        summary = "Understand how Modifiers work and how to chain them to build exactly the UI you want.",
                        content =
                            """
## What is a Modifier?

A `Modifier` is an **ordered chain** of decoration/behavior instructions applied to a Composable. Think of it as a sequence of transformations applied one by one.

```kotlin
Text(
    text = "Hello",
    modifier = Modifier
        .padding(16.dp)       // 1. Add 16dp space around
        .background(Color.Blue) // 2. Blue background
        .size(200.dp)          // 3. Force 200dp size
        .clip(RoundedCornerShape(8.dp)) // 4. Clip corners
)
```

## Order Matters!

The order of Modifier calls matters — each step wraps the result of the previous:

```kotlin
// Padding OUTSIDE background = padding has no background
Modifier.padding(16.dp).background(Color.Blue)

// Padding INSIDE background = background covers the padding area
Modifier.background(Color.Blue).padding(16.dp)
```

## Common Modifiers

| Modifier | Purpose |
|---|---|
| `.fillMaxSize()` | Take all available space |
| `.fillMaxWidth()` | Fill parent width |
| `.size(dp)` | Set exact size |
| `.padding(dp)` | Inner/outer spacing |
| `.background(color)` | Background color |
| `.clip(shape)` | Clip to a shape |
| `.clickable { }` | Make tappable |
| `.weight(1f)` | Share available space in Row/Column |
| `.align(Alignment)` | Position in Box |

## Custom Modifier Extensions

You can create reusable modifier extensions:

```kotlin
fun Modifier.cardStyle(elevation: Dp = 4.dp) = this
    .shadow(elevation, RoundedCornerShape(12.dp))
    .background(MaterialTheme.colorScheme.surface)
    .padding(16.dp)
```
                            """.trimIndent(),
                        codeExample =
                            """
// Real-world modifier usage
@Composable
fun FeatureCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Modifier is an ordered chain — order of calls matters!",
                                "padding before background ≠ padding after background",
                                ".weight() distributes remaining space proportionally in Row/Column",
                                "Create extension functions for reusable modifier combinations",
                                "Always accept Modifier as a parameter in your Composables",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_c4_1",
                                    lessonId = "compose_4",
                                    text = "Why does the order of Modifier calls matter?",
                                    options =
                                        listOf(
                                            "It doesn't matter, the result is the same",
                                            "Each modifier wraps the result of the previous one",
                                            "Modifiers are applied in reverse order",
                                            "Only the last modifier takes effect",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Modifier calls are applied sequentially — each one wraps the previous, so swapping padding and background gives different visual results.",
                                ),
                                Question(
                                    id = "q_c4_2",
                                    lessonId = "compose_4",
                                    text = "What does `.weight(1f)` do inside a Row?",
                                    options =
                                        listOf(
                                            "Sets the element's font weight",
                                            "Makes the element 1dp wide",
                                            "Fills remaining horizontal space proportionally",
                                            "Makes the Row scroll horizontally",
                                        ),
                                    correctIndex = 2,
                                    explanation = "weight() distributes the remaining space in a Row/Column among weighted children proportionally to their weight values.",
                                ),
                                Question(
                                    id = "q_c4_3",
                                    lessonId = "compose_4",
                                    text = "How do you make a Composable clickable using modifiers?",
                                    options =
                                        listOf(
                                            "Modifier.onClick { }",
                                            "Modifier.clickable { }",
                                            "Modifier.tappable()",
                                            "Modifier.setOnClickListener { }",
                                        ),
                                    correctIndex = 1,
                                    explanation = "The .clickable { } modifier handles touch input, ripple effects, and accessibility in a single call.",
                                ),
                                Question(
                                    id = "q_c4_4",
                                    lessonId = "compose_4",
                                    text = "If you apply `.padding(16.dp).background(Color.Red)`, where is the red color applied?",
                                    options =
                                        listOf(
                                            "Inside the padding area",
                                            "Outside the padding area (the content area only)",
                                            "To the whole screen",
                                            "It depends on the child",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Padding is applied first, then background. The background will only cover the area inside the padding.",
                                ),
                                Question(
                                    id = "q_c4_5",
                                    lessonId = "compose_4",
                                    text = "Which modifier is used to constrain a child to a specific width and height?",
                                    options = listOf(".fill()", ".wrapContent()", ".size()", ".layout()"),
                                    correctIndex = 2,
                                    explanation = ".size(width, height) fixes the dimensions of a Composable.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "compose_5",
                        topicId = "compose_foundations",
                        order = 5,
                        title = "Side Effects: LaunchedEffect, DisposableEffect & more",
                        summary = "Learn when and how to safely perform side effects inside Composable functions.",
                        content =
                            """
## Why Effect APIs?

Composables are pure functions that should be side-effect free. But sometimes you *need* to do things like:
- Start a coroutine when a screen appears
- Subscribe/unsubscribe to a callback
- Show a Snackbar in response to an event

For these cases, Compose provides **Effect APIs**.

## LaunchedEffect

Runs a suspend block when the Composable enters the composition. Re-runs when the `key` changes.

```kotlin
LaunchedEffect(key1 = userId) {
    // This block runs when userId changes
    val user = fetchUser(userId)
    updateState(user)
}
```

Use `LaunchedEffect(Unit)` for a one-time launch.

## DisposableEffect

For effects that need cleanup (like registering listeners):

```kotlin
DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_START) doSomething()
    }
    lifecycleOwner.lifecycle.addObserver(observer)
    onDispose {
        lifecycleOwner.lifecycle.removeObserver(observer)
    }
}
```

## rememberCoroutineScope

Gives you a `CoroutineScope` tied to the Composable's lifecycle, for launching coroutines from event handlers:

```kotlin
val scope = rememberCoroutineScope()
Button(onClick = {
    scope.launch { scaffoldState.snackbarHostState.showSnackbar("Done!") }
}) { Text("Save") }
```

## SideEffect

Runs on every **successful** recomposition. Use for syncing non-Compose state:

```kotlin
SideEffect {
    analytics.setScreen(currentRoute)
}
```

## derivedStateOf

Derives state from other state. Recomputes only when dependencies change:

```kotlin
val isScrolled by remember {
    derivedStateOf { listState.firstVisibleItemIndex > 0 }
}
```
                            """.trimIndent(),
                        codeExample =
                            """
@Composable
fun TimerScreen(durationSeconds: Int) {
    var remaining by remember { mutableIntStateOf(durationSeconds) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // LaunchedEffect: restart countdown when duration changes
    LaunchedEffect(durationSeconds) {
        remaining = durationSeconds
        while (remaining > 0) {
            delay(1000)
            remaining--
        }
    }

    // Show snackbar when timer ends
    LaunchedEffect(remaining) {
        if (remaining == 0) {
            snackbarHostState.showSnackbar("Time's up!")
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${"$"}remaining s",
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(Modifier.height(24.dp))
                Button(onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Paused!")
                    }
                }) { Text("Pause") }
            }
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "LaunchedEffect runs a coroutine tied to composition — restart when key changes",
                                "DisposableEffect is for effects that need cleanup with onDispose { }",
                                "rememberCoroutineScope for launching coroutines from event handlers",
                                "SideEffect runs after every successful recomposition",
                                "derivedStateOf avoids unnecessary recompositions for derived values",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_c5_1",
                                    lessonId = "compose_5",
                                    text = "When does LaunchedEffect re-run its block?",
                                    options =
                                        listOf(
                                            "On every recomposition",
                                            "Only once on first composition",
                                            "When its key parameter changes",
                                            "When the ViewModel changes",
                                        ),
                                    correctIndex = 2,
                                    explanation = "LaunchedEffect re-runs its block when any of its key parameters change, or once on first entry if key is Unit.",
                                ),
                                Question(
                                    id = "q_c5_2",
                                    lessonId = "compose_5",
                                    text = "What is the purpose of `onDispose` inside DisposableEffect?",
                                    options =
                                        listOf(
                                            "To save state before the Composable leaves",
                                            "To clean up resources when the Composable leaves the composition",
                                            "To dispose of the ViewModel",
                                            "To cancel all coroutines",
                                        ),
                                    correctIndex = 1,
                                    explanation = "onDispose runs when the Composable leaves the composition, letting you unregister listeners or cancel subscriptions.",
                                ),
                                Question(
                                    id = "q_c5_3",
                                    lessonId = "compose_5",
                                    text = "Which API should you use to launch a coroutine from a button click handler?",
                                    options =
                                        listOf(
                                            "LaunchedEffect",
                                            "rememberCoroutineScope",
                                            "SideEffect",
                                            "CoroutineScope(Dispatchers.Main)",
                                        ),
                                    correctIndex = 1,
                                    explanation = "rememberCoroutineScope provides a scope tied to the composition's lifecycle, safe for one-off events like clicks.",
                                ),
                                Question(
                                    id = "q_c5_4",
                                    lessonId = "compose_5",
                                    text = "When should you use `derivedStateOf`?",
                                    options =
                                        listOf(
                                            "For any calculation in Compose",
                                            "To transform one state into another, especially when the input changes more often than the result",
                                            "To save data to Room",
                                            "Inside a ViewModel only",
                                        ),
                                    correctIndex = 1,
                                    explanation = "derivedStateOf is perfect for cases like scroll progress, where the offset changes constantly but you only care if it's past a threshold.",
                                ),
                                Question(
                                    id = "q_c5_5",
                                    lessonId = "compose_5",
                                    text = "What is the difference between `LaunchedEffect(Unit)` and `LaunchedEffect(true)`?",
                                    options =
                                        listOf(
                                            "They are identical — both run once and never restart",
                                            "True restarts every recomposition",
                                            "Unit is faster",
                                            "True is for boolean state only",
                                        ),
                                    correctIndex = 0,
                                    explanation = "Both use a constant key, so the effect will only run once when the Composable enters the composition and will not restart.",
                                ),
                            ),
                    ),
                ),
        )

    // ─────────────────────────────────────────────
    // TOPIC 2 — Advanced Compose
    // ─────────────────────────────────────────────
    private fun buildAdvancedComposeTopic() =
        Topic(
            id = "advanced_compose",
            title = "Advanced Compose",
            description = "Animations, Navigation, theming, performance, and custom drawing.",
            emoji = "✨",
            colorHex = "#6A1B9A",
            secondaryColorHex = "#CE93D8",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedMinutes = 50,
            lessons =
                listOf(
                    Lesson(
                        id = "adv_compose_1",
                        topicId = "advanced_compose",
                        order = 1,
                        title = "Animations in Compose",
                        summary = "Bring your UI to life with Compose's animation APIs.",
                        content =
                            """
## Animation APIs Overview

Compose offers multiple animation APIs — from simple one-liners to powerful custom animations.

### animate*AsState — The Simplest API

Animate a single value when it changes:

```kotlin
val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(300)
)
Box(modifier = Modifier.alpha(alpha)) { ... }
```

Available: `animateFloatAsState`, `animateDpAsState`, `animateColorAsState`, `animateSizeAsState`, etc.

### AnimatedVisibility

Show/hide content with animation:

```kotlin
AnimatedVisibility(
    visible = isExpanded,
    enter = fadeIn() + expandVertically(),
    exit = fadeOut() + shrinkVertically()
) {
    ExpandedContent()
}
```

### AnimatedContent

Animate between content changes:

```kotlin
AnimatedContent(targetState = count) { targetCount ->
    Text(text = "${"$"}targetCount")
}
```

### updateTransition — Multiple properties

Animate multiple values simultaneously:

```kotlin
val transition = updateTransition(targetState = selected, label = "card")
val bgColor by transition.animateColor { state ->
    if (state) Color.Blue else Color.Gray
}
val borderWidth by transition.animateDp { state ->
    if (state) 3.dp else 1.dp
}
```

### Infinite Animations

```kotlin
val infiniteTransition = rememberInfiniteTransition()
val rotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing))
)
```
                            """.trimIndent(),
                        codeExample =
                            """
@Composable
fun AnimatedTopicCard(
    topic: Topic,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val transition = updateTransition(isSelected, label = "selection")

    val elevation by transition.animateDp(label = "elevation") { selected ->
        if (selected) 12.dp else 4.dp
    }
    val scale by transition.animateFloat(label = "scale") { selected ->
        if (selected) 1.05f else 1f
    }
    val borderColor by transition.animateColor(label = "border") { selected ->
        if (selected) MaterialTheme.colorScheme.primary else Color.Transparent
    }

    Card(
        modifier = Modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Text(
            text = topic.title,
            modifier = Modifier.padding(16.dp)
        )
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "animate*AsState for simple single-value animations",
                                "AnimatedVisibility for show/hide transitions",
                                "AnimatedContent for transitioning between different content",
                                "updateTransition animates multiple properties simultaneously",
                                "rememberInfiniteTransition for looping animations",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_ac1_1",
                                    lessonId = "adv_compose_1",
                                    text = "Which API would you use to animate multiple properties (color + size) simultaneously?",
                                    options =
                                        listOf(
                                            "animateFloatAsState called multiple times",
                                            "updateTransition",
                                            "AnimatedVisibility",
                                            "LaunchedEffect",
                                        ),
                                    correctIndex = 1,
                                    explanation = "updateTransition manages multiple animated values tied to the same state change, keeping them synchronized.",
                                ),
                                Question(
                                    id = "q_ac1_2",
                                    lessonId = "adv_compose_1",
                                    text = "What does AnimatedContent do?",
                                    options =
                                        listOf(
                                            "Loads content asynchronously",
                                            "Animates transitions between different content based on a state change",
                                            "Creates a loading animation",
                                            "Caches Composables",
                                        ),
                                    correctIndex = 1,
                                    explanation = "AnimatedContent provides crossfade-like transitions when its targetState changes, animating old content out and new content in.",
                                ),
                                Question(
                                    id = "q_ac1_3",
                                    lessonId = "adv_compose_1",
                                    text = "How do you create a looping/infinite animation in Compose?",
                                    options =
                                        listOf(
                                            "Use rememberInfiniteTransition",
                                            "Use a while(true) loop in a Coroutine",
                                            "Set duration to -1",
                                            "Animations cannot loop",
                                        ),
                                    correctIndex = 0,
                                    explanation = "rememberInfiniteTransition allows you to create child animations (like animateFloat) that repeat forever.",
                                ),
                                Question(
                                    id = "q_ac1_4",
                                    lessonId = "adv_compose_1",
                                    text = "Which spec would you use for a spring-based animation?",
                                    options = listOf("tween()", "spring()", "keyframes()", "snap()"),
                                    correctIndex = 1,
                                    explanation = "spring() creates a physics-based animation that feels more natural and bouncy than a time-based tween.",
                                ),
                                Question(
                                    id = "q_ac1_5",
                                    lessonId = "adv_compose_1",
                                    text = "What is the purpose of `animateColorAsState`?",
                                    options =
                                        listOf(
                                            "To change the color of the text",
                                            "To smoothly transition between two color values",
                                            "To generate random colors",
                                            "To set the theme color",
                                        ),
                                    correctIndex = 1,
                                    explanation = "It's a high-level API that produces a smoothly changing Color value as the input color changes.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "adv_compose_2",
                        topicId = "advanced_compose",
                        order = 2,
                        title = "Compose Navigation",
                        summary = "Navigate between screens cleanly with Navigation Compose.",
                        content =
                            """
## Navigation Compose

Compose Navigation uses a `NavController` + `NavHost` to navigate between composable destinations.

### Setup

```kotlin
val navController = rememberNavController()

NavHost(
    navController = navController,
    startDestination = "home"
) {
    composable("home") { HomeScreen(navController) }
    composable("detail/{itemId}") { backStackEntry ->
        val itemId = backStackEntry.arguments?.getString("itemId")
        DetailScreen(itemId)
    }
}
```

### Navigation Actions

```kotlin
// Navigate to a route
navController.navigate("detail/42")

// Navigate and pop the back stack
navController.navigate("home") {
    popUpTo("home") { inclusive = true }
}

// Navigate with no duplicates
navController.navigate("detail/42") {
    launchSingleTop = true
}
```

### Back Navigation

```kotlin
// Go back
navController.navigateUp()
// or
navController.popBackStack()
```

### Passing Complex Data

For complex data, use a ViewModel (not NavArgs) — keep Args simple (IDs, primitives).

### Bottom Navigation

```kotlin
BottomNavigation {
    bottomNavItems.forEach { item ->
        BottomNavigationItem(
            selected = currentRoute == item.route,
            onClick = { navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }},
            icon = { Icon(item.icon, contentDescription = item.title) },
            label = { Text(item.title) }
        )
    }
}
```
                            """.trimIndent(),
                        codeExample =
                            """
// Define routes as constants — type-safe approach
object Screen {
    const val HOME = "home"
    const val TOPIC = "topic/{topicId}"
    const val LESSON = "lesson/{topicId}/{lessonId}"

    fun topicRoute(topicId: String) = "topic/${"$"}topicId"
    fun lessonRoute(topicId: String, lessonId: String) = "lesson/${"$"}topicId/${"$"}lessonId"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.HOME) {
        composable(Screen.HOME) {
            HomeScreen(onTopicClick = { topicId ->
                navController.navigate(Screen.topicRoute(topicId))
            })
        }
        composable(
            route = Screen.TOPIC,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments!!.getString("topicId")!!
            TopicScreen(
                topicId = topicId,
                onLessonClick = { lessonId ->
                    navController.navigate(Screen.lessonRoute(topicId, lessonId))
                },
                onBack = navController::navigateUp
            )
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "NavController manages the back stack; NavHost defines destinations",
                                "Use string constants for routes to avoid typos",
                                "Pass simple data (IDs) via NavArgs; complex data via ViewModel",
                                "Use popUpTo + restoreState for proper bottom nav behavior",
                                "Always pass NavController down through lambda callbacks, not directly",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_ac2_1",
                                    lessonId = "adv_compose_2",
                                    text = "What is the best practice for passing data between screens?",
                                    options =
                                        listOf(
                                            "Pass the full object through NavArgs",
                                            "Use a global variable",
                                            "Pass only IDs through NavArgs, fetch data in the destination via ViewModel",
                                            "Use intent extras",
                                        ),
                                    correctIndex = 2,
                                    explanation = "NavArgs should contain only simple primitive identifiers. Fetch actual data in the destination ViewModel to keep navigation clean.",
                                ),
                                Question(
                                    id = "q_ac2_2",
                                    lessonId = "adv_compose_2",
                                    text = "How should you pass NavController to child Composables?",
                                    options =
                                        listOf(
                                            "Pass NavController directly as a parameter",
                                            "Use a CompositionLocal",
                                            "Pass navigation actions as lambda callbacks",
                                            "Use a global NavController object",
                                        ),
                                    correctIndex = 2,
                                    explanation = "Composables should receive navigation actions as lambdas (e.g., onBack: () -> Unit) rather than NavController itself, for better testability and reusability.",
                                ),
                                Question(
                                    id = "q_ac2_3",
                                    lessonId = "adv_compose_2",
                                    text = "What does `popUpTo` do in a navigation call?",
                                    options =
                                        listOf(
                                            "Shows a popup dialog",
                                            "Removes destinations from the back stack until the specified route is reached",
                                            "Navigates to the top of the app",
                                            "Closes the app",
                                        ),
                                    correctIndex = 1,
                                    explanation = "popUpTo is used to clear the back stack up to a certain point, preventing users from backing into screens like Login after succeeding.",
                                ),
                                Question(
                                    id = "q_ac2_4",
                                    lessonId = "adv_compose_2",
                                    text = "How do you handle a dynamic argument like `{userId}` in a NavHost route?",
                                    options =
                                        listOf(
                                            "Use a query parameter",
                                            "Define it in the route string and register it in the `arguments` list",
                                            "Pass it via a SharedFlow",
                                            "It is handled automatically",
                                        ),
                                    correctIndex = 1,
                                    explanation = "You define the placeholder in the route string and provide a list of `navArgument` to specify the type and behavior.",
                                ),
                                Question(
                                    id = "q_ac2_5",
                                    lessonId = "adv_compose_2",
                                    text = "What is the role of the `NavHost`?",
                                    options =
                                        listOf(
                                            "It handles network requests",
                                            "It acts as a container that displays the current destination of the NavController",
                                            "It stores the user's login state",
                                            "It provides the theme to the app",
                                        ),
                                    correctIndex = 1,
                                    explanation = "NavHost links the NavController with a navigation graph that specifies the composable destinations.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "adv_compose_3",
                        topicId = "advanced_compose",
                        order = 3,
                        title = "Compose Performance Tips",
                        summary = "Understand stability, derivedStateOf, and best practices to keep your UI smooth.",
                        content =
                            """
## Understanding Recomposition

Recomposition is efficient but not free. Unnecessary recompositions can cause jank.

## Stability & the Compose Compiler

Compose compiler skips recomposing a Composable if all its parameters are **stable** and haven't changed.

**Stable types:**
- Primitives (Int, Float, String, Boolean)
- `@Stable` or `@Immutable` annotated classes
- Functional types (lambdas)

**Unstable types (trigger recomposition):**
- `List<T>`, `Map<K, V>` (use `ImmutableList` from kotlinx-collections-immutable instead)
- Mutable classes

```kotlin
@Immutable
data class UiState(val items: ImmutableList<Item>)
```

## derivedStateOf

Use when a value is derived from other state. Avoids recomposing due to intermediate state changes:

```kotlin
// ❌ Recomposes on every scroll
val showButton = listState.firstVisibleItemIndex > 0

// ✅ Only recomposes when the derived boolean changes
val showButton by remember {
    derivedStateOf { listState.firstVisibleItemIndex > 0 }
}
```

## key() in Composables

Forces a Composable to be reset when the key changes:

```kotlin
key(userId) {
    UserProfile(userId)  // recreated when userId changes
}
```

## Avoid Computations in Composition

Move expensive work out of the composition:

```kotlin
// ❌ Recalculated every recomposition
val sorted = items.sortedBy { it.name }

// ✅ Only recalculated when items changes
val sorted = remember(items) { items.sortedBy { it.name } }
```

## Use `@Stable` and `@Immutable`

```kotlin
@Stable  // promises the type changes predictably
class MyState { var value by mutableStateOf(0) }

@Immutable  // promises the type never changes after construction
data class Config(val theme: String, val language: String)
```
                            """.trimIndent(),
                        codeExample =
                            """
// Performance-optimized list screen
@Composable
fun TopicListScreen(
    topics: ImmutableList<Topic>,  // Stable parameter
    listState: LazyListState = rememberLazyListState()
) {
    // derivedStateOf: only recompose the FAB when crossing the threshold
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 3 }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = topics,
                key = { it.id }  // Stable key for smart diffing
            ) { topic ->
                TopicCard(
                    topic = topic,
                    // Pass lambdas — they're stable
                    onClick = rememberCallback(topic.id) { /* navigate */ }
                )
            }
        }

        AnimatedVisibility(
            visible = showScrollToTop,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            FloatingActionButton(onClick = { /* scroll to top */ }) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Scroll to top")
            }
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Compose skips recomposing Composables with stable, unchanged parameters",
                                "Use derivedStateOf to avoid recomposing on every intermediate state change",
                                "Prefer ImmutableList over List for stable state in Compose",
                                "remember(key) { } caches expensive computations",
                                "Use @Immutable/@Stable annotations to help the compiler optimize",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_ac3_1",
                                    lessonId = "adv_compose_3",
                                    text = "Why should you use `derivedStateOf` for scroll-based visibility?",
                                    options =
                                        listOf(
                                            "To make scrolling faster",
                                            "To avoid recomposing on every scroll pixel when only the threshold change matters",
                                            "To save the scroll position",
                                            "It's required for LazyColumn",
                                        ),
                                    correctIndex = 1,
                                    explanation = "derivedStateOf only triggers recomposition when the derived Boolean changes (true/false), not on every scroll offset update.",
                                ),
                                Question(
                                    id = "q_ac3_2",
                                    lessonId = "adv_compose_3",
                                    text = "What makes a type stable in Compose?",
                                    options =
                                        listOf(
                                            "It implements Parcelable",
                                            "It's a primitive, annotated with @Stable/@Immutable, or a lambda",
                                            "It's declared in the domain layer",
                                            "It extends ViewModel",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Compose considers types stable if they're primitives, lambdas, or annotated with @Stable/@Immutable — the compiler can then skip recomposing when they haven't changed.",
                                ),
                                Question(
                                    id = "q_ac3_3",
                                    lessonId = "adv_compose_3",
                                    text = "Why is a `List` considered unstable by the Compose compiler?",
                                    options =
                                        listOf(
                                            "Because it can be very large",
                                            "Because the compiler cannot guarantee that the underlying implementation is immutable",
                                            "Because it doesn't implement equals()",
                                            "Lists are actually stable",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Standard Kotlin Interfaces like List can be implemented by mutable classes (like ArrayList), so Compose always recomposes them to be safe.",
                                ),
                                Question(
                                    id = "q_ac3_4",
                                    lessonId = "adv_compose_3",
                                    text = "What is the benefit of the `@Immutable` annotation?",
                                    options =
                                        listOf(
                                            "It makes the class fields final",
                                            "It promises the compiler that all fields are immutable, allowing it to skip unnecessary recompositions",
                                            "It prevents the class from being inherited",
                                            "It's required for Room entities",
                                        ),
                                    correctIndex = 1,
                                    explanation = "@Immutable is a promise to the compiler that the object state will never change after construction.",
                                ),
                                Question(
                                    id = "q_ac3_5",
                                    lessonId = "adv_compose_3",
                                    text = "How does `remember(key) { }` help with performance?",
                                    options =
                                        listOf(
                                            "It compresses data",
                                            "It caches the result and only re-calculates it if the key changes",
                                            "It moves the work to a background thread",
                                            "It prevents memory leaks",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Use remember(key) to avoid doing expensive work (like sorting a list) on every recomposition if the inputs haven't changed.",
                                ),
                            ),
                    ),
                ),
        )

    // ─────────────────────────────────────────────
    // TOPIC 3 — Kotlin Coroutines
    // ─────────────────────────────────────────────
    private fun buildCoroutinesTopic() =
        Topic(
            id = "coroutines",
            title = "Kotlin Coroutines",
            description = "Write clean async code without callbacks. Master structured concurrency.",
            emoji = "⚡",
            colorHex = "#E65100",
            secondaryColorHex = "#FFCC02",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedMinutes = 55,
            lessons =
                listOf(
                    Lesson(
                        id = "coroutines_1",
                        topicId = "coroutines",
                        order = 1,
                        title = "Suspend Functions & Coroutine Basics",
                        summary = "Understand what coroutines are and how suspend functions work.",
                        content =
                            """
## What are Coroutines?

Coroutines are **lightweight threads** that can suspend their execution without blocking the thread. They let you write asynchronous code that looks and reads like synchronous code.

```kotlin
// Without coroutines (callback hell)
fetchUser(id) { user ->
    fetchPosts(user.id) { posts ->
        fetchComments(posts.first().id) { comments ->
            updateUI(user, posts, comments)  // deeply nested
        }
    }
}

// With coroutines (sequential and clean)
val user = fetchUser(id)
val posts = fetchPosts(user.id)
val comments = fetchComments(posts.first().id)
updateUI(user, posts, comments)
```

## Suspend Functions

A `suspend` function can be paused and resumed without blocking a thread. It can only be called from another suspend function or from a coroutine.

```kotlin
suspend fun fetchUser(id: String): User {
    return withContext(Dispatchers.IO) {
        api.getUser(id)  // blocks the IO thread, not the main thread
    }
}
```

## Coroutine Builders

- `launch { }` — fire-and-forget; returns a `Job`
- `async { }` — returns a `Deferred<T>` with a result
- `runBlocking { }` — blocks the current thread (testing only!)

```kotlin
// launch: no result needed
val job = scope.launch {
    saveToDatabase(data)
}

// async: need the result
val deferred = scope.async { fetchData() }
val result = deferred.await()  // suspends until done
```

## The Golden Rule

> **Never use `runBlocking` on the main thread in production code.**
> Use `lifecycleScope.launch` or `viewModelScope.launch` instead.
                            """.trimIndent(),
                        codeExample =
                            """
// Real ViewModel using coroutines
class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun loadUser(userId: String) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                // Sequential: fetch user, then their posts
                val user = userRepository.getUser(userId)
                val posts = userRepository.getPostsForUser(userId)
                _uiState.value = UserUiState.Success(user, posts)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // Parallel: fetch user and posts concurrently
    fun loadUserAndPostsConcurrently(userId: String) {
        viewModelScope.launch {
            val userDeferred = async { userRepository.getUser(userId) }
            val postsDeferred = async { userRepository.getPostsForUser(userId) }
            val user = userDeferred.await()
            val posts = postsDeferred.await()
            _uiState.value = UserUiState.Success(user, posts)
        }
    }
}

sealed interface UserUiState {
    data object Loading : UserUiState
    data class Success(val user: User, val posts: List<Post>) : UserUiState
    data class Error(val message: String) : UserUiState
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Coroutines are lightweight — you can have thousands without performance issues",
                                "suspend functions pause, not block — the thread is free to do other work",
                                "launch is for fire-and-forget; async is for when you need a result",
                                "viewModelScope.launch ties the coroutine to the ViewModel lifecycle",
                                "Never use runBlocking on the main thread in production",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_co1_1",
                                    lessonId = "coroutines_1",
                                    text = "What is the difference between `launch` and `async`?",
                                    options =
                                        listOf(
                                            "launch is on main thread, async is on IO thread",
                                            "launch returns a Job (no result); async returns Deferred<T> (with result)",
                                            "async is slower than launch",
                                            "launch can suspend, async cannot",
                                        ),
                                    correctIndex = 1,
                                    explanation = "launch is for fire-and-forget coroutines. async returns a Deferred whose result you await — useful for parallel computations.",
                                ),
                                Question(
                                    id = "q_co1_2",
                                    lessonId = "coroutines_1",
                                    text = "Where should you launch most coroutines in a ViewModel?",
                                    options =
                                        listOf(
                                            "GlobalScope",
                                            "runBlocking",
                                            "viewModelScope",
                                            "lifecycleScope",
                                        ),
                                    correctIndex = 2,
                                    explanation = "viewModelScope automatically cancels all coroutines when the ViewModel is cleared, preventing memory leaks.",
                                ),
                                Question(
                                    id = "q_co1_3",
                                    lessonId = "coroutines_1",
                                    text = "When a suspend function pauses, what happens to the thread?",
                                    options =
                                        listOf(
                                            "The thread blocks and waits",
                                            "The thread terminates",
                                            "The thread is free to execute other coroutines",
                                            "The thread moves to background",
                                        ),
                                    correctIndex = 2,
                                    explanation = "A key advantage: suspending a coroutine does NOT block the thread. The thread is free to run other work while the coroutine is suspended.",
                                ),
                                Question(
                                    id = "q_co1_4",
                                    lessonId = "coroutines_1",
                                    text = "Can you call a suspend function from a regular function?",
                                    options =
                                        listOf(
                                            "Yes, always",
                                            "No, it must be called from another suspend function or a coroutine builder",
                                            "Only if the function is in an Activity",
                                            "Only if using withContext",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Suspension requires a coroutine context. Regular functions don't have one.",
                                ),
                                Question(
                                    id = "q_co1_5",
                                    lessonId = "coroutines_1",
                                    text = "What is the role of `runBlocking`?",
                                    options =
                                        listOf(
                                            "To run production code faster",
                                            "To bridge regular code and coroutines by blocking the current thread until completion (mainly for tests)",
                                            "To run code on the UI thread",
                                            "To prevent the app from closing",
                                        ),
                                    correctIndex = 1,
                                    explanation = "runBlocking blocks the calling thread, which is why it should only be used in unit tests or main functions, never in UI code.",
                                ),
                                Question(
                                    id = "q_co1_6",
                                    lessonId = "coroutines_1",
                                    text = "What does 'lightweight threads' mean for coroutines?",
                                    options =
                                        listOf(
                                            "They don't use memory",
                                            "They are limited to 10 per app",
                                            "You can launch thousands of them without significant performance overhead",
                                            "They only work on lightweight devices",
                                        ),
                                    correctIndex = 2,
                                    explanation = "Unlike OS threads which are expensive, coroutines are managed by the Kotlin runtime and are very efficient.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "coroutines_2",
                        topicId = "coroutines",
                        order = 2,
                        title = "Dispatchers & Coroutine Context",
                        summary = "Learn how to switch threads correctly with Dispatchers.",
                        content =
                            """
## Coroutine Dispatchers

Dispatchers determine **which thread** a coroutine runs on.

| Dispatcher | Use case |
|---|---|
| `Dispatchers.Main` | UI updates, lightweight work |
| `Dispatchers.IO` | Network, file, database operations |
| `Dispatchers.Default` | CPU-intensive work (parsing, sorting) |
| `Dispatchers.Unconfined` | Rarely used; no specific thread |

```kotlin
viewModelScope.launch(Dispatchers.IO) {
    val data = fetchFromNetwork()  // runs on IO pool
    withContext(Dispatchers.Main) {
        updateUI(data)  // switch back to main
    }
}
```

## withContext — Thread Switching

`withContext` suspends the coroutine, switches to the given dispatcher, executes the block, then switches back:

```kotlin
suspend fun loadData(): Data = withContext(Dispatchers.IO) {
    api.fetchData()  // IO thread
}  // automatically back to the original dispatcher
```

## CoroutineContext

A coroutine's context is a set of properties:
- `Dispatcher` — which thread
- `Job` — lifecycle management
- `CoroutineName` — for debugging
- `CoroutineExceptionHandler` — error handling

```kotlin
val context = Dispatchers.IO + CoroutineName("DataFetch") + exceptionHandler
scope.launch(context) { ... }
```

## The IO Dispatcher Optimization

`Dispatchers.IO` uses a thread pool of up to 64 threads (or more, configurable). Ideal for blocking I/O:

```kotlin
// Correct: blocking database call on IO
suspend fun getUser(id: Int): User = withContext(Dispatchers.IO) {
    database.userDao().getUser(id)  // suspends, not blocks
}
```
                            """.trimIndent(),
                        codeExample =
                            """
class ImageRepository(private val api: ImageApi) {

    // withContext switches to IO, then back to original dispatcher
    suspend fun fetchImage(url: String): Bitmap = withContext(Dispatchers.IO) {
        api.downloadImage(url)
    }

    // CPU-intensive work on Default dispatcher
    suspend fun processImage(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        bitmap.applyFilter().resize(800, 600)
    }

    // Chain them naturally — looks synchronous!
    suspend fun fetchAndProcessImage(url: String): Bitmap {
        val raw = fetchImage(url)         // IO
        return processImage(raw)          // Default
    }
}

class ImageViewModel(private val repo: ImageRepository) : ViewModel() {
    private val _image = MutableStateFlow<Bitmap?>(null)
    val image = _image.asStateFlow()

    fun loadImage(url: String) {
        viewModelScope.launch {  // starts on Main
            _image.value = repo.fetchAndProcessImage(url)  // suspends here
            // After suspension: automatically back on Main to update StateFlow
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Dispatchers.IO for network/database, Dispatchers.Default for CPU work",
                                "withContext switches dispatcher for a block, then restores the original",
                                "viewModelScope launches on Dispatchers.Main by default",
                                "Never do blocking I/O on Dispatchers.Main — use Dispatchers.IO",
                                "CoroutineContext combines multiple context elements",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_co2_1",
                                    lessonId = "coroutines_2",
                                    text = "Which dispatcher should you use for network calls?",
                                    options =
                                        listOf(
                                            "Dispatchers.Main",
                                            "Dispatchers.Default",
                                            "Dispatchers.IO",
                                            "Dispatchers.Unconfined",
                                        ),
                                    correctIndex = 2,
                                    explanation = "Dispatchers.IO is optimized for blocking I/O operations — it uses a large thread pool to handle many simultaneous requests.",
                                ),
                                Question(
                                    id = "q_co2_2",
                                    lessonId = "coroutines_2",
                                    text = "What does `withContext(Dispatchers.IO)` return?",
                                    options =
                                        listOf(
                                            "A Job",
                                            "A Deferred<T>",
                                            "The result of the block, after switching back to the original dispatcher",
                                            "A Flow",
                                        ),
                                    correctIndex = 2,
                                    explanation = "withContext is a suspending function that executes the block on the given dispatcher and returns its result, then resumes on the original dispatcher.",
                                ),
                                Question(
                                    id = "q_co2_3",
                                    lessonId = "coroutines_2",
                                    text = "Which dispatcher is best for complex JSON parsing or sorting large lists?",
                                    options = listOf("Main", "IO", "Default", "Unconfined"),
                                    correctIndex = 2,
                                    explanation = "Dispatchers.Default is optimized for CPU-intensive work and is backed by a thread pool equal to the number of CPU cores.",
                                ),
                                Question(
                                    id = "q_co2_4",
                                    lessonId = "coroutines_2",
                                    text = "What happens if you run a long loop on `Dispatchers.Main`?",
                                    options =
                                        listOf(
                                            "It runs faster",
                                            "It blocks the UI thread, causing the app to freeze (ANR)",
                                            "It uses the GPU",
                                            "It automatically moves to background",
                                        ),
                                    correctIndex = 1,
                                    explanation = "The Main thread handles drawing and user input. Blocking it for more than a few milliseconds causes jank or an 'Application Not Responding' crash.",
                                ),
                                Question(
                                    id = "q_co2_5",
                                    lessonId = "coroutines_2",
                                    text = "How do you combine multiple CoroutineContext elements (like a Dispatcher and a Name)?",
                                    options =
                                        listOf(
                                            "Using the `and` keyword",
                                            "Using the `+` operator",
                                            "By passing them as a List",
                                            "You can only have one element",
                                        ),
                                    correctIndex = 1,
                                    explanation = "The + operator is overloaded to combine CoroutineContext elements into a single context.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "coroutines_3",
                        topicId = "coroutines",
                        order = 3,
                        title = "Structured Concurrency & Scopes",
                        summary = "Learn how coroutine scopes enforce lifecycle and prevent leaks.",
                        content =
                            """
## What is Structured Concurrency?

Structured Concurrency is the principle that:
> A coroutine must complete before the coroutine that launched it can complete.

This prevents **orphaned coroutines** (fire-and-forget with no lifecycle management).

## Coroutine Scopes

A `CoroutineScope` owns coroutines. When the scope is cancelled, all its children are cancelled too.

```kotlin
// CoroutineScope tied to a lifecycle
val scope = CoroutineScope(Dispatchers.Main + Job())
scope.launch { doWork() }
// When done: scope.cancel() — cancels all children
```

### Built-in Scopes

- **viewModelScope** — cancelled when ViewModel is cleared
- **lifecycleScope** — cancelled when the lifecycle owner is destroyed
- **GlobalScope** — NOT recommended (lives as long as the app)

## Parent-Child Relationships

Child coroutines inherit the context of their parent. If a parent is cancelled, all children are cancelled.

```kotlin
viewModelScope.launch {
    val job1 = launch { task1() }
    val job2 = launch { task2() }
    // If viewModelScope is cancelled, job1 and job2 are cancelled too
}
```

## coroutineScope vs supervisorScope

- `coroutineScope { }` — if any child fails, ALL children are cancelled
- `supervisorScope { }` — children fail independently; other children continue

```kotlin
// All or nothing
coroutineScope {
    val a = async { fetchA() }
    val b = async { fetchB() }
    combine(a.await(), b.await())
}

// Independent failures
supervisorScope {
    launch { fetchNonCritical() }  // failure won't affect...
    launch { fetchCritical() }     // ...this one
}
```
                            """.trimIndent(),
                        codeExample =
                            """
class DataViewModel(
    private val primaryRepo: PrimaryRepository,
    private val analyticsRepo: AnalyticsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DataState>(DataState.Loading)
    val state = _state.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            try {
                // coroutineScope: if ANY child fails, both fail
                val data = coroutineScope {
                    val primary = async { primaryRepo.fetchData() }
                    val analytics = async { analyticsRepo.fetchMetrics() }
                    DataResult(primary.await(), analytics.await())
                }
                _state.value = DataState.Success(data)
            } catch (e: Exception) {
                _state.value = DataState.Error(e.message ?: "Failed")
            }
        }
    }

    fun loadWithFallback() {
        viewModelScope.launch {
            // supervisorScope: analytics failure won't block primary data
            val primaryData = primaryRepo.fetchData()

            supervisorScope {
                launch {
                    try { analyticsRepo.trackView() }
                    catch (e: Exception) { /* silently ignore analytics failure */ }
                }
            }
            _state.value = DataState.Success(DataResult(primaryData, null))
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Structured concurrency: child coroutines must complete before their parent",
                                "Cancelling a scope cancels all its children — no leaks",
                                "viewModelScope and lifecycleScope are the correct scopes for Android",
                                "coroutineScope: one failure cancels all; supervisorScope: failures are independent",
                                "Never use GlobalScope — it creates unmanaged coroutines",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_co3_1",
                                    lessonId = "coroutines_3",
                                    text = "What happens to child coroutines when a parent coroutine is cancelled?",
                                    options =
                                        listOf(
                                            "They continue running independently",
                                            "They complete their current task then stop",
                                            "They are cancelled immediately",
                                            "They move to GlobalScope",
                                        ),
                                    correctIndex = 2,
                                    explanation = "In structured concurrency, cancelling a parent propagates cancellation to all its children immediately.",
                                ),
                                Question(
                                    id = "q_co3_2",
                                    lessonId = "coroutines_3",
                                    text = "When should you use `supervisorScope`?",
                                    options =
                                        listOf(
                                            "When you want all coroutines to run on the Main thread",
                                            "When you want one child's failure to cancel siblings",
                                            "When child failures should not affect other children",
                                            "For background-only coroutines",
                                        ),
                                    correctIndex = 2,
                                    explanation = "supervisorScope makes children independent — a failure in one child doesn't cancel the others, unlike coroutineScope.",
                                ),
                                Question(
                                    id = "q_co3_3",
                                    lessonId = "coroutines_3",
                                    text = "Why is `GlobalScope` discouraged in Android development?",
                                    options =
                                        listOf(
                                            "It uses too much battery",
                                            "It creates coroutines that aren't tied to any lifecycle, leading to memory leaks and wasted work",
                                            "It only works in Java",
                                            "It doesn't support suspend functions",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Coroutines in GlobalScope live as long as the application, making them hard to manage and prone to leaking resources.",
                                ),
                                Question(
                                    id = "q_co3_4",
                                    lessonId = "coroutines_3",
                                    text = "What is a 'Job' in Kotlin coroutines?",
                                    options =
                                        listOf(
                                            "A background thread",
                                            "A handle to a coroutine that can be used to monitor its status or cancel it",
                                            "A piece of work for the CPU",
                                            "A Room database operation",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Every coroutine builder (launch, async) returns or involves a Job, which represents the coroutine's lifecycle.",
                                ),
                                Question(
                                    id = "q_co3_5",
                                    lessonId = "coroutines_3",
                                    text = "In `coroutineScope { }`, what happens if one child coroutine fails with an exception?",
                                    options =
                                        listOf(
                                            "Only that child stops",
                                            "The scope and all sibling coroutines are cancelled",
                                            "The app crashes immediately",
                                            "Nothing happens to other coroutines",
                                        ),
                                    correctIndex = 1,
                                    explanation = "coroutineScope follows the 'all-or-nothing' rule: if any child fails, it cancels the scope and all other children.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "coroutines_4",
                        topicId = "coroutines",
                        order = 4,
                        title = "Exception Handling in Coroutines",
                        summary = "Handle errors gracefully with try-catch, CoroutineExceptionHandler, and supervisorScope.",
                        content =
                            """
## Exception Handling Strategies

### 1. try-catch in suspend functions

The simplest approach — wrap suspect code:

```kotlin
viewModelScope.launch {
    try {
        val data = repository.fetchData()
        _state.value = State.Success(data)
    } catch (e: NetworkException) {
        _state.value = State.Error("No internet")
    } catch (e: Exception) {
        _state.value = State.Error("Unknown error")
    }
}
```

### 2. CoroutineExceptionHandler

Handles uncaught exceptions in `launch` coroutines (does NOT work with `async`):

```kotlin
val handler = CoroutineExceptionHandler { _, throwable ->
    Log.e("TAG", "Coroutine failed", throwable)
    _errorState.value = throwable.message
}

viewModelScope.launch(handler) {
    doRiskyWork()
}
```

### 3. Result / kotlin.Result

Wrap results in a `Result<T>`:

```kotlin
suspend fun safeApiCall(call: suspend () -> T): Result<T> = runCatching { call() }

viewModelScope.launch {
    safeApiCall { api.fetchData() }
        .onSuccess { data -> _state.value = State.Success(data) }
        .onFailure { e -> _state.value = State.Error(e.message) }
}
```

### CancellationException — Special Treatment

**Never catch CancellationException!** It's the mechanism for structured cancellation:

```kotlin
// ❌ This breaks cancellation
try { suspendingWork() } catch (e: Exception) { /* caught CancellationException! */ }

// ✅ Correct: only catch non-cancellation exceptions
try { suspendingWork() } catch (e: Exception) {
    if (e is CancellationException) throw e  // re-throw!
    handleError(e)
}
```
                            """.trimIndent(),
                        codeExample =
                            """
// Generic safe API call wrapper
suspend fun <T> safeCall(
    block: suspend () -> T
): Result<T> = try {
    Result.success(block())
} catch (e: CancellationException) {
    throw e  // ALWAYS re-throw CancellationException
} catch (e: Exception) {
    Result.failure(e)
}

// Usage in ViewModel
class ProductViewModel(private val repo: ProductRepository) : ViewModel() {

    private val _state = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val state = _state.asStateFlow()

    fun loadProduct(id: String) {
        viewModelScope.launch {
            _state.value = ProductUiState.Loading

            safeCall { repo.getProduct(id) }
                .onSuccess { product ->
                    _state.value = ProductUiState.Success(product)
                }
                .onFailure { error ->
                    _state.value = when (error) {
                        is NetworkException -> ProductUiState.NetworkError
                        is NotFoundException -> ProductUiState.NotFound
                        else -> ProductUiState.GenericError(error.message)
                    }
                }
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Use try-catch inside launch blocks for predictable error handling",
                                "CoroutineExceptionHandler catches uncaught exceptions from launch",
                                "ALWAYS re-throw CancellationException — never swallow it",
                                "kotlin.Result wrapping (runCatching) is great for repository layer",
                                "async exceptions are exposed only when you .await() — catch there",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_co4_1",
                                    lessonId = "coroutines_4",
                                    text = "Why should you NEVER swallow CancellationException?",
                                    options =
                                        listOf(
                                            "It causes ANRs",
                                            "It breaks structured concurrency — the coroutine won't know it was cancelled",
                                            "It crashes the app",
                                            "It causes memory leaks",
                                        ),
                                    correctIndex = 1,
                                    explanation = "CancellationException is the signal for structured cancellation. Catching and ignoring it prevents the coroutine from being properly cancelled.",
                                ),
                                Question(
                                    id = "q_co4_2",
                                    lessonId = "coroutines_4",
                                    text = "Does `CoroutineExceptionHandler` work with `async`?",
                                    options =
                                        listOf(
                                            "Yes, it catches all exceptions",
                                            "No, async exceptions are exposed through Deferred.await()",
                                            "Only if supervisorScope is used",
                                            "Only on Dispatchers.IO",
                                        ),
                                    correctIndex = 1,
                                    explanation = "CoroutineExceptionHandler only works for uncaught exceptions in launch. For async, exceptions are stored in the Deferred and thrown when you call .await().",
                                ),
                                Question(
                                    id = "q_co4_3",
                                    lessonId = "coroutines_4",
                                    text = "Where is the best place to use `try-catch` for an API call?",
                                    options =
                                        listOf(
                                            "Inside the Composable",
                                            "Directly around the suspending call in the ViewModel or Repository",
                                            "In the Application class",
                                            "API calls shouldn't use try-catch",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Wrapping the risky call directly allows you to handle specific errors (like NetworkException) and update the UI state accordingly.",
                                ),
                                Question(
                                    id = "q_co4_4",
                                    lessonId = "coroutines_4",
                                    text = "What is the purpose of `runCatching { }`?",
                                    options =
                                        listOf(
                                            "It runs a coroutine safely",
                                            "It's a Kotlin idiomatic way to execute a block and return a Result object containing either success or failure",
                                            "It catches memory leaks",
                                            "It restarts the app on failure",
                                        ),
                                    correctIndex = 1,
                                    explanation = "runCatching simplifies error handling by wrapping the result in a Result type that provides .onSuccess and .onFailure helpers.",
                                ),
                                Question(
                                    id = "q_co4_5",
                                    lessonId = "coroutines_4",
                                    text = "When an exception occurs in a coroutine, how does it propagate?",
                                    options =
                                        listOf(
                                            "It disappears",
                                            "It propagates up to the parent coroutine/scope",
                                            "It stays in the child coroutine",
                                            "It prints to Logcat only",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Exceptions propagate up the hierarchy, which is why a failure in one child can cancel the entire parent scope unless a Supervisor is used.",
                                ),
                            ),
                    ),
                ),
        )

    // ─────────────────────────────────────────────
    // TOPIC 4 — Kotlin Flows
    // ─────────────────────────────────────────────
    private fun buildFlowsTopic() =
        Topic(
            id = "kotlin_flows",
            title = "Kotlin Flows",
            description = "Reactive streams with Kotlin. Master cold/hot flows, operators, StateFlow and SharedFlow.",
            emoji = "🌊",
            colorHex = "#00695C",
            secondaryColorHex = "#4DB6AC",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedMinutes = 60,
            lessons =
                listOf(
                    Lesson(
                        id = "flows_1",
                        topicId = "kotlin_flows",
                        order = 1,
                        title = "Cold Flows & Flow Builders",
                        summary = "Understand what a Flow is and how to create cold flows.",
                        content =
                            """
## What is a Flow?

A `Flow<T>` is a **cold, asynchronous stream** of values. Unlike a `List`, values are emitted over time. Unlike `LiveData`, Flows are entirely Kotlin — no Android dependency.

> **Cold**: A Flow doesn't run until someone collects it. Each collector gets its own execution.

```kotlin
val myFlow: Flow<Int> = flow {
    emit(1)
    delay(1000)
    emit(2)
    delay(1000)
    emit(3)
}
```

## Flow Builders

### flow { }
The most flexible builder — emit values imperativity:
```kotlin
fun eventsFlow(): Flow<Event> = flow {
    while (true) {
        val event = pollEvent()
        emit(event)
    }
}
```

### flowOf()
Emit a fixed set of values:
```kotlin
val colors = flowOf("Red", "Green", "Blue")
```

### asFlow()
Convert a collection or sequence:
```kotlin
val numbers = (1..100).asFlow()
```

### channelFlow
For multi-threaded emission:
```kotlin
val flow = channelFlow {
    launch { send(fetchFromNetwork()) }
    launch { send(fetchFromCache()) }
}
```

## Collecting a Flow

You `collect` a flow inside a coroutine:

```kotlin
viewModelScope.launch {
    myFlow.collect { value ->
        _state.value = value
    }
}
```

## Flow is Cold — Demonstrated

```kotlin
val flow = flow { println("Building"); emit(1) }

// Nothing printed yet
flow.collect { }    // "Building" prints (1st collector)
flow.collect { }    // "Building" prints again! (2nd collector, fresh execution)
```
                            """.trimIndent(),
                        codeExample =
                            """
// Repository exposing data as a Flow
class WeatherRepository(private val api: WeatherApi) {

    // Cold flow: only fetches when collected
    fun observeWeather(city: String): Flow<Weather> = flow {
        while (true) {
            val weather = api.fetchWeather(city)  // network call
            emit(weather)
            delay(30_000)  // poll every 30 seconds
        }
    }.flowOn(Dispatchers.IO)  // run the upstream on IO

    // From a list of cities
    fun weatherForCities(cities: List<String>): Flow<Pair<String, Weather>> = flow {
        for (city in cities) {
            val weather = api.fetchWeather(city)
            emit(city to weather)
            delay(100)
        }
    }.flowOn(Dispatchers.IO)
}

// In ViewModel
class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {
    val weather: StateFlow<Weather?> = repo
        .observeWeather("London")
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Flow is cold — it doesn't execute until collected",
                                "Each collector gets a fresh, independent execution",
                                "flowOn() changes the dispatcher for upstream operations",
                                "Use flow { } for custom emission logic",
                                "Never emit from inside a coroutine launched within flow { } — use channelFlow instead",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_fl1_1",
                                    lessonId = "flows_1",
                                    text = "What does 'cold flow' mean?",
                                    options =
                                        listOf(
                                            "The flow emits very rarely",
                                            "The flow's code only runs when someone collects it",
                                            "The flow uses Dispatchers.IO",
                                            "The flow cannot be cancelled",
                                        ),
                                    correctIndex = 1,
                                    explanation = "A cold flow only starts executing when a collector subscribes. Each collector gets its own independent execution from the start.",
                                ),
                                Question(
                                    id = "q_fl1_2",
                                    lessonId = "flows_1",
                                    text = "What does `flowOn(Dispatchers.IO)` do?",
                                    options =
                                        listOf(
                                            "Collects the flow on the IO dispatcher",
                                            "Changes the dispatcher for the upstream (emission) part of the flow",
                                            "Schedules the flow to run every hour",
                                            "Buffers all values before emitting",
                                        ),
                                    correctIndex = 1,
                                    explanation = "flowOn changes the coroutine context for everything upstream of it — the emission side. Collection still happens on the original context.",
                                ),
                                Question(
                                    id = "q_fl1_3",
                                    lessonId = "flows_1",
                                    text = "Which function is used to start receiving values from a cold Flow?",
                                    options = listOf("start()", "collect()", "observe()", "execute()"),
                                    correctIndex = 1,
                                    explanation = "collect() is a terminal operator that triggers the flow's execution and starts receiving emitted values.",
                                ),
                                Question(
                                    id = "q_fl1_4",
                                    lessonId = "flows_1",
                                    text = "How do you emit a value from inside a `flow { }` builder?",
                                    options = listOf("send(value)", "push(value)", "emit(value)", "return(value)"),
                                    correctIndex = 2,
                                    explanation = "The emit() function is used inside the flow builder to send a new value to the collector.",
                                ),
                                Question(
                                    id = "q_fl1_5",
                                    lessonId = "flows_1",
                                    text = "Can a cold Flow have multiple collectors?",
                                    options =
                                        listOf(
                                            "No, only one collector is allowed",
                                            "Yes, and each collector will trigger a fresh execution of the flow code",
                                            "Yes, and they all share the same values from the same execution",
                                            "Only if it is converted to a StateFlow",
                                        ),
                                    correctIndex = 1,
                                    explanation = "One of the defining features of a cold Flow is that it is not shared; each call to collect() starts a new independent execution.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "flows_2",
                        topicId = "kotlin_flows",
                        order = 2,
                        title = "Flow Operators",
                        summary = "Transform, filter, and combine flows with powerful operators.",
                        content =
                            """
## Intermediate Operators (transform the flow)

### map / filter / take / drop
```kotlin
flow
    .filter { it > 0 }
    .map { it * 2 }
    .take(5)
```

### transform
More powerful than map — can emit multiple values per input:
```kotlin
flow.transform { value ->
    emit(value)
    emit(value * 2)
}
```

### flatMapLatest ⭐
Cancels previous inner flow when a new value arrives. Perfect for search:
```kotlin
searchQuery
    .debounce(300)
    .filter { it.length > 2 }
    .flatMapLatest { query ->
        searchRepository.search(query)
    }
    .collect { results -> updateUI(results) }
```

### flatMapConcat / flatMapMerge
- `flatMapConcat` — processes inner flows sequentially
- `flatMapMerge` — processes inner flows concurrently

### distinctUntilChanged
Skip emission if value didn't change:
```kotlin
flow.distinctUntilChanged()
```

## Terminal Operators (consume the flow)

```kotlin
flow.collect { value -> process(value) }
flow.toList()  // collects all values into a List
flow.first()   // collects only the first value
flow.single()  // asserts exactly one value
flow.reduce { acc, value -> acc + value }
flow.fold(initial = 0) { acc, value -> acc + value }
```

## Combining Flows

### combine
Emits whenever ANY of the flows emits:
```kotlin
combine(flow1, flow2) { a, b -> Result(a, b) }
```

### zip
Emits only when BOTH flows have a new value (pairs them):
```kotlin
namesFlow.zip(scoresFlow) { name, score -> "${"$"}name: ${"$"}score" }
```

### merge
Merge multiple flows into one:
```kotlin
merge(networkFlow, cacheFlow).collect { data -> handle(data) }
```
                            """.trimIndent(),
                        codeExample =
                            """
// Search with debounce + flatMapLatest
class SearchViewModel(private val searchRepo: SearchRepository) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // The core Flow pipeline
    val searchResults: StateFlow<SearchUiState> = _query
        .debounce(300L)                         // Wait 300ms after last keystroke
        .filter { it.isNotBlank() }             // Ignore empty queries
        .distinctUntilChanged()                 // Skip if same as last
        .flatMapLatest { query ->               // Cancel previous search on new query
            flow {
                emit(SearchUiState.Loading)
                val results = searchRepo.search(query)
                emit(SearchUiState.Results(results))
            }.catch { e ->
                emit(SearchUiState.Error(e.message))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Idle
        )

    fun onQueryChange(query: String) {
        _query.value = query
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "flatMapLatest cancels the previous inner flow — ideal for search/autocomplete",
                                "debounce delays emission until no new values arrive for the given duration",
                                "combine emits when ANY input changes; zip pairs values 1-to-1",
                                "catch operator handles exceptions mid-stream",
                                "distinctUntilChanged avoids processing duplicate sequential values",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_fl2_1",
                                    lessonId = "flows_2",
                                    text = "Why is `flatMapLatest` perfect for a search feature?",
                                    options =
                                        listOf(
                                            "It caches search results",
                                            "It cancels the previous search when a new query arrives",
                                            "It runs searches in parallel",
                                            "It debounces the input automatically",
                                        ),
                                    correctIndex = 1,
                                    explanation = "flatMapLatest automatically cancels the inner flow from the previous query when a new query arrives, preventing outdated results.",
                                ),
                                Question(
                                    id = "q_fl2_2",
                                    lessonId = "flows_2",
                                    text = "What is the difference between `combine` and `zip`?",
                                    options =
                                        listOf(
                                            "combine is for two flows; zip supports more",
                                            "combine emits when any flow emits; zip emits only when both have new values",
                                            "zip is asynchronous; combine is synchronous",
                                            "They are identical",
                                        ),
                                    correctIndex = 1,
                                    explanation = "combine re-emits with the latest value of all flows whenever any one of them emits. zip pairs values: it waits for both flows to emit before pairing them.",
                                ),
                                Question(
                                    id = "q_fl2_3",
                                    lessonId = "flows_2",
                                    text = "What does the `debounce(300L)` operator do?",
                                    options =
                                        listOf(
                                            "Repeats the last value every 300ms",
                                            "Ignores values that come faster than 300ms apart, only emitting the last one after the period passes",
                                            "Limits the flow to 300 total values",
                                            "Changes the dispatcher",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Debouncing is essential for search fields to avoid making a network request for every single keystroke.",
                                ),
                                Question(
                                    id = "q_fl2_4",
                                    lessonId = "flows_2",
                                    text = "Which operator would you use to handle exceptions inside a Flow pipeline?",
                                    options = listOf("try-catch", "handleError", "catch { }", "onErrorResumeNext"),
                                    correctIndex = 2,
                                    explanation = "The catch operator encapsulates the logic for handling upstream exceptions without leaking them to the collector.",
                                ),
                                Question(
                                    id = "q_fl2_5",
                                    lessonId = "flows_2",
                                    text = "What is the result of applying `distinctUntilChanged()`?",
                                    options =
                                        listOf(
                                            "All duplicate values in the flow are removed",
                                            "Subsequent identical values are skipped (only unique transitions are emitted)",
                                            "The flow is sorted",
                                            "The flow emits in reverse order",
                                        ),
                                    correctIndex = 1,
                                    explanation = "It prevents the flow from emitting the same value multiple times in a row, which is useful for avoiding redundant UI updates.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "flows_3",
                        topicId = "kotlin_flows",
                        order = 3,
                        title = "StateFlow & SharedFlow — Hot Flows",
                        summary = "Master hot flows for sharing state and events across the app.",
                        content =
                            """
## Hot vs Cold Flows

| | Cold Flow | Hot Flow |
|---|---|---|
| Starts | When collected | When created |
| Collectors | Each gets own execution | Share the same stream |
| Values | Replayed from start | Only get current/new values |
| Examples | Flow { } | StateFlow, SharedFlow |

## StateFlow

`StateFlow` is a hot flow that always holds a **current value** and emits when it changes. It's perfect for representing UI state.

```kotlin
val _state = MutableStateFlow(UiState.Loading)
val state: StateFlow<UiState> = _state.asStateFlow()  // expose as read-only

// Update
_state.value = UiState.Success(data)
```

**StateFlow properties:**
- Always has a value (initial value required)
- Replays the current value to new collectors
- Uses structural equality — won't emit if value doesn't change
- CONFLATED: fast collectors only see latest value

## SharedFlow

`SharedFlow` is for **one-to-many broadcasts** — events, navigation, messages:

```kotlin
private val _events = MutableSharedFlow<UiEvent>()
val events: SharedFlow<UiEvent> = _events.asSharedFlow()

// Emit
viewModelScope.launch { _events.emit(UiEvent.ShowToast("Saved!")) }
```

**SharedFlow configurations:**
```kotlin
MutableSharedFlow(
    replay = 0,          // How many past values new collectors see (0 = none)
    extraBufferCapacity = 1,  // Buffer for slow collectors
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)
```

## stateIn — Convert Flow to StateFlow

```kotlin
val uiState: StateFlow<UiState> = repository.dataFlow()
    .map { data -> data.toUiState() }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),  // active while observed, with 5s grace period
        initialValue = UiState.Loading
    )
```

> **SharingStarted.WhileSubscribed(5000)** keeps the flow active for 5 seconds after the last subscriber disappears (helps with rotation).

## shareIn — Convert Flow to SharedFlow

```kotlin
val sharedFlow = repository.eventsFlow()
    .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
```
                            """.trimIndent(),
                        codeExample =
                            """
class OrderViewModel(private val repo: OrderRepository) : ViewModel() {

    // StateFlow for UI state — always has a value
    private val _uiState = MutableStateFlow<OrderUiState>(OrderUiState.Loading)
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    // SharedFlow for one-off events — navigation, toasts
    private val _events = MutableSharedFlow<OrderEvent>()
    val events: SharedFlow<OrderEvent> = _events.asSharedFlow()

    // Auto-updated StateFlow from repository
    val orders: StateFlow<List<Order>> = repo.observeOrders()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun placeOrder(item: Item) {
        viewModelScope.launch {
            _uiState.value = OrderUiState.Loading
            repo.placeOrder(item)
                .onSuccess {
                    _uiState.value = OrderUiState.Success
                    _events.emit(OrderEvent.ShowConfirmation(it.orderId))
                }
                .onFailure { error ->
                    _uiState.value = OrderUiState.Error(error.message)
                    _events.emit(OrderEvent.ShowError(error.message ?: "Failed"))
                }
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "StateFlow = hot flow with always-valid current value; perfect for UI state",
                                "SharedFlow = hot broadcast for one-time events (navigation, toasts)",
                                "Expose MutableStateFlow as read-only StateFlow (asStateFlow())",
                                "stateIn() converts a cold Flow into a StateFlow with proper lifecycle",
                                "SharingStarted.WhileSubscribed(5000) keeps flow alive during screen rotation",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_fl3_1",
                                    lessonId = "flows_3",
                                    text = "Why should you use SharedFlow instead of StateFlow for navigation events?",
                                    options =
                                        listOf(
                                            "SharedFlow is faster",
                                            "StateFlow replays its latest value — a new subscriber would re-navigate; SharedFlow by default doesn't replay",
                                            "SharedFlow works on all threads",
                                            "StateFlow doesn't support navigation",
                                        ),
                                    correctIndex = 1,
                                    explanation = "StateFlow replays its current value to new collectors. A navigation event would trigger again after rotation. SharedFlow with replay=0 is the correct choice for one-time events.",
                                ),
                                Question(
                                    id = "q_fl3_2",
                                    lessonId = "flows_3",
                                    text = "What does `SharingStarted.WhileSubscribed(5000)` do in stateIn()?",
                                    options =
                                        listOf(
                                            "Starts the flow after 5000ms",
                                            "Keeps the flow active while there are subscribers, plus 5s grace period after the last subscriber disappears",
                                            "Buffers 5000 values",
                                            "Retries 5000 times on failure",
                                        ),
                                    correctIndex = 1,
                                    explanation = "WhileSubscribed(5000) keeps the upstream flow active for 5 seconds after the last subscriber disappears — avoiding restart during screen rotation.",
                                ),
                                Question(
                                    id = "q_fl3_3",
                                    lessonId = "flows_3",
                                    text = "What is the key difference between a hot flow and a cold flow?",
                                    options =
                                        listOf(
                                            "Hot flows are faster",
                                            "Cold flows only run when collected; hot flows can produce values even without collectors",
                                            "Cold flows only work on background threads",
                                            "Hot flows use more memory",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Hot flows (like StateFlow) are active regardless of collectors. Cold flows (like flow { }) are lazy and start fresh for every collector.",
                                ),
                                Question(
                                    id = "q_fl3_4",
                                    lessonId = "flows_3",
                                    text = "Does a StateFlow ever have a null value?",
                                    options =
                                        listOf(
                                            "Yes, always",
                                            "Only if the initial value is null",
                                            "No, it must always have a value (non-nullable by default unless T is nullable)",
                                            "StateFlow doesn't support values",
                                        ),
                                    correctIndex = 1,
                                    explanation = "StateFlow must be initialized with a value. It always provides its 'current' state to any observer.",
                                ),
                                Question(
                                    id = "q_fl3_5",
                                    lessonId = "flows_3",
                                    text = "Which function converts a cold Flow into a StateFlow?",
                                    options = listOf("asStateFlow()", "stateIn()", "toStateFlow()", "observe()"),
                                    correctIndex = 1,
                                    explanation = "stateIn() is a operator that starts a collection of the upstream flow in a given scope and exposes the results as a StateFlow.",
                                ),
                            ),
                    ),
                ),
        )

    // ─────────────────────────────────────────────
    // TOPIC 5 — Clean Architecture
    // ─────────────────────────────────────────────
    private fun buildCleanArchitectureTopic() =
        Topic(
            id = "clean_architecture",
            title = "Clean Architecture",
            description = "Structure your app for scalability, testability, and maintainability.",
            emoji = "🏗️",
            colorHex = "#2E7D32",
            secondaryColorHex = "#81C784",
            difficulty = Difficulty.ADVANCED,
            estimatedMinutes = 65,
            lessons =
                listOf(
                    Lesson(
                        id = "clean_1",
                        topicId = "clean_architecture",
                        order = 1,
                        title = "The Three Layers",
                        summary = "Understand the Domain, Data, and Presentation layers and their responsibilities.",
                        content =
                            """
## Clean Architecture Layers

Clean Architecture splits your codebase into concentric layers. The core rule:
> **Dependencies point inward.** Outer layers depend on inner layers, never the reverse.

```
┌─────────────────────────────┐
│   Presentation (ViewModel)  │ ← depends on Domain
│  ┌───────────────────────┐  │
│  │    Domain (Use Cases) │  │ ← depends on nothing
│  │  ┌─────────────────┐  │  │
│  │  │   Data (Repo)   │  │  │ ← depends on Domain interfaces
│  │  └─────────────────┘  │  │
│  └───────────────────────┘  │
└─────────────────────────────┘
```

## Domain Layer — The Core

- **Entities/Models** — pure Kotlin data classes. No Android, no frameworks.
- **Repository interfaces** — defines what data operations are possible
- **Use Cases** — encapsulate a single business operation

```kotlin
// Pure Kotlin — no imports from Android, Room, or Retrofit
data class User(val id: String, val name: String, val email: String)

interface UserRepository {
    suspend fun getUser(id: String): User
    fun observeUsers(): Flow<List<User>>
}

class GetUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id: String): User = repository.getUser(id)
}
```

## Data Layer

Implements domain interfaces. Uses frameworks (Room, Retrofit, DataStore):

```kotlin
class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun getUser(id: String): User {
        return localDataSource.getUser(id) ?: remoteDataSource.getUser(id).also {
            localDataSource.saveUser(it)
        }
    }
}
```

## Presentation Layer

ViewModels calling use cases, exposing UI state:

```kotlin
class UserViewModel(private val getUser: GetUserUseCase) : ViewModel() {
    fun load(id: String) {
        viewModelScope.launch {
            _state.value = State.Success(getUser(id))
        }
    }
}
```
                            """.trimIndent(),
                        codeExample =
                            """
// ──── Domain Layer (pure Kotlin) ────

data class Article(
    val id: String,
    val title: String,
    val content: String,
    val publishedAt: Long
)

interface ArticleRepository {
    fun getArticles(): Flow<List<Article>>
    suspend fun getArticleById(id: String): Article
    suspend fun bookmarkArticle(articleId: String)
}

class GetLatestArticlesUseCase(private val repo: ArticleRepository) {
    operator fun invoke(): Flow<List<Article>> =
        repo.getArticles().map { articles ->
            articles.sortedByDescending { it.publishedAt }
        }
}

// ──── Data Layer ────

class ArticleRepositoryImpl(
    private val remoteSource: ArticleRemoteSource,
    private val localSource: ArticleLocalSource
) : ArticleRepository {
    override fun getArticles(): Flow<List<Article>> =
        localSource.observeArticles()  // always show cached, refresh in background

    override suspend fun getArticleById(id: String): Article =
        localSource.getArticle(id) ?: remoteSource.fetchArticle(id)
}

// ──── Presentation Layer ────

class ArticleViewModel(
    private val getLatestArticles: GetLatestArticlesUseCase
) : ViewModel() {
    val articles: StateFlow<List<Article>> = getLatestArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Domain layer has NO framework dependencies — pure Kotlin only",
                                "Dependencies point inward: Presentation → Domain ← Data",
                                "Repository interfaces live in Domain; implementations live in Data",
                                "Use Cases contain one business operation — small and focused",
                                "This architecture makes testing each layer independently easy",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_cl1_1",
                                    lessonId = "clean_1",
                                    text = "Which layer should have zero Android/framework dependencies?",
                                    options =
                                        listOf(
                                            "Presentation layer",
                                            "Data layer",
                                            "Domain layer",
                                            "All layers can have Android dependencies",
                                        ),
                                    correctIndex = 2,
                                    explanation = "The Domain layer is the inner-most core. It must be pure Kotlin with no framework imports, making it testable without any Android dependencies.",
                                ),
                                Question(
                                    id = "q_cl1_2",
                                    lessonId = "clean_1",
                                    text = "Where does the Repository INTERFACE go in Clean Architecture?",
                                    options =
                                        listOf(
                                            "Data layer, since it's about data",
                                            "Presentation layer",
                                            "Domain layer, so the domain doesn't depend on the data layer",
                                            "In a shared module",
                                        ),
                                    correctIndex = 2,
                                    explanation = "The Repository interface lives in the Domain layer. This inverts the dependency: the Data layer implements the Domain interface, not the other way around.",
                                ),
                                Question(
                                    id = "q_cl1_3",
                                    lessonId = "clean_1",
                                    text = "What is the 'Inward Dependency' rule?",
                                    options =
                                        listOf(
                                            "Inner layers must know about outer layers",
                                            "Outer layers depend on inner layers; inner layers are independent",
                                            "All layers depend on each other",
                                            "Data layer depends on Presentation",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Dependencies always point toward the core (Domain layer). This ensures the business logic remains stable and unaffected by external changes.",
                                ),
                                Question(
                                    id = "q_cl1_4",
                                    lessonId = "clean_1",
                                    text = "What belongs in the Data layer?",
                                    options =
                                        listOf(
                                            "Use Cases",
                                            "ViewModels",
                                            "Repository implementations and Data Sources (Retrofit, Room)",
                                            "Business logic",
                                        ),
                                    correctIndex = 2,
                                    explanation = "The Data layer handles the actual retrieval and storage of data using specific frameworks and libraries.",
                                ),
                                Question(
                                    id = "q_cl1_5",
                                    lessonId = "clean_1",
                                    text = "Which layer is responsible for translating data into something the user can see?",
                                    options = listOf("Domain", "Data", "Presentation", "Network"),
                                    correctIndex = 2,
                                    explanation = "The Presentation layer (UI and ViewModels) is the only layer concerned with the user interface and user interaction.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "clean_2",
                        topicId = "clean_architecture",
                        order = 2,
                        title = "Use Cases — Single Responsibility Business Logic",
                        summary = "Learn how to write focused, testable use cases that encapsulate business rules.",
                        content =
                            """
## What is a Use Case?

A Use Case (also called Interactor) encapsulates **one business operation**. It:
- Takes input (parameters)
- Applies business rules
- Returns output via the repository

```kotlin
class LoginUseCase(
    private val authRepo: AuthRepository,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        val emailResult = validateEmail(email)
        if (!emailResult.isValid) return LoginResult.InvalidEmail(emailResult.error)

        val passwordResult = validatePassword(password)
        if (!passwordResult.isValid) return LoginResult.InvalidPassword(passwordResult.error)

        return try {
            val user = authRepo.login(email, password)
            LoginResult.Success(user)
        } catch (e: WrongCredentialsException) {
            LoginResult.WrongCredentials
        }
    }
}
```

## Why Use Cases?

1. **Single Responsibility** — each use case does ONE thing
2. **Reusable** — multiple ViewModels can share the same use case
3. **Testable** — easy to unit test with mock repositories
4. **Self-documenting** — `GetUserUseCase` clearly shows intent

## Validation Use Cases

```kotlin
class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) return ValidationResult(false, "Email is required")
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return ValidationResult(false, "Invalid email format")
        return ValidationResult(isValid = true)
    }
}
```

## Operator fun invoke

Using `operator fun invoke` lets you call the use case like a function:

```kotlin
// Without operator invoke:
loginUseCase.execute(email, password)

// With operator invoke:
loginUseCase(email, password)  // clean!
```
                            """.trimIndent(),
                        codeExample =
                            """
// Domain models
data class ValidationResult(val isValid: Boolean, val errorMessage: String? = null)

sealed class RegisterResult {
    data class Success(val user: User) : RegisterResult()
    data class ValidationError(val field: String, val message: String) : RegisterResult()
    data class NetworkError(val message: String) : RegisterResult()
    object EmailAlreadyExists : RegisterResult()
}

// Focused use cases
class ValidateUsernameUseCase {
    operator fun invoke(username: String): ValidationResult = when {
        username.isBlank() -> ValidationResult(false, "Username required")
        username.length < 3 -> ValidationResult(false, "Min 3 characters")
        username.length > 20 -> ValidationResult(false, "Max 20 characters")
        !username.matches(Regex("[a-zA-Z0-9_]+")) -> ValidationResult(false, "Letters, numbers, _ only")
        else -> ValidationResult(true)
    }
}

class RegisterUserUseCase(
    private val authRepo: AuthRepository,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateUsername: ValidateUsernameUseCase
) {
    suspend operator fun invoke(
        username: String, email: String, password: String
    ): RegisterResult {
        val usernameResult = validateUsername(username)
        if (!usernameResult.isValid) return RegisterResult.ValidationError("username", usernameResult.errorMessage!!)

        val emailResult = validateEmail(email)
        if (!emailResult.isValid) return RegisterResult.ValidationError("email", emailResult.errorMessage!!)

        return try {
            val user = authRepo.register(username, email, password)
            RegisterResult.Success(user)
        } catch (e: EmailAlreadyExistsException) {
            RegisterResult.EmailAlreadyExists
        } catch (e: Exception) {
            RegisterResult.NetworkError(e.message ?: "Unknown error")
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Use cases do ONE thing — single responsibility",
                                "operator fun invoke makes use cases callable as functions",
                                "Use cases are easily unit-testable by mocking the repository",
                                "Multiple ViewModels can share the same use case",
                                "Validation logic belongs in use cases, not ViewModels or repositories",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_cl2_1",
                                    lessonId = "clean_2",
                                    text = "What is the main benefit of `operator fun invoke` in a use case?",
                                    options =
                                        listOf(
                                            "It makes the function run on a background thread",
                                            "It allows calling the use case like a function: useCase(params) instead of useCase.execute(params)",
                                            "It makes the use case serializable",
                                            "It implements the interface automatically",
                                        ),
                                    correctIndex = 1,
                                    explanation = "operator fun invoke makes the class callable as a function, giving a cleaner syntax: getUser(id) instead of getUser.invoke(id).",
                                ),
                                Question(
                                    id = "q_cl2_2",
                                    lessonId = "clean_2",
                                    text = "Where should form validation logic live in Clean Architecture?",
                                    options =
                                        listOf(
                                            "In the UI/Composable",
                                            "In the ViewModel",
                                            "In domain Use Cases",
                                            "In the Repository",
                                        ),
                                    correctIndex = 2,
                                    explanation = "Validation is business logic and belongs in the Domain layer as Use Cases. This makes it reusable and independently testable.",
                                ),
                                Question(
                                    id = "q_cl2_3",
                                    lessonId = "clean_2",
                                    text = "A Use Case should ideally handle how many business operations?",
                                    options = listOf("All operations for a feature", "Exactly one", "Up to five", "As many as needed"),
                                    correctIndex = 1,
                                    explanation = "Use Cases follow the Single Responsibility Principle: one class, one business action. This keeps them focused and easy to maintain.",
                                ),
                                Question(
                                    id = "q_cl2_4",
                                    lessonId = "clean_2",
                                    text = "Can a Use Case depend on another Use Case?",
                                    options =
                                        listOf(
                                            "No, never",
                                            "Yes, this is common for complex logic",
                                            "Only in the Data layer",
                                            "Only if the other Use Case is in the same file",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Use Cases can be composed. For example, a RegisterUserUseCase might call a ValidateEmailUseCase.",
                                ),
                                Question(
                                    id = "q_cl2_5",
                                    lessonId = "clean_2",
                                    text = "What is the advantage of using Use Cases instead of calling Repositories directly from ViewModels?",
                                    options =
                                        listOf(
                                            "It makes the app faster",
                                            "It increases code reuse and makes the ViewModel smaller and focused only on UI logic",
                                            "It is required by Hilt",
                                            "It allows using Room",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Use Cases act as a bridge, preventing ViewModels from becoming bloated with business logic that could be shared across the app.",
                                ),
                            ),
                    ),
                ),
        )

    // ─────────────────────────────────────────────
    // TOPIC 6 — MVVM Pattern
    // ─────────────────────────────────────────────
    private fun buildMvvmTopic() =
        Topic(
            id = "mvvm",
            title = "MVVM & UI State",
            description = "Build robust ViewModels with proper UI state, events, and one-way data flow.",
            emoji = "🔄",
            colorHex = "#B71C1C",
            secondaryColorHex = "#EF9A9A",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedMinutes = 45,
            lessons =
                listOf(
                    Lesson(
                        id = "mvvm_1",
                        topicId = "mvvm",
                        order = 1,
                        title = "ViewModel & Lifecycle",
                        summary = "Understand the ViewModel, its lifecycle, and why it survives configuration changes.",
                        content =
                            """
## What is a ViewModel?

A `ViewModel` is a class that:
1. Holds and manages **UI-related data** in a lifecycle-conscious way
2. Survives **configuration changes** (screen rotation)
3. Scoped to an Activity/Fragment/NavBackStackEntry

```kotlin
class MyViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() { _count.value++ }

    override fun onCleared() {
        super.onCleared()
        // Called when Activity is finished (not rotated)
        // Good for cleanup
    }
}
```

## Why ViewModels Survive Rotation

When you rotate the screen:
1. Activity is **destroyed and recreated**
2. But `ViewModelStore` (held by `ViewModelStoreOwner`) **survives**
3. So the ViewModel **keeps its data**

## viewModelScope

`viewModelScope` is a `CoroutineScope` tied to the ViewModel's lifecycle:

```kotlin
class DataViewModel : ViewModel() {
    fun fetchData() {
        viewModelScope.launch {  // cancelled when ViewModel is cleared
            val data = repository.fetch()
            _state.value = UiState.Success(data)
        }
    }
}
```

## ViewModelFactory — Manual DI

When a ViewModel needs constructor parameters:

```kotlin
class MyViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MyViewModel(repository) as T
    }
}

// Usage
val vm: MyViewModel by viewModels { MyViewModelFactory(repository) }
```
                            """.trimIndent(),
                        codeExample =
                            """
// Modern ViewModel with viewModelFactory (no Hilt)
class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProfileEvent>()
    val events: SharedFlow<ProfileEvent> = _events.asSharedFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                val user = getUserUseCase()
                _uiState.value = ProfileUiState.Success(user)
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message)
            }
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            updateProfileUseCase(name)
                .onSuccess { _events.emit(ProfileEvent.ShowToast("Profile updated!")) }
                .onFailure { _events.emit(ProfileEvent.ShowToast("Update failed")) }
        }
    }

    companion object {
        fun factory(container: AppContainer) = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as AndroidDevRoadmapApp
                ProfileViewModel(
                    getUserUseCase = app.container.getUserUseCase,
                    updateProfileUseCase = app.container.updateProfileUseCase
                )
            }
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "ViewModels survive configuration changes — don't store View references in them",
                                "viewModelScope auto-cancels coroutines when the ViewModel is cleared",
                                "Expose immutable StateFlow/SharedFlow, mutate private MutableStateFlow",
                                "ViewModelFactory lets you inject dependencies without Hilt",
                                "onCleared() is the right place for cleanup (not configuration changes)",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_mv1_1",
                                    lessonId = "mvvm_1",
                                    text = "What happens to a ViewModel when the screen is rotated?",
                                    options =
                                        listOf(
                                            "It is destroyed and recreated",
                                            "It is cleared and all data is lost",
                                            "It survives — the same instance is reused by the new Activity",
                                            "It is moved to a background thread",
                                        ),
                                    correctIndex = 2,
                                    explanation = "The Activity is destroyed on rotation, but the ViewModel is retained in the ViewModelStore, so the same instance is used by the new Activity.",
                                ),
                                Question(
                                    id = "q_mv1_2",
                                    lessonId = "mvvm_1",
                                    text = "Why should you NOT store a View or Context reference in a ViewModel?",
                                    options =
                                        listOf(
                                            "It causes slower rendering",
                                            "Views are not Serializable",
                                            "The ViewModel outlives the Activity — holding a View reference causes memory leaks",
                                            "ViewModels don't have access to Context",
                                        ),
                                    correctIndex = 2,
                                    explanation = "ViewModels outlive Activities during configuration changes. Holding a reference to a destroyed Activity/View causes memory leaks.",
                                ),
                                Question(
                                    id = "q_mv1_3",
                                    lessonId = "mvvm_1",
                                    text = "Which CoroutineScope should you use inside a ViewModel?",
                                    options = listOf("GlobalScope", "lifecycleScope", "viewModelScope", "MainScope"),
                                    correctIndex = 2,
                                    explanation = "viewModelScope is specifically designed for ViewModels; it is automatically cancelled when the ViewModel is destroyed.",
                                ),
                                Question(
                                    id = "q_mv1_4",
                                    lessonId = "mvvm_1",
                                    text = "What is the purpose of `onCleared()` in a ViewModel?",
                                    options =
                                        listOf(
                                            "To save data before rotation",
                                            "To clean up resources (like closing sockets or cancelling timers) when the ViewModel is permanently destroyed",
                                            "To refresh the UI",
                                            "To clear the cache",
                                        ),
                                    correctIndex = 1,
                                    explanation = "onCleared is called when the Activity is finishing or the back stack entry is removed, indicating the ViewModel is no longer needed.",
                                ),
                                Question(
                                    id = "q_mv1_5",
                                    lessonId = "mvvm_1",
                                    text = "How do you provide dependencies to a ViewModel constructor if you're not using a DI framework like Hilt?",
                                    options =
                                        listOf(
                                            "You can't have constructor parameters",
                                            "Use a ViewModelProvider.Factory",
                                            "Use a global variable",
                                            "Pass them in the init block",
                                        ),
                                    correctIndex = 1,
                                    explanation = "A Factory is required to tell the ViewModelProvider how to instantiate your ViewModel with its dependencies.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "mvvm_2",
                        topicId = "mvvm",
                        order = 2,
                        title = "UI State Pattern & One-Way Data Flow",
                        summary = "Design robust UI state with sealed classes and enforce one-way data flow.",
                        content =
                            """
## One-Way Data Flow (UDF)

In UDF:
- **State** flows down from ViewModel to UI
- **Events** flow up from UI to ViewModel
- The ViewModel is the single source of truth for UI state

```
   ViewModel                  UI
      │                       │
      │ ← UI Events (clicks) ──┤
      │                       │
      ├── UI State (StateFlow) →│
```

## Modeling UI State with Sealed Classes

```kotlin
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val items: List<Item>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
```

## The Full UI State Object

A more complete approach uses a data class for complex screens:

```kotlin
data class HomeUiState(
    val isLoading: Boolean = false,
    val topics: List<Topic> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
) {
    val filteredTopics get() = topics.filter { it.title.contains(searchQuery, true) }
    val showEmptyState get() = !isLoading && filteredTopics.isEmpty()
}
```

## Handling UI Events vs UI Effects

- **UI State** — something the UI always needs to display (text, list, loading indicator)
- **UI Effects** — one-time actions (navigation, toast, snackbar)

```kotlin
// State: in StateFlow
data class UiState(val name: String, val isLoading: Boolean)

// Effects: in SharedFlow (replay = 0)
sealed class UiEffect {
    data object NavigateBack : UiEffect()
    data class ShowSnackbar(val message: String) : UiEffect()
}
```
                            """.trimIndent(),
                        codeExample =
                            """
// Complete MVVM pattern with UDF
data class TopicListUiState(
    val isLoading: Boolean = true,
    val topics: List<Topic> = emptyList(),
    val progress: UserProgress = UserProgress(),
    val searchQuery: String = "",
    val error: String? = null
) {
    val displayedTopics get() = if (searchQuery.isBlank()) topics
        else topics.filter { it.title.contains(searchQuery, true) }
}

sealed class TopicListEvent {
    data class SearchQueryChanged(val query: String) : TopicListEvent()
    data class TopicClicked(val topicId: String) : TopicListEvent()
    data object RefreshClicked : TopicListEvent()
}

sealed class TopicListEffect {
    data class NavigateToTopic(val topicId: String) : TopicListEffect()
    data class ShowError(val message: String) : TopicListEffect()
}

class TopicListViewModel(
    private val getTopics: GetAllTopicsUseCase,
    private val observeProgress: ObserveProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TopicListUiState())
    val uiState: StateFlow<TopicListUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<TopicListEffect>()
    val effects: SharedFlow<TopicListEffect> = _effects.asSharedFlow()

    init {
        loadData()
        observeProgress()
    }

    fun onEvent(event: TopicListEvent) {
        when (event) {
            is TopicListEvent.SearchQueryChanged ->
                _uiState.update { it.copy(searchQuery = event.query) }
            is TopicListEvent.TopicClicked -> navigateToTopic(event.topicId)
            TopicListEvent.RefreshClicked -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val topics = getTopics()
                _uiState.update { it.copy(isLoading = false, topics = topics) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                _effects.emit(TopicListEffect.ShowError(e.message ?: "Error"))
            }
        }
    }

    private fun observeProgress() {
        viewModelScope.launch {
            observeProgress().collect { progress ->
                _uiState.update { it.copy(progress = progress) }
            }
        }
    }

    private fun navigateToTopic(topicId: String) {
        viewModelScope.launch {
            _effects.emit(TopicListEffect.NavigateToTopic(topicId))
        }
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "State flows down; events flow up — one-way data flow",
                                "Use sealed classes for discrete states, data classes for complex state",
                                "Separate UI State (ongoing) from UI Effects (one-time)",
                                "StateFlow.update { } is safe for concurrent updates",
                                "Always process events through the ViewModel — never modify state directly from UI",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_mv2_1",
                                    lessonId = "mvvm_2",
                                    text = "In one-way data flow, how should the UI notify the ViewModel of a click?",
                                    options =
                                        listOf(
                                            "Directly modify the ViewModel's StateFlow",
                                            "Call a method or send an event/action to the ViewModel",
                                            "Use a global event bus",
                                            "Modify a shared variable",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Events flow UP from UI to ViewModel via function calls or event objects. The ViewModel then updates state, which flows back DOWN to the UI.",
                                ),
                                Question(
                                    id = "q_mv2_2",
                                    lessonId = "mvvm_2",
                                    text = "Should navigation events be modeled as UI State or UI Effects?",
                                    options =
                                        listOf(
                                            "UI State — the navigation target should always be visible",
                                            "UI Effects — navigation is a one-time action that shouldn't replay",
                                            "Either works equally well",
                                            "Navigation should be handled in the Repository",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Navigation is a one-time action (effect/side effect). If it were in StateFlow, rotating the screen would re-navigate. Use SharedFlow with replay=0 for navigation events.",
                                ),
                                Question(
                                    id = "q_mv2_3",
                                    lessonId = "mvvm_2",
                                    text = "What is a 'Single Source of Truth' in MVVM?",
                                    options =
                                        listOf(
                                            "The database",
                                            "The ViewModel state that the UI observes",
                                            "The user input",
                                            "The network",
                                        ),
                                    correctIndex = 1,
                                    explanation = "The UI should only ever reflect the state provided by the ViewModel. This ensures consistency and makes debugging easier.",
                                ),
                                Question(
                                    id = "q_mv2_4",
                                    lessonId = "mvvm_2",
                                    text = "Which tool is best for modeling complex UI state with multiple mutually exclusive states (e.g., Loading, Success, Error)?",
                                    options = listOf("List", "Int", "Sealed Class/Interface", "String"),
                                    correctIndex = 2,
                                    explanation = "Sealed hierarchies allow you to represent restricted types, ensuring the UI only handles valid states and enabling exhaustive 'when' expressions.",
                                ),
                                Question(
                                    id = "q_mv2_5",
                                    lessonId = "mvvm_2",
                                    text = "What is the benefit of One-Way Data Flow?",
                                    options =
                                        listOf(
                                            "Less code",
                                            "Easier debugging and testing because state changes are predictable and centralized in the ViewModel",
                                            "Faster performance",
                                            "Better looking UI",
                                        ),
                                    correctIndex = 1,
                                    explanation = "UDF provides a clear path for data: events go up, state comes down. This prevents 'state sync' bugs where the UI and ViewModel disagree.",
                                ),
                            ),
                    ),
                ),
        )

    // ─────────────────────────────────────────────
    // TOPIC 7 — Dependency Injection
    // ─────────────────────────────────────────────
    private fun buildDependencyInjectionTopic() =
        Topic(
            id = "dependency_injection",
            title = "Dependency Injection",
            description = "Decouple your code with DI. From manual DI to Hilt — the modern standard.",
            emoji = "🔧",
            colorHex = "#1A237E",
            secondaryColorHex = "#9FA8DA",
            difficulty = Difficulty.ADVANCED,
            estimatedMinutes = 55,
            lessons =
                listOf(
                    Lesson(
                        id = "di_1",
                        topicId = "dependency_injection",
                        order = 1,
                        title = "DI Fundamentals & Manual DI",
                        summary = "Understand why DI matters and how to do it manually before using a framework.",
                        content =
                            """
## What is Dependency Injection?

DI is about **providing dependencies from outside** a class, rather than creating them inside.

```kotlin
// ❌ Without DI — tightly coupled
class UserViewModel {
    private val repo = UserRepositoryImpl(RetrofitClient.api, AppDatabase.userDao())
    // UserViewModel knows about Retrofit and Room!
}

// ✅ With DI — loosely coupled
class UserViewModel(private val repo: UserRepository) {
    // UserViewModel only knows about the UserRepository interface
}
```

## Benefits of DI

1. **Testability** — swap real dependencies with fakes in tests
2. **Flexibility** — change implementations without modifying consumers
3. **Decoupling** — classes don't know how to build their own dependencies
4. **Reusability** — single dependency instances (singletons) shared across app

## Manual DI — AppContainer Pattern

The simplest form of DI: build and hold all dependencies in an Application class:

```kotlin
class AppContainer(context: Context) {
    // Singletons — created once, shared
    val database: AppDatabase = AppDatabase.create(context)
    val api: Api = RetrofitFactory.create()

    // Per-request objects
    val userRepo: UserRepository = UserRepositoryImpl(api, database.userDao())
    val getUserUseCase = GetUserUseCase(userRepo)

    // ViewModel factory
    fun userViewModelFactory() = viewModelFactory {
        initializer { UserViewModel(getUserUseCase) }
    }
}

class MyApp : Application() {
    val container by lazy { AppContainer(this) }
}
```

## Using Manual DI in Activity/Compose

```kotlin
@Composable
fun UserScreen() {
    val app = LocalContext.current.applicationContext as MyApp
    val vm: UserViewModel = viewModel(factory = app.container.userViewModelFactory())
    // ...
}
```
                            """.trimIndent(),
                        codeExample =
                            """
// Manual DI example — clean and educational
interface AnalyticsService {
    fun trackScreen(name: String)
    fun trackEvent(name: String, params: Map<String, String> = emptyMap())
}

// Real implementation (for production)
class FirebaseAnalyticsService : AnalyticsService {
    override fun trackScreen(name: String) { /* Firebase call */ }
    override fun trackEvent(name: String, params: Map<String, String>) { /* Firebase call */ }
}

// Fake implementation (for testing)
class FakeAnalyticsService : AnalyticsService {
    val trackedScreens = mutableListOf<String>()
    val trackedEvents = mutableListOf<String>()
    override fun trackScreen(name: String) { trackedScreens.add(name) }
    override fun trackEvent(name: String, params: Map<String, String>) { trackedEvents.add(name) }
}

// ViewModel doesn't care which implementation it gets
class ArticleViewModel(
    private val getArticles: GetArticlesUseCase,
    private val analytics: AnalyticsService
) : ViewModel() {
    init {
        analytics.trackScreen("Articles")
    }
}

// AppContainer provides the real implementation
class AppContainer(context: Context) {
    val analytics: AnalyticsService = FirebaseAnalyticsService()
    val articleRepo = ArticleRepositoryImpl(...)
    val getArticles = GetArticlesUseCase(articleRepo)

    fun articleViewModelFactory() = viewModelFactory {
        initializer { ArticleViewModel(getArticles, analytics) }
    }
}

// Test can inject FakeAnalyticsService
val testVm = ArticleViewModel(getArticles, FakeAnalyticsService())
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "DI = provide dependencies from outside, don't create them inside",
                                "Depend on interfaces (abstractions), not concrete implementations",
                                "AppContainer is a simple, effective manual DI approach",
                                "DI makes unit testing easy — just inject fake/mock dependencies",
                                "Singletons in AppContainer ensure only one instance exists app-wide",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_di1_1",
                                    lessonId = "di_1",
                                    text = "What is the key benefit of depending on an interface instead of a concrete class?",
                                    options =
                                        listOf(
                                            "Interfaces are faster",
                                            "You can swap implementations without changing the dependent class — great for testing",
                                            "Interfaces use less memory",
                                            "Concrete classes don't compile with Hilt",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Depending on interfaces lets you inject different implementations: a real one in production, a fake one in tests — without modifying the class.",
                                ),
                                Question(
                                    id = "q_di1_2",
                                    lessonId = "di_1",
                                    text = "What is 'Constructor Injection'?",
                                    options =
                                        listOf(
                                            "Creating dependencies in the constructor",
                                            "Passing dependencies as parameters to the constructor",
                                            "Inheriting from a base class",
                                            "Using a static factory",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Constructor injection is the most common form of DI, where a class explicitly states its requirements in its constructor.",
                                ),
                                Question(
                                    id = "q_di1_3",
                                    lessonId = "di_1",
                                    text = "In the AppContainer pattern, where does the container usually live?",
                                    options =
                                        listOf(
                                            "In the Activity",
                                            "In the ViewModel",
                                            "In the Application class",
                                            "In a global static variable",
                                        ),
                                    correctIndex = 2,
                                    explanation = "The Application class lives as long as the process, making it the perfect place to hold the app's global dependencies.",
                                ),
                                Question(
                                    id = "q_di1_4",
                                    lessonId = "di_1",
                                    text = "Why does DI improve testability?",
                                    options =
                                        listOf(
                                            "It makes code run faster",
                                            "It allows you to inject mocks or fakes instead of real network/database implementations",
                                            "It's required for JUnit",
                                            "It reduces the number of lines of code",
                                        ),
                                    correctIndex = 1,
                                    explanation = "By injecting dependencies, you can provide controlled 'fakes' in unit tests, ensuring you only test the class in isolation.",
                                ),
                                Question(
                                    id = "q_di1_5",
                                    lessonId = "di_1",
                                    text = "What does it mean for a class to be 'decoupled'?",
                                    options =
                                        listOf(
                                            "It has no dependencies",
                                            "It doesn't know the internal implementation details of its dependencies",
                                            "It is in a separate module",
                                            "It is a private class",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Decoupling ensures that changes in one class (e.g., changing from Retrofit to Ktor) don't force changes in other classes.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "di_2",
                        topicId = "dependency_injection",
                        order = 2,
                        title = "Hilt — Dependency Injection on Android",
                        summary = "Learn how Hilt automates DI with annotations and compile-time validation.",
                        content =
                            """
## What is Hilt?

Hilt is Google's recommended DI framework for Android, built on top of Dagger. It eliminates boilerplate while providing compile-time safety.

## Setup

```kotlin
// build.gradle.kts
plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.52")
    ksp("com.google.dagger:hilt-android-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
}
```

```kotlin
// Application class
@HiltAndroidApp
class MyApp : Application()
```

## Key Annotations

### @Inject — Mark constructor for injection

```kotlin
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val db: UserDao
) : UserRepository
```

### @HiltViewModel

```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel()
```

```kotlin
// In Composable — automatically injected!
@Composable
fun UserScreen() {
    val vm: UserViewModel = hiltViewModel()
}
```

### @Module & @Provides

For types you don't own (Retrofit, Room, etc.):

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)
}
```

### @Binds — Bind interface to implementation

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
```

## Scopes

| Annotation | Scope | Lifetime |
|---|---|---|
| `@Singleton` | SingletonComponent | App lifetime |
| `@ActivityRetainedScoped` | ActivityRetainedComponent | ViewModel lifetime |
| `@ViewModelScoped` | ViewModelComponent | ViewModel lifetime |
| `@ActivityScoped` | ActivityComponent | Activity lifetime |
                            """.trimIndent(),
                        codeExample =
                            """
// Complete Hilt setup example

// 1. Application
@HiltAndroidApp
class MyApplication : Application()

// 2. Network Module
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
}

// 3. Repository binding
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindUserRepo(impl: UserRepositoryImpl): UserRepository
}

// 4. Repository implementation
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val dao: UserDao
) : UserRepository { /* ... */ }

// 5. Use Case
class GetUserUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke(id: String) = repo.getUser(id)
}

// 6. ViewModel — zero boilerplate factory!
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUser: GetUserUseCase
) : ViewModel() {
    // Use the use case
}

// 7. Composable — just one line
@Composable
fun UserScreen(vm: UserViewModel = hiltViewModel()) {
    val state by vm.uiState.collectAsStateWithLifecycle()
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "@HiltAndroidApp on Application is required to initialize Hilt",
                                "@HiltViewModel + hiltViewModel() gives you DI in Composables for free",
                                "@Module + @Provides for types you don't own; @Binds for interface-to-impl",
                                "@Singleton ensures one instance app-wide; scope annotations control lifetime",
                                "Hilt validates the whole dependency graph at compile time — no runtime crashes",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_di2_1",
                                    lessonId = "di_2",
                                    text = "What annotation do you need on a ViewModel to make Hilt inject into it?",
                                    options =
                                        listOf(
                                            "@AndroidEntryPoint",
                                            "@HiltViewModel",
                                            "@Singleton",
                                            "@Inject on the class",
                                        ),
                                    correctIndex = 1,
                                    explanation = "@HiltViewModel marks the ViewModel for Hilt injection. Combined with @Inject constructor, Hilt generates the ViewModelFactory automatically.",
                                ),
                                Question(
                                    id = "q_di2_2",
                                    lessonId = "di_2",
                                    text = "When should you use @Binds vs @Provides?",
                                    options =
                                        listOf(
                                            "They are interchangeable",
                                            "@Binds binds an interface to its implementation; @Provides creates instances of types you don't own",
                                            "@Provides is only for singletons",
                                            "@Binds is for ViewModels; @Provides for repositories",
                                        ),
                                    correctIndex = 1,
                                    explanation = "@Binds is efficient (generates no code) and used to bind an interface to its implementation. @Provides is used when you need to call a constructor or builder (like Retrofit.Builder()).",
                                ),
                                Question(
                                    id = "q_di2_3",
                                    lessonId = "di_2",
                                    text = "What is the purpose of `@HiltAndroidApp`?",
                                    options =
                                        listOf(
                                            "It makes the app faster",
                                            "It triggers Hilt code generation and provides the base dependency container",
                                            "It marks an Activity for injection",
                                            "It is used for database migrations",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Every app using Hilt must have an Application class annotated with @HiltAndroidApp to kick off the dependency graph.",
                                ),
                                Question(
                                    id = "q_di2_4",
                                    lessonId = "di_2",
                                    text = "Which annotation is used to inject dependencies into an Activity or Fragment?",
                                    options = listOf("@Inject", "@HiltViewModel", "@AndroidEntryPoint", "@Module"),
                                    correctIndex = 2,
                                    explanation = "@AndroidEntryPoint enables Hilt injection in Android components like Activities, Fragments, Views, and Services.",
                                ),
                                Question(
                                    id = "q_di2_5",
                                    lessonId = "di_2",
                                    text = "What does the `@Singleton` scope do?",
                                    options =
                                        listOf(
                                            "Ensures only one instance of the dependency exists for the entire app lifetime",
                                            "Creates a new instance for every Activity",
                                            "Only works with the Application class",
                                            "Prevents the class from being garbage collected",
                                        ),
                                    correctIndex = 0,
                                    explanation = "A @Singleton dependency is created once in the SingletonComponent and shared everywhere it's injected.",
                                ),
                            ),
                    ),
                ),
        )

    // ─────────────────────────────────────────────
    // TOPIC 8 — Room Database
    // ─────────────────────────────────────────────
    private fun buildRoomDatabaseTopic() =
        Topic(
            id = "room_database",
            title = "Room Database",
            description = "Persist data locally with Room — Android's SQLite abstraction library.",
            emoji = "🗄️",
            colorHex = "#4E342E",
            secondaryColorHex = "#A1887F",
            difficulty = Difficulty.INTERMEDIATE,
            estimatedMinutes = 50,
            lessons =
                listOf(
                    Lesson(
                        id = "room_1",
                        topicId = "room_database",
                        order = 1,
                        title = "Entities, DAOs & Database",
                        summary = "Learn the three core components of Room: @Entity, @Dao, and @Database.",
                        content =
                            """
## Room Components

Room has three main annotations:

### @Entity — The Table
Maps a Kotlin class to a SQLite table:

```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "display_name") val name: String,
    val email: String,
    @ColumnInfo(defaultValue = "0") val createdAt: Long = System.currentTimeMillis()
)
```

### @Dao — Data Access Object
Interface with query methods:

```kotlin
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users ORDER BY display_name ASC")
    fun observeAllUsers(): Flow<List<UserEntity>>  // Flow = reactive!

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
```

### @Database — The Database
Ties entities and DAOs together:

```kotlin
@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = instance ?: synchronized(this) {
            Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
                .also { instance = it }
        }
    }
}
```
                            """.trimIndent(),
                        codeExample =
                            """
// Complete Room setup for a Notes app

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val isPinned: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val color: String = "#FFFFFF"
)

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity): Long  // returns new row ID

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("SELECT * FROM notes ORDER BY isPinned DESC, createdAt DESC")
    fun observeAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    @Query("UPDATE notes SET isPinned = :pinned WHERE id = :id")
    suspend fun updatePinnedStatus(id: Int, pinned: Boolean)
}

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "@Entity maps a class to a database table",
                                "@Dao methods with @Insert, @Update, @Delete, @Query handle all CRUD",
                                "Return Flow<T> from @Query for reactive, auto-updating queries",
                                "@PrimaryKey(autoGenerate = true) creates an auto-incrementing ID",
                                "Use @ColumnInfo to customize column names",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_rm1_1",
                                    lessonId = "room_1",
                                    text = "What return type should a @Query method use for reactive (auto-updating) results?",
                                    options =
                                        listOf(
                                            "List<T>",
                                            "LiveData<T> only",
                                            "Flow<T>",
                                            "Deferred<T>",
                                        ),
                                    correctIndex = 2,
                                    explanation = "Returning Flow<T> from a @Query method makes it reactive — it automatically re-emits whenever the underlying data changes.",
                                ),
                                Question(
                                    id = "q_rm1_2",
                                    lessonId = "room_1",
                                    text = "What does `OnConflictStrategy.REPLACE` do in @Insert?",
                                    options =
                                        listOf(
                                            "Throws an exception on duplicate keys",
                                            "Ignores the duplicate insert",
                                            "Deletes the old row and inserts the new one",
                                            "Merges the old and new data",
                                        ),
                                    correctIndex = 2,
                                    explanation = "REPLACE removes the conflicting row and inserts the new one — effectively an upsert (insert or update).",
                                ),
                                Question(
                                    id = "q_rm1_3",
                                    lessonId = "room_1",
                                    text = "Which annotation marks a class as a database table in Room?",
                                    options = listOf("@Table", "@Database", "@Entity", "@Model"),
                                    correctIndex = 2,
                                    explanation = "The @Entity annotation tells Room to create a table corresponding to that data class.",
                                ),
                                Question(
                                    id = "q_rm1_4",
                                    lessonId = "room_1",
                                    text = "What is a 'DAO' in Room?",
                                    options =
                                        listOf(
                                            "Database Access Object — an interface for defining database queries",
                                            "Data Android Object",
                                            "A background thread",
                                            "A type of migration",
                                        ),
                                    correctIndex = 0,
                                    explanation = "DAOs (Data Access Objects) are the main component of Room that defines the methods for accessing the database.",
                                ),
                                Question(
                                    id = "q_rm1_5",
                                    lessonId = "room_1",
                                    text = "Why should DAO methods for writing data (Insert, Update, Delete) be `suspend` functions?",
                                    options =
                                        listOf(
                                            "They shouldn't be",
                                            "To ensure they run on the Main thread",
                                            "To prevent blocking the Main thread, since database I/O is slow",
                                            "To make them serializable",
                                        ),
                                    correctIndex = 2,
                                    explanation = "Database operations are slow and could cause ANRs if run on the Main thread. Room makes it easy to run them on background threads using coroutines.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "room_2",
                        topicId = "room_database",
                        order = 2,
                        title = "Migrations & Type Converters",
                        summary = "Handle database schema changes safely and store complex types.",
                        content =
                            """
## Database Migrations

When you change the database schema, you must provide a Migration to preserve user data:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add a new column
        database.execSQL("ALTER TABLE users ADD COLUMN avatar_url TEXT DEFAULT NULL")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create a new table
        database.execSQL(${"\"\"\""}
            CREATE TABLE IF NOT EXISTS `tags` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL
            )
        ${"\"\"\""})
    }
}

Room.databaseBuilder(context, AppDatabase::class.java, "db")
    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
    .build()
```

> **Never use `fallbackToDestructiveMigration()` in production** — it deletes all user data!

## Type Converters

Room only stores primitive types. For custom types, use `@TypeConverter`:

```kotlin
class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = value.joinToString(",")

    @TypeConverter
    fun toStringList(value: String): List<String> =
        if (value.isBlank()) emptyList() else value.split(",")

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}

@Database(entities = [...], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase()
```

## Room with Repository Pattern

```kotlin
class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.observeAllNotes()
        .map { entities -> entities.map { it.toDomain() } }

    suspend fun saveNote(note: Note) = noteDao.insert(note.toEntity())
    suspend fun deleteNote(note: Note) = noteDao.delete(note.toEntity())
}

// Mapper functions
fun NoteEntity.toDomain() = Note(id, title, content, isPinned)
fun Note.toEntity() = NoteEntity(id, title, content, isPinned)
```
                            """.trimIndent(),
                        codeExample =
                            """
// Full repository with Room + Flows + mapping
class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override fun observeNotes(): Flow<List<Note>> =
        dao.observeAllNotes().map { entities -> entities.map(NoteEntity::toDomain) }

    override fun searchNotes(query: String): Flow<List<Note>> =
        dao.searchNotes(query).map { entities -> entities.map(NoteEntity::toDomain) }

    override suspend fun saveNote(note: Note): Long =
        dao.insert(note.toEntity())

    override suspend fun updateNote(note: Note) =
        dao.update(note.toEntity())

    override suspend fun deleteNote(note: Note) =
        dao.delete(note.toEntity())

    override suspend fun getNoteById(id: Int): Note? =
        dao.getNoteById(id)?.toDomain()
}

// ViewModel using the repository
class NotesViewModel(private val repo: NoteRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")

    val notes: StateFlow<List<Note>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) repo.observeNotes()
            else repo.searchNotes(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveNote(title: String, content: String) {
        viewModelScope.launch {
            repo.saveNote(Note(title = title, content = content))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { repo.deleteNote(note) }
    }

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Always write Migration objects when changing the schema — never destroy user data",
                                "TypeConverters let you store complex types (lists, dates, enums)",
                                "Map Room entities to domain models in the repository layer",
                                "Room + Flow = reactive, auto-updating data without polling",
                                "Never run Room operations on the Main thread — use suspend functions or Flow",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_rm2_1",
                                    lessonId = "room_2",
                                    text = "Why should you avoid `fallbackToDestructiveMigration()` in production?",
                                    options =
                                        listOf(
                                            "It makes the app slower",
                                            "It deletes all user data when the database version changes",
                                            "It breaks Room's reactive Flow feature",
                                            "It doesn't work on Android 10+",
                                        ),
                                    correctIndex = 1,
                                    explanation = "fallbackToDestructiveMigration drops and recreates the database when Room can't find a migration, permanently deleting all user data.",
                                ),
                                Question(
                                    id = "q_rm2_2",
                                    lessonId = "room_2",
                                    text = "What is the purpose of a TypeConverter in Room?",
                                    options =
                                        listOf(
                                            "To convert between different database types",
                                            "To convert complex Kotlin types (lists, custom classes) to/from SQLite-supported types",
                                            "To migrate data between tables",
                                            "To convert Room entities to domain models",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Room can only store primitives. TypeConverters let you serialize complex types (like List<String> or Date) to/from a type Room understands (String, Long).",
                                ),
                                Question(
                                    id = "q_rm2_3",
                                    lessonId = "room_2",
                                    text = "What must you do when adding a new column to an existing @Entity?",
                                    options =
                                        listOf(
                                            "Nothing, Room handles it",
                                            "Increment the database version and provide a Migration object",
                                            "Reinstall the app",
                                            "Change the DAO interface",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Changing the schema requires a version bump and an explicit migration path to avoid data loss.",
                                ),
                                Question(
                                    id = "q_rm2_4",
                                    lessonId = "room_2",
                                    text = "Where is the best place to map Room entities to Domain models?",
                                    options = listOf("In the DAO", "In the Repository implementation", "In the ViewModel", "In the UI"),
                                    correctIndex = 1,
                                    explanation = "The Repository layer acts as a boundary, ensuring that the rest of the app (Domain and UI) only deals with pure domain models, not database-specific entities.",
                                ),
                                Question(
                                    id = "q_rm2_5",
                                    lessonId = "room_2",
                                    text = "Can you use multiple TypeConverters in one database?",
                                    options =
                                        listOf(
                                            "No, only one is allowed",
                                            "Yes, by listing them in the @TypeConverters annotation",
                                            "Only in separate tables",
                                            "Only for Strings",
                                        ),
                                    correctIndex = 1,
                                    explanation = "You can provide a class containing as many @TypeConverter methods as you need to handle all your custom types.",
                                ),
                            ),
                    ),
                ),
        )

    // ─────────────────────────────────────────────
    // TOPIC 9 — Testing & Quality
    // ─────────────────────────────────────────────
    private fun buildTestingTopic() =
        Topic(
            id = "testing_quality",
            title = "Testing & Quality",
            description = "Ensure your app works as expected. Master Unit tests, UI tests, and mocking.",
            emoji = "🧪",
            colorHex = "#00838F",
            secondaryColorHex = "#4DD0E1",
            difficulty = Difficulty.ADVANCED,
            estimatedMinutes = 60,
            lessons =
                listOf(
                    Lesson(
                        id = "test_1",
                        topicId = "testing_quality",
                        order = 1,
                        title = "Unit Testing & Mocking",
                        summary = "Learn how to test your business logic in isolation using JUnit and MockK.",
                        content =
                            """
## Why Unit Test?

Unit tests verify small, isolated pieces of code (functions, classes) in seconds. They catch regressions early and serve as documentation.

## MockK — The Modern Mocking Library

In Clean Architecture, we mock the **Repository** to test the **Use Case**, or mock **Use Cases** to test the **ViewModel**.

```kotlin
val repo = mockk<UserRepository>()
val useCase = GetUserUseCase(repo)

// Stubbing
coEvery { repo.getUser("1") } returns User("1", "Alice")

// Assertion
val result = useCase("1")
assertEquals("Alice", result.name)
```

## Testing Coroutines

Use `runTest` to skip delays and `StandardTestDispatcher` to control time:

```kotlin
@Test
fun `load data updates state`() = runTest {
    viewModel.load()
    advanceUntilIdle() // process all coroutines
    assertEquals(State.Success, viewModel.uiState.value)
}
```

## The Main Dispatcher Rule

In tests, `Dispatchers.Main` is not available. You must swap it using a JUnit Rule or @Before block:

```kotlin
@Before
fun setup() {
    Dispatchers.setMain(StandardTestDispatcher())
}
```
                            """.trimIndent(),
                        codeExample =
                            """
// Typical ViewModel Test
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getAllTopics = mockk<GetAllTopicsUseCase>()
    private val observeProgress = mockk<ObserveProgressUseCase>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Default stubs
        every { getAllTopics() } returns emptyList()
        every { observeProgress() } returns flowOf(UserProgress())
        
        viewModel = HomeViewModel(getAllTopics, observeProgress)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search query updates correctly`() = runTest {
        viewModel.onSearchQueryChange("Compose")
        assertEquals("Compose", viewModel.uiState.value.searchQuery)
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "Mock dependencies to test classes in isolation",
                                "Use MockK for modern, idiomatic Kotlin mocking",
                                "runTest allows testing suspend functions without real-world delays",
                                "Swap Dispatchers.Main with a TestDispatcher in unit tests",
                                "Unit tests should be fast, stable, and have zero Android dependencies",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_ts1_1",
                                    lessonId = "test_1",
                                    text = "What is the purpose of mocking a dependency?",
                                    options =
                                        listOf(
                                            "To make the code run faster",
                                            "To replace a real dependency (like a network API) with a controlled object for testing",
                                            "To save memory",
                                            "To bypass Hilt",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Mocking allows you to isolate the unit being tested by controlling the behavior of its dependencies.",
                                ),
                                Question(
                                    id = "q_ts1_2",
                                    lessonId = "test_1",
                                    text = "Which Coroutine builder should you use in unit tests?",
                                    options = listOf("launch", "async", "runTest", "runBlocking"),
                                    correctIndex = 2,
                                    explanation = "runTest is specifically designed for testing; it automatically skips delay() and provides control over virtual time.",
                                ),
                                Question(
                                    id = "q_ts1_3",
                                    lessonId = "test_1",
                                    text = "Why do we need to call `Dispatchers.setMain` in unit tests?",
                                    options =
                                        listOf(
                                            "Because the Android Main thread doesn't exist in local JUnit tests",
                                            "To make tests multi-threaded",
                                            "To use the GPU",
                                            "To enable Room database",
                                        ),
                                    correctIndex = 0,
                                    explanation = "Local tests run on the JVM, not an Android device. There is no Looper.getMainLooper(), so we must provide a test dispatcher.",
                                ),
                            ),
                    ),
                    Lesson(
                        id = "test_2",
                        topicId = "testing_quality",
                        order = 2,
                        title = "Compose UI Testing",
                        summary = "Verify your UI behavior with the Compose Testing library.",
                        content =
                            """
## Semantics — The Bridge to UI

Compose uses **Semantics** (metadata about UI elements) to find and interact with nodes.

## Finding Nodes

- `onNodeWithText("Login")`
- `onNodeWithContentDescription("Back")`
- `onNodeWithTag("search_field")`

## Performing Actions

```kotlin
composeTestRule.onNodeWithText("Search").performTextInput("Compose")
composeTestRule.onNodeWithText("Submit").performClick()
```

## Assertions

```kotlin
composeTestRule.onNodeWithText("Success").assertIsDisplayed()
composeTestRule.onNodeWithTag("error_icon").assertDoesNotExist()
```

## The Compose Test Rule

```kotlin
@get:Rule
val composeTestRule = createAndroidComposeRule<MainActivity>()
// or for isolated component tests:
val composeTestRule = createComposeRule()
```
                            """.trimIndent(),
                        codeExample =
                            """
// UI Test for Navigation
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigateToProgress() {
        // Find icon by content description and click
        composeTestRule
            .onNodeWithContentDescription("Progress")
            .performClick()

        // Verify destination screen title exists
        composeTestRule
            .onNodeWithText("My Progress")
            .assertIsDisplayed()
    }

    @Test
    fun testSearchFiltering() {
        val query = "Coroutines"
        
        // Input text into search field
        composeTestRule
            .onNodeWithText("Search topics...")
            .performTextInput(query)

        // Verify filtered result is shown
        composeTestRule
            .onNodeWithText("Kotlin Coroutines")
            .assertExists()
            
        // Verify unrelated result is hidden
        composeTestRule
            .onNodeWithText("Room Database")
            .assertDoesNotExist()
    }
}
                            """.trimIndent(),
                        keyPoints =
                            listOf(
                                "UI tests use Semantics to find and interact with Composables",
                                "Use contentDescription for icons to make them accessible and testable",
                                "createComposeRule() is for testing isolated components; createAndroidComposeRule<T>() for Activities",
                                "UI tests run on an emulator or real device (androidTest folder)",
                                "Verify both 'Happy Path' and 'Error States' in your UI tests",
                            ),
                        questions =
                            listOf(
                                Question(
                                    id = "q_ts2_1",
                                    lessonId = "test_2",
                                    text = "Which function is used to find a Composable based on its visible text?",
                                    options =
                                        listOf(
                                            "findViewByText()",
                                            "onNodeWithText()",
                                            "onNodeWithTag()",
                                            "getComposable()",
                                        ),
                                    correctIndex = 1,
                                    explanation = "onNodeWithText is the most common way to find UI elements in Compose tests.",
                                ),
                                Question(
                                    id = "q_ts2_2",
                                    lessonId = "test_2",
                                    text = "Where should UI tests (instrumented tests) be located in your project?",
                                    options =
                                        listOf(
                                            "src/test/java",
                                            "src/main/java",
                                            "src/androidTest/java",
                                            "src/debug/java",
                                        ),
                                    correctIndex = 2,
                                    explanation = "androidTest is the standard folder for tests that require a real Android device or emulator to run.",
                                ),
                                Question(
                                    id = "q_ts2_3",
                                    lessonId = "test_2",
                                    text = "Why is `contentDescription` important for testing?",
                                    options =
                                        listOf(
                                            "It improves performance",
                                            "It allows finding elements that have no text (like icons) and improves accessibility",
                                            "It sets the font size",
                                            "It is required by the compiler",
                                        ),
                                    correctIndex = 1,
                                    explanation = "Icons have no text, so contentDescription provides the necessary semantic information for both Screen Readers and UI tests.",
                                ),
                            ),
                    ),
                ),
        )
}
