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
@ExposesResourceFor(ExistingApplication.class)
class ApplyHyperController {

    private final ExistingApplicationResourceAssembler resourceAssembler;

    private Map<String, ExistingApplication> applications = new HashMap<>();

    ApplyHyperController(ExistingApplicationResourceAssembler existingApplicationResourceAssembler) {
        this.resourceAssembler = existingApplicationResourceAssembler;
    }

    @PostMapping()
    ResponseEntity<Resource<ExistingApplication>> applyForJob(@RequestBody Application application) {
        ExistingApplication existingApplication = new ExistingApplication(application);
        applications.put(existingApplication.getId(), existingApplication);
        return new ResponseEntity<>(resourceAssembler.toResource(existingApplication), ACCEPTED);
    }

    @PutMapping("{id}")
    ResponseEntity<Resource<ExistingApplication>> applyForJob(@PathVariable String id, @RequestBody Application application) {
        ExistingApplication existingApplication = new ExistingApplication(id, application);
        applications.put(existingApplication.getId(), existingApplication);
        return new ResponseEntity<>(resourceAssembler.toResource(existingApplication), ACCEPTED);
    }

    @GetMapping()
    ResponseEntity<Resources<ExistingApplication>> showApplications() {
        return new ResponseEntity<>(resourceAssembler.toResource(applications.values()), OK);
    }

    @GetMapping("{id}")
    ResponseEntity<Resource<ExistingApplication>> showApplication(@PathVariable String id) {
        ExistingApplication application = applications.get(id);
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
