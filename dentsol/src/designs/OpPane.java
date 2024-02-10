package designs;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class OpPane extends JPanel {

	/**
	 * Create the panel.
	 */
	public OpPane() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Are You Sure To Exit ?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNewLabel.setBounds(28, 0, 187, 30);
		add(lblNewLabel);

	}
}
