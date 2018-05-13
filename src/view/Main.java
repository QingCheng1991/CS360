package view;


import javax.swing.JFrame;

import controller.Controller;
import model.CollectionSitesList;

public class Main {
	public static void main(String[] args) {
	
		CollectionSitesList model = new CollectionSitesList();

		Controller controller = new Controller(model);
		GUIManager guiManager = new GUIManager(controller);
		
		JFrame frame = new Frame(guiManager);
		
		model.addObserver(guiManager);
		
		model.loadData("listData.bin");
		
		frame.setVisible(true);

		
	}

}
