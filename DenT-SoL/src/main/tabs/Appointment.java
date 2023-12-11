package main.tabs;

import designs.CLabel;
import designs.CTextField;
import designs.MyScrollBarUI;
import gradientpane.Imagbg;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import main.MainFrame;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.formdev.flatlaf.ui.FlatScrollPaneUI;




@SuppressWarnings("serial")
public class Appointment
  extends Imagbg
{
  static JTable table;
  CTextField pnamef;
  CTextField pagef;
  CTextField genderf;
  CTextField pphonef;
  CTextField datef;
  public CLabel lblNewLabel;
  private static ResultSet rs;
  private static Statement st;
  Date dt = new Date();
  

  static SimpleDateFormat frmtr = new SimpleDateFormat("dd/MM/yyyy");
  

  String strd = frmtr.format(dt);
  
  Toolkit t = Toolkit.getDefaultToolkit();
  
  Dimension dim = new Dimension(t.getScreenSize());
  
  int height = (int)dim.getHeight();
  
  int width = (int)dim.getWidth();
  private CTextField searchf;
  private CTextField apdatef;
  static CTextField pidf;
  
  public static void loadd()
    throws SQLException
  {
    try
    {
      String cd = System.getProperty("user.dir");
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
      String queri = "select * from apnt order by date";
      st = con.createStatement();
      rs = st.executeQuery(queri);
      int SNo = 0;
      while (rs.next()) {
        SNo++;
        String pid = rs.getString("pid");
        String ptname = rs.getString("pname");
        String age = rs.getString("age");
        String gender = rs.getString("gender");
        String phone = rs.getString("phone");
        String date = rs.getString("date");
        String apdate = rs.getString("apdate");
        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = fr.parse(date);
        String stdt = frmtr.format(sd);
        Date asd = fr.parse(apdate);
        String apstdt = frmtr.format(asd);
        Object[] bo = { Integer.valueOf(SNo), pid, ptname, age, gender, phone, stdt, apstdt };
        DefaultTableModel dm = (DefaultTableModel)table.getModel();
        dm.insertRow(0, bo);
      }
      con.close();
    } catch (Throwable ea) {
      ea.printStackTrace();
    }
    try {
      MainFrame.apts();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
  
  public void indata() throws Throwable
  {
    String cd = System.getProperty("user.dir");
    Class.forName("org.sqlite.JDBC");
    Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
    SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
    Date sd = frmtr.parse(datef.getText());
    String date = fr.format(sd);
    String a = apdatef.getText();
    Date asd = frmtr.parse(a);
    String apstd = fr.format(asd);
    PreparedStatement ps = con.prepareStatement("insert into apnt(pid,pname, age, gender, phone, date, apdate)values(?,?,?,?,?,?,?)");
    ps.setString(1, pidf.getText());
    ps.setString(2, pnamef.getText().toUpperCase());
    ps.setString(3, pagef.getText().toUpperCase());
    ps.setString(4, genderf.getText().toUpperCase());
    ps.setString(5, pphonef.getText().toUpperCase());
    ps.setString(6, date);
    ps.setString(7, apstd);
    ps.executeUpdate();
    ps.close();
    con.close();
  }
  
  public void reset() {
    pnamef.setText(null);
    pagef.setText(null);
    genderf.setText(null);
    pphonef.setText(null);
    datef.setText(strd);
    apdatef.setText(null);
    pnamef.requestFocus();
  }
  
  public static void id() throws Throwable {
	    String cd = System.getProperty("user.dir");
	    Class.forName("org.sqlite.JDBC");
	    Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
	    
	    // Fetch the highest numeric value used so far in the "PTdata" table
	    long highestNumericValue = 0;
	    String highestNumericSql = "SELECT MAX(CAST(SUBSTR(pid, 3) AS INTEGER)) FROM apnt";
	    Statement highestNumericStmt = con.createStatement();
	    ResultSet highestNumericRs = highestNumericStmt.executeQuery(highestNumericSql);
	    
	    if (highestNumericRs.next()) {
	        highestNumericValue = highestNumericRs.getLong(1);
	    }
	    
	    highestNumericStmt.close();
	    highestNumericRs.close();
	    
	    long startingValue = Math.max(1, highestNumericValue + 1); // Start from the highest numeric value + 1
	    
	    // Limit the value to prevent exceeding the upper limit
	    long upperLimit = 1000000000000000001L;
	    long newValue = Math.min(startingValue, upperLimit);
	    
	    String newId = "AD" + newValue;
	    pidf.setText(newId);
	    
	    con.close();
	}

 
  
  public Appointment() throws Throwable {
    init();
    loadd();
    MainFrame.apts();
    id();
  }
  
  public void init() throws Throwable
  {
    setFont(new Font("Tahoma", 1, 13));
    setBounds(0, 0, 1240, 621);
    lblNewLabel = new CLabel();
    lblNewLabel.setFocusable(false);
    lblNewLabel.setLineColor(new Color(255, 255, 0));
    lblNewLabel.setStrokeWidth(5);
    lblNewLabel.setText("APPOINTMENTS");
    lblNewLabel.setForeground(new Color(255, 82, 32));
    lblNewLabel.setOpaque(false);
    lblNewLabel.setFont(new Font("Tahoma", 1, 24));
    lblNewLabel.setHorizontalAlignment(0);
    CLabel lblNewLabel_1 = new CLabel();
    lblNewLabel_1.setFocusable(false);
    lblNewLabel_1.setLineColor(new Color(255, 0, 0));
    lblNewLabel_1.setStrokeWidth(4);
    lblNewLabel_1.setText("Add Appointment");
    lblNewLabel_1.setForeground(new Color(0, 255, 255));
    lblNewLabel_1.setOpaque(false);
    lblNewLabel_1.setFont(new Font("Tahoma", 1, 17));
    lblNewLabel_1.setHorizontalAlignment(0);
    DropShadowBorder shadowborder = new DropShadowBorder();
    shadowborder.setShowLeftShadow(true);
    shadowborder.setShowRightShadow(true);
    shadowborder.setShowTopShadow(true);
    shadowborder.setShowBottomShadow(true);
    shadowborder.setShadowColor(Color.white);
    shadowborder.setShadowSize(10);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setFocusTraversalKeysEnabled(false);
    scrollPane.setFocusable(false);
    scrollPane.setOpaque(false);
    scrollPane.setBorder(shadowborder);
    JLabel lblfname = new JLabel("Patient Name");
    lblfname.setFocusTraversalKeysEnabled(false);
    lblfname.setFocusable(false);
    lblfname.setFont(new Font("Tahoma", 1, 14));
    lblfname.setForeground(new Color(231, 255, 132));
    pnamef = new CTextField();
    pnamef.setFont(new Font("Tahoma", 1, 12));
    pnamef.setColumns(10);
    pnamef.requestFocus();
    JLabel lblage = new JLabel("Age");
    lblage.setFocusable(false);
    lblage.setFocusTraversalKeysEnabled(false);
    lblage.setForeground(new Color(255, 255, 128));
    lblage.setFont(new Font("Tahoma", 1, 14));
    pagef = new CTextField();
    pagef.setFont(new Font("Tahoma", 1, 12));
    pagef.setColumns(10);
    JLabel lblgender = new JLabel("Gender");
    lblgender.setFocusable(false);
    lblgender.setFocusTraversalKeysEnabled(false);
    lblgender.setForeground(new Color(255, 255, 128));
    lblgender.setFont(new Font("Tahoma", 1, 14));
    genderf = new CTextField();
    genderf.setHorizontalAlignment(0);
    PromptSupport.setPrompt(" M/F ", genderf);
    genderf.setFont(new Font("Tahoma", 1, 12));
    genderf.setColumns(10);
    JLabel lblphone = new JLabel("Contact");
    lblphone.setFocusable(false);
    lblphone.setFocusTraversalKeysEnabled(false);
    lblphone.setForeground(new Color(255, 255, 128));
    lblphone.setFont(new Font("Tahoma", 1, 14));
    pphonef = new CTextField();
    pphonef.setFont(new Font("Tahoma", 1, 12));
    pphonef.setColumns(10);
    JLabel lbldate = new JLabel("Date");
    lbldate.setFocusTraversalKeysEnabled(false);
    lbldate.setFocusable(false);
    lbldate.setForeground(new Color(255, 255, 128));
    lbldate.setFont(new Font("Tahoma", 1, 14));
    datef = new CTextField();
    datef.setForeground(new Color(255, 140, 0));
    datef.setText(strd);
    datef.setFont(new Font("Tahoma", 1, 12));
    datef.setColumns(10);
    JLabel lbltreatment = new JLabel("Appointment Date");
    lbltreatment.setFocusTraversalKeysEnabled(false);
    lbltreatment.setFocusable(false);
    lbltreatment.setForeground(new Color(255, 255, 128));
    lbltreatment.setFont(new Font("Tahoma", 1, 14));
    JButton btnNewButton = new JButton("Add");
    btnNewButton.setForeground(new Color(133, 254, 203));
    btnNewButton.setBorder(new CompoundBorder(new EtchedBorder(0, null, null), new BevelBorder(0, null, null, null, null)));
    btnNewButton.setOpaque(false);
    btnNewButton.setBackground(new Color(0, 100, 0));
    btnNewButton.setFont(new Font("Tahoma", 1, 13));
    JButton btnUpdate = new JButton("Update");
    btnUpdate.setForeground(new Color(128, 255, 255));
    btnUpdate.setBorder(new CompoundBorder(new EtchedBorder(0, null, null), new BevelBorder(0, null, null, null, null)));
    btnUpdate.setOpaque(false);
    btnUpdate.setBackground(new Color(0, 255, 255));
    btnUpdate.setFont(new Font("Tahoma", 1, 13));
    JButton btnReset = new JButton("Reset");
    btnReset.setForeground(new Color(128, 255, 255));
    btnReset.setBorder(new CompoundBorder(new EtchedBorder(0, null, null), new BevelBorder(0, null, null, null, null)));
    btnReset.setOpaque(false);
    btnReset.setBackground(new Color(255, 0, 255));
    btnReset.setFont(new Font("Tahoma", 1, 13));
    JButton btnModify = new JButton("Modify");
    btnModify.setForeground(new Color(128, 255, 255));
    btnModify.setBorder(new CompoundBorder(new CompoundBorder(new EtchedBorder(0, null, null), new BevelBorder(0, null, null, null, null)), null));
    btnModify.setOpaque(false);
    btnModify.setBackground(Color.CYAN);
    btnModify.setFont(new Font("Tahoma", 1, 13));
    JButton btnRemove = new JButton("Remove");
    btnRemove.setBorder(new CompoundBorder(new EtchedBorder(0, null, null), new BevelBorder(0, null, null, null, null)));
    btnRemove.setOpaque(false);
    btnRemove.setBackground(Color.RED);
    btnRemove.setForeground(Color.RED);
    btnRemove.setFont(new Font("Tahoma", 1, 13));
    searchf = new CTextField();
    searchf.setFont(new Font("Tahoma", 1, 13));
    PromptSupport.setPrompt("Search ...", searchf);
    PromptSupport.setForeground(Color.BLACK, searchf);
    PromptSupport.setFontStyle(Integer.valueOf(13), searchf);
    searchf.setLineColor(new Color(0, 255, 64));
    searchf.setColumns(10);
    CLabel lblNewLabel_3 = new CLabel();
    lblNewLabel_3.setFocusTraversalKeysEnabled(false);
    lblNewLabel_3.setFocusable(false);
    lblNewLabel_3.setLineColor(new Color(255, 0, 0));
    lblNewLabel_3.setText("Search");
    lblNewLabel_3.setForeground(new Color(255, 255, 0));
    lblNewLabel_3.setFont(new Font("Tahoma", 1, 13));
    apdatef = new CTextField();
    apdatef.setFont(new Font("Tahoma", 1, 12));
    apdatef.setForeground(new Color(255, 140, 0));
    PromptSupport.setPrompt("dd/MM/yyyy", apdatef);
    apdatef.setColumns(10);
    DefaultTableModel tableModel = new DefaultTableModel(new Object[0][], new String[] { "SNo.", "P_ID", "Name", "Age", "Gender", "Phone", "Date", "Appointment Date" }) {
        final boolean[] columnEditables = new boolean[8];
        
        public boolean isCellEditable(int row, int column)
        {
          return columnEditables[column];
        }
      };
    table = new JTable(tableModel);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    table.setRowHeight(26);
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setBackground(new Color(255, 213, 0));
    table.getTableHeader().setForeground(new Color(0, 0, 0));
    table.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
    table.getTableHeader().setFont(new Font("Tahoma", 1, 12));
    table.setSelectionBackground(new Color(40, 253, 216));
    table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 30));
    scrollPane.setViewportView(table); 
    table.getColumnModel().getColumn(0).setPreferredWidth(35);
    table.getColumnModel().getColumn(0).setResizable(true);
    table.getColumnModel().getColumn(1).setResizable(true);
    table.getColumnModel().getColumn(1).setPreferredWidth(42);
    table.getColumnModel().getColumn(2).setResizable(true);
    table.getColumnModel().getColumn(2).setPreferredWidth(114);
    table.getColumnModel().getColumn(3).setPreferredWidth(23);
    table.getColumnModel().getColumn(3).setResizable(true);
    table.getColumnModel().getColumn(4).setPreferredWidth(24);
    table.getColumnModel().getColumn(4).setResizable(true);
    table.getColumnModel().getColumn(5).setPreferredWidth(80);
    table.getColumnModel().getColumn(5).setResizable(true);
    table.getColumnModel().getColumn(6).setPreferredWidth(66);
    table.getColumnModel().getColumn(6).setResizable(true);
    table.getColumnModel().getColumn(7).setPreferredWidth(100);
    table.getColumnModel().getColumn(7).setResizable(true);
   
    table.getTableHeader().setReorderingAllowed(false);
    scrollPane.setViewportView(table);
    scrollPane.setOpaque(false);
    scrollPane.setUI(new FlatScrollPaneUI());
    scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
    scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());
    searchf.addKeyListener(new KeyAdapter()
    {
      public void keyReleased(KeyEvent e) {
        DefaultTableModel dm = (DefaultTableModel)Appointment.table.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter(dm);
        Appointment.table.setRowSorter(trs);
        String str = searchf.getText().toUpperCase();
        if (str.length() == 0)
          trs.setRowFilter(null);
        trs.setRowFilter(RowFilter.regexFilter("(?i)" + str, new int[0]));
        searchf.requestFocus();
      }
    });
    pnamef.addKeyListener(new KeyAdapter()
    {
      @SuppressWarnings({ "unchecked", "rawtypes" })
	public void keyReleased(KeyEvent e) {
        int[] row = Appointment.table.getSelectedRows();
        DefaultTableModel dm2 = (DefaultTableModel)Appointment.table.getModel();
        String id = null;
        for (int element : row) {
          int mdr = Appointment.table.convertRowIndexToModel(element);
          id = dm2.getValueAt(mdr, 1).toString();
        }
        if (id == null) {
          DefaultTableModel dm = (DefaultTableModel)Appointment.table.getModel();
          Object trs = new TableRowSorter(dm);
          Appointment.table.setRowSorter((RowSorter)trs);
          String str = pnamef.getText().toUpperCase();
          if (str.length() == 0)
            ((TableRowSorter)trs).setRowFilter(null);
          ((TableRowSorter)trs).setRowFilter(RowFilter.regexFilter("(?i)" + str, new int[0]));
          pnamef.requestFocus();
        } else { id.equals(Appointment.pidf.getText());
        }
        
      }
      

    });
    btnNewButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        int[] row = Appointment.table.getSelectedRows();
        DefaultTableModel dm2 = (DefaultTableModel)Appointment.table.getModel();
        String id = null;
        for (int element : row) {
          int mdr = Appointment.table.convertRowIndexToModel(element);
          id = dm2.getValueAt(mdr, 1).toString();
        }
        if (id == null) {
          try {
            if (!pnamef.getText().isEmpty()) {
              indata();
              DefaultTableModel dm = (DefaultTableModel)Appointment.table.getModel();
              dm.setRowCount(0);
              Appointment.loadd();
              Appointment.id();
            }
          } catch (Throwable e1) {
            e1.printStackTrace();
          }
          reset();
          pnamef.requestFocus();
        } else { id.equals(Appointment.pidf.getText());
        }
        
      }
    });
    btnNewButton.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          int[] row = Appointment.table.getSelectedRows();
          DefaultTableModel dm2 = (DefaultTableModel)Appointment.table.getModel();
          String id = null;
          for (int element : row) {
            int mdr = Appointment.table.convertRowIndexToModel(element);
            id = dm2.getValueAt(mdr, 1).toString();
          }
          if (id == null) {
            try {
              if (!pnamef.getText().isEmpty()) {
                indata();
                DefaultTableModel dm = (DefaultTableModel)Appointment.table.getModel();
                dm.setRowCount(0);
                Appointment.loadd();
                Appointment.id();
              }
            } catch (Throwable e1) {
              e1.printStackTrace();
            }
            reset();
          } else { id.equals(Appointment.pidf.getText());
          }
          
          pnamef.requestFocus();
        }
      }
    });
    btnModify.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        try {
          int[] row = Appointment.table.getSelectedRows();
          DefaultTableModel dm2 = (DefaultTableModel)Appointment.table.getModel();
          if (Appointment.table.getSelectedRowCount() != 0) {
            Appointment.table.disable();
            for (int element : row) {
              int mdr = Appointment.table.convertRowIndexToModel(element);
              Appointment.pidf.setText(dm2.getValueAt(mdr, 1).toString());
              pnamef.setText(dm2.getValueAt(mdr, 2).toString());
              pagef.setText(dm2.getValueAt(mdr, 3).toString());
              pphonef.setText(dm2.getValueAt(mdr, 5).toString());
              genderf.setText(dm2.getValueAt(mdr, 4).toString());
              
              apdatef.setText(dm2.getValueAt(mdr, 7).toString());
            }
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    btnUpdate.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        Appointment.table.enable();
        PreparedStatement pst = null;
        try {
          String cd = System.getProperty("user.dir");
          Class.forName("org.sqlite.JDBC");
          Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
          TableModel dms = Appointment.table.getModel();
          int[] row = Appointment.table.getSelectedRows();
          if (Appointment.table.getSelectedRowCount() != 0)
            for (int element : row) {
              int mdr = Appointment.table.convertRowIndexToModel(element);
              String pname1 = dms.getValueAt(mdr, 1).toString();
              SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
              Date sd = Appointment.frmtr.parse(datef.getText());
              String date = fr.format(sd);
              Date asd = Appointment.frmtr.parse(apdatef.getText());
              String adate = fr.format(asd);
              if (!pnamef.getText().isEmpty()) {
                String query = "UPDATE apnt set pname=?, age=?,  gender=?, phone=?, date=?, apdate=? where pid='" + pname1 + "'";
                pst = con.prepareStatement(query);
                pst.setString(1, pnamef.getText().toUpperCase());
                pst.setString(2, pagef.getText());
                pst.setString(3, genderf.getText().toUpperCase());
                pst.setString(4, pphonef.getText());
                pst.setString(5, date);
                pst.setString(6, adate);
                pst.executeUpdate();
                DefaultTableModel dm = (DefaultTableModel)Appointment.table.getModel();
                dm.setRowCount(0);
                Appointment.loadd();
                Appointment.id();
                dm.fireTableDataChanged();
                reset();
              }
            }
          con.close();
        } catch (Throwable ex) {
          ex.printStackTrace();
        }
        pnamef.requestFocus();
      }
    });
    addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e) {
        Appointment.table.clearSelection();
        try {
          Appointment.id();
        } catch (Throwable ex) {
          throw new RuntimeException(ex);
        }
      }
    });
    btnReset.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        reset();
        try {
          Appointment.id();
        } catch (Throwable ex) {
          throw new RuntimeException(ex);
        }
        pnamef.requestFocus();
        Appointment.table.clearSelection();
        Appointment.table.enable();
      }
    });
    btnRemove.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
    	  try {
    		  String cd = System.getProperty("user.dir");
	    	    Class.forName("org.sqlite.JDBC");
	    	    Connection con1 = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
	    	    PreparedStatement ps = con1.prepareStatement("Select * from admin");
	    	    ResultSet rs = ps.executeQuery();
	    	    String pass = null;
	    	    if(rs.next()) {
	    	    	pass = rs.getString(1);
	    	    }
        
        TableModel dmd = Appointment.table.getModel();
        int[] row = Appointment.table.getSelectedRows();
        if (Appointment.table.getSelectedRowCount() != 0) {
          String op = JOptionPane.showInputDialog(null, "Enter Passcode ", "Confirm to delete", 2);
          try {
            if (!op.equals(null))
              if (op.equalsIgnoreCase(pass)) {
                try {
                 
                  Class.forName("org.sqlite.JDBC");
                  Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
                  for (int element : row) {
                    int mdr = Appointment.table.convertRowIndexToModel(element);
                    String pname1 = dmd.getValueAt(mdr, 1).toString();
                    String query = "delete from apnt where pid ='" + pname1 + "'";
                    ps = con.prepareStatement(query);
                    ps.executeUpdate();
                    DefaultTableModel dm1 = (DefaultTableModel)Appointment.table.getModel();
                    dm1.setRowCount(0);
                    Appointment.loadd();
                    Appointment.id();
                  }
                  con.close();
                } catch (Throwable ex) {
                  ex.printStackTrace();
                }
              }
              else {
                JOptionPane.showConfirmDialog(null, "Wrong Passcode ,Try Again ", "Error", -1);
              }
          } catch (NullPointerException localNullPointerException) {}
        }
    	  }catch(Throwable a) {}
      }
    });
    pphonef.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        String phone = pphonef.getText();
        char c = e.getKeyChar();
        if ((Character.isLetter(c)) || (phone.length() > 9)) {
          pphonef.setEditable(false);
          if (e.getKeyCode() == 8)
            pphonef.setEditable(true);
        } else {
          pphonef.setEditable(true);
        }
      }
    });
    pagef.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isLetter(c)) {
          pagef.setEditable(false);
          if (e.getKeyCode() == 8)
            pagef.setEditable(true);
        } else {
          pagef.setEditable(true);
        }
      }
    });
    pidf = new CTextField();
    pidf.setFocusable(false);
    pidf.setLineColor(new Color(255, 0, 128));
    pidf.setFont(new Font("Tahoma", 1, 11));
    pidf.setColumns(10);
    pidf.setEditable(false);
    apdatef.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        String dt = apdatef.getText();
        if ((dt.length() == 2) || (dt.length() == 5)) {
          apdatef.setText(dt + "/");
          if (e.getKeyCode() == 8)
            apdatef.setText(null);
        } else if (dt.length() >= 10) {
          apdatef.setEditable(false);
          if (e.getKeyCode() == 8) {
            apdatef.setEditable(true);
          }
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER|| e.getKeyCode()==KeyEvent.VK_TAB) {
        	btnNewButton.requestFocus();
        }
      }
    });
    pnamef.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
          genderf.requestFocus();
        }
        
      }
    });
    genderf.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
          pagef.requestFocus();
        }
      }
    });
    pagef.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
          datef.requestFocus();
        }
      }
    });
    datef.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
          pphonef.requestFocus();
        }
      }
    });
    pphonef.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
          apdatef.requestFocus();
        }
        
      }
      

    });
    GroupLayout groupLayout = new GroupLayout(this);
    groupLayout.setHorizontalGroup(
    	groupLayout.createParallelGroup(Alignment.LEADING)
    		.addGroup(groupLayout.createSequentialGroup()
    			.addGap(39)
    			.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
    			.addGap(65)
    			.addComponent(searchf, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
    			.addGap(2)
    			.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
    			.addGap(552))
    		.addGroup(groupLayout.createSequentialGroup()
    			.addGap(27)
    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    				.addComponent(lblfname, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
    				.addComponent(lblage, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
    				.addComponent(lbldate, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
    				.addComponent(lblphone, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
    				.addComponent(lbltreatment, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
    			.addGap(2)
    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    				.addComponent(pnamef, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
    				.addGroup(groupLayout.createSequentialGroup()
    					.addComponent(pagef, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
    					.addGap(10)
    					.addComponent(lblgender, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
    					.addGap(4)
    					.addComponent(genderf, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
    				.addComponent(datef, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
    				.addComponent(pphonef, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(2)
    					.addComponent(apdatef, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE))
    				.addComponent(pidf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    			.addGap(39)
    			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
    			.addGap(10))
    		.addGroup(groupLayout.createSequentialGroup()
    			.addGap(39)
    			.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
    			.addGap(18)
    			.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
    			.addGap(18)
    			.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
    			.addGap(114)
    			.addComponent(btnModify, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
    			.addGap(339)
    			.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
    		.addGroup(groupLayout.createSequentialGroup()
    			.addGap(459)
    			.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
    			.addGap(328))
    );
    groupLayout.setVerticalGroup(
    	groupLayout.createParallelGroup(Alignment.LEADING)
    		.addGroup(groupLayout.createSequentialGroup()
    			.addContainerGap()
    			.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
    			.addGap(33)
    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    				.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(32)
    					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    						.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
    						.addComponent(searchf, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))))
    			.addGap(7)
    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(57)
    					.addComponent(lblfname, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
    					.addGap(24)
    					.addComponent(lblage, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
    					.addGap(28)
    					.addComponent(lbldate, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
    					.addGap(12)
    					.addComponent(lblphone, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
    					.addGap(27)
    					.addComponent(lbltreatment, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
    				.addGroup(groupLayout.createSequentialGroup()
    					.addPreferredGap(ComponentPlacement.RELATED)
    					.addComponent(pidf, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
    					.addGap(18)
    					.addComponent(pnamef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
    					.addGap(18)
    					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    						.addComponent(pagef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
    						.addGroup(groupLayout.createSequentialGroup()
    							.addGap(1)
    							.addComponent(lblgender, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
    						.addComponent(genderf, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
    					.addGap(20)
    					.addComponent(datef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
    					.addGap(11)
    					.addComponent(pphonef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
    					.addGap(22)
    					.addComponent(apdatef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(4)
    					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)))
    			.addGap(6)
    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
    				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
    				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
    					.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
    					.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(8)
    					.addComponent(btnModify, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
    				.addGroup(groupLayout.createSequentialGroup()
    					.addGap(7)
    					.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
    			.addGap(17))
    );
    setLayout(groupLayout);
  }


}
