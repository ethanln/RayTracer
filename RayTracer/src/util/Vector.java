package util;

public class Vector {
	public enum Axis{
		X,
		Y,
		Z,
		NONE
	};
	
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
	
	public Vector eliminateAxis(Axis axis){
		switch(axis){
			case X:
				return new Vector(0.0f, this.y, this.z);
			case Y:
				return new Vector(this.x, 0.0f, this.z);
			case Z:
				return new Vector(this.x, this.y, 0.0f);
			default:
				return this;
		}
	}
	
	public Axis getLargestAxis(){
		float highest = Math.max(this.x, this.y);
		highest = Math.max(highest, this.z);
		
		if(highest == this.x){
			return Axis.X;
		}
		else if(highest == this.y){
			return Axis.Y;
		}
		else{
			return Axis.Z;
		}
	}
	
	public float getVerticalCoordinate(Axis axis){
		switch(axis){
			case X:
				return this.z;
			case Y:
				return this.z;
			case Z:
				return this.y;
			default:
				return this.x;
		}
	}
	
	public float getHorizontalCoordinate(Axis axis){
		switch(axis){
			case X:
				return this.y;
			case Y:
				return this.x;
			case Z:
				return this.x;
			default:
				return this.x;
		}
	}
	
	public UV toUV(Axis axis){
		switch(axis){
			case X:
				return new UV(this.y, this.z);
			case Y:
				return new UV(this.x, this.z);
			case Z:
				return new UV(this.x, this.y);
			default:
				return new UV(0.0f, 0.0f);
		}
	}
	
	public Vector translateVector(Vector translation){
		return new Vector(this.x - translation.x, this.y - translation.y, this.z - translation.z);
	}
}
