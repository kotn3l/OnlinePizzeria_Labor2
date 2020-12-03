package hu.onlinepizzeria.server.core.exceptions;

public class UnauthorizedEx extends RuntimeException {
    public UnauthorizedEx(String message) {
        super(message);
    }
}
