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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
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
import main.popups.VisitT;

@SuppressWarnings("serial")
public class Ortho extends Imagbg {
	static SimpleDateFormat frmtr = new SimpleDateFormat("dd/MM/yyyy");
	static CTextField pidf;
	static JTable table;

	public static void id() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");

		// Fetch the highest numeric value used so far in the "PTdata" table
		long highestNumericValue = 0;
		String highestNumericSql = "SELECT MAX(CAST(SUBSTR(pid, 3) AS INTEGER)) FROM orthopt";
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

	public static void ldata() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");

		String queri = "SELECT * FROM orthopt ORDER BY status DESC, date ASC ";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(queri);
		int SNo = 0;
		while (rs.next()) {
			SNo++;
			String pid = rs.getString("pid");
			String ptname = rs.getString("pname");
			String age = rs.getString("age");
			String gender = rs.getString("gender");
			String phone = rs.getString("phone");
			Boolean lvisit = Boolean.valueOf(rs.getBoolean("status"));
			SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
			Date sd = fr.parse(rs.getString("date"));
			String date = frmtr.format(sd);
			Object[] bo = { Integer.valueOf(SNo), pid, ptname, age, gender, phone, date, lvisit };
			DefaultTableModel dm = (DefaultTableModel) table.getModel();
			dm.insertRow(0, bo);
		}

		con.close();
	}

	CTextField datef;
	Toolkit t = Toolkit.getDefaultToolkit();
	Dimension dim = new Dimension(t.getScreenSize());
	public DefaultTableModel dm;
	Date dt = new Date();
	CTextField genderf;

	int height = (int) dim.getHeight();

	public CLabel lblNewLabel;

	CTextField pagef;

	CTextField pnamef;

	CTextField pphonef;

	private PreparedStatement ps;

	private CTextField searchf;

	String strd = frmtr.format(dt);

	VisitT v = new VisitT();

	private CTextField visitf;

	int width = (int) dim.getWidth();

	public Ortho() throws Throwable {
		init();

		ldata();
		id();
	}

	public void init() {
		setFont(new Font("Tahoma", 1, 13));
		setBounds(0, 0, 1250, 598);
		lblNewLabel = new CLabel();
		lblNewLabel.setFocusable(false);
		lblNewLabel.setStrokeWidth(6);
		lblNewLabel.setForeground(new Color(255, 128, 64));
		lblNewLabel.setLineColor(new Color(255, 255, 0));
		lblNewLabel.setText("ORTHO");
		lblNewLabel.setOpaque(false);
		lblNewLabel.setFont(new Font("Tahoma", 1, 24));
		lblNewLabel.setHorizontalAlignment(0);
		CLabel lblNewLabel_1 = new CLabel();
		lblNewLabel_1.setStrokeWidth(4);
		lblNewLabel_1.setFocusable(false);
		lblNewLabel_1.setLineColor(new Color(255, 255, 128));
		lblNewLabel_1.setText("Register Ortho Patient");
		lblNewLabel_1.setForeground(new Color(255, 128, 128));
		lblNewLabel_1.setOpaque(false);
		lblNewLabel_1.setFont(new Font("Tahoma", 1, 17));
		lblNewLabel_1.setHorizontalAlignment(0);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		scrollPane.setFocusTraversalKeysEnabled(false);
		DropShadowBorder shadowborder = new DropShadowBorder();
		shadowborder.setShowLeftShadow(true);
		shadowborder.setShowRightShadow(true);
		shadowborder.setShowTopShadow(true);
		shadowborder.setShowBottomShadow(true);
		shadowborder.setShadowColor(Color.cyan);
		shadowborder.setShadowSize(10);
		scrollPane.setUI(new FlatScrollPaneUI());
		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.setOpaque(false);
		scrollPane.setBorder(shadowborder);
		JLabel lblfname = new JLabel("Patient Name");
		lblfname.setFocusTraversalKeysEnabled(false);
		lblfname.setFocusable(false);
		lblfname.setForeground(new Color(255, 255, 0));
		lblfname.setFont(new Font("Tahoma", 1, 14));
		pnamef = new CTextField();
		pnamef.setLineColor(new Color(10, 241, 166));
		pnamef.setFont(new Font("Tahoma", 1, 12));
		pnamef.setColumns(10);
		pnamef.requestFocus();
		JLabel lblage = new JLabel("Age");
		lblage.setFocusTraversalKeysEnabled(false);
		lblage.setFocusable(false);
		lblage.setForeground(new Color(255, 255, 0));
		lblage.setFont(new Font("Tahoma", 1, 14));
		pagef = new CTextField();
		pagef.setLineColor(new Color(10, 241, 166));
		pagef.setFont(new Font("Tahoma", 1, 12));
		pagef.setColumns(10);
		JLabel lblgender = new JLabel("Gender");
		lblgender.setFocusTraversalKeysEnabled(false);
		lblgender.setFocusable(false);
		lblgender.setForeground(new Color(255, 255, 0));
		lblgender.setFont(new Font("Tahoma", 1, 14));
		genderf = new CTextField();
		genderf.setLineColor(new Color(10, 241, 166));
		genderf.setHorizontalAlignment(0);
		PromptSupport.setPrompt(" M/F ", genderf);
		genderf.setFont(new Font("Tahoma", 1, 12));
		genderf.setColumns(10);
		JLabel lblphone = new JLabel("Contact");
		lblphone.setFocusable(false);
		lblphone.setForeground(new Color(255, 255, 0));
		lblphone.setFont(new Font("Tahoma", 1, 14));
		pphonef = new CTextField();
		pphonef.setLineColor(new Color(10, 241, 166));
		pphonef.setFont(new Font("Tahoma", 1, 12));
		pphonef.setColumns(10);
		JLabel lbldate = new JLabel("Date");
		lbldate.setFocusTraversalKeysEnabled(false);
		lbldate.setFocusable(false);
		lbldate.setForeground(new Color(255, 255, 0));
		lbldate.setFont(new Font("Tahoma", 1, 14));
		datef = new CTextField();
		datef.setLineColor(new Color(10, 241, 166));
		datef.setText(strd);
		datef.setForeground(new Color(255, 140, 0));
		datef.setFont(new Font("Tahoma", 1, 12));
		datef.setColumns(10);
		JLabel lbltreatment = new JLabel("Visits");
		lbltreatment.setFocusTraversalKeysEnabled(false);
		lbltreatment.setFocusable(false);
		lbltreatment.setForeground(new Color(255, 255, 0));
		lbltreatment.setFont(new Font("Tahoma", 1, 14));
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setForeground(new Color(242, 242, 0));
		btnNewButton.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		btnNewButton.setOpaque(false);
		btnNewButton.setBackground(new Color(0, 100, 0));
		btnNewButton.setFont(new Font("Tahoma", 1, 13));
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(242, 242, 0));
		btnUpdate.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		btnUpdate.setOpaque(false);
		btnUpdate.setBackground(new Color(0, 255, 255));
		btnUpdate.setFont(new Font("Tahoma", 1, 13));
		JButton btnReset = new JButton("Reset");
		btnReset.setForeground(new Color(242, 242, 0));
		btnReset.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		btnReset.setOpaque(false);
		btnReset.setBackground(new Color(255, 0, 255));
		btnReset.setFont(new Font("Tahoma", 1, 13));
		JButton btnModify = new JButton("Modify");
		btnModify.setForeground(new Color(242, 242, 0));
		btnModify.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		btnModify.setOpaque(false);
		btnModify.setBackground(Color.CYAN);
		btnModify.setFont(new Font("Tahoma", 1, 13));
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBorder(new CompoundBorder(new BevelBorder(0, null, null, null, null),
				new BevelBorder(0, null, null, null, null)));
		btnRemove.setOpaque(false);
		btnRemove.setBackground(Color.RED);
		btnRemove.setForeground(Color.RED);
		btnRemove.setFont(new Font("Tahoma", 1, 13));
		searchf = new CTextField();
		searchf.setFont(new Font("Tahoma", 1, 14));
		PromptSupport.setPrompt("Search...", searchf);
		PromptSupport.setForeground(Color.BLACK, searchf);
		PromptSupport.setFontStyle(Integer.valueOf(13), searchf);
		searchf.setLineColor(new Color(10, 241, 166));
		searchf.setColumns(10);
		CLabel lblNewLabel_3 = new CLabel();
		lblNewLabel_3.setFocusable(false);
		lblNewLabel_3.setLineColor(new Color(255, 0, 128));
		lblNewLabel_3.setText("Search");
		lblNewLabel_3.setForeground(new Color(255, 255, 0));
		lblNewLabel_3.setFont(new Font("Tahoma", 1, 13));
		visitf = new CTextField();
		visitf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					btnNewButton.requestFocus();
				}
			}
		});
		visitf.setLineColor(new Color(10, 241, 166));
		visitf.setFont(new Font("Tahoma", 1, 12));
		visitf.setColumns(10);
		PromptSupport.setPrompt("ex. 1st, 2nd ...", visitf);
		DefaultTableModel tableModel = new DefaultTableModel(new Object[0][],
				new String[] { "S.No", "P_ID", "Patient Name", "Age", "Sex", "Contact", "Date", "Completed" }) {
			boolean[] columnEditables = new boolean[8];

			@SuppressWarnings("rawtypes")
			Class[] columnTypes = { Object.class, Object.class, Object.class, Object.class, Object.class, Object.class,
					Object.class, Boolean.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table = new JTable(tableModel);

		table.setRowHeight(20);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBackground(new Color(255, 213, 0));
		table.getTableHeader().setForeground(new Color(0, 0, 0));
		table.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
		table.getTableHeader().setFont(new Font("Tahoma", 1, 12));
		table.setSelectionBackground(new Color(40, 253, 216));
		table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 30));
		table.setRowHeight(26);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < table.getColumnCount() - 1; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table.getColumnModel().getColumn(0).setResizable(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(34);
		table.getColumnModel().getColumn(1).setResizable(true);
		table.getColumnModel().getColumn(1).setPreferredWidth(40);
		table.getColumnModel().getColumn(2).setResizable(true);
		table.getColumnModel().getColumn(2).setPreferredWidth(189);
		table.getColumnModel().getColumn(3).setResizable(true);
		table.getColumnModel().getColumn(3).setPreferredWidth(24);
		table.getColumnModel().getColumn(4).setResizable(true);
		table.getColumnModel().getColumn(4).setPreferredWidth(24);
		table.getColumnModel().getColumn(5).setResizable(true);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.getColumnModel().getColumn(6).setResizable(true);
		table.getColumnModel().getColumn(6).setPreferredWidth(70);
		table.getColumnModel().getColumn(7).setResizable(true);
		scrollPane.setViewportView(table);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				v.dispose();
				try {
					if (e.getClickCount() == 2) {
						int[] row = Ortho.table.getSelectedRows();
						for (int element : row) {
							DefaultTableModel dtm = (DefaultTableModel) Ortho.table.getModel();
							int mdr = Ortho.table.convertRowIndexToModel(element);
							String pname = dtm.getValueAt(mdr, 1).toString();
							String pname1 = dtm.getValueAt(mdr, 2).toString();
							v.lvdata(pname, pname1);
							v.setVisible(true);
						}
					}
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		searchf.addKeyListener(new KeyAdapter() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel dm = (DefaultTableModel) Ortho.table.getModel();
				TableRowSorter<DefaultTableModel> trs = new TableRowSorter(dm);
				Ortho.table.setRowSorter(trs);
				String str = searchf.getText().toUpperCase();
				if (str.length() == 0)
					trs.setRowFilter(null);
				trs.setRowFilter(RowFilter.regexFilter("(?i)" + str, new int[0]));
				searchf.requestFocus();
			}
		});
		pnamef.addKeyListener(new KeyAdapter() {
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void keyReleased(KeyEvent e) {
				int[] row = Ortho.table.getSelectedRows();
				DefaultTableModel dm2 = (DefaultTableModel) Ortho.table.getModel();
				String id = null;
				for (int element : row) {
					int mdr = Ortho.table.convertRowIndexToModel(element);
					id = dm2.getValueAt(mdr, 1).toString();
				}
				if (id == null) {
					DefaultTableModel dm = (DefaultTableModel) Ortho.table.getModel();
					Object trs = new TableRowSorter(dm);
					Ortho.table.setRowSorter((RowSorter) trs);
					String str = pnamef.getText().toUpperCase();
					if (str.length() == 0)
						((TableRowSorter) trs).setRowFilter(null);
					((TableRowSorter) trs).setRowFilter(RowFilter.regexFilter("(?i)" + str, new int[0]));
					pnamef.requestFocus();
				} else {
					id.equals(Ortho.pidf.getText());
				}

			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] row = Ortho.table.getSelectedRows();
				DefaultTableModel dm2 = (DefaultTableModel) Ortho.table.getModel();
				String id = null;
				for (int element : row) {
					int mdr = Ortho.table.convertRowIndexToModel(element);
					id = dm2.getValueAt(mdr, 1).toString();
				}
				if (id == null) {
					if (!pnamef.getText().isEmpty())
						try {
							if (visitf.getText().isEmpty()) {
								orthodata();
								DefaultTableModel dm = (DefaultTableModel) Ortho.table.getModel();
								dm.setRowCount(0);
								Ortho.ldata();
								Ortho.id();
							} else {
								orthodata();
								vstdata();
								DefaultTableModel dm = (DefaultTableModel) Ortho.table.getModel();
								dm.setRowCount(0);
								Ortho.ldata();
								Ortho.id();
							}
						} catch (Throwable e1) {
							e1.printStackTrace();
						}
					reset();
				} else {
					id.equals(Ortho.pidf.getText());
				}

				pnamef.requestFocus();
			}
		});
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					int[] row = Ortho.table.getSelectedRows();
					DefaultTableModel dm2 = (DefaultTableModel) Ortho.table.getModel();
					String id = null;
					for (int element : row) {
						int mdr = Ortho.table.convertRowIndexToModel(element);
						id = dm2.getValueAt(mdr, 1).toString();
					}
					if (id == null) {
						try {
							if (!pnamef.getText().isEmpty())
								if (visitf.getText().isEmpty()) {
									orthodata();
									DefaultTableModel dm = (DefaultTableModel) Ortho.table.getModel();
									dm.setRowCount(0);
									Ortho.ldata();
									Ortho.id();
								} else {
									orthodata();
									vstdata();
									DefaultTableModel dm = (DefaultTableModel) Ortho.table.getModel();
									dm.setRowCount(0);
									Ortho.ldata();
									Ortho.id();
								}
						} catch (Throwable e1) {
							e1.printStackTrace();
						}
						reset();
					} else {
						id.equals(Ortho.pidf.getText());
					}

					pnamef.requestFocus();
				}
			}
		});
		btnModify.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int[] row = Ortho.table.getSelectedRows();
					DefaultTableModel dm2 = (DefaultTableModel) Ortho.table.getModel();
					if (Ortho.table.getSelectedRowCount() != 0) {
						Ortho.table.disable();
						for (int element : row) {
							int mdr = Ortho.table.convertRowIndexToModel(element);
							Ortho.pidf.setText(dm2.getValueAt(mdr, 1).toString());
							pnamef.setText(dm2.getValueAt(mdr, 2).toString());
							pagef.setText(dm2.getValueAt(mdr, 3).toString());
							pphonef.setText(dm2.getValueAt(mdr, 5).toString());
							genderf.setText(dm2.getValueAt(mdr, 4).toString());
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
				Ortho.table.enable();
				try {
					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");

					TableModel dms = Ortho.table.getModel();
					int[] row = Ortho.table.getSelectedRows();
					if (Ortho.table.getSelectedRowCount() != 0) {
						if (!pnamef.getText().isEmpty()) {
							for (int element : row) {
								int mdr = Ortho.table.convertRowIndexToModel(element);
								String pname1 = dms.getValueAt(mdr, 1).toString();
								SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
								Date sd = Ortho.frmtr.parse(datef.getText());
								String date = fr.format(sd);
								String query = "UPDATE orthopt set pname=?, age=?,  gender=?, phone=?, date=?, status=? where pid='"
										+ pname1 + "'";
								PreparedStatement pst = con.prepareStatement(query);
								pst.setString(1, pnamef.getText().toUpperCase());
								pst.setString(2, pagef.getText());
								pst.setString(3, genderf.getText().toUpperCase());
								pst.setString(4, pphonef.getText());
								pst.setString(5, date);
								String qrt = "update visit set pname=?, phone=? where pid ='" + pname1 + "'";
								PreparedStatement pst1 = con.prepareStatement(qrt);
								pst1.setString(1, pnamef.getText().toUpperCase());
								pst1.setString(2, pphonef.getText());
								pst1.executeUpdate();
								if (!visitf.getText().isEmpty()) {
									PreparedStatement ps = con.prepareStatement(
											"insert into visit(pid, pname, phone, date, vst)values(?,?,?,?,?)");
									ps.setString(1, Ortho.pidf.getText());
									ps.setString(2, pnamef.getText().toUpperCase());
									ps.setString(3, pphonef.getText());
									ps.setString(5, visitf.getText());
									ps.setString(4, date);
									ps.executeUpdate();
								}
								String queri = "select vst from visit where pid='" + Ortho.pidf.getText()
										+ "' order by date";
								Statement st = con.createStatement();
								ResultSet rs = st.executeQuery(queri);
								String lvis = null;
								while (rs.next())
									lvis = rs.getString("vst");
								Boolean lvisit = Boolean.valueOf(false);
								if ((lvis != null)
										&& ((lvis.equalsIgnoreCase("last")) || (lvis.equalsIgnoreCase("completed"))
												|| (lvis.equalsIgnoreCase("done")) || (lvis.equalsIgnoreCase("end"))))
									lvisit = Boolean.valueOf(true);
								pst.setBoolean(6, lvisit.booleanValue());
								pst.executeUpdate();
							}
							reset();
						}
						DefaultTableModel dm = (DefaultTableModel) Ortho.table.getModel();
						dm.setRowCount(0);
						Ortho.ldata();
						Ortho.id();
						dm.fireTableDataChanged();
					}
					pnamef.requestFocus();

					con.close();
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		});
		btnReset.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				try {
					Ortho.id();
				} catch (Throwable ex) {
					throw new RuntimeException(ex);
				}
				pnamef.requestFocus();
				Ortho.table.clearSelection();
				Ortho.table.enable();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Ortho.table.clearSelection();
				try {
					Ortho.id();
				} catch (Throwable ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con1 = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");

					PreparedStatement ps = con1.prepareStatement("Select * from admin");
					ResultSet rs = ps.executeQuery();
					TableModel dmd = Ortho.table.getModel();
					int[] row = Ortho.table.getSelectedRows();
					if (Ortho.table.getSelectedRowCount() != 0) {
						String pass = null;
						if (rs.next()) {
							pass = rs.getString(1);
						}
						JPanel panel = new JPanel();

						JLabel label = new JLabel("   Enter Passcode");
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
													.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");

											for (int element : row) {
												int mdr = Ortho.table.convertRowIndexToModel(element);
												String pname1 = dmd.getValueAt(mdr, 1).toString();
												String query = "delete from orthopt where pid ='" + pname1 + "'";
												ps = con.prepareStatement(query);
												ps.executeUpdate();
												String qr = "delete from visit where pid = '" + pname1 + "'";
												ps = con.prepareStatement(qr);
												ps.executeUpdate();
												DefaultTableModel dm1 = (DefaultTableModel) Ortho.table.getModel();
												dm1.setRowCount(0);
												Ortho.ldata();
												Ortho.id();
											}

											con.close();
										} catch (Throwable ex) {
											// ex.printStackTrace();
										}
									} else
										JOptionPane.showConfirmDialog(null, "Wrong Passcode ,Try Again ", "Error", -1);
								}
							} catch (NullPointerException localNullPointerException) {
							}
						}
					} else {
						JOptionPane.showConfirmDialog(null, " Please First Select Row\n That You Want To Delete",
								"Error", -1);
					}
				} catch (Exception a) {
				}
			}
		});

		pphonef.addKeyListener(new KeyAdapter() {
			@Override
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
		pagef.addKeyListener(new KeyAdapter() {
			@Override
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
		pnamef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					pagef.requestFocus();
				}

			}
		});
		genderf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					pphonef.requestFocus();
				}
			}
		});
		pagef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					genderf.requestFocus();
				}
			}
		});
		datef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					visitf.requestFocus();
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
					visitf.requestFocus();
				}
			}
		});

		pphonef.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == 9) || (e.getKeyCode() == 10)) {
					datef.requestFocus();

				}

			}

		});
		pidf = new CTextField();
		pidf.setFocusable(false);
		pidf.setLineColor(new Color(255, 0, 128));
		pidf.setFont(new Font("Tahoma", 1, 11));
		pidf.setEditable(false);
		pidf.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(394)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE).addGap(443))
				.addGroup(groupLayout.createSequentialGroup().addGap(41)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
						.addGap(81).addComponent(searchf, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
						.addGap(604))
				.addGroup(groupLayout.createSequentialGroup().addGap(41)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblfname, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(lblage,
										GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(lblphone,
										GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(lbldate,
										GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(lbltreatment,
										GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
						.addGap(4)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(pidf, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
								.addComponent(pnamef, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(pagef, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(lblgender, GroupLayout.PREFERRED_SIZE, 66,
												GroupLayout.PREFERRED_SIZE)
										.addGap(4).addComponent(genderf, GroupLayout.PREFERRED_SIZE, 66,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(pphonef, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
								.addComponent(datef, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
								.addComponent(visitf, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
						.addGap(18).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE).addGap(10))
				.addGroup(groupLayout.createSequentialGroup().addGap(30)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addGap(10).addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addGap(138)
						.addComponent(btnModify, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addGap(288)
						.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(11)
				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE).addGap(32)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup().addGap(27)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(searchf, GroupLayout.PREFERRED_SIZE, 41,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 41,
												GroupLayout.PREFERRED_SIZE))))
				.addGap(8)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addGap(86)
						.addComponent(lblfname, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(lblage, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addGap(7)
						.addComponent(lblphone, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addGap(15)
						.addComponent(lbldate, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE).addGap(6)
						.addComponent(lbltreatment, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(10)
								.addComponent(pidf, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addGap(42)
								.addComponent(pnamef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addGap(3).addComponent(pagef,
												GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup().addGap(1).addComponent(lblgender,
												GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
										.addComponent(genderf, GroupLayout.PREFERRED_SIZE, 38,
												GroupLayout.PREFERRED_SIZE))
								.addGap(7)
								.addComponent(pphonef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addGap(15)
								.addComponent(datef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addGap(13)
								.addComponent(visitf, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
				.addGap(6)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(btnReset,
								GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(btnModify,
								GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(7).addComponent(btnRemove,
								GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
				.addGap(16)));
		setLayout(groupLayout);
	}

	public void orthodata() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");

		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		Date sd = frmtr.parse(datef.getText());
		String date = fr.format(sd);
		boolean lvisit = false;
		ps = con.prepareStatement(
				"insert into orthopt(pid, pname, age, gender, phone, date, status)values(?,?,?,?,?,?,?)");
		ps.setString(1, pidf.getText());
		ps.setString(2, pnamef.getText().toUpperCase());
		ps.setString(3, pagef.getText());
		ps.setString(4, genderf.getText().toUpperCase());
		ps.setString(5, pphonef.getText());
		ps.setString(6, date);
		ps.setBoolean(7, lvisit);
		ps.executeUpdate();

		con.close();
	}

	public void reset() {
		pnamef.setText(null);
		pagef.setText(null);
		genderf.setText(null);
		pphonef.setText(null);
		datef.setText(strd);
		visitf.setText(null);
		pnamef.requestFocus();
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

	public void vstdata() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");

		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		Date sd = frmtr.parse(datef.getText());
		String date = fr.format(sd);
		ps = con.prepareStatement("insert into visit(pid, pname, phone, date, vst)values(?,?,?,?,?)");
		ps.setString(1, pidf.getText());
		ps.setString(2, pnamef.getText().toUpperCase());
		ps.setString(4, date);
		ps.setString(5, visitf.getText());
		ps.setString(3, pphonef.getText());
		ps.executeUpdate();

		con.close();
	}

}
