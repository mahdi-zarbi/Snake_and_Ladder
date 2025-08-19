import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChoiceBoxtest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ChoiceBox<String> choiceBox=new ChoiceBox<>();
        String[] array={"one","two","three"};
        choiceBox.setItems(FXCollections.observableArrayList(array));


        VBox vBox=new VBox();
        vBox.setPadding(new Insets(50));
        vBox.setSpacing(20);
        vBox.getChildren().addAll(choiceBox);
        Scene scene=new Scene(vBox,300,400);
        stage.setScene(scene);
        stage.show();
    }
}
