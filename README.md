# Quizy

Fetches a list of quizes from quizy.gazeta.pl

## Użycie

```bash
javaw -Drosette.api.key=<API_KEY> -Drosette.api.altUrl=<URL> -DresolveUnknown=true quizy-1.0SNAPSHOT-jar-with-dependencies.jar
```

### Właściwości

```properties
rosette.api.key - klucz API Rosetty
rosette.api.altUrl - URL Rosetty (opcjonalnie)
resolveUnknown - jeśli ustawione na 'true', próbuj ustalić język dla elementów UNKNOWN
```

### Rosetta

[https://www.rosette.com/](https://www.rosette.com/)