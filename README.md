# Mobile Development EntertainMe

## Overview
This is a repository storing the mobile development portion of the EntertainMe project. The application is an entertainment recommendation app that was created as part of the requirement to graduate from Bangkit Academy 2024 as a Mobile Development Learning path.

## TODO
- [x] Design the application UI/UX using figma
- [x] Application Layout Design Implementation (XML)
- [x] Importing the required libraries
- [x] Establish a connection to REST API
- [x] Authentication features using Firebase Auth
- [x] Implementation of Machine Learning Model through API
- [x] Implementation of Google Maps API
- [x] Implementation of Firebase Realtime Database to store user data

## Features
- Get Books, Movies, and Travel Recommendations based on user's survey
- Get current stress level according to latest user's stress survey
- Schedule activities to do, marked them as done, delete completed activities and filter activities based on categories

## Installation Instructions

### Prerequisites
1. **Android Studio**: Ensure you have the latest version of Android Studio installed.
2. **Firebase Configuration**: You will need to set up a Firebase project and download the `google-services.json` file.

### Steps

1. **Fork the Repository**
   - Click the `Fork` button in the top right corner of the page to create a copy of the repository in your own GitHub account.

2. **Clone Repository**
   ```sh
   git clone https://github.com/entertainmeproject/capstone-entertainme-mobiledevelopment.git
   cd capstone-entertainme-mobiledevelopment
   ```
   
3. **Open in Android Studio**
   - Open Android Studio.
   - Select `Open an existing project`.
   - Navigate to the folder where you cloned this repository.
     
4. **Firebase Setup**
   - Open [Firebase Console](https://console.firebase.google.com/).
   - Create or select your Firebase project.
   - Add a new Android app with the package name matching your project.
   - Download the `google-services.json` file and place it in the `app` folder of your project.
     
5. **Sync Project with Gradle Files**
   - In Android Studio, click `File > Sync Project with Gradle Files` to sync the changes.
     
6. **Run the App**
   - Connect an Android device or use an emulator.
   - Click the `Run` button (green play icon) to run the application.

### Troubleshooting
- If you encounter issues with dependencies or build errors, try `Invalidate Caches / Restart` from the `File` menu in Android Studio.
- Ensure you have a stable internet connection when syncing the project for the first time to download all required dependencies.

### Additional Notes
- Ensure `google-services.json` file are not committed to a public repository for security reasons. You can use .gitignore to ensure these files are not committed.
- If you have any questions or issues, feel free to open an issue in this repository.
