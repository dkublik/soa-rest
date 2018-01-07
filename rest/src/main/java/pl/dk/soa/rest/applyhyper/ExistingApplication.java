package pl.dk.soa.rest.applyhyper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.UUID.randomUUID;

@Getter
@Setter
@NoArgsConstructor
public class ExistingApplication extends Application {

    private String id;

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
