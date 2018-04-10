package pl.dk.soa.rest.listing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.Arrays.asList;
import static java.util.Locale.forLanguageTag;
import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/listings", produces = APPLICATION_JSON_VALUE)
@Api(description = "job listings")
class ListingsController {

    @GetMapping(path = "/{id}/language/{language}")
    @ApiOperation(value = "listing")
    Offer offer(@PathVariable UUID id, @PathVariable String language) {
        forLanguageTag(language).getISO3Language();
        Offer offer = offers().get(0);
        offer.setId(id);
        return offer;
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "listing")
    Offer offerByParam(@PathVariable UUID id, @RequestParam String language) {
        forLanguageTag(language).getISO3Language();
        Offer offer = offers().get(0);
        offer.setId(id);
        return offer;
    }

    @GetMapping
    @ApiOperation(value = "listings")
    List<Offer> offersAll() {
        return offers();
    }

    private List<Offer> offers() {
        return asList(
                new Offer(randomUUID(), "Senior Java Developer", "Google", "Silicon Valley", now(), "http://www.google.com/offers/12121212"),
                new Offer(randomUUID(), "Senior QA Engineer", "Google", "Silicon Valley", now(), "http://www.google.com/offers/007"),
                new Offer(randomUUID(), "Jedi Master", "Imperium", "The dark side", now(), "http://www.imperium.org.eu")
        );
    }

}
