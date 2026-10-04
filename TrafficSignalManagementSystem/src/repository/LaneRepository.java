package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Lane;

public class LaneRepository {

    private List<Lane> lanes;

    public LaneRepository() {
        this.lanes = new ArrayList<>();
    }

    public void add(Lane lane) {
        lanes.add(lane);
    }

    public Optional<Lane> findById(String laneId) {
        return lanes.stream()
                .filter(l -> l.getLaneId().equals(laneId))
                .findFirst();
    }

    public List<Lane> getAll() {
        return lanes;
    }
}