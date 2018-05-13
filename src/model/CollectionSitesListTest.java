package model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import util.CollectionSiteRawData;

public class CollectionSitesListTest {

	@Test
	public void testAdd() {
		CollectionSitesList list = new CollectionSitesList();
		CollectionSitesList correctList = new CollectionSitesList();
		
		CollectionSiteRawData rawData = new CollectionSiteRawData(0, "name", "desc", 12, 23, new Date(23));
		CollectionSiteModel model = new CollectionSiteModel(rawData);
		list.add(rawData);
		correctList.add(rawData);
		assertEquals(list, correctList);
	}

	@Test
	public void testRemove() {
		CollectionSitesList list = new CollectionSitesList();
		CollectionSitesList correctList = new CollectionSitesList();
		CollectionSiteRawData rawData = new CollectionSiteRawData(0, "name", "desc", 12, 23, new Date(23));
		CollectionSiteRawData rawData1 = new CollectionSiteRawData(1, "name1", "desc1", 22, 24, new Date(25));
		CollectionSiteModel model = new CollectionSiteModel(rawData);
		list.add(rawData);
		list.add(rawData1);
		correctList.add(rawData);
		
		list.remove(rawData1);
		
		assertEquals(list, correctList);
	}
	

	@Test
	public void testEdit() {
		CollectionSitesList list = new CollectionSitesList();
		CollectionSitesList correctList = new CollectionSitesList();
		
		CollectionSiteRawData rawData = new CollectionSiteRawData(0, "name", "desc", 12, 23, new Date(23));
		CollectionSiteRawData CorrectrawData = new CollectionSiteRawData(0, "name2", "desc", 23, 23, new Date(23));
		CollectionSiteModel model = new CollectionSiteModel(CorrectrawData);
		list.add(rawData);
		
		correctList.add(CorrectrawData);
		
		list.edit(CorrectrawData);
		assertEquals(list, correctList);
		
	}

	
	public void testSaveData() {
		CollectionSitesList answer = new CollectionSitesList();
		CollectionSitesList correct = new CollectionSitesList();
		CollectionSiteRawData rawData = new CollectionSiteRawData(0, "name", "desc", 12, 23, new Date(23));
		
		
		
		try {
			FileOutputStream file = new FileOutputStream(new File("listData.bin"));
			ObjectOutputStream obj = new ObjectOutputStream(file);

			// Write objects to file
			correct.add(rawData);
			
			obj.writeObject(correct);
			

			file.close();
			obj.close();

		

		}catch (FileNotFoundException e) 
		{
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
		
		
		try {
			FileInputStream file = new FileInputStream(new File("listData.bin"));
			ObjectInputStream obj = new ObjectInputStream(file);

			// Read objects
			answer =  (CollectionSitesList) obj.readObject();
			
			
			
			file.close();
			obj.close();
			

		} catch (FileNotFoundException e) {
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(answer, correct);
		
	}
	//@Test
	public void testimportData() {
		
		CollectionSitesList answer = new CollectionSitesList();
		CollectionSitesList correct = new CollectionSitesList();
		
		CollectionSiteRawData rawData = new CollectionSiteRawData(100, "Cedar Creek", "Tonkel Road", (float)41.218589, (float)-85.076772, new Date(1));
		
		correct.add(rawData);
		
		answer.importData("sample-sites-oneline-correct.csv");
		assertEquals(answer, correct);
		
	}
	
	@Test
	public void testimportData2() {
		
		CollectionSitesList answer = new CollectionSitesList();
		CollectionSitesList correct = new CollectionSitesList();
		
		CollectionSiteRawData rawData = new CollectionSiteRawData(100, "Cedar Creek", "Tonkel Road", (float)41.218589, (float)-85.076772, new Date(1));
		
		correct.add(rawData);
		
		answer.importData("sample-sites-invalid.csv");
		assertEquals(answer, correct);
	}
	
	@Test
	public void testValidateInput() {
		CollectionSitesList answer = new CollectionSitesList();
		assertEquals(0,answer._checkValidation(new String[] {"100","-85.5","41.1", "Tonkel Road", "Cedar Creek"}));
	}
	@Test
	public void testValidateInput2() {
		CollectionSitesList answer = new CollectionSitesList();
		assertTrue( 0 > answer._checkValidation(new String[] {"100","abc","41.1", "Tonkel Road", "Cedar Creek"}));
	}
}
