package otmankarim.U5W2D5.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Element with id " + id + " not found");
    }

    public NotFoundException(String mail) {
        super("Element with email " + mail + " not found");
    }
}
