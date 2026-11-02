package exception;

/** Thrown when a student is evaluated for a course not registered by him/her. */
public class NotRegisteredException extends ExamException {
    public NotRegisteredException(String studentId, String courseCode) {
        super("Student " + studentId + " is not registered for course " + courseCode);
    }
}
