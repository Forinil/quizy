package pl.mitusinski;

/**
 * Created by Łukasz on 11.12.2017.
 * github.com/lmitu
 */
class FileDataUpdater {

    private final DataFileManager dataFileManager = new DataFileManager();

    void updateQuizLanguage(String id, String languageCode, QuizList quizList) {
        quizList.getElementById(id).ifPresent(q -> {
            q.setLanguage(languageCode);
            dataFileManager.setQuizList(quizList);
        });
    }
}
