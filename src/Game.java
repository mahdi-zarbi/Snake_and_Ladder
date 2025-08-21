import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Image icon=new Image("/Image_Font/dice_0.png");
        stage.getIcons().add(icon);
        stage.setTitle("Snake and Ladder");
        StartPane.startScene(stage);

    }
}
