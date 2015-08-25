package aacohn84.elevatorsimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        SimulatorConfig config = getSimulatorConfig(stdin);

        ElevatorDispatcher dispatcher = new ElevatorDispatcher(config, new Elevator());

        if (config.simMode.equals(SimMode.REAL_TIME)) {
            print("The elevator system will now accept your requests.\n" +
                  "Enter a floor number at any time to issue a request.\n" +
                  "When you are finished, enter 'q' to quit.\n" +
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
                print("\n\n>");
                input = stdin.readLine();
            }
            print("Input closed. The program will terminate after the simulation is complete.\n");
        }
    }

    private static SimulatorConfig getSimulatorConfig(BufferedReader stdin) throws IOException {
        print("Welcome to Elevator Simulator. Please choose a simulation mode:\n" +
              "\n" +
              "R - Real-time - Make requests and see how the elevator system reacts in real time.\n" +
              "\n" +
              "C - Compressed-time - Specify a sequence of requests for the elevator system to process\n" +
              "                      and get the results immediately.\n" +
              "\n" +
              "> ");
        SimMode simMode = null;
        String input = stdin.readLine();
        while (!input.equalsIgnoreCase("C") && !input.equalsIgnoreCase("R")) {
            print("Please enter 'R' for real-time mode, or 'C' for compressed-time mode.\n\n" +
                  "> ");
            input = stdin.readLine();
        }
        switch (input.toLowerCase()) {
        case "c":
            simMode = SimMode.COMPRESSED_TIME;
            break;
        case "r":
            simMode = SimMode.REAL_TIME;
            break;
        default:
            System.err.println("Invalid simulation mode.");
            System.exit(1);
        }

        int numFloors = getIntInRange(stdin, "How many floors in this building?", 2, 16);
        int travelTime = getIntInRange(stdin, "How much time traveling between floors (in seconds)?", 5, 10);
        int doorTime = getIntInRange(stdin, "How long do the doors remain open (in seconds)?", 5, 10);

        print("Simulation mode: " + simMode.toString() + "\n");
        print("Number of floors: " + numFloors + "\n");
        print("Time between floors: " + travelTime + "s\n");
        print("Time doors stay open: " + doorTime + "s\n");

        return new SimulatorConfig(travelTime, doorTime, numFloors, simMode);
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
