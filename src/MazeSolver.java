/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @author Eisha Yadav
 * @version 03/25/2025
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

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
        // Initialize Stack
        Stack<MazeCell> next = new Stack<>();
        MazeCell currentCell = maze.getStartCell();
        next.push(currentCell);

        // Continue to iterate while there are still elements in the stack
        while (!next.empty()){
            // Set the cell to explored
            currentCell.setExplored(true);
            // Return the solution once the end is reached
            if (currentCell == maze.getEndCell()){
                return getSolution();
            }
            // Go to down each route in the North,East,South,West directional order.
            // North
            addCell(currentCell, next, -1, 0);
            // East
            addCell(currentCell, next, 0, 1);
            // South
            addCell(currentCell, next, 1, 0);
            // West
            addCell(currentCell, next, 0, -1);

            currentCell = next.pop();
        }
        return getSolution();
    }

    // Function to add cell to stack, and perform checks to see if visited.
    // Sets Explored Status + Parent Status of cells as well
    public void addCell(MazeCell currentCell, Stack<MazeCell> next, int rowAdd, int colAdd){
        if(maze.isValidCell(currentCell.getRow() + rowAdd, currentCell.getCol() + colAdd)){
            next.add(maze.getCell(currentCell.getRow() + rowAdd, currentCell.getCol() + colAdd));
            // Prepare the following cell from the stack
            next.peek().setParent(currentCell);
            next.peek().setExplored(true);
        }

    }
    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Utilize a Queue, to allow for First In, First Out Traversal
        Queue<MazeCell> next = new LinkedList<MazeCell>();
        MazeCell currentCell = maze.getStartCell();
        next.add(currentCell);

        // While there are still elements in the queue
        while (!next.isEmpty()){
            currentCell.setExplored(true);
            // If code has reached the end of maze, stop
            if (currentCell.equals(maze.getEndCell())){
                return getSolution();
            }
            // NORTH
            addCell(currentCell, -1, 0, next);
            // EAST
            addCell(currentCell, 0, 1, next);
            // SOUTH
            addCell(currentCell, 1, 0, next);
            // WEST
            addCell(currentCell, 0, -1, next);

            // Prepare the Next Current Cell
            next.remove();
            currentCell = next.peek();
        }
        return getSolution();
    }

    // Add the neighboring cells to the queue
    public void addCell(MazeCell currentCell, int rowAdd, int colChange, Queue<MazeCell> next){
        if (maze.isValidCell(currentCell.getRow() + rowAdd, currentCell.getCol() + colChange)){
            MazeCell temp = maze.getCell(currentCell.getRow() + rowAdd, currentCell.getCol() +colChange);
            next.add(temp);
            temp.setParent(currentCell);
            temp.setExplored(true);
        }
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
        System.out.println("\n");
        maze.printSolution(sol);
    }
}
