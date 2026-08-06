# 📱 Android Dev Roadmap

![Android CI](https://github.com/abderhim/Android_Dev_Roadmap/actions/workflows/android_ci.yml/badge.svg)

> **Master modern Android development — one lesson at a time.**

A free, comprehensive Android learning app that teaches you everything you need to become a great Android developer — through structured lessons, real code examples, interactive quizzes, and progress tracking.

---

## ✨ Features

- 📚 **8 in-depth learning topics** covering all modern Android skills
- 💻 **Real code examples** for every lesson — collapsible code blocks
- 🧠 **Interactive quizzes** after each lesson to test your knowledge
- 📊 **Progress tracking** — your progress is saved locally and persists across sessions
- 🔍 **Search topics** from the home screen
- 🎨 **Beautiful Material 3 UI** with gradient topic cards and smooth animations
- 🌙 **Dark/Light theme** support

---

## 📖 What You'll Learn

| Topic | Lessons | Level |
|---|---|---|
| 🎨 Jetpack Compose | 5 lessons | Beginner |
| ✨ Advanced Compose | 3 lessons | Intermediate |
| ⚡ Kotlin Coroutines | 4 lessons | Intermediate |
| 🌊 Kotlin Flows | 3 lessons | Intermediate |
| 🏗️ Clean Architecture | 2 lessons | Advanced |
| 🔄 MVVM & UI State | 2 lessons | Intermediate |
| 🔧 Dependency Injection | 2 lessons | Advanced |
| 🗄️ Room Database | 2 lessons | Intermediate |
| 🧪 Testing & Quality | 2 lessons | Advanced |

### Topics covered in detail:
- **Jetpack Compose** — Composable functions, recomposition, layouts (Column/Row/Box/LazyColumn), state hoisting, Modifiers, side effects (LaunchedEffect, DisposableEffect, rememberCoroutineScope)
- **Advanced Compose** — Animations (animate*AsState, AnimatedVisibility, updateTransition), Navigation Compose, performance tips (derivedStateOf, stability, @Immutable)
- **Kotlin Coroutines** — Suspend functions, coroutine builders (launch/async), Dispatchers, structured concurrency, coroutineScope vs supervisorScope, exception handling
- **Kotlin Flows** — Cold flows, flow builders, operators (map, filter, flatMapLatest, debounce, combine, zip), StateFlow, SharedFlow, stateIn/shareIn
- **Clean Architecture** — Domain/Data/Presentation layers, Repository pattern, Use Cases with operator fun invoke
- **MVVM & UI State** — ViewModel lifecycle, one-way data flow (UDF), UI State vs UI Effects, MutableStateFlow.update
- **Dependency Injection** — Manual DI (AppContainer pattern), Hilt setup, @HiltViewModel, @Module/@Provides/@Binds, scopes
- **Room Database** — @Entity, @Dao, @Database, reactive queries with Flow, migrations, TypeConverters
- **Testing & Quality** — Unit Testing with MockK, Testing Coroutines (runTest), Compose UI Testing, Semantics, Navigation Testing

---

## 🏗️ Architecture

This app is itself built following the principles it teaches:

```
app/
├── domain/                      # Pure Kotlin — zero Android dependencies
│   ├── model/                   # Topic, Lesson, Question, UserProgress
│   ├── repository/              # LearningRepository interface
│   └── usecase/                 # 7 focused use cases
├── data/
│   ├── local/                   # Room Database, DAOs, Entities
│   ├── datasource/              # All lesson content (LearningDataSource)
│   └── repository/              # LearningRepositoryImpl (Hilt injected)
├── presentation/
│   ├── navigation/              # NavHost + Screen routes (Type-Safe)
│   └── screens/
│       ├── home/                # HomeScreen + HomeViewModel
│       ├── topic/               # TopicDetailScreen + TopicViewModel
│       ├── lesson/              # LessonScreen + LessonViewModel
│       ├── quiz/                # QuizScreen + QuizViewModel
│       └── progress/            # ProgressScreen + ProgressViewModel
├── di/                          # Hilt Modules (AppModule)
├── AndroidDevRoadmapApp.kt      # @HiltAndroidApp
└── MainActivity.kt              # @AndroidEntryPoint + WindowSizeClass
```

**Patterns used:**
- ✅ Clean Architecture (Domain → Data → Presentation)
- ✅ MVVM with StateFlow + one-way data flow
- ✅ Repository pattern with interface in Domain layer
- ✅ Use Cases with `operator fun invoke`
- ✅ Dependency Injection via **Hilt**
- ✅ UI State + UI Effects separation (StateFlow + SharedFlow)
- ✅ Type-Safe Navigation (Kotlin Serialization)
- ✅ Offline-First with **Room Database**
- ✅ Adaptive Layouts (supporting Tablets & Foldables)
- ✅ Shared Element Transitions

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Jetpack Compose** | Entire UI |
| **Material 3** | Design system |
| **Navigation Compose** | Type-safe screen navigation |
| **Kotlin Coroutines** | Async operations |
| **Kotlin Flows** | Reactive state management |
| **StateFlow / SharedFlow** | ViewModel → UI communication |
| **Room Database** | Local persistence |
| **Dagger Hilt** | Dependency injection |
| **ViewModel** | Lifecycle-aware state holders |
| **KSP** | Kotlin Symbol Processing (for Room/Hilt) |

---

## 🚀 Getting Started

### Requirements
- Android Studio Ladybug or newer
- JDK 11+
- Android device/emulator running API 24+

### Build & Run
```bash
git clone https://github.com/abderhim/android-dev-roadmap.git
cd android-dev-roadmap
```
Open in Android Studio → **Sync Gradle** → **Run**

---

## 📸 Screenshots

> *Coming soon*

---

## 🤝 Contributing

Contributions are welcome! If you want to:
- Add a new topic or lesson
- Fix a typo or improve an explanation
- Improve the UI

Feel free to open a PR or create an issue.

---

## 📄 License

```
MIT License — free to use, modify, and distribute.
```

---

## 👨‍💻 Author

**Abderrahim Ibn Said**  
Android Developer  
[GitHub](https://github.com/abderhim) · [LinkedIn](https://www.linkedin.com/in/abderrahim-ibn-said/)

---

> ⭐ If this app helped you, consider starring the repo — it helps other developers find it!

