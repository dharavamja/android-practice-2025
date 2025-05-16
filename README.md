# android-practice-2025
Single Screen Memory Match Game (Jetpack Compose)
A simple, single-screen memory matching game built with Jetpack Compose. This project serves as a practical example for learning and practicing fundamental Jetpack Compose concepts and building single-screen Android applications.

*Currently Implemented Features*

- Randomized Card Display: Each game, the cards (icons) are shuffled and displayed in a 3x4 grid, with each icon appearing exactly twice.

- Memory Matching Logic:
  - Tap a card to reveal its icon.
  - Tap a second card to attempt a match.
  - Matched pairs remain face up and turn green.
  - Unmatched pairs flip back face down after a short delay (and turn blue).
  - Selected (but not yet matched) cards turn yellow.
  - Turn Counter: Tracks the number of pairs of cards attempted.
  - Reset Button: Starts a new game with a fresh, randomized set of cards and resets the turn count.
  - Undo Functionality: Allows the player to undo their last move (limited to 5 undos per game).
  - Visual Feedback: Utilizes different background colors to indicate the state of each card (matched, selected, or default).
  - Snackbars: Provides feedback messages for actions like successful undo, game reset, and attempting to select already matched or selected cards.

- Technologies Used:
  - Kotlin: The primary programming language for Android development.
  - Jetpack Compose: Android's modern toolkit for building native UI.
  - Material Design Icons: Used for the card images.

*Getting Started:*

*Clone the repository:*
Bash
git clone https://github.com/dharavamja/android-practice-2025.git
Open the project in Android Studio.
Build and run the app on an emulator or a physical Android device.

*How to Play:*

- Tap on a card to reveal the hidden icon.
- Tap on a second card to see if it matches the first.
- If the icons match, they will stay face up (green).
- If they don't match, they will flip back over (blue).
- Continue matching pairs until all cards are face up.
- The "Turn" counter tracks your attempts.
- Use the "Reset" button to start a new game.
- Use the "Undo" button to revert your last move (limited uses).

*Project Structure:*
MainActivity.kt: Contains the main activity and the Composable functions for the game UI and logic.

*Contributing:*
Contributions are welcome! Feel free to open issues for bugs or feature requests, or submit pull requests with improvements.
