import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckBoxtest extends Application implements EventHandler {

    public static void main(String[] args) {
        launch(args);
    }
    int total=0;
    Label label=new Label();

    @Override
    public void start(Stage stage) {
        VBox vBox=new VBox();
        vBox.setPadding(new Insets(50));
        vBox.setSpacing(20);

//        checkBox.setIndeterminate(true);
//        checkBox.setAllowIndeterminate(true);
//        checkBox.setOnAction(actionEvent -> {
//            System.out.println(checkBox.isSelected());
//        });

        CheckBox checkBox_Drink=new CheckBox("Drink 5$");
        CheckBox checkBox_Salad=new CheckBox("Salad 2$");
        CheckBox checkBox_Dessert=new CheckBox("Dessert 5$");
        label.setText("Total : 0$ ");

        checkBox_Dessert.setOnAction(this);
        checkBox_Salad.setOnAction(this);
        checkBox_Drink.setOnAction(this);

        stage.setTitle("Check Box");
        vBox.getChildren().addAll(checkBox_Dessert,checkBox_Drink,checkBox_Salad,label);
        Scene scene=new Scene(vBox,300,400);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(Event event) {
        CheckBox checkBox=(CheckBox) event.getSource();
        if(checkBox.getText().equalsIgnoreCase("Drink 5$")){
            total+=checkBox.isSelected()?+5:-5;
        }else if (checkBox.getText().equalsIgnoreCase("Salad 2$")) {
            total+=checkBox.isSelected()?+2:-2;
        }else{
            total+=checkBox.isSelected()?+5:-5;
        }
        label.setText("Total: "+total+"$");
    }
}
