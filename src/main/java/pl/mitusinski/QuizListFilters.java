package pl.mitusinski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mitusinski.quizfilters.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by Łukasz on 10.12.2017.
 *
 */
class QuizListFilters {

    ObservableList<Quiz> getObservableQuizList(QuizList quiz) {
        return applyFilterToObservableQuizList(quiz, new EmptyFilter());
    }

    ObservableList<Quiz> getObservableFilteredQuizList(QuizList quiz, String query) {
        return applyFilterToObservableQuizList(quiz, new QueryFilter(query));
    }

    ObservableList<Quiz> getObservableQuizListFilteredByLanguage(QuizList quiz, String language) {
        return applyFilterToObservableQuizList(quiz, new LanguageFilter(language));
    }

    private ObservableList<Quiz> applyFilterToObservableQuizList(QuizList quiz, QuizFilter filter) {
        List<Quiz> quizList = quiz.getQuizList();
        ObservableList<Quiz> list = FXCollections.observableArrayList();
        quizList.stream()
                .filter(new NotCorruptedFilter())
                .filter(filter)
                .forEach(list::add);
        Collections.reverse(list);
        return list;
    }
}
