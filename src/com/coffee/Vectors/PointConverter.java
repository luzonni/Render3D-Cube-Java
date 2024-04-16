package com.coffee.Vectors;

import java.awt.Point;

import com.coffee.Renderer.Display;

public class PointConverter {
	
	private static final double ZoomFactor = 1.2;
	private static double scale = 2;
	
	public static Point converterPoint(MyPoint point3D) {
		double x3D = point3D.y * scale;
		double y3D = point3D.z * scale;
		double depth = point3D.x * scale;
		double[] newVal = scale(x3D, y3D, depth);
		int x2D = (int) (Display.WIDTH / 2 + newVal[0]);
		int y2D = (int) (Display.HEIGHT / 2 - newVal[1]);
		return new Point(x2D, y2D);
	}
	
	private static double[] scale(double x3D, double y3D, double depth) {
		double hypotenuse_dist = Math.sqrt(Math.pow(x3D, 2) + Math.pow(y3D, 2));
		double theta = Math.atan2(y3D,  x3D);
		double depth2 = 30 - depth;
		//1400 is global scale
		double localScale = Math.abs(600/(depth2+600));
		hypotenuse_dist *= localScale;
		double[] newVal = new double[2];
		newVal[0] = hypotenuse_dist * Math.cos(theta);
		newVal[1] = hypotenuse_dist * Math.sin(theta);
		return newVal;	
	}
	
	public static void rotateAxisX(MyPoint p, boolean CW, double degrees) {
		double radius = Math.sqrt(Math.pow(p.y, 2) + Math.pow(p.z, 2));
		double theta = Math.atan2(p.z, p.y);
		theta += 2*Math.PI / 360 * degrees * (CW ? -1 : 1);
		p.y = radius * Math.cos(theta);
		p.z = radius * Math.sin(theta);
	}
	
	public static void rotateAxisY(MyPoint p, boolean CW, double degrees) {
		double radius = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.z, 2));
		double theta = Math.atan2(p.x, p.z);
		theta += 2*Math.PI / 360 * degrees * (CW ? -1 : 1);
		p.x = radius * Math.sin(theta);
		p.z = radius * Math.cos(theta);
	}
	
	public static void rotateAxisZ(MyPoint p, boolean CW, double degrees) {
		double radius = Math.sqrt(Math.pow(p.y, 2) + Math.pow(p.x, 2));
		double theta = Math.atan2(p.y, p.x);
		theta += 2*Math.PI / 360 * degrees * (CW ? -1 : 1);
		p.y = radius * Math.sin(theta);
		p.x = radius * Math.cos(theta);
	}

	public static void zoomIn() {
		scale *= ZoomFactor;
	}

	public static void zoomOut() {
		scale /= ZoomFactor;
	}

}
