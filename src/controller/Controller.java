package controller;

import model.CollectionSitesList;
import util.CollectionSiteRawData;

public class Controller {
	private CollectionSitesList collectionSiteList;
	
	public Controller(CollectionSitesList list) {
		 collectionSiteList = list;
	}
	
	public void addSite(CollectionSiteRawData data) {
		collectionSiteList.add(data);
	}
	
	public void removeSite(CollectionSiteRawData data) {
		collectionSiteList.remove(data);
	}
	
	public void editSite(CollectionSiteRawData data) {
		collectionSiteList.edit(data);
	}

	public void saveDataOnGUIExit() {
		collectionSiteList.saveData();
	}

	public void importDataFromFile(String fileName) {
		collectionSiteList.importData(fileName);
	}
}
