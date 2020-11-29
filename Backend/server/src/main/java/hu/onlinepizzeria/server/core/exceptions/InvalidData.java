package hu.onlinepizzeria.server.core.exceptions;

public class InvalidData extends Throwable {
    public InvalidData(String dataName) {
        super("Nem megfelelő adat: " + dataName);
    }
}
