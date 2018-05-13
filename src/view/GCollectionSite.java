package view;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import model.CollectionSiteModel;

public class GCollectionSite{
	private GUIManager guiManager;
	private CollectionSiteModel siteModel;
	public GCollectionSite(CollectionSiteModel collectionSiteModel) {
		siteModel = collectionSiteModel;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof GCollectionSite) {
			GCollectionSite temp = (GCollectionSite) obj;
			return temp.siteModel.equals(this.siteModel);
		}
		return false;
	}

	public int getSiteNumber() {
		return siteModel.getSiteNumber();
	}
	public String getSiteName() {
		return siteModel.getSiteName();
	}
	public String getSiteDescription() {
		return siteModel.getSiteDescription();
	}
	public float getSiteLatitude() {
		return siteModel.getSiteLatitude();
	}
	public float getSiteLongtitude() {
		return siteModel.getSiteLongtitude();
	}
	public Date getLastCollectedSampleDate() {
		return siteModel.getLastCollectedSampleDate();
	}
	
	public String getPersonName() {
		return siteModel.getPersonName();
	}
}
