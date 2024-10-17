package ir.sadeqam.Reactive_Spring_RBAC_JWT.service.exceptions;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }
}
