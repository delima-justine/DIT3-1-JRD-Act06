### DIT3-1-JRD-Act06

1. Which API did you choose and why?
- I chose OpenWeatherMap API because of its easy to use and free to use.

2. How did you implement data fetching and JSON parsing?
- Data fetching was implemented using the Retrofit library to handle HTTP requests. I defined the API endpoints in an interface called `OpenWeatherService`. To prevent blocking the main thread, all network operations are performed asynchronously using Kotlin Coroutines on a background thread (`Dispatchers.IO`).
- For JSON parsing, I used the Gson library with Retrofit's `GsonConverterFactory`. This automatically converts the JSON responses from the API into Kotlin data classes (`WeatherResponse`, `GeoResponse`, etc.), making the data easy and safe to work with.

3. What challenges did you face when handling errors or slow connections?
- A key challenge was ensuring the app didn't crash due to network errors or when running network operations on the main thread. I addressed this by:
  - Wrapping all API calls in a `try-catch` block to handle exceptions, like network failures or parsing issues, without crashing the app.
  - Using Kotlin Coroutines with `Dispatchers.IO` to offload network requests from the main thread, which keeps the UI responsive during slow connections.
  - Adding the `INTERNET` permission to the `AndroidManifest.xml` file, which is a common oversight that was causing crashes.

4. How would you improve your app's UI or performance in the future versions?
- **UI Improvements:**
  - Implement a loading indicator, like a `ProgressBar`, to provide visual feedback to the user while data is being fetched.
  - Display user-friendly error messages on the screen using Toasts or Snackbars instead of just logging them.
  - Add an `EditText` field to allow users to input a city of their choice, making the app more interactive.
- **Performance Improvements:**
  - Adopt the MVVM (Model-View-ViewModel) architecture to better separate concerns and manage UI state more effectively, making the code more scalable and testable.
  - Implement a caching strategy to store weather data locally (e.g., using Room). This would reduce redundant API calls and allow the app to display stale data when offline.
