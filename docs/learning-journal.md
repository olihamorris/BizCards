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

**GitHub setup**
- Created a public GitHub repository called BizCards.
- In Android Studio's terminal I ran git init, git add ., git commit, then connected the repository and pushed with git push -u origin main.
- I made a typing mistake in the journal file name (.mg instead of .md). I will rename it and push the fix.

**Time today**
- Two working blocks: 10:05 to 12:07 and 14:17 to 16:00 (about 3 hrs 45 mins), including waiting for the emulator and the Gradle downloads.

## 07-08 November 2026 - Session 2: Deck List screen

**What I worked on**
- Replaced the default MainActivity.kt with my first real screen, the Deck List screen.
- Created a FlashCard data class holding a term and a definition.
- Built the screen in Jetpack Compose with a title, two text fields (Term and Definition), an "Add card" button, and a scrollable list of cards.
- Tried to run it on my phone, but the phone had stopped appearing in Android Studio.

**What I learned**
- A data class is a simple Kotlin class for holding information, such as a flashcard.
- mutableStateListOf and mutableStateOf let Compose update the screen automatically when the data changes.
- LazyColumn shows a scrollable list, and each item can be drawn as a Card.
- Imports matter: Modifier must be imported from androidx.compose.ui, and a wrong import shows red text in the code.

**Problems and how I solved them**
- The Modifier word showed red because the import was wrong. I changed it to import androidx.compose.ui.Modifier.
- My phone no longer appeared as a device. I checked that File transfer was selected for USB, then used "Revoke USB debugging authorizations" in Developer options, turned USB debugging off and on, and reconnected the cable. The phone then appeared in the Device Manager.
- I opened the TalkBack developer settings by mistake. Developer options on this phone is under Settings > System > Advanced.

**Next steps**
- Run the Deck List screen on my phone and confirm it works.
- Commit and push the code to GitHub.
- Add a second screen and navigation between screens.