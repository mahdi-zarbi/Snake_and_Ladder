//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.stage.Stage;
//
//public class OneScene {
//
//    public static AnchorPane oneScene(Stage stage){
//        AnchorPane anchorPane=new AnchorPane();
//
//        Label label=new Label("Select the game type ");
//        label.setMinSize(260,72);
//        label.setFont(Font.font("System", FontWeight.BOLD,32));
//        AnchorPane.setLeftAnchor(label,150.0);
//        AnchorPane.setTopAnchor(label,28.0);
//
//        Button twoPlayers=new Button("2 Players");
//        twoPlayers.setFont(Font.font("Segoe Print",FontWeight.BOLD,35));
//        twoPlayers.setMinSize(195,70);
//        AnchorPane.setLeftAnchor(twoPlayers,70.0);
//        AnchorPane.setTopAnchor(twoPlayers,120.0);
//
//        Button threePlayers = new Button("3 Players");
//        threePlayers.setFont(Font.font("Segoe Print",FontWeight.BOLD,35));
//        threePlayers.setMinSize(195,70);
//        AnchorPane.setLeftAnchor(threePlayers,70.0);
//        AnchorPane.setTopAnchor(threePlayers,190.0);
//
//        Button fourPlayers=new Button("4 Players");
//        fourPlayers.setFont(Font.font("Segoe Print",FontWeight.BOLD,35));
//        fourPlayers.setMinSize(195,70);
//        AnchorPane.setLeftAnchor(fourPlayers,70.0);
//        AnchorPane.setTopAnchor(fourPlayers,260.0);
//
//        anchorPane.getChildren().addAll(twoPlayers,threePlayers,fourPlayers,label);
//        Scene scene=MainScene.getScene(stage,anchorPane);
//
//
//        twoPlayers.setOnAction(actionEvent -> {
//            Scene secondScene = new Scene(SecondScene.secondScene(stage, 2, 221.6));
//            stage.setScene(secondScene);
//
//        });
//
//        threePlayers.setOnAction(actionEvent -> {
//            Scene secondScene = new Scene(SecondScene.secondScene(stage, 3, 309.2));
//            stage.setScene(secondScene);
//
//        });
//
//        fourPlayers.setOnAction(actionEvent -> {
//            Scene secondScene = new Scene(SecondScene.secondScene(stage, 4, 396.8));
//            stage.setScene(secondScene);
//
//        });
//
//        return anchorPane;
//    }
//}
