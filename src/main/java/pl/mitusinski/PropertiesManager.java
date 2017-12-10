package pl.mitusinski;

import java.util.Optional;

/**
 * Created by Łukasz on 10.12.2017.
 * github.com/lmitu
 */
public class PropertiesManager {

    // property codes
    private static final String KEY_PROP_NAME = "rosette.api.key";
    private static final String URL_PROP_NAME = "rosette.api.altUrl";
    private static final String RESOLVE_UNKNOWNS_PROP_NAME = "resolveUnknown";

    Boolean getResolveUnknownsFromSystemProperty() {
        String resolveUnknowns = System.getProperty(RESOLVE_UNKNOWNS_PROP_NAME);
        return resolveUnknowns != null && resolveUnknowns.trim().length() >= 1 && Boolean.parseBoolean(resolveUnknowns.trim());
    }

    public Optional<String> getApiKeyFromSystemProperty() {
        String apiKeyStr = System.getProperty(KEY_PROP_NAME);
        if (apiKeyStr == null || apiKeyStr.trim().length() < 1) {
            System.out.println("Error: API key is not set");
            return Optional.empty();
        }
        return Optional.of(apiKeyStr.trim());
    }

    public String getAltUrlFromSystemProperty() {
        String altUrlStr = System.getProperty(URL_PROP_NAME);
        if (altUrlStr == null || altUrlStr.trim().length() < 1) {
            altUrlStr = "https://api.rosette.com/rest/v1";
        }
        return altUrlStr.trim();
    }
}
