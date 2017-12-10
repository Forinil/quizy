package pl.mitusinski.quizfilters;

import pl.mitusinski.Quiz;

/**
 * Created by Łukasz on 10.12.2017.
 * github.com/lmitu
 */
public class LanguageFilter implements QuizFilter {

    private String language;

    public LanguageFilter(String language) {
        this.language = language;
    }

    @Override
    public boolean test(Quiz q) {
        return q.getLanguage().equals(language);
    }
}
