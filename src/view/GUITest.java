package view;

import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.CollectionSiteModel;
import model.CollectionSitesList;

public class GUITest {
	//clicking the window close button does not exit this test program. Must terminate 
	// the program in order to stop it.
	private static MapPanel classMapPanel;
	private static GUIManager classGuiManager;
	public static void main(String[] args) {
		makeClassVariables();
		//showSiteInformationDialog();
		showMapFrame_WithGUIManager();
		showMainFrame();
		//showGSiteTableListPanel();
		showGSiteTableListPanel_WithGUIManager();
		//showMapPinButton();
	}
	
	private static void makeClassVariables() {
		CollectionSitesList model = new CollectionSitesList();
		Controller controller = new Controller(model);
		classGuiManager = new GUIManager(controller);
		classMapPanel = new MapPanel(classGuiManager);
		model.addObserver(classGuiManager);
		classGuiManager.addObserver(classMapPanel);
	}

	private static void showMapFrame_WithGUIManager() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame mapTestFrame = new JFrame();
				mapTestFrame.add(classMapPanel);
				mapTestFrame.pack();
				mapTestFrame.setVisible(true);
				mapTestFrame.setBounds(900,100,700,900);
			}
		});
	}

	private static void showGSiteTableListPanel_WithGUIManager() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame siteTableFrame = new JFrame();
				siteTableFrame.setTitle("GUI Test Only");
				
				SiteInformationDialog dialog = new SiteInformationDialog(0, classGuiManager, siteTableFrame);
				dialog.pack();
				
				GSiteTableListPanel listPanel = new GSiteTableListPanel(siteTableFrame,classGuiManager);
				
				
				classGuiManager.addObserver(dialog);
				classGuiManager.addObserver(listPanel);
				
				siteTableFrame.setBounds(400,500,400,100);
				siteTableFrame.add(listPanel);
				siteTableFrame.setTitle("GSiteTableListPanel");
				//listPanel.addSite(createTempGCollectionSite("abc"));
				//listPanel.addSite(createTempGCollectionSite("def"));
				siteTableFrame.pack();
				siteTableFrame.setVisible(true);

			}
		});
		//classGuiManager.addTestSite(createTempGCollectionSite("new", 41.0, -85.2));

	}

	private static void showSiteInformationDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SiteInformationDialog dialog = new SiteInformationDialog(SiteInformationDialog.ADD_MODE, null, null);
				dialog.pack();
				dialog.setVisible(true);
			}
		});
	}
	
	private static void showMapFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame mapTestFrame = new JFrame();
				JPanel mapPanel = new MapPanel(null);
				mapTestFrame.add(mapPanel);
				mapTestFrame.pack();
				mapTestFrame.setVisible(true);
				mapTestFrame.setBounds(500,200,600,500);
			}
		});
	}

	private static void showMainFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame mainFrame = new JFrame();
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setBounds(100,600,300,100);
				mainFrame.add(new JLabel("Terminate these test GUIs by closing this frame."));
				mainFrame.setTitle("MainFrame");
				mainFrame.setVisible(true);
			}
		});
	}
	
	private static void showGSiteTableListPanel() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame siteTableFrame = new JFrame();
				GSiteTableListPanel listPanel = new GSiteTableListPanel(siteTableFrame,null);
				siteTableFrame.setBounds(400,600,400,100);
				siteTableFrame.add(listPanel);
				siteTableFrame.setTitle("GSiteTableListPanel");
				listPanel.addSite(createTempGCollectionSite());
				listPanel.addSite(createTempGCollectionSite());
				siteTableFrame.pack();
				siteTableFrame.setVisible(true);
			}
		});
	}
	
	private static void showMapPinButton() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame siteTableFrame = new JFrame();
				siteTableFrame.setBounds(1200,200,400,400);
				JPanel panel = new JPanel();
				panel.add(new GMapPinButton(null));
				siteTableFrame.add(panel);
				//siteTableFrame.pack();
				siteTableFrame.setVisible(true);
			}
		});
	}
	
	private static Random randomGenerator = new Random();
	
	private static GCollectionSite createTempGCollectionSite() {
		return new GCollectionSite(new CollectionSiteModel(randomGenerator.nextInt(100), "name", "description", (float)3.5,(float)5.5,new Date(123)));
	}
	
	private static GCollectionSite createTempGCollectionSite(String siteName) {
		return new GCollectionSite(new CollectionSiteModel(randomGenerator.nextInt(900)+100, siteName, "description", (float)3.5,(float)5.5,new Date(123)));
	}
	
	static GCollectionSite createTempGCollectionSite(String siteName, double latitude, double longtitude) {
		return new GCollectionSite(new CollectionSiteModel(randomGenerator.nextInt(100), "name", "description", (float)latitude,(float)longtitude,new Date(123)));
	}
}
