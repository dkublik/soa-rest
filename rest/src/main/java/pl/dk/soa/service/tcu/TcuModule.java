package pl.dk.soa.service.tcu;

import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dk.soa.service.device.Car;
import pl.dk.soa.service.ecu.response.ThrottleResponse;
import pl.dk.soa.service.sensors.response.RpmResponse;
import pl.dk.soa.service.sensors.response.VssResponse;

import static java.lang.Math.PI;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static pl.dk.soa.service.device.Car.*;
import static pl.dk.soa.service.util.RangeTransformer.transformToRange;

@Service
@Getter
class TcuModule {

    static final String SENSORS_HOST = "http://localhost:8182";

    static final String ECU_HOST = "http://localhost:8182";

    private final RestTemplate restTemplate;

    private final Car car;

    TcuModule(RestTemplate restTemplate, Car car) {
        this.restTemplate = restTemplate;
        this.car = car;
    }

    @Scheduled(fixedDelay=1000)
    public void adjustGear() {
        try {
            int speed = restTemplate.getForObject(SENSORS_HOST + "/sensors/vss", VssResponse.class).getSpeed();
            int rpm = restTemplate.getForObject(SENSORS_HOST + "/sensors/rpm", RpmResponse.class).getRpm();
            int throttleLevel = restTemplate.getForObject(ECU_HOST + "/ecu/throttle", ThrottleResponse.class).getLevel();
            int gear = calculateGear(speed, rpm, throttleLevel);
            car.setCurrentGear(gear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int getCurrentGear() {
        return car.getCurrentGear();
    }

    private int calculateGear(int speed, int rpm, int throttleLevel) {
        int gear = transformToRange(speed, new int[]{0, 20, 30, 45, 65, 220}, new int[]{1, 2, 3, 4, 5, 6});
        if ((MAX_THROTTLE_LEVEL - throttleLevel) < 5) {
            gear = min(MAX_GEAR, gear + 1);
        }
        if (goingUp(speed, rpm)) {
            gear = max(gear - 1, 1);
        } else if (goingDown(speed, rpm)) {
            gear = min(gear + 1, MAX_GEAR);
        }
        return gear;
    }

    private double expectedRpm(int speed) {
        double metersPerMinute = speed * 1000d / 60;
        double wheelCircumference = PI * WHEEL_DIAMETER;
        return metersPerMinute / wheelCircumference;
    }

    private boolean goingUp(int speed, int rpm) {
        return rpm - expectedRpm(speed) > 100;
    }

    private boolean goingDown(int speed, int rpm) {
        return expectedRpm(speed) - rpm > 100;
    }

}
