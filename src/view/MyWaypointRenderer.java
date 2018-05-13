package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypointRenderer;
import org.jxmapviewer.viewer.Waypoint;

public class MyWaypointRenderer extends DefaultWaypointRenderer{
	private BufferedImage img = null;
	private JButton button = new JButton("hey");

	/**
	 * Uses a default waypoint image
	 */
	public MyWaypointRenderer()
	{
		try
		{
			img = ImageIO.read(new File("pin.png"));
		}
		catch (Exception ex)
		{
			System.out.println("failed");
			//log.warn("couldn't read standard_waypoint.png", ex);
		}
	}
	
	@Override
	public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint w)
	{
		if (img == null)
			return;

		Point2D point = map.getTileFactory().geoToPixel(w.getPosition(), map.getZoom());
		
		int x = (int)point.getX() -img.getWidth() / 2;
		int y = (int)point.getY() -img.getHeight();
		
		g.drawImage(img, x, y, null);
		if (!map.isAncestorOf(button)) {
			map.add(button);
		}
		button.setLocation(x,y);
		button.repaint();
		//map.revalidate();
	}
}
