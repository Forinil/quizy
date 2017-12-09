package pl.mitusinski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Łukasz on 18.05.2017.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "quizlist")
public class QuizList {

    public QuizList() {
    }

    public QuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @XmlElement(name = "quiz")
    private List<Quiz> quizList = new ArrayList<>();

    public ObservableList<Quiz> getObservableQuizList() {
        ObservableList<Quiz> list = FXCollections.observableArrayList();
        quizList.stream()
                .filter(q -> q != null && "N".equals(q.getCorrupted()))
                .forEach(list::add);
        Collections.reverse(list);
        return list;
    }

    public ObservableList<Quiz> getObservableFilteredQuizList(String query) {
        ObservableList<Quiz> list = FXCollections.observableArrayList();
        quizList.stream()
                .filter(q -> q != null && "N".equals(q.getCorrupted()))
                .filter(q -> q.getTitle().contains(query))
                .forEach(list::add);
        Collections.reverse(list);
        return list;
    }

    public ObservableList<Quiz> getObservableQuizListFilteredByLanguage(String language) {
        ObservableList<Quiz> list = FXCollections.observableArrayList();
        quizList.stream()
                .filter(q -> q != null && "N".equals(q.getCorrupted()))
                .filter(q -> q.getLanguage().equals(language))
                .forEach(list::add);
        Collections.reverse(list);
        return list;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }
}
