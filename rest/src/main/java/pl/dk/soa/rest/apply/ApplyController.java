package pl.dk.soa.rest.apply;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.Comparator.comparing;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/job-applications", produces = APPLICATION_JSON_VALUE)
@Api(description = "job applications")
class ApplyController {

    private Map<String, ExistingApplication> applications = new HashMap<>();

    @PostMapping()
    @ApiOperation(value = "apply for job")
    ResponseEntity<AppIdResponse> applyForJob(@RequestBody Application application) {
        ExistingApplication existingApplication = new ExistingApplication(application);
        applications.put(existingApplication.getId(), existingApplication);
        return new ResponseEntity<>(new AppIdResponse(existingApplication.getId()), ACCEPTED);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "apply for job with app ID")
    ResponseEntity<AppIdResponse> applyForJob(@PathVariable String id, @RequestBody Application application) {
        ExistingApplication storedApplication = new ExistingApplication(id, application);
        applications.put(id, storedApplication);
        return new ResponseEntity<>(new AppIdResponse(id), ACCEPTED);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "show Application")
    ResponseEntity<ExistingApplication> showApplication(@PathVariable String id) {
        ExistingApplication application = applications.get(id);
        if (application == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(application, OK);
    }

    @GetMapping()
    @ApiOperation(value = "show Applications")
    List<ExistingApplication> showApplications() {
        List<ExistingApplication> applicationList = new ArrayList<>(applications.values());
        applicationList.sort(comparing(ExistingApplication::getCreatedTime));
        return applicationList;
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "delete Application")
    ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        applications.remove(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
