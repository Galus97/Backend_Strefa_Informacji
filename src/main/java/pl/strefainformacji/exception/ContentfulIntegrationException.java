package pl.strefainformacji.exception;

public class ContentfulIntegrationException extends RuntimeException {
    public ContentfulIntegrationException(String message) {
        super(message);
    }

    public ContentfulIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
