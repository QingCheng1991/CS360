package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import java.awt.Insets;
import java.awt.geom.Point2D;

import util.GUIOperationResult;


public class MapPanel extends JPanel implements Observer{
	private ArrayList<MapSiteItem> gSiteList;
	private GUIManager guiManager;
	private JXMapViewer mapViewer;
	Set<SwingWaypoint> waypoints ;
	WaypointPainter<SwingWaypoint> swingWaypointPainter;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	
	public MapPanel(GUIManager guiManagerParam) {
		setLayout(new BorderLayout(0, 0));
		
		gSiteList = new ArrayList<MapSiteItem>();
		guiManager = guiManagerParam;
		//this.setPreferredSize(new Dimension(500, 500));
		mapViewer = new JXMapViewer();
		//mapViewer.setPreferredSize(new Dimension(400,400));
        
        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
     
        // Use 8 threads in parallel to load the tiles
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		tileFactory.setThreadPoolSize(2);
		mapViewer.setTileFactory(tileFactory);
		
		
		GeoPosition fortwayne = new GeoPosition(41.10, -85.12);
		GeoPosition fort2 = new GeoPosition(40.10, -85.12);
		
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		
		waypoints = new HashSet<SwingWaypoint>(Arrays.asList(
	                //new SwingWaypoint(null,"Fort Wayne", fortwayne)));
				));
		swingWaypointPainter = new SwingWaypointOverlayPainter();
		
		swingWaypointPainter.setWaypoints(waypoints);
		mapViewer.setZoom(9);
		mapViewer.setAddressLocation(fortwayne);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
		mapViewer.setOverlayPainter(swingWaypointPainter);

		// Add the JButtons to the map viewer
		for (SwingWaypoint w : waypoints) {
			mapViewer.add(w.getButton());
		}

		mapViewer.setLayout(new GridBagLayout());
		
		zoomInButton = new JButton("+");
		zoomInButton.setFont(new Font("Tahoma",Font.PLAIN, 18));
		zoomInButton.setPreferredSize(new Dimension(35,35));
		zoomInButton.setMargin(new Insets(0,0,0,0));
		zoomOutButton = new JButton("\u2015");
		zoomOutButton.setFont(new Font("Tahoma",Font.PLAIN, 18));
		zoomOutButton.setPreferredSize(new Dimension(35,35));
		zoomOutButton.setMargin(new Insets(0,0,0,0));
		
		JPanel sideBar = new JPanel();
		sideBar.setLayout(new GridBagLayout());
		sideBar.setOpaque(false);
		
		java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(0,0,10,0);
		sideBar.add(zoomInButton, gridBagConstraints);
		
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(10,0,0,0);
		sideBar.add(zoomOutButton, gridBagConstraints);
		
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		//mapViewer.add(sideBar, gridBagConstraints);
		
		//mapViewer.add(sideBar, BorderLayout.WEST);
		this.add(mapViewer);
	}
	
	public void removeSite(GCollectionSite gSite) {
		MapSiteItem temp = new MapSiteItem(this,gSite);
		for (int i = 0; i < gSiteList.size(); i++) {
			if (gSiteList.get(i).equals(temp)) {
				mapViewer.remove(gSiteList.get(i).getButton());
				waypoints.remove(gSiteList.get(i).getSwingWaypoint());
				swingWaypointPainter.setWaypoints(waypoints);
				gSiteList.remove(i);
				mapViewer.revalidate();
				mapViewer.repaint();
				break;
			}
		}
	}
	
	public void addSiteDebug(GCollectionSite gSite) {
		MapSiteItem newItem = new MapSiteItem(this,gSite);
		gSiteList.add(newItem);
		/*for (SwingWaypoint w : waypoints) {
			mapViewer.remove(w.getButton());
		}*/
		boolean result = waypoints.add(newItem.getSwingWaypoint());
		swingWaypointPainter.setWaypoints(waypoints);
		
		Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(new DefaultWaypoint(newItem.getGpsLocation())));
		WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
		waypointPainter.setRenderer(new MyWaypointRenderer());
		waypointPainter.setWaypoints(waypoints);
		
		mapViewer.setOverlayPainter(waypointPainter);
		/*for (Waypoint w : waypoints) {
			mapViewer.add(w.getButton());
		}*/
		//mapViewer.add(newItem.getButton());
		Point2D point = mapViewer.getTileFactory().geoToPixel(newItem.getSwingWaypoint().getPosition(), mapViewer.getZoom());
		JButton abtn = new JButton("mybutton");
		mapViewer.add(abtn);
		abtn.setLocation((int)point.getX(), (int)point.getY());
		mapViewer.repaint();
	}
	
	public void addSite(GCollectionSite gSite) {
		MapSiteItem newItem = new MapSiteItem(this,gSite);
		gSiteList.add(newItem);
		boolean result = waypoints.add(newItem.getSwingWaypoint());
		swingWaypointPainter.setWaypoints(waypoints);
		mapViewer.add(newItem.getButton());
		mapViewer.revalidate();
		mapViewer.repaint();
		
	}
	
	public void editSite(GCollectionSite gSite) {
		MapSiteItem temp = new MapSiteItem(this,gSite);
		for (MapSiteItem i : gSiteList) {
			if (i.equals(temp)) {
				//remove the button from the map, and waypoint from the set
				mapViewer.remove(i.getButton());
				boolean result = waypoints.remove(i.getSwingWaypoint());
				
				//swingWaypointPainter.setWaypoints(waypoints);
				//mapViewer.repaint();
				i.setgSite(gSite);
				
				//add that item to the map and to the set
				result = waypoints.add(i.getSwingWaypoint());
				mapViewer.add(i.getButton());
				
				//update the painter and the mapviewer
				swingWaypointPainter.setWaypoints(waypoints);
				mapViewer.revalidate();
				mapViewer.repaint();
				break;
			}
		}
	}
	
	public void selectSite(GCollectionSite gSite) {
		guiManager.selectSite(gSite);
	}

	/*private int getIndexOf(GCollectionSite gSite) {
		for (int i = 0; i < gSiteList.size(); i++) {
			if ( gSiteList.get(i).getSiteNumber() == gSite.getSiteNumber()) {
				return i;
			}
		}
		return -1;
	}*/
	
	public void notifyGUIManagerOnSiteItemActivation(GCollectionSite gSite) {
		guiManager.giveGSiteAttention(gSite);
		
	}
	
	public void notifyGUIManagerOnSiteItemSelection(GCollectionSite gSite) {
		guiManager.selectSite(gSite);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		//logger.info("received update method call from " + arg0.getClass().toString());
		if (arg0 instanceof GUIManager && arg1 instanceof GUIOperationResult) {
			GUIOperationResult result = (GUIOperationResult)arg1;
			//logger.info("the argument is of type GUIOperationResult, ADD = " + result.isAddOperation() +
						//", EDIT = " + result.isEditOperation() + "SUCCESS = " + result.isSuccess());
			if (result.isSuccess()) {
				switch (result.getOperation()) {
				case GUIOperationResult.ADD: 
						addSite(result.getGCollectionSite());
						break;
				case GUIOperationResult.EDIT:
						editSite(result.getGCollectionSite());
						break;
				case GUIOperationResult.REMOVE:
						removeSite(result.getGCollectionSite());
						break;
				}
			}
				
		}
		
	}

	
}
