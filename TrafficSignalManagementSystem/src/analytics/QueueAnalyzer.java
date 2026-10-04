package analytics;

import model.Lane;
import java.util.List;

public class QueueAnalyzer {

    public int totalQueueLength(List<Lane> lanes) {
        int total = 0;
        for (Lane lane : lanes) {
            total += lane.getQueueLength();
        }
        return total;
    }

    public Lane findLongestQueueLane(List<Lane> lanes) {
        Lane longest = null;
        int maxLength = -1;
        for (Lane lane : lanes) {
            if (lane.getQueueLength() > maxLength) {
                maxLength = lane.getQueueLength();
                longest = lane;
            }
        }
        return longest;
    }
}
