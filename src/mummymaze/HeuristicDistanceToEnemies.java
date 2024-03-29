package mummymaze;

import agent.Heuristic;

import static java.lang.Math.*;

public class HeuristicDistanceToEnemies extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state) {
        char[][] matrix = state.getMatrix();

        // maximum possible diagonal that can exist in this problem (eg. hero in [1,1] and enemy in [11;11])
        // height and width will be equal to
        // hypotenuse ^ 2 = 10^2 + 10^2 <=> h^2 = 200 <=> h = sqrt(200)
        double maxDiagonal = Math.sqrt(200);
        double distance = Double.MAX_VALUE;

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] == MummyMazeState.SCORPION || matrix[i][j] == MummyMazeState.WHITE_MUMMY || matrix[i][j] == MummyMazeState.RED_MUMMY){
                    // calculate the hypotenuse between the height of the hero position and exit position and width of hero position and exit position
                    double currentDistance = sqrt(pow(abs(state.getHeroPositionLine() - i), 2) + pow(abs(state.getHeroPositionColumn() - j), 2));

                    distance = currentDistance < distance ? currentDistance : distance;
                }
            }
        }

        return distance == Double.MAX_VALUE ? 1 : maxDiagonal - distance;
    }
    
    @Override
    public String toString(){
        return "Distance to nearest enemy";
    }    
}