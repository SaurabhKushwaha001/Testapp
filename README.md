# 📺 YT Course Tracker

> An Android application to organize, track, and complete your YouTube-based learning journey — all in one clean, distraction-free interface.

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat&logo=firebase&logoColor=black)](https://firebase.google.com)
[![License](https://img.shields.io/badge/License-Educational-blue)](#license)

---

## 📖 Overview

**YT Course Tracker** helps learners stay organized while studying through YouTube playlists and courses. Users can sign up, browse curated courses, watch videos directly within the app, and track their progress through each course — eliminating the need to bounce between YouTube and a separate notes app or spreadsheet.

---

## ✨ Features

- 🔐 **Secure Authentication** — Sign up, log in, log out, and reset passwords via Firebase Authentication.
- 📚 **Course Browsing** — Explore available courses with thumbnails and descriptions.
- 🎬 **Video Playback** — Watch course videos directly inside the app.
- 📊 **Progress Tracking** — Automatically track completion status for individual videos and entire courses.
- ⚡ **Smooth, Modern UI** — Built entirely with Jetpack Compose for a fast, responsive experience.

---

## 🛠️ Tech Stack

| Category         | Technology                          |
|-------------------|--------------------------------------|
| Language          | Kotlin                              |
| UI Toolkit        | Jetpack Compose                     |
| Architecture      | MVVM (Model-View-ViewModel)         |
| Navigation        | Navigation Compose                  |
| Authentication    | Firebase Authentication             |
| Cloud Database    | Firebase Firestore                  |
| Local Storage     | SQLite                              |
| Networking        | Retrofit                            |

---

## 🚀 Getting Started

Follow these steps to set up the project locally.

### Prerequisites

- Android Studio (latest stable version recommended)
- A Firebase project
- Android device or emulator running API 21+

### 1. Clone the Repository

```bash
git clone https://github.com/SaurabhKushwaha001/YT-cource-tracker.git
```

### 2. Open the Project

1. Launch Android Studio.
2. Select **Open an Existing Project**.
3. Choose the cloned project folder.

### 3. Firebase Setup

1. Create a project in the [Firebase Console](https://console.firebase.google.com/).
2. Register an Android app within your Firebase project.
3. Download the generated `google-services.json` file.
4. Place `google-services.json` inside the `app/` directory.
5. Enable **Email/Password** sign-in under Firebase Authentication.

### 4. Run the Application

1. Sync Gradle files.
2. Connect a physical Android device or start an emulator.
3. Click **Run ▶** in Android Studio.

---
## 📂 Project Structure
 
```
YT-Course-Tracker/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/...        # Kotlin source files (MVVM layers)
│   │   │   ├── res/             # UI resources
│   │   │   └── AndroidManifest.xml
│   ├── google-services.json     # Firebase config (not committed)
│   └── build.gradle
├── build.gradle
└── README.md
```
 
---

## 🗺️ Roadmap

- [ ] Offline video caching
- [ ] Course categories and search
- [ ] Push notifications for course reminders
- [ ] Dark mode support

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 👤 Author

**Saurabh Kushwaha**

---

## 📄 License

This project is developed for **educational purposes** and is free to use, modify, and distribute for learning.
