package hu.onlinepizzeria.server.core.exceptions;

public class AlreadyExists extends RuntimeException {
    public AlreadyExists(String className) {
        super("Ilyen azonosítóval már létezik egy " + className + "!");
    }
}
