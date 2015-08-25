package aacohn84.elevatorsimulator;

public final class SimulatorConfig {
    public final int travelTime;
    public final int doorTime;
    public final int numFloors;
    public final SimMode simMode;

    public SimulatorConfig(int travelTime, int doorTime, int numFloors, SimMode simMode) {
        this.travelTime = travelTime;
        this.doorTime = doorTime;
        this.numFloors = numFloors;
        this.simMode = simMode;
    }
}
