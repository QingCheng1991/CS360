package view;

import javax.swing.JButton;

import org.jxmapviewer.viewer.GeoPosition;

public class MapSiteItem {
	private GeoPosition gpsLocation;
	private GCollectionSite gSite;
	private SwingWaypoint swingWaypoint;
	private MapPanel mapPanel;
	public MapSiteItem(MapPanel panel, GCollectionSite gSiteParam) {
		gSite = gSiteParam;
		mapPanel = panel;
		gpsLocation = new GeoPosition(gSite.getSiteLatitude(), gSite.getSiteLongtitude());
		swingWaypoint = new SwingWaypoint(this, "",gpsLocation);
	}

	public GCollectionSite getgSite() {
		return gSite;
	}

	public void setgSite(GCollectionSite gSite) {
		this.gSite = gSite;
		gpsLocation = new GeoPosition(gSite.getSiteLatitude(), gSite.getSiteLongtitude());
		swingWaypoint = new SwingWaypoint(this, "", gpsLocation);
	}

	public SwingWaypoint getSwingWaypoint() {
		return swingWaypoint;
	}
	
	public JButton getButton() {
		return swingWaypoint.getButton();
	}
	
	public GeoPosition getGpsLocation() {
		return gpsLocation;
	}

	public boolean equals(Object e) {
		if (e instanceof MapSiteItem) {
			MapSiteItem temp = (MapSiteItem) e;
			if (this.gSite.equals(temp.gSite)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * this method is called when a pin receives a MouseEntered event
	 */
	public void activate() {
		mapPanel.notifyGUIManagerOnSiteItemActivation(this.gSite);
	}
	
	/**
	 * this method is called when a pin receives a MouseClick event
	 */
	public void click() {
		mapPanel.notifyGUIManagerOnSiteItemSelection(this.gSite);
	}
}
