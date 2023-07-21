package com.example.sudoku;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;


public class HelloApplication extends Application {
    GridPane g=new GridPane();
    Scene s=new Scene(g,1200,700);
    TextField t[]=new TextField[81];


    @Override
    public void start(Stage stage) throws IOException
    {

        for(int i=0;i< t.length;i++)
        {
            t[i]=new TextField();
            t[i].setPrefWidth(80);
            t[i].setPrefHeight(65);
            t[i].setFont(new Font(20));
            t[i].setStyle("-fx-font-weight: bold;");
        }
        final int[][] seconds = {{0}};
        Label timerLabel=new Label();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds[0][0]++;
            timerLabel.setText("Time: " + seconds[0][0] + " seconds");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        // Start the timer
        timeline.play();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Rectangle rectangle = new Rectangle(270,232, Color.WHITE);

                rectangle.setStroke(Color.WHITE);
                g.add(rectangle, col, row);

                rectangle.setFill(Color.BLACK);
            }
        }
        Label title=new Label("Sudoku");
        title.setFont(new Font(40));
        title.setStyle("-fx-font-weight: bold;");
        timerLabel.setStyle("-fx-font-weight: bold;");
        g.add(new Label("                                   "),4,0);
        g.add(title,5,0);
        Button check=new Button("Check ✔ ");
        check.setStyle("-fx-font-weight: bold;");
        HBox bbp=new HBox(new Label("            "),check);
        g.add(bbp,5,1);
        Button Refresh=new Button("Refresh ↻ ");
        Refresh.setStyle("-fx-font-weight: bold;");
        HBox bbp2=new HBox(new Label("           "),Refresh);
        VBox bbp1=new VBox(bbp2,new Label(" "),timerLabel);
        g.add(bbp1,5,2);

        int count=0;
        HBox h[]=new HBox[27];
        for(int i=0;i<h.length;i++)
        {
            h[i]=new HBox(new Label(" "),t[count],t[count+1],t[count+2]);
            count+=3;
            h[i].setSpacing(5);

        }
        count=0;
        VBox v[]=new VBox[9];
        for(int i=0;i< v.length;i++)
        {

            v[i]=new VBox(new Label(" "),h[count],h[count+1],h[count+2]);
            count+=3;
            v[i].setSpacing(5);
        }
        count=0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                g.add(v[count],col,row);
                count++;
            }
        }
        randomnumbergenerator();
        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(int i=0;i<t.length;i++)
                {
                    if(!t[i].getText().equals(""))
                    {
                        int n= Integer.parseInt(t[i].getText());
                        if(n<9 && n>-1)
                        {
                            columnchecker();
                            gridchecker();
                            rowchecker();
                            setFull();
                        }else t[i].setText("");
                    }
                }
            }
        });
        Refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(int i=0;i< t.length;i++)
                {
                    t[i].setText("");
                }
                seconds[0] = new int[]{0};
                randomnumbergenerator();
            }
        });
        stage.setTitle("Sudoku");
        stage.setScene(s);
        stage.show();
    }
    public void setFull()
    {
        boolean full=true;
        for(int i=0;i< t.length;i++) {
         //   System.out.println(t[i].getText());
            if (t[i].getText().equals("")) {
                full = false;
            }
        }
        if(full)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("YOU Won..!!");
            alert.show();
            for(int i=0;i< t.length;i++)
                t[i].setText("");
            randomnumbergenerator();
        }
    }
public void randomnumbergenerator()
{
    Random r=new Random();
    int arr[]= new int[18];
    for(int i=0;i< arr.length;i++) {
        arr[i] = r.nextInt(9);
    }
   for(int i:arr)
   {
       t[r.nextInt(81)].setText(String.valueOf(i));
   }
   columnchecker();
   gridchecker();
   rowchecker();
}
public void columnchecker()
{
    int colalt=0;
    int rowalt=0;
    int alt=0;
    int start=0;
    int end=0;
    for(int col=0;col<21;col++)
    {
        if(col==0)
        {
            start=0;
            end=60;
        } else if (col==1) {
            start=1;
            end=61;
        }else if (col==2) {
            start=2;
            end=62;
        }else if (col==9) {
            start=9;
            end=69;
        }else if (col==10) {
            start=10;
            end=70;
        }else if (col==11) {
            start=11;
            end=71;
        }else if (col==18) {
            start=18;
            end=78;
        }else if (col==19) {
            start=19;
            end=79;
        }else if (col==20) {
            start=20;
            end=80;
        }
         for(int i=start;i<=end;i+=3)
         {
             if(!t[i].getText().equals(null))
                 for(int row=start;row<=end;row+=3)
             {
               if(i!=row)
                 if(!t[row].getText().equals(null)) {
                     if (t[i].getText().equals(t[row].getText())) {
                             t[row].setText("");
                     }
                 }
                 if(rowalt==2)
                 {
                     rowalt=0;
                     row+=18;
                 }else rowalt++;

             }
             if(alt==2)
             {
                 alt=0;
                 i+=18;
             }else alt++;
         }
        if(colalt==2)
        {
            colalt=0;
            col+=6;
        }else colalt++;
    }
}
public void rowchecker()
{
    int colalt=0;
    int rowalt=0;
    int alt=0;
    int start=0;
    int end=0;
    for(int row=0;row<61;row+=3)
    {
        if(row==0)
        {
            start=0;
            end=20;
        } else if (row==3) {
            start=3;
            end=23;
        }else if (row==6) {
            start=6;
            end=26;
        }else if (row==27) {
            start=27;
            end=47;
        }else if (row==30) {
            start=30;
            end=50;
        }else if (row==33) {
            start=33;
            end=53;
        }else if (row==54) {
            start=54;
            end=74;
        }else if (row==57) {
            start=57;
            end=77;
        }else if (row==60) {
            start=60;
            end=80;
        }
        for(int i=start;i<=end;i++)
        {
            if(!t[i].getText().equals(null))
                for(int col=start;col<=end;col++)
                {
                    if(i!=col)
                        if(!t[col].getText().equals(null)) {
                            if (t[i].getText().equals(t[col].getText())) {
                                t[col].setText("");
                            }
                        }
                    if(colalt==2)
                    {
                        colalt=0;
                        col+=6;
                    }else colalt++;

                }
            if(alt==2)
            {
                alt=0;
                i+=6;
            }else alt++;
        }
        if(rowalt==2)
        {
            rowalt=0;
            row+=6;
        }else rowalt++;
    }
}

public void gridchecker()
{
    int start=0;
    int end=8;
    for(int grid=0;grid<9;grid++)
    {
          for(int index=start;index<=end;index++){
              if(!t[index].getText().equals(null))
                for(int alt=start;alt<=end;alt++){
                    if(!t[alt].getText().equals(null))
                        if(alt!=index)
                            if(t[index].getText().equals(t[alt].getText()))
                            {

                                t[alt].setText("");

                            }
                }
          }
          start+=9;
          end+=9;
    }
}

    public static void main(String[] args) {
        launch();
    }
}