package graphic_object;


import util.RColor;
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
	private RColor reflective;
	private RColor diffuse;
	private RColor specularHighlight;
	private RColor transparency;
	
	private float phongConstant;
	
	private float refractionMaterial;

	
	/**
	 * Constructor
	 * @param _shapeType
	 */
	public GraphicObject(ShapeType _shapeType, MassType _massType){
		this.shapeType = _shapeType;
		this.massType = _massType;
		this.reflective = new RColor(0.0f, 0.0f, 0.0f);
		this.diffuse = new RColor(0.0f, 0.0f, 0.0f);
		this.specularHighlight = new RColor(0.0f, 0.0f, 0.0f);
		this.setTransparency(new RColor(0.0f, 0.0f, 0.0f));
		this.phongConstant = 0.0f;
		this.refractionMaterial = 0.0f;
	}
	
	/**
	 * Overridable method for collision computation, returns null if no collision occurs.
	 * @param initialPos
	 * @param direction
	 * @return
	 */
	public abstract Vector doesCollide(Vector initialPos, Vector direction);
	
	public abstract Vector getNormal(Vector pt);
	
	public abstract boolean equals(GraphicObject obj);
	
	public ShapeType getShapeType() {
		return shapeType;
	}

	public RColor getReflective() {
		return reflective;
	}

	public void setReflective(RColor reflective) {
		this.reflective = reflective;
	}

	public RColor getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(RColor diffuse) {
		this.diffuse = diffuse;
	}

	public RColor getSpecularHighlight() {
		return specularHighlight;
	}

	public void setSpecularHighlight(RColor specularHighlight) {
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

	public float getRefractionMaterial() {
		return refractionMaterial;
	}

	public void setRefractionMaterial(float refractionMaterial) {
		this.refractionMaterial = refractionMaterial;
	}

	public RColor getTransparency() {
		return transparency;
	}

	public void setTransparency(RColor transparency) {
		this.transparency = transparency;
	}
}
