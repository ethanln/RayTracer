package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import graphic_object.GraphicObject;
import util.Vector;

public class Scene implements Iterable<GraphicObject>{
	private Vector camLookAt;
	private Vector camLookFrom;
	private Vector camLookUp;
	private Vector lightSource;
	
	private Color lightColor;
	private Color ambientLight;
	private Color backgroundColor;
	
	private float fov;
	
	private ArrayList<GraphicObject> objects;
	
	public Scene(){
		this.objects = new ArrayList<GraphicObject>();
		
		this.camLookAt = new Vector(0.0f, 0.0f, 0.0f);
		this.camLookFrom = new Vector(0.0f, 0.0f, 0.0f);
		this.camLookUp = new Vector(0.0f, 0.0f, 0.0f);
		this.lightSource = new Vector(0.0f, 0.0f, 0.0f);
		
		this.lightColor = new Color(0, 0, 0);
		this.lightColor = new Color(0, 0, 0);
		this.lightColor = new Color(0, 0, 0);
		
		this.fov = 0.0f;
	}

	@Override
	public Iterator<GraphicObject> iterator() {
		return this.objects.iterator();
	}

	public Vector getCamLookAt() {
		return camLookAt;
	}

	public void setCamLookAt(Vector camLookAt) {
		this.camLookAt = camLookAt;
	}

	public Vector getCamLookFrom() {
		return camLookFrom;
	}

	public void setCamLookFrom(Vector camLookFrom) {
		this.camLookFrom = camLookFrom;
	}

	public Vector getCamLookUp() {
		return camLookUp;
	}

	public void setCamLookUp(Vector camLookUp) {
		this.camLookUp = camLookUp;
	}

	public Vector getLightSource() {
		return lightSource;
	}

	public void setLightSource(Vector lightSource) {
		this.lightSource = lightSource;
	}

	public Color getLightColor() {
		return lightColor;
	}

	public void setLightColor(Color lightColor) {
		this.lightColor = lightColor;
	}

	public Color getAmbientLight() {
		return ambientLight;
	}

	public void setAmbientLight(Color ambientLight) {
		this.ambientLight = ambientLight;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}
	
	public void addGraphicObject(GraphicObject obj){
		this.objects.add(obj);
	}
	
	public GraphicObject getGraphicObject(int index){
		return this.objects.get(index);
	}
}
