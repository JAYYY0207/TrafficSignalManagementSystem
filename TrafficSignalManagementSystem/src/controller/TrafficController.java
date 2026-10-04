package controller;

import enums.Direction;
import model.Intersection;
import service.TrafficService;

public class TrafficController {

    private TrafficService trafficService;

    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    public int getWaitingVehicleCount(Intersection intersection) {
        return trafficService.getTotalVehiclesWaiting(intersection);
    }

    public void processDirection(Intersection intersection, Direction direction) {
        trafficService.processGreenDirection(intersection, direction);
    }
}