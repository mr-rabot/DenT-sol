package load;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.border.DropShadowBorder;

import gradientpane.LodBg;
import main.MainFrame;

@SuppressWarnings("serial")
public class Lpage extends JFrame {

	private static JProgressBar progressBar;

	public static void main(String[] args) throws Throwable {
		Lpage lp = new Lpage();
		lp.setVisible(true);
		MainFrame window = new MainFrame();
		Thread t = new Thread(() -> {
			for (int i = 0; i <= 300; i++) {
				int j = i / 3;
				if (i == 0)
					lp.lstring.setText("Loading Componets .");
				if (i == 20)
					lp.lstring.setText("Loading Componets ..");
				if (i == 40)
					lp.lstring.setText("Loading Componets ...");
				if (i == 60)
					lp.lstring.setText("Setting-up Database .");
				if (i == 80)
					lp.lstring.setText("setting-up Database ..");
				if (i == 100)
					lp.lstring.setText("setting-up Database ...");
				if (i == 120)
					lp.lstring.setText("Database Connected.");
				if (i == 180)
					lp.lstring.setText("Loading Database .");
				if (i == 200)
					lp.lstring.setText("Loading Database ..");
				if (i == 210)
					lp.lstring.setText("Loading Database ...");
				if (i == 220)
					lp.lstring.setText("Loading Database ");
				if (i == 230)
					lp.lstring.setText("Loading Database ..");
				if (i == 240)
					lp.lstring.setText("Loading Database ...");
				if (i == 250)
					lp.lstring.setText("Loading ...");
				if (i == 260)
					lp.lstring.setText("Loading ...");
				if (i == 270)
					lp.lstring.setText("Loading Done ");
				if (i == 300) {
					lp.lstring.setText(" loading Done ");
					window.frmDentsol.setVisible(true);
					window.frmDentsol.setExtendedState(MAXIMIZED_BOTH);
					lp.dispose();
				}
				progressBar.setValue(j);
				try {
					Thread.sleep(5L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	public LodBg contentPane;

	public JLabel lstring;

	public Lpage() {
		DropShadowBorder shadowborder = new DropShadowBorder();
		shadowborder.setShowLeftShadow(true);
		shadowborder.setShowRightShadow(true);
		shadowborder.setShowTopShadow(true);
		shadowborder.setShowBottomShadow(true);
		shadowborder.setShadowColor(Color.black);
		shadowborder.setShadowSize(10);

		setUndecorated(true);
		setDefaultCloseOperation(3);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/Images/adsoft.png")));
		setSize(771, 450);
		setLocationRelativeTo(null);
		this.contentPane = new LodBg();
		contentPane.setOpaque(true);
		contentPane.setBorder(shadowborder);
		setContentPane(this.contentPane);
		this.contentPane.setLayout((LayoutManager) null);
		contentPane.setLayout(null);
		contentPane.setLayout(null);

		lstring = new JLabel("");
		lstring.setForeground(new Color(128, 0, 0));
		lstring.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lstring.setBounds(21, 324, 265, 33);
		contentPane.add(lstring);
		DropShadowBorder shadowborder1 = new DropShadowBorder();
		shadowborder1.setShowLeftShadow(true);
		shadowborder1.setShowRightShadow(true);
		shadowborder1.setShowTopShadow(true);
		shadowborder1.setShowBottomShadow(true);
		shadowborder1.setShadowColor(Color.red);
		shadowborder1.setShadowSize(10);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(255, 0, 0));
		progressBar.setOpaque(false);
		progressBar.setBounds(10, 365, 751, 32);
		progressBar.setBorder(shadowborder1);
		contentPane.add(progressBar);

		JLabel lblNewLabel = new JLabel("DenT-SoL");
		lblNewLabel.setOpaque(false);
		lblNewLabel.setBackground(new Color(0, 0, 0, 50));
		lblNewLabel.setHorizontalTextPosition(SwingConstants.LEADING);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 32));
		lblNewLabel.setBorder(shadowborder1);
		lblNewLabel.setBounds(222, 118, 389, 70);
		contentPane.add(lblNewLabel);

	}
}
