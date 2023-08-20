package main.popups;

import designs.CLabel;
import designs.MyScrollBarUI;
import main.tabs.PatientDetails;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import javax.swing.table.TableModel;

import org.jdesktop.swingx.border.DropShadowBorder;

import com.formdev.flatlaf.ui.FlatScrollPaneUI;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Crowndetails extends JFrame
{
  private JPanel contentPane;
  private JTable table;
  private final CLabel lblNewLabel_1;
  Toolkit t = Toolkit.getDefaultToolkit();
  
  Dimension dim = new Dimension(t.getScreenSize());
  
  int width = (int)dim.getWidth();
  String cd = System.getProperty("user.dir");
  
  public void lcdata(String id, String name) throws Throwable {
    Class.forName("org.sqlite.JDBC");
    Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");
    String queri = "select * from uninfo where pid='" + id + "' order by date ";
    DefaultTableModel dm = (DefaultTableModel)table.getModel();
    lblNewLabel_1.setText(name);
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(queri);
    dm.setRowCount(0);
    while (rs.next()) {
    	int mid = rs.getInt("id");
      String vdate = rs.getString("date");
      String vunts = rs.getString("units");
      int nounits = rs.getInt("nunits");
      String ctype = rs.getString("ctype");
      String lname = rs.getString("labname");
      String rvdate = rs.getString("rvdate");
      SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat frmtr = new SimpleDateFormat("dd-MMM-yyyy");
      Date sd = fr.parse(vdate);
      String rdt = null;
      if(!(rvdate==null)) {
    	  Date rd = fr.parse(rvdate);
      	rdt = frmtr.format(rd);
      }
      String date = frmtr.format(sd);
      Object[] bo = {mid, date, vunts, ctype, lname, Integer.valueOf(nounits),rdt };
      dm.insertRow(0, bo);
    }
    con.close();
  }
  
  @SuppressWarnings("serial")
public Crowndetails() {
    setType(Type.POPUP);
    setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
    setTitle("units");
    setAlwaysOnTop(true);
    setIconImage(Toolkit.getDefaultToolkit().getImage(Crowndetails.class.getResource("/Images/ic1.png")));
    setDefaultCloseOperation(2);
    setBounds(width - 900, 189, 894, 444);
    contentPane = new JPanel();
    contentPane.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null), new BevelBorder(0, null, null, null, null)));
    setContentPane(contentPane);
    lblNewLabel_1 = new CLabel();
    lblNewLabel_1.setLineColor(new Color(255, 0, 0));
    lblNewLabel_1.setForeground(new Color(0, 128, 128));
    lblNewLabel_1.setFont(new Font("Tahoma", 1, 16));
    lblNewLabel_1.setHorizontalAlignment(0);
    DropShadowBorder shadowborder = new DropShadowBorder();
    shadowborder.setShowLeftShadow(true);
    shadowborder.setShowRightShadow(true);
    shadowborder.setShowTopShadow(true);
    shadowborder.setShowBottomShadow(true);
    shadowborder.setShadowColor(Color.black);
    shadowborder.setShadowSize(10);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBorder(shadowborder);
    scrollPane.setUI(new FlatScrollPaneUI());
    scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
    scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());
    DefaultTableModel tableModel = new DefaultTableModel(new Object[0][], new String[] { "ID","Date", "Units", "Crown Type", "Labname", "No. Units","Recieve date" }) {
    	 final boolean[] columnEditables = { true, false, true, false, false, false, false };
        
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
    table.getTableHeader().setFont(new Font("Tahoma", 1, 12));
    table.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
    table.setSelectionBackground(new Color(83, 250, 71));
    table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 30));
    table.getColumnModel().getColumn(0).setResizable(true);
    table.getColumnModel().getColumn(0).setPreferredWidth(8);
    table.getColumnModel().getColumn(1).setResizable(true);
    table.getColumnModel().getColumn(1).setPreferredWidth(50);
    table.getColumnModel().getColumn(2).setResizable(true);
    table.getColumnModel().getColumn(2).setPreferredWidth(143);
    table.getColumnModel().getColumn(3).setResizable(true);
    table.getColumnModel().getColumn(3).setPreferredWidth(50);
    table.getColumnModel().getColumn(4).setResizable(true);
    table.getColumnModel().getColumn(4).setPreferredWidth(50);
    table.getColumnModel().getColumn(5).setResizable(true);
    table.getColumnModel().getColumn(5).setPreferredWidth(25);
    table.getColumnModel().getColumn(6).setResizable(true);
    table.getColumnModel().getColumn(6).setPreferredWidth(50);
    
    table.getTableHeader().setReorderingAllowed(false);
    table.setAutoCreateRowSorter(false);
    scrollPane.setViewportView(table);
    
    JButton btnRemove = new JButton("remove");
    
    btnRemove.setFont(new Font("Tahoma", Font.BOLD, 13));
    btnRemove.setBackground(Color.RED);
    btnRemove.setOpaque(false);    
    btnRemove.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
    
    btnRemove.addMouseListener(new MouseAdapter() {
    	@Override
    	public void mouseClicked(MouseEvent e) {
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
    		TableModel dmd = table.getModel();
            int[] row = table.getSelectedRows();
            if (table.getSelectedRowCount() != 0) {
              String op = JOptionPane.showInputDialog(contentPane, "Enter Passcode ", "Confirm to delete", 2);
              try {
                if (!op.equals(null)) {
                  if (op.equalsIgnoreCase(pass)) {
                    try {
                     
                      Class.forName("org.sqlite.JDBC");
                      Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");
                      for (int element : row) {
                        int mdr = table.convertRowIndexToModel(element);
                        int pname1 = (int) dmd.getValueAt(mdr, 0);
                        String query = "delete from uninfo where id =" + pname1 + "";
                        ps = con.prepareStatement(query);
                        ps.executeUpdate();
                        DefaultTableModel dm = (DefaultTableModel)table.getModel();
                        dm.removeRow(mdr);
                       JOptionPane.showMessageDialog(contentPane, "Data Deleted !!! ");
                      }
                      con.close();
                    } catch (Throwable ex) {
                      ex.printStackTrace();
                    }
                  } else
                    JOptionPane.showConfirmDialog(contentPane, "Wrong Passcode ,Try Again ", "Error", -1);
                }
              } catch (NullPointerException localNullPointerException) {}
            }
    	}catch(Throwable a) {}
    	}
    });
    GroupLayout gl_contentPane = new GroupLayout(contentPane);
    gl_contentPane.setHorizontalGroup(
    	gl_contentPane.createParallelGroup(Alignment.LEADING)
    		.addGroup(gl_contentPane.createSequentialGroup()
    			.addGap(36)
    			.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
    			.addGap(52))
    		.addGroup(gl_contentPane.createSequentialGroup()
    			.addGap(1)
    			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
    		.addGroup(gl_contentPane.createSequentialGroup()
    			.addGap(373)
    			.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
    			.addContainerGap(397, Short.MAX_VALUE))
    );
    gl_contentPane.setVerticalGroup(
    	gl_contentPane.createParallelGroup(Alignment.LEADING)
    		.addGroup(gl_contentPane.createSequentialGroup()
    			.addGap(6)
    			.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
    			.addGap(18)
    			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
    			.addPreferredGap(ComponentPlacement.RELATED)
    			.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
    			.addContainerGap())
    );
    contentPane.setLayout(gl_contentPane);
  }
}
