package designs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class CLabel extends JLabel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Color fillColor;
	private Color lineColor;
	private String strng;

	private int strokeWidth;

	public CLabel() {
		fillColor = new Color(236, 240, 241, 0);
		lineColor = new Color(52, 155, 220);
		strokeWidth = 3;
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
	}

	public Color getFillColor() {
		return fillColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public String getStrng() {
		return strng;
	}

	public int getStrokeWidth() {
		return strokeWidth;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!isOpaque()) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int s = strokeWidth;
			int w = getWidth() - (2 * s);
			int h = getHeight() - (2 * s);
			g2d.setColor(fillColor);
			g2d.fillRoundRect(s, s, w, h, h, h);
			g2d.setStroke(new BasicStroke(s));
			g2d.setColor(lineColor);
			g2d.drawRoundRect(s, s, w, h, h, h);
		}
		super.paintComponent(g);
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setStrng(String strng) {
		this.strng = strng;
	}

	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

}