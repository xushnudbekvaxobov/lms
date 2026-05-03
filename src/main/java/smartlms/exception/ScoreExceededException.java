package smartlms.exception;

public class ScoreExceededException extends RuntimeException{
    public ScoreExceededException(String message) {
        super(message);
    }
}
