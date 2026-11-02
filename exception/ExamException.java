package exception;

/** Custom exception for all exam and grading rule violations. */
public class ExamException extends Exception {
    public ExamException(String message) {
        super(message);
    }
}
