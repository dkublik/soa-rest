package pl.dk.soa.service.ecu;

import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dk.soa.service.device.Car;
import pl.dk.soa.service.sensors.response.*;

import static pl.dk.soa.service.util.RangeTransformer.transformToRange;

@Service
@Getter
class EcuModule {

    static final String SENSORS_HOST = "http://localhost:8182";

    private final RestTemplate restTemplate;

    private final Car car;

    EcuModule(RestTemplate restTemplate, Car car) {
        this.restTemplate = restTemplate;
        this.car = car;
    }

    @Scheduled(fixedDelay=1000)
    public void adjustThrottle() {
        try {
            int pressure = restTemplate.getForObject(SENSORS_HOST + "/sensors/map", MapResponse.class).getPressure();
            int temperature = restTemplate.getForObject(SENSORS_HOST + "/sensors/cts", CtsResponse.class).getTemperature();
            int pedalPosition = restTemplate.getForObject(SENSORS_HOST + "/sensors/app", AppResponse.class).getPedalPosition();
            int throttleLevel = calculateThrottleLevel(pressure, temperature, pedalPosition);
            car.setThrottleLevel(throttleLevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int getThrottleLevel() {
        return car.getThrottleLevel();
    }

    private int calculateThrottleLevel(int pressure, int temperature, int pedalPosition) {
        int throttleLevel = transformToRange(pedalPosition, new int[]{0, 20, 40, 60, 80, 100}, new int[]{0, 10, 20, 40, 60, 100});
        if (temperature > 110) {
            throttleLevel = (int) (0.8 * throttleLevel);
        }
        if (pressure > 110) {
            throttleLevel = (int) (0.8 * throttleLevel);
        }
        return throttleLevel;
    }
}
