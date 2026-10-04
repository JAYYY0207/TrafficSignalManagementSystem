package service;

import algorithm.DensityCalculator;
import model.Lane;

public class DensityService {

    public int getDensity(Lane lane) {
        return DensityCalculator.calculateDensity(lane);
    }

    public String getDensityLevel(Lane lane) {
        int density = getDensity(lane);
        return DensityCalculator.densityLevel(density);
    }
}
