package application.helpers;

public class ServiceResult 
{
	private boolean IsValid = true;
	private String Message = "";
	
	public void AddError(String error)
	{
		this.IsValid = false;
		this.Message += error + "\n";
	}
	
	public boolean GetValidationResult()
	{
		return this.IsValid;
	}
	
	public String GetMessage()
	{
		return this.Message;
	}
}
