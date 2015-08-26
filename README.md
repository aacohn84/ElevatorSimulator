Elevator Simulator
=
*Author: Aaron Cohn*  
*Date: 8/26/2015*

# Build
This project was created with **JetBrains IntelliJ 14.1**, and I have included the project files in the repository, so you can import the project and build it with IntelliJ if you have it installed. Alternatively, you could build the project using Eclipse or NetBeans by creating a new project from existing sources.

The application code is in the `src` directory, and the tests are in the `test` directory. The two directories follow the same package structure. The application `main` function is in `src/aacohn84/elevatorsimulator/Main.java`.

**jUnit** is the only external library in use on this project. It is stored in the `lib` directory, so if building from the command-line, make sure to include the contents of `lib` in the classpath.

The code uses some features of Java 8, so **JDK 1.8+** is required to build the code.

# Running the main application
This is an interactive console application. If you imported the project into IntelliJ, you can run the main application within the IDE by using the default run configuration. Otherwise, I have included a JAR file `/ElevatorSimulator.jar` which you can run on the JVM from the command-line. ElevatorSimulator requires **JRE 1.8+** to run.

# Input/Output
After you start the application, it will prompt you to enter the number of floors in the building and the amount of time it takes to travel between floors. Once the configuration is set, the simulation will begin. You can enter any number from 1..N, where N is the number of floors you chose. The concept is basically that you're standing in the elevator and pressing buttons to tell it where to go. You can select any floor at any time. You can cancel a request to visit a floor by selecting that floor again before the elevator services the original request.

The simulation will continue until you enter `q`.

If the elevator is idle, then it will immediately go to whatever floor you select. If it is in transit, then your selection will be toggled on the switch board and serviced via the elevator algorithm.
