package pl.strefainformacji.exception;

public class SpecificArticleNotFoundException extends RuntimeException {
    public SpecificArticleNotFoundException(String message) {
        super(message);
    }
}
