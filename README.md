<h1 align="center">CryptoApp</h1></br>

## Screenshots

<p align="center">
<img src="/screenshots/sign_in.png" width="20%"/>
<img src="/screenshots/sign_up.png" width="20%"/>
<img src="/screenshots/detail.png" width="20%"/>
<img src="/screenshots/home.png" width="20%"/>
<img src="/screenshots/favorites.png" width="20%"/>
</p>

## Tech stack & Open-source libraries

- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based
    + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
      and [Flow](https://developer.android.com/kotlin/flow)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) -
  Collection of libraries that help you design robust, testable, and maintainable apps.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an
      action when lifecycle state changes
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn't destroyed on UI changes.
    - [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in data
      layer that contains application data and business logic.
- [Android Hilt](https://developer.android.com/training/dependency-injection/hilt-android) -
  Dependency Injection Library
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
- [Navigation](https://developer.android.com/guide/navigation) Navigation refers to the interactions
  that let users navigate across, into, and back out from the different pieces of content within
  your app.
- [Room Database](https://developer.android.com/training/data-storage/room) Apps that handle
  non-trivial amounts of structured data can benefit greatly from persisting that data locally. The
  most common use case is to cache relevant pieces of data so that when the device cannot access the
  network, the user can still browse that content while they are offline.
- [Firebase Auth & Firestore](https://firebase.google.com/docs/android/setup) Firebase is a mobile
  platform that helps you quickly develop high-quality apps, grow your user base, and earn more
  money.
- [Glide](https://github.com/bumptech/glide) Glide is a fast and efficient open source media
  management and image loading framework for Android that wraps media decoding, memory and disk
  caching, and resource pooling into a simple and easy to use interface.
- [OkHttp](https://square.github.io/okhttp/) An HTTP client that efficiently make network requests
