package com.whitewoodcity.ui;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExceptionBox extends Stage{

    private Button exceptionButton;

    private TextArea textArea = new TextArea();

    public ExceptionBox(Button exceptionButton){
        this.exceptionButton = exceptionButton;
        StackPane root = new StackPane();
        root.setPadding(new Insets(10));
        root.getChildren().addAll(textArea);
        Scene scene = new Scene(root);

        this.setScene(scene);
    }

    public void setExceptionMessage(String exceptionMessage) {
        if(exceptionMessage!=null&&!exceptionMessage.trim().equals("")){
            exceptionButton.setTextFill(Color.RED);
        }else{
            exceptionButton.setTextFill(Color.BLACK);
        }
        textArea.setText(exceptionMessage);
    }

    public void clearExceptionMessage(){
        this.setExceptionMessage(null);
    }
}
