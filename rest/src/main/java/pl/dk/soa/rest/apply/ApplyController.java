package pl.dk.soa.rest.apply;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/job-applications", produces = APPLICATION_JSON_VALUE)
@Api(description = "job applications")
class ApplyController {

    private Map<String, Application> applications = new HashMap<>();

    @PostMapping()
    @ApiOperation(value = "apply for job")
    ResponseEntity<AppIdResponse> applyForJob(@RequestBody Application application) {
        String applicationId = randomUUID().toString();
        applications.put(applicationId, application);
        return new ResponseEntity<>(new AppIdResponse(applicationId), ACCEPTED);
    }

    @PutMapping("/{applicationId}")
    @ApiOperation(value = "apply for job with app ID")
    ResponseEntity<AppIdResponse> applyForJob(@PathVariable String applicationId, @RequestBody Application application) {
        applications.put(applicationId, application);
        return new ResponseEntity<>(new AppIdResponse(applicationId), ACCEPTED);
    }

    @GetMapping("/{applicationId}")
    @ApiOperation(value = "show Application")
    ResponseEntity<Application> showApplication(@PathVariable String applicationId) {
        Application application = applications.get(applicationId);
        if (application == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(application, OK);
    }

    @DeleteMapping("/{applicationId}")
    @ApiOperation(value = "show Application")
    ResponseEntity<Void> deleteApplication(@PathVariable String applicationId) {
        applications.remove(applicationId);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
