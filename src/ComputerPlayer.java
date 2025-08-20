public class ComputerPlayer extends BasePlayer {
    public ComputerPlayer(String name, String color) {
        super(name, color);
    }

    @Override
    public void takeTurn() {
        int dice = ContorollerGame.rand();
        int tentativePos = position + dice;

        if (tentativePos > 100) tentativePos = position;

        int finalPos = ContorollerGame.snake.getOrDefault(tentativePos,
                ContorollerGame.ladder.getOrDefault(tentativePos, tentativePos));

        setPosition(finalPos);

        if (finalPos == 100) {
            hasWon = true;
            ContorollerGame.gameOver = true;
        }
    }
}