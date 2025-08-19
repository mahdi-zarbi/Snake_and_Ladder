import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SnakeAndLadderGame extends Application {

    // Constants for the game board
    private static final int TILE_SIZE = 70;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int BOARD_WIDTH = TILE_SIZE * WIDTH;
    private static final int BOARD_HEIGHT = TILE_SIZE * HEIGHT;

    // UI Elements
    private final GridPane board = new GridPane();
    private final Pane piecePane = new Pane(); // Pane to hold player pieces and snake/ladder lines
    private Button rollButton;
    private Label diceResultLabel;
    private Label turnLabel;
    private Label gameStatusLabel;

    // Game state variables
    private final Circle playerPiece = new Circle(TILE_SIZE / 3, Color.DODGERBLUE);
    private final Circle computerPiece = new Circle(TILE_SIZE / 3, Color.CRIMSON);
    private int playerPosition = 1;
    private int computerPosition = 1;
    private boolean isPlayerTurn = true;
    private boolean gameEnded = false;
    private final Random random = new Random();

    // Map to store the start and end points of snakes and ladders
    private final Map<Integer, Integer> snakesAndLadders = new HashMap<>();

    /**
     * The main entry point for all JavaFX applications.
     */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Snake and Ladder Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Creates the main content of the game window, including the board and UI controls.
     * @return The root Parent node for the scene.
     */
    private Parent createContent() {
        // Main layout using BorderPane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #F0F8FF;");

        // Initialize the game elements
        initializeSnakesAndLadders();
        createGameBoard();

        // Stack the board and the piece pane on top of each other
        StackPane boardStack = new StackPane(board, piecePane);
        root.setCenter(boardStack);

        // Create the UI controls on the right side
        root.setRight(createControls());

        // Place initial pieces on the board
        positionPiece(playerPiece, playerPosition);
        positionPiece(computerPiece, computerPosition);

        // Make the piece pane non-interactive so it doesn't block clicks on the board
        piecePane.setMouseTransparent(true);

        // Draw the visual lines for snakes and ladders after the scene is rendered
        board.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                drawSnakesAndLadders();
            }
        });

        return root;
    }

    /**
     * Creates the 10x10 game board using a GridPane.
     */
    private void createGameBoard() {
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            int row = i / WIDTH;
            int col = i % WIDTH;

            // Calculate tile number based on the S-pattern of the board
            int tileNumber;
            if (row % 2 == 0) {
                tileNumber = (HEIGHT - 1 - row) * WIDTH + col + 1;
            } else {
                tileNumber = (HEIGHT - 1 - row) * WIDTH + (WIDTH - 1 - col) + 1;
            }

            // Create each tile as a StackPane
            StackPane tile = new StackPane();
            tile.setPrefSize(TILE_SIZE, TILE_SIZE);
            tile.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0.5))));

            // Alternate tile colors
            if ((row + col) % 2 == 0) {
                tile.setStyle("-fx-background-color: #aae0aae6;");
            } else {
                tile.setStyle("-fx-background-color: #e6f5e6e6;");
            }

            Label numberLabel = new Label(String.valueOf(tileNumber));
            numberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            tile.getChildren().add(numberLabel);

            board.add(tile, col, row);
        }
        // Add player pieces to the piece pane
        piecePane.getChildren().addAll(playerPiece, computerPiece);
    }

    /**
     * Creates the UI controls (button and labels).
     * @return A VBox containing the UI controls.
     */
    private VBox createControls() {
        // Dice image
        ImageView diceImage = new ImageView(new Image("https://i.imgur.com/g28eG2s.png", 100, 100, true, true, true));

        // Roll button
        rollButton = new Button("Roll Dice");
        rollButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        rollButton.setPrefWidth(150);
        rollButton.setOnAction(e -> handleRollAction());

        // Labels for game information
        diceResultLabel = new Label("Dice: -");
        diceResultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        turnLabel = new Label("Turn: Player");
        turnLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        turnLabel.setTextFill(Color.DODGERBLUE);

        gameStatusLabel = new Label("Game in progress...");
        gameStatusLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        gameStatusLabel.setWrapText(true);
        gameStatusLabel.setMaxWidth(180);

        // Layout for controls
        VBox controlsBox = new VBox(20, turnLabel, diceImage, diceResultLabel, rollButton, gameStatusLabel);
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.setPadding(new Insets(0, 20, 0, 20));
        controlsBox.setPrefWidth(200);

        return controlsBox;
    }

    /**
     * Defines the start and end points for all snakes and ladders.
     */
    private void initializeSnakesAndLadders() {
        // Ladders (start -> end)
        snakesAndLadders.put(4, 14);
        snakesAndLadders.put(9, 31);
        snakesAndLadders.put(20, 38);
        snakesAndLadders.put(28, 84);
        snakesAndLadders.put(40, 59);
        snakesAndLadders.put(51, 67);
        snakesAndLadders.put(63, 81);
        snakesAndLadders.put(71, 91);

        // Snakes (start -> end)
        snakesAndLadders.put(17, 7);
        snakesAndLadders.put(54, 34);
        snakesAndLadders.put(62, 19);
        snakesAndLadders.put(64, 60);
        snakesAndLadders.put(87, 24);
        snakesAndLadders.put(93, 73);
        snakesAndLadders.put(95, 75);
        snakesAndLadders.put(99, 78);
    }

    /**
     * Draws lines on the piecePane to visually represent snakes and ladders.
     */
    private void drawSnakesAndLadders() {
        for (Map.Entry<Integer, Integer> entry : snakesAndLadders.entrySet()) {
            int start = entry.getKey();
            int end = entry.getValue();

            // Get pixel coordinates for start and end tiles
            double[] startCoords = getPixelCoordinates(start);
            double[] endCoords = getPixelCoordinates(end);

            Line line = new Line(startCoords[0], startCoords[1], endCoords[0], endCoords[1]);
            line.setStrokeWidth(4);

            // Ladders are green, snakes are red
            if (end > start) {
                line.setStroke(Color.FORESTGREEN);
            } else {
                line.setStroke(Color.ORANGERED);
            }
            line.setOpacity(0.7);
            piecePane.getChildren().add(line);
        }
        // Ensure pieces are drawn on top of the lines
        playerPiece.toFront();
        computerPiece.toFront();
    }

    /**
     * Handles the dice roll action when the button is clicked.
     */
    private void handleRollAction() {
        if (gameEnded || !isPlayerTurn) return;

        rollButton.setDisable(true);
        int diceRoll = random.nextInt(6) + 1;
        diceResultLabel.setText("Dice: " + diceRoll);

        movePiece(playerPiece, playerPosition, diceRoll);
    }

    /**
     * Handles the computer's turn.
     */
    private void computerTurn() {
        if (gameEnded) return;

        // A short delay to simulate the computer "thinking"
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            int diceRoll = random.nextInt(6) + 1;
            diceResultLabel.setText("Dice: " + diceRoll);
            movePiece(computerPiece, computerPosition, diceRoll);
        });
        pause.play();
    }

    /**
     * Moves a piece on the board based on the dice roll.
     * @param piece The Circle object representing the player/computer.
     * @param currentPosition The current tile number of the piece.
     * @param diceRoll The result of the dice roll.
     */
    private void movePiece(Circle piece, int currentPosition, int diceRoll) {
        int newPosition = currentPosition + diceRoll;

        // Animate the move step by step
        SequentialTransition sequentialTransition = new SequentialTransition();
        int tempPos = currentPosition;
        for (int i = 0; i < diceRoll; i++) {
            if (tempPos + 1 > 100) break;
            tempPos++;
            double[] coords = getPixelCoordinates(tempPos);
            TranslateTransition tt = new TranslateTransition(Duration.millis(250), piece);
            tt.setToX(coords[0] - (TILE_SIZE / 2.0));
            tt.setToY(coords[1] - (TILE_SIZE / 2.0));
            sequentialTransition.getChildren().add(tt);
        }

        sequentialTransition.setOnFinished(e -> {
            // Update the position after the animation
            if (isPlayerTurn) {
                playerPosition = Math.min(newPosition, 100);
            } else {
                computerPosition = Math.min(newPosition, 100);
            }

            // Check for a win
            if (checkWinCondition()) return;

            // Check for snakes or ladders
            checkForSnakeOrLadder();
        });

        sequentialTransition.play();
    }

    /**
     * Checks if the current player has landed on a snake or ladder and moves them accordingly.
     */
    private void checkForSnakeOrLadder() {
        int currentPos = isPlayerTurn ? playerPosition : computerPosition;
        Circle currentPiece = isPlayerTurn ? playerPiece : computerPiece;

        if (snakesAndLadders.containsKey(currentPos)) {
            int destination = snakesAndLadders.get(currentPos);
            String type = destination > currentPos ? "a ladder" : "a snake";
            gameStatusLabel.setText((isPlayerTurn ? "Player" : "Computer") + " landed on " + type + "!");

            // Animate the move to the new position
            double[] coords = getPixelCoordinates(destination);
            TranslateTransition tt = new TranslateTransition(Duration.millis(500), currentPiece);
            tt.setToX(coords[0] - (TILE_SIZE / 2.0));
            tt.setToY(coords[1] - (TILE_SIZE / 2.0));
            tt.setOnFinished(e -> {
                if (isPlayerTurn) {
                    playerPosition = destination;
                } else {
                    computerPosition = destination;
                }
                // Check for a win again after moving
                if (checkWinCondition()) return;

                // Switch turns after the snake/ladder move
                switchTurn();
            });
            tt.play();
        } else {
            // If no snake or ladder, just switch turns
            switchTurn();
        }
    }

    /**
     * Checks if the current player has reached tile 100.
     * @return true if a player has won, false otherwise.
     */
    private boolean checkWinCondition() {
        if (playerPosition == 100) {
            endGame("Player Wins! 🎉");
            return true;
        } else if (computerPosition == 100) {
            endGame("Computer Wins! 🤖");
            return true;
        }
        return false;
    }

    /**
     * Ends the game and displays a final message.
     * @param message The winning message to display.
     */
    private void endGame(String message) {
        gameEnded = true;
        gameStatusLabel.setText(message);
        rollButton.setDisable(true);
        turnLabel.setText("Game Over!");
    }

    /**
     * Switches the turn from player to computer and vice-versa.
     */
    private void switchTurn() {
        isPlayerTurn = !isPlayerTurn;
        if (isPlayerTurn) {
            turnLabel.setText("Turn: Player");
            turnLabel.setTextFill(Color.DODGERBLUE);
            gameStatusLabel.setText("Your turn to roll.");
            rollButton.setDisable(false);
        } else {
            turnLabel.setText("Turn: Computer");
            turnLabel.setTextFill(Color.CRIMSON);
            gameStatusLabel.setText("Computer is thinking...");
            computerTurn();
        }
    }

    /**
     * Positions a piece at the center of a given tile number.
     * @param piece The Circle object to position.
     * @param position The tile number (1-100).
     */
    private void positionPiece(Circle piece, int position) {
        double[] coords = getPixelCoordinates(position);
        piece.setTranslateX(coords[0] - (TILE_SIZE / 2.0));
        piece.setTranslateY(coords[1] - (TILE_SIZE / 2.0));
    }

    /**
     * Converts a tile number (1-100) to its center pixel coordinates (x, y) on the board.
     * @param position The tile number.
     * @return An array containing the [x, y] coordinates.
     */
    private double[] getPixelCoordinates(int position) {
        // Find the corresponding node (StackPane) in the GridPane
        for (Node node : board.getChildren()) {
            if (node instanceof StackPane) {
                Label label = (Label) ((StackPane) node).getChildren().get(0);
                if (Integer.parseInt(label.getText()) == position) {
                    // Get the center coordinates of the tile
                    double x = node.getBoundsInParent().getMinX() + TILE_SIZE / 2.0;
                    double y = node.getBoundsInParent().getMinY() + TILE_SIZE / 2.0;
                    return new double[]{x, y};
                }
            }
        }
        return new double[]{0, 0}; // Should not happen
    }

    /**
     * The main method to launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

