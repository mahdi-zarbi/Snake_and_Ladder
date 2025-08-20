public abstract class BasePlayer {
    protected String name;
    protected String color;
    protected int position;
    protected boolean hasWon;

    public BasePlayer(String name, String color) {
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

    public abstract void takeTurn();
}