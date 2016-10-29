package model;

import java.util.ArrayList;
import java.util.Iterator;

import graphic_object.GraphicObject;
import util.RColor;
import util.Vector;

public class Scene implements Iterable<GraphicObject>{
	private Vector camLookAt;
	private Vector camLookFrom;
	private Vector camLookUp;
	private Vector lightSource;
	
	private RColor lightColor;
	private RColor ambientLight;
	private RColor backgroundColor;
	
	private float fov;
	
	private ArrayList<GraphicObject> objects;
	
	public Scene(){
		this.objects = new ArrayList<GraphicObject>();
		
		this.camLookAt = new Vector(0.0f, 0.0f, 0.0f);
		this.camLookFrom = new Vector(0.0f, 0.0f, 0.0f);
		this.camLookUp = new Vector(0.0f, 0.0f, 0.0f);
		this.lightSource = new Vector(0.0f, 0.0f, 0.0f);
		
		this.lightColor = new RColor(0, 0, 0);
		this.ambientLight = new RColor(0, 0, 0);
		this.backgroundColor = new RColor(0, 0, 0);
		
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

	public RColor getLightColor() {
		return lightColor;
	}

	public void setLightColor(RColor lightColor) {
		this.lightColor = lightColor;
	}

	public RColor getAmbientLight() {
		return ambientLight;
	}

	public void setAmbientLight(RColor ambientLight) {
		this.ambientLight = ambientLight;
	}

	public RColor getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(RColor backgroundColor) {
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
