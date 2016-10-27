package raytracer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import util.Vector;

public class Ray implements Iterable<Ray>{
	/**
	 * Ray type enumerations, definitions for different kinds of casted rays.
	 * @author Ethan
	 *
	 */
	public enum RayType{
		TRANSMISSION,
		REFLECTION,
		SHADOW,
		PRIMARY,
		NONE
	};
	/**
	 * texture color of position
	 */
	private Color color;
	
	/**
	 * position of collision
	 */
	private Vector collisionPos;
	
	/**
	 * initial position of ray.
	 */
	private Vector initialPos;
	
	/**
	 * The vector ray that created the node
	 */
	private Vector ray;
	
	/**
	 * Type of ray: TRANSMISSION, REFLECTION, SHADOW
	 */
	private RayType type; 
	
	/**
	 * Child rays.
	 */
	private ArrayList<Ray> rays;
	
	public Ray(){	
		this.color = null;
		this.collisionPos = null;
		this.setInitialPos(null);
		this.ray = null;
		this.type = RayType.NONE;
		
		this.rays = new ArrayList<Ray>();
	}

	public Color getColor() {
		return color;
	}

	public Vector getCollisionPos() {
		return collisionPos;
	}

	public Vector getRay() {
		return ray;
	}

	public RayType getType() {
		return type;
	}
	
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setCollisionPos(Vector collisionPos) {
		this.collisionPos = collisionPos;
	}

	public void setRay(Vector ray) {
		this.ray = ray;
	}

	public void setType(RayType type) {
		this.type = type;
	}
	
	public void addRay(Ray _ray){
		this.rays.add(_ray);
	}

	@Override
	public Iterator<Ray> iterator() {
		return this.rays.iterator();
	}

	public Vector getInitialPos() {
		return initialPos;
	}

	public void setInitialPos(Vector initialPos) {
		this.initialPos = initialPos;
	}
}
