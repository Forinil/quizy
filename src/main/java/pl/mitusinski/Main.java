package pl.mitusinski;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;


public class Main extends Application {

    public static final int SCENE_HEIGHT = 400;
    public static final int SCENE_WIDTH = 600;
    public static final String MAIN_FORM = "/mainForm.fxml";
    public static final String DATA_FILE = "data.xml";
    public static final String APP_TITLE = "Quizy";
    public static final String MSG_UPDATE_IN_PROG = "Quizy - pobieranie...";
    public static final String MSG_UPDATE_COMPL = "Quizy - aktualna lista [ Nowych: %d]";

    private static Stage primaryStage;
    private static QuizList quizList = new QuizList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initScene();
    }

    private void initScene() {
        try {
            quizList = loadQuizFromXml();
            QuizUpdater quizUpdater = new QuizUpdater();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource(MAIN_FORM));
            Parent root = loader.load();
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            primaryStage.show();
            primaryStage.setResizable(false);

            Controller controller = loader.getController();
            controller.setMainHandle(this);
            controller.setPrimaryStage(primaryStage);
            performQuizUpdate(quizUpdater, controller);

            quizUpdater.update();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void performQuizUpdate(QuizUpdater quizUpdater, Controller controller) {
        quizUpdater.getSync().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue) {
                    primaryStage.setTitle(MSG_UPDATE_IN_PROG);
                } else {
                    quizList = loadQuizFromXml();
                    controller.updateList();
                    primaryStage.setTitle(String.format(MSG_UPDATE_COMPL, quizUpdater.getNewCount()));
                }
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private QuizList loadQuizFromXml() {
        File file = new File("data.xml");
        try {
            JAXBContext jaxb = JAXBContext.newInstance(QuizList.class);
            Unmarshaller unm = jaxb.createUnmarshaller();
            return (QuizList) unm.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new QuizList();
    }

    public QuizList getQuizList() {
        return quizList;
    }

    public void refreshQuizList() {
        quizList = loadQuizFromXml();
    }

}
