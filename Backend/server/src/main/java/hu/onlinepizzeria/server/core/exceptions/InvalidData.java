package hu.onlinepizzeria.server.core.exceptions;

public class InvalidData extends RuntimeException {
    public InvalidData(String message) {
        super(message);
    }
}
