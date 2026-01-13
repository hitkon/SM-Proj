# Project: DM Helper

## Overview
**DM Helper** is an Android application designed to assist Dungeon Masters (DMs) in managing tabletop RPG sessions. It features tools for tracking character stats, managing initiatives, tracking conditions, and organizing character blueprints.

The application is built using **Kotlin** and follows modern Android development practices, including **Room** for local data persistence and **ViewBinding** for UI interaction.

## Key Features
*   **Session Management:** Start and manage game sessions (`SessionActivity`).
*   **Character Library:** View and manage a library of character blueprints (`CharacterLibraryActivity`).
*   **Character Sheets:** Detailed view of character attributes and stats (`CharacterSheetActivity`, `CharacterBlueprintSheetActivity`).
*   **PDF Import:** Parse character data from PDF files to create blueprints (`PdfCharacterParser`), utilizing the `itextg` library.
*   **Condition Tracking:** Visual indicators and logic for various RPG conditions (e.g., blinded, stunned, prone) (`ConditionHelper`).
*   **Portrait Gallery:** Manage character portraits (`PortraitGalleryActivity`).

## Tech Stack
*   **Language:** Kotlin
*   **Platform:** Android (Min SDK: 33, Target SDK: 36)
*   **Build System:** Gradle (Kotlin DSL)
*   **Database:** Room (SQLite abstraction)
    *   Entities: `Character`, `CharacterBlueprint`
    *   Secondary DB: `PortraitDatabase`
*   **UI:** XML Layouts with ViewBinding
*   **Asynchronicity:** Kotlin Coroutines (`lifecycleScope`, `launch`)
*   **External Libraries:**
    *   `com.itextpdf:itextg`: PDF parsing
    *   `com.google.android.flexbox`: Flexible layouts
    *   `com.google.code.gson`: JSON serialization

## Directory Structure
*   `app/src/main/java/com/example/dm_helper/`: Main source code.
    *   `data/`: Data access objects and entities for portraits.
    *   `adapters/`: RecyclerView adapters.
    *   `AppDatabase.kt`: Main Room database configuration.
*   `app/src/main/res/layout/`: XML layout files for activities and items.
*   `app/src/main/res/drawable/`: Drawable resources, including extensive condition icons.
*   `app/src/main/res/raw/`: Raw assets (e.g., sample PDFs).

## Setup & Building
1.  **Prerequisites:**
    *   JDK 11 or higher.
    *   Android Studio.
2.  **Build:**
    ```bash
    ./gradlew build
    ```
3.  **Run Tests:**
    ```bash
    ./gradlew test
    ```

## Development Conventions
*   **Code Style:** Standard Kotlin coding conventions.
*   **Architecture:**
    *   Activities serve as the primary controllers.
    *   Database operations are performed asynchronously using Coroutines.
    *   `Room` is used for all persistence.
*   **Resources:** Condition images are stored in `drawable` with descriptive names (e.g., `blinded.png`).
