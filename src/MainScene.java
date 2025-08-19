import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

//public class MainScene {
//
//    public static Scene getScene(Stage stage,AnchorPane anchorPane,boolean check){
//
//        Rectangle2D screenBounds= Screen.getPrimary().getVisualBounds();
//        double screenWidth=screenBounds.getWidth();
//        double screenHeight=screenBounds.getHeight();
//
//        AnchorPane a=new AnchorPane();
//        Scene scene=new Scene(a,screenWidth,screenHeight);
//
//        AnchorPane.setLeftAnchor(anchorPane,(scene.getWidth()-600)/2);
//        AnchorPane.setTopAnchor(anchorPane,(scene.getHeight()-200)/2);
//
//
//
//        Image image=new Image("/game.jpg");
//        ImageView background=new ImageView(image);
//        background.setPreserveRatio(false);
//        background.setSmooth(true);
//        background.setOpacity(1);
//        a.getChildren().add(background);
//
//        background.fitWidthProperty().bind(scene.widthProperty());
//        background.fitHeightProperty().bind(scene.heightProperty());
//
//        Label title=new Label("SNAKE & LADDERS");
//        Font.loadFont(MainScene.class.getResource("/MoiraiOne-Regular.ttf").toExternalForm(),50);
//        title.setFont(Font.font("Moirai One", FontWeight.BOLD,90.0));
//        title.setTextFill(Color.DARKRED);
//        AnchorPane.setLeftAnchor(title,scene.getWidth()/4.5);
//        AnchorPane.setTopAnchor(title,100.0);
//
//        Button quit=new Button("Quit");
//        quit.setTextFill(Color.RED);
//        quit.setFont(Font.font("Impact",FontWeight.BOLD,20));
//        quit.setOnAction(actionEvent -> stage.close());
//        AnchorPane.setRightAnchor(quit, 20.0);
//        AnchorPane.setBottomAnchor(quit, 20.0);
//
//
//        a.getChildren().addAll(anchorPane,title,quit);
//
//        stage.setResizable(false);
//        stage.setFullScreenExitHint("");
//        stage.setFullScreenExitKeyCombination(null);
//        stage.setScene(scene);
//        stage.show();
//        return scene;
//    }
//
//}
public class MainScene {

    public static Scene getScene(Stage stage, AnchorPane contentPane) {

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, screenWidth, screenHeight);

        Image image = new Image("/game.jpg");
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
        title.setTextFill(Color.DARKRED);
        AnchorPane.setLeftAnchor(title, screenWidth / 4.5);
        AnchorPane.setTopAnchor(title, 100.0);

        Button quit = new Button("Quit");
        quit.setTextFill(Color.RED);
        quit.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        quit.setOnAction(actionEvent -> stage.close());
        AnchorPane.setRightAnchor(quit, 20.0);
        AnchorPane.setBottomAnchor(quit, 20.0);

        AnchorPane.setLeftAnchor(contentPane, (screenWidth - 600) / 2);
        AnchorPane.setTopAnchor(contentPane, (screenHeight - 200) / 2);

        root.getChildren().addAll(contentPane, title, quit);

        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setScene(scene);
        stage.show();

        return scene;
    }
}