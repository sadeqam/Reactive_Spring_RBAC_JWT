package ir.sadeqam.Reactive_Spring_RBAC_JWT.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class DataNotFoundException extends ResponseStatusException {

    public DataNotFoundException(){
        super(HttpStatus.NOT_FOUND);
    }

    public DataNotFoundException(String reason){
        super(HttpStatus.NOT_FOUND, reason);
    }

    public DataNotFoundException(HttpStatusCode status) {
        super(status);
    }

    public DataNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public DataNotFoundException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }

    public DataNotFoundException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    protected DataNotFoundException(HttpStatusCode status, String reason, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(status, reason, cause, messageDetailCode, messageDetailArguments);
    }
}
