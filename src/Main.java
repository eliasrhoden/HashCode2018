import java.util.List;

public class Main {

    public static void main(String[] args) {


        String[] inputFiles = {
                "a_example.in",
                "b_should_be_easy.in",
                "c_no_hurry.in",
                "d_metropolis.in",
                "e_high_bonus.in"

        };


        for(String file:inputFiles){
            System.out.println("Reading file: " + file);
            ProblemState problem = ProblemState.readProblemState(file);
            System.out.println("Done reading, optimizing");
            RideOptimizer optimizer = new RideOptimizer(problem.cars,problem.steps,problem.bonus);
            List<Car> cars = optimizer.optimzeCarRoutes(problem.rideList);
            System.out.println("Done optimizing, writing output");
            OutputMaker.createOutput(cars, file.substring(0,2));
            System.out.println("Done!");
        }


    }

}
