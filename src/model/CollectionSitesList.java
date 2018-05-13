package model;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.CollectionSiteRawData;
import util.ModelOperationResult;

public class CollectionSitesList extends Observable implements Serializable{
	
private ArrayList<CollectionSiteModel>list;
private String dataFilePath;
private static Logger logger = Logger.getLogger("model.CollectionSitesList");
private static FileHandler fh = null;

public CollectionSitesList(String path) {
	dataFilePath = path;
	list = new ArrayList<CollectionSiteModel>();
	initializeLoggingFile();
}
public CollectionSitesList() {
	dataFilePath = "CollectionData.txt";
	list = new ArrayList<CollectionSiteModel>();
	initializeLoggingFile();
}

private void initializeLoggingFile() {
	if (false)
		try {
			fh = new FileHandler("CollectionSitesList.txt");
			logger.addHandler(fh);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	logger.setUseParentHandlers(false);
	logger.setLevel(Level.SEVERE);
}
public int add(CollectionSiteRawData data) 
{
	CollectionSiteModel newinfo = new CollectionSiteModel(data);
	for(int i=0; i<list.size();i++) 
	{
		if(newinfo.getSiteNumber() == list.get(i).getSiteNumber()) 
		{
			ModelOperationResult results = new ModelOperationResult(ModelOperationResult.FAILURE,"The SiteNumber is already exist!",ModelOperationResult.ADD,null);
			setChanged();
			notifyObservers(results);
			return -1;
		
		
		}
	}
	logger.info("add site successfully");
	list.add(newinfo);
	ModelOperationResult results = new ModelOperationResult(ModelOperationResult.SUCCESS,"The Site Information has been successfult added into list!",ModelOperationResult.ADD,newinfo);
	setChanged();
	notifyObservers(results);
	return 0;
}

public void remove(CollectionSiteRawData data) 
{
	CollectionSiteModel newinfo = new CollectionSiteModel(data);
	for(int i=0; i<list.size(); i++) 
	{
		if(newinfo.equals(list.get(i)) ) 
		{
			list.remove(i);
			ModelOperationResult results = new ModelOperationResult(ModelOperationResult.SUCCESS,"The Site Information has been remove from the list!",ModelOperationResult.REMOVE,newinfo);
			setChanged();
			notifyObservers(results);
			return;
		}
	}
	ModelOperationResult results = new ModelOperationResult(ModelOperationResult.FAILURE,"The Site Infomation does not exist!",ModelOperationResult.REMOVE,newinfo);
	setChanged();
	notifyObservers(results);
	
	
	
}
public void edit(CollectionSiteRawData data) 
{
	CollectionSiteModel newinfo = new CollectionSiteModel(data);
	for(int i=0; i<list.size(); i++) 
	{
		if(newinfo.equals(list.get(i))) 
		{
			list.get(i).setSiteName(data.getSiteName());
			list.get(i).setSiteDescription(data.getSiteDescription());
			list.get(i).setSiteLatitude(data.getSiteLatitude());
			list.get(i).setSiteLongtitude(data.getSiteLongtitude());
			list.get(i).setLastCollectedSampleDate(data.getLastCollectedSampleDate());
			ModelOperationResult results = new ModelOperationResult(ModelOperationResult.SUCCESS,"The Site Infomation has been updated!",ModelOperationResult.EDIT,newinfo);
			setChanged();
			notifyObservers(results);
			return;
		}
		
	}
	ModelOperationResult results = new ModelOperationResult(ModelOperationResult.FAILURE,"Failure to update the Site Infomation!",ModelOperationResult.EDIT,newinfo);
	setChanged();
	notifyObservers(results);
	
	
	
}
public ArrayList<CollectionSiteModel> getCollectionSiteList(){
	ArrayList<CollectionSiteModel> displayList = new ArrayList<CollectionSiteModel>(list);
	
	
	return displayList;
}
public int saveData() 
{
	try {
		FileOutputStream file = new FileOutputStream(new File("listData.bin"));
		ObjectOutputStream obj = new ObjectOutputStream(file);

		// Write objects to file
		
		CollectionSitesListDiskObject diskObj = new CollectionSitesListDiskObject();
		diskObj.arr = list;
		obj.writeObject(diskObj);

		file.close();
		obj.close();
		ModelOperationResult results = new ModelOperationResult(
				ModelOperationResult.SUCCESS,"Save data successfully",
				ModelOperationResult.SAVE,null);
		setChanged();
		notifyObservers(results);
		return 1;

	}catch (FileNotFoundException e) 
	{
		/*ModelOperationResult results = new ModelOperationResult(
				ModelOperationResult.FAILURE,"Saving failed. File not found.",
				ModelOperationResult.SAVE,null);
		setChanged();
		notifyObservers(results);*/
		return 0;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;
	}
}
public int loadData(String dataFilePath) 
{
	try {
		FileInputStream file = new FileInputStream(new File(dataFilePath));
		ObjectInputStream obj = new ObjectInputStream(file);

		// Read objects
		CollectionSitesListDiskObject diskObj = (CollectionSitesListDiskObject) obj.readObject();
	    for (CollectionSiteModel e : diskObj.arr) {
	    	 add(new CollectionSiteRawData(e.getSiteNumber(), e.getSiteName(), e.getSiteDescription(), e.getSiteLatitude(), e.getSiteLongtitude(), e.getLastCollectedSampleDate(),e.getPersonName()));
	    }

		file.close();
		obj.close();
		ModelOperationResult results = new ModelOperationResult(
				ModelOperationResult.SUCCESS,"Load data succesfully",
				ModelOperationResult.LOAD,null);
		setChanged();
		notifyObservers(results);
		return 1;
	} catch (FileNotFoundException e) {
		/*ModelOperationResult results = new ModelOperationResult(
				ModelOperationResult.FAILURE,"Failure to load data. File not found.",
				ModelOperationResult.LOAD,null);
		setChanged();
		notifyObservers(results);*/
		return 0;
	} catch (IOException e) {
		ModelOperationResult results = new ModelOperationResult(
				ModelOperationResult.FAILURE,"Failure to load data. IO Error.",
				ModelOperationResult.LOAD,null);
		setChanged();
		notifyObservers(results);
		e.printStackTrace();
		return 0;
	} catch (ClassNotFoundException e) {
		ModelOperationResult results = new ModelOperationResult(
				ModelOperationResult.FAILURE,"Failure to load data. Internal Error",
				ModelOperationResult.LOAD,null);
		setChanged();
		notifyObservers(results);
		e.printStackTrace();
	}
	return 0;
}
@Override
public boolean equals(Object e) {
	if (e instanceof CollectionSitesList ) {
		CollectionSitesList a = (CollectionSitesList) e;
		if (this.list.size() != a.list.size()) return false;
		for (int i = 0; i < a.list.size(); i++) {
			if (!this.list.get(i).equals(a.list.get(i)))
					return false;
		}
		return true;
	}
	else return false;
}


public void importData(String importFilePath)  {
	int valid=0;
    int invalid =0;
    int void_title =0;
	if(importFilePath.endsWith(".csv")) 
	{
	
		
	
	   CollectionSitesList temp = new CollectionSitesList();
       String line = "";
       String cvsSplitBy = ",";
       

       try (BufferedReader br = new BufferedReader(new FileReader(importFilePath))) {
    	   	 try (BufferedWriter file = new BufferedWriter(new FileWriter("import_log_report.txt"))) 
    	   	 {
          while ((line = br.readLine()) != null) 
           {
        	      if(void_title != 0) {
        	    	  
        	      
               // use comma as separator
               String[] data = line.split(cvsSplitBy);
               

               if(checkValidation(data,file)) 
               {
            	   //valid

               CollectionSiteRawData rawData = new CollectionSiteRawData(Integer.parseInt(data[0]), data[4], data[3], Float.valueOf(data[2]), Float.valueOf(data[1]),null);

               int result = add(rawData);
               if (result <0) {
            	   invalid++;
            	   file.write(line);
					file.newLine();
					file.write("Error: Site number " + rawData.getSiteNumber() + " may already exist in the database.");
					file.newLine();
               }
               else valid++;
               }
               else 
               {
            	   	//invalid
            	   invalid++;
            	   }
        	      }
        	      else 
        	      {
        	    	  //void the Title
        	      }
               
               
           
        	   void_title++;
           }
       //write log report
       for (CollectionSiteModel e : temp.list) {
    	   //add(new CollectionSiteRawData(e.getSiteNumber(), e.getSiteName(), e.getSiteDescription(), e.getSiteLatitude(), e.getSiteLongtitude(), e.getLastCollectedSampleDate()));
       }
       file.write("The total amount of valid infomation:"+valid);
       file.newLine();
       file.write("The total amount of invalid infomation:"+invalid);
       
       }catch (IOException e) {
    	   		//log report invalid
			e.printStackTrace();

		}
    	   }catch (IOException e) {
    		   //import file invalid
			e.printStackTrace();

		}
   
	}
	else 
	{
	//can find .csv file
	return;
	}
	
	

	
	

	
	
	
	
	
	
}

int _checkValidation(String[] input) {
	if(input.length < 5) return -100;
	if (!(input[0] != null && input[0].matches("\\d*\\.?\\d+\\w"))) return -1;
	if (!(input[1] != null && input[1].trim().matches("[-+]?[0-9][0-9]\\.[0-9]*\\w"))) return -2;
	if (!(input[2] != null && input[2].trim().matches("[-+]?[0-9][0-9]\\.[0-9]*\\w"))) return -3;
	if (!(input[3] != null && input[3].trim().length() > 0)) return -4;
	if (!(input[4] != null && input[3].trim().length() > 0)) return -5;
	return 0;
}
boolean checkValidation(String [] input,BufferedWriter file) 
{
	
	String errorMessage = "";
	int validationResult = _checkValidation(input);
	switch (validationResult) {
	case -100: errorMessage = "Not enough arguments. There must be at least 5.";
				break;
	case -1: errorMessage = "Invalid site number";
			break;
	case -2: errorMessage = "Invalid site latitude";
		break;
	case -3:errorMessage = "Invalid site longtitude";
		break;
	case -4:errorMessage = "Invalid site description";
		break;
	case -5:errorMessage = "Invalid site name";
		break;
	}
	if (validationResult == 0 )
	{
		for(int i=0; i<list.size();i++) 
		{
				if(input[0] == Integer.toString(list.get(i).getSiteNumber())) 
				{
					try {
					file.write(input[0]+input[1]+input[2]+input[3]+input[4]);
					file.newLine();
					file.write("siteNumber ready exists in the database(siteNumber conflict)");
					file.newLine();
					}
					catch (IOException e) {
	
						e.printStackTrace();
	
					}
					
				}
			}
		return true;
	}
	else 
	{
		try {
		file.write(String.join(",", input));
		file.newLine();
		file.write("The reason for this is invalid data might be the variable siteNumber(integer)/Latitude(float)/Longtitude(float) is incorrect type\n");
		file.write(errorMessage);
		file.newLine();
		
		return false;
	}
	catch (IOException e) {

		e.printStackTrace();

	}
	}
	
	return false;
}



	
}

