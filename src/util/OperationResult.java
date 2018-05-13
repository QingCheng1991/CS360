package util;

public class OperationResult{
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	public static final int ADD = 11;
	public static final int REMOVE = 12;
	public static final int EDIT = 13;
	
	private int operationResult;	
	protected int operation;
	private String operationResultDescription;
	private Object objdata;

	public int getOperationResult() {
		return operationResult;
	}
	public int getOperation() {
		return operation;
	}
	public String getOperationResultDescription() {
		return operationResultDescription;
	}
	public Object getObjdata() {
		return objdata;
	}
	public OperationResult( int operationResultCodeParam, String descriptionParam, int operationParam, Object objParam ) {
		operationResult = operationResultCodeParam;
		operationResultDescription = descriptionParam;
		operation = operationParam;
		objdata = objParam;
	}
	public boolean isFailure() {
		return (operationResult == FAILURE);
	}

	public boolean isSuccess() {
		return (operationResult == SUCCESS);
	}

	public boolean isUndefined() {
		return (! (operationResult == SUCCESS || operationResult == FAILURE) );
	}

	public String getDescription() {
		return operationResultDescription;
	}

	public boolean isAddOperation() {
		return (operation == ADD);
	}
	
	public boolean isRemoveOperation() {
		return (operation == REMOVE);
	}
	
	public boolean isEditOperation() {
		return (operation == EDIT);
	}

	
}
