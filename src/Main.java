import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProblemState problem = ProblemState.readProblemState("a_example.in");
        RideOptimizer optimizer = new RideOptimizer(problem.cars,problem.steps);
        List<Car> cars = optimizer.optimzeCarRoutes(problem.rideList);
        OutputMaker.createOutput(cars);
    }

}
