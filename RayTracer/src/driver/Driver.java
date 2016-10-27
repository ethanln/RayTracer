package driver;

import model.Scene;
import raytracer.RayTracer;
import util.SceneParser;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scene s = SceneParser.buildScene("scenes//SceneII.rayTracing");
		RayTracer tracer = new RayTracer(s, 120, 50, 5);
		tracer.trace();
	}

}
