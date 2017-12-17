package pl.mitusinski.language;

import com.basistech.rosette.api.HttpRosetteAPI;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.LanguageDetectionResult;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageResponse;
import pl.mitusinski.PropertiesManager;

import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by Łukasz on 10.12.2017.
 * github.com/lmitu
 */
public class LanguageRecognizer {

    private final static PropertiesManager propertiesManager = new PropertiesManager();

    public String getLanguage(String corrupted, String title) {
        if (corrupted.equals("Y")) {
            return LanguageCodes.UNKNOWN;
        }

        Pattern spanish = Pattern.compile("[" + LanguageRegexPatterns.SPANISH_SYMBOLS + "]+");
        Pattern polish = Pattern.compile("[" + LanguageRegexPatterns.POLISH_SYMBOLS + "]+");

        if (spanish.matcher(title).find()) {
            return LanguageCodes.ES;
        }

        if (polish.matcher(title).find()) {
            return LanguageCodes.PL;
        }

        Optional<String> rosettaAPIKey = propertiesManager.getApiKeyFromSystemProperty();
        if (!rosettaAPIKey.isPresent()) {
            return LanguageCodes.UNKNOWN;
        } else {
            return detectLanguageViaRosetta(rosettaAPIKey.get(), title);
        }
    }

    private String detectLanguageViaRosetta(String rosettaAPIKey, String text) {
        String language = LanguageCodes.UNKNOWN;

        HttpRosetteAPI rosetteApi = new HttpRosetteAPI.Builder()
                .key(rosettaAPIKey)
                .url(propertiesManager.getAltUrlFromSystemProperty())
                .build();
        //The api object creates an http client, but to provide your own:
        //api.httpClient(CloseableHttpClient)
        DocumentRequest<LanguageOptions> request = new DocumentRequest.Builder<LanguageOptions>().content(text).build();
        LanguageResponse response = rosetteApi.perform(HttpRosetteAPI.LANGUAGE_SERVICE_PATH, request, LanguageResponse.class);
        Optional<LanguageDetectionResult> detectedLanguage = response.getLanguageDetections()
                .stream()
                .max(Comparator.comparing(LanguageDetectionResult::getConfidence));

        if (detectedLanguage.isPresent()) {
            LanguageDetectionResult languageDetectionResult = detectedLanguage.get();
            language = languageDetectionResult.getLanguage().ISO639_1().toUpperCase();
        }

        return language;
    }
}
