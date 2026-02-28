# Training Project 🏋️

An Android app for managing workouts, developed in my free time as a personal project.

## Description

Training Project is an app for people who work out and want to track their workouts in a simple and effective way. The app provides timing tools and workout plan management, all saved locally on your device.

## Features

### Stopwatch 
- Stopwatch with start/stop functions
- Recording of lap times
- Simple interface to monitor your workout times

### Timer 
- Customizable timer with minute and second selection
- Pause and reset functions
- Sound alert when countdown ends
- Perfect for timed exercises or intervals

### Workout Plans Management 
- Create custom workout plans
- Add and edit exercises for each plan
- For each exercise you can set:
  - Exercise name
  - Number of sets
  - Rest times
  - Execution times
- Delete plans and exercises
- Edit plan names
- All data saved locally with Room Database

## Technologies Used

- **Language**: Kotlin
- **Database**: Room Database (SQLite)
- **UI**: XML Layouts + ViewBinding
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34

## Project Structure

```
app/src/main/java/com/example/trainingproject/
├── Home.kt                    # Main activity with bottom navigation
├── Cronometro.kt             # Stopwatch fragment
├── Timer.kt                  # Timer fragment
├── Schede.kt                 # Workout plans management fragment
├── EserciziScheda.kt         # Exercise list activity
├── AggiungiEsercizio.kt      # Add exercise activity
├── ModificaEsercizio.kt      # Edit exercise activity
├── AdapterListView/          # Custom adapters for lists
├── RoomDatabase/
│   ├── entities/             # Database entities (Scheda, Esercizio)
│   ├── DAO/                  # Data Access Objects
│   └── database/             # Database configuration
└── ViewModels/               # ViewModels for state management
```

## Possible Future Updates

- **Workout Statistics**: Charts and metrics showing workout progress over time
- **Auto Timers**: Automatic timer system to track rest times between exercises without manual input
- **Workout History**: Complete log of completed workouts with dates and performance
- **Export/Import Data**: Ability to export and import workout plans
- **Notifications**: Reminders for scheduled workouts
- **Custom Themes**: Dark mode and color customization

## Requirements

- Android 7.0 (API 24) or higher
- About 10 MB of free space

## 📝 Notes

This is a personal project developed in my free time for learning and personal use. The app doesn't collect any personal data and works completely offline.

All data is saved locally on the user's device using Room Database.
