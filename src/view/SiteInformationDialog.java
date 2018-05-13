package view;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import util.CollectionSiteRawData;
import util.GUIOperationResult;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SiteInformationDialog extends JDialog implements Observer{
	private JTextField siteNumber;
	private JTextField siteName;
	private JTextField siteLatitude;
	private JTextField siteLongtitude;
	private JTextArea siteDescription;
	private JTextField lastCollectedSampleDate;
	private ArrayList<JTextField> jtextfieldList;
	
	private JLabel siteNumberLabel;
	private JLabel siteNameLabel;
	private JLabel siteLatitudeLabel;
	private JLabel siteLongtitudeLabel;
	private JLabel siteDescriptionLabel;
	private JLabel lastCollectedSampleDateLabel;
	private JLabel frameModeLabel;
	
	private JButton OKButton;
	private JButton cancelButton;
	
	private final int SITE_LATITUDE_TEXTFLD_WIDTH = 100;
	private final int SITE_NAME_TEXTFLD_WIDTH = 150;
	private final int SITE_NUMBER_TEXTFLD_WIDTH = 100;
	private final int DATE_TEXTFLD_WIDTH=100;
	private final int DESCRIPTION_WIDTH=230;
	
	private final int SITE_LATITUDE_TEXTFLD_HEIGHT = 30;
	private final int SITE_NAME_TEXTFLD_HEIGHT = 30;
	private final int SITE_NUMBER_TEXTFLD_HEIGHT = 30;
	private final int DATE_TEXTFLD_HEIGHT=30;
	private final int DESCRIPTION_HEIGHT=100;
	
	private final String addFrameModeStr = "New Collection Site";
	private final String editFrameModeStr = "Update A Collection Site";
	private final String viewFrameModeStr = "View A Collection Site";

	private final int FRAME_MODE_FONT_SIZE = 22;
	
	
	public static final int ADD_MODE = 1;
	public static final int EDIT_MODE = 0;
	public static final int VIEW_MODE = 2;
	
	private GUIManager guiManager;
	private JFrame parentFrame;
	
	private SimpleDateFormat dateFormat;
	private SimpleDateFormat timeFormat;
	private int dialogMode = ADD_MODE;
	private SiteInformationDialog currentDialog;
	
	private JLabel nameLabel;
	private JTextField nameTextField;
	
	private static Logger logger = Logger.getLogger("view.siteInformation");
	private static FileHandler fh = null;
	private JTextField timeTextField = new JTextField();
	
	public SiteInformationDialog(int frameMode, GUIManager guiManagerParam, JFrame parentFrameParam) {
		super(parentFrameParam);
		currentDialog = this;
		/*logging*/
		if (false)
			try {
				fh = new FileHandler("siteInformation.txt");
				logger.addHandler(fh);
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.SEVERE);

		parentFrame = parentFrameParam;
		guiManager = guiManagerParam;
		if (guiManager != null)
			guiManager.addObserver(this);
		jtextfieldList = new ArrayList<JTextField>();
		
		dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		timeFormat = new SimpleDateFormat("HH:mm");
		frameModeLabel = new JLabel(addFrameModeStr);
		frameModeLabel.setFont(new Font("Tahoma", Font.PLAIN, FRAME_MODE_FONT_SIZE));
		
		
		siteNumber = new JTextField(); 
		jtextfieldList.add(siteNumber);
		siteName = new JTextField(); jtextfieldList.add(siteName);
		siteLatitude = new JTextField(); jtextfieldList.add(siteLatitude);
		siteLongtitude = new JTextField(); jtextfieldList.add(siteLongtitude);
		nameTextField = new JTextField(); jtextfieldList.add(nameTextField);
		siteDescription = new JTextArea(); 
		lastCollectedSampleDate = new JTextField(); jtextfieldList.add(lastCollectedSampleDate);
		jtextfieldList.add(timeTextField);
		JScrollPane siteDescriptionPane = new JScrollPane(siteDescription);
		
		siteNumberLabel = new JLabel("Site Number(*)");
		siteNameLabel = new JLabel("Site Name(*)");
		siteLatitudeLabel = new JLabel("Site Latitude(*)");
		siteLongtitudeLabel = new JLabel("Site Longtitude(*)");
		lastCollectedSampleDateLabel = new JLabel("<html>Last Date of Sample Collection(*)<br/>(mm/dd/yyyy)</html>");
		nameLabel = new JLabel("<html>Name of Person <br/> taking the sample(*)");
		siteDescriptionLabel = new JLabel("Site's Location Description");
		
		
		
		OKButton = new JButton("OK");
		OKButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validateAndDispatchEventToGUIManager();
			}
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource() == cancelButton) {
					resetDialog();
					currentDialog.setVisible(false);
				}
					
			}
		});
		
		//frameModeLabel.setText("");
		JPanel OKpanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) OKpanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		OKpanel.add(OKButton);
		OKpanel.add(cancelButton);
		
		JLabel lblTimehhmm = new JLabel("Time: (hh:mm) (*)");
		
		//textField = new JTextField();
		timeTextField.setColumns(10);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(frameModeLabel)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(siteNumberLabel)
								.addComponent(siteNameLabel)
								.addComponent(siteLatitudeLabel)
								.addComponent(siteLongtitudeLabel)
								.addComponent(lastCollectedSampleDateLabel)
								.addComponent(nameLabel)
								.addComponent(siteDescriptionLabel))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(siteNumber, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(siteName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(siteLatitude, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(siteLongtitude, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lastCollectedSampleDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblTimehhmm)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(timeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(nameTextField, 230, 230, 230)
								.addComponent(siteDescriptionPane, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
								.addComponent(OKpanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(frameModeLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(siteNumberLabel)
						.addComponent(siteNumber, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(siteNameLabel)
						.addComponent(siteName, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(siteLatitudeLabel)
						.addComponent(siteLatitude, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(siteLongtitudeLabel)
						.addComponent(siteLongtitude, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lastCollectedSampleDateLabel)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lastCollectedSampleDate, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblTimehhmm)
							.addComponent(timeTextField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(siteDescriptionLabel)
						.addComponent(siteDescriptionPane, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
					.addComponent(OKpanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		getContentPane().setLayout(groupLayout);
		
		this.pack();
		setDialogMode(ADD_MODE);
	}

	void resetAppearanceDialog() {
		
	}
	
	void resetDialog() {
		for (JTextField tf : jtextfieldList) {
			tf.setText("");
		}
		siteDescription.setText("");
		setFormEditable(true);
	}
	
	private void editSiteMode(GCollectionSite gSite) {
		fillFormWith(gSite);
		setFormEditable(true);
	}
	
	private void viewSiteMode(GCollectionSite gSite) {
		fillFormWith(gSite);
		setFormEditable(false);
	}
	
	private void setFormEditable(boolean flag) {
		for (JTextField tf : jtextfieldList) {
			tf.setEditable(flag);
		}
		siteDescription.setEditable(flag);
	}
	
	private void fillFormWith(GCollectionSite gSite) {
		siteNumber.setText(((Integer)gSite.getSiteNumber()).toString());
		siteName.setText(gSite.getSiteName());
		siteLatitude.setText( ((Float)gSite.getSiteLatitude()).toString() );
		siteLongtitude.setText( ((Float)gSite.getSiteLongtitude()).toString() );
		siteDescription.setText(gSite.getSiteDescription());
		if (gSite.getLastCollectedSampleDate() != null) {
			lastCollectedSampleDate.setText( dateFormat.format(gSite.getLastCollectedSampleDate()).toString());
			timeTextField.setText(timeFormat.format(gSite.getLastCollectedSampleDate()));
		}
		if (gSite.getPersonName() != null) {
			nameTextField.setText(gSite.getPersonName());
		}
		
	}
	
	private void validateAndDispatchEventToGUIManager() {
		CollectionSiteRawData rawData = null;
		if (dialogMode != VIEW_MODE) {
			boolean validationResult = validateInput();
			logger.info("input validate on siteNumber " + siteNumber.getText() + ":" + validationResult);
			if ( !validationResult) return;
			//try {
				
				Date sampleTime = null; 
				try{
					sampleTime = timeFormat.parse(timeTextField.getText());
				} catch (Exception e) {
					
				}
				
				Date sampleDate = null;
				try{
					sampleDate = dateFormat.parse(lastCollectedSampleDate.getText());
				} catch ( Exception e) {
					
				}
				Date toBeSentDate = null;
				
				Calendar originalCalendar = Calendar.getInstance();
				Calendar sampleDateCalendar = (Calendar)originalCalendar.clone();
				Calendar sampleTimeCalendar = (Calendar)originalCalendar.clone();
				if (sampleDate != null) {
					sampleDateCalendar.setTime(sampleDate);
					
					if (sampleTime != null) {
						sampleTimeCalendar.setTime(sampleTime);
						sampleDateCalendar.set(Calendar.HOUR_OF_DAY, sampleTimeCalendar.get(Calendar.HOUR_OF_DAY));
						sampleDateCalendar.set(Calendar.MINUTE, sampleTimeCalendar.get(Calendar.MINUTE));
					} else {
						sampleDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
						sampleDateCalendar.set(Calendar.MINUTE,0);
					}
				}
				rawData = new CollectionSiteRawData(Integer.parseInt(siteNumber.getText()), 
							siteName.getText(), siteDescription.getText(), 
							Float.parseFloat(siteLatitude.getText()), 
							Float.parseFloat(siteLongtitude.getText()), 
							sampleDateCalendar.getTime(),nameTextField.getText());
				
			/*} catch (NumberFormatException | ParseException e) {
				e.printStackTrace();
			}*/
		} else if (dialogMode == VIEW_MODE){
			
		}
		
		
		switch (dialogMode) {
			case ADD_MODE:
					logger.info("calling gui addSite method");
					guiManager.addSite(rawData);
					break;
			case EDIT_MODE:
					logger.info("calling gui editSite method");
					guiManager.editSite(rawData);
					break;
			case VIEW_MODE:
					logger.info("done view dialog");
					this.setVisible(false);
					this.resetDialog();
					break;
		}
	}
	
	private boolean validateInput() {
		boolean validBool = true;
		
		//validate site NUMBER
		String valid = siteNumber.getText();
		
		try {
			if(valid.length() == 3)
			{
				 int test = Integer.parseInt(valid);
		            validBool = true;
		         if (test < 100 || test > 999)
					{
						JOptionPane.showMessageDialog(null, "Please enter valid 3-digit site Number.");
			            validBool = false;
			            return false;
					}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please enter valid 3-digit site Number.");
	            validBool = false;
	            return false;
			}
        
        } catch (NumberFormatException ex) {
        	JOptionPane.showMessageDialog(null, "Please enter valid site Number");
            validBool = false;
            return false;
        }
        
		//Validate site name
		valid = siteName.getText();
		 if(valid != null && valid.matches("[-+]?\\d*\\.?\\d+")){
			
			 JOptionPane.showMessageDialog(null, "Please enter valid site Name");
			 validBool = false;
			 return false;
		 }

		 if(valid == null || valid.trim().isEmpty())
		 {
			 JOptionPane.showMessageDialog(null, "Please enter valid site Name");
			 validBool = false;
			 return false;
		 }
		 Float validNum = 0f ;
		//Validate site latitude
			valid = siteLatitude.getText();
			 if(!(valid != null && valid.matches("[-+]?\\d*\\.?\\d+"))){
					
				 JOptionPane.showMessageDialog(null, "Please enter valid site latitude");
				 validBool = false;
				 return false;
			 }
			 

			 if(valid == null || valid.trim().isEmpty())
			 {
				 JOptionPane.showMessageDialog(null, "Please enter valid site Latitude");
				 validBool = false;
				 return false;
			 }
			 
			 try {
				 validNum = Float.parseFloat(valid);
			 }
			 catch(NumberFormatException e){
				 
			 }
			 if(validNum <0 || validNum >90)
			 {
				 JOptionPane.showMessageDialog(null, "Please enter valid site Latitude");
				 validBool = false;
				 return false;
			 }
		//Validate site Longitude
		valid = siteLongtitude.getText();
		 if(!(valid != null && valid.matches("[-+]?\\d*\\.?\\d+")) ){
			
			 JOptionPane.showMessageDialog(null, "Please enter valid site Longitude");
			 validBool = false;
			 return false;
		 }

		 if(valid == null || valid.trim().isEmpty())
		 {
			 JOptionPane.showMessageDialog(null, "Please enter valid site Longitude");
			 validBool = false;
			 return false;
		 }
		
		 validNum = 0f ;
		 try {
			 validNum = Float.parseFloat(valid);
		 }
		 catch(NumberFormatException e){
			 
		 }
		 if(validNum <-180 || validNum >180)
		 {
			 JOptionPane.showMessageDialog(null, "Please enter valid site Longitude");
			 validBool = false;
			 return false;
		 }
		
		//date validation
				final String DATE_FORMAT = "mm/dd/yyyy";

				valid = lastCollectedSampleDate.getText();
				 try {
			            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			            df.setLenient(false);
			            df.parse(valid);
			            validBool = true;
			        } catch (ParseException e) {
			        	JOptionPane.showMessageDialog(null, "Please enter valid Date");
			            validBool = false;
			            return false;
			        }
				 //user Name Validation
				 valid = nameTextField.getText();
				 if(valid != null && valid.matches("[-+]?\\d*\\.?\\d+")){
					
					 JOptionPane.showMessageDialog(null, "Please enter a valid human name");
					 validBool = false;
					 return false;
				 }

				 if(valid == null || valid.trim().isEmpty())
				 {
					 JOptionPane.showMessageDialog(null, "Please enter a valid human name");
					 validBool = false;
					 return false;
				 }
				 
				 //Time Validation
				 final String TIME_FORMAT = "HH:mm";

					valid = timeTextField.getText();
					 try {
				            DateFormat df = new SimpleDateFormat(TIME_FORMAT);
				            df.setLenient(false);
				            df.parse(valid);
				            validBool = true;
				        } catch (ParseException e) {
				        	JOptionPane.showMessageDialog(null, "Please enter valid Time");
				            validBool = false;
				            return false;
				        }
		return validBool;
	}
	
	private void setDialogMode(int frameMode) {
		switch (frameMode) {
		case ADD_MODE:	
						frameModeLabel.setText(addFrameModeStr);
						this.setTitle(addFrameModeStr);
						//setDialogMode(ADD_MODE);
						dialogMode = ADD_MODE;
						cancelButton.setVisible(true);
						break;
		case EDIT_MODE: 
						frameModeLabel.setText(editFrameModeStr);
						this.setTitle(editFrameModeStr);
						dialogMode = EDIT_MODE;
						//setDialogMode(EDIT_MODE);
						cancelButton.setVisible(true);
						break;
		case VIEW_MODE:	
						frameModeLabel.setText(viewFrameModeStr);
						this.setTitle(viewFrameModeStr);
						//setDialogMode(VIEW_MODE);
						dialogMode = VIEW_MODE;
						cancelButton.setVisible(false);
						break;
		default:		
						frameModeLabel.setText(addFrameModeStr);
						dialogMode = ADD_MODE;
						break;
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		logger.info("update method called.");
		if (o instanceof GUIManager && arg instanceof GUIOpenDialogOperation) {
			logger.info("Object is of type GUIOpenDialogOperation");
			GUIOpenDialogOperation temp = (GUIOpenDialogOperation) arg;
			switch (temp.getOperationCode()) {
			case GUIOpenDialogOperation.OPEN_ADD_DIALOG :
					resetDialog();
					setDialogMode(ADD_MODE);
					this.setVisible(true);
					break;
			case GUIOpenDialogOperation.OPEN_EDIT_DIALOG :
					editSiteMode(temp.getGSite());
					setDialogMode(EDIT_MODE);
					this.setVisible(true);
					break;
			case GUIOpenDialogOperation.OPEN_VIEW_DIALOG:
					viewSiteMode(temp.getGSite());
					setDialogMode(VIEW_MODE);
					this.setVisible(true);
					break;
			default:
					break;
			}
		}
		else if (o instanceof GUIManager && arg instanceof GUIOperationResult) {
			logger.info("Object is of type GUIOperationResult");
			GUIOperationResult result = (GUIOperationResult) arg;
			logger.info("Operation success = " + result.isSuccess());
			if (result.isSuccess()) {
				this.resetDialog();
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, result.getDescription(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
