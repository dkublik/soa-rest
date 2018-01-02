package pl.dk.soa.service.device;

import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.dk.soa.service.util.RangeTransformer;

import static java.lang.Math.*;

@Service
public class Car {

    public static final int MAX_GEAR = 6;

    public static final int MAX_THROTTLE_LEVEL = 100;

    public static final double WHEEL_DIAMETER = 0.6;

    private int coolantTemperature;

    private int manifoldPressure;

    @Getter
    @Setter
    private int throttleLevel;

    @Getter
    @Setter
    private int accPedalPosition;

    private int rpm;

    @Getter
    private int speed;

    @Getter
    @Setter
    private int currentGear = 1;

    public int getRpm() {
        double metersPerMinute = speed * 1000d / 60;
        double wheelCircumference = PI * WHEEL_DIAMETER;
        rpm = (int) (metersPerMinute / wheelCircumference);
        double slope = (9 + random()) / 10;
        rpm = (int) (slope * rpm);
        return rpm;
    }

    public int getManifoldPressure() {
        int pressure = RangeTransformer.transformToRange(throttleLevel, new int[]{0, 10, 100}, new int[]{0, 40, 100});
        double rFactor = (8 + random() * 4) / 10;
        manifoldPressure = (int) (pressure * rFactor);
        return manifoldPressure;
    }

    public int getCoolantTemperature() {
        int maxTemperature =RangeTransformer.transformToRange(throttleLevel, new int[]{0, 100}, new int[]{30, 100});
        int tempIncrease = 10;
        if (maxTemperature < coolantTemperature) {
            coolantTemperature = max(coolantTemperature - tempIncrease, maxTemperature);
        } else {
            coolantTemperature = min(coolantTemperature + tempIncrease, maxTemperature);
        }
        double rFactor = (8 + random() * 4) / 10;
        coolantTemperature = (int) (coolantTemperature * rFactor);
        return coolantTemperature;
    }

    @Scheduled(fixedDelay=100)
    public void adjustSpeed() {
        int maxSpeed = (int)(throttleLevel / 100d * 220);
        int speedIncrease = min((int) (throttleLevel / 10d), 2);
        if (maxSpeed < speed) {
            speed = max(speed - 1, maxSpeed);
        } else {
            speed = min(speed + speedIncrease, maxSpeed);
        }
    }

}
