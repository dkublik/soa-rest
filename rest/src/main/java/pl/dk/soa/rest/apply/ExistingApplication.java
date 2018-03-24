package pl.dk.soa.rest.apply;

import lombok.Getter;

import java.time.Instant;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class ExistingApplication extends Application {

    private String id;
    private Instant createdTime = now();

    ExistingApplication(Application application) {
        this(randomUUID().toString(), application);
    }

    ExistingApplication(String id, Application application) {
        this.id = id;
        setFirstName(application.getFirstName());
        setLastName(application.getLastName());
        setEmail(application.getEmail());
        setMessageToRecruiter(application.getMessageToRecruiter());
        setListingId(application.getListingId());
        setCompanyId(application.getCompanyId());
    }

}
