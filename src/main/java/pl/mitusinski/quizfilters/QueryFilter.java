package pl.mitusinski.quizfilters;

import pl.mitusinski.Quiz;

/**
 * Created by Łukasz on 10.12.2017.
 * github.com/lmitu
 */
public class QueryFilter implements QuizFilter {
    private String query;

    public QueryFilter(String query) {
        this.query = query;
    }

    @Override
    public boolean test(Quiz q) {
        return q.getTitle().contains(query);
    }
}
