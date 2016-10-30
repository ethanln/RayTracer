package raytracer;

import graphic_object.GraphicObject;
import util.Vector;

public class CollisionInstance {
	private GraphicObject closestObject;
	private Vector intersection;
	
	public CollisionInstance(GraphicObject _closestObject, Vector _intersection){
		this.setClosestObject(_closestObject);
		this.setIntersection(_intersection);
	}

	public GraphicObject getClosestObject() {
		return closestObject;
	}

	public void setClosestObject(GraphicObject closestObject) {
		this.closestObject = closestObject;
	}

	public Vector getIntersection() {
		return intersection;
	}

	public void setIntersection(Vector intersection) {
		this.intersection = intersection;
	}
}
