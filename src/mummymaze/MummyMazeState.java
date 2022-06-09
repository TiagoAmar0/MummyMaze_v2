package mummymaze;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MummyMazeState extends State implements Cloneable {

    private final char[][] matrix;

    public static final char VERTICAL_WALL = '|';
    public static final char HORIZONTAL_WALL = '-';
    public static final char HERO = 'H';
    public static final char EMPTY = '.';
    public static final char EXIT = 'S';
    public static final char WHITE_MUMMY = 'M';
    public static final char RED_MUMMY = 'V';
    public static final char TRAP = 'A';
    public static final char SCORPION = 'E';
    public static final char KEY = 'C';
    public static final char CLOSED_HORIZONTAL_DOOR = '=';
    public static final char OPENED_HORIZONTAL_DOOR = '_';
    public static final char CLOSED_VERTICAL_DOOR = '"';
    public static final char OPENED_VERTICAL_DOOR = ')';

    private int heroPositionLine;
    private int heroPositionColumn;
    private LinkedList<Position> redMummiesPosition;
    private LinkedList<Position> whiteMummiesPosition;
    private LinkedList<Position> scorpionsPosition;
    private LinkedList<Position> keysPosition;

    private boolean lost;

    public MummyMazeState(char[][] matrix, boolean lost) {
        this.matrix = new char[matrix.length][matrix.length];
        this.lost = lost;
        this.whiteMummiesPosition = new LinkedList<>();
        this.redMummiesPosition = new LinkedList<>();
        this.scorpionsPosition = new LinkedList<>();
        this.keysPosition = new LinkedList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                this.matrix[i][j] = matrix[i][j];

                switch (matrix[i][j]){
                    case HERO:
                        heroPositionLine = i;
                        heroPositionColumn = j;
                        break;
                    case KEY:
                        keysPosition.add(new Position(i,j));
                        break;
                    case WHITE_MUMMY:
                        whiteMummiesPosition.add(new Position(i, j));
                        break;
                    case RED_MUMMY:
                        redMummiesPosition.add(new Position(i,j));
                        break;
                    case SCORPION:
                        scorpionsPosition.add(new Position(i,j));
                        break;
                }
            }
        }

    }

    public MummyMazeState(char[][] matrix, boolean lost, LinkedList<Position> keysPosition) {
        this.matrix = new char[matrix.length][matrix.length];
        this.lost = lost;
        this.whiteMummiesPosition = new LinkedList<>();
        this.redMummiesPosition = new LinkedList<>();
        this.scorpionsPosition = new LinkedList<>();
        this.keysPosition = new LinkedList<>(keysPosition);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                this.matrix[i][j] = matrix[i][j];

                switch (matrix[i][j]){
                    case HERO:
                        heroPositionLine = i;
                        heroPositionColumn = j;
                        break;
                    case WHITE_MUMMY:
                        whiteMummiesPosition.add(new Position(i, j));
                        break;
                    case RED_MUMMY:
                        redMummiesPosition.add(new Position(i,j));
                        break;
                    case SCORPION:
                        scorpionsPosition.add(new Position(i,j));
                        break;
                }
            }
        }
    }

    public char[][] getMatrix(){
        return matrix;
    }

    public MummyMazeState(String matrix, boolean lost){
        this.matrix = new char[13][13];
        this.lost = lost;
        this.whiteMummiesPosition = new LinkedList<>();
        this.redMummiesPosition = new LinkedList<>();
        this.keysPosition = new LinkedList<>();
        this.scorpionsPosition = new LinkedList<>();

        String[] matrixLines = matrix.split("\n");

        for(int i = 0; i < matrixLines.length; i++){
            this.matrix[i] = matrixLines[i].toCharArray();
            for(int j = 0; j < 13; j++){

                switch (this.matrix[i][j]){
                    case HERO:
                        heroPositionLine = i;
                        heroPositionColumn = j;
                        break;
                    case WHITE_MUMMY:
                        whiteMummiesPosition.add(new Position(i, j));
                        break;
                    case RED_MUMMY:
                        redMummiesPosition.add(new Position(i,j));
                        break;
                    case KEY:
                        keysPosition.add(new Position(i,j));
                        break;
                    case SCORPION:
                        scorpionsPosition.add(new Position(i,j));
                        break;
                }
            }
        }
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
        firePuzzleChanged(null);
    }

    public boolean canMoveUp() {

        // If hero in first line, cannot move up
        if(heroPositionLine <= 1)
            return false;

        // Cannot if it is a wall, closed horizontal door, mummy, scorpion or trap
        if(matrix[heroPositionLine - 1][heroPositionColumn] == HORIZONTAL_WALL ||
                matrix[heroPositionLine - 1][heroPositionColumn] == CLOSED_HORIZONTAL_DOOR ||
                matrix[heroPositionLine - 2][heroPositionColumn] == WHITE_MUMMY ||
                matrix[heroPositionLine - 2][heroPositionColumn] == RED_MUMMY ||
                matrix[heroPositionLine - 2][heroPositionColumn] == SCORPION ||
                matrix[heroPositionLine - 2][heroPositionColumn] == TRAP)
            return false;

        return true;
    }

    public boolean canMoveRight() {
        if(heroPositionColumn >= matrix[heroPositionLine].length - 2)
            return false;

        // Cannot if it is a wall, closed vertical door, mummy, scorpion or trap
        if(matrix[heroPositionLine][heroPositionColumn + 1] == VERTICAL_WALL||
                matrix[heroPositionLine][heroPositionColumn + 1] == CLOSED_VERTICAL_DOOR ||
                matrix[heroPositionLine][heroPositionColumn + 2] == WHITE_MUMMY ||
                matrix[heroPositionLine][heroPositionColumn + 2] == RED_MUMMY ||
                matrix[heroPositionLine][heroPositionColumn + 2] == SCORPION ||
                matrix[heroPositionLine][heroPositionColumn + 2] == TRAP)
            return false;

        return true;
    }

    public boolean canMoveDown() {
        if(heroPositionLine >= matrix.length - 2)
            return false;

        // Cannot if it is a wall, closed horizontal door, mummy, scorpion or trap
        if(matrix[heroPositionLine + 1][heroPositionColumn] == HORIZONTAL_WALL ||
                matrix[heroPositionLine + 1][heroPositionColumn] == CLOSED_HORIZONTAL_DOOR ||
                matrix[heroPositionLine + 2][heroPositionColumn] == WHITE_MUMMY ||
                matrix[heroPositionLine + 2][heroPositionColumn] == RED_MUMMY ||
                matrix[heroPositionLine + 2][heroPositionColumn] == SCORPION ||
                matrix[heroPositionLine + 2][heroPositionColumn] == TRAP)
            return false;

        return true;
    }

    public boolean canMoveLeft() {
        if(heroPositionColumn <= 1)
            return false;

        // Cannot if it is a wall, closed vertical door, mummy, scorpion or trap
        if(matrix[heroPositionLine][heroPositionColumn - 1] == VERTICAL_WALL ||
                matrix[heroPositionLine][heroPositionColumn - 1] == CLOSED_VERTICAL_DOOR ||
                matrix[heroPositionLine][heroPositionColumn - 2] == WHITE_MUMMY ||
                matrix[heroPositionLine][heroPositionColumn - 2] == RED_MUMMY ||
                matrix[heroPositionLine][heroPositionColumn - 2] == SCORPION ||
                matrix[heroPositionLine][heroPositionColumn - 2] == TRAP)
            return false;

        return true;
    }

    private void moveWhiteMummies() {
        int mov = 0;

        // Loop all white mummies
        for(Position p : whiteMummiesPosition){
            // First try to put the mummy in same column as the hero
            mov = moveEnemyHorizontally(mov, p, 2, WHITE_MUMMY);

            // If there are available movements left, try to put the mummy in the same row as the hero
            if(mov < 2)
                moveEnemyVertically(mov, p, 2, WHITE_MUMMY);
        }
    }

    private void moveScorpions(){
        int mov = 0;
        for (Position p : scorpionsPosition){
            mov = moveEnemyHorizontally(mov, p, 1, SCORPION);

            if(mov < 1)
                moveEnemyVertically(mov, p, 1, SCORPION);
        }
    }

    private int moveEnemyVertically(int mov, Position p, int limit, char enemy){
        while (!lost && heroPositionLine < p.getX()&& canEnemyMoveUp(p.getX(), p.getY(), enemy) && mov < limit){
            matrix[p.getX()][p.getY()] = EMPTY;

            checkIfKillMummy(p.getX() - 2, p.getY(), enemy);

            p.setX(p.getX() - 2);
            if(matrix[p.getX()][p.getY()] == HERO){
                lost = true;
            }
            matrix[p.getX()][p.getY()] = enemy;
            mov++;
        }
        while (!lost && heroPositionLine > p.getX() && canEnemyMoveDown(p.getX(), p.getY(), enemy) && mov < limit){
            matrix[p.getX()][p.getY()] = EMPTY;

            checkIfKillMummy(p.getX() + 2, p.getY(), enemy);

            p.setX(p.getX() + 2);
            if(matrix[p.getX()][p.getY()] == HERO){
                lost = true;
            }
            matrix[p.getX()][p.getY()] = enemy;
            mov++;
        }
        return mov;
    }

    private int moveEnemyHorizontally(int mov, Position p, int limit, char enemy){
        while(!lost && heroPositionColumn < p.getY() && canEnemyMoveLeft(p.getX(), p.getY(), enemy) && mov < limit) {
            matrix[p.getX()][p.getY()] = EMPTY;

            checkIfKillMummy(p.getX(), p.getY() - 2, enemy);

            p.setY(p.getY() - 2);
            if (matrix[p.getX()][p.getY()] == HERO) {
                lost = true;
            }

            matrix[p.getX()][p.getY()] = enemy;
            mov++;
        }

        while(!lost && heroPositionColumn > p.getY() && canEnemyMoveRight(p.getX(), p.getY(), enemy) && mov < limit){
            matrix[p.getX()][p.getY()] = EMPTY;

            checkIfKillMummy(p.getX(), p.getY() + 2, enemy);

            p.setY(p.getY() + 2);
            if(matrix[p.getX()][p.getY()] == HERO){
                lost = true;
            }
            matrix[p.getX()][p.getY()] = enemy;
            mov++;
        }

        return mov;
    }

    private void checkIfKillMummy(int x, int y, char enemy){
        // If white mummies collide, kill the mummy that was first in the position
        if((enemy == WHITE_MUMMY || enemy == RED_MUMMY) && matrix[x][y] == WHITE_MUMMY)
            removeEnemyFromList(whiteMummiesPosition, x, y);

        // If white mummies collide, kill the mummy that was first in the position
        if((enemy == WHITE_MUMMY || enemy == RED_MUMMY) && matrix[x][y] == RED_MUMMY)
            removeEnemyFromList(redMummiesPosition, x, y);
    }

    private void moveRedMummies(){
        int mov = 0;

        // Loop all red mummies
        for(Position p : redMummiesPosition){
            // First try to put the mummy in the same row as the hero
            mov = moveEnemyVertically(mov, p, 2, RED_MUMMY);

            // If there are available movements left, try to put the mummy in the same column as the hero
            if(mov < 2)
                moveEnemyHorizontally(mov, p, 2, RED_MUMMY);
        }
    }

    private boolean canEnemyMoveDown(int x, int y, char enemy) {
        if(x >= matrix.length - 2)
            return false;

        if(enemy == SCORPION && (matrix[x + 2][y] == RED_MUMMY || matrix[x + 2][y] == WHITE_MUMMY))
            return false;

        if(matrix[x + 2][y] == SCORPION)
            return false;

        // Cannot if it is a wall or closed horizontal door
        if(matrix[x + 1][y] == HORIZONTAL_WALL ||
                matrix[x + 1][y] == CLOSED_HORIZONTAL_DOOR)
            return false;

        return true;
    }

    private boolean canEnemyMoveUp(int x, int y, char enemy) {
        if(x <= 1)
            return false;

        if(enemy == SCORPION && (matrix[x - 2][y] == RED_MUMMY || matrix[x - 2][y] == WHITE_MUMMY))
            return false;

        if(matrix[x - 2][y] == SCORPION)
            return false;

        // Cannot if it is a wall or closed horizontal door
        if(matrix[x - 1][y] == HORIZONTAL_WALL ||
                matrix[x - 1][y] == CLOSED_HORIZONTAL_DOOR)
            return false;

        return true;
    }

    private boolean canEnemyMoveRight(int x, int y, char enemy) {
        if(y >= matrix[x].length - 2)
            return false;

        if(enemy == SCORPION && (matrix[x][y + 2] == RED_MUMMY || matrix[x][y + 2] == WHITE_MUMMY))
            return false;

        if(matrix[x][y + 2] == SCORPION)
            return false;

        // Cannot if it is a wall or closed vertical door
        if(matrix[x][y + 1] == VERTICAL_WALL||
                matrix[x][y + 1] == CLOSED_VERTICAL_DOOR)
            return false;

        return true;
    }

    private boolean canEnemyMoveLeft(int x, int y, char enemy) {
        if(y <= 1)
            return false;

        if(enemy == SCORPION && (matrix[x][y - 2] == RED_MUMMY || matrix[x][y - 2] == WHITE_MUMMY))
            return false;

        if(matrix[x][y - 2] == SCORPION)
            return false;

        // Cannot if it is a wall, closed vertical door
        if(matrix[x][y - 1] == VERTICAL_WALL ||
                matrix[x][y - 1] == CLOSED_VERTICAL_DOOR)
            return false;

        return true;
    }

    private void moveEnemies(){
        moveWhiteMummies();
        moveRedMummies();
        moveScorpions();
    }

    // Hero doesn't move, only the enemies move
    public void stand() {
        moveEnemies();
    }

    public void moveUp() {

        int x = matrix[heroPositionLine - 1][heroPositionColumn] == EXIT ? heroPositionLine - 1 : heroPositionLine - 2;

        if(matrix[x][heroPositionColumn] == KEY)
            toggleDoors();

        matrix[heroPositionLine][heroPositionColumn] = EMPTY;
        for (Position position : keysPosition)
            if(position.getX() == heroPositionLine && position.getY() == heroPositionColumn)
                matrix[heroPositionLine][heroPositionColumn] = KEY;


        matrix[x][heroPositionColumn] = HERO;

        heroPositionLine = x;

        moveEnemies();

    }

    public void moveRight() {
        int y = matrix[heroPositionLine][heroPositionColumn + 1] == EXIT ? heroPositionColumn + 1 : heroPositionColumn + 2;

        if(matrix[heroPositionLine][y] == KEY)
            toggleDoors();

        matrix[heroPositionLine][heroPositionColumn] = EMPTY;
        for (Position position : keysPosition)
            if(position.getX() == heroPositionLine && position.getY() == heroPositionColumn)
                matrix[heroPositionLine][heroPositionColumn] = KEY;

        matrix[heroPositionLine][y] = HERO;

        heroPositionColumn = y;

        moveEnemies();
    }

    public void moveDown() {

        int x = matrix[heroPositionLine + 1][heroPositionColumn] == EXIT ? heroPositionLine + 1 : heroPositionLine + 2;

        if(matrix[x][heroPositionColumn] == KEY)
            toggleDoors();

        matrix[heroPositionLine][heroPositionColumn] = EMPTY;
        for (Position position : keysPosition)
            if(position.getX() == heroPositionLine && position.getY() == heroPositionColumn)
                matrix[heroPositionLine][heroPositionColumn] = KEY;

        matrix[x][heroPositionColumn] = HERO;

        heroPositionLine = x;

        moveEnemies();
    }

    public void moveLeft() {

        int y = matrix[heroPositionLine][heroPositionColumn - 1] == EXIT ? heroPositionColumn - 1 : heroPositionColumn - 2;

        if(matrix[heroPositionLine][y] == KEY)
            toggleDoors();

        matrix[heroPositionLine][heroPositionColumn] = EMPTY;
        for (Position position : keysPosition)
            if(position.getX() == heroPositionLine && position.getY() == heroPositionColumn)
                matrix[heroPositionLine][heroPositionColumn] = KEY;

        matrix[heroPositionLine][y] = HERO;


        heroPositionColumn = y;

        moveEnemies();
    }

    public int getNumLines() {
        return matrix.length;
    }

    public int getNumColumns() {
        return matrix[0].length;
    }

    public boolean isLost(){
        return lost;
    }

    public int getTileValue(int line, int column) {
        if (!isValidPosition(line, column)) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return matrix[line][column];
    }

    // Toggle doors
    private void toggleDoors(){
        for (int i = 0; i < this.matrix.length; i++){
            for (int j = 0; j < this.matrix[0].length; j++){
                switch (this.matrix[i][j]){
                    case CLOSED_HORIZONTAL_DOOR:
                        this.matrix[i][j] = OPENED_HORIZONTAL_DOOR;
                        break;
                    case OPENED_HORIZONTAL_DOOR:
                        this.matrix[i][j] = CLOSED_HORIZONTAL_DOOR;
                        break;
                    case CLOSED_VERTICAL_DOOR:
                        this.matrix[i][j] = OPENED_VERTICAL_DOOR;
                        break;
                    case OPENED_VERTICAL_DOOR:
                        this.matrix[i][j] = CLOSED_VERTICAL_DOOR;
                        break;
                }
            }
        }
    }

    public boolean isValidPosition(int line, int column) {
        return line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length;
    }

    private LinkedList<Position> removeEnemyFromList(LinkedList<Position> list, int x, int y){
        for(Position p : list)
            if(p.getX() == x && p.getY() == y)
                list.remove(p);

        return list;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MummyMazeState)) {
            return false;
        }

        MummyMazeState o = (MummyMazeState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public MummyMazeState clone() {
        return new MummyMazeState(matrix, lost, keysPosition);
    }
    //Listeners
    private transient ArrayList<MummyMazeListener> listeners = new ArrayList<MummyMazeListener>(3);

    public synchronized void removeListener(MummyMazeListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(MummyMazeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void firePuzzleChanged(MummyMazeEvent pe) {
        for (MummyMazeListener listener : listeners) {
            listener.gameChanged(null);
        }
    }

    public int getHeroPositionLine() {
        return heroPositionLine;
    }

    public int getHeroPositionColumn() {
        return heroPositionColumn;
    }

    static class Position {
        private int x;
        private int y;

        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
