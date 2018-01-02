package pl.dk.soa.rest.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.time.Instant.now;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/listings", produces = APPLICATION_JSON_VALUE)
@Api(description = "job listings")
class ListingsController {

    @GetMapping(path = "/recommended-for-user/{userHashId}/language/{language}")
    @ApiOperation(value = "listings recommended for user")
    ResponseEntity<List<Offer>> recommendedForUser(@PathVariable String userHashId, @PathVariable String language) {
        List<Offer> offers = asList(
                new Offer(randomUUID(), "Senior Java Developer", "Google", "Silicon Valley", now(), "http://www.google.com/offers/12121212"),
                new Offer(randomUUID(), "Senior QA Engineer", "Google", "Silicon Valley", now(), "http://www.google.com/offers/007")
        );
        return new ResponseEntity<>(offers, OK);
    }

}
