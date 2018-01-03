package pl.dk.soa.rest.listing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Offer {

    private UUID id;
    private String title;
    private String companyName;
    private String location;
    private Instant datePosted;
    private String url;

}
