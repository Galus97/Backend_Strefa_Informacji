package pl.strefainformacji.exception;

public class ArticleImagesNotFoundException extends RuntimeException {
    public ArticleImagesNotFoundException(String message) {
        super(message);
    }
}
