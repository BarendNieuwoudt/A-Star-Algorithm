import java.util.ArrayList;

public class astar {

	// grid [y-coordinate][x-coordinate]

	public static void main(String[] args) {

		// Map to be traversed :
		int y = 5, x = 5;
		node[][] grid = new node[y][x];

		// Populate grid (Otherwise the grid will contain NULL values) :
		for (int i = 0; i < y; i++)
			for (int j = 0; j < x; j++)
				grid[i][j] = new node(i, j);

		// Add walls :
		grid[1][1].setWalkable(false);
		
		
		A_Star_No_Diagonals(grid[0][0], grid[4][4], grid);
		System.out.println();
		A_Star_Diagonals(grid[0][0], grid[4][3], grid);

	}

	// WITH DIAGONAL MOVEMENT
	public static void A_Star_Diagonals(node start, node end, node[][] grid) {
		// Calculate g score and set neighbors for each node :
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[0].length; j++) {
				// Calculate g score :
				grid[i][j].setG(heuristic(grid[i][j], grid[grid.length - 1][grid[0].length - 1]));
				// Add walkable neighbors :
				// Check top left
				if (i > 0 && j > 0) 
					if (grid[i - 1][j - 1].isWalkable())
						grid[i][j].addNeighbors(grid[i - 1][j - 1]);

				// check bottom right
				if (i < grid.length - 1 && j < grid[0].length - 1)
					if (grid[i + 1][j + 1].isWalkable())
						grid[i][j].addNeighbors(grid[i + 1][j + 1]);

				// check top right
				if (i > 0 && j < grid[0].length - 1)
					if (grid[i - 1][j + 1].isWalkable())
						grid[i][j].addNeighbors(grid[i - 1][j + 1]);

				// check bottom left
				if (j > 0 && i < grid.length - 1)
					if (grid[i + 1][j - 1].isWalkable())
						grid[i][j].addNeighbors(grid[i + 1][j - 1]);

				if (i > 0)
					if (grid[i - 1][j].isWalkable())
						grid[i][j].addNeighbors(grid[i - 1][j]);

				if (j > 0)
					if (grid[i][j - 1].isWalkable())
						grid[i][j].addNeighbors(grid[i][j - 1]);

				if (i < grid.length - 1)
					if (grid[i + 1][j].isWalkable())
						grid[i][j].addNeighbors(grid[i + 1][j]);

				if (j < grid[0].length - 1)
					if (grid[i][j + 1].isWalkable())
						grid[i][j].addNeighbors(grid[i][j + 1]);
			}
		rest_A_Star(start, end, grid);
	}

	// WITHOUT DIAGONAL MOVEMENT
	public static void A_Star_No_Diagonals(node start, node end, node[][] grid) {
		// Calculate g score and set neighbors for each node :
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[0].length; j++) {
				// Calculate g score :
				grid[i][j].setG(heuristic(grid[i][j], grid[grid.length - 1][grid[0].length - 1]));
				// Add walkable neighbors :
				if (i > 0)
					if (grid[i - 1][j].isWalkable())
						grid[i][j].addNeighbors(grid[i - 1][j]);
				if (j > 0)
					if (grid[i][j - 1].isWalkable())
						grid[i][j].addNeighbors(grid[i][j - 1]);
				if (i < grid.length - 1)
					if (grid[i + 1][j].isWalkable())
						grid[i][j].addNeighbors(grid[i + 1][j]);
				if (j < grid[0].length - 1)
					if (grid[i][j + 1].isWalkable())
						grid[i][j].addNeighbors(grid[i][j + 1]);
			}
		rest_A_Star(start, end, grid);
	}

	public static void rest_A_Star(node start, node end, node[][] grid) {
		
		// Nodes that have already been evaluated :
		ArrayList<node> closedSet = new ArrayList<node>();
		// Nodes that still need to be evaluated :
		ArrayList<node> openSet = new ArrayList<node>();

		// The first node has a g of 0 (cost from going from start to start = 0) :
		start.setG(0);

		// Add Start to openSet, it still has to be evaluated :
		openSet.add(start);

		boolean pathFound = false;

		// While openSet is not empty, there are still nodes to evaluate :
		while (!openSet.isEmpty()) {

			// Find the node with the smallest f score :
			node current = openSet.get(0);
			for (int i = 0; i < openSet.size(); i++) {
				if (openSet.get(i).getF() < current.getF()) {
					current = openSet.get(i);
				}
			}

			// If the next node is the end node, end :
			if (current == end) {
				pathFound = true;
				break;
			}

			// Remove current from openSet (it has been evaluated) :
			openSet.remove(openSet.indexOf(current));
			// Add current to closedSet :
			closedSet.add(current);

			for (int k = 0; k < current.getNeighbors().size(); k++) {
				node t = current.getNeighbors().get(k);

				// Ignore neighbor that is already evaluated :
				if (closedSet.contains(t))
					continue;

				// Distance from start to neighbor
				double provG = current.getG() + 1;

				if (!openSet.contains(t))
					// New node discovered
					openSet.add(t);
				else if (provG >= t.getG()) 
					continue;

				// The path is the best until now, record it :
				t.setCameFrom(current);
				t.setG(provG);
				t.setF(t.getG() + heuristic(t, end));
			}
		}

		if (pathFound) {
			// Solution :
			ArrayList<node> solution = new ArrayList<node>();
			node l = end.getCameFrom();
			while (l != start) {
				solution.add(l);
				l = l.getCameFrom();
			}
			// Display Solution :
			for(int p = solution.size()-1; p >= 0; p--)
				System.out.println(solution.get(p).toString());
			
		} else {
			// No Solution :
			System.out.println("NO SOLUTION");
		}
	}

	public static double heuristic(node start, node end) {
		// Physical Distance from end point
		double a = Math.pow((end.getX() - start.getX()), 2);
		double b = Math.pow((end.getY() - start.getY()), 2);
		return Math.sqrt(a + b);
	}

}
