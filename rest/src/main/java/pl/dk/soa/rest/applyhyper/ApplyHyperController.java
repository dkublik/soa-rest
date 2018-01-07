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
@ExposesResourceFor(ExistingApplication.class)
class ApplyHyperController {

    private final ExistingApplicationResourceAssembler resourceAssembler;

    private Map<String, ExistingApplication> applications = new HashMap<>();

    ApplyHyperController(ExistingApplicationResourceAssembler existingApplicationResourceAssembler) {
        this.resourceAssembler = existingApplicationResourceAssembler;
    }

    @PostMapping()
    @ApiOperation(value = "apply for job")
    ResponseEntity<Resource<ExistingApplication>> applyForJob(@RequestBody Application application) {
        ExistingApplication existingApplication = new ExistingApplication(application);
        applications.put(existingApplication.getId(), existingApplication);
        return new ResponseEntity<>(resourceAssembler.toResource(existingApplication), ACCEPTED);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "apply for job with app ID")
    ResponseEntity<Resource<ExistingApplication>> applyForJob(@PathVariable String id, @RequestBody Application application) {
        ExistingApplication existingApplication = new ExistingApplication(id, application);
        applications.put(existingApplication.getId(), existingApplication);
        return new ResponseEntity<>(resourceAssembler.toResource(existingApplication), ACCEPTED);
    }

    @GetMapping()
    @ApiOperation(value = "show Application")
    ResponseEntity<Resources<ExistingApplication>> showApplications() {
        return new ResponseEntity<>(resourceAssembler.toResource(applications.values()), OK);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "show Application")
    ResponseEntity<Resource<ExistingApplication>> showApplication(@PathVariable String id) {
        ExistingApplication application = applications.get(id);
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
