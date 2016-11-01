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
	
	public Vector add(Vector translation){
		return new Vector(this.x + translation.x, this.y + translation.y, this.z + translation.z);
	}
	
	public float dotProduct(Vector vec){
		return (this.x * vec.x) + (this.y * vec.y) + (this.z * vec.z);
	}
	
	public Vector multiplyByConstant(float constant){
		return new Vector(this.x * constant, this.y * constant, this.z * constant);
	}
	
	public Vector subtract(Vector vec){
		return new Vector(this.x - vec.x, this.y - vec.y, this.z - vec.z);
	}
	
	public Vector divideByConstant(float constant){
		return new Vector(this.x / constant, this.y / constant, this.z / constant);
	}
	
	public Vector invert(){
		return new Vector(-this.x, -this.y, -this.z);
	}
	
	public static Vector crossProduct(Vector p1, Vector p2){
		
		Vector e1 = p1;
		Vector e2 = p2;
		
		float xn = (e1.y * e2.z) - (e2.y * e1.z); // a2 * b3 - b2 * a3
		float yn = (e1.z * e2.x) - (e2.z * e1.x); // a1 * b3 - b1 * a3
		float zn = (e1.x * e2.y) - (e2.x * e1.y); // a1 * b2 - b1 * a2
		
		return new Vector(xn, yn, zn);

	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		try{
			Vector v = (Vector)obj;
			if(v.x == this.x && v.y == this.y && v.z == this.z){
				return true;
			}
		}
		catch(Exception e){
			return false;
		}
		return false;
	}
}
