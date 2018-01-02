package pl.dk.soa.service.ecu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dk.soa.service.ecu.response.ThrottleResponse;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/ecu", produces = APPLICATION_JSON_VALUE)
@Api(description = "engine control unit controller")
class ECUController {

    private final EcuModule ecuModule;

    ECUController(EcuModule ecuModule) {
        this.ecuModule = ecuModule;
    }

    @GetMapping(path = "/throttle")
    @ApiOperation(value = "throttle")
    ResponseEntity<ThrottleResponse> throttle() {
        return new ResponseEntity<>(new ThrottleResponse(ecuModule.getThrottleLevel()), OK);
    }

}
