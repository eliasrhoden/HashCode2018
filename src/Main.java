import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Reading file");
        ProblemState problem = ProblemState.readProblemState("a_example.in");
        System.out.println("Done reading, optimizing");
        RideOptimizer optimizer = new RideOptimizer(problem.cars,problem.steps);
        List<Car> cars = optimizer.optimzeCarRoutes(problem.rideList);
        System.out.println("Done optimizing, writing output");
        OutputMaker.createOutput(cars);
        System.out.println("Done!");
    }

}
