import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
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

import java.io.InputStream;
import java.util.List;

public class BoardGame {

    static double d = 36;

    public static Scene board(Stage stage, List<BasePlayer> players) {

        ContorollerGame.initializeBoard();

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

        Scene scene = new Scene(anchorPane, 1100, 700);

        ImageView[] tokens = new ImageView[players.size()];
        Button[] buttons = new Button[players.size()];

        for (int i = 0; i < players.size(); i++) {
            BasePlayer player = players.get(i);
            Image img = new Image(player.getColor() + ".png");
            ImageView token = new ImageView(img);
            token.setFitWidth(74.8 + i);
            token.setFitHeight(74.8 + i);
            tokens[i] = token;

            Button roll = new Button("Roll");
            buttons[i] = roll;
            AnchorPane.setLeftAnchor(roll, 55.0);
            AnchorPane.setTopAnchor(roll, i * 150.0 + 150);
            anchorPane.getChildren().add(roll);
        }

        ImageView diceView = new ImageView();
        diceView.setFitWidth(160);
        diceView.setFitHeight(160);
        anchorPane.getChildren().add(diceView);

        for (int i = 0; i < players.size(); i++) {
            BasePlayer player = players.get(i);

            Image winnerImage = new Image("winner.png");
            ImageView winner = new ImageView(winnerImage);
            winner.setFitHeight(70);
            winner.setFitWidth(70);

            final int current = i;
            final int next = (i + 1) % players.size();

            if (player.hasWon()) {
                AnchorPane.setLeftAnchor(winner, 300.0);
                AnchorPane.setTopAnchor(winner, current * 150.0 + 85);
                anchorPane.getChildren().add(winner);
                anchorPane.getChildren().remove(diceView);
                break;
            } else {
                if (player instanceof ComputerPlayer) {
                    buttons[i].setDisable(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                    pause.setOnFinished(e -> {
                        playTurn(anchorPane, tokens, diceView, winner, players, current, next, buttons);
                    });
                    pause.play();
                } else {
                    buttons[i].setOnAction(e -> {
                        playTurn(anchorPane, tokens, diceView, winner, players, current, next, buttons);
                    });
                }
            }
        }

        Image imageLabel = new Image("img_4.png");

        for (int i = 0; i < players.size(); i++) {
            ImageView imageView = new ImageView(imageLabel);
            imageView.setFitHeight(150);
            imageView.setFitWidth(250);
            if (players.size() < 2) {
                AnchorPane.setLeftAnchor(imageView, 1.0);
                AnchorPane.setTopAnchor(imageView, i * 180 + 70.0);
            } else {
                AnchorPane.setLeftAnchor(imageView, 1.0);
                AnchorPane.setTopAnchor(imageView, i * 150 + 10.0);
            }

            anchorPane.getChildren().add(imageView);

            BasePlayer player = players.get(i);
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

            if (players.size() < 2) {
                AnchorPane.setLeftAnchor(label, 50.0);
                AnchorPane.setTopAnchor(label, i * 180 + 100.0);
            } else {
                AnchorPane.setLeftAnchor(label, 50.0);
                AnchorPane.setTopAnchor(label, i * 150 + 70.0);
            }
            anchorPane.getChildren().add(label);
        }

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        return scene;
    }

    private static void playTurn(AnchorPane anchorPane, ImageView[] tokens, ImageView diceView,
                                 ImageView winner, List<BasePlayer> players, int current, int next, Button[] buttons) {

        for (int l = 0; l < players.size(); l++) {
            buttons[l].setDisable(l != next);
        }

        BasePlayer player = players.get(current);
        ImageView token = tokens[current];
        anchorPane.getChildren().remove(token);
        anchorPane.getChildren().add(token);

        List<Point2D> steps = ContorollerGame.movePlayerWithSteps(player, current);
        SequentialTransition sequence = new SequentialTransition();

        for (Point2D step : steps) {
            TranslateTransition move = new TranslateTransition(Duration.seconds(0.6), token);
            move.setToX(step.getX() - token.getLayoutX() - d + 400);
            move.setToY(step.getY() - token.getLayoutY() - d - 4);
            move.setInterpolator(Interpolator.EASE_BOTH);
            sequence.getChildren().add(move);
        }

        String imagePath = "dice_" + ContorollerGame.random + ".png";
        InputStream stream = BoardGame.class.getResourceAsStream(imagePath);
        diceView.setImage(new Image(stream));
        AnchorPane.setTopAnchor(diceView, current * 145.0 + 45);
        AnchorPane.setLeftAnchor(diceView, 250.0);

        sequence.setOnFinished(ev -> {
            if (player.hasWon()) {
                AnchorPane.setLeftAnchor(winner, 250.0);
                AnchorPane.setTopAnchor(winner, current * 145.0 + 45);
                anchorPane.getChildren().add(winner);
                anchorPane.getChildren().remove(diceView);
            } else if (players.get(next) instanceof ComputerPlayer) {
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    playTurn(anchorPane, tokens, diceView, winner, players, next, (next + 1) % players.size(), buttons);
                });
                pause.play();
            }
        });

        sequence.play();
    }
}