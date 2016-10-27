package util;

public class Vector {
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vector(float _x, float _y, float _z, float _w){
		this.x = _x;
		this.y = _y;
		this.z = _z;
		this.w = _w;
	}
	
	public Vector(float _x, float _y, float _z){
		this.x = _x;
		this.y = _y;
		this.z = _z;
		this.w = 0.0f;
	}
	
	public Vector(float _x, float _y){
		this.x = _x;
		this.y = _y;
		this.z = 0.0f;
		this.w = 0.0f;
	}
	
	public Vector(){
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
		this.w = 0.0f;
	} 
	
	public float getMagnitude(){
		return (float)Math.sqrt(Math.pow(this.x, 2.0f) + Math.pow(this.y, 2.0f) + Math.pow(this.z, 2.0f));
	}
	
	public Vector getDirection(Vector vec){
		return new Vector(this.x - vec.x, this.y - vec.y, this.z - vec.z);
	}
	
	public Vector normalize(){
		float val = (float)Math.sqrt((float)Math.pow(this.x, 2) + (float)Math.pow(this.y, 2) + (float)Math.pow(this.z, 2));
		return new Vector(this.x / val, this.y / val, this.z / val);
	}
}
