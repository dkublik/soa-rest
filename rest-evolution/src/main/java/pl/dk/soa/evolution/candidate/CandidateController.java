package pl.dk.soa.evolution.candidate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/candidates", produces = APPLICATION_JSON_VALUE)
@Api(description = "all-candidates", tags = "correct")
class CandidateController {

    private final CandidateService candidateService;

    CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping({"{login}"})
    @ApiOperation(value = "get")
    ResponseEntity<Candidate> getCandidate(@PathVariable String login) {
        return candidateService.getCandidate(login)
                .map(candidate -> new ResponseEntity<>(candidate, OK))
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @PostMapping()
    @ApiOperation(value = "add")
    ResponseEntity<Candidate> addCandidate(@RequestBody Candidate candidate) {
        candidateService.addCandidate(candidate);
        return new ResponseEntity<>(CREATED);
    }

}
