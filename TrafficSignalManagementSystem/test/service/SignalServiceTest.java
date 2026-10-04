package service;

import enums.Direction;
import enums.SignalColor;
import model.Intersection;
import model.TrafficSignal;
import org.junit.Test;
import static org.junit.Assert.*;

public class SignalServiceTest {

    @Test
    public void testSetSignalChangesColor() throws Exception {
        Intersection intersection = new Intersection("I1");
        intersection.addSignal(new TrafficSignal("S1", Direction.NORTH));

        SignalService service = new SignalService();
        service.setSignal(intersection, Direction.NORTH, SignalColor.GREEN, 15);

        assertEquals(SignalColor.GREEN, intersection.getSignal(Direction.NORTH).getCurrentState().getColor());
    }
}