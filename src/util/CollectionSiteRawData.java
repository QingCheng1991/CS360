package util;

import java.util.Date;

import model.CollectionSiteModel;

public class CollectionSiteRawData extends CollectionSiteModel{

	public CollectionSiteRawData(int number, String name, String description, float latitude, float longtitude,
			Date date) {
		super(number, name, description, latitude, longtitude, date);
	}
	public CollectionSiteRawData(int number, String name, String description, float latitude, float longtitude,
			Date date, String personName) {
		super(number, name, description, latitude, longtitude, date, personName);
		//System.out.println("this constructor called.");
	}

/*public int siteNumber;
public String siteName;
public String siteDecription;
public float siteLatitude;
public float siteLongtitude;
public int LastCollectedSampleDate;*/
}
