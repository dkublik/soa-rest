package pl.dk.soa.rest.applyhyper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(path = "/v2/job-applications", produces = HAL_JSON_VALUE)
@Api(description = "job applications")
@ExposesResourceFor(StoredApplication.class)
class ApplyHyperController {

    private final StoredApplicationResourceAssembler resourceAssembler;

    private Map<String, StoredApplication> applications = new HashMap<>();

    ApplyHyperController(StoredApplicationResourceAssembler storedApplicationResourceAssembler) {
        this.resourceAssembler = storedApplicationResourceAssembler;
    }

    @PostMapping()
    @ApiOperation(value = "apply for job")
    ResponseEntity<Resource<StoredApplication>> applyForJob(@RequestBody Application application) {
        StoredApplication storedApplication = new StoredApplication(application);
        applications.put(storedApplication.getId(), storedApplication);
        return new ResponseEntity<>(resourceAssembler.toResource(storedApplication), ACCEPTED);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "apply for job with app ID")
    ResponseEntity<Resource<StoredApplication>> applyForJob(@PathVariable String id, @RequestBody Application application) {
        StoredApplication storedApplication = new StoredApplication(id, application);
        applications.put(storedApplication.getId(), storedApplication);
        return new ResponseEntity<>(resourceAssembler.toResource(storedApplication), ACCEPTED);
    }

    @GetMapping()
    @ApiOperation(value = "show Application")
    ResponseEntity<Resources<StoredApplication>> showApplications() {
        return new ResponseEntity<>(resourceAssembler.toResource(applications.values()), OK);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "show Application")
    ResponseEntity<Resource<StoredApplication>> showApplication(@PathVariable String id) {
        StoredApplication application = applications.get(id);
        if (application == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(resourceAssembler.toResource(application), OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "show Application")
    ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        applications.remove(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
