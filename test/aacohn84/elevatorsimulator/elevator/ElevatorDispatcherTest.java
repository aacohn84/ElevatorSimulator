package aacohn84.elevatorsimulator.elevator;

import org.junit.Assert;
import org.junit.Test;

import aacohn84.elevatorsimulator.SimMode;
import aacohn84.elevatorsimulator.SimulatorConfig;

public class ElevatorDispatcherTest {
    @Test
    public void sendNextRequest_oneFloorToggled_shouldGoToThatFloor() throws InterruptedException {
        SimulatorConfig config = new SimulatorConfig(5, 5, 5, SimMode.COMPRESSED_TIME);
        Elevator elevator = new Elevator(config);
        ElevatorDispatcher dispatcher = new ElevatorDispatcher(config, elevator);
        elevator.direction = 1;
        elevator.toggleFloorSelection(2);
        dispatcher.sendNextRequest(elevator);
        Thread.sleep(10);
        Assert.assertEquals(2, elevator.currentFloor);
    }
}
