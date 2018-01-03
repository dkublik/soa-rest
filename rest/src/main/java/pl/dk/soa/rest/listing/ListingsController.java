package pl.dk.soa.rest.listing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.Arrays.asList;
import static java.util.Locale.forLanguageTag;
import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/listings", produces = APPLICATION_JSON_VALUE)
@Api(description = "job listings")
class ListingsController {

    @GetMapping(path = "/recommended-for-user/{userHashId}/language/{language}")
    @ApiOperation(value = "listings recommended for user")
    ResponseEntity<List<Offer>> recommendedForUser(@PathVariable UUID userHashId, @PathVariable String language) {
        forLanguageTag(language).getISO3Language();
        return new ResponseEntity<>(offers(), OK);
    }

    @GetMapping(path = "/recommended-for-user/{userHashId}")
    @ApiOperation(value = "listings recommended for user")
    ResponseEntity<List<Offer>> recommendedForUserByParams(@PathVariable UUID userHashId, @RequestParam String language) {
        forLanguageTag(language).getISO3Language();
        return new ResponseEntity<>(offers(), OK);
    }

    private List<Offer> offers() {
        return asList(
                new Offer(randomUUID(), "Senior Java Developer", "Google", "Silicon Valley", now(), "http://www.google.com/offers/12121212"),
                new Offer(randomUUID(), "Senior QA Engineer", "Google", "Silicon Valley", now(), "http://www.google.com/offers/007")
        );
    }

}
