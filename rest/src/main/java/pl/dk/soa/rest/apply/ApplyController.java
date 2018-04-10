package pl.dk.soa.rest.apply;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dk.soa.rest.service.Application;
import pl.dk.soa.rest.service.ApplyService;
import pl.dk.soa.rest.service.StoredApplication;

import java.util.*;

import static java.util.Comparator.comparing;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/job-applications", produces = APPLICATION_JSON_VALUE)
@Api(description = "job applications")
class ApplyController {

    private final ApplyService applyService;

    ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @PostMapping()
    @ApiOperation(value = "apply for job")
    ResponseEntity<AppIdResponse> applyForJob(@RequestBody Application application) {
        StoredApplication storedApplication = new StoredApplication(application);
        applyService.addApplication(storedApplication);
        return new ResponseEntity<>(new AppIdResponse(storedApplication.getId()), ACCEPTED);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "apply for job with app ID")
    ResponseEntity<AppIdResponse> applyForJob(@PathVariable String id, @RequestBody Application application) {
        StoredApplication storedApplication = new StoredApplication(id, application);
        applyService.addApplication(storedApplication);
        return new ResponseEntity<>(new AppIdResponse(id), ACCEPTED);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "show Application")
    ResponseEntity<StoredApplication> showApplication(@PathVariable String id) {
        return applyService.getApplication(id)
                .map(app -> new ResponseEntity<>(app, OK))
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping()
    @ApiOperation(value = "show Applications")
    List<StoredApplication> showApplications() {
        List<StoredApplication> applicationList = new ArrayList<>(applyService.getApplications());
        applicationList.sort(comparing(StoredApplication::getCreatedTime));
        return applicationList;
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "delete Application")
    ResponseEntity<Void> deleteApplication(@PathVariable String id) {
        applyService.remove(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
