package hu.onlinepizzeria.server.core.exceptions;

public class AlreadyExists extends Throwable {
    public AlreadyExists(String className) {
        super("Ilyen azonosítóval már létezik egy " + className + "!");
    }
}
