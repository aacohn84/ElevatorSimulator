package aacohn84.elevatorsimulator.elevator;

import java.util.Timer;
import java.util.TimerTask;

import aacohn84.elevatorsimulator.SimMode;
import aacohn84.elevatorsimulator.SimulatorConfig;
import aacohn84.elevatorsimulator.event.Event;

public class Elevator {
    int currentFloor = 1;
    int destinationFloor = 1;
    int direction = 0; // idle
    boolean[] floorSelectionToggles;
    SimulatorConfig config;
    Timer elevatorTaskTimer = new Timer();
    Event<Elevator> elevatorArrived = new Event<>();
    int timeScale = 1;

    public Elevator(SimulatorConfig config) {
        this.config = config;
        this.floorSelectionToggles = new boolean[config.numFloors];
        if (config.simMode.equals(SimMode.REAL_TIME)) {
            timeScale = 1000; // TimerTask delays will be in seconds instead of milliseconds
        }
    }

    synchronized void goToFloor(int floor) {
        if (floor < 1 || floor > config.numFloors) {
            throw new FloorNumberOutOfBoundsException(floor, config.numFloors);
        }
        destinationFloor = floor;
        if (destinationFloor > currentFloor) {
            direction = 1;
        } else {
            direction = -1;
        }
        int floorsToTravel = Math.abs(currentFloor - destinationFloor);
        long timeToDestination = floorsToTravel * config.travelTime * timeScale; // max: 15 * 10 * 1000 = 150000
        GoToFloorTask goToFloorTask = new GoToFloorTask(timeToDestination);
        elevatorTaskTimer.schedule(goToFloorTask, 0L);
    }

    synchronized void setIdle() {
        direction = 0;
    }

    synchronized boolean isIdle() {
        return direction == 0;
    }

    synchronized void toggleFloorSelection(int floor) {
        floorSelectionToggles[floor - 1] ^= true;
    }

    synchronized void toggleFloorSelectionOff(int floor) {
        floorSelectionToggles[floor - 1] = false;
    }

    synchronized boolean isFloorSelected(int floor) {
        return floorSelectionToggles[floor - 1];
    }

    synchronized void shutdown() {
        elevatorTaskTimer.cancel();
    }

    class GoToFloorTask extends TimerTask {
        final long timeToDestination;

        public GoToFloorTask(long timeToDestination) {
            this.timeToDestination = timeToDestination;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(timeToDestination);
            } catch (InterruptedException e) {
                // this should not happen
                e.printStackTrace();
            }
            currentFloor = destinationFloor;
            System.out.println("arrived at " + currentFloor);
            toggleFloorSelectionOff(currentFloor);
            elevatorArrived.notify(Elevator.this);
        }
    }
}
