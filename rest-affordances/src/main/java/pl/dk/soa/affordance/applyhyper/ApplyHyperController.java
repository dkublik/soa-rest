package pl.dk.soa.affordance.applyhyper;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "/v3/job-applications", produces = HAL_JSON_VALUE)
@ExposesResourceFor(StoredApplication.class)
class ApplyHyperController {

    private final StoredApplicationResourceAssembler resourceAssembler;

    private Map<String, StoredApplication> applications = new HashMap<>();

    ApplyHyperController(StoredApplicationResourceAssembler storedApplicationResourceAssembler) {
        this.resourceAssembler = storedApplicationResourceAssembler;
    }

    @PostMapping()
    ResponseEntity<Resource<StoredApplication>> applyForJob(@RequestBody Application application) {
        StoredApplication storedApplication = new StoredApplication(application);
        applications.put(storedApplication.getId(), storedApplication);
        return new ResponseEntity<>(resourceAssembler.toResource(storedApplication), ACCEPTED);
    }

    @PutMapping("{id}")
    ResponseEntity<Resource<StoredApplication>> applyForJob(@PathVariable String id, @RequestBody Application application) {
        StoredApplication storedApplication = new StoredApplication(id, application);
        applications.put(storedApplication.getId(), storedApplication);
        return new ResponseEntity<>(resourceAssembler.toResource(storedApplication), ACCEPTED);
    }

    @GetMapping()
    ResponseEntity<Resources<StoredApplication>> showApplications() {
        return new ResponseEntity<>(resourceAssembler.toResource(applications.values()), OK);
    }

    @GetMapping("{id}")
    ResponseEntity<Resource<StoredApplication>> showApplication(@PathVariable String id) {
        StoredApplication application = applications.get(id);
        if (application == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(resourceAssembler.toResource(application), OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        applications.remove(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
