package util;

public class GUIInternalNotification extends OperationResult {
	public static final int GIVE_ATTENTION = 100;

	public GUIInternalNotification(int operationResultCodeParam, String descriptionParam, int operationParam,
			Object objParam) {
		super(operationResultCodeParam, descriptionParam, operationParam, objParam);
		
	}
	
	public boolean isGiveAttentionOperation() {
		return (operation == GIVE_ATTENTION);
	}
}
