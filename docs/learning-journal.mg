# Learning Journal - BizCards (CSE 310 Mobile App Module)

## 07 November 2026 - Session 1: Environment setup and first run

**What I worked on**
- Checked that Android Studio and the Android SDK were installed and up to date.
- Created an Android emulator (Medium Phone, API 30) and tried to run it.
- Created a new project called BizCards using the Empty Activity (Jetpack Compose) template with Kotlin.
- Fixed Gradle sync problems and ran the default app on my own phone (HMD Nokia 1.4).
- Created a docs folder for this learning journal and my time log.

**What I learned**
- A new Android project needs the internet the first time, because Gradle downloads many libraries.
- The emulator can be very slow on my laptop. Running the app on a real phone over USB is a good alternative.
- To run on a real phone, USB debugging must be allowed on the phone, and the phone then shows up in the device box in Android Studio.
- "Project Files" view shows the real folders on disk, while "Android" view shows a simplified version.

**Problems and how I solved them**
- The emulator stayed on a black screen and then a boot screen for a long time. I stopped it and used my own phone instead.
- The Gradle sync failed with "Unknown host dl.google.com". The cause was no internet connection, after a power cut stopped the download. I turned the internet back on, clicked Try Again, and the sync finished.
- The first build also failed with the same host error. After the internet was working again, I ran it again and it succeeded.

**Result**
- The default app built successfully and opened on my phone showing "Hello Android!".

**Next steps**
- Create the time log and set up the GitHub repository.
- Learn Jetpack Compose basics (composables, state, layouts) and start the Deck List screen.