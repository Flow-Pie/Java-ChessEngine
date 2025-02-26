# Java Chess Engine ♟️🤖

## Overview

The **Java Chess Engine** is a fully functional chess engine built with Java. It allows you to play chess, evaluate positions, and make intelligent decisions through AI. Designed to be modular, efficient, and extendable, this engine is perfect for use in standalone chess games, AI research, or educational purposes.

With advanced features such as move generation, board evaluation, **PGN support**, and a **Swing-based graphical user interface (GUI)**, this engine is a great tool for developers and chess enthusiasts alike. Future updates will introduce AI-based decision-making, allowing you to play against the computer in single-player mode.

---

## Features 🌟

### Current Features
- **Move Generation**: Automatically generates all legal chess moves based on the current board state.
- **Board Evaluation**: Uses heuristic algorithms to evaluate and score board positions.
- **Swing-Based GUI**: A visually appealing and interactive graphical user interface built using Java Swing for playing chess.
- **Chess Engine Core**: Fully functional chess logic, including move validation, check/checkmate detection, and game state management.
- **Two-Player Mode**: Supports two human players playing against each other.
- **PGN Support**: Load and save games in **PGN (Portable Game Notation)** format for easy game sharing and analysis.

### Upcoming Features 🚀
- **AI Player**: Implementation of the **minimax algorithm** with **alpha-beta pruning** for an optimized AI player, enabling single-player mode.
- **Command-Line Interface (CLI)**: A simple and intuitive CLI for interactive gameplay.
- **FEN Notation**: Parses and generates **FEN (Forsyth-Edwards Notation)** strings to represent board states.
- **API Integration**: Exposes a RESTful API to interact with the engine programmatically.
- **Maven Support**: Integration with Maven for better dependency management and project building.

---

## Installation ⚙️

### Prerequisites

Before you begin, ensure that you have the following installed:

- **Java 11 or later** (for running the engine)

### Clone the Repository

Start by cloning the repository to your local machine:

```sh
$ git clone https://github.com/Flow-Pie/Java-ChessEngine.git
$ cd Java-ChessEngine
Running the Application
To run the chess engine with the Swing-based GUI, navigate to the project directory and execute the following command:

sh
Copy code
$ java -cp . com.main.Main
This will launch the graphical user interface, allowing two players to play chess interactively.

Project Structure 🗂️
Here’s an overview of the project structure:

plaintext
Copy code
Java-ChessEngine/
├── Chess.iml
├── out/                          # Compiled class files
│   ├── com/
│   │   ├── main/
│   │   │   ├── Board.class
│   │   │   ├── GamePanel$1.class
│   │   │   ├── GamePanel.class
│   │   │   ├── Main.class
│   │   │   ├── Mouse.class
│   │   │   └── Type.class
│   │   └── piece/
│   │       ├── Bishop.class
│   │       ├── King.class
│   │       ├── Knight.class
│   │       ├── Pawn.class
│   │       ├── Piece.class
│   │       ├── Queen.class
│   │       └── Rook.class
│   └── production/
│       └── Chess/
│           ├── com/
│           │   ├── main/
│           │   │   ├── Board.class
│           │   │   ├── GamePanel.class
│           │   │   ├── Main.class
│           │   │   ├── Mouse.class
│           │   │   └── Type.class
│           │   └── piece/
│           │       ├── Bishop.class
│           │       ├── King.class
│           │       ├── Knight.class
│           │       ├── Pawn.class
│           │       ├── Piece.class
│           │       ├── Queen.class
│           │       └── Rook.class
│           └── piece/
│               ├── b-bishop.png
│               ├── b-king.png
│               ├── b-knight.png
│               ├── b-pawn.png
│               ├── b-queen.png
│               ├── b-rook.png
│               ├── w-bishop.png
│               ├── w-king.png
│               ├── w-knight.png
│               ├── w-pawn.png
│               ├── w-queen.png
│               └── w-rook.png
├── README.md                     # Project documentation
├── res/                          # Resource files (piece images)
│   └── piece/
│       ├── b-bishop.png
│       ├── b-king.png
│       ├── b-knight.png
│       ├── b-pawn.png
│       ├── b-queen.png
│       ├── b-rook.png
│       ├── w-bishop.png
│       ├── w-king.png
│       ├── w-knight.png
│       ├── w-pawn.png
│       ├── w-queen.png
│       └── w-rook.png
├── src/                          # Source code
│   └── com/
│       ├── main/                 # Main application logic
│       │   ├── Board.java        # Board representation and state management
│       │   ├── GamePanel.java    # GUI panel for the chessboard
│       │   ├── Main.java         # Entry point for the application
│       │   ├── Mouse.java        # Mouse event handling for piece movement
│       │   └── Type.java         # Enum for piece types
│       └── piece/                # Chess piece logic
│           ├── Bishop.java       # Bishop piece implementation
│           ├── King.java         # King piece implementation
│           ├── Knight.java       # Knight piece implementation
│           ├── Pawn.java         # Pawn piece implementation
│           ├── Piece.java        # Base class for all chess pieces
│           ├── Queen.java        # Queen piece implementation
│           └── Rook.java         # Rook piece implementation
└── structure.txt                 # File structure overview
Future Enhancements 🌱
AI Integration: Implement AI-based decision-making using advanced algorithms like minimax with alpha-beta pruning, enabling single-player mode.
FEN Support: Add support for parsing and generating FEN strings to represent board states.
RESTful API: Develop a RESTful API to allow programmatic interaction with the chess engine.
Maven Integration: Integrate Maven for better dependency management and project building.
Unit Testing: Add comprehensive unit tests to ensure the reliability and correctness of the engine.
Enhanced GUI Features: Add features like undo/redo, game history, and a settings menu for customization.
Contributing 🤝
Contributions are welcome! If you'd like to contribute to the development of this chess engine, please follow these steps:

Fork the repository.
Create a new branch for your feature or bugfix.
Commit your changes and push them to your fork.
Submit a pull request with a detailed description of your changes.
License 📄
This project is licensed under the MIT License. See the LICENSE file for more details.

Enjoy playing and developing with the Java Chess Engine! Feel free to reach out with any questions or suggestions. Happy coding! 🎉
