import java.util.ArrayList;

public class node {
	
	// INSTANCE VARIABLES : 
	
	private double f, g, h;
	private int y, x;
	private node cameFrom;
	private boolean walkable = true;
	private ArrayList<node> neighbors = new ArrayList<node>();
	
	// CONSTRUCTOR : 
	
	public node(int y, int x){
		this.setF(Double.POSITIVE_INFINITY);
		this.setG(Double.POSITIVE_INFINITY);
		this.h = 0;
		this.setY(y);
		this.setX(x);
	}
	
	// GETTERS & SETTERS : 
	
	public void addNeighbors(node neighbor) {
		this.neighbors.add(neighbor);
	}
	
	public ArrayList<node> getNeighbors(){
		return neighbors;
	}
	
	public double getF() {
		return g + h;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public node getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(node cameFrom) {
		this.cameFrom = cameFrom;
	}

	public void setF(double f) {
		this.f = f;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	// TO STRING : 
	public String toString() {
		return "x:" + x + " y:" + y + "; ";
	}
}
