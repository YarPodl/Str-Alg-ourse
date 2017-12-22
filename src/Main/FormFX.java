package Main;

import Array.Chances;
import Array.arraySortingItself;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class FormFX extends Application {

    private final static short maxNumber = 10000;

    private static int countOfTact = 100;
    private static int lengthOfTact = 100000;
    private static Thread thread;
    private static int shift;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Самосортирующиеся структуры данных");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis(0, 35000000, 2500000);
        xAxis.setLabel ("Число запросов, ед");
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("График зависимости дельты от числа запросов");
        chart.setCreateSymbols(false);
        chart.setPrefSize(600, 600);



        Pane pane = new VBox();
        Pane controlPane = new HBox(10);
        Pane resultPane = new HBox(10);


        Label resultLabel = new Label();
        resultPane.getChildren().add(resultLabel);

        ComboBox comboBox = new ComboBox();
        controlPane.getChildren().add(comboBox);
        comboBox.getItems().addAll("Обмен элементов",
                "Вставка в начало",
                "Комбинированный",
                "C изменяемым сдвигом",
                "Наилучший"
        );
        comboBox.getSelectionModel().select(0);

        Pattern pattern = Pattern.compile("\\d{0,8}");
        TextField textFieldTacts = new TextField("100"){{
            textProperty().addListener((observable, oldValue, newValue) -> {
                if (!pattern.matcher(newValue).matches()) {
                    setText(oldValue);
                }
                try {
                    countOfTact = Integer.parseInt(getText());
                } catch (NumberFormatException ignored) {
                }
            });
        }};
        TextField textFieldCount = new TextField("100000"){{
            textProperty().addListener((observable, oldValue, newValue) -> {
                if (!pattern.matcher(newValue).matches()) {
                    setText(oldValue);
                }
                try {
                    lengthOfTact = Integer.parseInt(getText());
                } catch (NumberFormatException ignored) {
                }
            });
        }};
        TextField textFieldShift = new TextField("1"){{
            textProperty().addListener((observable, oldValue, newValue) -> {
                if (!pattern.matcher(newValue).matches()) {
                    setText(oldValue);
                }
                try {
                    shift = Integer.parseInt(getText());
                } catch (NumberFormatException ignored) {
                }
            });
        }};




        //arraySwap.setShift(3);


        class requests extends Task{
            private requests(LineChart<Number, Number> chart, Button buttonStart, Button buttonCancel, int param) {
                super();
                this.chart = chart;
                this.buttonStart = buttonStart;
                this.buttonCancel = buttonCancel;
                this.param = param;
            }

            private int param;
            private LineChart<Number, Number> chart;
            private Button buttonStart;
            private Button buttonCancel;
            private int prevDelta;
            @Override
            public Void call() {
                buttonStart.setDisable(true);
                buttonCancel.setDisable(false);
                Chances chances = new Chances(maxNumber);
                short[] t = new short[maxNumber];
                for (short i = 0; i < maxNumber; i++) {
                    t[i] = i;
                }
                arraySortingItself array = arraySortingItself.createArray.create(t, comboBox.getSelectionModel().getSelectedIndex(), param);

                prevDelta = array.getDelta(chances);

                XYChart.Series series = new XYChart.Series();
                ObservableList<XYChart.Data> dates = FXCollections.observableArrayList();
                series.setData(dates);
                Platform.runLater(() -> {
                    series.setName(Integer.toString(chart.getData().size() + 1));
                    chart.getData().add(series);
                    dates.add(new XYChart.Data(0, prevDelta));
                });
                final int requestsToThousands = lengthOfTact/1000;

                for (int j = 1; j < countOfTact; j++) {

                    if (Thread.interrupted()) {
                        break;
                    }
                    for (int i = 0; i < lengthOfTact; i++) {
                        array.search(chances.nextNumber());
                    }
                    int delta = array.getDelta(chances);
                    int finalJ1 = j * lengthOfTact;
                    Platform.runLater(()-> dates.add(new XYChart.Data(finalJ1, delta)));
                    /*if ((delta - prevDelta) > 20 ){
                        Platform.runLater(()-> resultLabel.setText(
                                "Минимальная дельта: " +
                                Integer.toString(delta) +
                                ",  количество: " +
                                Integer.toString(finalJ1)
                        ));
                        break;
                    }
                    prevDelta = delta;*/
                }
                buttonStart.setDisable(false);
                buttonCancel.setDisable(true);
                return null;
            }
        }



        Button buttonStart = new Button("Запустить"){{
            setDefaultButton(true);

        }};
        Button buttonCancel = new Button("Остановить"){{
            setCancelButton(true);
        }};
        Button buttonClear = new Button("Очистить");

        buttonStart.setOnAction((event)-> {
            thread = new Thread(new requests(chart, buttonStart, buttonCancel, shift));
            thread.start();
        });
        buttonCancel.setOnAction((event)-> {
            thread.interrupt();
        });
        buttonClear.setOnAction((event)-> {
            thread.interrupt();
            chart.getData().clear();
        });


        controlPane.getChildren().add(buttonStart);
        controlPane.getChildren().add(buttonCancel);
        controlPane.getChildren().add(buttonClear);
        controlPane.getChildren().add( new VBox(){{
            getChildren().add(new Label("Количество тактов"));
            getChildren().add(textFieldTacts);
        }});
        controlPane.getChildren().add(new VBox(){{
            getChildren().add(new Label("Количество запросов в такте"));
            getChildren().add(textFieldCount);
        }});
        controlPane.getChildren().add(new VBox(){{
            getChildren().add(new Label("Сдвиг"));
            getChildren().add(textFieldShift);
        }});

        controlPane.setPadding(new Insets(20, 20, 10, 20));
        pane.getChildren().add(controlPane);
        pane.getChildren().add(chart);
        pane.getChildren().add(resultPane);
        Scene scene = new Scene(pane, 1000, 600);
        primaryStage.setScene(scene);

        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            if ((thread != null) && thread.isAlive()){
                thread.interrupt();
            }
        });







    }
}
