package pl.mitusinski;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Controller {
    private Main main;

    @FXML
    private TableView<Quiz> tableView;

    @FXML
    private TableColumn<Quiz, String> idColumn;

    @FXML
    private TableColumn<Quiz, String> titleColumn;

    @FXML
    public TableColumn<Quiz, String> languageColumn;

    @FXML
    private TextField searchQuery;

    @FXML
    private void initialize() {
    }

    private QuizListFilters quizListFilters = new QuizListFilters();

    public void setMainHandle(Main main) {
        this.main = main;
        updateList();
    }

    public void setPrimaryStage(Stage primaryStage) {
        Stage primaryStage1 = primaryStage;
    }

    public void updateList() {
        ObservableList<Quiz> observableQuizList = quizListFilters.getObservableQuizList(main.getQuizList());
        updateTableData(observableQuizList);
    }

    public void openInBrowser() {
        try {
            int id = Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getId());
            Desktop.getDesktop().browse(new URI(QuizUpdater.getUrl(id)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void filterList() {
        String filterString = searchQuery.getText();
        updateTableData(quizListFilters.getObservableFilteredQuizList(main.getQuizList(), filterString));
    }

    public void refreshList() {
        QuizUpdater quizUpdater = new QuizUpdater();
        main.performQuizUpdate(quizUpdater, this);
        quizUpdater.update();
    }

    private void updateTableData(ObservableList<Quiz> observableList) {
        tableView.setItems(observableList);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        languageColumn.setCellValueFactory(cellData -> cellData.getValue().getLanguageProperty());
    }

    public void polishOnly() {
        updateTableData(quizListFilters.getObservableQuizListFilteredByLanguage(main.getQuizList(), QuizUpdater.PL));
    }

    public void spanishOnly() {
        updateTableData(quizListFilters.getObservableQuizListFilteredByLanguage(main.getQuizList(), QuizUpdater.ES));
    }

    public void englishOnly() {
        updateTableData(quizListFilters.getObservableQuizListFilteredByLanguage(main.getQuizList(), QuizUpdater.EN));
    }

    public void unknownOnly() {
        updateTableData(quizListFilters.getObservableQuizListFilteredByLanguage(main.getQuizList(), QuizUpdater.UNKNOWN));
    }

    public void allLanguages() {
        updateTableData(quizListFilters.getObservableQuizList(main.getQuizList()));
    }
}
