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

## 08 November 2026 - Session 3: Study screen and navigation

**What I worked on**
- Ran the Deck List screen on my phone and tested adding a card.
- Added the Navigation library so the app can move between screens.
- Built the Study screen, where a card flips between its term and definition when tapped.
- Added the "I knew it" and "Review again" buttons, a score, and a Finished screen with a "Study again" button.
- Tested every button on my phone, then committed and pushed the code to GitHub.

**What I learned**
- Navigation in Compose uses a NavController and a NavHost. The NavHost lists the screens, and navigate() moves to one.
- State can be shared between two screens by keeping the list of cards in one place (the parent function) and passing it to both screens.
- remember and mutableStateOf keep values such as the current card number, the score and whether the card is flipped. When a value changes, the screen updates itself.
- A new library is added in the dependencies block of the app's build.gradle.kts file, and then the project must be synced with the internet on.

**Problems and how I solved them**
- Red errors appeared for NavHost and composable ("unresolved reference"). The Navigation line was missing from the dependencies block, because I had typed the material3 line twice by mistake. I replaced the extra line with the Navigation dependency and synced again, and the errors disappeared.
- A top-level build.gradle.kts and an app-level build.gradle.kts both exist. The dependencies must go in the one inside the app folder.

**Result**
- Both screens work on my phone. Cards can be added, studied, flipped and scored, and the Back button returns to the card list.

**Next steps**
- Add a Room database so that cards are still there after the app is closed.
- Write the README and record the demo video.
- Submit the module.

## 09 November 2026 - Sessions 4 and 5: Room database

**What I worked on**
- Added the Room database so that flashcards are saved on the phone and stay after the app is closed.
- Added the KSP plugin and the Room libraries to the project's build files and synced the project.
- Created a new file, CardDatabase.kt, with the card table (FlashCard), the DAO (the actions on the table) and the database itself.
- Changed MainActivity.kt so that the screens read their cards from the database and save new cards into it.
- Tested on my phone: I added cards, closed the app completely, opened it again, and the cards were still there.

**What I learned**
- Room saves data in a small database on the phone itself, so it stays when the app is closed or the phone restarts. The data stays on that phone only.
- Three parts make up a Room database: an Entity (the table), a DAO (the actions such as get all cards and insert), and the Database class.
- KSP is a tool that reads labels like @Entity and @Dao and writes the long database code for me.
- A Flow gives the screen the latest list of cards, and the screen updates by itself when a card is saved.
- Database work runs in the background (a coroutine) so that the screen does not freeze.
- A file's package line must match its folder, and duplicated lines can cause red errors.

**Problems and how I solved them**
- After a power cut the project dropped off the recent projects list. I opened it again with Open, chose the BizCards folder and let it import.
- CardDatabase.kt showed red errors because the package line was written twice. I deleted the extra line and the errors disappeared.
- The first build failed with "No such host is known (dl.google.com)" because Room's code generator needed a download. I turned the internet on, ran again, and it succeeded.
- I worked around power cuts by saving often and committing to Git in small steps.

**Result**
- Cards I add (for example Revenue and Coins) are still there after I close and reopen the app.

**Next steps**
- Commit and push the Room version to GitHub.
- Add useful comments to all the code and test everything one more time.
- Write the README, record the demo video, finish the time log and submit.