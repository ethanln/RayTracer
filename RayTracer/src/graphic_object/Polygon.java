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
		// find point on plane.
		Vector dir = direction.normalize();
		Vector n = this.getNormal(null).normalize();
		Vector o = initialPos;
		
		Vector p0 = this.edges.get(0).pt1;
		Vector p1 = this.edges.get(1).pt1;
		Vector p2 = this.edges.get(2).pt1;
		
		Vector p = p0.add(p1).add(p2).divideByConstant(3.0f);
		float t = (-1.0f) * o.subtract(p).dotProduct(n) / dir.dotProduct(n);
		Vector inter = o.add(dir.multiplyByConstant(t));
		
		// increment initial pos.
		Vector e = direction.multiplyByConstant(0.0001f);
		
		// test for intersection.
		boolean result = doesIntersectTriangle(direction, initialPos.add(e), inter);
		
		// return intersection if is on triangle.
		if(result){
			return inter;
		}
		return null;
		
	}
	
	/**
	 * test for triangle intersection.
	 * @param direction
	 * @param orig
	 * @param intersection
	 * @return
	 */
	private boolean doesIntersectTriangle(Vector direction, Vector orig, Vector intersection){
		Vector interTemp = new Vector(intersection.x, intersection.y, intersection.z);
		Vector p1 = this.edges.get(0).pt1;
		Vector p2 = this.edges.get(1).pt1;
		Vector p3 = this.edges.get(2).pt1;
		
		Vector edge1 = p2.subtract(p1);
		Vector edge2 = p3.subtract(p1);

		Vector pvec = Vector.crossProduct(direction, edge2);
		float det = edge1.dotProduct(pvec);
		if (det == 0) {
			if (this.isOnPlane(orig) && this.isPointInTriangle(orig)) {
				if (interTemp != null){
					interTemp = (orig);
				}
				return true;
			}
			return false;
		}

		det = 1.0f / det;

		Vector tvec = orig.subtract(p1);
		float u = tvec.dotProduct(pvec) * det;
		if (u < 0.0f || u > 1.0f){
			return false;
		}

		Vector qvec = Vector.crossProduct(tvec, edge1);
		float v = direction.dotProduct(qvec) * det;
		if (v < 0.0f || u + v > 1.0f){
			return false;
		}

		float t = edge2.dotProduct(qvec) * det;
		if (t < 0){
			return false;
		}

		return true;
	}
	
	/**
	 * test for point on triangle.
	 * @param point
	 * @return
	 */
	private boolean isPointInTriangle(Vector point){
		Vector v0 = this.edges.get(0).pt1.subtract(point);
		Vector v1 = this.edges.get(1).pt1.subtract(point);
		Vector v2 = this.edges.get(2).pt1.subtract(point);

		float e1 = v0.dotProduct(v1);
		float e2 = v0.dotProduct(v2);
		float e3 = v1.dotProduct(v2);
		float e4 = v2.dotProduct(v2);

		if (e3 * e2 - e4 * e1 < 0){
			return false;
		}
		float bb = v1.dotProduct(v1);
		if (e1 * e3 - e2 * bb < 0){
			return false;
		}
		return true;
	}
	
	/**
	 * test if point is on triangle plane.
	 * @param point
	 * @return
	 */
	private boolean isOnPlane(Vector point){
		Vector p0 = this.edges.get(0).pt1;
		Vector p1 = this.edges.get(1).pt1;
		Vector p2 = this.edges.get(2).pt1;
		
		Vector n = Vector.crossProduct(p0.subtract(p2), new Vector(p1.x-p2.x, p1.y-p2.y, p1.z-p2.z)).normalize();
		float d = (-1.0f) * p1.dotProduct(n);
		
		float dist = n.dotProduct(point) + d;
		
		return dist == 0.0f;
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
	
	@Override
	public Vector getNormal(Vector pt){
		Vector pt1 = this.edges.get(0).pt1;
		Vector pt2 = this.edges.get(1).pt1;
		Vector pt3 = this.edges.get(2).pt1;
		
		Vector e1 = pt2.getDirection(pt1);
		Vector e2 = pt3.getDirection(pt1);
		
		float xn = (e1.y * e2.z) - (e2.y * e1.z); // a2 * b3 - b2 * a3
		float yn = (e1.z * e2.x) - (e2.z * e1.x); // a1 * b3 - b1 * a3
		float zn = (e1.x * e2.y) - (e2.x * e1.y); // a1 * b2 - b1 * a2
		
		return new Vector(xn, yn, zn);
	}

	@Override
	public boolean equals(GraphicObject obj) {
		if(obj == null){
			return false;
		}
		
		if(obj.getShapeType() == ShapeType.POLYGON){
			Vector p10 = this.getEdge(0).pt1;
			Vector p11 = this.getEdge(1).pt1;
			Vector p12 = this.getEdge(2).pt1;
			
			Polygon p = (Polygon)obj;
			Vector p20 = p.getEdge(0).pt1;
			Vector p21 = p.getEdge(1).pt1;
			Vector p22 = p.getEdge(2).pt1;
			
			if(p10.equals(p20) && p11.equals(p21) && p12.equals(p22)){
				return true;
			}
		}
		return false;
	}
}
