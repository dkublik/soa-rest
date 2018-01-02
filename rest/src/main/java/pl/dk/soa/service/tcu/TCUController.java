package pl.dk.soa.service.tcu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dk.soa.service.tcu.response.CurrentGearResponse;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/tcu", produces = APPLICATION_JSON_VALUE)
@Api(description = "transmission control unit controller")
class TCUController {

    private final TcuModule tcuModule;

    TCUController(TcuModule tcuModule) {
        this.tcuModule = tcuModule;
    }

    @GetMapping(path = "/current-gear")
    @ApiOperation(value = "current-gear")
    ResponseEntity<CurrentGearResponse> currentGear() {
        return new ResponseEntity<>(new CurrentGearResponse(tcuModule.getCurrentGear()), OK);
    }

}
