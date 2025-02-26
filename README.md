```markdown
# Java Chess Engine ♟️🤖

## Overview

The **Java Chess Engine** is a fully functional chess engine built with Java. It enables playing chess, evaluating positions, and making intelligent decisions through AI. Designed to be modular, efficient, and extendable, this engine is ideal for use in standalone chess games, as well as for AI research and educational purposes.

With advanced features like move generation, board evaluation, AI-based decision making, and support for common chess formats, this engine serves as a powerful tool for developers and chess enthusiasts alike.

## Features 🌟

- **Move Generation**: Automatically generates all legal chess moves based on the current board state.
- **Board Evaluation**: Uses heuristic algorithms to evaluate and score board positions for both human and AI players.
- **AI Player**: Implements the **minimax algorithm** with **alpha-beta pruning** for an optimized AI player.
- **Command-Line Interface (CLI)**: A simple and intuitive CLI for interactive gameplay.
- **PGN Support**: Load and save games in **PGN (Portable Game Notation)** format for easy game sharing and analysis.
- **FEN Notation**: Parses and generates **FEN (Forsyth-Edwards Notation)** strings to represent board states.
- **API Integration**: Exposes a RESTful API to interact with the engine programmatically.

## Installation ⚙️

### Prerequisites

Before you begin, ensure that you have the following installed:

- **Java 11 or later** (for running the engine)
- **Maven** (for dependency management and building the project)

### Clone the Repository

Start by cloning the repository to your local machine:

```sh
$ git clone https://github.com/Flow-Pie/Java-ChessEngine.git
$ cd Java-ChessEngine
```

### Build and Run the Project

To compile and run the chess engine, use Maven:

```sh
$ mvn compile
$ mvn exec:java -Dexec.mainClass="com.main.ChessGame"
```

This will start the game, and you'll be able to interact with the engine via the command-line interface.

## Usage 🕹️

After running the engine, you can start playing chess by entering moves using **standard algebraic notation**. For example:

- **e2e4** (moving a pawn from e2 to e4)
- **Nf3** (moving a knight to f3)

The AI will respond to each move, attempting to play the best possible response based on its evaluation of the board.

### Example of Game Flow:
1. **User Move**: `e2e4`
2. **AI Response**: `e7e5`
3. **User Move**: `Nf3`
4. **AI Response**: `Nc6`

### API Integration

The engine provides a RESTful API for seamless integration with external applications, such as mobile apps, web apps, or other services. The following endpoints are available:

- `POST /move`: Submits a move in algebraic notation and returns the updated board state.
- `GET /board`: Returns the current board state in FEN format.
- `POST /undo`: Undoes the last move made.

Example API request:

```sh
POST /move
{
  "move": "e2e4"
}
```

### Example Response:

```json
{
  "status": "success",
  "board": "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
}
```

## Contributing 💻

We welcome contributions! Here's how you can help improve this project:

1. **Fork** the repository to your own GitHub account.
2. **Create a feature branch** (`git checkout -b feature-name`).
3. **Commit your changes** (`git commit -m 'Add new feature'`).
4. **Push to the branch** (`git push origin feature-name`).
5. **Submit a pull request** with a detailed description of your changes.

Please ensure that your code follows the project's coding conventions and includes relevant tests where applicable.

## License 📜

This project is licensed under the **MIT License**. See the `LICENSE` file for more details.

## Contact 📬

For any issues, suggestions, or inquiries, please feel free to:

- Create an issue on [GitHub Issues](https://github.com/Flow-Pie/Java-ChessEngine/issues).
- Contact the project maintainer, **Flow-Pie**, via GitHub discussions.

Happy coding and playing! 🎮♟️
```


