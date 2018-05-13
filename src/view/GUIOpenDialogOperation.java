package view;

class GUIOpenDialogOperation {
	
	static final int OPEN_ADD_DIALOG = 0;
	static final int OPEN_EDIT_DIALOG = 1;
	static final int OPEN_VIEW_DIALOG = 2;
	
	private int operationCode;
	private GCollectionSite gSite;
	
	GUIOpenDialogOperation(int operationCodeParam, GCollectionSite gSiteParam) {
		operationCode = operationCodeParam;
		gSite = gSiteParam;
	}
	
	int getOperationCode(){
		return operationCode;
	}
	
	GCollectionSite getGSite() {
		return gSite;
	}
}
