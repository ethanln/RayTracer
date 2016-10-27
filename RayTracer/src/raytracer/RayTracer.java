package raytracer;

import java.awt.Color;
import java.util.ArrayList;

import graphic_object.GraphicObject;
import model.Scene;
import raytracer.Ray.RayType;
import util.Vector;

public class RayTracer {

	private Color[][] pixels;
	private Vector[][] coordinates;
	private Ray root;
	private int depth;
	private Scene scene;
	private int width;
	private int height;
	
	public RayTracer(Scene _scene, int _width, int _height, int _depth){
		this.pixels = new Color[_height][_width];
		this.coordinates = new Vector[_height][_width];
		this.root = null;
		this.depth = _depth;
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
			String line = "";
			for(int j = 0; j < this.width; j++){
				// Setup ray tree.
				this.root = new Ray();
				
				// TODO: instantiate root with vector ray between camera position and pixel position
				this.root.setRay(this.coordinates[i][j].getDirection(this.scene.getCamLookFrom()));
				this.root.setInitialPos(this.scene.getCamLookFrom());
				this.root.setType(RayType.PRIMARY);
				if(this.rayTrace(this.root, 1)){
					line += "0";
				}
				else{
					line += "-";
				}
				//System.out.println(this.root.getRay().x + ", " + this.root.getRay().y + ", " + this.root.getRay().z);
				
				// determine pixel color after getting ray tree is finished and store it in pixel matrix.
				// this.pixels[i][j] = this.computeColor(this.root);
			}
			System.out.println(line);
		}
		return false;
	}
	
	/**
	 * Recursively compute all rays.
	 * @param _ray
	 */
	private boolean rayTrace(Ray _ray, int currentDepth){
		ArrayList<Vector> collisions = new ArrayList<Vector>();
		ArrayList<GraphicObject> objects = new ArrayList<GraphicObject>();
		
		// check for any collision by iterating through all objects, temporarily store the closest object for later use.
		for(GraphicObject obj : this.scene){
			Vector collision = obj.doesCollide(_ray.getInitialPos(), _ray.getRay());
			if(collision != null){
				collisions.add(collision);
				objects.add(obj);
			}
		}
		
		//if no collision, set primary ray to background color, other words keep color null.
		if(collisions.size() == 0){
			if(_ray.getType() == RayType.PRIMARY){
				_ray.setColor(this.scene.getBackgroundColor());
			}
			return false;  // TEMP
		}
		
		// determine which object is closest and mark the closest collision point.
		GraphicObject closestObject = null;
		Vector closestPoint = null;
		float smallestDist = Float.POSITIVE_INFINITY;
		
		for(int i = 0; i < collisions.size(); i++){
			if(collisions.get(i).getDirection(_ray.getInitialPos()).getMagnitude() < smallestDist){
				closestObject = objects.get(i);
				closestPoint = collisions.get(i);
			}
		}

		return collisions.size() > 0;
		// =====TODO: check for any collision by iterating through all objects, temporarily store the object for later use.
		// =====	TODO: if no collision, set ray type to NON and return
		// =====	TODO: if collision, mark collision point.

		// TODO: get color
		// TODO: cast shadow ray and adjust color if its in light or shadow
		
		// If current depth is less than depth limit, cast all rays
		//if(currentDepth < this.depth){
			// TODO: COMPUTE TO GET ALL RAYS AND ADD THEM TO THE RAY OBJECT
			//		TODO: compute normal for Transmission on object and add to current ray list
			//		TODO: if object is transparent, compute reflection and add to current ray list	
		//}
		
		// At end of each recusive call:
		//for(Ray ray : _ray){
			// do not count shadow rays for recursive calls
		//	if(ray.getType() != Ray.RayType.SHADOW){
		//		rayTrace(ray, currentDepth + 1);
		//	}
		//}
	}
	
	/**
	 * recursively compute color values starting from the bottom to the root of the tree of rays.
	 * @return
	 */
	private Color computeColor(Ray _ray){
		// TODO: Implement
		return null;
	}
	
	/**
	 * draws all currently stored pixels to a image file.
	 */
	public void draw(){
		// TODO: draws all pixels to a image file.
	}
}
