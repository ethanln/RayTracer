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
import shader.ShaderFactory;
import shader.ShaderFactory.ShaderType;
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
	public RColor rayTrace(Ray _ray, int currentDepth){
		// increment depth
		currentDepth++;
		// find colliding objects
		CollisionInstance collisionInfo = this.findCollidingObject(_ray);

		if(collisionInfo == null || collisionInfo.getClosestObject() == null || collisionInfo.getIntersection() == null){
			if(_ray.getType() == RayType.PRIMARY || _ray.getType() == RayType.TRANSMISSION){
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
		// get ambient light
		RColor intersectionColor = ShaderFactory.makeShader(ShaderType.AMBIENT).doShader(this, collisionInfo, _ray, currentDepth);
		
		if(currentDepth < this.depthLimit){
			// Reflection Ray
			if(!collisionInfo.getClosestObject().getReflective().isBlack() /*&& _ray.getType() != RayType.REFLECTION*/){
				RColor reflections = ShaderFactory.makeShader(ShaderType.REFLECTION).doShader(this, collisionInfo, _ray, currentDepth);
				if(reflections != null){
					intersectionColor = intersectionColor.add(reflections);
				}
				intersectionColor = intersectionColor.multiplyByConstant(1.0f / currentDepth);
			}
			// Refraction Ray
			if(collisionInfo.getClosestObject().getMassType() == MassType.TRANSPARENT /*&& _ray.getType() != RayType.TRANSMISSION*/){
				intersectionColor = intersectionColor.add(ShaderFactory.makeShader(ShaderType.REFRACTION).doShader(this, collisionInfo, _ray, currentDepth));		
			}
		}
		return intersectionColor;
	}
	
	private CollisionInstance findCollidingObject(Ray _ray){
		ArrayList<Vector> collisions = new ArrayList<Vector>();
		ArrayList<GraphicObject> objects = new ArrayList<GraphicObject>();
		
		// Check and save all collision instances to be examined after.
		for(GraphicObject obj : this.scene){
			Vector collision = obj.doesCollide(_ray.getInitialPos(), _ray.getRay());
			if(collision != null){
				collisions.add(collision);
				objects.add(obj);
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
	
	public Scene getScene(){
		return this.scene;
	}
}
