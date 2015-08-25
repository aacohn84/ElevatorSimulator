package aacohn84.elevatorsimulator;

public final class SimulatorConfig {
    final int travelTime;
    final int doorTime;
    final int numFloors;
    final SimMode simMode;

    public SimulatorConfig(int travelTime, int doorTime, int numFloors, SimMode simMode) {
        this.travelTime = travelTime;
        this.doorTime = doorTime;
        this.numFloors = numFloors;
        this.simMode = simMode;
    }
}
