package aacohn84.elevatorsimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import aacohn84.elevatorsimulator.elevator.Elevator;
import aacohn84.elevatorsimulator.elevator.ElevatorDispatcher;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        SimulatorConfig config = getSimulatorConfig(stdin);

        ElevatorDispatcher dispatcher = new ElevatorDispatcher(config, new Elevator(config));

        if (config.simMode.equals(SimMode.REAL_TIME)) {
            runRealTimeSim(stdin, config, dispatcher);
        }
    }

    private static SimulatorConfig getSimulatorConfig(BufferedReader stdin) throws IOException {
        print("Welcome to Elevator Simulator. Let's do a little configuration.\n\n");

        int numFloors = getIntInRange(stdin, "How many floors in this building?", 2, 16);
        int travelTime = getIntInRange(stdin, "How much time traveling between floors (in seconds)?", 5, 10);

        print("Simulation mode: " + SimMode.REAL_TIME.toString() + "\n" +
              "Number of floors: " + numFloors + "\n" +
              "Time between floors: " + travelTime + "s\n\n");

        return new SimulatorConfig(travelTime, numFloors, SimMode.REAL_TIME);
    }

    private static void runRealTimeSim(BufferedReader stdin, SimulatorConfig config, ElevatorDispatcher dispatcher)
            throws IOException {
        print("All set! Enter a floor number at any time to visit that floor. You can enter floor numbers while the\n" +
              "elevator is in transit. You can cancel a request by repeating it before the elevator executes it.\n" +
              "\n" +
              "Enter 'q' to quit whenever you're finished.\n" +
              "\n" +
              "> ");

        String input = stdin.readLine();
        while (!input.equalsIgnoreCase("q")) {
            Integer floor = parseInt(input);
            if (floor == null) {
                print("\"" + input + "\" is not a valid floor number\n");
            } else {
                if (floor > 0 && floor < config.numFloors) {
                    dispatcher.panelRequest(floor);
                } else {
                    print(floor + " cannot be reached by this elevator. " +
                          "choose a floor between 1 and " + config.numFloors + "\n");
                }
            }
            print("\n>");
            input = stdin.readLine();
        }
        dispatcher.shutdown();
        print("Input closed. Elevator will shut down after it finishes with its current request.\n");
    }

    public static void print(String msg) {
        System.out.print(msg);
    }

    private static int getIntInRange(BufferedReader stdin, String prompt, int min, int max) throws IOException {
        print(prompt + " (" + min + '-' + max + ")\n" +
              "\n" +
              "> ");
        Integer num = getInteger(stdin);
        while (num == null || num < min || num > max) {
            print("Please choose a number between " + min + " and " + max + ": ");
            num = getInteger(stdin);
        }
        return num;
    }

    private static Integer getInteger(BufferedReader stdin) throws IOException {
        String input = stdin.readLine();
        return parseInt(input);
    }

    private static Integer parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
