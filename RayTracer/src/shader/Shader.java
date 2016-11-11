package shader;

import graphic_object.GraphicObject;
import graphic_object.GraphicObject.MassType;
import model.Scene;
import raytracer.CollisionInstance;
import raytracer.Ray;
import raytracer.RayTracer;
import util.RColor;
import util.Vector;

public abstract class Shader {

	public Shader(){
	}
	
	public abstract RColor doShader(RayTracer tracer, CollisionInstance collisionInfo, Ray _ray, int currentDepth);
	
	/**
	 * Casts ray from intersection to light source and computes intersections.
	 * @param intersection
	 * @param closestObject
	 * @param scene
	 * @return
	 */
	protected boolean isInShadow(Vector intersection, GraphicObject closestObject, Scene scene){
		Vector shadowRay = scene.getLightSource().getDirection(intersection);
		// check for any collision for the light ray.
		for(GraphicObject obj : scene){
			// be sure we're not checking the object of the current intersection, for reflection rays.
			if(!obj.equals(closestObject)){
				Vector collision = obj.doesCollide(intersection, shadowRay);
				if(obj.getMassType() == MassType.TRANSPARENT){
					continue;
				}
				if(collision != null){
					return true;
				}
			}
		}
		return false;
	}
}
