package main;

import designs.OpPane;
import designs.CustomTP;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import main.tabs.Appointment;
import main.tabs.Home;
import main.tabs.Lab;
import main.tabs.Ortho;
import main.tabs.PatientDetails;
import org.jdesktop.swingx.border.DropShadowBorder;

public class MainFrame
{
  public JFrame frmDentsol;
  public static int aptsn;
  Toolkit t = Toolkit.getDefaultToolkit();
  
  Dimension dim = new Dimension(t.getScreenSize());
  
  int height = (int)dim.getHeight();
  
  int width = (int)dim.getWidth();
  static String cd = System.getProperty("user.dir");
  
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
    initialize();
    
    apts();
    if (aptsn != 0) {
      if (SystemTray.isSupported()) {
        displayTray();
      } else {
        JOptionPane.showConfirmDialog(null, "System tray not supported!", "Error", 0);
      }
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
    Home h = new Home();
    PatientDetails pd = new PatientDetails();
    Ortho o = new Ortho();
    Appointment ap = new Appointment();
    Lab lb = new Lab();
    tabbedPane.addTab("Home", icon1, h, "Add New Patients");
    tabbedPane.addTab("Patient Details", icon2, pd, "Check Patient Treatments");
    tabbedPane.addTab("Lab", icon3, lb, "Crown, number of units, Lab Name for which Crown send and Recieved date etc.");
    tabbedPane.addTab("Ortho", icon4, o, "Add New Ortho Patients and check Number of patients Ortho patients visits");
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
  }
}
