package shader;

import raytracer.CollisionInstance;
import raytracer.Ray;
import raytracer.RayTracer;
import raytracer.Ray.RayType;
import util.RColor;
import util.Vector;

public class RefractionShader extends Shader{

	public RefractionShader(){
	}
	
	@Override
	public RColor doShader(RayTracer tracer, CollisionInstance collisionInfo, Ray _ray, int currentDepth) {
		Vector normal = collisionInfo.getClosestObject().getNormal(collisionInfo.getIntersection()).normalize();
		
		//float r = collisionInfo.getClosestObject().getRefractionMaterial();
		//float c = normal.multiplyByConstant(-1.0f).dotProduct(_ray.getRay().normalize());
		//Vector refraction = _ray.getRay().normalize().multiplyByConstant(r);
		//Vector a = normal.multiplyByConstant((r*c - (float)Math.sqrt(1 - (float)Math.pow(r, 2) * (1 - (float)Math.pow(c, 2)))));
		//refraction = refraction.add(a);
		Vector reflection = null;
		if(!_ray.isInsideShape()){
			// ===================  I need to be sure I'm doing this right.
			float numerator = normal.dotProduct(_ray.getRay().normalize());
			float denominator = normal.getMagnitude() * _ray.getRay().normalize().getMagnitude();
			float angle = (float)Math.acos(numerator / denominator);
			
			Vector M = normal.multiplyByConstant((float)Math.cos(angle));
			M = M.subtract(_ray.getRay().normalize());
			M = M.divideByConstant((float)Math.sin(angle)).normalize();
			
			float angleOfRefraction = (float)Math.asin((float)Math.sin(angle) / collisionInfo.getClosestObject().getRefractionMaterial());
			
			// THIS SHIT AINT WORKING...VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
			Vector T = M.multiplyByConstant((float)Math.sin(angleOfRefraction));
			reflection = T.subtract(normal.multiplyByConstant((float)Math.cos(angleOfRefraction))).normalize();
		}
		else{
			// ============  need to figure out how to cast the right ray.
			reflection = normal;
		}
		Ray transmissionRay = new Ray();
		transmissionRay.setRay(reflection);
		transmissionRay.setPreviousObject(null);
			
		transmissionRay.setInitialPos(collisionInfo.getIntersection());
		transmissionRay.setType(RayType.TRANSMISSION);
		
		if(!_ray.isInsideShape()){
			transmissionRay.setInsideShape(true);
		}
		
		return tracer.rayTrace(transmissionRay, currentDepth);
	}

}
