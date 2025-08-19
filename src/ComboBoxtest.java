import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ComboBoxtest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        VBox vBox=new VBox();
        vBox.setPadding(new Insets(50));
        vBox.setSpacing(20);

        Label label1= new Label("FieldStudy : ");
        Label label2= new Label("SubCategory : ");

        List<String> array1=new ArrayList<>();
        array1.add("Computer");array1.add("Medical");

        List<String> array2=new ArrayList<>();
        array2.add("hardware");array2.add("security");array2.add("software");array2.add("AI");

        List<String> array3=new ArrayList<>();
        array3.add("General");array3.add("Optometrist");array3.add("Surgery");

        ComboBox <String> FieldStudy=new ComboBox<>();//جنریکه باید برای جلوگیری از اخطار نوع داده اون رو مشخص کنیم
        ComboBox<String> SubCategory=new ComboBox<>();

        FieldStudy.setItems(FXCollections.observableArrayList(array1));//combobox1.setValue("Computer"); //مقدار دهی اولیه
        FieldStudy.setOnAction(actionEvent -> {
            String choose=FieldStudy.getValue();
            if(choose.equals("Doctor")){
                SubCategory.setItems(FXCollections.observableArrayList(array3));
            }else{
                SubCategory.setItems(FXCollections.observableArrayList(array2));
            }
        });


//        comboBox.setVisibleRowCount(2);//تعداد سطر هایی رو وقتی که فلش رو میزنیم نشون میده
//        comboBox.setEditable(true);//قابلست ادیت شدن میده

//        combobox1.setOnAction(actionEvent -> {
//            String f=combobox1.getValue();
//            System.out.println(f);
//        });

        vBox.getChildren().addAll(label1,FieldStudy,label2,SubCategory);
        Scene scene=new Scene(vBox,300,400);
        stage.setScene(scene);
        stage.show();

    }
}
