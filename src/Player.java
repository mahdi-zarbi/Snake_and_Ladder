public class Player {
    private String name;
    private String color;
    private int position;
    private boolean hasWon;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.position = 0;
        this.hasWon = false;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void setPosition(int position) {
        this.position = position;
        if (position >= 100) {
            this.hasWon = true;
        }
    }


}