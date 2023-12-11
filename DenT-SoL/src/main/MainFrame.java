package main;

import designs.OpPane;
import designs.CustomTP;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;

import main.tabs.Appointment;
import main.tabs.Home;
import main.tabs.Lab;
import main.tabs.Ortho;
import main.tabs.PatientDetails;
import org.jdesktop.swingx.border.DropShadowBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame
{
  public JFrame frmDentsol;
  Toolkit t = Toolkit.getDefaultToolkit();
  
  Dimension dim = new Dimension(t.getScreenSize());
  
  int height = (int)dim.getHeight();
  
  int width = (int)dim.getWidth();
  static String cd = System.getProperty("user.dir");
  
  private Home h;
  private PatientDetails pd;
  private Ortho o;
  private Appointment ap;
  private Lab lb;
  private static int aptsn;
  public static void apts() throws Throwable {
    Class.forName("org.sqlite.JDBC");
    Connection con = DriverManager.getConnection("jdbc:sqlite:" + cd + "/ImpFiles/database/pdata.db");
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
    con.close();
  }
  
  public static void displayTray() throws AWTException
  {
    SystemTray tray = SystemTray.getSystemTray();
    Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
    TrayIcon trayIcon = new TrayIcon(image, "DenT-SoL");
    trayIcon.setImageAutoSize(true);
    tray.add(trayIcon);
    trayIcon.displayMessage("Today We Have  ( " + aptsn + " ) Appointmnts", "DenT-SoL", TrayIcon.MessageType.INFO);
  }
  
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

	  Timer timer = new Timer(4 * 60 * 60 * 1000, new ActionListener() {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			  refreshApplication();
		  }
	  });
	  
	  
	  timer.start();
	  
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

    
  private void initialize()
    throws Throwable
  {
    frmDentsol = new JFrame();
    frmDentsol.setTitle("DenT-SoL");
    frmDentsol.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
    frmDentsol.setBackground(new Color(0, 0, 0));
    frmDentsol.setExtendedState(6);
    frmDentsol.setSize(width, height - 80);
    
    ImageIcon icon1 = new ImageIcon((URL)Objects.requireNonNull(getClass().getResource("/Images/h.png")));
    ImageIcon icon2 = new ImageIcon((URL)Objects.requireNonNull(getClass().getResource("/Images/pd.png")));
    ImageIcon icon3 = new ImageIcon((URL)Objects.requireNonNull(getClass().getResource("/Images/lb.png")));
    ImageIcon icon4 = new ImageIcon((URL)Objects.requireNonNull(getClass().getResource("/Images/ot.png")));
    ImageIcon icon5 = new ImageIcon((URL)Objects.requireNonNull(getClass().getResource("/Images/ap1.png")));
    frmDentsol.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/Images/ic1.png")));
    frmDentsol.setDefaultCloseOperation(3);
    CustomTP tabbedPane = new CustomTP();
    
    final int[] initialOptionIndex = {0}; 
    String[] options = { "Yes", "No" };

    frmDentsol.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            OpPane n = new OpPane();

            
            int confirmed = JOptionPane.showOptionDialog(null, 
                n, "DenT-SoL", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[initialOptionIndex[0]]);
            
            if (confirmed == 0) {
                frmDentsol.dispose();
            } else if (confirmed == 1) {
                frmDentsol.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
    tabbedPane.addTab("Lab", icon3, lb, "Crown, number of units, Lab Name for which Crown send and Recieved date etc.");
    tabbedPane.addTab("Ortho", icon4, o, "Add New Ortho Patients and check Number of patients Ortho patients visits");
    apts();
    tabbedPane.addTab("Appointments(" + aptsn + ")", icon5, ap, "Add New Patient Appointments and check previous appointments");
    GroupLayout groupLayout = new GroupLayout(frmDentsol.getContentPane());
    groupLayout.setHorizontalGroup(
      groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(tabbedPane));
    
    groupLayout.setVerticalGroup(
      groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(tabbedPane, -1, height - 80, 32767));
    
    frmDentsol.getContentPane().setLayout(groupLayout);
    frmDentsol.pack();
    
    JMenuBar menuBar = new JMenuBar();
    frmDentsol.setJMenuBar(menuBar);
    
    JMenu mnNewMenu = new JMenu("    File    ");
    mnNewMenu.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
    menuBar.add(mnNewMenu);
    
    JMenuItem mntmNewMenuItem = new JMenuItem("    Export Data    ");
    
    mntmNewMenuItem.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
    mnNewMenu.add(mntmNewMenuItem);
    
    JMenuItem mntmNewMenuItem_1 = new JMenuItem("    Import Data     ");
   
    mntmNewMenuItem_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
    mnNewMenu.add(mntmNewMenuItem_1);
    
    mntmNewMenuItem.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		
    		exportData();
    	}
    });
    
    mntmNewMenuItem_1.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		
    		importData();
    	}
    });
  
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
	            
	            String sourceFolder =cd + "/ImpFiles/database/";

	            try (FileOutputStream fos = new FileOutputStream(exportFile);
	                 ZipOutputStream zos = new ZipOutputStream(fos)) {

	                addFolderToZip("", sourceFolder, zos);
	            }

	            String successMessage = "Data exported successfully to: " + exportFile.getAbsolutePath();
	            JOptionPane.showMessageDialog(null, successMessage, "Export Successful", JOptionPane.INFORMATION_MESSAGE);

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
	      
	            String destinationFolder = "ImpFiles/";
	            String databaseFolder = "database";

	            try (FileInputStream fis = new FileInputStream(importFile);
	                 ZipInputStream zis = new ZipInputStream(fis)) {

	                File destinationDir = new File(destinationFolder);
	                if (!destinationDir.exists()) {
	                    destinationDir.mkdirs();
	                }

	                Path databasePath = Paths.get(destinationFolder, databaseFolder);
	                if (Files.exists(databasePath)) {
	                    Files.walk(databasePath)
	                            .sorted(Comparator.reverseOrder())
	                            .map(Path::toFile)
	                            .forEach(File::delete);
	                }

	                while (extractZipEntry(zis, destinationFolder) != null) {
	                    // Continue extracting entries until null
	                }
	            }

	            String successMessage = "Data imported successfully from: " + importFile.getAbsolutePath();
	            JOptionPane.showMessageDialog(null, successMessage, "Import Successful", JOptionPane.INFORMATION_MESSAGE);

	        } catch (IOException ex) {
	            ex.printStackTrace();
	            String errorMessage = "Error importing data from: " + importFile.getAbsolutePath();
	            JOptionPane.showMessageDialog(null, errorMessage, "Import Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
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
  private static void addFolderToZip(String path, String sourceFolder, ZipOutputStream zos) throws IOException {
	  File folder = new File(sourceFolder);
	  for (String fileName : folder.list()) {
		  if (path.equals("")) {
			  addFileToZip(folder.getName(), sourceFolder + File.separator + fileName, zos);
		  } else {
			  addFileToZip(path + File.separator + folder.getName(), sourceFolder + File.separator + fileName, zos);
		  }
	  }
  }
  
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
  
}

