package aacohn84.elevatorsimulator;

public class FloorNumberOutOfBoundsException extends IllegalArgumentException {
    public FloorNumberOutOfBoundsException(int floorNumber, int numFloors) {
        super(String.format("Invalid floor number: %d, must be between 1 and %d.", floorNumber, numFloors));
    }
}
