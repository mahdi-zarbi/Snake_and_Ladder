import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;

public class SecondScene {

    static double buttonWidth = 150.0;
    static double buttonHeight = 46.0;
    static double maxWidth = 150.0;
    static List<String> allColors = new ArrayList<>(List.of("red", "blue", "yellow", "green"));
    static Set<String> selectedColors = new HashSet<>();
    static int currentPlayerIndex = 0;
    static List<TextField> playerFields = new ArrayList<>();
    static List<Player> players = new ArrayList<>();
    static List<String> selectedColorsByOrder = new ArrayList<>();

    public static AnchorPane secondScene(Stage stage, int number, double top) {
        AnchorPane anchorPane = new AnchorPane();
        Button nextButton = new Button("Next");
        nextButton.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        nextButton.setDisable(true);
        AnchorPane.setLeftAnchor(nextButton, 45.0);
        AnchorPane.setTopAnchor(nextButton, top);
        anchorPane.getChildren().add(nextButton);

        double[] tops = {43.2, 132.4, 221.6, 310.8};
        int a = number;

        for (int i = 0; i < a; i++) {
            TextField playerField = new TextField();
            playerField.setMinWidth(buttonWidth);
            playerField.setMinHeight(buttonHeight);
            playerField.setMaxWidth(maxWidth);
            AnchorPane.setLeftAnchor(playerField, 45.0);
            AnchorPane.setTopAnchor(playerField, tops[i]);
            playerField.setFont(Font.font("Segoe Print", FontWeight.BOLD, 20));
            playerField.setAlignment(Pos.CENTER);

            if (a == 1) {
                a++;
                playerField.setText("Robot");
                playerField.setEditable(false);
            }

            anchorPane.getChildren().add(playerField);
            playerFields.add(playerField);
        }

        Button playGame = new Button("Play Game");
        playGame.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        AnchorPane.setBottomAnchor(playGame, 0.0);
        AnchorPane.setRightAnchor(playGame, 0.0);
        anchorPane.getChildren().add(playGame);

        diceGame(anchorPane, tops[currentPlayerIndex] - 7.0, nextButton);

        Scene scene=MainScene.getScene(stage,anchorPane);

        nextButton.setOnAction(e -> {

            currentPlayerIndex++;

            String name = playerFields.get(currentPlayerIndex - 1).getText().trim();
            String color = selectedColorsByOrder.get(currentPlayerIndex - 1);

            players.add(new Player(name, color));

            if ((number == 1 && currentPlayerIndex < 2) || (number > 1 && currentPlayerIndex < number)) {
                diceGame(anchorPane, tops[currentPlayerIndex] - 7.0, nextButton);
                nextButton.setDisable(true);
            } else {
                nextButton.setText("Done");
                nextButton.setDisable(true);


                playGame.setOnAction(actionEvent -> {
                    Scene gameScene = BoardGame.board(stage, players);
                    stage.setScene(gameScene);
                    stage.setFullScreen(true);
                });
            }
        });
        stage.setFullScreen(true);
        return anchorPane;
    }

    public static void diceGame(AnchorPane anchorPane, double height, Button nextButton) {
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);

        ToggleGroup toggleGroup = new ToggleGroup();

        double leftStart = 240.0;
        double spacing = 90.0;

        List<String> availableColors = new ArrayList<>();
        for (String color : allColors) {
            if (!selectedColors.contains(color)) {
                availableColors.add(color);
            }
        }

        for (int i = 0; i < availableColors.size(); i++) {
            String color = availableColors.get(i);
            Image img = new Image(color + ".png");
            ImageView view = new ImageView(img);
            view.setFitWidth(95);
            view.setFitHeight(95);
            view.setPreserveRatio(true);

            RadioButton radio = new RadioButton();
            radio.setGraphic(view);
            radio.setToggleGroup(toggleGroup);
            radio.setUserData(color);
            AnchorPane.setTopAnchor(radio, height - 15);
            AnchorPane.setLeftAnchor(radio, leftStart + i * spacing);
            anchorPane.getChildren().add(radio);
        }

        toggleGroup.selectedToggleProperty().addListener((_, _, newToggle) -> {
            for (Toggle toggle : toggleGroup.getToggles()) {
                RadioButton rb = (RadioButton) toggle;
                Node graphic = rb.getGraphic();
                if (rb == newToggle) {
                    graphic.setEffect(null);
                    String selectedColor = rb.getUserData().toString();
                    selectedColors.add(selectedColor);
                    selectedColorsByOrder.add(selectedColor);
                    nextButton.setDisable(false);
                } else {
                    graphic.setEffect(grayscale);
                }
            }
        });
    }
}