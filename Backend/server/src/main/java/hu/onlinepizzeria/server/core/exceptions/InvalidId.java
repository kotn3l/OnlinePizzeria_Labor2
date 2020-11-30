package hu.onlinepizzeria.server.core.exceptions;

public class InvalidId extends Throwable {
    public InvalidId(String className, String id) {
        super("Ilyen (" + id +") azonosítóval nem létezik " + className + "!");
    }
}
