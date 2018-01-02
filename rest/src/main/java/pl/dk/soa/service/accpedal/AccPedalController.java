package pl.dk.soa.service.accpedal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dk.soa.service.device.Car;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/acc", produces = APPLICATION_JSON_VALUE)
@Api(description = "controlling acceleration pedal position")
class AccPedalController {

    private final Car car;

    AccPedalController(Car car) {
        this.car = car;
    }

    @PostMapping
    @ApiOperation(value = "pedal position")
    ResponseEntity<Void> setPedalPosition(@RequestBody(required = false) Map<String, String> body) {
        Integer pedalPosition = Integer.parseInt(body.get("pedalPosition"));
        car.setAccPedalPosition(pedalPosition);
        return new ResponseEntity<>(OK);
    }

}
