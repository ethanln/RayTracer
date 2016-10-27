package util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import graphic_object.Sphere;
import graphic_object.GraphicObject.MassType;
import graphic_object.GraphicObject.ShapeType;
import graphic_object.Edge;
import graphic_object.Polygon;
import model.Scene;

public class SceneParser {
	private static SceneParser inst;
	
	private SceneParser(){}
	
	private static SceneParser instance(){
		if(inst == null){
			inst = new SceneParser();
		}
		return inst;
	}
	
	private Scene _buildScene(String fileName){
		Scene scene = new Scene();
		
		BufferedReader reader = null;
		try{
			String line;
			reader = new BufferedReader(new FileReader(fileName));
			
			while((line = reader.readLine()) != null){
				this.readTokens(line, scene);
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
			return null;
		}
		finally{
			try{
				reader.close();
			}
			catch(IOException ex){
				ex.printStackTrace();
				return null;
			}
		}
		return scene;
	}
	
	private void readTokens(String line, Scene scene){
		Scanner reader = null;
		
		reader = new Scanner(line);
		String token = reader.next();
		// TODO: Build methods that handles all these cases
		switch(token){
			case "CameraLookAt":
				this.parseCameraLookAt(scene, reader);
				break;
			case "CameraLookFrom":
				this.parseCameraLookFrom(scene, reader);
				break;
			case "CameraLookUp":
				this.parseCameraLookUp(scene, reader);
				break;
			case "FieldOfView":
				this.parseFOV(scene, reader);
				break;
			case "DirectionToLight":
				this.parseDirectionToLight(scene, reader);
				break;
			case "AmbientLight":
				this.parseAmbientLight(scene, reader);
				break;
			case "BackgroundColor":
				this.parseBackgroundColor(scene, reader);
				break;
			case "Sphere":
				this.parseSphere(scene, reader);
				break;
			case "Polygon":
				this.parsePolygon(scene, reader);
				break;
			case "Triangle":
				this.parsePolygon(scene, reader);
				break;
			default:
				break;
		}	
		
		reader.close();
	}
	
	/**
	 * parse camera look at.
	 * @param scene
	 * @param reader
	 */
	private void parseCameraLookAt(Scene scene, Scanner reader){
		float x = Float.parseFloat(reader.next());
		float y = Float.parseFloat(reader.next());
		float z = Float.parseFloat(reader.next());
		
		scene.setCamLookAt(new Vector(x, y, z));
	}
	
	/**
	 * parse camera look from.
	 * @param scene
	 * @param reader
	 */
	private void parseCameraLookFrom(Scene scene, Scanner reader){
		float x = Float.parseFloat(reader.next());
		float y = Float.parseFloat(reader.next());
		float z = Float.parseFloat(reader.next());
		
		scene.setCamLookFrom(new Vector(x, y, z));
	}

	/**
	 * parse camera lookup.
	 * @param scene
	 * @param reader
	 */
	private void parseCameraLookUp(Scene scene, Scanner reader){
		float x = Float.parseFloat(reader.next());
		float y = Float.parseFloat(reader.next());
		float z = Float.parseFloat(reader.next());
		
		scene.setCamLookUp(new Vector(x, y, z));
	}
	
	/**
	 * parse field of view
	 * @param scene
	 * @param reader
	 */
	private void parseFOV(Scene scene, Scanner reader){
		float fov = Float.parseFloat(reader.next());
		
		scene.setFov(fov);
	}
	
	/**
	 * parse light direction and light color
	 * @param scene
	 * @param reader
	 */
	private void parseDirectionToLight(Scene scene, Scanner reader){
		float x = Float.parseFloat(reader.next());
		float y = Float.parseFloat(reader.next());
		float z = Float.parseFloat(reader.next());
		
		scene.setLightSource(new Vector(x, y, z));
		
		if(reader.hasNext()){
			reader.next();
			int R = (int)(255.0f * Float.parseFloat(reader.next()));
			int G = (int)(255.0f * Float.parseFloat(reader.next()));
			int B = (int)(255.0f * Float.parseFloat(reader.next()));
			scene.setLightColor(new Color(R, G, B));
		}
	}
	
	/**
	 * parse ambient light color.
	 * @param scene
	 * @param reader
	 */
	private void parseAmbientLight(Scene scene, Scanner reader){
		int R = (int)(255.0f * Float.parseFloat(reader.next()));
		int G = (int)(255.0f * Float.parseFloat(reader.next()));
		int B = (int)(255.0f * Float.parseFloat(reader.next()));
		scene.setAmbientLight(new Color(R, G, B));
	}
	
	/**
	 * parse background color.
	 * @param scene
	 * @param reader
	 */
	private void parseBackgroundColor(Scene scene, Scanner reader){
		int R = (int)(255.0f * Float.parseFloat(reader.next()));
		int G = (int)(255.0f * Float.parseFloat(reader.next()));
		int B = (int)(255.0f * Float.parseFloat(reader.next()));
		scene.setBackgroundColor(new Color(R, G, B));
	}
	
	/**
	 * parse sphere.
	 * @param scene
	 * @param reader
	 */
	private void parseSphere(Scene scene, Scanner reader){
		Sphere s = new Sphere(ShapeType.SPHERE, MassType.SOLID);
		String token;
		
		/**
		 * parse materials and geometric data.
		 */
		while(reader.hasNext()){
			token = reader.next();
			switch(token){
				case "Center":
					float x = Float.parseFloat(reader.next());
					float y = Float.parseFloat(reader.next());
					float z = Float.parseFloat(reader.next());
					s.setCenter(new Vector(x, y, z));
					break;
					
				case "Radius":
					float radius = Float.parseFloat(reader.next());
					s.setRadius(radius);
					break;
					
				case "Diffuse":
					int Rd = (int)(255.0f * Float.parseFloat(reader.next()));
					int Gd = (int)(255.0f * Float.parseFloat(reader.next()));
					int Bd = (int)(255.0f * Float.parseFloat(reader.next()));
					s.setDiffuse(new Color(Rd, Gd, Bd));
					break;
					
				case "SpecularHighlight":
					int Rs = (int)(255.0f * Float.parseFloat(reader.next()));
					int Gs = (int)(255.0f * Float.parseFloat(reader.next()));
					int Bs = (int)(255.0f * Float.parseFloat(reader.next()));
					s.setSpecularHighlight(new Color(Rs, Gs, Bs));
					break;
					
				case "PhongConstant":
					float phongConstant = Float.parseFloat(reader.next());
					s.setPhongConstant(phongConstant);
					break;
					
				case "Reflective":
					int Rr = (int)(255.0f * Float.parseFloat(reader.next()));
					int Gr = (int)(255.0f * Float.parseFloat(reader.next()));
					int Br = (int)(255.0f * Float.parseFloat(reader.next()));
					s.setReflective(new Color(Rr, Gr, Br));
					break;
					
				case "Transparent":
					s.setMassType(MassType.TRANSPARENT);
					break;
					
				default:
					break;
			}
		}
		
		scene.addGraphicObject(s);
	}
	
	private void parsePolygon(Scene scene, Scanner reader){
		Polygon p = new Polygon(ShapeType.POLYGON, MassType.SOLID);
		String token;
		ArrayList<Vector> points = new ArrayList<Vector>();
		
		/**
		 * get all points.
		 */
		while(reader.hasNext()){
			token = reader.next();
			if(token.equals("Material")){
				break;
			}
			float x = Float.parseFloat(token);
			float y = Float.parseFloat(reader.next());
			float z = Float.parseFloat(reader.next());
			
			points.add(new Vector(x, y, z));
		}
		
		/**
		 * get all edges.
		 */
		Vector prev = null;
		for(Vector point : points){
			if(prev != null){
				Edge e = new Edge();
				e.pt1 = prev;
				e.pt2 = point;
				p.addEdge(e);
			}
			prev = point;
		}
		
		/**
		 * include loop edge.
		 */
		Edge e = new Edge();
		e.pt1 = prev;
		e.pt2 = points.get(0);
		p.addEdge(e);
		
		
		/**
		 * parse materials
		 */
		while(reader.hasNext()){
			token = reader.next();
			switch(token){				
				case "Diffuse":
					int Rd = (int)(255.0f * Float.parseFloat(reader.next()));
					int Gd = (int)(255.0f * Float.parseFloat(reader.next()));
					int Bd = (int)(255.0f * Float.parseFloat(reader.next()));
					p.setDiffuse(new Color(Rd, Gd, Bd));
					break;
					
				case "SpecularHighlight":
					int Rs = (int)(255.0f * Float.parseFloat(reader.next()));
					int Gs = (int)(255.0f * Float.parseFloat(reader.next()));
					int Bs = (int)(255.0f * Float.parseFloat(reader.next()));
					p.setSpecularHighlight(new Color(Rs, Gs, Bs));
					break;
					
				case "PhongConstant":
					float phongConstant = Float.parseFloat(reader.next());
					p.setPhongConstant(phongConstant);
					break;
					
				case "Reflective":
					int Rr = (int)(255.0f * Float.parseFloat(reader.next()));
					int Gr = (int)(255.0f * Float.parseFloat(reader.next()));
					int Br = (int)(255.0f * Float.parseFloat(reader.next()));
					p.setReflective(new Color(Rr, Gr, Br));
					break;
					
				case "Transparent":
					p.setMassType(MassType.TRANSPARENT);
					break;
					
				default:
					break;
			}
		}
		
		scene.addGraphicObject(p);
	}
	
	public static Scene buildScene(String fileName){
		return instance()._buildScene(fileName);
	}
}
