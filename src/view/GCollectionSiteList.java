package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.Controller;
import model.CollectionSiteModel;
import util.CollectionSiteRawData;
import util.ModelOperationResult;

public class GCollectionSiteList{
	private List<GCollectionSite> listGSites;
	private Controller controller;
	private GUIManager guiManager;
	
	public GCollectionSiteList(GUIManager guiManagerParam, Controller controllerParam) {
		controller = controllerParam;
		listGSites = new ArrayList<GCollectionSite>();
		guiManager = guiManagerParam;
	}
	
	public void addSite(CollectionSiteRawData data) {
		controller.addSite(data);
	}

	public void removeSite(CollectionSiteRawData data) {
		controller.removeSite(data);
	}
	
	public void editSite(CollectionSiteRawData data) {
		controller.editSite(data);
	}
	
	public void saveDataOnGUIExit() {
		controller.saveDataOnGUIExit();
	}
	
	public GCollectionSite update(ModelOperationResult result) {
		GCollectionSite gSite = null;
		if (result.getObjdata() != null && result.getObjdata() instanceof CollectionSiteModel) {
			CollectionSiteModel model = (CollectionSiteModel) result.getObjdata();
			gSite = new GCollectionSite(model);
		} else return gSite;
		if (result.isAddOperation() && result.isSuccess()) {
			//assumption
			listGSites.add(gSite);
			return gSite;
		} else if (result.isRemoveOperation() && result.isSuccess()) {
			listGSites.remove(gSite);
			return gSite;
		} else if (result.isEditOperation() && result.isSuccess()) {
			return gSite;
		}
		return gSite;
	}

	public void importDataFromFile(String fileName) {
		controller.importDataFromFile(fileName);
		
	}
	
}
