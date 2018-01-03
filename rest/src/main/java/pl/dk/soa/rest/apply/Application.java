package pl.dk.soa.rest.apply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    private String firstName;
    private String lastName;
    private String email;
    private String messageToRecruiter;
    private String listingId;
    private String companyId;

}
