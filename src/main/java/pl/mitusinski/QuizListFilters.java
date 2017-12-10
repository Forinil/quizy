package pl.mitusinski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.List;

/**
 * Created by Łukasz on 10.12.2017.
 */
public class QuizListFilters {

    public ObservableList<Quiz> getObservableQuizList(QuizList quiz) {
        List<Quiz> quizList = quiz.getQuizList();
        ObservableList<Quiz> list = FXCollections.observableArrayList();
        quizList.stream()
                .filter(q -> q != null && "N".equals(q.getCorrupted()))
                .forEach(list::add);
        Collections.reverse(list);
        return list;
    }

    public ObservableList<Quiz> getObservableFilteredQuizList(QuizList quiz, String query) {
        List<Quiz> quizList = quiz.getQuizList();
        ObservableList<Quiz> list = FXCollections.observableArrayList();
        quizList.stream()
                .filter(q -> q != null && "N".equals(q.getCorrupted()))
                .filter(q -> q.getTitle().contains(query))
                .forEach(list::add);
        Collections.reverse(list);
        return list;
    }

    public ObservableList<Quiz> getObservableQuizListFilteredByLanguage(QuizList quiz, String language) {
        List<Quiz> quizList = quiz.getQuizList();
        ObservableList<Quiz> list = FXCollections.observableArrayList();
        quizList.stream()
                .filter(q -> q != null && "N".equals(q.getCorrupted()))
                .filter(q -> q.getLanguage().equals(language))
                .forEach(list::add);
        Collections.reverse(list);
        return list;
    }
}
