package main.popups;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.border.DropShadowBorder;

import com.formdev.flatlaf.ui.FlatScrollPaneUI;

import designs.CLabel;
import designs.MyScrollBarUI;

public class TreatmentDetails extends JFrame {
	private JTextArea addresslbl;
	String cd = System.getProperty("user.dir");
	Toolkit t = Toolkit.getDefaultToolkit();
	Dimension dim = new Dimension(t.getScreenSize());

	int height = (int) dim.getHeight();

	private JLabel lblNewLabel;

	private final CLabel lblNewLabel_1;
	private JScrollPane scrollPane_1;
	
	public JTable ttbl;
	int width = (int) dim.getWidth();

	@SuppressWarnings("serial")
	public TreatmentDetails() {
		setBounds(width - 788, 320, 770, 409);
		setTitle("Treatments");
//    setAlwaysOnTop(true);
		setDefaultCloseOperation(2);
		setIconImage(Toolkit.getDefaultToolkit().getImage(TreatmentDetails.class.getResource("/Images/adsoft.png")));
		JScrollPane scrollPane = new JScrollPane();
		DropShadowBorder shadowborder = new DropShadowBorder();
		shadowborder.setShowLeftShadow(true);
		shadowborder.setShowRightShadow(true);
		shadowborder.setShowTopShadow(true);
		shadowborder.setShowBottomShadow(true);
		shadowborder.setShadowColor(Color.black);
		shadowborder.setShadowSize(10);
		scrollPane.setBorder(shadowborder);

		lblNewLabel_1 = new CLabel();
		lblNewLabel_1.setStrokeWidth(5);
		lblNewLabel_1.setLineColor(new Color(255, 0, 128));
		lblNewLabel_1.setFont(new Font("Times New Roman", 1, 21));
		lblNewLabel_1.setForeground(new Color(255, 128, 64));
		lblNewLabel_1.setHorizontalAlignment(0);

		lblNewLabel = new JLabel("Address");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(0);
		lblNewLabel.setFont(new Font("Tahoma", 1, 15));

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setUI(new FlatScrollPaneUI());
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane_1.setOpaque(false);
		scrollPane_1.setBorder(shadowborder);

		addresslbl = new JTextArea();
		addresslbl.setBorder(new SoftBevelBorder(0, null, null, null, null));
		addresslbl.setFont(new Font("Monospaced", 1, 15));
		addresslbl.setEditable(false);
		addresslbl.setLineWrap(true);
		addresslbl.setWrapStyleWord(true);
		addresslbl.setFocusable(false);
		scrollPane_1.setViewportView(addresslbl);
		DefaultTableModel tableModel = new DefaultTableModel(new Object[0][],

				new String[] { "ID", "Treatment", "Date" }) {
			boolean[] columnEditables = { true, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		ttbl = new JTable(tableModel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < ttbl.getColumnCount(); i++) {
			ttbl.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		ttbl.setRowHeight(32);
		ttbl.getTableHeader().setReorderingAllowed(false);
		ttbl.getTableHeader().setBackground(new Color(255, 213, 0));
		ttbl.getTableHeader().setForeground(new Color(0, 0, 0));
		ttbl.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
		ttbl.getTableHeader().setFont(new Font("Tahoma", 1, 12));
		ttbl.getTableHeader().setPreferredSize(new Dimension(ttbl.getWidth(), 38));
		ttbl.setSelectionBackground(new Color(40, 253, 216));
		ttbl.getColumnModel().getColumn(0).setResizable(true);
		ttbl.getColumnModel().getColumn(0).setMinWidth(25);
		ttbl.getColumnModel().getColumn(1).setPreferredWidth(500);
		ttbl.getColumnModel().getColumn(1).setResizable(true);
		ttbl.getColumnModel().getColumn(2).setPreferredWidth(55);
		ttbl.getColumnModel().getColumn(2).setMinWidth(25);
		ttbl.getColumnModel().getColumn(2).setResizable(true);
		ttbl.setAutoCreateRowSorter(false);
		ttbl.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(ttbl);
		scrollPane.setUI(new FlatScrollPaneUI());
		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());

		JButton btnremove = new JButton("remove");
		btnremove.setOpaque(false);
		btnremove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con1 = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
					con1.setAutoCommit(false);
					PreparedStatement ps = con1.prepareStatement("Select * from admin");
					ResultSet rs = ps.executeQuery();
					String pass = null;
					JPanel panel = new JPanel();
					JLabel label = new JLabel("  Enter Passcode");
					label.setHorizontalTextPosition(SwingConstants.CENTER);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setFont(new Font("Tahoma", 1, 14));
					label.setForeground(new Color(255, 102, 0));
					JPasswordField passf = new JPasswordField(10);
					passf.setEchoChar('*');
					passf.setFont(new Font("Tahoma", 1, 11));
					JToggleButton tb = new JToggleButton("show");
					ItemListener il = new ItemListener() {

						@Override
						public void itemStateChanged(ItemEvent e) {
							int state = e.getStateChange();
							if (state == ItemEvent.SELECTED) {
								passf.setEchoChar((char) 0);
								tb.setText("hide");
							} else {
								passf.setEchoChar('*');
								tb.setText("show");
							}
							// TODO Auto-generated method stub

						}
					};
					tb.addItemListener(il);
					panel.add(label);
					panel.add(passf);
					panel.add(tb);
					panel.setAlignmentX(Component.CENTER_ALIGNMENT);
					String[] options = new String[] { "Ok", "Cancel" };
					int option = JOptionPane.showInternalOptionDialog(null, panel, "Confirm to Delete !",
							JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					String password = null;
					if (option == 0) {
						password = new String(passf.getPassword());
					}

					if (rs.next()) {
						pass = rs.getString(1);
					}
					con1.commit();
					con1.close();
					TableModel dmd = ttbl.getModel();
					int[] row = ttbl.getSelectedRows();
					if (ttbl.getSelectedRowCount() != 0) {

						if (!password.equals(null)) {
							if (password.equalsIgnoreCase(pass)) {
								try {

									Class.forName("org.sqlite.JDBC");
									Connection con = DriverManager
											.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");

									for (int element : row) {
										int mdr = ttbl.convertRowIndexToModel(element);
										int pname1 = (int) dmd.getValueAt(mdr, 0);
										String qr = "delete from pttdata where id = " + pname1 + "";
										ps = con.prepareStatement(qr);
										ps.executeUpdate();
										DefaultTableModel dm = (DefaultTableModel) ttbl.getModel();
										dm.removeRow(mdr);
									}
									JOptionPane.showMessageDialog(getContentPane(), "Data Deleted !!! ");

									con.close();
								} catch (Throwable ex) {
									ex.printStackTrace();
								}
							} else
								JOptionPane.showConfirmDialog(getContentPane(), "Wrong Passcode ,Try Again ", "Error",
										-1);
						}
					}
				} catch (Throwable a) {
				}
			}
		});
		btnremove.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, null, null),
				new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		btnremove.setBackground(new Color(255, 0, 0));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(23)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE).addGap(55)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(96).addComponent(lblNewLabel,
										GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
						.addGap(10))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup().addGap(332)
						.addComponent(btnremove, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(321, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(6)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(16).addComponent(lblNewLabel_1,
								GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addGap(11).addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 77,
										GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnremove, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE).addGap(5)));

		getContentPane().setLayout(groupLayout);
	}

	public void tdata(String name, String phone, String address) throws Throwable {
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
		con.setAutoCommit(false);
		DefaultTableModel dm1 = (DefaultTableModel) ttbl.getModel();
		dm1.setRowCount(0);

		String query = "select id, treatment, date from pttdata where ptname = '" + name + "' and phone = '" + phone
				+ "' order by date desc";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			int ids = rs.getInt("id");
			String treat = rs.getString("treatment");
			String datef = rs.getString("date");
			SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
			Date sd = fr.parse(datef);
			SimpleDateFormat frmtr = new SimpleDateFormat("dd-MMM-yyyy");
			String date = frmtr.format(sd);
			Object[] dta = { ids, treat, date };
			dm1.addRow(dta);
		}
		lblNewLabel_1.setText(name);
		addresslbl.setText(address);
		con.commit();
		con.close();
	}
}
