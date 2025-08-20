import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

public class MainScene {

    public static Scene getScene(Stage stage) {

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1100, 700);

        Image image = new Image("/startpane.jpg");
        ImageView background = new ImageView(image);
        background.setPreserveRatio(false);
        background.setSmooth(true);
        background.setOpacity(1);
        background.fitWidthProperty().bind(scene.widthProperty());
        background.fitHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(background);

        Button twoPlayers=new Button();
        twoPlayers.setMinSize(75,85);
        AnchorPane.setRightAnchor(twoPlayers,355.0);
        AnchorPane.setBottomAnchor(twoPlayers,165.0);
        twoPlayers.setBackground(new Background(new BackgroundFill(null,null,null)));

        Button threePlayers = new Button();
        threePlayers.setMinSize(75,85);
        AnchorPane.setRightAnchor(threePlayers,230.0);
        AnchorPane.setBottomAnchor(threePlayers,165.0);
        threePlayers.setBackground(new Background(new BackgroundFill(null,null,null)));

        Button fourPlayers=new Button();
        fourPlayers.setMinSize(75,85);
        AnchorPane.setRightAnchor(fourPlayers,107.0);
        AnchorPane.setBottomAnchor(fourPlayers,165.0);
        fourPlayers.setBackground(new Background(new BackgroundFill(null,null,null)));

        twoPlayers.setOnAction(actionEvent -> {
            Scene secondScene = SecondScene.secondScene(stage, 2);
            stage.setScene(secondScene);

        });

        threePlayers.setOnAction(actionEvent -> {
            Scene secondScene = SecondScene.secondScene(stage, 3);
            stage.setScene(secondScene);

        });

        fourPlayers.setOnAction(actionEvent -> {
           Scene secondScene= SecondScene.secondScene(stage,4);
            stage.setScene(secondScene);

        });

        Button quit = new Button();
        quit.setBackground(new Background(new BackgroundFill(null,null,null)));
        quit.setOnAction(actionEvent -> stage.close());
        quit.setMinWidth(100.0);
        quit.setMinHeight(30.0);
        AnchorPane.setRightAnchor(quit, 210.0);
        AnchorPane.setBottomAnchor(quit, 42.0);

        root.getChildren().addAll(twoPlayers,threePlayers,fourPlayers, quit);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        return scene;
    }
}