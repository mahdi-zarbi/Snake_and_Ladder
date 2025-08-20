import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainScene {

    public static Scene getScene(Stage stage, AnchorPane contentPane) {

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 1000, 700);

        Image image = new Image("/startpane.jpg");
        ImageView background = new ImageView(image);
        background.setPreserveRatio(false);
        background.setSmooth(true);
        background.setOpacity(1);
        background.fitWidthProperty().bind(scene.widthProperty());
        background.fitHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(background);



        Font.loadFont(MainScene.class.getResource("/MoiraiOne-Regular.ttf").toExternalForm(), 50);
        Label title = new Label("SNAKE & LADDERS");
        title.setFont(Font.font("Moirai One", FontWeight.BOLD, 90.0));
        title.setTextFill(Color.BLUE);
        title.setMinWidth(1000.0);
        title.setAlignment(Pos.CENTER);


        Button quit = new Button("Quit");
        quit.setTextFill(Color.RED);
        quit.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        quit.setOnAction(actionEvent -> stage.close());
        AnchorPane.setRightAnchor(quit, 20.0);
        AnchorPane.setBottomAnchor(quit, 20.0);

        AnchorPane.setLeftAnchor(contentPane, 200.0);
        AnchorPane.setTopAnchor(contentPane, 150.0);

        root.getChildren().addAll(contentPane, title, quit);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        return scene;
    }
}