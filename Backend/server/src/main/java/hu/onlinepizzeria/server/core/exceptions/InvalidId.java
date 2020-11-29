package hu.onlinepizzeria.server.core.exceptions;

public class InvalidId extends Throwable {
    public InvalidId(String className) {
        super("Ilyen azonosítóval nem létezik " + className + "!");
    }
}
