package aacohn84.elevatorsimulator.elevator;

import aacohn84.elevatorsimulator.SimulatorConfig;
import aacohn84.elevatorsimulator.event.EventListener;

public class ElevatorDispatcher {
    Elevator elevator;
    SimulatorConfig config;
    ArrivalListener arrivalListener = new ArrivalListener();

    public ElevatorDispatcher(SimulatorConfig config, Elevator elevator) {
        this.config = config;
        this.elevator = elevator;

        elevator.elevatorArrived.attach(arrivalListener);
    }

    /**
     * Simulates the call-button being pressed in the hallway of a floor.
     *
     * @param floor
     *         - the floor to call the elevator from.
     */
    public synchronized void callRequest(int floor) {
        // in the single-elevator case, a hallway request has the same effect as a panel request
        panelRequest(floor);
    }

    /**
     * Simulates a floor being selected using the panel inside the elevator.
     *
     * @param floor
     *         - the floor to visit.
     */
    public synchronized void panelRequest(int floor) {
        if (floor < 1 || floor > config.numFloors) {
            throw new FloorNumberOutOfBoundsException(floor, config.numFloors);
        }
        System.out.println("request for " + floor);
        if (elevator.isIdle()) {
            System.out.println("idle - send elevator to " + floor);
            elevator.goToFloor(floor);
        } else {
            System.out.println("transiting - toggle " + floor);
            elevator.toggleFloorSelection(floor);
        }
    }

    synchronized void sendNextRequest(Elevator elevator) {
        // go to the next selected floor in the current direction
        for (int i = elevator.currentFloor; i > 0 && i < config.numFloors; i += elevator.direction) {
            if (elevator.isFloorSelected(i)) {
                System.out.println("next, go to " + i);
                elevator.goToFloor(i);
                return;
            }
        }
        // otherwise, go to the next floor in the opposite direction
        elevator.direction *= -1;
        for (int i = elevator.currentFloor; i > 0 && i < config.numFloors; i += elevator.direction) {
            if (elevator.isFloorSelected(i)) {
                System.out.println("next, go to " + i);
                elevator.goToFloor(i);
                return;
            }
        }
        // no floors selected, set the elevator to idle
        System.out.println("set idle");
        elevator.setIdle();
    }

    public synchronized void shutdown() {
        elevator.shutdown();
    }

    private class ArrivalListener implements EventListener<Elevator> {
        @Override
        public void update(Elevator elevator) {
            sendNextRequest(elevator);
        }
    }
}
