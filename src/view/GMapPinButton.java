package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.MouseInputListener;


public class GMapPinButton extends JButton {
	private String pinPath = "resources/pin.png";
	private String pinBrightPath = "resources/pin-bright.png";
	private String pinOldPath = "resources/pin-old.png";
	private String pinOldHoverPath = "resources/pin-old-hover.png";
	private ImageIcon okPin;
	private ImageIcon okPinHover;
	private ImageIcon okPinPressed;
	private ImageIcon oldPinHover;
	private ImageIcon oldPin;
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	private JButton currentButton; 
	private MapSiteItem mapSiteItem;
	private int pinSize = 50;
	private int buttonHeight = 50;
	private int buttonWidth = 40;
	public GMapPinButton(MapSiteItem mapSiteItemParam, String text) {
		super();
		GMapPinButtonCommonConstructor(mapSiteItemParam);
	}
	
	public GMapPinButton(MapSiteItem mapSiteItemParam) {
		super();
		GMapPinButtonCommonConstructor(mapSiteItemParam);
	}
	
	public void GMapPinButtonCommonConstructor(MapSiteItem mapSiteItemParam) {
		mapSiteItem = mapSiteItemParam;
		currentButton = this;
		okPin = makeIcon(pinPath, pinSize,pinSize);
		okPinHover = makeIcon(pinBrightPath,pinSize,pinSize);
		oldPin = makeIcon(pinOldPath,pinSize,pinSize);
		oldPinHover = makeIcon(pinOldHoverPath, pinSize,pinSize);
		if (isOld()) {
			this.setIcon(oldPin);
			this.setRolloverIcon(oldPinHover);
		} else {
			this.setIcon(okPin);
			this.setRolloverIcon(okPinHover);
		}
		//this.setPressedIcon(okPinPressed);
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(true);
		this.setBorder(null);
		PinButtonMouseListener mouseListener = new PinButtonMouseListener();
		this.addMouseMotionListener(mouseListener);
		this.addMouseListener(mouseListener);
		this.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		this.setMargin(new Insets(0,0,0,0));
		//this.revalidate();
		//this.repaint();
	}
	
	public boolean isOld() {
		if (mapSiteItem != null) {
			GCollectionSite gSite = mapSiteItem.getgSite();
			Date sampleDate = gSite.getLastCollectedSampleDate();
			if (sampleDate == null) return true;
			Date today = new Date();
			if (TimeUnit.DAYS.convert((long)today.getTime() - (long)sampleDate.getTime(),TimeUnit.MILLISECONDS) > (long)90) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//System.out.println("repaint");
		super.paintComponent(g);
	}
	private ImageIcon makeIcon(String img, int i, int j) {
		//The process of scaling an image!
		ImageIcon ico = new ImageIcon(img);
		Image image = ico.getImage(); // transform it 
		Image newimg = image.getScaledInstance(i, j,  Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);  // transform it back
	}
	
	private class PinButtonMouseListener implements MouseInputListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if (mapSiteItem != null ) mapSiteItem.click();
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			currentButton.setCursor(handCursor);
			if (mapSiteItem != null) mapSiteItem.activate();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
		
	}
}
