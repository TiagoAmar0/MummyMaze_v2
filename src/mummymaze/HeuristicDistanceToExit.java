package mummymaze;

import agent.Heuristic;

import static java.lang.Math.*;

public class HeuristicDistanceToExit extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        double distance = Double.MAX_VALUE;
        char[][] matrix = state.getMatrix();

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] == MummyMazeState.EXIT){

                    // calculate the hypotenuse between the heigh of the hero position and exit position and width of hero position and exit position
                    double currentDistance = sqrt(pow(abs(state.getHeroPositionLine() - i), 2) + pow(abs(state.getHeroPositionColumn() - j), 2));

                    distance = currentDistance < distance ? currentDistance : distance;
                }
            }
        }

        return distance;
    }
    
    @Override
    public String toString(){
        return "Distance to nearest exit.";
    }
}