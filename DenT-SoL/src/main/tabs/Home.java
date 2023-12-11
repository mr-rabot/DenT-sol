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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import main.Data;
import main.MainFrame;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.formdev.flatlaf.ui.FlatScrollPaneUI;

@SuppressWarnings("serial")
public class Home
extends Imagbg
implements Runnable
{
	private JTable table;
	private CTextField fnamef;
	private CTextField agef;
	private CTextField genderf;
	private CTextField datef;
	public static CLabel lbldatewt;
	public static CLabel lblNewLabel;
	public JTextArea treatmentsf;
	Thread t = null;
	
	int h = 0;
	
	int m = 0;
	
	int s = 0;
	
	String time = "";
	
	
	private ResultSet rs;
	
	
	private PreparedStatement ps;
	
	public Statement st;
	
	public DefaultTableModel dm;
	
	Date dt = new Date();
	
	SimpleDateFormat frmtr = new SimpleDateFormat("dd/MM/yyyy");
	
	String strd = frmtr.format(dt);
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	Dimension dim = new Dimension(tk.getScreenSize());
	
	int height = (int)dim.getHeight();
	
	int width = (int)dim.getWidth();
	private CTextField pidf;
	private CLabel lbldatew;
	private CLabel lblNewLabel_1;
	private JLabel lblfname;
	private JLabel lblage;
	private JLabel lblgender;
	private JLabel lblphone;
	private JLabel lbldate;
	private JButton btnNewButton;
	private JButton btnUpdate;
	private JLabel lbltreatment;
	private JButton btnReset;
	private JButton btnModify;
	private JButton btnRemove;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTextArea addressf;
	private CTextField phonef;
	
	public ArrayList<Data> userList() throws Throwable
	{
		ArrayList<Data> userList = new ArrayList<Data>();
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
		ps = con.prepareStatement("select * from PTdata order by date ");
		rs = ps.executeQuery();
		int SNo = 1;
		while (rs.next()) {
			SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
			Date sd = fr.parse(rs.getString("date"));
			String stdt = frmtr.format(sd);
			Data data = new Data(SNo, rs.getString("pid"), rs.getString("fname"), rs.getString("age"), rs.getString("gender"), rs.getString("phone"), stdt, rs.getString("address"));
			SNo++;
			userList.add(data);
		}
		con.close();
		return userList;
	}
	
	private void id() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
		
		// Fetch the highest numeric value used so far in the "PTdata" table
		long highestNumericValue = 0;
		String highestNumericSql = "SELECT MAX(CAST(SUBSTR(pid, 3) AS INTEGER)) FROM PTdata";
		Statement highestNumericStmt = con.createStatement();
		ResultSet highestNumericRs = highestNumericStmt.executeQuery(highestNumericSql);
		
		if (highestNumericRs.next()) {
			highestNumericValue = highestNumericRs.getLong(1);
		}
		
		highestNumericStmt.close();
		highestNumericRs.close();
		
		long startingValue = Math.max(1, highestNumericValue + 1); // Start from the highest numeric value + 1
		
		long upperLimit = 1000000000000000001L;
		long newValue = Math.min(startingValue, upperLimit);
		
		String newId = "AD" + newValue;
		pidf.setText(newId);
		
		con.close();
	}
	
	
	
	public void defheading() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
		ps = con.prepareStatement("Select * from heading");
		rs = ps.executeQuery();
		if(rs.next()) {
			String head = rs.getString(1);
			lblNewLabel.setText(head.toUpperCase());
		}
		con.close();
	}
	
	public void Ptdata() throws Throwable {
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		Date sd = frmtr.parse(datef.getText());
		String date = fr.format(sd);
		PreparedStatement ps = con.prepareStatement("insert into PTdata(pid, fname , age, gender, phone, date, address)values(?,?,?,?,?,?,?)");
		ps.setString(1, pidf.getText());
		ps.setString(2, fnamef.getText().toUpperCase());
		ps.setString(3, agef.getText());
		ps.setString(4, genderf.getText().toUpperCase());
		ps.setString(5, phonef.getText());
		ps.setString(6, date);
		ps.setString(7, addressf.getText());
		ps.executeUpdate();
		ps.close();
		con.close();
	}
	
	public void pttdata() throws Throwable {
		String fname = fnamef.getText().toUpperCase();
		
		String name = fname;
		String phone = phonef.getText();
		String treatment = treatmentsf.getText().toUpperCase();
		SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
		Date sd = frmtr.parse(datef.getText());
		String date = fr.format(sd);
		String cd = System.getProperty("user.dir");
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
		ps = con.prepareStatement("insert into pttdata(pid, ptname, phone, treatment, date)values(?,?,?,?,?)");
		ps.setString(1, pidf.getText());
		ps.setString(2, name);
		ps.setString(3, phone);
		ps.setString(4, treatment);
		ps.setString(5, date);
		ps.executeUpdate();
		con.close();
	}
	
	public void loadd() throws Throwable {
		ArrayList<Data> list = userList();
		DefaultTableModel dm = (DefaultTableModel)table.getModel();
		Object[] obj1 = new Object[8];
		for (Data element : list) {
			obj1[0] = Integer.valueOf(element.getSNo());
			obj1[1] = element.getPid();
			obj1[2] = element.getfName();
			obj1[3] = element.getAge();
			obj1[4] = element.getContact();
			obj1[5] = element.getGender();
			obj1[6] = element.getDate();
			obj1[7] = element.getAddress();
			dm.insertRow(0, obj1);
		}
	}
	
	public void clearf() {
		fnamef.setText(null);
		agef.setText(null);
		phonef.setText(null);
		addressf.setText(null);
		treatmentsf.setText(null);
		genderf.setText(null);
		datef.setText(strd);
		fnamef.requestFocus();
	}
	
	public void tlbl() {
		lbldatewt.setText(time);
	}
	
	public void run()
	{
		try {
			for (;;) {
				Calendar cal = Calendar.getInstance();
				h = cal.get(11);
				if (h > 12)
					h -= 12;
				m = cal.get(12);
				s = cal.get(13);
				SimpleDateFormat ftime = new SimpleDateFormat("hh:mm:ss aa");
				Date date = cal.getTime();
				time = ftime.format(date);
				tlbl();
				Thread.sleep(1000L);
			}
		} catch (Throwable a) {
			a.printStackTrace();
		}
	}
	
	
	public Home() throws Throwable
	{
		setBorder(new BevelBorder(0, null, null, null, null));
		init();
		t = new Thread(this);
		DropShadowBorder shadowborder1 = new DropShadowBorder();
		shadowborder1.setShowLeftShadow(true);
		shadowborder1.setShowRightShadow(true);
		shadowborder1.setShowTopShadow(true);
		shadowborder1.setShowBottomShadow(true);
		shadowborder1.setShadowColor(Color.cyan);
		shadowborder1.setShadowSize(10);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setUI(new FlatScrollPaneUI());
		scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane_1.setOpaque(false);
		addressf = new JTextArea();
		addressf.setFocusTraversalPolicyProvider(true);
		addressf.setLineWrap(true);
		addressf.setWrapStyleWord(true);
		addressf.setFont(new Font("Monospaced", 1, 16));
		addressf.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\t' || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					e.consume(); 
					treatmentsf.requestFocus();
				}
			}
		});
		scrollPane_1.setViewportView(addressf);
		scrollPane_1.setBorder(shadowborder1);
		JLabel lblTreatment = new JLabel("Treatment");
		lblTreatment.setFocusTraversalKeysEnabled(false);
		lblTreatment.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTreatment.setHorizontalAlignment(SwingConstants.CENTER);
		lblTreatment.setFocusable(false);
		lblTreatment.setFont(new Font("Tahoma", 1, 14));
		lblTreatment.setForeground(new Color(203, 255, 81));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setUI(new FlatScrollPaneUI());
		scrollPane_2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane_2.setOpaque(false);
		treatmentsf = new JTextArea();
		treatmentsf.setFocusTraversalPolicyProvider(true);
		treatmentsf.setLineWrap(true);
		treatmentsf.setWrapStyleWord(true);
		treatmentsf.setFont(new Font("Monospaced", 1, 16));
		treatmentsf.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\t' || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					e.consume(); 
					btnNewButton.requestFocus();
				}
			}
		});
		scrollPane_2.setViewportView(treatmentsf);
		scrollPane_2.setBorder(shadowborder1);
		
		phonef = new CTextField();
		phonef.setLineColor(new Color(0, 255, 64));
		phonef.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent e) {
				String phone = phonef.getText();
				char c = e.getKeyChar();
				if ((Character.isLetter(c)) || (phone.length() > 9)) {
					e.consume();
					phonef.setEditable(false);
					if (e.getKeyCode() == 8)
						phonef.setEditable(true);
				} else {
					phonef.setEditable(true);
				}
				
			}
		});
		phonef.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_TAB) || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					agef.requestFocus();
				}
			}
		});
		phonef.setFont(new Font("Tahoma", 1, 12));
		phonef.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lbldatew, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(lbldatewt, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(27)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)))
					.addGap(121)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
					.addGap(383))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(112)
					.addComponent(pidf, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(lblfname, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(fnamef, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(lblphone, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(phonef, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(lblage, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(agef, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addComponent(lblgender, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(genderf, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lbldate, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbltreatment, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTreatment, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(datef, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addComponent(scrollPane_2))
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(167)
					.addComponent(btnModify, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(242)
					.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lbldatew, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbldatewt, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(9)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(pidf, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(lblfname, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
								.addComponent(fnamef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
							.addGap(19)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(lblphone, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
								.addComponent(phonef, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
							.addGap(7)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblage, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(agef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(lblgender, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
								.addComponent(genderf, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
							.addGap(14)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(lbldate, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
									.addGap(13)
									.addComponent(lbltreatment, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
									.addGap(50)
									.addComponent(lblTreatment, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
									.addGap(30))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(datef, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
									.addGap(8)
									.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
									.addGap(20)
									.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
									.addGap(11))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(212)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(9)
							.addComponent(btnModify, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(9)
							.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addGap(11))
		);
		setLayout(groupLayout);
		t.start();
		defheading();
		loadd();
		id();
	}
	
	public void init() {
		setFont(new Font("Tahoma", 1, 13));
		setBounds(0, 0, 1243, 614);
		lblNewLabel = new CLabel();
		lblNewLabel.setFocusable(false);
		lblNewLabel.setForeground(new Color(185, 255, 198));
		lblNewLabel.setStrokeWidth(6);
		lblNewLabel.setLineColor(new Color(255, 128, 0));
		lblNewLabel.setOpaque(false);
		lblNewLabel.setFont(new Font("Tahoma", 1, 26));
		lblNewLabel.setHorizontalAlignment(0);
		lblNewLabel_1 = new CLabel();
		lblNewLabel_1.setFocusable(false);
		lblNewLabel_1.setStrokeWidth(4);
		lblNewLabel_1.setForeground(new Color(3, 252, 184));
		lblNewLabel_1.setLineColor(new Color(255, 128, 0));
		lblNewLabel_1.setText("Registration");
		lblNewLabel_1.setOpaque(false);
		lblNewLabel_1.setFont(new Font("Tahoma", 1, 17));
		lblNewLabel_1.setHorizontalAlignment(0);
		DropShadowBorder shadowborder = new DropShadowBorder();
		shadowborder.setShowLeftShadow(true);
		shadowborder.setShowRightShadow(true);
		shadowborder.setShowTopShadow(true);
		shadowborder.setShowBottomShadow(true);
		shadowborder.setShadowColor(new Color(0, 240, 249));
		shadowborder.setShadowSize(10);
		scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		scrollPane.setFocusTraversalKeysEnabled(false);
		scrollPane.setOpaque(false);
		scrollPane.setUI(new FlatScrollPaneUI());
		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());
		scrollPane.setBorder(shadowborder);
		lblfname = new JLabel("Full Name");
		lblfname.setFocusTraversalKeysEnabled(false);
		lblfname.setFocusable(false);
		lblfname.setForeground(new Color(203, 255, 81));
		lblfname.setFont(new Font("Tahoma", 1, 14));
		fnamef = new CTextField();
		fnamef.setLineColor(new Color(0, 255, 64));
		fnamef.setFont(new Font("Tahoma", 1, 12));
		fnamef.setColumns(10);
		fnamef.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_TAB) || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					phonef.requestFocus();
				}
			}
		});
		fnamef.requestFocus();
		lblage = new JLabel("Age");
		lblage.setFocusable(false);
		lblage.setFocusTraversalKeysEnabled(false);
		lblage.setForeground(new Color(203, 255, 81));
		lblage.setFont(new Font("Tahoma", 1, 14));
		agef = new CTextField();
		agef.setLineColor(new Color(0, 255, 64));
		agef.setFont(new Font("Tahoma", 1, 12));
		agef.setColumns(10);
		agef.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_TAB) || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					genderf.requestFocus();
				}
			}
		});
		lblgender = new JLabel("Gender");
		lblgender.setFocusTraversalKeysEnabled(false);
		lblgender.setFocusable(false);
		lblgender.setForeground(new Color(203, 255, 81));
		lblgender.setFont(new Font("Tahoma", 1, 14));
		genderf = new CTextField();
		genderf.setLineColor(new Color(0, 255, 64));
		genderf.setHorizontalAlignment(0);
		PromptSupport.setPrompt(" M/F ", genderf);
		genderf.setFont(new Font("Tahoma", 1, 12));
		genderf.setColumns(10);
		genderf.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_TAB) || (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					datef.requestFocus();
				}
			}
		});
		lblphone = new JLabel("Contact");
		lblphone.setFocusTraversalKeysEnabled(false);
		lblphone.setFocusable(false);
		lblphone.setForeground(new Color(203, 255, 81));
		lblphone.setFont(new Font("Tahoma", 1, 14));
		lbldate = new JLabel("Date");
		lbldate.setFocusTraversalKeysEnabled(false);
		lbldate.setFocusable(false);
		lbldate.setForeground(new Color(203, 255, 81));
		lbldate.setFont(new Font("Tahoma", 1, 14));
		datef = new CTextField();
		datef.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
					e.consume(); // Consume the event to prevent further processing
					
					addressf.requestFocus();
				}
			}
		});
		datef.setLineColor(new Color(0, 255, 64));
		datef.setText(strd);
		datef.setForeground(new Color(0, 0, 128));
		datef.setFont(new Font("Tahoma", 1, 12));
		datef.setColumns(10);
		lbltreatment = new JLabel("Address");
		lbltreatment.setFocusTraversalKeysEnabled(false);
		lbltreatment.setFocusable(false);
		lbltreatment.setForeground(new Color(203, 255, 81));
		lbltreatment.setFont(new Font("Tahoma", 1, 14));
		DropShadowBorder shadowborder2 = new DropShadowBorder();
		shadowborder2.setShowLeftShadow(true);
		shadowborder2.setShowRightShadow(true);
		shadowborder2.setShowTopShadow(true);
		shadowborder2.setShowBottomShadow(true);
		shadowborder2.setShadowColor(new Color(0, 235, 250));
		shadowborder2.setShadowSize(10);
		btnNewButton = new JButton("Add");
		btnNewButton.addMouseListener(new MouseAdapter()
		{
			
			public void mouseClicked(MouseEvent e)
			{
				if (e.getButton() == 3) {
					if (fnamef.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "\tPatient Name Can't be Empty \n\tPlease Enter Patient Details\n\t     And Try Again !!!");
					} else {
						try {
							JPanel p = new JPanel();
							JLabel l = new JLabel();
							l.setText("Add To ..");
							l.setForeground(new Color(128, 0, 0));
							l.setFont(new Font("Arial Black", 1, 14));
							p.setSize(100, 200);
							p.add(l);
							int value = JOptionPane.showOptionDialog(null, p, "DenT-SoL", 0, -1, null, new String[] { "Appointment", "Ortho" }, Integer.valueOf(1));
							if (value == 0) {
								String apdatef = JOptionPane.showInputDialog(null, "Enter Appontment Date(dd/MM/yyyy)", Integer.valueOf(0));
								SimpleDateFormat frmtr1 = new SimpleDateFormat("dd/MM/yyyy");
								SimpleDateFormat frmtr = new SimpleDateFormat("dd/MM/yyyy");
								String cd = System.getProperty("user.dir");
								Class.forName("org.sqlite.JDBC");
								Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
								SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
								Date sd = frmtr1.parse(datef.getText());
								String date = fr.format(sd);
								String a = apdatef.toString();
								Date asd = frmtr.parse(a);
								String apstd = fr.format(asd);
								PreparedStatement ps = con.prepareStatement("insert into apnt(pid,pname, age, gender, phone, date, apdate)values(?,?,?,?,?,?,?)");
								ps.setString(1, Appointment.pidf.getText());
								ps.setString(2, fnamef.getText().toUpperCase());
								ps.setString(3, agef.getText().toUpperCase());
								ps.setString(4, genderf.getText().toUpperCase());
								ps.setString(5, phonef.getText().toUpperCase());
								ps.setString(6, date);
								ps.setString(7, apstd);
								ps.executeUpdate();
								JOptionPane.showMessageDialog(null, "Appointment Added");
								DefaultTableModel dm = (DefaultTableModel)Appointment.table.getModel();
								dm.fireTableDataChanged();
								dm.setRowCount(0);
								Appointment.loadd();
								Appointment.id();
								ps.close();
								con.close();
							}
							if (value == 1) {
								String cd = System.getProperty("user.dir");
								Class.forName("org.sqlite.JDBC");
								Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/ortho.db");
								SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
								Date sd = Home.this.frmtr.parse(datef.getText());
								String date = fr.format(sd);
								boolean lvisit = false;
								PreparedStatement ps = con.prepareStatement("insert into orthopt(pid, pname, age, gender, phone, date, status)values(?,?,?,?,?,?,?)");
								ps.setString(1, Ortho.pidf.getText());
								ps.setString(2, fnamef.getText().toUpperCase());
								ps.setString(3, agef.getText().toUpperCase());
								ps.setString(4, genderf.getText().toUpperCase());
								ps.setString(5, phonef.getText().toUpperCase());
								ps.setString(6, date);
								ps.setBoolean(7, lvisit);
								ps.executeUpdate();
								
								String visitf = JOptionPane.showInputDialog(null, "Enter Visit (ex. 1st, 2nd...) ", Integer.valueOf(0));
								PreparedStatement ps1 = con.prepareStatement("insert into visit(pid, pname, phone, date, vst)values(?,?,?,?,?)");
								ps1.setString(1, Ortho.pidf.getText());
								ps1.setString(2, fnamef.getText().toUpperCase());
								ps1.setString(4, date);
								ps1.setString(5, visitf.toString().toUpperCase());
								ps1.setString(3, phonef.getText().toUpperCase());
								ps1.executeUpdate();
								DefaultTableModel dm = (DefaultTableModel)Ortho.table.getModel();
								dm.setRowCount(0);
								Ortho.ldata();
								Ortho.id();
								MainFrame.apts();
								con.close();
								
								JOptionPane.showMessageDialog(null, " Added to Ortho ");
							}
							
							
						}
						catch (Throwable localThrowable) {}
					}
					
				}
				
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 100));
		btnNewButton.setBorder(shadowborder2);
		btnNewButton.setOpaque(false);
		btnNewButton.setBackground(new Color(0, 249, 242));
		btnNewButton.setFont(new Font("Tahoma", 1, 13));
		btnUpdate = new JButton("Update");
		btnUpdate.setForeground(new Color(255, 255, 100));
		btnUpdate.setBorder(shadowborder2);
		btnUpdate.setOpaque(false);
		btnUpdate.setBackground(new Color(0, 255, 255));
		btnUpdate.setFont(new Font("Tahoma", 1, 13));
		btnReset = new JButton("Reset");
		btnReset.setForeground(new Color(255, 255, 100));
		btnReset.setBorder(shadowborder2);
		btnReset.setOpaque(false);
		btnReset.setBackground(new Color(255, 0, 255));
		btnReset.setFont(new Font("Tahoma", 1, 13));
		btnModify = new JButton("Modify");
		btnModify.setForeground(new Color(255, 255, 100));
		btnModify.setBorder(shadowborder2);
		btnModify.setOpaque(false);
		btnModify.setBackground(Color.CYAN);
		btnModify.setFont(new Font("Tahoma", 1, 13));
		btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getButton()==MouseEvent.BUTTON3) {
					try {
						String cd = System.getProperty("user.dir");
						Class.forName("org.sqlite.JDBC");
						Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
						PreparedStatement ps = con.prepareStatement("Select * from admin");
						ResultSet rs = ps.executeQuery();
						String pass = null;
						if(rs.next()) {
							pass = rs.getString(1);
						}
						con.close();
						String op = JOptionPane.showInputDialog(null, "Enter old Passcode ", "DenT-SoL", 2);
						if (op.equals("")) {
							JOptionPane.showConfirmDialog(null, "Please try again and  Enter passcode, passcode should not Empty ", "Error", -1);
							
						}
						else if (op.equalsIgnoreCase(pass)) {
							String newPass = JOptionPane.showInputDialog(null, "Enter New Password ", "DenT-SoL", 2);
							if (!newPass.equalsIgnoreCase("")) {
								
								con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
								String qrt = "update admin set psd ='" +newPass + "'";
								ps = con.prepareStatement(qrt);
								ps.executeUpdate();
								JOptionPane.showConfirmDialog(null, "Updated Successfully !!! ", "DenT-SoL", -1);
							}
							
						} else {
							JOptionPane.showConfirmDialog(null, "Wrong Passcode ,Try Again ", "Error", -1);
						}
						con.close();
					} catch (Throwable ea) {
						System.err.println();
					}
				}
			}
		});
		
		DropShadowBorder shadowborder1 = new DropShadowBorder();
		shadowborder1.setShowLeftShadow(true);
		shadowborder1.setShowRightShadow(true);
		shadowborder1.setShowTopShadow(true);
		shadowborder1.setShowBottomShadow(true);
		shadowborder1.setShadowColor(Color.red);
		shadowborder1.setShadowSize(8);
		btnRemove.setBorder(shadowborder1);
		btnRemove.setOpaque(false);
		btnRemove.setBackground(Color.RED);
		btnRemove.setForeground(new Color(255, 255, 100));
		btnRemove.setFont(new Font("Tahoma", 1, 13));
		Date dta = new Date();
		SimpleDateFormat frmt = new SimpleDateFormat("EEEE , dd MMMM yyyy");
		String strda = frmt.format(dta);
		lbldatew = new CLabel();
		lbldatew.setFocusable(false);
		lbldatew.setLineColor(new Color(0, 255, 255));
		lbldatew.setBackground(new Color(0, 255, 255));
		lbldatew.setForeground(new Color(255, 255, 0));
		lbldatew.setText(strda);
		lbldatew.setOpaque(false);
		lbldatew.setHorizontalAlignment(0);
		lbldatew.setFont(new Font("Tahoma", 1, 13));
		lbldatewt = new CLabel();
		lbldatewt.setFocusable(false);
		lbldatewt.setLineColor(new Color(0, 255, 255));
		lbldatewt.setBackground(new Color(0, 255, 255));
		lbldatewt.setForeground(new Color(255, 255, 0));
		lbldatewt.setOpaque(false);
		lbldatewt.setHorizontalAlignment(0);
		lbldatewt.setFont(new Font("Tahoma", 1, 13));
		pidf = new CTextField();
		pidf.setFocusable(false);
		pidf.setLineColor(new Color(0, 255, 64));
		pidf.setFont(new Font("Tahoma", 1, 11));
		pidf.setColumns(10);
		pidf.setEditable(false);
		DefaultTableModel tableModel = new DefaultTableModel(new Object[0][], new String[] { "S.No", "P_ID", "Full Name", "Age", "Sex", "Contact", "Date" }) {
			final boolean[] columnEditables = new boolean[7];
			
			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		};
		table = new JTable(tableModel);
		table.setFocusable(false);
		table.setFocusTraversalKeysEnabled(false);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table.setRowHeight(20);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBackground(new Color(255, 213, 0));
		table.getTableHeader().setForeground(new Color(0, 0, 0));
		table.getTableHeader().setBorder(new BevelBorder(0, null, null, null, null));
		table.getTableHeader().setFont(new Font("Tahoma", 1, 12));
		table.setSelectionBackground(new Color(40, 253, 216));
		table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 30));
		table.setRowHeight(26);
		table.getColumnModel().getColumn(0).setResizable(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setResizable(true);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setResizable(true);
		table.getColumnModel().getColumn(2).setPreferredWidth(210);
		table.getColumnModel().getColumn(3).setResizable(true);
		table.getColumnModel().getColumn(3).setPreferredWidth(18);
		table.getColumnModel().getColumn(4).setResizable(true);
		table.getColumnModel().getColumn(4).setPreferredWidth(24);
		table.getColumnModel().getColumn(5).setResizable(true);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		table.getColumnModel().getColumn(6).setResizable(true);
		table.setAutoCreateRowSorter(false);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		fnamef.addKeyListener(new KeyAdapter()
		{
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent e) {
				int[] row = table.getSelectedRows();
				DefaultTableModel dm2 = (DefaultTableModel)table.getModel();
				String ids = null;
				for (int element : row) {
					int mdr = table.convertRowIndexToModel(element);
					ids = dm2.getValueAt(mdr, 1).toString();
				}
				if (ids == null) {
					DefaultTableModel dm = (DefaultTableModel)table.getModel();
					Object trs = new TableRowSorter(dm);
					table.setRowSorter((RowSorter)trs);
					String str = fnamef.getText().toUpperCase();
					if (str.length() == 0)
						((TableRowSorter)trs).setRowFilter(null);
					((TableRowSorter)trs).setRowFilter(RowFilter.regexFilter("(?i)" + str, new int[0]));
					fnamef.requestFocus();
				} else { ids.equals(pidf.getText());
				}
				
			}
			
		});
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int[] row = table.getSelectedRows();
				DefaultTableModel dm2 = (DefaultTableModel)table.getModel();
				String id = null;
				for (int element : row) {
					int mdr = table.convertRowIndexToModel(element);
					id = dm2.getValueAt(mdr, 1).toString();
				}
				if (id == null) {
					if (!fnamef.getText().isEmpty()) {
						try {
							if (treatmentsf.getText().isEmpty()) {
								Ptdata();
								DefaultTableModel dm = (DefaultTableModel)table.getModel();
								dm.setRowCount(0);
								DefaultTableModel dm1 = (DefaultTableModel)PatientDetails.table.getModel();
								dm1.setRowCount(0);
								PatientDetails.loadL();
								loadd();
								id();
								dm1.fireTableDataChanged();
							} else {
								Ptdata();
								pttdata();
								DefaultTableModel dm = (DefaultTableModel)table.getModel();
								dm.setRowCount(0);
								DefaultTableModel dm1 = (DefaultTableModel)PatientDetails.table.getModel();
								dm1.setRowCount(0);
								PatientDetails.loadL();
								loadd();
								id();
								dm1.fireTableDataChanged();
							}
						} catch (Throwable ex) {
							System.err.println("Error In SAVEb : " + ex);
						}
						clearf();
					}
					fnamef.requestFocus();
				} else { id.equals(pidf.getText());
				}
				
			}
		});
		btnNewButton.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				int[] row = table.getSelectedRows();
				DefaultTableModel dm2 = (DefaultTableModel)table.getModel();
				String id = null;
				for (int element : row) {
					int mdr = table.convertRowIndexToModel(element);
					id = dm2.getValueAt(mdr, 1).toString();
				}
				if (id == null) {
					if (!fnamef.getText().isEmpty()) {
						try {
							if (treatmentsf.getText().isEmpty()) {
								Ptdata();
								DefaultTableModel dm = (DefaultTableModel)table.getModel();
								dm.setRowCount(0);
								DefaultTableModel dm1 = (DefaultTableModel)PatientDetails.table.getModel();
								dm1.setRowCount(0);
								PatientDetails.loadL();
								loadd();
								id();
								dm1.fireTableDataChanged();
							} else {
								Ptdata();
								pttdata();
								DefaultTableModel dm = (DefaultTableModel)table.getModel();
								dm.setRowCount(0);
								DefaultTableModel dm1 = (DefaultTableModel)PatientDetails.table.getModel();
								dm1.setRowCount(0);
								PatientDetails.loadL();
								loadd();
								id();
								dm1.fireTableDataChanged();
							}
						} catch (Throwable ex) {
							System.err.println("Error In SAVEb : " + ex);
						}
						clearf();
					}
					fnamef.requestFocus();
				} else { id.equals(pidf.getText());
				}
				
			}
		});
		btnModify.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) {
				try {
					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
					
					
					int[] row = table.getSelectedRows();
					DefaultTableModel dm2 = (DefaultTableModel)table.getModel();
					if (table.getSelectedRowCount() != 0) {
						table.disable();
						for (int element : row) {
							int mdr = table.convertRowIndexToModel(element);
							pidf.setText(dm2.getValueAt(mdr, 1).toString());
							fnamef.setText(dm2.getValueAt(mdr, 2).toString());
							agef.setText(dm2.getValueAt(mdr, 3).toString());
							phonef.setText(dm2.getValueAt(mdr, 5).toString());
							genderf.setText(dm2.getValueAt(mdr, 4).toString());
							
							
							String pid = dm2.getValueAt(mdr, 1).toString();
							if (pid != null) {
								PreparedStatement ps = con.prepareStatement("select address from PTdata where pid ='" + pid + "'");
								ResultSet rs = ps.executeQuery();
								addressf.setText(rs.getString("address"));
							}
						}
						con.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) {
				table.clearSelection();
				try {
					id();
				} catch (Throwable ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		btnUpdate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				table.enable();
				try {
					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
					TableModel dms = table.getModel();
					int[] row = table.getSelectedRows();
					if ((table.getSelectedRowCount() != 0) && 
							(!fnamef.getText().isEmpty())) {
						for (int element : row) {
							int mdr = table.convertRowIndexToModel(element);
							String pname1 = dms.getValueAt(mdr, 1).toString();
							SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
							Date sd = frmtr.parse(datef.getText());
							String date = fr.format(sd);
							String query = "UPDATE PTdata set fname=?, age=?,  gender=?, phone=?, date=?, address=? where pid='" + pname1 + "'";
							PreparedStatement pst = con.prepareStatement(query);
							pst.setString(1, fnamef.getText().toUpperCase());
							pst.setString(2, agef.getText());
							pst.setString(5, date);
							pst.setString(3, genderf.getText().toUpperCase());
							pst.setString(4, phonef.getText());
							pst.setString(6, addressf.getText());
							pst.executeUpdate();
							String pname = fnamef.getText().toUpperCase();
							String qrt = "update pttdata set ptname=?, phone=? where pid='" + pname1 + "'";
							PreparedStatement pst1 = con.prepareStatement(qrt);
							pst1.setString(1, pname);
							pst1.setString(2, phonef.getText());
							pst1.executeUpdate();
							if (!treatmentsf.getText().isEmpty()) {
								ps = con.prepareStatement("insert into pttdata(pid, ptname, phone, treatment, date)values(?,?,?,?,?)");
								ps.setString(1, pidf.getText());
								ps.setString(2, pname);
								ps.setString(3, phonef.getText());
								ps.setString(4, treatmentsf.getText().toUpperCase());
								ps.setString(5, date);
								ps.executeUpdate();
							}
							clearf();
						}
						ps.close();
					}
					DefaultTableModel dm = (DefaultTableModel)table.getModel();
					dm.setRowCount(0);
					loadd();
					DefaultTableModel dm1 = (DefaultTableModel)PatientDetails.table.getModel();
					dm1.setRowCount(0);
					PatientDetails.loadL();
					dm.fireTableDataChanged();
					id();
					con.close();
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
				fnamef.requestFocus();
			}
		});
		btnReset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				try {
					clearf();
					id();
					fnamef.requestFocus();
					table.clearSelection();
					table.enable();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				try {
					String cd = System.getProperty("user.dir");
					Class.forName("org.sqlite.JDBC");
					Connection con1 = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
					ps = con1.prepareStatement("Select * from admin");
					rs = ps.executeQuery();
					String pass = null;
					if(rs.next()) {
						pass = rs.getString(1);
					}
					
					TableModel dmd = table.getModel();
					int[] row = table.getSelectedRows();
					if (table.getSelectedRowCount() != 0) {
						String op = JOptionPane.showInputDialog(null, "Enter Passcode ", "Confirm to delete", 2);
						try {
							if (!op.equals(null)) {
								if (op.equalsIgnoreCase(pass)) {
									try {
										Class.forName("org.sqlite.JDBC");
										Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
										for (int element : row) {
											int mdr = table.convertRowIndexToModel(element);
											String pname1 = dmd.getValueAt(mdr, 1).toString();
											String query = "delete from PTdata where pid ='" + pname1 + "'";
											ps = con.prepareStatement(query);
											ps.executeUpdate();
											String qr = "delete from pttdata where pid = '" + pname1 + "'";
											ps = con.prepareStatement(qr);
											ps.executeUpdate();
											DefaultTableModel dm = (DefaultTableModel)table.getModel();
											dm.setRowCount(0);
											loadd();
											DefaultTableModel dm1 = (DefaultTableModel)PatientDetails.table.getModel();
											dm1.setRowCount(0);
											PatientDetails.loadL();
											id();
										}
										con.close();
									} catch (Throwable ex) {
										ex.printStackTrace();
									}
								} else
									JOptionPane.showConfirmDialog(null, "Wrong Passcode ,Try Again ", "Error", -1);
							}
						} catch (NullPointerException localNullPointerException) {}
					}
				}catch(Throwable a) {}
			}
		});
		
		agef.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (Character.isLetter(c)) {
					agef.setEditable(false);
					if (e.getKeyCode() == 8)
						agef.setEditable(true);
				} else if(e.getKeyChar() == '\t'){
					addressf.requestFocus();
				}else {
					agef.setEditable(true);
				}
				
			}
		});
		lblNewLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2) {
					try {
						String cd = System.getProperty("user.dir");
						Class.forName("org.sqlite.JDBC");
						Connection con1 = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
						ps = con1.prepareStatement("Select * from epsd");
						rs = ps.executeQuery();
						String pass = null;
						if(rs.next()) {
							pass = rs.getString(1);
						}
						String op = JOptionPane.showInputDialog(null, "Enter Passcode ", "update heading", 2);
						if (op.equals("")) {
							JOptionPane.showConfirmDialog(null, "Please try again and  Enter passcode passcode  passcode should not Empty ", "Error", -1);
							
						}
						else if (op.equalsIgnoreCase(pass)) {
							String head = JOptionPane.showInputDialog(null, "Enter New Heading ", "update heading", 2);
							if (!head.equalsIgnoreCase("")) {
								ps.close();
								con1.close();
								Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
								String qrt = "update heading set hdg='" + head + "'";
								PreparedStatement ps1 = con.prepareStatement(qrt);
								ps1.executeUpdate();
								defheading();
								con.close();
							}
						} else {
							JOptionPane.showConfirmDialog(null, "Wrong Passcode ,Try Again ", "Error", -1);
						}
					} catch (Throwable ea) {
						ea.printStackTrace();
					}
				}else if(e.getButton()==MouseEvent.BUTTON3) {
					try {
						String cd = System.getProperty("user.dir");
						Class.forName("org.sqlite.JDBC");
						Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
						PreparedStatement ps = con.prepareStatement("Select * from epsd");
						ResultSet rs = ps.executeQuery();
						String pass = null;
						if(rs.next()) {
							pass = rs.getString(1);
						}
						con.close();
						String op = JOptionPane.showInputDialog(null, "Enter old Passcode ", "DenT-SoL", 2);
						if (op.equals("")) {
							JOptionPane.showConfirmDialog(null, "Please try again and  Enter passcode, passcode should not Empty ", "Error", -1);
							
						}
						else if (op.equalsIgnoreCase(pass)) {
							String newPass = JOptionPane.showInputDialog(null, "Enter New Password ", "DenT-SoL", 2);
							if (!newPass.equalsIgnoreCase("")) {
								
								con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/Admin.db");
								String qrt = "update epsd set psd ='" +newPass + "'";
								ps = con.prepareStatement(qrt);
								ps.executeUpdate();
								JOptionPane.showConfirmDialog(null, "Updated Successfully !!! ", "DenT-SoL", -1);
							}
							
						} else {
							JOptionPane.showConfirmDialog(null, "Wrong Passcode ,Try Again ", "Error", -1);
						}
						con.close();
					} catch (Throwable ea) {
						ea.printStackTrace();
					}
					
				}
			}
		});
	}

	
}


