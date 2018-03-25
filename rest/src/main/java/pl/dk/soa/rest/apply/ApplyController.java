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

    private Map<String, StoredApplication> applications = new HashMap<>();

    @PostMapping()
    @ApiOperation(value = "apply for job")
    ResponseEntity<AppIdResponse> applyForJob(@RequestBody Application application) {
        StoredApplication storedApplication = new StoredApplication(application);
        applications.put(storedApplication.getId(), storedApplication);
        return new ResponseEntity<>(new AppIdResponse(storedApplication.getId()), ACCEPTED);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "apply for job with app ID")
    ResponseEntity<AppIdResponse> applyForJob(@PathVariable String id, @RequestBody Application application) {
        StoredApplication storedApplication = new StoredApplication(id, application);
        applications.put(id, storedApplication);
        return new ResponseEntity<>(new AppIdResponse(id), ACCEPTED);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "show Application")
    ResponseEntity<StoredApplication> showApplication(@PathVariable String id) {
        StoredApplication application = applications.get(id);
        if (application == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(application, OK);
    }

    @GetMapping()
    @ApiOperation(value = "show Applications")
    List<StoredApplication> showApplications() {
        List<StoredApplication> applicationList = new ArrayList<>(applications.values());
        applicationList.sort(comparing(StoredApplication::getCreatedTime));
        return applicationList;
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "delete Application")
    ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        applications.remove(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
