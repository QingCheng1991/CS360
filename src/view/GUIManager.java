package view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import controller.Controller;
import model.CollectionSitesList;
import util.CollectionSiteRawData;
import util.GUIInternalNotification;
import util.GUIOperationResult;
import util.ModelOperationResult;
import util.OperationResult;

public class GUIManager extends Observable implements Observer{
	private GCollectionSiteList gSiteList;
	private static Logger logger = Logger.getLogger("view.GUIManager");
	private static FileHandler fh = null;
	
	public GUIManager(Controller controllerParam) {
		gSiteList = new GCollectionSiteList(this,controllerParam);
		if (false)
			try {
				fh = new FileHandler("guiManager.txt");
				logger.addHandler(fh);
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.SEVERE);
	}
	
	public void openAddSiteDialog() {
		setChanged();
		notifyObservers(new GUIOpenDialogOperation(GUIOpenDialogOperation.OPEN_ADD_DIALOG, null));
	}
	
	public void openEditSiteDialog(GCollectionSite gSite) {
		if (gSite == null) return;
		setChanged();
		notifyObservers(new GUIOpenDialogOperation(GUIOpenDialogOperation.OPEN_EDIT_DIALOG, gSite));
	}
	
	public void openViewSiteDialog(GCollectionSite gSite) {
		setChanged();
		notifyObservers(new GUIOpenDialogOperation(GUIOpenDialogOperation.OPEN_VIEW_DIALOG, gSite));
	}
	
	public void addSite(CollectionSiteRawData siteData) {
		gSiteList.addSite(siteData);
	}
	
	public void removeSite(CollectionSiteRawData siteData) {
		gSiteList.removeSite(siteData);
	}
	
	public void editSite(CollectionSiteRawData siteData) {
		gSiteList.editSite(siteData);
	}
	
	public void selectSite(GCollectionSite gSite) {
		setChanged();
		notifyObservers(new GUIOpenDialogOperation(GUIOpenDialogOperation.OPEN_VIEW_DIALOG, gSite));
	}
	
	public void giveGSiteAttention(GCollectionSite gSite) {
		setChanged();
		notifyObservers(new GUIInternalNotification(GUIInternalNotification.SUCCESS,
				"Give site attention",GUIInternalNotification.GIVE_ATTENTION,gSite));
	}
	
	void addTestSite(GCollectionSite gSite){
		CollectionSiteRawData rawData = new CollectionSiteRawData(gSite.getSiteNumber(),
				gSite.getSiteName(), gSite.getSiteDescription(), gSite.getSiteLatitude(), gSite.getSiteLongtitude(), gSite.getLastCollectedSampleDate());
		addSite(rawData);
	}
	
	void saveDataOnGUIExit() {
		gSiteList.saveDataOnGUIExit();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof CollectionSitesList && arg1 instanceof ModelOperationResult) {
			ModelOperationResult modelOpResult = (ModelOperationResult) arg1;
			if (modelOpResult.isSaveOperation()) {
				if (modelOpResult.isSuccess()) {
					logger.info("received Model operation SAVE result SUCCESS = " + modelOpResult.isSuccess() + ", FAILURE = " + modelOpResult.isFailure());
					System.exit(0);
				} else {
					int result = JOptionPane.showConfirmDialog(null, "unable to save data. Proceeding will exit the program without saving data. Proceed to exitting the program ?","Fail to save data", JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}
			} else if (modelOpResult.isLoadOperation()) {
				logger.info("received Model operation LOAD result SUCCESS = " + modelOpResult.isSuccess() + ", FAILURE = " + modelOpResult.isFailure());
				if (modelOpResult.isFailure()) {
					JOptionPane.showMessageDialog(null, "Fail to load data.");
				}
			} else {
			GCollectionSite gsite = gSiteList.update(modelOpResult);
			GUIOperationResult guiOpResult = new GUIOperationResult(modelOpResult,gsite);
			logger.info("received Model operation result SUCCESS = " + modelOpResult.isSuccess() + ", FAILURE = " + modelOpResult.isFailure());
			setChanged();
			notifyObservers(guiOpResult);
			}
		}
	}

	public void importDataFromFile(String fileName) {
		gSiteList.importDataFromFile(fileName);
		
	}
}
