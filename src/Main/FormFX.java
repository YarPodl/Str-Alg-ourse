package Main;

import Array.ArrayInsert;
import Array.ArraySwap;
import Array.Chances;
import Array.arraySortingItself;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FormFX extends Application {

    final static int count = 1000000;
    final static short maxNumber = 10000;






    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Самосортирующиеся структуры данных");

        LineChart<Number, Number> chart = new LineChart<>(new NumberAxis(), new NumberAxis());
        chart.setTitle("График зависимости дельты от числа запросов");
        chart.setCreateSymbols(false);
        chart.setPrefSize(600, 600);
        XYChart.Series series = new XYChart.Series();
        //series.setName("");
        ObservableList<XYChart.Data> dates = FXCollections.observableArrayList();
        series.setData(dates);
        chart.getData().add(series);


        Pane pane = new VBox();
        Pane controlPane = new HBox(20);

        ComboBox comboBox = new ComboBox();
        controlPane.getChildren().add(comboBox);
        comboBox.getItems().addAll("Обмен элементов", "Сдвиг элемента");
        comboBox.getSelectionModel().select(0);




        //arraySwap.setShift(3);


        class requests extends Task{
            @Override
            public Void call() {
                Chances chances = new Chances(maxNumber);
                short[] t = new short[maxNumber];
                for (short i = 0; i < maxNumber; i++) {
                    t[i] = i;
                }
                arraySortingItself arraySwap = arraySortingItself.createArray.create(t, comboBox.getSelectionModel().getSelectedIndex());
                Platform.runLater(() -> {
                    dates.clear();
                    dates.add(new XYChart.Data(0, arraySwap.getDelta(chances)));
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j1 = 1; j1 < 100; j1++) {

                    for (int i = 0; i < 1000; i++) {
                        arraySwap.search(chances.nextNumber());

                    }
                    int delta = arraySwap.getDelta(chances);
                    int finalJ1 = j1;
                    Platform.runLater(()-> dates.add(new XYChart.Data(finalJ1, delta)));

                }
                return null;
                /*
                final int max = 1000000;
                for (int i=1; i<=max; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);
                }
                */

            }
        }





        Button button = new Button("Запуск");
        controlPane.getChildren().add(button);
        controlPane.setPadding(new Insets(20, 20, 10, 20));
        button.setOnAction((event)-> new Thread(new requests()).start());
        pane.getChildren().add(controlPane);
        pane.getChildren().add(chart);
        Scene scene = new Scene(pane, 600, 600);
        primaryStage.setScene(scene);

        primaryStage.show();







    }
}
