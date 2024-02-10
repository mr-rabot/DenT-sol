package designs;

import java.awt.Color;

import javax.swing.JTabbedPane;

/**
 *
 * @author RAVEN
 */
public class CustomTP extends JTabbedPane {

	private Color selectedColor = new Color(248, 91, 50);

	private Color unselectedColor = new Color(230, 230, 230);

	public CustomTP() {
		setBackground(new Color(250, 250, 250));
		setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		setUI(new CustomTPUI(this));
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public Color getUnselectedColor() {
		return unselectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
		repaint();
	}

	public void setUnselectedColor(Color unselectedColor) {
		this.unselectedColor = unselectedColor;
		repaint();
	}
}
