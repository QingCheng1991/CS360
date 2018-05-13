package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.File;
import com.sun.xml.internal.ws.api.Component;

import model.CollectionSitesList;

public class MenuBar extends javax.swing.JMenuBar{
	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenuItem importMenuItem;
	private javax.swing.JMenuItem exitMenuItem;
	private GUIManager guiManager;
	private String fileName;
	private CollectionSitesList importer = new CollectionSitesList();
	
	public MenuBar(GUIManager guiManagerParam) 
	{
		
		guiManager = guiManagerParam;
		ActionListener menuListener = new MenuListener();
		
		
		importMenuItem = new javax.swing.JMenuItem("Import From ...");
		importMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_I);
		importMenuItem.addActionListener(menuListener);
		importMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog((java.awt.Component)e.getSource());
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			        File file = fileChooser.getSelectedFile();
			        try {
			           fileName = file.toString();
			           guiManager.importDataFromFile(fileName);
			           JOptionPane.showMessageDialog(null, "Please have a look at import_log_report.txt for import results.");		           
			        } catch (Exception ex) {
			          System.out.println("problem accessing file" + file.getAbsolutePath());
			        }
			    } 
			    else {
			        System.out.println("File access cancelled by user.");
			    } 
				
			}
		});
		
		
		exitMenuItem = new javax.swing.JMenuItem("Exit");
		exitMenuItem.setMnemonic(java.awt.event.KeyEvent.VK_E);
		exitMenuItem.addActionListener(menuListener);
		
		fileMenu = new javax.swing.JMenu("File");
		fileMenu.add(importMenuItem);
		fileMenu.add(exitMenuItem);
		fileMenu.setMnemonic(java.awt.event.KeyEvent.VK_F);
		
		this.add(fileMenu);
		
	}
	
	private class MenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == importMenuItem) {
				
			} else if (arg0.getSource() == exitMenuItem) {
				guiManager.saveDataOnGUIExit();
			}
			
		}
		
	}
}
