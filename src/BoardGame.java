
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class BoardGame {

    static double d = 36;

    static int number;

    public static Scene board(Stage stage, List<Player> players) {

        ContorollerGame.initializeBoard();

//        BorderPane root = new BorderPane();

        AnchorPane anchorPane = new AnchorPane();



        ImageView background = new ImageView(new Image("img_2.png"));
        background.setPreserveRatio(false);
        background.setFitWidth(500);
        background.setFitHeight(700);
        anchorPane.getChildren().add(background);

        ImageView boardImage = new ImageView(new Image("board2.jpg"));
        boardImage.setFitWidth(700);
        boardImage.setFitHeight(700);
        boardImage.setPreserveRatio(false);
        boardImage.setX(400);
        boardImage.setY(0);

        anchorPane.getChildren().add(boardImage);

        Button quit = new Button("Quit");
        quit.setTextFill(Color.RED);
        quit.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        quit.setOnAction(actionEvent -> stage.close());
        AnchorPane.setLeftAnchor(quit, 20.0);
        AnchorPane.setBottomAnchor(quit, 20.0);
        anchorPane.getChildren().add(quit);

        Scene scene = new Scene(anchorPane, 1100,  700);

        ImageView[] tokens = new ImageView[players.size()];
        Button[] buttons = new Button[players.size()];

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Image img = new Image(player.getColor() + ".png");
            ImageView token = new ImageView(img);
            token.setFitWidth(74.8+i);
            token.setFitHeight(74.8+i);
            tokens[i] = token;

            Button roll = new Button("Roll");
            buttons[i] = roll;
            AnchorPane.setLeftAnchor(roll, 55.0);
            AnchorPane.setTopAnchor(roll, i * 150.0 + 150);
            anchorPane.getChildren().addAll( roll);
        }




        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Label wonLabel = new Label("Won");
            wonLabel.setFont(Font.font("Impact", FontWeight.BOLD, 40));
            wonLabel.setBackground(Background.fill(Color.GREEN));

            final int current = i;
            final int next = (i + 1) % players.size();

            if (player.hasWon()) {
                AnchorPane.setLeftAnchor(wonLabel, 300.0);
                AnchorPane.setTopAnchor(wonLabel, i * 150.0 + 85);
                anchorPane.getChildren().add(wonLabel);
                break;
            } else {
                buttons[i].setOnAction(e -> {
                    for (int l = 0; l < players.size(); l++) {
                        buttons[l].setDisable(l != next);
                    }

                    ImageView token = tokens[current];
                    anchorPane.getChildren().remove(token);
                    anchorPane.getChildren().add(token);

                    List<Point2D> steps = ContorollerGame.movePlayerWithSteps(player, current);
                    SequentialTransition sequence = new SequentialTransition();
                    System.out.println(ContorollerGame.random);
                    for (Point2D step : steps) {
                        TranslateTransition move = new TranslateTransition(Duration.seconds(0.6), token);
                        move.setToX(step.getX() - token.getLayoutX() - d+400);
                        move.setToY(step.getY() - token.getLayoutY() - d - 4);
                        move.setInterpolator(Interpolator.EASE_BOTH);
                        sequence.getChildren().add(move);
                    }

                    Image diceNumber =new Image("dice_"+ContorollerGame.random+".png");
                    ImageView viewDice=new ImageView(diceNumber);
                    viewDice.setFitWidth(100);
                    viewDice.setFitHeight(100);
                    AnchorPane.setTopAnchor(viewDice,current*150.0);
                    AnchorPane.setLeftAnchor(viewDice,200.0);
                    anchorPane.getChildren().add(viewDice);

                    sequence.setOnFinished(ev -> {
                        if (player.hasWon()) {
                            Label win = new Label("Won");
                            win.setFont(Font.font("Impact", FontWeight.BOLD, 40));
                            win.setBackground(Background.fill(Color.GREEN));
                            AnchorPane.setLeftAnchor(win, 300.0);
                            AnchorPane.setTopAnchor(win, current * 150.0 + 85);
                            anchorPane.getChildren().add(win);
                        }
                    });

                    sequence.play();
                });
            }
        }
        Image imageLabel=new Image("img_4.png");



        for (int i = 0; i < players.size(); i++) {

            ImageView imageView=new ImageView(imageLabel);
            imageView.setFitHeight(150);
            imageView.setFitWidth(250);
            if(players.size()<2){
                AnchorPane.setLeftAnchor(imageView,1.0);
                AnchorPane.setTopAnchor(imageView,i*180+70.0);
            }else{
                AnchorPane.setLeftAnchor(imageView,1.0);
                AnchorPane.setTopAnchor(imageView,i*150+10.0);
            }

            anchorPane.getChildren().add(imageView);
            Player player = players.get(i);
            Label label = new Label(player.getName());
            label.setFont(Font.font("Impact", 30));
            label.setMinWidth(149);
            label.setAlignment(Pos.CENTER);


            Color bgColor;
            switch (player.getColor().toLowerCase()) {
                case "red": bgColor = Color.RED; break;
                case "yellow": bgColor = Color.DARKORANGE; break;
                case "green": bgColor = Color.GREEN; break;
                default: bgColor = Color.BLUE; break;
            }
            label.setTextFill(bgColor);

            if(players.size()<2){
                AnchorPane.setLeftAnchor(label,50.0);
                AnchorPane.setTopAnchor(label,i*180+100.0);
            }else{
                AnchorPane.setLeftAnchor(label,50.0);
                AnchorPane.setTopAnchor(label,i*150+70.0);
            }
            anchorPane.getChildren().add(label);
        }

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        return scene;
    }
}
