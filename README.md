# Weather Forecast   
<img src="https://github.com/user-attachments/assets/453e97d7-05a2-4e4f-a816-353572733bab" width="150" />

**Weather Now & Later** is a modularized Android application that provides current weather information and a 7-day weather forecast for a given city. The app features a clean architecture using MVVM and MVI patterns, implements dependency injection with Dagger-Hilt, and includes a basic CI/CD pipeline. The app also supports dark mode.

## Features

- **City Input Screen**: Users can input a city name to fetch weather data.
- **Current Weather Display**: Displays the current weather for the input city, including temperature, condition (e.g., sunny, cloudy), and an appropriate weather icon.
- **7-Day Forecast**: A 7-day weather forecast is displayed, showing the same details as the current weather.
- **Last Searched City**: The app stores the last searched city and shows its weather data when the app is reopened.
- **Dark Mode Support**: The app supports dark mode for a better user experience in low-light conditions.

## Architecture

The app follows a **Clean Architecture** structure with the following layers:

- **Presentation**: Handles UI components, such as activities, fragments, and composables.
- **Use Cases**: Contains business logic for interacting with repositories.
- **Repository**: Manages data and communicates with remote and local data sources.
- **Data Sources**:
  - **Local**: Stores the last searched city.
  - **Remote**: Fetches weather data from a weather API.

### Architectural Patterns

- **MVVM** (Model-View-ViewModel) for the city input screen and current weather display.
- **MVI** (Model-View-Intent) for the 7-day forecast list.

## Technologies Used

- **Kotlin**
- **Jetpack Compose** for UI
- **MVVM** and **MVI** architectural patterns
- **Dagger-Hilt** for Dependency Injection
- **Retrofit** for API communication
- **Room** for local data storage
- **Coroutines** for background tasks
- **JUnit** for unit testing
- **Mockito** for mocking in unit tests
- **GitHub Actions** for CI/CD

## Setup & Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/HamdyAbdELFatah/WeatherNowLater.git
   ```

2. Open the project in Android Studio.

3. Build and run the app on an emulator or a physical device.

## Modules

The app is modularized into the following modules:

- **App**: Main module containing the application entry point.
- **Core**: Contains common utilities and shared components.
- **Data**: Includes data sources and repository.
- **Features**: Sub-modules for each feature/screen (e.g., city input, current weather, 7-day forecast).

## Unit Testing

Unit tests cover at least 80% of the code. The app uses **JUnit** for testing and **Mockito** for mocking dependencies.

To run the unit tests:

```bash
./gradlew test
```

## CI/CD Pipeline

The project is set up with **GitHub Actions** to automate the following:

- Lint the code.
- Run unit tests.
- Build and generate an APK.

## Bonus Features

- **Instrumented Tests**: The project includes instrumented tests for UI interactions.
- **Dark Mode Support**: The app supports dark mode based on the system theme.

## Custom Library

A custom library has been created to format weather data and display custom weather icons. This library is published to a local Maven repository for easy reuse.

## Contributing

Feel free to fork the repository, open issues, or submit pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
