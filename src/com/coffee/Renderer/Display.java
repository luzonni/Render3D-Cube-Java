package com.coffee.Renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.coffee.Renderer.input.ClickType;
import com.coffee.Renderer.input.Mouse;
import com.coffee.Renderer.shapes.MyPolygon;
import com.coffee.Renderer.shapes.Tetrahedron;
import com.coffee.Vectors.MyPoint;
import com.coffee.Vectors.PointConverter;

public class Display extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private JFrame frame;
	private static String title = "3D Render";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private static boolean running = false;
	private static final double Hz = 60.0;
	
	private Tetrahedron tetra;
	
	private Mouse mouse;
	
	public Display() {
		this.frame = new JFrame();
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
		this.mouse = new Mouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addMouseWheelListener(mouse);
	}
	
	public void start() {
		running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}
	
	public void stop() {
		running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ClickType prevMouse = ClickType.Unknown;
	int initialX, initialY;
	public void tick() {
		int x = this.mouse.getMouseX();
		int y = this.mouse.getMouseY();
		double mouseSensitive = 4.5;
		if(this.mouse.getButton() == ClickType.LeftClick) {
			
			int xDif = x - initialX;
			int yDif = y - initialY;

			this.tetra.rotate(true, 0, -yDif/mouseSensitive, -xDif/mouseSensitive);
		}else if(this.mouse.getButton() == ClickType.RigthClick) {
			
			int xDif = x - initialX;

			this.tetra.rotate(true, -xDif/mouseSensitive, 0, 0);
		}
		
		if(this.mouse.isScroolUp()) {
			PointConverter.zoomIn();
		}else if(this.mouse.isScroolDown()) {
			PointConverter.zoomOut();
		}
		this.mouse.reserScrool();
		initialX = x;
		initialY = y;
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		tetra.render(g);
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / Hz;
		double delta = 0;
		int hz = 0;
		int frames = 0;
		
		init();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				hz++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + frames + " fps " + hz + " Hz");
				frames = 0;
				hz = 0;
			}
		}
		stop();
	}
	
	private void init() {
		int size = 30;
		MyPoint p1 = new MyPoint(size/2, -size/2, -size/2);
		MyPoint p2 = new MyPoint(size/2, size/2, -size/2);
		MyPoint p3 = new MyPoint(size/2, size/2, size/2);
		MyPoint p4 = new MyPoint(size/2, -size/2, size/2);
		MyPoint p5 = new MyPoint(-size/2, -size/2, -size/2);
		MyPoint p6 = new MyPoint(-size/2, size/2, -size/2);
		MyPoint p7 = new MyPoint(-size/2, size/2, size/2);
		MyPoint p8 = new MyPoint(-size/2, -size/2, size/2);
		
		this.tetra = new Tetrahedron(
				new MyPolygon(Color.RED, p1, p2, p3, p4),
				new MyPolygon(Color.BLUE, p5, p6, p7, p8),
				new MyPolygon(Color.CYAN, p1, p2, p6, p5),
				new MyPolygon(Color.YELLOW, p1, p5, p8, p4),
				new MyPolygon(Color.GREEN, p2, p6, p7, p3),
				new MyPolygon(Color.PINK, p4, p3, p7, p8)
				);
	}
	
	public static void main(String[] agrs) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setLocationRelativeTo(null);
		display.frame.setResizable(false);
		display.frame.setVisible(true);
		
		display.start();
	}

}
