package model;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Observable;

import util.CollectionSiteRawData;

public class CollectionSiteModel extends Observable implements Serializable{
	private int siteNumber;
	private String siteName;
	private String siteDescription;
	private float siteLatitude;
	private float siteLongtitude;
	private Date LastCollectedSampleDate;
	private String personName;


	public CollectionSiteModel(int number, String name, String description, float latitude, float longtitude, java.util.Date date) {
		siteNumber = number;
		siteName = name;
		siteDescription = description;
		siteLatitude = latitude;
		siteLongtitude = longtitude;
		if (date!= null)
			LastCollectedSampleDate = (Date)date.clone();
		else LastCollectedSampleDate = null;
		
	}
	public CollectionSiteModel(CollectionSiteRawData data) {
		siteNumber = data.getSiteNumber();
		siteName = data.getSiteName();
		siteDescription = data.getSiteDescription();
		siteLatitude = data.getSiteLatitude();
		siteLongtitude = data.getSiteLongtitude();
		if (data.getLastCollectedSampleDate() != null)
			LastCollectedSampleDate = (Date) data.getLastCollectedSampleDate().clone();
		else LastCollectedSampleDate = null;
		personName = data.getPersonName();
	}
	
	public CollectionSiteModel(int number, String name, String description, float latitude, float longtitude, java.util.Date date, String personNameParam) {
		siteNumber = number;
		siteName = name;
		siteDescription = description;
		siteLatitude = latitude;
		siteLongtitude = longtitude;
		if (date != null) LastCollectedSampleDate = (Date)date.clone();
		else LastCollectedSampleDate = null;
		personName = personNameParam;
	}
	public int getSiteNumber() {
		return siteNumber;
	}
	public String getSiteName() {
		return siteName;
	}
	public String getSiteDescription() {
		return siteDescription;
	}
	public float getSiteLatitude() {
		return siteLatitude;
	}
	public float getSiteLongtitude() {
		return siteLongtitude;
	}
	public Date getLastCollectedSampleDate() {
		return LastCollectedSampleDate;
	}
	
	public String getPersonName() {
		return personName;
	}
	public boolean equals(Object x) {
		if(x instanceof CollectionSiteModel)
		{
			CollectionSiteModel temp = (CollectionSiteModel)x;
			if(temp.siteNumber == this.siteNumber ) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	
	void setSiteNumber(int number) {
		siteNumber = number;
	}
	void setSiteName(String name) {
		siteName = name;
	}
	void setSiteDescription(String description) {
		siteDescription = description;
	}
	void setSiteLatitude(float latitude) {
		siteLatitude = latitude;
	}
	void setSiteLongtitude(float longtitude) {
		siteLongtitude = longtitude;
	}
	void setLastCollectedSampleDate(Date date) {
		LastCollectedSampleDate = (Date)date.clone();
	}
	
}
