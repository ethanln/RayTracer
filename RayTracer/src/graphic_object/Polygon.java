package graphic_object;

import java.util.ArrayList;
import java.util.Iterator;

import util.UV;
import util.Vector;
import util.Vector.Axis;

public class Polygon extends GraphicObject implements Iterable<Edge>{
	private ArrayList<Edge> edges;
	
	
	public Polygon(ShapeType _shapeType, MassType _massType){
		super(_shapeType, _massType);
		this.edges = new ArrayList<Edge>();
	}

	@Override
	public Vector doesCollide(Vector initialPos, Vector direction) {
		if(true){
			return null; // hold off on polygons for the time being.
		}
		Vector normalDirection = direction.normalize();
		Vector n = this.getNormal(null).normalize();
		ArrayList<UV> vertices = new ArrayList<UV>();
		
		// get axis with the highest value.
		Axis axisEliminate = n.getLargestAxis();
		
		// Project vertices.
		for(Edge e : edges){
			vertices.add(e.pt1.toUV(axisEliminate));
		}
		
		// Project ray.
		UV Ri = normalDirection.toUV(axisEliminate);
		
		ArrayList<UV> translatedVertices = new ArrayList<UV>();
		// Translate all points.
		for(int i = 0; i < vertices.size(); i++){
			translatedVertices.add(vertices.get(i).translate(Ri));
		}
		
		// Translate ray to origin.
		UV translatedRi = Ri.translate(Ri);
		
		// Find intersection point.
		Vector interSectionPoint = this.getIntersectionPoint(translatedVertices, translatedRi);

		return interSectionPoint;
	}
	
	private Vector getIntersectionPoint(ArrayList<UV> vertices, UV ray){
		int numCrossings = 0;
		int signHolder = vertices.get(0).v < 0 ? -1 : 1;
		
		for(int i = 0; i < vertices.size(); i++){
			int nextIndex = i == vertices.size() - 1 ? 0 : i + 1;
			int nextSignHolder = vertices.get(nextIndex).v < 0 ? -1 : 1;
			
			if(signHolder != nextSignHolder){
				if(vertices.get(i).u > 0 && vertices.get(nextIndex).u > 0){
					numCrossings++;
				}
				else if(vertices.get(i).u > 0 || vertices.get(nextIndex).u > 0){
					
					float ucross = vertices.get(i).u - vertices.get(i).v * (vertices.get(nextIndex).u - vertices.get(i).u)
								   / (vertices.get(nextIndex).v - vertices.get(i).v);
					if(ucross > 0){
						numCrossings++;
					}
				}
			}
			signHolder = nextSignHolder;
		}
		if(numCrossings > 0){
			int i = 0;
			i = 1;
		}
		if(numCrossings % 2 != 0){
			return new Vector(0.0f, 0.0f, 0.0f);
		}
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
	
	@Override
	public Vector getNormal(Vector pt){
		Vector pt1 = this.edges.get(0).pt1;
		Vector pt2 = this.edges.get(1).pt1;
		Vector pt3 = this.edges.get(2).pt1;
		
		Vector e1 = pt1.getDirection(pt2);
		Vector e2 = pt3.getDirection(pt2);
		
		float xn = (e1.y * e2.z) - (e2.y * e1.z); // a2 * b3 - b2 * a3
		float yn = (e1.z * e2.x) - (e2.z * e1.x); // a1 * b3 - b1 * a3
		float zn = (e1.x * e2.y) - (e2.x * e1.y); // a1 * b2 - b1 * a2
		
		return new Vector(xn, yn, zn);
	}

	@Override
	public boolean equals(GraphicObject obj) {
		if(obj.getShapeType() == ShapeType.POLYGON){
			Polygon p = (Polygon)obj;
		}
		return false;
	}
}
