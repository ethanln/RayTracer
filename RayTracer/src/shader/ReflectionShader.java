package shader;

import raytracer.CollisionInstance;
import raytracer.Ray;
import raytracer.RayTracer;
import raytracer.Ray.RayType;
import util.RColor;
import util.Vector;

public class ReflectionShader extends Shader{

	public ReflectionShader(){}
	
	@Override
	public RColor doShader(RayTracer tracer, CollisionInstance collisionInfo, Ray _ray, int currentDepth) {
		if(isInShadow(collisionInfo.getIntersection(), collisionInfo.getClosestObject(), tracer.getScene())){
			return null;
		}
		
		// HOW DO i HANDLE MULTIPLE REFLECTIONS?
		Vector normal = collisionInfo.getClosestObject().getNormal(collisionInfo.getIntersection()).normalize();
		float dotProductDN = _ray.getRay().normalize().dotProduct(normal) * 2.0f;
		normal = normal.multiplyByConstant(dotProductDN);
		Vector reflection = _ray.getRay().normalize().subtract(normal).normalize();
			
		Ray reflectionRay = new Ray();
		reflectionRay.setRay(reflection);
		reflectionRay.setInitialPos(collisionInfo.getIntersection());
		reflectionRay.setType(RayType.REFLECTION);
		reflectionRay.setPreviousObject(collisionInfo.getClosestObject());
		
		// recursive call.
		RColor rayColorResult = tracer.rayTrace(reflectionRay, currentDepth);
		
		if(rayColorResult != null){
			// do reflections appear on dark side of objects????????????
			return rayColorResult.multiply(collisionInfo.getClosestObject().getReflective());
		}
		return null;
	}

}
