package pl.dk.soa.service.util;

public class RangeTransformer {

    public static int transformToRange(double value, double oldMin, double oldMax, double newMin, double newMax) {
        double oldP = (value - oldMin) / (oldMax - oldMin);
        return (int) (oldP * (newMax - newMin) + newMin);
    }

    public static int transformToRange(double value, int[] oldRanges, int[] newRanges) {
        for (int i = 0; i < oldRanges.length - 1; i ++) {
            if (value >= oldRanges[i] && value <= oldRanges[i+1]) {
                return transformToRange(value, oldRanges[i], oldRanges[i + 1], newRanges[i], newRanges[i+1]);
            }
        }
        throw new RuntimeException("not in range");
    }


}
