package pl.dk.soa.evolution.company;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/company", produces = APPLICATION_JSON_VALUE)
@Api(description = "company", tags = "correct")
class CompanyController {

    private final CompanyService companyService;

    CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping({"{companyName}"})
    @ApiOperation(value = "get")
    ResponseEntity<CompanyContent> getCompanyContent(@PathVariable String companyName) {
        return companyService.getCompanyContent(companyName)
                .map(companyContent -> new ResponseEntity<>(companyContent, OK))
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

}
