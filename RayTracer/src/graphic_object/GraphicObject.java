package graphic_object;

import java.awt.Color;

import util.Vector;

public abstract class GraphicObject{
	public enum ShapeType{
		SPHERE,
		POLYGON	
	};
	
	public enum MassType{
		TRANSPARENT,
		SOLID
	}
	
	// SHAPE TYPE
	private ShapeType shapeType;
	
	// MASS TYPE
	private MassType massType;
	
	// MATERIALS
	private Color reflective;
	private Color diffuse;
	private Color specularHighlight;
	
	private float phongConstant;
	
	/**
	 * Constructor
	 * @param _shapeType
	 */
	public GraphicObject(ShapeType _shapeType, MassType _massType){
		this.shapeType = _shapeType;
		this.massType = _massType;
	}
	
	/**
	 * Overridable method for collision computation, returns null if no collision occurs.
	 * @param initialPos
	 * @param direction
	 * @return
	 */
	public abstract Vector doesCollide(Vector initialPos, Vector direction);
	
	public ShapeType getShapeType() {
		return shapeType;
	}

	public Color getReflective() {
		return reflective;
	}

	public void setReflective(Color reflective) {
		this.reflective = reflective;
	}

	public Color getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Color diffuse) {
		this.diffuse = diffuse;
	}

	public Color getSpecularHighlight() {
		return specularHighlight;
	}

	public void setSpecularHighlight(Color specularHighlight) {
		this.specularHighlight = specularHighlight;
	}

	public float getPhongConstant() {
		return phongConstant;
	}

	public void setPhongConstant(float phongConstant) {
		this.phongConstant = phongConstant;
	}

	public MassType getMassType() {
		return massType;
	}

	public void setMassType(MassType massType) {
		this.massType = massType;
	}
}
