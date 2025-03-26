/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Should be from end to start cells, then reversed
        ArrayList<MazeCell> mazeCells = new ArrayList<>();
        MazeCell currentCell = maze.getEndCell();
        while (currentCell != maze.getStartCell()){
            mazeCells.add(currentCell);
            currentCell = currentCell.getParent();
        }
        // Add Start Cell
        mazeCells.add(currentCell);
        Collections.reverse(mazeCells);
        return mazeCells;
    }
    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Stack<MazeCell> next = new Stack<>();
        MazeCell currentCell = maze.getStartCell();
        checkNeighbor(currentCell, next);
        return null;
    }

    // Recursive Helper Function
    public void checkNeighbor(MazeCell currentCell, Stack<MazeCell> next){
        currentCell.setExplored(true);
        // Base Case
        if (currentCell == maze.getEndCell()){
            return;
        }
        // Go to down each route in the North,East,South,West directional order.
        // NORTH
        addCell(currentCell, next, -1, 0);
        // EAST
        addCell(currentCell, next, 0, 1);
        // SOUTH
        addCell(currentCell, next, 1, 0);
        // WEST
        addCell(currentCell, next, 0, -1);
        // Recursive Step (where current cell is the top element of the stack)
        checkNeighbor(next.pop(), next);
    }

    // Function to add cell to stack, and perform checks to see if visited.
    // Sets Explored Status + Parent Status of cells as well
    public void addCell(MazeCell currentCell, Stack<MazeCell> next, int rowAdd, int colAdd){
        if(maze.isValidCell(currentCell.getRow() + rowAdd, currentCell.getCol()+ colAdd)){
            next.add(maze.getCell(currentCell.getRow() + rowAdd, currentCell.getCol() + colAdd));
        }
    }
    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        return null;
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
