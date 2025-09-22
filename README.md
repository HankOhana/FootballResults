## Project Title

Football Fixtures App

## Description

Quick sample of multi-module Android application that displays a list of football fixtures using
SportMonks API

## Dependencies

- **Dagger Hilt**: For dependency injection.
- **Retrofit**: For network operations.
- **Room**: For local database storage.
- **Kotlin Coroutines**: For managing background tasks.
- **Jetpack Compose**: For building the UI.
- **Navigation Compose**: For handling navigation in Jetpack Compose.
- **JUnit**: For making unit test case
- **MockK**: For mocking in unit tests.
- **Robolectric**: For running Android tests on the JVM.
- **Hilt Testing**: For testing with Dagger Hilt.
- **Compose Testing**: For testing Jetpack Compose UIs.

## Features

- Displays a list of football fixtures.

## Project Structure

### app Module

The App Layer is the entry point of the application.

- **ui/**
    - `MainActivity.kt`: The main activity that hosts the composable content.
- **navigation/**
    - `NavigationHost.kt`: Sets up the navigation graph for the application.

- **MainApplication.kt**: The application class that initializes Dagger Hilt.

### core Module

The Core Layer contains shared utilities and constants that can be used across different modules of
the application.

- **utils/**
    - `Constants.kt`: Contains application-wide constants such as the API base URL and API key.
    - `FlowExt.kt`: Extension functions for working with Kotlin Flows.
    - `ResultExt.kt`: Extension functions for handling Result types.

### domain Module

The Domain Layer contains the business logic of the application independent of any external
frameworks or libraries.

- **model/**
    - `Fixture.kt`: The domain model representing a fixture.

- **repository/**
    - `FixtureRepository.kt`: The repository interface for working with fixtures.

- **usecase/**
    - `GetFixturesUseCase.kt`: Use case for fetching a list of fixtures.
    - `RefreshFixturesUseCase.kt`: Use case for refreshing the list of fixtures.

### domain-di Module

The Domain Dependency Injection Layer provides the necessary dependencies for the domain layer using
Dagger Hilt.

- **DomainModule.kt**: Provides domain layer dependencies for Dagger Hilt.

### data Module

The Data Layer is responsible for managing data from various sources such as network and local
database.

- **db/**
  - `AppDatabase.kt`: Room database class.
  - `DatabaseModule.kt`: Provides database-related dependencies for Dagger Hilt.
  - **dao/**
    - `FixtureDao.kt`: Data Access Object for fixtures.
  - **entity/**
    - `FixtureCached.kt`: Data class representing the database entity for a fixture.

- **network/**
    - `ApiService.kt`: Retrofit interface for network calls.
    - `NetworkModule.kt`: Provides network-related dependencies for Dagger Hilt.
    - `FixtureApiResponse.kt`: Data class representing the API response for a fixture.

- **repository/**
    - `FixtureRepositoryImpl.kt`: Implementation of the `FixtureRepository` interface.

- **mapper/**
    - `FixtureMapper.kt`: Maps network DTOs to domain models, db entities to domain models and
      vice versa.

- **model/**
    - `FixtureDto.kt`: Data class representing the network DTO for a fixture.

- **di/**
    - `BindsModule.kt`: Binds repository implementations to their interfaces for Dagger Hilt.

### presentation Module

The Presentation Layer is responsible for the UI and user interaction.
It uses ViewModels to manage UI-related data and state.