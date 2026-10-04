package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.TrafficSignal;

public class SignalRepository {

    private List<TrafficSignal> signals;

    public SignalRepository() {
        this.signals = new ArrayList<>();
    }

    public void add(TrafficSignal signal) {
        signals.add(signal);
    }

    public Optional<TrafficSignal> findById(String signalId) {
        return signals.stream()
                .filter(s -> s.getSignalId().equals(signalId))
                .findFirst();
    }

    public List<TrafficSignal> getAll() {
        return signals;
    }
}
