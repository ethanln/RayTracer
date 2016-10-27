package graphic_object;

import java.util.ArrayList;
import java.util.Iterator;

import util.Vector;

public class Polygon extends GraphicObject implements Iterable<Edge>{
	private ArrayList<Edge> edges;
	
	
	public Polygon(ShapeType _shapeType, MassType _massType){
		super(_shapeType, _massType);
		this.edges = new ArrayList<Edge>();
	}

	@Override
	public Vector doesCollide(Vector initialPos, Vector direction) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEdge(Edge edge){
		this.edges.add(edge);
	}

	@Override
	public Iterator<Edge> iterator() {
		return this.edges.iterator();
	}
	
	public Edge getEdge(int index){
		return this.edges.get(index);
	}
	
	public Vector getNormal(){
		Vector e1 = this.edges.get(0).pt1.getDirection(this.edges.get(0).pt2);
		Vector e2 = this.edges.get(1).pt2.getDirection(this.edges.get(1).pt1);
		
		float xn = (e1.y * e2.z) - (e2.y * e1.z); // a2 * b3 - b2 * a3
		float yn = (e1.z * e2.x) - (e2.z * e1.x); // a1 * b3 - b1 * a3
		float zn = (e1.x * e2.y) - (e2.x * e1.y); // a1 * b2 - b1 * a2
		return new Vector(xn, yn, zn);
	}
}
