package pl.mitusinski.quizfilters;

import pl.mitusinski.Quiz;

/**
 * Created by Łukasz on 10.12.2017.
 * github.com/lmitu
 */
public class EmptyFilter implements QuizFilter {
    @Override
    public boolean test(Quiz quiz) {
        return true;
    }
}
