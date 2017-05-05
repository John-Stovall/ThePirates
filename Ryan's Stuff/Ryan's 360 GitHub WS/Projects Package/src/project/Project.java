package project;
import java.io.Serializable;

/**
 * @author Ryan Hansen
 * 
 * Interface which establishes the getters and setters
 * for the project classes.
 */
public interface Project extends Serializable {
	
	public void setName(String theName);
	public String getName();
	
	public void setIntialCost(double theInitialCost);
	public double getIntialCost();
	
	public void setMonthlyCost(double theMonthlyCost);
	public double getMonthlyCost();
	
	public void setQuantity(int theQuantity);
	public int getQuantity();
	
	public void setPricePerUnit(double thePricePerUnit);
	public double getPricePerUnit();
	
	public void setElectricityUsage(double theElectricityUsage);
	public double getElectricityUsage();
	
	public void setGasUsage(double theGasUsage);
	public double getGasUsage();
	
	public void setWaterUsage(double theWaterUsage);
	public double getWaterUsage();
}
