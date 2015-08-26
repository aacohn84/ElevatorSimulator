package aacohn84.elevatorsimulator.elevator;

import org.junit.Assert;
import org.junit.Test;

import aacohn84.elevatorsimulator.SimMode;
import aacohn84.elevatorsimulator.SimulatorConfig;
import aacohn84.elevatorsimulator.event.EventListener;

public class ElevatorTest {
    @Test
    public void goToFloor_validFloor_arrivalListenerNotified() throws InterruptedException {
        Elevator elevator = new Elevator(new SimulatorConfig(5, 5, SimMode.COMPRESSED_TIME));
        EventListener<Elevator> arrivalListener = (arrivedElev) -> Assert.assertEquals(arrivedElev.currentFloor, 5);
        elevator.elevatorArrived.attach(arrivalListener);
        elevator.goToFloor(5);
        Thread.sleep(25); // must delay for about 25 millis so event can fire before execution ends
        Assert.assertEquals(elevator.currentFloor, 5);
    }

    @Test(expected = FloorNumberOutOfBoundsException.class)
    public void goToFloor_floorTooLow_exceptionThrown() {
        Elevator elevator = new Elevator(new SimulatorConfig(5, 5, SimMode.COMPRESSED_TIME));
        EventListener<Elevator> arrivalListener = (arrivedElev) -> Assert.assertEquals(arrivedElev.currentFloor, 5);
        elevator.elevatorArrived.attach(arrivalListener);
        elevator.goToFloor(0);
    }

    @Test(expected = FloorNumberOutOfBoundsException.class)
    public void goToFloor_floorTooHigh_exceptionThrown() {
        Elevator elevator = new Elevator(new SimulatorConfig(5, 5, SimMode.COMPRESSED_TIME));
        EventListener<Elevator> arrivalListener = (arrivedElev) -> Assert.assertEquals(arrivedElev.currentFloor, 5);
        elevator.elevatorArrived.attach(arrivalListener);
        elevator.goToFloor(6);
    }
}
