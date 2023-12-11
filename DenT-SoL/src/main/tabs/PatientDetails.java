package main.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.border.DropShadowBorder;
import org.jdesktop.swingx.prompt.PromptSupport;

import designs.CLabel;
import designs.CTextField;
import gradientpane.Imagbg;
import main.popups.TreatmentDetails;
import javax.swing.JComboBox;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientDetails
  extends Imagbg
{
  private static final long serialVersionUID = 1L;
  static JTable table;
  private static CTextField searchpd;
  public CLabel lblNewLabel;
  private static ResultSet rs;
  private static PreparedStatement ps;
  JComboBox<String> comboBox;
  TreatmentDetails treatmentDetails = new TreatmentDetails();
  Toolkit t = Toolkit.getDefaultToolkit();
  
  Dimension dim = new Dimension(t.getScreenSize());
  
  int height = (int)dim.getHeight();
  
  int width = (int)dim.getWidth();
  
  public static void loadL() throws Throwable {
    String cd = System.getProperty("user.dir");
    Class.forName("org.sqlite.JDBC");
    String url = "jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db";
    Connection con = DriverManager.getConnection(url);
    ps = con.prepareStatement("select * from PTdata order by date");
    rs = ps.executeQuery();
    int SNo = 0;
    while (rs.next()) {
      SNo++;
      String fnm = rs.getString("fname");
      String age = rs.getString("age");
      String gender = rs.getString("gender");
      String phone = rs.getString("phone");
      String datef = rs.getString("date");
      
      SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
      Date sd = fr.parse(datef);
      SimpleDateFormat frmtr = new SimpleDateFormat("dd-MMM-yyyy");
      String date = frmtr.format(sd);
      Object[] obj = { Integer.valueOf(SNo), fnm, age, gender, phone, date };
      DefaultTableModel dm = (DefaultTableModel)table.getModel();
      dm.insertRow(0, obj);
    }
    con.close();
  }

  public static void loadByTreatment(String treatment) throws Throwable {
	  String cd = System.getProperty("user.dir");
	    Class.forName("org.sqlite.JDBC");
	    String url = "jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db";
	    Connection con = DriverManager.getConnection(url);
	    ps = con.prepareStatement("SELECT DISTINCT pid FROM pttdata WHERE treatment like ?");
        ps.setString(1, treatment+"%");
        rs = ps.executeQuery();
        List<String> pidList = new ArrayList<>();
        while (rs.next()) {
            String pid = rs.getString("pid");
            pidList.add(pid);
        }
        
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        dm.setRowCount(0);

        for (String pid : pidList) {
            ps = con.prepareStatement("SELECT * FROM PTdata WHERE pid = ? order by date");
            ps.setString(1, pid);
            rs = ps.executeQuery();

            while (rs.next()) {
                int SNo = dm.getRowCount() + 1;
                String fnm = rs.getString("fname");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String datef = rs.getString("date");

                SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
                Date sd = fr.parse(datef);
                SimpleDateFormat frmtr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = frmtr.format(sd);

                Object[] obj = { SNo, fnm, age, gender, phone, date };
                dm.insertRow(0, obj);
            }
        }
        
	    con.close();
	}
	   
  
  public PatientDetails() throws Throwable {
  	addMouseListener(new MouseAdapter() {
  		@Override
  		public void mouseClicked(MouseEvent e) {
  			table.clearSelection();
  		}
  	});
    setBorder(new BevelBorder(0, null, null, null, null));
    init();
    loadL();
    final String[] treat = { null };

    comboBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {  
            treat[0] = (String) comboBox.getSelectedItem();
            if (treat[0] == null || treat[0].isEmpty() || treat[0].equalsIgnoreCase("Sort By")) {
                try {
                	DefaultTableModel dm = (DefaultTableModel) table.getModel();
                	dm.setRowCount(0);
					loadL();
				} catch (Throwable e1) {
					
					e1.printStackTrace();
				} 
            } else {
                try {
                	DefaultTableModel dm = (DefaultTableModel) table.getModel();
                	dm.setRowCount(0);
					loadByTreatment(treat[0].toUpperCase());
				} catch (Throwable e1) {
				
					e1.printStackTrace();
				}
            }
        }
    });
  }
  
  @SuppressWarnings("serial")
public void init()
{
    setForeground(new Color(0, 128, 128));
    setFont(new Font("Tahoma", 1, 13));
    setBounds(0, 0, 1216, 580);
    lblNewLabel = new CLabel();
    lblNewLabel.setFocusable(false);
    lblNewLabel.setForeground(new Color(6, 244, 0));
    lblNewLabel.setLineColor(new Color(255, 69, 6));
    lblNewLabel.setStrokeWidth(6);
    lblNewLabel.setText("Patient Details");
    lblNewLabel.setOpaque(false);
    lblNewLabel.setFont(new Font("Tahoma", 1, 24));
    lblNewLabel.setHorizontalAlignment(0);
    DropShadowBorder shadowborder = new DropShadowBorder();
    shadowborder.setShowLeftShadow(true);
    shadowborder.setShowRightShadow(true);
    shadowborder.setShowTopShadow(true);
    shadowborder.setShowBottomShadow(true);
    shadowborder.setShadowColor(Color.black);
    shadowborder.setShadowSize(15);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setOpaque(false);
    scrollPane.setBorder(shadowborder);
    searchpd = new CTextField();
    searchpd.setFont(new Font("Tahoma", 1, 14));
    searchpd.setFocusable(true);
    PromptSupport.setPrompt("Search...", searchpd);
    PromptSupport.setForeground(Color.BLACK, searchpd);
    PromptSupport.setFontStyle(Integer.valueOf(13), searchpd);
    searchpd.setColumns(10);
    
    DefaultTableModel tableModel = new DefaultTableModel(new Object[0][], new String[]{
            "S.No", "First Name", "Age", "Gender", "Contact", "Date"
    }) {
        boolean[] columnEditables = new boolean[6];

        @Override
        public boolean isCellEditable(int row, int column) {
            return columnEditables[column];
        }
    };
    table = new JTable(tableModel);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setBackground(new Color(255, 213, 0));
    table.getTableHeader().setForeground(new Color(0, 0, 0));
    table.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
    table.getTableHeader().setFont(new Font("Tahoma", 1, 12));
    table.setSelectionBackground(new Color(40, 253, 216));
    table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 30));
    table.setRowHeight(22);
    table.setOpaque(false);
    table.getColumnModel().getColumn(0).setResizable(true);
    table.getColumnModel().getColumn(0).setPreferredWidth(26);
    table.getColumnModel().getColumn(1).setResizable(true);
    table.getColumnModel().getColumn(1).setPreferredWidth(210);
    table.getColumnModel().getColumn(2).setResizable(true);
    table.getColumnModel().getColumn(2).setPreferredWidth(18);
    table.getColumnModel().getColumn(3).setResizable(true);
    table.getColumnModel().getColumn(3).setPreferredWidth(24);
    table.getColumnModel().getColumn(4).setResizable(true);
    table.getColumnModel().getColumn(4).setPreferredWidth(70);
    table.getColumnModel().getColumn(5).setResizable(true);
    table.getTableHeader().setReorderingAllowed(false);
    scrollPane.setViewportView(table);
    searchpd.addKeyListener(new KeyAdapter()
    {
      public void keyReleased(KeyEvent e) {
        DefaultTableModel dm = (DefaultTableModel)PatientDetails.table.getModel();
        TableRowSorter<DefaultTableModel> trs2 = new TableRowSorter(dm);
        PatientDetails.table.setRowSorter(trs2);
        String str = searchpd.getText().toUpperCase();
        if (str.length() == 0)
          trs2.setRowFilter(null);
        trs2.setRowFilter(RowFilter.regexFilter("(?i)" + str, new int[0]));
        searchpd.requestFocus();
      }
    });
    table.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e) {
        int[] row = PatientDetails.table.getSelectedRows();
        try {
          treatmentDetails.dispose();
          
          if (e.getClickCount() == 2) {
            for (int element : row)
            {
              DefaultTableModel dtm = (DefaultTableModel)PatientDetails.table.getModel();
              int mdr = PatientDetails.table.convertRowIndexToModel(element);
              String pname1 = dtm.getValueAt(mdr, 1).toString();
              String name = pname1;
              String phone = dtm.getValueAt(mdr, 4).toString();
              String cd = System.getProperty("user.dir");
              Class.forName("org.sqlite.JDBC");
              String url = "jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db";
              Connection con = DriverManager.getConnection(url);
              PatientDetails.ps = con.prepareStatement("select address from PTdata  where fname = '" + name + "' and phone = '" + phone + "'");
              PatientDetails.rs = PatientDetails.ps.executeQuery();
              String addr = null;
              if (PatientDetails.rs.next()) {
                addr = PatientDetails.rs.getString("address");
              }
              

              treatmentDetails.tdata(name, phone, addr);
              treatmentDetails.setVisible(true);
              con.close();
            }
          }
        }
        catch (Throwable e1) {
          System.err.println("Exception in tb2 : " + e1);
        }
      }
    });
    JLabel filepath = new JLabel("");
    filepath.setForeground(Color.BLUE);
    
    CLabel lblNewLabel_1 = new CLabel();
    lblNewLabel_1.setFocusable(false);
    lblNewLabel_1.setForeground(new Color(255, 255, 0));
    lblNewLabel_1.setFont(new Font("Tahoma", 1, 14));
    lblNewLabel_1.setText("Search");
    
    comboBox= new JComboBox<>();
    comboBox.setEditable(true);
    comboBox.addItem("Sort By");
    comboBox.setSelectedItem("Sort By");
    comboBox.addItem("Checkup");
    comboBox.addItem("Cleaning");
    comboBox.addItem("Filling");
    comboBox.addItem("Extraction");
   
    comboBox.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
    GroupLayout groupLayout = new GroupLayout(this);
    groupLayout.setHorizontalGroup(
    	groupLayout.createParallelGroup(Alignment.TRAILING)
    		.addGroup(groupLayout.createSequentialGroup()
    			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(96)
    					.addComponent(searchpd, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
    					.addPreferredGap(ComponentPlacement.RELATED)
    					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
    					.addGap(505))
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(400)
    					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)))
    			.addGap(28)
    			.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
    			.addGap(167))
    		.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
    			.addGap(66)
    			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
    			.addGap(89))
    );
    groupLayout.setVerticalGroup(
    	groupLayout.createParallelGroup(Alignment.LEADING)
    		.addGroup(groupLayout.createSequentialGroup()
    			.addGap(19)
    			.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
    			.addGap(52)
    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
    				.addComponent(searchpd, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
    				.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
    				.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
    			.addGap(11)
    			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
    			.addGap(41))
    );
    
    setLayout(groupLayout);
  }


}
