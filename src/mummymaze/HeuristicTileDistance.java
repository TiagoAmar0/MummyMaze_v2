package mummymaze;

import agent.Heuristic;

public class HeuristicTileDistance extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return 0;
        //return state.computeTileDistances(problem.getGoalState());
    }
    
    @Override
    public String toString(){
        return "Tiles distance to final position";
    }
}