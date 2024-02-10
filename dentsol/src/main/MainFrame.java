package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.jdesktop.swingx.border.DropShadowBorder;

import designs.CustomTP;
import designs.OpPane;
import main.tabs.Appointment;
import main.tabs.Home;
import main.tabs.Lab;
import main.tabs.Ortho;
import main.tabs.PatientDetails;

public class MainFrame {
	private static int aptsn;
	static String cd = System.getProperty("user.dir");

	private static void addFileToZip(String path, String srcFile, ZipOutputStream zos) throws IOException {
		try (FileInputStream fis = new FileInputStream(srcFile)) {
			ZipEntry zipEntry = new ZipEntry(path + File.separator + new File(srcFile).getName());
			zos.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}
		}
	}

	private static void addFolderToZip(String path, String sourceFolder, ZipOutputStream zos) throws IOException {
		File folder = new File(sourceFolder);
		for (String fileName : Objects.requireNonNull(folder.list())) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), sourceFolder + File.separator + fileName, zos);
			} else {
				addFileToZip(path + File.separator + folder.getName(), sourceFolder + File.separator + fileName, zos);
			}
		}
	}

	public static void apts() throws Throwable {
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
		con.setAutoCommit(false);
		Date dt = new Date();
		SimpleDateFormat frmtr = new SimpleDateFormat("yyyy-MM-dd");
		String strd = frmtr.format(dt);
		String queri = "select count(pid) as pid from apnt where apdate ='" + strd + "';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(queri);
		aptsn = 0;
		while (rs.next()) {
			aptsn = rs.getInt("pid");
		}
		st.close();
		con.commit();
		con.close();
	}

	public static void displayTray() throws AWTException {
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		TrayIcon trayIcon = new TrayIcon(image, "DenT-SoL");
		trayIcon.setImageAutoSize(true);
		tray.add(trayIcon);
		trayIcon.displayMessage("Today We Have  ( " + aptsn + " ) Appointmnts", "DenT-SoL", TrayIcon.MessageType.INFO);
	}

	private static File extractZipEntry(ZipInputStream zis, String destinationFolder) throws IOException {
		ZipEntry entry = zis.getNextEntry();
		if (entry != null) {
			String filePath = destinationFolder + File.separator + entry.getName();
			File entryFile = new File(filePath);

			if (entry.isDirectory()) {
				entryFile.mkdirs();
			} else {

				entryFile.getParentFile().mkdirs();

				try (FileOutputStream fos = new FileOutputStream(entryFile)) {
					byte[] buffer = new byte[1024];
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
				}
			}

			zis.closeEntry();
			return entryFile;
		} else {
			return null;
		}
	}

	private Appointment ap;
	private JButton btnNewButton;
	private Path databasePath;
	Toolkit t = Toolkit.getDefaultToolkit();
	Dimension dim = new Dimension(t.getScreenSize());
	public JFrame frmDentsol;
	private Home h;
	int height = (int) dim.getHeight();

	private Lab lb;

	private Ortho o;

	private PatientDetails pd;


	int width = (int) dim.getWidth();

	public MainFrame() throws Throwable {
		h = new Home();
		pd = new PatientDetails();
		o = new Ortho();
		ap = new Appointment();
		lb = new Lab();
		initialize();
		apts();
		if (aptsn != 0) {
			if (SystemTray.isSupported()) {
				displayTray();
			} else {
				JOptionPane.showConfirmDialog(null, "System tray not supported!", "Error", 0);
			}
		}

		Timer timer = new Timer(3 * 60 * 60 * 1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshApplication();
			}
		});

		timer.start();

	}

	private void exportData() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify export directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File exportDirectory = fileChooser.getSelectedFile();
			File exportFile = new File(exportDirectory, "database.zip");

			try {

				String sourceFolder = cd + "/ImpFiles/database/";

				try (FileOutputStream fos = new FileOutputStream(exportFile);
						ZipOutputStream zos = new ZipOutputStream(fos)) {

					addFolderToZip("", sourceFolder, zos);
				}

				String successMessage = "Data exported successfully to: " + exportFile.getAbsolutePath();
				JOptionPane.showMessageDialog(null, successMessage, "Export Successful",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (IOException ex) {
				ex.printStackTrace();
				String errorMessage = "Error exporting data to: " + exportFile.getAbsolutePath();
				JOptionPane.showMessageDialog(null, errorMessage, "Export Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void importData() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify import ZIP file");
		fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || file.getName().toLowerCase().endsWith(".zip");
			}

			@Override
			public String getDescription() {
				return "ZIP Files (*.zip)";
			}
		});

		int userSelection = fileChooser.showOpenDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File importFile = fileChooser.getSelectedFile();

			try {
				String destinationFolder = cd + "/ImpFiles/";
				String databaseFolder = "database";
				databasePath = Paths.get(destinationFolder);

				try (FileInputStream fis = new FileInputStream(importFile);
						ZipInputStream zis = new ZipInputStream(fis)) {

					File destinationDir = new File(destinationFolder, databaseFolder);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}

					if (!Files.exists(databasePath)) {
						Files.createDirectories(databasePath);
					}

					while (extractZipEntry(zis, databasePath.toString()) != null) {

					}

					String successMessage = "Data imported successfully from: " + importFile.getAbsolutePath();
					JOptionPane.showMessageDialog(null, successMessage, "Import Successful",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (IOException ex) {
					ex.printStackTrace();
					String errorMessage = "Error importing data from: " + importFile.getAbsolutePath();
					JOptionPane.showMessageDialog(null, errorMessage, "Import Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Throwable a) {
				a.printStackTrace();
			}
			restartApplication();
		}
	}

	private void initialize() throws Throwable {
		frmDentsol = new JFrame();
		frmDentsol.setTitle("DenT-SoL");
		frmDentsol.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frmDentsol.setBackground(new Color(0, 0, 0));
		frmDentsol.setExtendedState(6);
		frmDentsol.setSize(width, height - 80);

		ImageIcon icon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/h.png")));
		ImageIcon icon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/pd.png")));
		ImageIcon icon3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/lb.png")));
		ImageIcon icon4 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/ot.png")));
		ImageIcon icon5 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/ap1.png")));
		frmDentsol.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/Images/ic1.png")));
		frmDentsol.setDefaultCloseOperation(3);
		CustomTP tabbedPane = new CustomTP();

		final int[] initialOptionIndex = { 0 };
		String[] options = { "Yes", "No" };

		frmDentsol.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				OpPane n = new OpPane();

				int confirmed = JOptionPane.showOptionDialog(null, n, "DenT-SoL", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[initialOptionIndex[0]]);

				if (confirmed == 0) {
					frmDentsol.dispose();
				} else if (confirmed == 1) {
					frmDentsol.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				}
			}
		});

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_TAB) {
					initialOptionIndex[0] = (initialOptionIndex[0] + 1) % options.length;
					UIManager.put("Button.defaultButtonFollowsFocus", initialOptionIndex[0]);
				}
				return false;
			}
		});
		DropShadowBorder shadowborder = new DropShadowBorder();
		shadowborder.setShowLeftShadow(true);
		shadowborder.setShowRightShadow(true);
		shadowborder.setShowTopShadow(true);
		shadowborder.setShowBottomShadow(true);
		shadowborder.setShadowColor(Color.black);
		shadowborder.setShadowSize(3);
		tabbedPane.setBorder(shadowborder);
		tabbedPane.setFont(new Font("Sanskrit Text", 1, 14));
		tabbedPane.setForeground(new Color(101, 24, 0));
		tabbedPane.addTab("Home", icon1, h, "Add New Patients");
		tabbedPane.addTab("Patient Details", icon2, pd, "Check Patient Treatments");
		tabbedPane.addTab("Lab", icon3, lb,
				"Crown, number of units, Lab Name for which Crown send and Recieved date etc.");
		tabbedPane.addTab("Ortho", icon4, o,
				"Add New Ortho Patients and check Number of patients Ortho patients visits");
		apts();
		tabbedPane.addTab("Appointments(" + aptsn + ")", icon5, ap,
				"Add New Patient Appointments and check previous appointments");
		GroupLayout groupLayout = new GroupLayout(frmDentsol.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(tabbedPane));

		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(tabbedPane, -1, height - 80, 32767));

		frmDentsol.getContentPane().setLayout(groupLayout);
		frmDentsol.pack();
		JMenuBar menuBar = new JMenuBar();
		frmDentsol.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File    ");
		mnNewMenu.setIconTextGap(10);
		mnNewMenu.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/folder.png")));
		mnNewMenu.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem(" Export Data    ");
		mntmNewMenuItem.setIconTextGap(18);
		mntmNewMenuItem.setBackground(Color.gray);
		mntmNewMenuItem.setForeground(Color.white);
		mntmNewMenuItem.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/export.png")));

		mntmNewMenuItem.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		Dimension preferredSize = mntmNewMenuItem.getPreferredSize();
		preferredSize.height = 50;
		mntmNewMenuItem.setPreferredSize(preferredSize);

		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem(" Import Data     ");
		mntmNewMenuItem_1.setIconTextGap(18);
		mntmNewMenuItem_1.setBackground(Color.gray);
		mntmNewMenuItem_1.setForeground(Color.white);
		mntmNewMenuItem_1.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/import.png")));
		preferredSize.height = 50;
		mntmNewMenuItem_1.setPreferredSize(preferredSize);

		mntmNewMenuItem_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		mnNewMenu.add(mntmNewMenuItem_1);
		btnNewButton = new JButton(" Refresh");
		btnNewButton.setFocusable(false);
		btnNewButton.setRequestFocusEnabled(false);
		btnNewButton.setIconTextGap(6);
		btnNewButton.setBackground(new Color(255, 255, 128));
		btnNewButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnNewButton.setIcon(new ImageIcon(MainFrame.class.getResource("/Images/refresh.png")));
		btnNewButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));

		Dimension preferredSize1 = btnNewButton.getPreferredSize();
		preferredSize1.height = 20;
		preferredSize1.width = 100;
		btnNewButton.setPreferredSize(preferredSize1);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartApplication();
			}
		});
		btnNewButton.setMnemonic(KeyEvent.VK_ENTER);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		menuBar.add(btnNewButton);

		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				exportData();
			}
		});

		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				importData();

			}
		});

	}

	private void refreshApplication() {

		try {
			apts();

			if (aptsn != 0) {
				if (SystemTray.isSupported()) {
					displayTray();
				} else {
					JOptionPane.showConfirmDialog(null, "System tray not supported!", "Error", 0);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void restartApplication() {
		frmDentsol.dispose();

		try {
			MainFrame newFrame = new MainFrame();
			newFrame.frmDentsol.setVisible(true);
			newFrame.frmDentsol.setExtendedState(Frame.MAXIMIZED_BOTH);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
