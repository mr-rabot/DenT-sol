package main.tabs;

import java.awt.Color;
import java.awt.Component;
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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.border.DropShadowBorder;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.formdev.flatlaf.ui.FlatScrollPaneUI;

import designs.CLabel;
import designs.CTextField;
import designs.MyScrollBarUI;
import gradientpane.Imagbg;
import main.popups.Crownd;
import main.popups.Crowndetails;

@SuppressWarnings("serial")
public class Lab extends Imagbg {
	static Date dt = new Date();
	static SimpleDateFormat frmtr = new SimpleDateFormat("dd/MM/yyyy");
	static String strd = frmtr.format(dt);
	private JLabel cdetailsb;
	private CTextField ctypef;
	private CTextField datef;
	Toolkit t = Toolkit.getDefaultToolkit();
	Dimension dim = new Dimension(t.getScreenSize());
	private DefaultTableModel dm;
	Crownd frame = new Crownd();
	int height = (int) dim.getHeight();
	public JLabel lblNewLabel;
	public CLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_3_1_2;
	private JLabel lblNewLabel_3_1_3;
	private JLabel lblNewLabel_3_1_4;
	private CTextField lbnamef;
	private CTextField nunitsf;
	private CTextField pidf;
	private PreparedStatement ps;
	private CTextField ptnamef;

	private String rdate;

	private CTextField rdatef;

	private ResultSet rs;

	private CTextField searchf;

	private Statement st;

	

	private JTable table_1;

	private JTextArea unitsf;

	Crowndetails v = new Crowndetails();
	int width = (int) dim.getWidth();

	public Lab() throws Throwable {
		setBorder(new BevelBorder(0, null, null, null, null));
		init();
		labdatal();
		id();
	}

	public void cdata() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");

		int nunit = Integer.parseInt(nunitsf.getText());
		String dt = datef.getText();
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		Date datefr = frmtr.parse(dt);
		String date = fr.format(datefr);
		String rdt = rdatef.getText();
		String rdatea = null;
		if (!rdatef.getText().isEmpty()) {
			Date rdatefr = frmtr.parse(rdt);
			rdatea = fr.format(rdatefr);
		}
		ps = con.prepareStatement(
				"insert into uninfo(pid, ptname, labname, date, units, nunits,ctype,rvdate )values(?,?,?,?,?,?,?,?)");
		ps.setString(1, pidf.getText());
		ps.setString(2, ptnamef.getText().toUpperCase());
		ps.setString(4, date);
		ps.setString(3, lbnamef.getText().toUpperCase());
		ps.setString(5, unitsf.getText());
		ps.setInt(6, nunit);
		ps.setString(7, ctypef.getText().toUpperCase());
		ps.setString(8, rdatea);
		ps.executeUpdate();

		con.close();
	}

	public void clearl() {
		ptnamef.setText(null);
		lbnamef.setText(null);
		unitsf.setText(null);
		ctypef.setText(null);
		datef.setText(strd);
		rdatef.setText(null);
		nunitsf.setText(null);
		ptnamef.requestFocus();
	}

	private void id() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");

		// Fetch the highest numeric value used so far in the "PTdata" table
		long highestNumericValue = 0;
		String highestNumericSql = "SELECT MAX(CAST(SUBSTR(pid, 3) AS INTEGER)) FROM labdata";
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

		String newId = "LB" + newValue;
		pidf.setText(newId);

		con.close();
	}

	public void init() {
		setBounds(0, 0, 1198, 629);
		lblNewLabel = new JLabel();
		lblNewLabel_1 = new CLabel();
		lblNewLabel_1.setFocusable(false);
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setLineColor(new Color(255, 255, 0));
		lblNewLabel_1.setStrokeWidth(6);
		lblNewLabel_1.setText("LAB");
		lblNewLabel_1.setOpaque(false);
		lblNewLabel_1.setFont(new Font("Tahoma", 1, 24));
		lblNewLabel_1.setHorizontalAlignment(0);
		DropShadowBorder shadowborder = new DropShadowBorder();
		shadowborder.setShowLeftShadow(true);
		shadowborder.setShowRightShadow(true);
		shadowborder.setShowTopShadow(true);
		shadowborder.setShowBottomShadow(true);
		shadowborder.setShadowColor(Color.cyan);
		shadowborder.setShadowSize(10);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		scrollPane.setFocusTraversalKeysEnabled(false);
		scrollPane.setUI(new FlatScrollPaneUI());
		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.setOpaque(false);
		scrollPane.setBorder(shadowborder);
		lblNewLabel_3 = new JLabel("Patient Name");
		lblNewLabel_3.setFocusTraversalKeysEnabled(false);
		lblNewLabel_3.setFocusable(false);
		lblNewLabel_3.setForeground(new Color(242, 242, 0));
		lblNewLabel_3.setFont(new Font("Tahoma", 1, 14));
		ptnamef = new CTextField();
		ptnamef.setFont(new Font("Tahoma", 1, 12));
		ptnamef.setColumns(10);
		ptnamef.requestFocus();
		JLabel lblNewLabel_3_1 = new JLabel("Teeth No.");
		lblNewLabel_3_1.setFocusTraversalKeysEnabled(false);
		lblNewLabel_3_1.setFocusable(false);
		lblNewLabel_3_1.setForeground(new Color(242, 242, 0));
		lblNewLabel_3_1.setFont(new Font("Tahoma", 1, 14));
		JLabel lblNewLabel_3_2 = new JLabel("Crown Type");
		lblNewLabel_3_2.setFocusTraversalKeysEnabled(false);
		lblNewLabel_3_2.setFocusable(false);
		lblNewLabel_3_2.setForeground(new Color(242, 242, 0));
		lblNewLabel_3_2.setFont(new Font("Tahoma", 1, 14));
		ctypef = new CTextField();
		ctypef.setFont(new Font("Tahoma", 1, 12));
		ctypef.setColumns(10);
		JLabel lblNewLabel_3_1_1 = new JLabel("No. Units");
		lblNewLabel_3_1_1.setFocusTraversalKeysEnabled(false);
		lblNewLabel_3_1_1.setFocusable(false);
		lblNewLabel_3_1_1.setForeground(new Color(242, 242, 0));
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", 1, 14));
		nunitsf = new CTextField();
		nunitsf.setEditable(false);
		nunitsf.setFont(new Font("Sitka Display", 1, 18));
		searchf = new CTextField();
		PromptSupport.setPrompt("Search...", searchf);
		PromptSupport.setForeground(Color.BLACK, searchf);
		PromptSupport.setFontStyle(Integer.valueOf(14), searchf);
		searchf.setLineColor(new Color(255, 0, 128));
		searchf.setFont(new Font("Tahoma", 1, 14));
		searchf.setColumns(10);
		searchf.addKeyListener(new KeyAdapter() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel dm = (DefaultTableModel) table_1.getModel();
				TableRowSorter<DefaultTableModel> trs2 = new TableRowSorter(dm);
				table_1.setRowSorter(trs2);
				String str = searchf.getText().toUpperCase();
				if (str.length() == 0)
					trs2.setRowFilter(null);
				trs2.setRowFilter(RowFilter.regexFilter("(?i)" + str, new int[0]));
				searchf.requestFocus();
			}
		});
		lblNewLabel_3_1_2 = new JLabel("Lab Name");
		lblNewLabel_3_1_2.setFocusTraversalKeysEnabled(false);
		lblNewLabel_3_1_2.setFocusable(false);
		lblNewLabel_3_1_2.setForeground(new Color(242, 242, 0));
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", 1, 14));
		lbnamef = new CTextField();
		lbnamef.setFont(new Font("Tahoma", 1, 12));
		lbnamef.setColumns(10);
		lblNewLabel_3_1_3 = new JLabel("Date");
		lblNewLabel_3_1_3.setFocusTraversalKeysEnabled(false);
		lblNewLabel_3_1_3.setFocusable(false);
		lblNewLabel_3_1_3.setForeground(new Color(242, 242, 0));
		lblNewLabel_3_1_3.setFont(new Font("Tahoma", 1, 14));
		datef = new CTextField();
		datef.setText(strd);
		datef.setForeground(new Color(255, 140, 0));
		datef.setFont(new Font("Tahoma", 1, 12));
		datef.setColumns(10);
		lblNewLabel_3_1_4 = new JLabel("Recieved Date");
		lblNewLabel_3_1_4.setFocusTraversalKeysEnabled(false);
		lblNewLabel_3_1_4.setFocusable(false);
		lblNewLabel_3_1_4.setForeground(new Color(242, 242, 0));
		lblNewLabel_3_1_4.setFont(new Font("Tahoma", 1, 14));
		rdatef = new CTextField();
		rdatef.setFont(new Font("Tahoma", 1, 12));
		rdatef.setColumns(10);
		PromptSupport.setPrompt("dd/MM/yyyy", rdatef);
		JButton btnremove = new JButton("Remove");

		btnremove.setOpaque(false);
		btnremove.setBackground(new Color(255, 69, 0));
		btnremove.setBorder(new CompoundBorder(new EtchedBorder(0, null, null), new EtchedBorder(0, null, null)));
		btnremove.setForeground(new Color(255, 255, 128));
		btnremove.setFont(new Font("Tahoma", 1, 13));
		JButton btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModify.setOpaque(false);
		btnModify.setForeground(new Color(255, 255, 128));
		btnModify.setFont(new Font("Tahoma", 1, 13));
		btnModify.setBorder(
				new CompoundBorder(new BevelBorder(0, null, null, null, null), new EtchedBorder(0, null, null)));
		btnModify.setBackground(new Color(0, 131, 13));
		JButton btnAdd = new JButton("Add");

		btnAdd.setOpaque(false);
		btnAdd.setForeground(new Color(255, 255, 128));
		btnAdd.setFont(new Font("Tahoma", 1, 13));
		btnAdd.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		btnAdd.setBackground(new Color(0, 255, 255));
		JButton btnReset = new JButton("Reset");
		btnReset.setOpaque(false);
		btnReset.setForeground(new Color(255, 255, 128));
		btnReset.setFont(new Font("Tahoma", 1, 13));
		btnReset.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		btnReset.setBackground(new Color(255, 255, 0));
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setOpaque(false);
		btnUpdate.setForeground(new Color(255, 255, 128));
		btnUpdate.setFont(new Font("Tahoma", 1, 13));
		btnUpdate.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		btnUpdate.setBackground(Color.CYAN);
		DropShadowBorder shadowbordersb = new DropShadowBorder();
		shadowbordersb.setShowLeftShadow(true);
		shadowbordersb.setShowRightShadow(true);
		shadowbordersb.setShowTopShadow(true);
		shadowbordersb.setShowBottomShadow(true);
		shadowbordersb.setShadowColor(Color.yellow);
		shadowbordersb.setShadowSize(10);
		cdetailsb = new JLabel("Crown Details");
		cdetailsb.setFocusable(false);
		cdetailsb.setBorder(shadowbordersb);
		cdetailsb.setForeground(new Color(0, 255, 0));
		cdetailsb.setFont(new Font("Eras Bold ITC", 1, 19));
		cdetailsb.setHorizontalAlignment(0);
		cdetailsb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				frame.fr.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				DropShadowBorder shadowbordersb1 = new DropShadowBorder();
				shadowbordersb1.setShowLeftShadow(true);
				shadowbordersb1.setShowRightShadow(true);
				shadowbordersb1.setShowTopShadow(true);
				shadowbordersb1.setShowBottomShadow(true);
				shadowbordersb1.setShadowColor(Color.pink);
				shadowbordersb1.setShadowSize(10);
				cdetailsb.setBorder(shadowbordersb1);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cdetailsb.setBorder(shadowbordersb);
			}

		});
		pidf = new CTextField();
		pidf.setFocusable(false);
		pidf.setLineColor(new Color(217, 0, 5));
		pidf.setFont(new Font("Tahoma", 1, 11));
		pidf.setColumns(10);
		pidf.setEditable(false);
		DefaultTableModel tableModel = new DefaultTableModel(new Object[0][],
				new String[] { "S.No.", "P_ID", "Patient Name", "Date", "Recieved Date" }) {
			final boolean[] columnEditables = new boolean[5];

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table_1 = new JTable(tableModel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < table_1.getColumnCount(); i++) {
			table_1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table_1.setRowHeight(26);
		table_1.getTableHeader().setReorderingAllowed(false);
		table_1.getTableHeader().setBackground(new Color(255, 213, 0));
		table_1.getTableHeader().setForeground(new Color(0, 0, 0));
		table_1.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
		table_1.getTableHeader().setFont(new Font("Tahoma", 1, 12));
		table_1.setSelectionBackground(new Color(40, 253, 216));
		table_1.getTableHeader().setPreferredSize(new Dimension(table_1.getWidth(), 30));
		table_1.getColumnModel().getColumn(0).setResizable(true);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(8);
		table_1.getColumnModel().getColumn(1).setResizable(true);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(15);
		table_1.getColumnModel().getColumn(2).setResizable(true);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(280);
		table_1.getColumnModel().getColumn(3).setResizable(true);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(40);
		table_1.getColumnModel().getColumn(4).setResizable(true);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(40);

		table_1.getTableHeader().setReorderingAllowed(false);
		table_1.setAutoCreateRowSorter(false);
		scrollPane.setViewportView(table_1);

		rdatef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				String dt = rdatef.getText();
				if ((dt.length() == 2) || (dt.length() == 5)) {
					rdatef.setText(dt + "/");
					if (e.getKeyCode() == 8)
						rdatef.setText(null);
				} else if (dt.length() >= 10) {
					rdatef.setEditable(false);
					if (e.getKeyCode() == 8)
						rdatef.setEditable(true);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					btnAdd.requestFocus();
				}

			}
		});

		datef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String dt = datef.getText();
				if ((dt.length() == 2) || (dt.length() == 5)) {
					datef.setText(dt + "/");
					if (e.getKeyCode() == 8)
						datef.setText(null);
				} else if (dt.length() >= 10) {
					datef.setEditable(false);
					if (e.getKeyCode() == 8) {
						datef.setEditable(true);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					rdatef.requestFocus();
				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] row = table_1.getSelectedRows();
				DefaultTableModel dm2 = (DefaultTableModel) table_1.getModel();
				String id = null;
				for (int element : row) {
					int mdr = table_1.convertRowIndexToModel(element);
					id = dm2.getValueAt(mdr, 1).toString();
				}
				if (id == null) {
					try {
						if (!ptnamef.getText().isEmpty()) {
							if (nunitsf.getText().isEmpty()) {
								labdata();
								DefaultTableModel dtm = (DefaultTableModel) table_1.getModel();
								dtm.setRowCount(0);
								labdatal();
								id();
							} else {
								labdata();
								cdata();
								DefaultTableModel dtm = (DefaultTableModel) table_1.getModel();
								dtm.setRowCount(0);
								labdatal();
								id();
							}
							clearl();
						}
					} catch (Throwable e1) {
						// e1.printStackTrace();
					}
					ptnamef.requestFocus();
				} else {
					id.equals(pidf.getText());
				}

			}
		});
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int[] row = table_1.getSelectedRows();
					DefaultTableModel dm2 = (DefaultTableModel) table_1.getModel();
					String id = null;
					for (int element : row) {
						int mdr = table_1.convertRowIndexToModel(element);
						id = dm2.getValueAt(mdr, 1).toString();
					}
					if (id == null) {
						try {
							if (!ptnamef.getText().isEmpty()) {
								if (nunitsf.getText().isEmpty()) {
									labdata();
									DefaultTableModel dtm = (DefaultTableModel) table_1.getModel();
									dtm.setRowCount(0);
									labdatal();
									id();
								} else {
									labdata();
									cdata();
									DefaultTableModel dtm = (DefaultTableModel) table_1.getModel();
									dtm.setRowCount(0);
									labdatal();
									id();
								}
								clearl();
							}
						} catch (Throwable e1) {
							// e1.printStackTrace();
						}
						ptnamef.requestFocus();
					} else {
						id.equals(pidf.getText());
					}

				}
			}
		});
		btnModify.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int[] row = table_1.getSelectedRows();
					DefaultTableModel dm2 = (DefaultTableModel) table_1.getModel();
					if (table_1.getSelectedRowCount() != 0) {
						table_1.disable();
						for (int element : row) {
							int mdr = table_1.convertRowIndexToModel(element);
							pidf.setText(dm2.getValueAt(mdr, 1).toString());
							ptnamef.setText(dm2.getValueAt(mdr, 2).toString());
							datef.setText(dm2.getValueAt(mdr, 3).toString());
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				table_1.enable();
				try {
					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");

					TableModel dms = table_1.getModel();

					int[] row = table_1.getSelectedRows();
					if (table_1.getSelectedRowCount() != 0) {
						if ((rdatef.getText().isEmpty()) || (rdatef.getText().equals(rdate))) {
							if (!ptnamef.getText().isEmpty()) {
								for (int element : row) {
									int mdr = table_1.convertRowIndexToModel(element);
									String ptname1 = dms.getValueAt(mdr, 1).toString();
									String dt = datef.getText();
									SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
									Date datefr = Lab.frmtr.parse(dt);
									String date = fr.format(datefr);
									String query = "UPDATE labdata set ptname=?, date=? where pid='" + ptname1 + "'";
									PreparedStatement pst = con.prepareStatement(query);
									pst.setString(1, ptnamef.getText().toUpperCase());
									pst.setString(2, date);

									pst.executeUpdate();
									String queri = "select * from uninfo where pid='" + ptname1 + "' order by date ";
									PreparedStatement psts = con.prepareStatement(queri);
									rs = psts.executeQuery();
									String lbname = null;
									String crtype = null;
									while (rs.next()) {
										lbname = rs.getString("labname");
										crtype = rs.getString("ctype");
									}
									if (!unitsf.getText().isEmpty()) {
										int nunitsa = Integer.parseInt(nunitsf.getText());
										Lab.this.ps = con.prepareStatement(
												"insert into uninfo(pid, ptname, labname, date, units, nunits,ctype)values(?,?,?,?,?,?,?)");
										Lab.this.ps.setString(1, pidf.getText());
										Lab.this.ps.setString(2, ptnamef.getText().toUpperCase());
										Lab.this.ps.setString(3, lbnamef.getText().toUpperCase());
										Lab.this.ps.setString(5, unitsf.getText());
										Lab.this.ps.setString(4, date);
										Lab.this.ps.setInt(6, nunitsa);
										Lab.this.ps.setString(7, ctypef.getText().toUpperCase());
										Lab.this.ps.executeUpdate();
									} else if (lbnamef.getText().equalsIgnoreCase(lbname)
											|| lbnamef.getText().isEmpty()) {
										String qrt = "update uninfo set ptname=?, ctype=?  where pid ='" + ptname1
												+ "' and date ='" + date + "'";
										PreparedStatement pst1 = con.prepareStatement(qrt);
										pst1.setString(1, ptnamef.getText().toUpperCase());
										pst1.setString(2, ctypef.getText().toUpperCase());
										pst1.executeUpdate();

									} else if (ctypef.getText().equalsIgnoreCase(crtype)
											|| ctypef.getText().isEmpty()) {
										String qrt = "update uninfo set ptname=?, labname=?  where pid ='" + ptname1
												+ "' and date ='" + date + "'";
										PreparedStatement pst1 = con.prepareStatement(qrt);
										pst1.setString(2, lbnamef.getText().toUpperCase());
										pst1.setString(1, ptnamef.getText().toUpperCase());
										pst1.executeUpdate();

									} else if (lbnamef.getText().equalsIgnoreCase(lbname)
											&& ctypef.getText().equalsIgnoreCase(crtype)) {
										String qrt = "update uninfo set ptname=? where pid ='" + ptname1
												+ "' and date ='" + date + "'";
										PreparedStatement pst1 = con.prepareStatement(qrt);
										pst1.setString(1, ptnamef.getText().toUpperCase());
										pst1.executeUpdate();
									} else {
										String qrt = "update uninfo set ptname=?, labname=?, ctype=?  where pid ='"
												+ ptname1 + "' and date ='" + date + "'";
										PreparedStatement pst1 = con.prepareStatement(qrt);
										pst1.setString(1, ptnamef.getText().toUpperCase());
										pst1.setString(2, lbnamef.getText().toUpperCase());
										pst1.setString(3, ctypef.getText().toUpperCase());
										pst1.executeUpdate();
									}

								}

							}

						} else {
							String qr = "update labdata set rvdate=?  where pid='" + pidf.getText() + "'";
							PreparedStatement ps = con.prepareStatement(qr);
							SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
							String dt = rdatef.getText();
							Date datefr = Lab.frmtr.parse(dt);
							String rdateu = fr.format(datefr);
							ps.setString(1, rdateu);
							ps.executeUpdate();

							String dt1 = datef.getText();
							SimpleDateFormat fr1 = new SimpleDateFormat("yyyy-MM-dd");
							Date datefr1 = Lab.frmtr.parse(dt1);
							String date = fr1.format(datefr1);
							String qr1 = "update uninfo set rvdate=?  where pid='" + pidf.getText() + "' and date ='"
									+ date + "'";
							PreparedStatement ps1 = con.prepareStatement(qr1);
							String rdateu1 = fr.format(datefr);
							ps1.setString(1, rdateu1);
							ps1.executeUpdate();
						}
						DefaultTableModel dm = (DefaultTableModel) table_1.getModel();
						dm.setRowCount(0);
						labdatal();
						id();
						dm.fireTableDataChanged();
						clearl();
						ptnamef.requestFocus();

						con.close();
					}
				} catch (Throwable e1) {
					// e1.printStackTrace();

				}

			}

		});
		btnremove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con1 = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");

					PreparedStatement ps = con1.prepareStatement("Select * from admin");
					ResultSet rs = ps.executeQuery();
					TableModel dmd = table_1.getModel();
					int[] row = table_1.getSelectedRows();
					if (table_1.getSelectedRowCount() != 0) {

						String pass = null;
						if (rs.next()) {
							pass = rs.getString(1);
						}
						JPanel panel = new JPanel();

						JLabel label = new JLabel("  Enter Passcode");
						label.setHorizontalTextPosition(SwingConstants.CENTER);
						label.setHorizontalAlignment(SwingConstants.CENTER);
						label.setFont(new Font("Tahoma", 1, 14));
						label.setForeground(new Color(255, 102, 0));
						JPasswordField passf = new JPasswordField(10);
						passf.setEchoChar('*');
						passf.setFont(new Font("Tahoma", 1, 12));
						passf.setPreferredSize(new Dimension(passf.getPreferredSize().width, 30));
						JButton eyeButton = new JButton(new ImageIcon(getClass().getResource("/Images/eyeop.png")));
						eyeButton.setPreferredSize(new Dimension(34, 34));
						eyeButton.setOpaque(false); // Make the button background transparent
						eyeButton.setContentAreaFilled(false); // Make the content area transparent
						eyeButton.setBorderPainted(false);
						eyeButton.setFocusPainted(false);
						eyeButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								togglePasswordVisibility(passf, eyeButton);
							}
						});
						panel.add(label);
						panel.add(passf);
						panel.add(eyeButton);
						panel.setAlignmentX(Component.CENTER_ALIGNMENT);
						String[] options = new String[] { "Ok", "Cancel" };
						int option = JOptionPane.showInternalOptionDialog(null, panel, "Confirm to Delete ! ",
								JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
						if (option == 0) {

							String password = new String(passf.getPassword());

							try {
								if (!password.equals(null)) {
									if (password.equalsIgnoreCase(pass)) {
										try {

											Class.forName("org.sqlite.JDBC");
											Connection con = DriverManager
													.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");

											for (int element : row) {
												int mdr = table_1.convertRowIndexToModel(element);
												String pname1 = dmd.getValueAt(mdr, 1).toString();
												String query = "delete from labdata where pid ='" + pname1 + "'";
												ps = con.prepareStatement(query);
												ps.executeUpdate();
												String qr = "delete from uninfo where pid = '" + pname1 + "'";
												ps = con.prepareStatement(qr);
												ps.executeUpdate();
												dm = ((DefaultTableModel) table_1.getModel());
												dm.setRowCount(0);
												labdatal();
												id();
											}

											con.close();
										} catch (Exception ex) {
											// System.err.println();
										}
									} else
										JOptionPane.showConfirmDialog(null, "Wrong Passcode ,Try Again ", "Error", -1);
								}
							} catch (NullPointerException localNullPointerException) {
							}
						} else {

						}
						ptnamef.requestFocus();
					} else {
						JOptionPane.showConfirmDialog(null, " Please First Select Row\n That You Want To Delete",
								"Error", -1);
					}
				} catch (Throwable NullPointerExceptionIgnore) {
					System.err.println();
				}
			}
		});

		btnremove.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {

					if (e.getKeyCode() == KeyEvent.VK_DELETE) {
						TableModel dmd = table_1.getModel();
						int[] row = table_1.getSelectedRows();
						String cd = System.getProperty("user.dir");
						Class.forName("org.sqlite.JDBC");
						Connection con1 = DriverManager
								.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");

						PreparedStatement ps = con1.prepareStatement("Select * from admin");
						ResultSet rs = ps.executeQuery();
						if (table_1.getSelectedRowCount() != 0) {
							String pass = null;
							if (rs.next()) {
								pass = rs.getString(1);
							}
							JPanel panel = new JPanel();
							JLabel label = new JLabel("Enter Passcode : ");
							JPasswordField passf = new JPasswordField(10);
							passf.setEchoChar('*');
							passf.setFont(new Font("Tahoma", 1, 12));
							passf.setPreferredSize(new Dimension(passf.getPreferredSize().width, 30));
							JButton eyeButton = new JButton(new ImageIcon(getClass().getResource("/Images/eyeop.png")));
							eyeButton.setPreferredSize(new Dimension(34, 34));
							eyeButton.setOpaque(false); // Make the button background transparent
							eyeButton.setContentAreaFilled(false); // Make the content area transparent
							eyeButton.setBorderPainted(false);
							eyeButton.setFocusPainted(false);
							eyeButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									togglePasswordVisibility(passf, eyeButton);
								}
							});
							panel.add(label);
							panel.add(passf);
							panel.add(eyeButton);
							String[] options = new String[] { "Ok", "Cancel" };
							int option = JOptionPane.showInternalOptionDialog(null, panel, "Enter Passcode ",
									JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

							if (option == 0) {

								String password = new String(passf.getPassword());

								try {
									if (!password.equals(null)) {
										if (password.equalsIgnoreCase(pass)) {
											try {
												String cd1 = System.getProperty("user.dir");
												Class.forName("org.sqlite.JDBC");
												Connection con = DriverManager.getConnection(
														"jdbc:sqlite:" + cd1 + "/ImpFiles/database/lab.db");

												for (int element : row) {
													int mdr = table_1.convertRowIndexToModel(element);
													String pname1 = dmd.getValueAt(mdr, 1).toString();
													String query = "delete from labdata where pid ='" + pname1 + "'";
													ps = con.prepareStatement(query);
													ps.executeUpdate();
													String qr = "delete from uninfo where pid = '" + pname1 + "'";
													ps = con.prepareStatement(qr);
													ps.executeUpdate();
													dm = ((DefaultTableModel) table_1.getModel());
													dm.setRowCount(0);
													labdatal();
													id();
												}

												con.close();
											} catch (Exception ex) {
												// System.err.println("Error in deleteb: " + ex);
											}
										} else
											JOptionPane.showConfirmDialog(null, "Wrong Passcode ,Try Again ", "Error",
													-1);
									}
								} catch (NullPointerException localNullPointerException) {
								}
							}
							ptnamef.requestFocus();

						} else {
							JOptionPane.showConfirmDialog(null, " Please First Select Row\n That You Want To Delete",
									"Error", -1);
						}
					}
				} catch (Throwable Ignore) {
					System.err.println();
				}
			}
		});

		btnReset.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					clearl();
					id();
					ptnamef.requestFocus();
					table_1.clearSelection();
					table_1.enable();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table_1.clearSelection();
				try {
					id();
				} catch (Throwable ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				v.dispose();
				int rows = table_1.rowAtPoint(e.getPoint());
				int[] row = table_1.getSelectedRows();
				try {
					if (rows == -1) {
						table_1.clearSelection();
					} else if (e.getClickCount() == 2) {
						for (int element : row) {
							DefaultTableModel dtm = (DefaultTableModel) table_1.getModel();
							int mdr = table_1.convertRowIndexToModel(element);
							String id = dtm.getValueAt(mdr, 1).toString();
							String pname = dtm.getValueAt(mdr, 2).toString();
							v.lcdata(id, pname);
							v.setVisible(true);
						}
					}
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		DropShadowBorder shadowborders = new DropShadowBorder();
		shadowborders.setShowLeftShadow(true);
		shadowborders.setShowRightShadow(true);
		shadowborders.setShowTopShadow(true);
		shadowborders.setShowBottomShadow(true);
		shadowborders.setShadowColor(Color.green);
		shadowborders.setShadowSize(8);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setUI(new FlatScrollPaneUI());
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.getVerticalScrollBar().setUI(new MyScrollBarUI());

		scrollPane_1.setOpaque(false);
		scrollPane_1.setBorder(shadowborders);

		CLabel txtfldSearh = new CLabel();
		txtfldSearh.setFocusable(false);
		txtfldSearh.setLineColor(new Color(255, 0, 128));
		txtfldSearh.setForeground(new Color(255, 255, 0));
		txtfldSearh.setText("Searh");
		txtfldSearh.setFont(new Font("Tahoma", 1, 14));
		unitsf = new JTextArea();

		scrollPane_1.setViewportView(unitsf);
		unitsf.setLineWrap(true);
		unitsf.setWrapStyleWord(true);
		PromptSupport.setPrompt("ex. 23,43,12 ...", unitsf);
		unitsf.setBorder(new BevelBorder(0, null, null, null, null));
		unitsf.setFont(new Font("Sitka Display", 1, 16));
		unitsf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (Character.isLetter(c)) {
					unitsf.setEditable(false);
					if (e.getKeyCode() == 8)
						unitsf.setEditable(true);
				} else {
					unitsf.setEditable(true);
					int count = 1;
					String unts = unitsf.getText();
					if (unitsf.getText().isEmpty()) {
						nunitsf.setText(null);
					} else {
						for (int i = 0; i < unts.length(); i++) {
							if (unts.charAt(i) == ',')
								count++;
							nunitsf.setText("" + count);
						}
					}
				}
			}
		});
		unitsf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\t') {
					e.consume();
				}
			}
		});
		ptnamef.addKeyListener(new KeyAdapter() {
			@Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void keyReleased(KeyEvent e) {
				int[] row = table_1.getSelectedRows();
				DefaultTableModel dm2 = (DefaultTableModel) table_1.getModel();
				String id = null;
				for (int element : row) {
					int mdr = table_1.convertRowIndexToModel(element);
					id = dm2.getValueAt(mdr, 1).toString();
				}
				if (id == null) {
					DefaultTableModel dm = (DefaultTableModel) table_1.getModel();
					Object trs2 = new TableRowSorter(dm);
					table_1.setRowSorter((RowSorter) trs2);
					String str = ptnamef.getText().toUpperCase();
					if (str.length() == 0)
						((TableRowSorter) trs2).setRowFilter(null);
					((TableRowSorter) trs2).setRowFilter(RowFilter.regexFilter("(?i)" + str, new int[0]));
					ptnamef.requestFocus();
				} else {
					id.equals(pidf.getText());
				}

			}

		});
		ptnamef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					unitsf.requestFocus();
				}

			}
		});
		unitsf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 9) {
					ctypef.requestFocus();
				}
			}
		});
		lbnamef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					datef.requestFocus();
				}
			}
		});
		datef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					rdatef.requestFocus();
				}
			}
		});
		ctypef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					lbnamef.requestFocus();
				}

			}

		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(407)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE).addGap(356))
				.addGroup(groupLayout.createSequentialGroup().addGap(12)
						.addComponent(searchf, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE).addGap(4)
						.addComponent(txtfldSearh, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addGap(379)
						.addComponent(cdetailsb, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
						.addGap(155).addComponent(pidf, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addGap(78))
				.addGroup(groupLayout.createSequentialGroup().addGap(12)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE).addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(lblNewLabel_3,
										GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(lblNewLabel_3_1,
										GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(lblNewLabel_3_1_1,
										GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(lblNewLabel_3_2,
										GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(lblNewLabel_3_1_2,
										GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(lblNewLabel_3_1_3,
										GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblNewLabel_3_1_4, GroupLayout.PREFERRED_SIZE, 112,
										GroupLayout.PREFERRED_SIZE))
						.addGap(4)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(ptnamef, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(nunitsf,
										GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(ctypef,
										GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(lbnamef,
										GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(datef,
										GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(rdatef,
										GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup().addGap(8).addComponent(scrollPane_1)))
						.addGap(44))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addGap(120)
						.addComponent(btnremove, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
						.addGap(225)
						.addComponent(btnModify, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
						.addGap(253).addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addGap(12).addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addGap(8)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(30)
				.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE).addGap(13)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(16).addComponent(searchf,
								GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(15).addComponent(txtfldSearh,
								GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addComponent(cdetailsb, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup().addGap(29)
								.addComponent(pidf, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
				.addGap(6)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addGap(5)
								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
								.addGap(14)
								.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 34,
										GroupLayout.PREFERRED_SIZE)
								.addGap(42)
								.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 38,
										GroupLayout.PREFERRED_SIZE)
								.addGap(17)
								.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 34,
										GroupLayout.PREFERRED_SIZE)
								.addGap(17)
								.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE, 38,
										GroupLayout.PREFERRED_SIZE)
								.addGap(7)
								.addComponent(lblNewLabel_3_1_3, GroupLayout.PREFERRED_SIZE, 38,
										GroupLayout.PREFERRED_SIZE)
								.addGap(11).addComponent(lblNewLabel_3_1_4, GroupLayout.PREFERRED_SIZE, 38,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(5)
								.addComponent(ptnamef, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addGap(8)
								.addComponent(nunitsf, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(ctypef, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(lbnamef, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addGap(9)
								.addComponent(datef, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(rdatef, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
				.addGap(6)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(btnremove,
								GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(btnModify,
								GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(btnReset,
								GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
				.addGap(15)));
		setLayout(groupLayout);
	}

	public void labdata() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");

		String dt = datef.getText();
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		Date datefr = frmtr.parse(dt);
		String date = fr.format(datefr);
		String rdt = rdatef.getText();
		String rdatea = null;
		if (!rdatef.getText().isEmpty()) {
			Date rdatefr = frmtr.parse(rdt);
			rdatea = fr.format(rdatefr);
		}
		ps = con.prepareStatement("insert into labdata(pid, ptname, date, rvdate)values(?,?,?,?)");
		ps.setString(1, pidf.getText());
		ps.setString(2, ptnamef.getText().toUpperCase());
		ps.setString(3, date);
		ps.setString(4, rdatea);
		ps.executeUpdate();
		ps.close();

		con.close();
	}

	public void labdatal() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/lab.db");

		String queri = "select * from labdata order by date";
		st = con.createStatement();
		rs = st.executeQuery(queri);
		int SNo = 0;
		while (rs.next()) {
			SNo++;
			String pid = rs.getString("pid");
			String ptname = rs.getString("ptname");
			String date = rs.getString("date");
			rdate = rs.getString("rvdate");
			SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
			Date datefr = fr.parse(date);
			String dt = frmtr.format(datefr);
			String rdt = null;
			if (!(rs.getString("rvdate") == null)) {
				Date rdatefr = fr.parse(rdate);
				rdt = frmtr.format(rdatefr);
			}
			Object[] bo = { Integer.valueOf(SNo), pid, ptname, dt, rdt };
			DefaultTableModel dm = (DefaultTableModel) table_1.getModel();
			dm.insertRow(0, bo);
		}

		con.close();
	}

	private void togglePasswordVisibility(JPasswordField passwordField, JButton eyeButton) {
		if (passwordField.getEchoChar() == 0) {
			passwordField.setEchoChar('*');
			eyeButton.setIcon(new ImageIcon(getClass().getResource("/Images/eyeop.png")));
		} else {
			passwordField.setEchoChar((char) 0);
			eyeButton.setIcon(new ImageIcon(getClass().getResource("/Images/eyeclo.png")));
		}
	}

}
