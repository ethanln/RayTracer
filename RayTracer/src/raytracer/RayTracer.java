package raytracer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import graphic_object.GraphicObject;
import graphic_object.GraphicObject.MassType;
import graphic_object.GraphicObject.ShapeType;
import graphic_object.Sphere;
import model.Scene;
import raytracer.Ray.RayType;
import util.RColor;
import util.Vector;

public class RayTracer {

	private Color[][] pixels;
	private Vector[][] coordinates;
	private Ray root;
	private int depthLimit;
	private Scene scene;
	private int width;
	private int height;
	
	public RayTracer(Scene _scene, int _width, int _height, int _depthLimit){
		this.pixels = new Color[_height][_width];
		this.coordinates = new Vector[_height][_width];
		this.root = null;
		this.depthLimit = _depthLimit;
		this.scene = _scene;
		this.width = _width;
		this.height = _height;
		
		this.setupImagePlane();
	}
	
	private void setupImagePlane(){
		float border = (float)Math.tan(Math.toRadians(this.scene.getFov() / 2.0f)) * this.scene.getCamLookFrom().z;
		float incrementX = (border * 2.0f) / (float)this.width;
		float incrementY = -((border * 2.0f) / (float)this.height);
		
		float currentX = -border;
		float currentY = border;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.coordinates[i][j] = new Vector(currentX, currentY);
				currentX += incrementX;
			}
			currentX = -border;
			currentY += incrementY;
		}
	}
	
	/**
	 * start ray tracing.
	 * @return
	 */
	public boolean trace(){
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				// Setup ray tree.
				this.root = new Ray();
				
				// TODO: instantiate root with vector ray between camera position and pixel position
				this.root.setRay(this.coordinates[i][j].getDirection(this.scene.getCamLookFrom()));
				this.root.setInitialPos(this.scene.getCamLookFrom());
				this.root.setType(RayType.PRIMARY);
				this.pixels[i][j] = this.rayTrace(this.root, 0).toColor();
			}
		}
		return false;
	}
	
	/**
	 * Recursively compute all rays.
	 * @param _ray
	 */
	private RColor rayTrace(Ray _ray, int currentDepth){
		currentDepth++;
		// find colliding objects
		CollisionInstance collisionInfo = this.findCollidingObject(_ray);

		if(collisionInfo == null){
			if(_ray.getType() == RayType.PRIMARY){
				return this.scene.getBackgroundColor();
			}
			return null;  
		}
		
		RColor intensityColor = computeIntensityColor(collisionInfo, currentDepth, _ray);
			
		return intensityColor;
	}
	
	/**
	 * Computes color intensity of intersection point with shadows included.
	 * @param intersection
	 * @param closestObject
	 * @return
	 */
	private RColor computeIntensityColor(CollisionInstance collisionInfo, int currentDepth, Ray _ray){
		// get light and normal directions
		Vector normal = collisionInfo.getClosestObject().getNormal(collisionInfo.getIntersection()).normalize();
		Vector lightDir = this.scene.getLightSource().getDirection(collisionInfo.getIntersection()).normalize();
		Vector eyeDir = this.scene.getCamLookFrom().getDirection(collisionInfo.getIntersection()).normalize();
		
		float dotProductNL = normal.dotProduct(lightDir);
		Vector reflectDir = normal.multiplyByConstant(2 * dotProductNL).subtract(lightDir).normalize();
		float dotProductER = eyeDir.dotProduct(reflectDir);
		float phongValue = (float)Math.pow((float)Math.max(0, dotProductER), collisionInfo.getClosestObject().getPhongConstant());

		RColor ambient = collisionInfo.getClosestObject().getDiffuse().multiply(this.scene.getAmbientLight());
		RColor diffuse = collisionInfo.getClosestObject().getDiffuse().multiplyByConstant(Math.max(0, dotProductNL));
		RColor phong = collisionInfo.getClosestObject().getSpecularHighlight().multiplyByConstant(phongValue);
		RColor appliedLight = this.scene.getLightColor().multiply(diffuse.add(phong));
		
		// determine if intersection is within shadow.
		boolean isShadow = isInShadow(collisionInfo.getIntersection(), collisionInfo.getClosestObject());
		if(isShadow){
			appliedLight = appliedLight.multiplyByConstant(0.0f);
		}
		
		ambient = ambient.add(appliedLight);
		
		if(currentDepth < this.depthLimit){
			if(!collisionInfo.getClosestObject().getReflective().isBlack() && !isShadow && _ray.getType() != RayType.REFLECTION){
				//r = d – 2n(d · n)
				float dotProductDN = _ray.getRay().normalize().dotProduct(normal) * 2.0f;
				normal = normal.multiplyByConstant(dotProductDN);
				Vector reflection = _ray.getRay().normalize().subtract(normal).normalize();
					
				Ray reflectionRay = new Ray();
				reflectionRay.setRay(reflection);
				reflectionRay.setInitialPos(collisionInfo.getIntersection());
				reflectionRay.setType(RayType.REFLECTION);
				reflectionRay.setPreviousObject(collisionInfo.getClosestObject());
				
				// recursive call.
				RColor rayColorResult = this.rayTrace(reflectionRay, currentDepth);
				
				if(rayColorResult != null){
					ambient = ambient.add(rayColorResult.multiply(collisionInfo.getClosestObject().getReflective()));
				}
			}
			if(collisionInfo.getClosestObject().getMassType() == MassType.TRANSPARENT){
				// Transmission ray
			}
		}
		
		// return final color
		return ambient;
	}
	
	/**
	 * Casts ray from intersection to light source and computes intersections.
	 * @param intersection
	 * @param closestObject
	 * @return
	 */
	private boolean isInShadow(Vector intersection, GraphicObject closestObject){
		Vector shadowRay = this.scene.getLightSource().getDirection(intersection);
		
		// check for any collision for the light ray.
		for(GraphicObject obj : this.scene){
			// be sure we're not checking the object of the current intersection.
			if(!obj.equals(closestObject)){
				Vector collision = obj.doesCollide(intersection, shadowRay);
				if(collision != null){
					return true;
				}
			}
		}
		return false;
	}
	
	private CollisionInstance findCollidingObject(Ray _ray){
		ArrayList<Vector> collisions = new ArrayList<Vector>();
		ArrayList<GraphicObject> objects = new ArrayList<GraphicObject>();
		
		// Check and save all collision instances to be examined after.
		for(GraphicObject obj : this.scene){
			if(!obj.equals(_ray.getPreviousObject())){
				Vector collision = obj.doesCollide(_ray.getInitialPos(), _ray.getRay());
				if(collision != null){
					collisions.add(collision);
					objects.add(obj);
				}
			}
		}
		
		//if no collision, set primary ray to background color, otherwise keep color null.
		if(collisions.size() == 0){
			return null;  
		}
		
		// determine which object is closest and mark the closest object and its intersection.
		GraphicObject closestObject = null;
		Vector intersection = null;
		float smallestDist = Float.POSITIVE_INFINITY;
		
		for(int i = 0; i < collisions.size(); i++){
			float dist = collisions.get(i).getDirection(_ray.getInitialPos()).getMagnitude();
			if(dist < smallestDist){
				closestObject = objects.get(i);
				intersection = collisions.get(i);
				smallestDist = dist;
			}
		}
		
		return new CollisionInstance(closestObject, intersection);
	}
	
	/**
	 * draws all currently stored pixels to a image file.
	 */
	public void draw(){
		BufferedImage image = null;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for(int i=0; i < height;i++){
            for(int j=0; j < width; j++){
                image.setRGB(j, i, pixels[i][j].getRGB());
            }
        }
		File f = new File("output\\Output.jpg");  //output file path
		try {
			ImageIO.write(image, "jpg", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: draws all pixels to a image file.
	}
}
