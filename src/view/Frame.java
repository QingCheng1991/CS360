package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JList;

public class Frame extends JFrame{
	private MenuBar menuBar;
	public Frame(GUIManager guiManagerParam) {
		setTitle("IPFW Department of Biology - Water Sample Collection Site Manager");
		setBounds(50,50,1200,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		MapPanel mapPanel = new MapPanel(guiManagerParam);
		getContentPane().add(mapPanel, BorderLayout.CENTER);

		GSiteTableListPanel listPanel = new GSiteTableListPanel(this,guiManagerParam);
		listPanel.setPreferredSize(new Dimension(400,400));
		getContentPane().add(listPanel, BorderLayout.WEST);
		
		menuBar = new MenuBar(guiManagerParam);
		this.setJMenuBar(menuBar);
		
		SiteInformationDialog dialog = new SiteInformationDialog(0, guiManagerParam, this);
		guiManagerParam.addObserver(dialog);
		guiManagerParam.addObserver(mapPanel);
		guiManagerParam.addObserver(listPanel);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setLocationRelativeTo(null);
		revalidate();
		repaint();
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				guiManagerParam.saveDataOnGUIExit();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

}
