package pl.dk.soa.rest.service;

import lombok.Getter;

import java.time.Instant;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class StoredApplication extends Application {

    private String id;
    private Instant createdTime = now();

    public StoredApplication(Application application) {
        this(randomUUID().toString(), application);
    }

    public StoredApplication(String id, Application application) {
        this.id = id;
        setFirstName(application.getFirstName());
        setLastName(application.getLastName());
        setEmail(application.getEmail());
        setMessageToRecruiter(application.getMessageToRecruiter());
        setListingId(application.getListingId());
        setCompanyId(application.getCompanyId());
    }

}
