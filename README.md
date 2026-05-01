# Java Minesweeper (MVC Architecture)

A fully functional, classic Minesweeper game built with Java Swing. This project demonstrates high-level technical agility, clean object-oriented programming (OOP), and efficient algorithm implementation.

## 🚀 Rapid Prototyping
* **Performance Benchmark**: The core game logic and the entire graphical user interface were developed within **30 minutes** to demonstrate technical proficiency and rapid implementation skills.

## 🛠 Features
* **MVC Pattern**: Features a strict separation between game logic (`BoardModel`) and the user interface (`MinesweeperGUI`) for maximum maintainability.
* **First-Click Safety**: Implements a strategic mine generation algorithm that triggers after the first move, ensuring the player never hits a mine on their first click[cite: 4].
* **Recursive Flood-Fill**: Utilizes a recursive algorithm to efficiently reveal all connected empty fields instantly[cite: 4].
* **Dynamic Difficulty**: Includes selectable levels: Easy (9x9), Medium (16x16), and Hard (16x30)[cite: 2].

## 💻 Technical Highlights
* **Custom UI Design**: Styled with a modern, clean color palette (Hex: #E8F0FE, #C0CFD8) to improve user experience and readability[cite: 3].
* **Efficient Adjacency Logic**: Pre-calculates neighbor counts immediately after grid generation to optimize real-time performance[cite: 4].

## 📖 How to Run
1. Open the project in BlueJ or any standard Java IDE.
2. Compile all classes.
3. Run the `main` method in the `Game` class[cite: 2].
