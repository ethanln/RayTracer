package shader;

import graphic_object.GraphicObject.ShapeType;
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

		Vector reflection = null;
		if(!_ray.isInsideShape()){
			// ===================  I need to be sure I'm doing this right.
			float numerator = normal.dotProduct(_ray.getRay().normalize().invert());
			float denominator = normal.getMagnitude() * _ray.getRay().normalize().invert().getMagnitude();
			float angle = (float)Math.acos(numerator / denominator);
			
			Vector M = normal.multiplyByConstant((float)Math.cos(angle));
			M = M.subtract(_ray.getRay().normalize().invert());
			M = M.divideByConstant((float)Math.sin(angle)).normalize();
			
			float angleOfRefraction = (float)Math.asin(collisionInfo.getClosestObject().getRefractionMaterialOutside() * (float)Math.sin(angle) / collisionInfo.getClosestObject().getRefractionMaterialInside());
			
			Vector T = M.multiplyByConstant((float)Math.sin(angleOfRefraction));
			reflection = T.subtract(normal.multiplyByConstant((float)Math.cos(angleOfRefraction))).normalize();
			
		}
		else{
			float numerator = normal.dotProduct(_ray.getRay().normalize());
			float denominator = normal.getMagnitude() * _ray.getRay().normalize().getMagnitude();
			float angle = (float)Math.acos(numerator / denominator);
			
			Vector M = normal.multiplyByConstant((float)Math.cos(angle));
			M = M.subtract(_ray.getRay().normalize());
			M = M.divideByConstant((float)Math.sin(angle)).normalize();
			
			float angleOfRefraction = (float)Math.asin(collisionInfo.getClosestObject().getRefractionMaterialInside() * (float)Math.sin(angle) / collisionInfo.getClosestObject().getRefractionMaterialOutside());
			
			Vector T = M.multiplyByConstant((float)Math.sin(angleOfRefraction));
			reflection = T.subtract(normal.multiplyByConstant((float)Math.cos(angleOfRefraction)));

		}
		Ray transmissionRay = new Ray();
		transmissionRay.setRay(reflection);
		transmissionRay.setPreviousObject(null);
			
		transmissionRay.setInitialPos(collisionInfo.getIntersection());
		transmissionRay.setType(RayType.TRANSMISSION);
		
		///transmissionRay.setPreviousRay(_ray.getRay());
		
		if(!_ray.isInsideShape() && collisionInfo.getClosestObject().getShapeType() == ShapeType.SPHERE){
			transmissionRay.setInsideShape(true);
		}
		RColor remainingReflection = collisionInfo.getClosestObject().getReflective().substractConstantByColor(1.0f);
		
		//return collisionInfo.getClosestObject().getTransparency().multiply(tracer.rayTrace(transmissionRay, currentDepth));
		return collisionInfo.getClosestObject().getTransparency().multiply(tracer.rayTrace(transmissionRay, currentDepth));
	}

}
