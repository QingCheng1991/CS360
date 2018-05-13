package util;

import view.GCollectionSite;

public class GUIOperationResult extends OperationResult{
	private GCollectionSite gSite;
	public static final int SELECT = 100;
	public GUIOperationResult(ModelOperationResult modelOpResult, GCollectionSite gSiteParam) {
		super(modelOpResult.getOperationResult(), modelOpResult.getOperationResultDescription(), modelOpResult.getOperation(), modelOpResult.getObjdata());
		gSite = gSiteParam;
	}
	public GCollectionSite getGCollectionSite() {
		return gSite;
	}

	public boolean isSelectOperation() {
		return (operation == SELECT);
	}
}
