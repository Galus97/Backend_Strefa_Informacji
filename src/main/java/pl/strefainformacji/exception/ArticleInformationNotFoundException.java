package pl.strefainformacji.exception;

public class ArticleInformationNotFoundException extends RuntimeException {
    public ArticleInformationNotFoundException(String message) {
        super(message);
    }
}
