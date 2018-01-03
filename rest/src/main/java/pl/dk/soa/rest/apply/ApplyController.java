package pl.dk.soa.rest.apply;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/job-applications", produces = APPLICATION_JSON_VALUE)
@Api(description = "job applications")
class ApplyController {

    @PostMapping()
    @ApiOperation(value = "apply for job")
    ResponseEntity<Void> applyForJob(@RequestBody Application application) {
        return new ResponseEntity<>(ACCEPTED);
    }

}
