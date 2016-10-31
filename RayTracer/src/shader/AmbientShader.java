package shader;

import raytracer.CollisionInstance;
import raytracer.Ray;
import raytracer.RayTracer;
import util.RColor;
import util.Vector;

public class AmbientShader extends Shader{
	public AmbientShader(){}

	@Override
	public RColor doShader(RayTracer tracer, CollisionInstance collisionInfo, Ray _ray, int currentDepth) {
		Vector normal = collisionInfo.getClosestObject().getNormal(collisionInfo.getIntersection()).normalize();
		Vector lightDir = tracer.getScene().getLightSource().getDirection(collisionInfo.getIntersection()).normalize();
		Vector eyeDir = tracer.getScene().getCamLookFrom().getDirection(collisionInfo.getIntersection()).normalize();
		
		float dotProductNL = normal.dotProduct(lightDir);
		Vector reflectDir = normal.multiplyByConstant(2 * dotProductNL).subtract(lightDir).normalize();
		float dotProductER = eyeDir.dotProduct(reflectDir);
		float phongValue = (float)Math.pow((float)Math.max(0, dotProductER), collisionInfo.getClosestObject().getPhongConstant());

		RColor ambient = collisionInfo.getClosestObject().getDiffuse().multiply(tracer.getScene().getAmbientLight());
		RColor diffuse = collisionInfo.getClosestObject().getDiffuse().multiplyByConstant(Math.max(0, dotProductNL));
		RColor phong = collisionInfo.getClosestObject().getSpecularHighlight().multiplyByConstant(phongValue);
		RColor appliedLight = tracer.getScene().getLightColor().multiply(diffuse.add(phong));
		
		// determine if intersection is within shadow.
		boolean isShadow = isInShadow(collisionInfo.getIntersection(), collisionInfo.getClosestObject(), tracer.getScene());
		if(isShadow){
			appliedLight = appliedLight.multiplyByConstant(0.0f);
		}
		
		// compute ambient color
		ambient = ambient.add(appliedLight);
		return ambient;
	}
}
