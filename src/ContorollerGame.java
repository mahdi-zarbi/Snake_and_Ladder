import javafx.geometry.Point2D;
import java.util.*;

public class ContorollerGame {

    public static int winnerIndex = -1;
    public static boolean gameOver = false;

    static double margin = 22.75;
    static double boardSize = 654.5;
    static double cellSize = boardSize / 10;

    static Map<Integer, Integer> snake = new HashMap<>();
    static Map<Integer, Integer> ladder = new HashMap<>();

    public static void initializeBoard() {

        gameOver = false;
        winnerIndex = -1;
        Snake();
        ladder();
    }

    public static void Snake() {
        snake.put(24, 19);
        snake.put(36, 14);
        snake.put(46, 32);
        snake.put(65, 39);
        snake.put(69, 54);
        snake.put(81, 78);
        snake.put(85, 67);
        snake.put(93, 71);
        snake.put(97, 82);
    }

    public static void ladder() {
        ladder.put(1, 38);
        ladder.put(9, 31);
        ladder.put(13, 56);
        ladder.put(40, 59);
        ladder.put(44, 63);
        ladder.put(68, 89);
        ladder.put(74, 96);
        ladder.put(80, 99);
    }

    public static Point2D coordinates(int cellNumber) {
        int row = (cellNumber - 1) / 10;
        int columnInRow = (cellNumber - 1) % 10;
        int column = (row % 2 == 0) ? columnInRow : 9 - columnInRow;

        double x = margin + (column * cellSize) + (cellSize / 2);
        double y = 700 - (margin + (row * cellSize) + (cellSize / 2));

        return new Point2D(x, y);
    }

    public static List<Point2D> movePlayerWithSteps(Player player, int playerIndex) {
        List<Point2D> steps = new ArrayList<>();

        if (gameOver) {
            steps.add(coordinates(player.getPosition()));
            return steps;
        }

        int currentPos = player.getPosition();
        int diceRoll = rand();
        int tentativePos = currentPos + diceRoll;

        if (tentativePos > 100) {
            tentativePos = currentPos;
        }

        steps.add(coordinates(tentativePos));

        int finalPos = snake.getOrDefault(tentativePos, ladder.getOrDefault(tentativePos, tentativePos));

        if (finalPos != tentativePos) {
            steps.add(coordinates(finalPos));
        }

        player.setPosition(finalPos);

        if (finalPos == 100) {
            gameOver = true;
            winnerIndex = playerIndex;
        }

        return steps;
    }

    public static int rand() {
        return new Random().nextInt(6) + 1;
    }

    public static int getPositionIndex(int playerIndex, List<Player> players) {
        return players.get(playerIndex).getPosition();
    }
}