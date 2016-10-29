package graphic_object;

import util.Vector;

public class Sphere extends GraphicObject{

	private Vector center;
	private float radius;
	
	public Sphere(ShapeType _shapeType, MassType _massType) {
		super(_shapeType, _massType);
	}

	@Override
	public Vector doesCollide(Vector initialPos, Vector direction) {
		Vector n = direction.normalize();
		float x0 = initialPos.x;
		float y0 = initialPos.y;
		float z0 = initialPos.z;
		
		float cx = this.center.x;
		float cy = this.center.y;
		float cz = this.center.z;
		
		float dx = n.x;
		float dy = n.y;
		float dz = n.z;
		
		float a = (dx*dx) + (dy*dy) + (dz*dz);
		float b = 2.0f *((dx * x0) - (dx * cx) + (dy * y0) - (dy*cy) + (dz * z0) - (dz * cz));
		
		float c = (float)Math.pow(x0, 2.0f) - (2.0f * x0 * cx) + (float)Math.pow(cx, 2.0f) 
				+ (float)Math.pow(y0, 2.0f) - (2.0f * y0 * cy) + (float)Math.pow(cy,  2.0f) 
				+ (float)Math.pow(z0, 2.0f) - (2.0f * z0 * cz) + (float)Math.pow(cz, 2.0f) 
				- (float)Math.pow(this.radius, 2.0f); 
		
		float discriminant = (float)Math.pow(b, 2.0f) - (4.0f * a * c);
		if(discriminant < 0){
			return null;
		}

		float t0 = ((-b) - (float)Math.sqrt((float)Math.pow(b, 2.0f) - (4.0f * a * c))) / (2.0f * a); 
		if(t0 <= 0.0f){
			float t1 = ((-b) + (float)Math.sqrt((float)Math.pow(b, 2.0f) - (4.0f * a * c))) / (2.0f * a);
			if(t1 <= 0.0f){
				return null;
			}

			return new Vector(x0 + (t1 * dx), y0 + (t1 * dy), z0 + (t1 * dz));
		}
		return new Vector(x0 + (t0 * dx), y0 + (t0 * dy), z0 + (t0 * dz));
	}

	public Vector getCenter() {
		return center;
	}

	public void setCenter(Vector center) {
		this.center = center;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	@Override
	public Vector getNormal(Vector pt) {
		return pt.getDirection(this.center);
	}

	@Override
	public boolean equals(GraphicObject obj) {
		if(obj.getShapeType() == ShapeType.SPHERE){
			Sphere s = (Sphere)obj;
			return s.getCenter().x == this.center.x 
					&& s.getCenter().y == this.center.y 
					&& s.getCenter().z == this.center.z
					&& s.radius == this.radius;
		}
		return false;
	}
}
