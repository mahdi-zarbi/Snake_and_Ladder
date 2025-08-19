import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class TextFieldtese extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        PasswordField passwordField=new PasswordField();
        TextField userName=new TextField();

        String pass="1234";
        String us="mahdi";
        Button button=new Button("Login");
        Label userlabel=new Label("UserName : ");
        Label passlabel=new Label(" Password : ");
        button.setOnAction(ActionEvent ->{
            if(userName.getText().equalsIgnoreCase(us)&&passwordField.getText().equalsIgnoreCase(pass)){
                System.out.println("Login");
            }
            else{
                System.out.println("Invalid");
                userName.clear();
                passwordField.clear();}
        });
        button.setDefaultButton(true);
        GridPane layout=new GridPane();
        Scene scene=new Scene(layout,300  ,400);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        layout.setPadding(new Insets(70,30,30,30));
        layout.add(userName,1,0);
        layout.add(userlabel,0,0);
        layout.add(passwordField,1,1);
        layout.add(passlabel,0,1);
        layout.add(button,1,2);
        stage.show();
    }
}