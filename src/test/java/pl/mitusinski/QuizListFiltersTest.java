package pl.mitusinski;

import javafx.collections.ObservableList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Łukasz on 10.12.2017.
 * github.com/lmitu
 */
public class QuizListFiltersTest {

    private final QuizListFilters filters = new QuizListFilters();

    private QuizList initializeQuizList() {
        List<Quiz> quizList = new ArrayList<>();
        quizList.add(new Quiz("0", "Jakiś polski quiz", "Jakiś polski quiz", new Date().toString(), "N", QuizUpdater.PL));
        quizList.add(new Quiz("1", "Quiz in english", "English quiz", new Date().toString(), "N", QuizUpdater.EN));
        quizList.add(new Quiz("2", "Quiz español", "Quiz español", new Date().toString(), "N", QuizUpdater.ES));
        quizList.add(new Quiz("3", "dsafasdf", "asfasdf", new Date().toString(), "N", QuizUpdater.UNKNOWN));
        quizList.add(new Quiz("4", "Quiz", "", new Date().toString(), "Y", QuizUpdater.UNKNOWN));
        return new QuizList(quizList);
    }

    @Test
    public void getObservableQuizListTest() {
        ObservableList<Quiz> result = filters.getObservableQuizList(initializeQuizList());
        assertTrue(matchQuizIdList(result, "0", "1", "2", "3"));
    }

    @Test
    public void getObservableFilteredQuizList() {
        ObservableList<Quiz> result = filters.getObservableFilteredQuizList(initializeQuizList(), "Quiz");
        assertTrue(matchQuizIdList(result, "1", "2"));
    }

    @Test
    public void getObservableQuizListFilteredByLanguagePL() {
        ObservableList<Quiz> result = filters.getObservableQuizListFilteredByLanguage(initializeQuizList(), QuizUpdater.PL);
        assertTrue(matchQuizIdList(result, "0"));
    }

    @Test
    public void getObservableQuizListFilteredByLanguageES() {
        ObservableList<Quiz> result = filters.getObservableQuizListFilteredByLanguage(initializeQuizList(), QuizUpdater.ES);
        assertTrue(matchQuizIdList(result, "2"));
    }

    @Test
    public void getObservableQuizListFilteredByLanguageEN() {
        ObservableList<Quiz> result = filters.getObservableQuizListFilteredByLanguage(initializeQuizList(), QuizUpdater.EN);
        assertTrue(matchQuizIdList(result, "1"));
    }

    @Test
    public void getObservableQuizListFilteredByLanguageUNKNOWN() {
        ObservableList<Quiz> result = filters.getObservableQuizListFilteredByLanguage(initializeQuizList(), QuizUpdater.UNKNOWN);
        assertTrue(matchQuizIdList(result, "3"));
    }

    private boolean matchQuizIdList(ObservableList<Quiz> list, String... ids) {
        long filteredQuizCount = list.stream().filter(quiz -> {
            for (String id : ids) {
                if (quiz.getId().equals(id))
                    return true;
            }
            return false;
        }).count();
        return filteredQuizCount == ids.length;
    }

}
