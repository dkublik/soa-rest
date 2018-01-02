package pl.dk.soa.service.sensors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dk.soa.service.device.Car;
import pl.dk.soa.service.sensors.response.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/sensors", produces = APPLICATION_JSON_VALUE)
@Api(description = "multiple sensors controller")
class SensorsController {

    private final Car car;

    SensorsController(Car car) {
        this.car = car;
    }

    @GetMapping(path = "/app")
    @ApiOperation(value = "app")
    ResponseEntity<AppResponse> app() {
        return new ResponseEntity<>(new AppResponse(car.getAccPedalPosition()), OK);
    }

    @GetMapping(path = "/cts")
    @ApiOperation(value = "cts")
    ResponseEntity<CtsResponse> temperature() {
        return new ResponseEntity<>(new CtsResponse(car.getCoolantTemperature()), OK);
    }

    @GetMapping(path = "/map")
    @ApiOperation(value = "map")
    ResponseEntity<MapResponse> map() {
        return new ResponseEntity<>(new MapResponse(car.getManifoldPressure()), OK);
    }

    @GetMapping(path = "/rpm")
    @ApiOperation(value = "rpm")
    ResponseEntity<RpmResponse> rpm() {
        return new ResponseEntity<>(new RpmResponse(car.getRpm()), OK);
    }

    @GetMapping(path = "/vss")
    @ApiOperation(value = "vss")
    ResponseEntity<VssResponse> vss() {
        return new ResponseEntity<>(new VssResponse(car.getSpeed()), OK);
    }

}
