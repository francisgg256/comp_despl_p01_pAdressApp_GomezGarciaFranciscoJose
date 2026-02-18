package es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.view;

import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.TabPane;

import java.text.DateFormatSymbols;
import java.util.*;

/**
 * The controller for the birthday statistics view.
 *
 * @author Marco Jakob
 */
public class BirthdayStatisticsController {

    @FXML private BarChart<String, Integer> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private PieChart pieChart;
    @FXML private LineChart<Number, Number> lineChart;
    @FXML private TabPane tabPane;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));
        xAxis.setCategories(monthNames);
    }


    public void selectTab(int tabIndex) {
        if (tabPane != null) {
            tabPane.getSelectionModel().select(tabIndex);
        }
    }


    public void setPersonData(ObservableList<Person> persons) {

        updateAllCharts(persons);


        persons.addListener((ListChangeListener<Person>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (Person p : c.getAddedSubList()) {
                        p.birthdayProperty().addListener((obs, oldV, newV) -> updateAllCharts(persons));
                    }
                }
            }
            updateAllCharts(persons);
        });


        for (Person p : persons) {
            p.birthdayProperty().addListener((obs, oldV, newV) -> updateAllCharts(persons));
        }
    }

    private void updateAllCharts(List<Person> persons) {
        updateBarChart(persons);
        updatePieChart(persons);
        updateLineChart(persons);
    }

    private void updateBarChart(List<Person> persons) {
        int[] monthCounter = new int[12];
        for (Person p : persons) {
            if (p.getBirthday() != null) {
                int month = p.getBirthday().getMonthValue() - 1;
                monthCounter[month]++;
            }
        }
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Birthdays");
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }
        barChart.getData().clear();
        barChart.getData().add(series);
    }

    private void updatePieChart(List<Person> persons) {
        int genZ = 0, millennials = 0, genX = 0, boomers = 0, others = 0;
        for (Person p : persons) {
            if (p.getBirthday() != null) {
                int year = p.getBirthday().getYear();
                if (year >= 1997 && year <= 2012) genZ++;
                else if (year >= 1981 && year <= 1996) millennials++;
                else if (year >= 1965 && year <= 1980) genX++;
                else if (year >= 1946 && year <= 1964) boomers++;
                else others++;
            }
        }
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Gen Z", genZ),
                new PieChart.Data("Millennials", millennials),
                new PieChart.Data("Gen X", genX),
                new PieChart.Data("Baby Boomers", boomers),
                new PieChart.Data("Others", others)
        );
        pieChart.setData(pieData);
    }

    private void updateLineChart(List<Person> persons) {
        Map<Integer, Integer> yearCounts = new TreeMap<>();
        for (Person p : persons) {
            if (p.getBirthday() != null) {
                int year = p.getBirthday().getYear();
                yearCounts.put(year, yearCounts.getOrDefault(year, 0) + 1);
            }
        }
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Births Trend");
        for (Map.Entry<Integer, Integer> entry : yearCounts.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        lineChart.getData().clear();
        lineChart.getData().add(series);
    }
}
