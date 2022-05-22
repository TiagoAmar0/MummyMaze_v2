package mummymaze;

import agent.Action;
import agent.Problem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static mummymaze.MummyMazeState.*;

public class MummyMazeProblem extends Problem<MummyMazeState> {

    protected List<Action> actions;

    public MummyMazeProblem(MummyMazeState initialState) {
        super(initialState);
        actions = new LinkedList<Action>() {{
            add(new ActionStand());
            add(new ActionDown());
            add(new ActionUp());
            add(new ActionRight());
            add(new ActionLeft());
        }};
    }


    @Override
    public List<Action<MummyMazeState>> getActions(MummyMazeState state) {
        List<Action<MummyMazeState>> possibleActions = new LinkedList<>();

        if(!state.isLost())
            for (Action action : actions) {
                if (action.isValid(state)) {
                    possibleActions.add(action);
                }
            }

        return possibleActions;
    }

    @Override
    public MummyMazeState getSuccessor(MummyMazeState state, Action action){
        MummyMazeState successor = state.clone();
        action.execute(successor);
        return successor;
    }

    @Override
    public boolean isGoal(MummyMazeState state) {
        LinkedList<Position> exits = state.getExits();


        for(Position p : exits)
            if(p.getX() == state.getHeroPositionLine() && p.getY() == state.getHeroPositionColumn())
                return true;

        return false;
    }

    @Override
    public double computePathCost(List<Action> path) {
        return path.size();
    }
}
