package util;

public class ModelOperationResult extends OperationResult{
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	public static final int ADD = 11;
	public static final int REMOVE = 12;
	public static final int EDIT = 13;
	public static final int SAVE = 14;
	public static final int LOAD = 15;
	
	//private int operationResult;	
	//protected int operation;
	//private String operationResultDescription;
	//private Object objdata;

	public ModelOperationResult( int operationResultCodeParam, String descriptionParam, int operationParam, Object objParam ) {
		super(operationResultCodeParam, descriptionParam, operationParam, objParam);
	}
	public boolean isSaveOperation() {
		return operation == SAVE;
	}
	
	public boolean isLoadOperation() {
		return operation == LOAD;
	}
}
