
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class BoardGame {

    static double d = 36;

    public static Scene board(Stage stage, List<Player> players) {

        ContorollerGame.initializeBoard();

        BorderPane root = new BorderPane();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        ImageView background = new ImageView(new Image("island.jpg"));
        background.setPreserveRatio(false);
        background.setFitWidth(screenWidth);
        background.setFitHeight(890);
        root.getChildren().add(background);

        ImageView boardImage = new ImageView(new Image("bord.jpg"));
        boardImage.setFitWidth(870);
        boardImage.setFitHeight(870);
        boardImage.setPreserveRatio(false);

        AnchorPane rightPane = new AnchorPane();
        rightPane.getChildren().add(boardImage);

        StackPane topPane = new StackPane();
        StackPane centerPane = new StackPane();
        StackPane bottomPane = new StackPane();
        AnchorPane leftPane = new AnchorPane();
        leftPane.setPrefWidth(500);

        Button quit = new Button("Quit");
        quit.setTextFill(Color.RED);
        quit.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        quit.setOnAction(actionEvent -> stage.close());
        AnchorPane.setLeftAnchor(quit, 20.0);
        AnchorPane.setBottomAnchor(quit, 20.0);
        leftPane.getChildren().add(quit);

        Scene scene = new Scene(root, screenWidth, screenHeight);

        ImageView[] tokens = new ImageView[players.size()];
        Button[] buttons = new Button[players.size()];

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Image img = new Image(player.getColor() + ".png");
            ImageView token = new ImageView(img);
            token.setFitWidth(74.8);
            token.setFitHeight(74.8);
            tokens[i] = token;

            Button roll = new Button("Roll");
            buttons[i] = roll;
            AnchorPane.setLeftAnchor(roll, 55.0);
            AnchorPane.setTopAnchor(roll, i * 150.0 + 150);
            Point2D startPos = ContorollerGame.coordinates(0);
            token.setLayoutX(startPos.getX() - d);
            token.setLayoutY(startPos.getY() - d - 4);
            leftPane.getChildren().addAll(token, roll);
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
                leftPane.getChildren().add(wonLabel);
                break;
            } else {
                buttons[i].setOnAction(e -> {
                    for (int l = 0; l < players.size(); l++) {
                        buttons[l].setDisable(l != next);
                    }

                    ImageView token = tokens[current];
                    rightPane.getChildren().remove(token);
                    rightPane.getChildren().add(token);

                    List<Point2D> steps = ContorollerGame.movePlayerWithSteps(player, current);
                    SequentialTransition sequence = new SequentialTransition();

                    for (Point2D step : steps) {
                        TranslateTransition move = new TranslateTransition(Duration.seconds(0.6), token);
                        move.setToX(step.getX() - token.getLayoutX() - d);
                        move.setToY(step.getY() - token.getLayoutY() - d - 4);
                        move.setInterpolator(Interpolator.EASE_BOTH);
                        sequence.getChildren().add(move);
                    }

                    sequence.setOnFinished(ev -> {
                        if (player.hasWon()) {
                            Label win = new Label("Won");
                            win.setFont(Font.font("Impact", FontWeight.BOLD, 40));
                            win.setBackground(Background.fill(Color.GREEN));
                            AnchorPane.setLeftAnchor(win, 300.0);
                            AnchorPane.setTopAnchor(win, current * 150.0 + 85);
                            leftPane.getChildren().add(win);
                        }
                    });

                    sequence.play();
                });
            }
        }

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Label label = new Label(player.getName());
            label.setFont(Font.font("Impact", FontWeight.BOLD, 40));
            label.setMinWidth((screenWidth - 600) / 3);
            label.setAlignment(Pos.CENTER);

            Color bgColor;
            switch (player.getColor().toLowerCase()) {
                case "red":
                    bgColor = Color.RED;
                    break;
                case "yellow":
                    bgColor = Color.YELLOW;
                    break;
                case "green":
                    bgColor = Color.GREEN;
                    break;
                default:
                    bgColor = Color.BLUE;
                    break;
            }
            label.setBackground(Background.fill(bgColor));

            AnchorPane.setLeftAnchor(label, 55.0);
            AnchorPane.setTopAnchor(label, i * 150.0 + 85);
            leftPane.getChildren().add(label);
        }

        root.setLeft(leftPane);
        root.setRight(rightPane);
        root.setCenter(centerPane);
        root.setTop(topPane);
        root.setBottom(bottomPane);

        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.setScene(scene);
        stage.show();

        return scene;
    }
}
