package pl.dk.soa.evolution.alloperations;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dk.soa.evolution.candidate.CandidateService;
import pl.dk.soa.evolution.company.CompanyService;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/all-operations", produces = APPLICATION_JSON_VALUE)
@Api(description = "all-operations")
class AllOperationsController {

    private final CandidateService candidateService;
    private final CompanyService companyService;

    AllOperationsController(CandidateService candidateService, CompanyService companyService) {
        this.candidateService = candidateService;
        this.companyService = companyService;
    }

    @PostMapping()
    @ApiOperation(value = "allOperations")
    ResponseEntity allOperations(@RequestBody Operation operation) {
        if (operation instanceof GetCandidateOperation) {
            return candidateService.getCandidate(((GetCandidateOperation)operation).getLogin())
                    .map(candidate -> new ResponseEntity<>(candidate, OK))
                    .orElse(new ResponseEntity<>(NOT_FOUND));
        } else if (operation instanceof AddCandidateOperation) {
            candidateService.addCandidate(((AddCandidateOperation)operation).getCandidate());
            return new ResponseEntity<>(CREATED);
        } else if (operation instanceof GetCompanyContentOperation) {
            return companyService.getCompanyContent(((GetCompanyContentOperation)operation).getCompanyName())
                    .map(companyContent -> new ResponseEntity<>(companyContent, OK))
                    .orElse(new ResponseEntity<>(NOT_FOUND));
        }
        return new ResponseEntity<>(METHOD_NOT_ALLOWED);
    }

}
