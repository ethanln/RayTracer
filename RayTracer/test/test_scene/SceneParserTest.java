package test_scene;
import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import graphic_object.GraphicObject.MassType;
import graphic_object.Polygon;
import graphic_object.Sphere;
import model.Scene;
import util.SceneParser;
import util.Vector;

public class SceneParserTest {

	@Test
	public void test() {
		Scene s = SceneParser.buildScene("scenes//test.rayTracing");
		
		// test look at.
		Vector lookAt = s.getCamLookAt();
		assertEquals(String.valueOf(lookAt.x), "0.0");
		assertEquals(String.valueOf(lookAt.y), "0.0");
		assertEquals(String.valueOf(lookAt.z), "0.0");
		
		// test look from.
		Vector lookFrom = s.getCamLookFrom();
		assertEquals(String.valueOf(lookFrom.x), "0.0");
		assertEquals(String.valueOf(lookFrom.y), "0.0");
		assertEquals(String.valueOf(lookFrom.z), "1.2");
		
		// test cam look up.
		Vector lookUp = s.getCamLookUp();
		assertEquals(String.valueOf(lookUp.x), "0.0");
		assertEquals(String.valueOf(lookUp.y), "1.0");
		assertEquals(String.valueOf(lookUp.z), "0.0");
		
		// test field ov view.
		assertEquals(String.valueOf(s.getFov()), "55.0");
		
		// test light direction.
		Vector directionToLight = s.getLightSource();
		assertEquals(String.valueOf(directionToLight.x), "0.0");
		assertEquals(String.valueOf(directionToLight.y), "1.0");
		assertEquals(String.valueOf(directionToLight.z), "0.0");
		
		// test light color.
		Color lightColor = s.getLightColor().toColor();
		assertEquals(String.valueOf(lightColor.getRed()), "255");
		assertEquals(String.valueOf(lightColor.getGreen()), "255");
		assertEquals(String.valueOf(lightColor.getBlue()), "255");
		
		// test ambient light color.
		Color ambientLight = s.getAmbientLight().toColor();
		assertEquals(String.valueOf(ambientLight.getRed()), "0");
		assertEquals(String.valueOf(ambientLight.getGreen()), "0");
		assertEquals(String.valueOf(ambientLight.getBlue()), "0");
		
		// test background color.
		Color backgroundColor = s.getBackgroundColor().toColor();
		assertEquals(String.valueOf(backgroundColor.getRed()), "51");
		assertEquals(String.valueOf(backgroundColor.getGreen()), "51");
		assertEquals(String.valueOf(backgroundColor.getBlue()), "51");
		
		Sphere sphere = (Sphere)s.getGraphicObject(0);
		
		// test sphere center.
		assertEquals(String.valueOf(sphere.getCenter().x), "0.0");
		assertEquals(String.valueOf(sphere.getCenter().y), "0.3");
		assertEquals(String.valueOf(sphere.getCenter().z), "0.0");
		
		// test sphere radius.
		assertEquals(String.valueOf(sphere.getRadius()), "0.2");
		
		// test sphere reflective color.
		assertEquals(String.valueOf(sphere.getReflective().toColor().getRed()), "191");
		assertEquals(String.valueOf(sphere.getReflective().toColor().getGreen()), "191");
		assertEquals(String.valueOf(sphere.getReflective().toColor().getBlue()), "191");
		
		// test sphere reflective color.
		assertEquals(sphere.getMassType(), MassType.TRANSPARENT);	
		
		
		Polygon poly = (Polygon)s.getGraphicObject(1);
		// test triangle edges.
		assertEquals(String.valueOf(poly.getEdge(0).pt1.x), "0.0");
		assertEquals(String.valueOf(poly.getEdge(0).pt1.y), "-0.5");
		assertEquals(String.valueOf(poly.getEdge(0).pt1.z), "0.5");
		
		assertEquals(String.valueOf(poly.getEdge(0).pt2.x), "1.0");
		assertEquals(String.valueOf(poly.getEdge(0).pt2.y), "0.5");
		assertEquals(String.valueOf(poly.getEdge(0).pt2.z), "0.0");
		
		assertEquals(String.valueOf(poly.getEdge(1).pt1.x), "1.0");
		assertEquals(String.valueOf(poly.getEdge(1).pt1.y), "0.5");
		assertEquals(String.valueOf(poly.getEdge(1).pt1.z), "0.0");
		
		assertEquals(String.valueOf(poly.getEdge(1).pt2.x), "0.0");
		assertEquals(String.valueOf(poly.getEdge(1).pt2.y), "-0.5");
		assertEquals(String.valueOf(poly.getEdge(1).pt2.z), "-0.5");
		
		// test triangle diffuse color.
		assertEquals(String.valueOf(poly.getDiffuse().toColor().getRed()), "0");
		assertEquals(String.valueOf(poly.getDiffuse().toColor().getGreen()), "0");
		assertEquals(String.valueOf(poly.getDiffuse().toColor().getBlue()), "255");
		
		// test triangle specular highlight color.
		assertEquals(String.valueOf(poly.getSpecularHighlight().toColor().getRed()), "255");
		assertEquals(String.valueOf(poly.getSpecularHighlight().toColor().getGreen()), "255");
		assertEquals(String.valueOf(poly.getSpecularHighlight().toColor().getBlue()), "255");
		
		assertEquals(String.valueOf(poly.getPhongConstant()), "4.0");
		
		assertEquals(String.valueOf(poly.getNormal(null).x), "1.0");
		assertEquals(String.valueOf(poly.getNormal(null).y), "-1.0");
		assertEquals(String.valueOf(poly.getNormal(null).z), "0.0");
		
		
		Polygon poly_again = (Polygon)s.getGraphicObject(2);
		// test triangle edges.
		assertEquals(String.valueOf(poly_again.getEdge(0).pt1.x), "0.0");
		assertEquals(String.valueOf(poly_again.getEdge(0).pt1.y), "-0.5");
		assertEquals(String.valueOf(poly_again.getEdge(0).pt1.z), "0.5");
		
		assertEquals(String.valueOf(poly_again.getEdge(0).pt2.x), "0.0");
		assertEquals(String.valueOf(poly_again.getEdge(0).pt2.y), "-0.5");
		assertEquals(String.valueOf(poly_again.getEdge(0).pt2.z), "-0.5");
		
		assertEquals(String.valueOf(poly_again.getEdge(1).pt1.x), "0.0");
		assertEquals(String.valueOf(poly_again.getEdge(1).pt1.y), "-0.5");
		assertEquals(String.valueOf(poly_again.getEdge(1).pt1.z), "-0.5");
		
		assertEquals(String.valueOf(poly_again.getEdge(1).pt2.x), "-1.0");
		assertEquals(String.valueOf(poly_again.getEdge(1).pt2.y), "0.5");
		assertEquals(String.valueOf(poly_again.getEdge(1).pt2.z), "0.0");
		
		// test triangle diffuse color.
		assertEquals(String.valueOf(poly_again.getDiffuse().toColor().getRed()), "255");
		assertEquals(String.valueOf(poly_again.getDiffuse().toColor().getGreen()), "255");
		assertEquals(String.valueOf(poly_again.getDiffuse().toColor().getBlue()), "0");
		
		// test triangle specular highlight color.
		assertEquals(String.valueOf(poly_again.getSpecularHighlight().toColor().getRed()), "255");
		assertEquals(String.valueOf(poly_again.getSpecularHighlight().toColor().getGreen()), "255");
		assertEquals(String.valueOf(poly_again.getSpecularHighlight().toColor().getBlue()), "255");
		
		assertEquals(String.valueOf(poly_again.getPhongConstant()), "4.0");
		
		assertEquals(String.valueOf(poly_again.getNormal(null).x), "-1.0");
		assertEquals(String.valueOf(poly_again.getNormal(null).y), "-1.0");
		assertEquals(String.valueOf(poly_again.getNormal(null).z), "0.0");
	}

}
