package util;

public class UV {
	public float u;
	public float v;
	
	public UV(float _u, float _v){
		this.u = _u;
		this.v = _v;
	}
	
	public UV translate(UV translation){
		return new UV(this.u - translation.u, this.v - translation.v);
	}
	
	public UV normalize(){
		return new UV(this.u / this.magnitude(), this.v / this.magnitude());
	}
	
	public float magnitude(){
		return (float)Math.sqrt(Math.pow(this.u, 2) + Math.pow(this.v, 2));
	}
}
