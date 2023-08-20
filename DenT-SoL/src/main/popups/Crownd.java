package main.popups;

import designs.CLabel;
import designs.CTextField;
import designs.MyScrollBarUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.formdev.flatlaf.ui.FlatScrollPaneUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Crownd extends JFrame
{
  public JFrame fr = new JFrame();
  
  private JPanel contentPane;
  
  private CTextField lname;
  
  private CTextField sdate;
  
  private CTextField edate;
  
  private CTextField ctype;
  
  private JTable table;
  
  private JLabel totalc;
  
  private ResultSet rs;
  
  private PreparedStatement ps;
  
  private Statement st;
  
  Date dt = new Date();
  
  SimpleDateFormat frmtr = new SimpleDateFormat("dd-MM-yyyy");
  
  String strd = frmtr.format(dt);
  
  Toolkit t = Toolkit.getDefaultToolkit();
  
  Dimension dim = new Dimension(t.getScreenSize());
  
  int height = (int)dim.getHeight();
  
  int width = (int)dim.getWidth();
  String cd = System.getProperty("user.dir");
  
  public void labdatal() throws Throwable {
    try {
      Class.forName("org.sqlite.JDBC");
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");
      String name = lname.getText().toUpperCase();
      String type = ctype.getText().toUpperCase();
      if ((type.isEmpty()) && (name.isEmpty())) {
        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = frmtr.parse(sdate.getText());
        String stdt = fr.format(sd);
        Date ed = frmtr.parse(edate.getText());
        String etdt = fr.format(ed);
        String q = "select sum(nunits) from uninfo where date between '" + stdt + "' and '" + etdt + "'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      } else if ((!edate.getText().isEmpty()) && (type.isEmpty()) && (name.isEmpty()) && (sdate.getText().isEmpty())) {
        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
        Date ed = frmtr.parse(edate.getText());
        String etdt = fr.format(ed);
        String q = "select sum(nunits) from uninfo where date <='" + etdt + "'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      }
      else if ((!edate.getText().isEmpty()) && (sdate.getText().isEmpty())) {
        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
        Date ed = frmtr.parse(edate.getText());
        String etdt = fr.format(ed);
        String q = "select sum(nunits) from uninfo where labname like '" + name + "%' and ctype like '" + type + "%' and date <='" + etdt + "'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      }
      else if ((type.isEmpty()) && (sdate.getText().isEmpty()) && (edate.getText().isEmpty())) {
        String q = "select sum(nunits) from uninfo where labname like '" + name + "%'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      } else if ((type.isEmpty()) && (sdate.getText().isEmpty())) {
        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
        Date ed = frmtr.parse(edate.getText());
        String etdt = fr.format(ed);
        String q = "select sum(nunits) from uninfo where labname like '" + name + "%' and date <= '" + etdt + "'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      }
      else if (type.isEmpty()) {
        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = frmtr.parse(sdate.getText());
        String stdt = fr.format(sd);
        Date ed = frmtr.parse(edate.getText());
        String etdt = fr.format(ed);
        String q = "select sum(nunits) from uninfo where labname  like '" + name + "%' and date between '" + stdt + "' and '" + etdt + "'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      } else if ((name.isEmpty()) && (sdate.getText().isEmpty()) && (edate.getText().isEmpty())) {
        String q = "select sum(nunits) from uninfo where  ctype like '" + type + "%'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      } else if (name.isEmpty()) {
        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = frmtr.parse(sdate.getText());
        String stdt = fr.format(sd);
        Date ed = frmtr.parse(edate.getText());
        String etdt = fr.format(ed);
        String q = "select sum(nunits) from uninfo where  ctype like '" + type + "%' and date between '" + stdt + "' and '" + etdt + "'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      } else if ((sdate.getText().isEmpty()) && (edate.getText().isEmpty())) {
        String q = "select sum(nunits) from uninfo where labname like '" + name + "%' and ctype like '" + type + "%'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      } else {
        SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
        Date sd = frmtr.parse(sdate.getText());
        String stdt = fr.format(sd);
        Date ed = frmtr.parse(edate.getText());
        String etdt = fr.format(ed);
        String q = "select sum(nunits) from uninfo where labname like '" + name + "%' and ctype like '" + type + "%' and date between '" + stdt + "' and '" + etdt + "'";
        ps = con.prepareStatement(q);
        rs = ps.executeQuery();
        int i = rs.getInt("sum(nunits)");
        totalc.setText(""+i);
        ps.close();
      }
      con.close();
    } catch (ParseException localParseException) {}
  }
  
  @SuppressWarnings("serial")
public Crownd() throws Throwable {
    fr.setType(java.awt.Window.Type.POPUP);
    fr.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
    fr.setAlwaysOnTop(true);
    fr.setIconImage(Toolkit.getDefaultToolkit().getImage(Crownd.class.getResource("/Images/ic1.png")));
    fr.setTitle("Dent-SoL");
    fr.setBounds(width - 775, 160, 721, 501);
    contentPane = new JPanel();
    contentPane.addMouseListener(new MouseAdapter() {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		 DefaultTableModel dm = (DefaultTableModel)table.getModel();
    		 dm.setRowCount(0);
    		 totalc.setText(""+0);
    		
    	}
    });
    contentPane.setBorder(new javax.swing.border.EtchedBorder(0, null, null));
    fr.setContentPane(contentPane);
    contentPane.setLayout(null);
    CLabel lblNewLabel = new CLabel();
    lblNewLabel.setLineColor(new Color(255, 0, 128));
    lblNewLabel.setStrokeWidth(5);
    lblNewLabel.setText("Aditya Dental");
    lblNewLabel.setForeground(new Color(0, 128, 128));
    lblNewLabel.setFont(new Font("Segoe UI Black", 1, 18));
    lblNewLabel.setHorizontalAlignment(0);
    lblNewLabel.setBounds(125, 10, 327, 36);
    JLabel lblNewLabel_1 = new JLabel("Lab Name");
    lblNewLabel_1.setForeground(new Color(0, 128, 128));
    lblNewLabel_1.setFont(new Font("Tahoma", 1, 14));
    lblNewLabel_1.setBounds(38, 70, 82, 30);
    lname = new CTextField();
    lname.setBounds(118, 70, 155, 30);
    lname.setColumns(10);
    JLabel lblNewLabel_11 = new JLabel("Date Range");
    lblNewLabel_11.setForeground(new Color(0, 128, 128));
    lblNewLabel_11.setFont(new Font("Tahoma", 1, 14));
    lblNewLabel_11.setBounds(75, 120, 82, 30);
    sdate = new CTextField();
    sdate.setBounds(168, 120, 90, 30);
    sdate.setColumns(10);
    PromptSupport.setPrompt("dd-MM-yyyy", sdate);
    JLabel lblNewLabel_1_1 = new JLabel("Crown Type");
    lblNewLabel_1_1.setForeground(new Color(0, 128, 128));
    lblNewLabel_1_1.setFont(new Font("Tahoma", 1, 14));
    lblNewLabel_1_1.setBounds(299, 70, 92, 30);
    ctype = new CTextField();
    ctype.setColumns(10);
    ctype.setBounds(401, 70, 155, 30);
    JLabel lblNewLabel_1_11 = new JLabel(" - ");
    lblNewLabel_1_11.setForeground(new Color(0, 128, 128));
    lblNewLabel_1_11.setFont(new Font("Stencil", 1, 20));
    lblNewLabel_1_11.setBounds(270, 120, 30, 30);
    edate = new CTextField();
    edate.setText(strd);
    edate.setColumns(10);
    edate.setBounds(301, 120, 95, 30);
    JButton getdtb = new JButton("Get Details");
    getdtb.setBackground(new Color(128, 255, 0));
    getdtb.setBorder(new CompoundBorder(new javax.swing.border.EtchedBorder(0, null, null), new BevelBorder(0, null, null, null, null)));
    getdtb.setForeground(new Color(255, 0, 0));
    getdtb.setFont(new Font("Segoe UI Black", 1, 13));
    getdtb.setBounds(216, 163, 143, 37);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null), new BevelBorder(0, null, null, null, null)));
    scrollPane.setBounds(5, 216, 599, 242);
    DefaultTableModel tableModel = new DefaultTableModel(new Object[0][], new String[] { "S.No", "Patient Name", "Type ", "Lab Name", "No.Crowns", "Date" }) {
        final boolean[] columnEditables = new boolean[6];
        
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
    table.setRowHeight(22);
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setBackground(Color.ORANGE);
    table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 30));
    table.getTableHeader().setFont(new Font("Tahoma", 1, 12));
    table.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
    table.setSelectionBackground(new Color(83, 250, 71));
    table.getColumnModel().getColumn(0).setPreferredWidth(40);
    table.getColumnModel().getColumn(1).setPreferredWidth(100);
    table.getColumnModel().getColumn(2).setPreferredWidth(100);
    table.getColumnModel().getColumn(3).setPreferredWidth(70);
    table.getColumnModel().getColumn(4).setPreferredWidth(60);
    table.getColumnModel().getColumn(5).setPreferredWidth(75);
    table.getColumnModel().getColumn(0).setResizable(true);
    table.getColumnModel().getColumn(1).setResizable(true);
    table.getColumnModel().getColumn(2).setResizable(true);
    table.getColumnModel().getColumn(3).setResizable(true);
    table.getColumnModel().getColumn(4).setResizable(true);
    table.getColumnModel().getColumn(5).setResizable(true);
    table.setAutoCreateRowSorter(false);
    table.getTableHeader().setReorderingAllowed(false);
    scrollPane.setViewportView(table);
    scrollPane.setUI(new FlatScrollPaneUI());
    scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
    scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());
    JLabel lblNewLabel_2 = new JLabel("Total");
    lblNewLabel_2.setForeground(new Color(34, 139, 34));
    lblNewLabel_2.setFont(new Font("Tahoma", 1, 12));
    lblNewLabel_2.setBounds(482, 173, 37, 26);
    totalc = new JLabel("");
    totalc.setHorizontalAlignment(0);
    totalc.setForeground(new Color(105, 1, 3));
    totalc.setFont(new Font("Tahoma", 1, 13));
    totalc.setBounds(524, 174, 54, 26);
    GroupLayout gl_contentPane = new GroupLayout(contentPane);
    gl_contentPane.setHorizontalGroup(
      gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(34)
      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(80)
      .addComponent(lname, -1, 206, 32767))
      .addComponent(lblNewLabel_1, -2, 82, -2))
      .addGap(26)
      .addComponent(lblNewLabel_1_1, -2, 92, -2)
      .addGap(10)
      .addComponent(ctype, -1, 207, 32767)
      .addGap(45))
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(71)
      .addComponent(lblNewLabel_11, -2, 82, -2)
      .addGap(11)
      .addComponent(sdate, -1, 142, 32767)
      .addGap(12)
      .addComponent(lblNewLabel_1_11, -2, 30, -2)
      .addGap(1)
      .addComponent(edate, -1, 146, 32767)
      .addGap(205))
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(212)
      .addComponent(getdtb, -1, 246, 32767)
      .addGap(123)
      .addComponent(lblNewLabel_2, -2, 37, -2)
      .addGap(5)
      .addComponent(totalc, -2, 54, -2)
      .addGap(23))
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(1)
      .addComponent(scrollPane, -1, 699, 32767))
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(121)
      .addComponent(lblNewLabel, -1, 430, 32767)
      .addGap(99)));
    
    gl_contentPane.setVerticalGroup(
      gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(6)
      .addComponent(lblNewLabel, -2, 42, -2)
      .addGap(18)
      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(lname, -2, 30, -2)
      .addComponent(lblNewLabel_1, -2, 30, -2)
      .addComponent(lblNewLabel_1_1, -2, 30, -2)
      .addComponent(ctype, -2, 30, -2))
      .addGap(20)
      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(lblNewLabel_11, -2, 30, -2)
      .addComponent(sdate, -2, 30, -2)
      .addComponent(lblNewLabel_1_11, -2, 30, -2)
      .addComponent(edate, -2, 30, -2))
      .addGap(13)
      .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(getdtb, -2, 37, -2)
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(10)
      .addComponent(lblNewLabel_2, -2, 26, -2))
      .addGroup(gl_contentPane.createSequentialGroup()
      .addGap(11)
      .addComponent(totalc, -2, 26, -2)))
      .addGap(16)
      .addComponent(scrollPane, -1, 245, 32767)));
    
    contentPane.setLayout(gl_contentPane);
    getdtb.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try {
          Class.forName("org.sqlite.JDBC");
          Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");
          String name = lname.getText().toUpperCase();
          String type = ctype.getText().toUpperCase();
          DefaultTableModel dm = (DefaultTableModel)table.getModel();
          try {
            if ((name.isEmpty()) && (type.isEmpty())) {
              SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
              Date sd = frmtr.parse(sdate.getText());
              String stdt = sfr.format(sd);
              Date ed = frmtr.parse(edate.getText());
              String etdt = sfr.format(ed);
              String queri = "select * from uninfo where  date between '" + stdt + "' and '" + etdt + "' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                Date datefr = sfr.parse(dt);
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            } else if ((!edate.getText().isEmpty()) && (name.isEmpty()) && (type.isEmpty()) && (sdate.getText().isEmpty())) {
              SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
              Date ed = frmtr.parse(edate.getText());
              String etdt = sfr.format(ed);
              String queri = "select * from uninfo where  date <= '" + etdt + "' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                Date datefr = sfr.parse(dt);
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            } else if ((type.isEmpty()) && (sdate.getText().isEmpty()) && (edate.getText().isEmpty())) {
              String queri = "select * from uninfo where labname like '" + name + "%' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
                Date datefr = sfr.parse(dt);
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            } else if ((!edate.getText().isEmpty()) && (type.isEmpty()) && (sdate.getText().isEmpty())) {
              SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
              Date ed = frmtr.parse(edate.getText());
              String etdt = sfr.format(ed);
              String queri = "select * from uninfo where labname like '" + name + "%' and date <= '" + etdt + "' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfr1 = new SimpleDateFormat("yyyy-MM-dd");
                Date datefr = sfr1.parse(dt);
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            } else if ((!edate.getText().isEmpty()) && (sdate.getText().isEmpty())) {
              SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
              Date ed = frmtr.parse(edate.getText());
              String etdt = sfr.format(ed);
              String queri = "select * from uninfo where labname like '" + name + "%' and date <= '" + etdt + "' and ctype like '" + type + "%' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfr1 = new SimpleDateFormat("yyyy-MM-dd");
                Date datefr = sfr1.parse(dt);
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            } else if (type.isEmpty()) {
              SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
              Date sd = frmtr.parse(sdate.getText());
              String stdt = fr.format(sd);
              Date ed = frmtr.parse(edate.getText());
              String etdt = fr.format(ed);
              String queri = "select * from uninfo where labname like '" + name + "%' and date between '" + stdt + "' and '" + etdt + "' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
                Date datefr = sfr.parse(dt);
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            } else if ((name.isEmpty()) && (sdate.getText().isEmpty()) && (edate.getText().isEmpty())) {
              String queri = "select * from uninfo where  ctype like '" + type + "%' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
                Date datefr = sfr.parse(dt);
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            } else if (name.isEmpty()) {
              SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
              Date sd = frmtr.parse(sdate.getText());
              String stdt = fr.format(sd);
              Date ed = frmtr.parse(edate.getText());
              String etdt = fr.format(ed);
              String queri = "select * from uninfo where  ctype like '" + type + "%' and date between '" + stdt + "' and '" + etdt + "' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
                Date datefr = sfr.parse(dt);
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            } else if ((sdate.getText().isEmpty()) && (edate.getText().isEmpty())) {
              String queri = "select * from uninfo where labname like '" + name + "%' and  ctype like '" + type + "%' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
                Date datefr = sfr.parse(dt);
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            }
            else {
              SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
              Date sd = frmtr.parse(sdate.getText());
              String stdt = fr.format(sd);
              Date ed = frmtr.parse(edate.getText());
              String etdt = fr.format(ed);
              String queri = "select * from uninfo where labname like '" + name + "%' and  ctype like '" + type + "%' and date between '" + stdt + "' and '" + etdt + "' order by date";
              st = con.createStatement();
              rs = st.executeQuery(queri);
              dm.setRowCount(0);
              int SNo = 0;
              while (rs.next()) {
                SNo++;
                String ptname = rs.getString("ptname");
                String lbname = rs.getString("labname");
                String ctype = rs.getString("ctype");
                int nunits = rs.getInt("nunits");
                String dt = rs.getString("date");
                SimpleDateFormat sfr = new SimpleDateFormat("yyyy-MM-dd");
                Date datefr = sfr.parse(dt);
                SimpleDateFormat sfrr = new SimpleDateFormat("dd-MMM-yyyy");
                String date = sfrr.format(datefr);
                Object[] bo = { Integer.valueOf(SNo), ptname, ctype, lbname, Integer.valueOf(nunits), date };
                dm.insertRow(0, bo);
              }
            }
            labdatal();
            st.close();
          } catch (ParseException localParseException) {}
          con.close();
        } catch (Throwable ea) {
          ea.printStackTrace();
        }
      }
    });
    sdate.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        String dt = sdate.getText();
        sdate.setEditable(true);
        if ((dt.length() == 2) || (dt.length() == 5)) {
          sdate.setText(dt + "-");
          if (e.getKeyCode() == 8)
            sdate.setText(null);
        } else if (dt.length() >= 10) {
          sdate.setEditable(false);
          if (e.getKeyCode() == 8)
            sdate.setEditable(true);
        }
      }
    });
    edate.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e) {
        String dt = edate.getText();
        if ((dt.length() == 2) || (dt.length() == 5)) {
          edate.setText(dt + "-");
          if (e.getKeyCode() == 8)
            edate.setText(null);
        } else if (dt.length() >= 10) {
          edate.setEditable(false);
          if (e.getKeyCode() == 8) {
            edate.setEditable(true);
          }
        }
      }
    });
  }
}
