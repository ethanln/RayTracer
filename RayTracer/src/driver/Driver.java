package driver;

import model.Scene;
import raytracer.RayTracer;
import util.SceneParser;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scene s = SceneParser.buildScene("scenes//diffuse.rayTracing");
		RayTracer tracer = new RayTracer(s, 800, 800, 5);
		System.out.println("Tracing...");
		tracer.trace();
		System.out.println("Drawing...");
		tracer.draw();
		System.out.println("Finished!");
	}

}
