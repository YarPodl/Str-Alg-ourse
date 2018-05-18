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
import javafx.scene.control.cell.PropertyValueFactory;
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
    private static int shift = 1;


    private Button buttonStart;
    private Button buttonClear;
    private Button buttonCancel;
    private ComboBox comboBox;
    private LineChart<Number, Number> chartDelta;
    private LineChart<Number, Number> chartCmp;
    private TableView<XYChart.Data> tableViewDelta;
    private TableView<XYChart.Data> tableViewCmp;
    private ObservableList <String> items;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Самосортирующиеся структуры данных");

        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis(0, 3500, 250);
        xAxis1.setLabel ("Число запросов, ед");
        chartDelta = new LineChart<>(xAxis1, yAxis1);
        chartDelta.setTitle("График зависимости дельты от числа запросов");
        chartDelta.setCreateSymbols(false);
        chartDelta.setPrefSize(600, 600);

        final NumberAxis xAxis2 = new NumberAxis();
        final NumberAxis yAxis2 = new NumberAxis( );
        xAxis2.setLabel ("Число сравнений, ед");
        chartCmp = new LineChart<>(xAxis2, yAxis2);
        chartCmp.setTitle("График зависимости количества сравнений от числа запросов");
        chartCmp.setCreateSymbols(false);
        chartCmp.setPrefSize(600, 600);

        tableViewDelta = new TableView<>();

        TableColumn<XYChart.Data, Integer> tableColumn1 = new TableColumn<>("Число запросов");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("XValue"));
        tableViewDelta.getColumns().add(tableColumn1);

        TableColumn<XYChart.Data, Integer> tableColumn2 = new TableColumn<>("Дельта");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("YValue"));
        tableViewDelta.getColumns().add(tableColumn2);

        tableViewCmp = new TableView<>();

        TableColumn<XYChart.Data, Integer> tableColumn3 = new TableColumn<>("Число запросов");
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("XValue"));
        tableViewCmp.getColumns().add(tableColumn3);

        TableColumn<XYChart.Data, Integer> tableColumn4 = new TableColumn<>("Количество сравнений");
        tableColumn4.setCellValueFactory(new PropertyValueFactory<>("YValue"));
        tableViewCmp.getColumns().add(tableColumn4);


        ListView<String> listView = new ListView<>();
        items = FXCollections.observableArrayList ();
        listView.setItems (items);
        listView.setStyle("-fx-font-size: 14pt");


        TabPane tabPane = new TabPane(new Tab("График дельты", chartDelta)
                , new Tab("График числа сравнений", chartCmp)
                , new Tab("Таблица дельты", tableViewDelta)
                , new Tab("Таблица числа сравнений", tableViewCmp)
                , new Tab("Характеристики", listView));


        Pane pane = new VBox();
        Pane controlPane = new HBox(10);
        Pane resultPane = new HBox(10);


        Label resultLabel = new Label();
        resultPane.getChildren().add(resultLabel);

        comboBox = new ComboBox();
        controlPane.getChildren().add(comboBox);
        comboBox.getItems().addAll(
                "Обмен элементов",
                "Вставка в начало",
                "Комбинированный",
                "C изменяемым сдвигом",
                "Совмещенный"
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






        buttonStart = new Button("Запустить"){{
            setDefaultButton(true);

        }};
        buttonCancel = new Button("Остановить"){{
            setCancelButton(true);
        }};
        buttonClear = new Button("Очистить");

        buttonStart.setOnAction((event)-> {
            thread = new Thread(new requests(shift));
            thread.start();
        });
        buttonCancel.setOnAction((event)-> {
            thread.interrupt();
        });
        buttonClear.setOnAction((event)-> {
            thread.interrupt();
            chartDelta.getData().clear();
            items.clear();
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
        pane.getChildren().add(tabPane);
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





    class requests extends Task{
        private requests(int param) {
            super();
            this.param = param;
        }

        private int param;
        private int prevDelta = Integer.MAX_VALUE;
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

            String name = Integer.toString(chartDelta.getData().size() + 1)
                    + " "
                    + comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());

            prevDelta = array.getDelta(chances) / maxNumber;

            XYChart.Series seriesDelta = new XYChart.Series();
            XYChart.Series seriesCmp = new XYChart.Series();
                /*TableColumn<String, Integer> tableColumn = new TableColumn<>(name);
                tableViewDelta.getColumns().add(tableColumn);*/
            ObservableList<XYChart.Data> datesDelta = FXCollections.observableArrayList();
            ObservableList<XYChart.Data> datesCmp = FXCollections.observableArrayList();
            tableViewDelta.setItems(datesDelta);
            tableViewCmp.setItems(datesCmp);
            seriesDelta.setData(datesDelta);
            seriesCmp.setData(datesCmp);
            Platform.runLater(() -> {
                seriesDelta.setName(name);
                seriesCmp.setName(name);
                //tableViewDelta.getColumns().add(new TableColumn<String, Integer>(name));
                chartDelta.getData().add(seriesDelta);
                chartCmp.getData().add(seriesCmp);
                datesDelta.add(new XYChart.Data(0, prevDelta));
                datesCmp.add(new XYChart.Data(0, maxNumber/2));
            });
            int sost = 0;

            for (int j = 1; j <= countOfTact; j++) {

                if (Thread.interrupted()) {
                    break;
                }
                int sum = 0;
                for (int i = 0; i < lengthOfTact; i++) {
                    array.search(chances.nextNumber());
                    sum += array.getCountCmp();
                }
                int delta = array.getDelta(chances) / maxNumber;
                int finalJ1 = j * lengthOfTact;
                int finalJ2 = sum / lengthOfTact;
                Platform.runLater(()-> {
                    datesDelta.add(new XYChart.Data(finalJ1, delta));
                    datesCmp.add(new XYChart.Data(finalJ1, finalJ2));
                });
                if (((delta - prevDelta) > 0) && sost < 3){
                    if (sost > 1) {
                        Platform.runLater(() -> {
                            items.add(name
                                    + ":\tМинимальная дельта:\t"
                                    + Integer.toString(delta)
                                    + ",\tколичество запросов:\t"
                                    + Integer.toString(finalJ1));
                        });
                    }
                    sost++;
                }
                prevDelta = delta;
            }
            buttonStart.setDisable(false);
            buttonCancel.setDisable(true);
            return null;
        }
    }
}
