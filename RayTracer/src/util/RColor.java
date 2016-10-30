package util;

import java.awt.Color;

public class RColor {
	public float R;
	public float G;
	public float B;
	
	public RColor(float _R, float _G, float _B){
		this.R = _R;
		this.G = _G;
		this.B = _B;
	}
	
	public Color toColor(){
		try{
			return new Color(this.R, this.G, this.B);
		}
		catch(Exception e){
			//e.printStackTrace();
			if(this.R > 1){
				this.R = 1.0f;
			}
			if(this.G > 1){
				this.G = 1.0f;
			}
			if(this.B > 1){
				this.B = 1.0f;
			}
		}
		return new Color(this.R, this.G, this.B);
	}
	
	public RColor multiply(RColor c){
		return new RColor(this.R * c.R, this.G * c.G, this.B * c.B);
	}
	
	public RColor add(RColor c){
		return new RColor(this.R + c.R, this.G + c.G, this.B + c.B);
	}
	
	public RColor substract(RColor c){
		return new RColor(this.R - c.R, this.G - c.G, this.B - c.B);
	}
	
	public RColor multiplyByConstant(float constant){
		return new RColor(this.R * constant, this.G * constant, this.B * constant);
	}
	
	public boolean isBlack(){
		return this.R == 0.0f && this.G == 0.0f && this.B == 0.0f;
	}
}
