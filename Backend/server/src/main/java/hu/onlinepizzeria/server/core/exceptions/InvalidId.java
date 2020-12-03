package hu.onlinepizzeria.server.core.exceptions;

public class InvalidId extends RuntimeException {
    public InvalidId(String className, String id) {
        super("Ilyen (" + id +") azonosítóval nem létezik " + className + "!");
    }
}
