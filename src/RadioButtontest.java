import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RadioButtontest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox vBox=new VBox();

        RadioButton radioButton1=new RadioButton("Messi");
        RadioButton radioButton2=new RadioButton("Ronaldo");
        RadioButton radioButton3=new RadioButton("Neymar");
        RadioButton radioButton4=new RadioButton("Suarez");

        ToggleGroup group=new ToggleGroup();
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);
        radioButton3.setToggleGroup(group);
        radioButton4.setToggleGroup(group);

        Label label=new Label("the best football players ?");

        Scene scene=new Scene(vBox,400,400);
        stage.setTitle("RadioButton test");
        vBox.setPadding(new Insets(50));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(label,radioButton1,radioButton3,radioButton2,radioButton4);
        stage.setScene(scene);
        stage.show();

    }
}
