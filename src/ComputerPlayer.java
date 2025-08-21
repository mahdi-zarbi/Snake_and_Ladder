public class ComputerPlayer extends BasePlayer {
    public ComputerPlayer(String name, String color) {
        super(name, color);
    }

    @Override
    public void takeTurn() {
        int dice = ControllerGame.rand();
        int tentativePos = position + dice;

        if (tentativePos > 100) tentativePos = position;

        int finalPos = ControllerGame.snake.getOrDefault(tentativePos,
                ControllerGame.ladder.getOrDefault(tentativePos, tentativePos));

        setPosition(finalPos);

        if (finalPos == 100) {
            hasWon = true;
            ControllerGame.gameOver = true;
        }
    }
}