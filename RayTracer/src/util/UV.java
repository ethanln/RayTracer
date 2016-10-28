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
}
