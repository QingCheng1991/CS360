package view;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


import util.CollectionSiteRawData;
import util.GUIInternalNotification;
import util.GUIOperationResult;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GSiteTableListPanel extends JPanel implements Observer {
	private GSiteTableModel tableModel;
	private JTable guiTable;
	private JButton addBtn;
	private JButton editBtn;
	private JButton removeBtn;
	private GUIManager guiManager;
	private JFrame parentFrame;
	private JComboBox userSwitcherComboBox;
	private static Logger logger = Logger.getLogger("view.GSiteTableListPanel");
	private static FileHandler fh = null;
	private boolean enableAdminInterfaceOnDelete = true;
	
	public GSiteTableListPanel(JFrame parentFrameParam, GUIManager guiManagerParam) {
		guiManager = guiManagerParam;
		parentFrame = parentFrameParam;
		
		if (false)
			try {
				fh = new FileHandler("gSiteTableListPanel.txt");
				logger.addHandler(fh);
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.SEVERE);
		
		tableModel = new GSiteTableModel();
		
		guiTable = new JTable(tableModel);
		guiTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		guiTable.getColumnModel().getColumn(0).setMaxWidth(60);
		guiTable.getSelectionModel().addListSelectionListener(new TableSelectionListener());
		guiTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//guiTable.getSelectionModel().a addListDataListener(new TableGUIDataListener());
		//guiTable.setCellSelectionEnabled(true);
		setSortOptions();
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel leftSidePane = new JPanel();
		add(leftSidePane, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {364, 0};
		gbl_panel.rowHeights = new int[]{35, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		leftSidePane.setLayout(gbl_panel);
		
		JPanel commandButtonPanel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		leftSidePane.add(commandButtonPanel, gbc_panel_1);
		
		addBtn = new JButton("Add");
		addBtn.setMnemonic(java.awt.event.KeyEvent.VK_A);
		commandButtonPanel.add(addBtn);
		
		editBtn = new JButton("Edit");
		editBtn.setMnemonic(java.awt.event.KeyEvent.VK_E);
		commandButtonPanel.add(editBtn);
		editBtn.setEnabled(false);
		
		removeBtn = new JButton("Remove");
		removeBtn.setMnemonic(java.awt.event.KeyEvent.VK_R);
		commandButtonPanel.add(removeBtn);
		removeBtn.setEnabled(false);
		
		
		String[] users = { "Admin", "Guest" };

		userSwitcherComboBox = new JComboBox(users);
		userSwitcherComboBox.setSelectedIndex(0);
		userSwitcherComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String selection = (String) cb.getSelectedItem();
				if(selection.equals("Admin" ))
				{
					String pass = JOptionPane.showInputDialog("Please Enter Password" );
					if(pass.equals("1234"))
					{
						enableAdminInterface();
					} else {
						JOptionPane.showMessageDialog(null, "Wrong password", "Authentication error", JOptionPane.ERROR_MESSAGE);
						cb.setSelectedIndex(1);
					}
				}
				
				if(selection.equals("Guest"))
				{
					enableGuestInterface();
				}
				

			}
		});
		
		activateInterface();
		//editBtn.setVisible(false);
		//removeBtn.setVisible(false);
		GridBagConstraints gbc_userSwitcherComboBox = new GridBagConstraints();
		gbc_userSwitcherComboBox.anchor = GridBagConstraints.EAST;
		gbc_userSwitcherComboBox.gridx = 1;
		gbc_userSwitcherComboBox.gridy = 0;
		//leftSidePane.add(userSwitcherComboBox, gbc_userSwitcherComboBox);
		//guiTable.getColumnModel().getColumn(1).setMaxWidth(150);
		//	guiTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		guiTable.setFillsViewportHeight(true);
		JScrollPane tableScrollPane = new JScrollPane(guiTable);
		//guiTable.setPreferredSize(new Dimension(100,400));
		//tableScrollPane.setPreferredSize(new Dimension(200,400));
		
		
		this.add(tableScrollPane, BorderLayout.CENTER);
		
		ActionHandler handler = new ActionHandler();
		addBtn.addActionListener(handler);
		removeBtn.addActionListener(handler);
		editBtn.addActionListener(handler);
		
		guiTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		    	
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && row != -1) {
		           guiManager.selectSite(tableModel.getSiteAtIndex(guiTable.convertRowIndexToModel(row)));
		        }
		    }
		});
	}
	
	private void setSortOptions() {
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		guiTable.setRowSorter(sorter);
		List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		//sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		guiTable.getRowSorter().toggleSortOrder(0);
	}
	
	public void removeSite(GCollectionSite gSite) {
		tableModel.removeGSite(gSite);
	}
	
	public void addSite(GCollectionSite gSite) {
		tableModel.addGSite(gSite);
	}
	
	public void editSite(GCollectionSite gSite) {
		tableModel.editGSite(gSite);
	}
	
	
	private void activateInterface() {
		if (isAdminMode()) 
			enableAdminInterface();
		else enableGuestInterface();
	}
	private boolean isAdminMode() {
		//System.out.println(userSwitcherComboBox.getSelectedItem().toString());
		return userSwitcherComboBox.getSelectedItem().toString().equals("Admin");
	}
	
	private void enableGuestInterface() {
		addBtn.setEnabled(false);
		editBtn.setEnabled(false);
		removeBtn.setEnabled(false);
	}
	
	private void enableAdminInterface() {
		addBtn.setEnabled(true);
	}
	
	private void defocusTable() {
		if (isAdminMode()) {
			enableAdminInterface();
			editBtn.setEnabled(false);
			removeBtn.setEnabled(false);
		} else {
			enableGuestInterface();
		}
	}
	
	private void giveAttentionToGSite(GCollectionSite gSite) {
		if (gSite == null) return;
		int modelIndex = tableModel.getIndexOf(gSite);
		guiTable.setRowSelectionInterval(guiTable.convertRowIndexToView(modelIndex), guiTable.convertRowIndexToView(modelIndex));
		
	}
	
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if ( arg0.getSource() == addBtn ) {
				//guiManager.addTestSite(GUITest.createTempGCollectionSite("blabla", 41.0, -85.2));
				guiManager.openAddSiteDialog();
			} else if ( arg0.getSource() == removeBtn ) {
				int userChoice = JOptionPane.showConfirmDialog(parentFrame,"Do you want to remove this collection site ?" , "Site removal confirmation", JOptionPane.YES_NO_OPTION);
				if (userChoice == JOptionPane.YES_OPTION) {
					if (!guiTable.isFocusOwner()) {defocusTable(); enableAdminInterfaceOnDelete = false;}
					GCollectionSite gSite = tableModel.getSiteAtIndex(guiTable.convertRowIndexToModel(guiTable.getSelectedRow()));
					CollectionSiteRawData rawData = new CollectionSiteRawData(gSite.getSiteNumber(), gSite.getSiteName(), gSite.getSiteDescription(), gSite.getSiteLatitude(), gSite.getSiteLongtitude(), gSite.getLastCollectedSampleDate());
					guiManager.removeSite(rawData);	
				}
			} else if ( arg0.getSource() == editBtn ) {
				guiManager.openEditSiteDialog(tableModel.getSiteAtIndex(
						guiTable.convertRowIndexToModel(guiTable.getSelectedRow())));
			}
		}
		
	}
	
	private class TableSelectionListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() == guiTable.getSelectionModel()) {
				//System.out.println("first index " + e.getFirstIndex());
				if (!e.getValueIsAdjusting() && e.getFirstIndex() >= 0 && isAdminMode()) {
					//System.out.println("selection listener");
					if (enableAdminInterfaceOnDelete == true) {
						editBtn.setEnabled(true);
						removeBtn.setEnabled(true);
					} else if (enableAdminInterfaceOnDelete == false) {
						enableAdminInterfaceOnDelete = true;
					}
				}
			}
		}
	}
	
	private class TableGUIDataListener implements ListDataListener{

		@Override
		public void contentsChanged(ListDataEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void intervalAdded(ListDataEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void intervalRemoved(ListDataEvent arg0) {
			editBtn.setEnabled(false);
			removeBtn.setEnabled(false);
			
		}
		
	}
	
	

	@Override
	public void update(Observable arg0, Object arg1) {
		logger.info("received update method call from " + arg0.getClass().toString());
		if (arg1 != null) logger.info("attached object is of type " + arg1.getClass().toString());
		if (arg0 instanceof GUIManager && arg1 instanceof GUIOperationResult) {
			GUIOperationResult result = (GUIOperationResult) arg1;
			logger.info("the argument is of type GUIOperationResult, ADD = " + result.isAddOperation() +
						", EDIT = " + result.isEditOperation() + "SUCCESS = " + result.isSuccess());
			if (result.isSuccess()) {
				switch (result.getOperation()) {
				case GUIOperationResult.ADD: 
						addSite(result.getGCollectionSite());
						break;
				case GUIOperationResult.EDIT:
						editSite(result.getGCollectionSite());
						break;
				case GUIOperationResult.REMOVE:
						removeSite(result.getGCollectionSite());
						break;
				}
			}		
		} else if (arg0 instanceof GUIManager && arg1 instanceof GUIInternalNotification) {
			GUIInternalNotification notification = (GUIInternalNotification) arg1;
			if (notification.isGiveAttentionOperation()) {
				giveAttentionToGSite((GCollectionSite)notification.getObjdata());
			}
			
		}
	}
}
