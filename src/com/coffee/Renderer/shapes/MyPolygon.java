package com.coffee.Renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.coffee.Vectors.MyPoint;
import com.coffee.Vectors.PointConverter;

public class MyPolygon {
	
	private MyPoint[] points;
	private Color color;
	
	public MyPolygon(MyPoint... points) {
		this.color = Color.WHITE;
		this.points = new MyPoint[points.length];
		for(int i = 0; i < points.length; i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x, p.y, p.z);
		}
	}
	
	public MyPolygon(Color color, MyPoint... points) {
		this.color = color;
		this.points = new MyPoint[points.length];
		for(int i = 0; i < points.length; i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x, p.y, p.z);
		}
	}
	
	public void render(Graphics g) {
		Polygon poly = new Polygon();
		for(int i = 0; i < this.points.length; i++) {
			Point p = PointConverter.converterPoint(this.points[i]);
			poly.addPoint(p.x, p.y);
			
		}
		g.setColor(this.color);
		g.fillPolygon(poly);
	}
	
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(MyPoint p : this.points) {
			PointConverter.rotateAxisX(p, CW, xDegrees);
			PointConverter.rotateAxisY(p, CW, yDegrees);
			PointConverter.rotateAxisZ(p, CW, zDegrees);
		}
	}
	
	public double getAverageX() {
		double sum = 0;
		for(MyPoint p : this.points) {
			sum += p.x;
		}
		return sum / this.points.length;
	}

	public void setColor(Color color2) {
		this.color = color2;
	}
	
	public static MyPolygon[] sortPoligons(MyPolygon[] polygons) {
		List<MyPolygon> polygonsList = new ArrayList<MyPolygon>();
		for(MyPolygon poly : polygons) {
			polygonsList.add(poly);
		}
		polygonsList.sort(new Comparator<MyPolygon>() {

			@Override
			public int compare(MyPolygon o1, MyPolygon o2) {
				return o2.getAverageX() - o1.getAverageX() < 0 ? 1 : -1;
			}
			
		});
		for(int i = 0; i < polygons.length; i++) {
			polygons[i] = polygonsList.get(i);
		}
		return polygons;
	}

}
