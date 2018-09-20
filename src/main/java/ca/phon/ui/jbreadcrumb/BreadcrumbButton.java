/*
 * Phon - An open source tool for research in phonology.
 * Copyright (C) 2005 - 2017, Gregory Hedlund <ghedlund@mun.ca> and Yvan Rose <yrose@mun.ca>
 * Dept of Linguistics, Memorial University <https://phon.ca>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.phon.ui.jbreadcrumb;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Button which can be added to the JBreadcrumb component.
 * Note: JBreadcrumb is initialized with a <code>null</code> layout
 */
public class BreadcrumbButton extends JButton {

	private static final long serialVersionUID = -1736113253724309947L;

	public BreadcrumbButton() {
		super();
		
		setOpaque(false);
		setBorder(new BreadcrumbStateBorder());
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		final Insets insets = getInsets();
		final Rectangle2D rect = new Rectangle2D.Double(insets.left, 0, getWidth()-insets.right-insets.left, getHeight());
		
		if(isEnabled())
			g2.setColor(getBackground());
		else
			g2.setColor(UIManager.getColor("Button.background"));
		g2.fill(rect);
		
		if(isEnabled()) {
			g2.setColor(getForeground());
		} else {
			g2.setColor(UIManager.getColor("Button.disabledForeground"));
		}
		Point p = centerTextInRectangle(g2, getText(),
				new Rectangle((int)rect.getX(), 0, (int)rect.getWidth(), (int)getHeight()));
		g2.drawString(getText(), p.x, p.y);
	}
	
	/**
	 * Returns the point at which to draw a string so that it appears
	 * center-aligned within a given rectangle.
	 * 
	 * @param g  the graphics context to work with
	 * @param txt  the string
	 * @param rect  the rectangle to place the string in
	 * 
	 * @return the point at which text should be drawn so that it is aligned
	 *         in the center of the given rectangle.
	 */
	private Point centerTextInRectangle(Graphics g, String txt, Rectangle rect) {
		return placeTextInRectangle(g, txt, rect, SwingConstants.CENTER, SwingConstants.CENTER);
	}

	/**
	 * Returns the point at which to draw a string so that it appears aligned
	 * within a given rectangle.
	 * 
	 * @param g  the graphics context to work with
	 * @param txt  the string
	 * @param rect  the rectangle to place the string in
	 * 
	 * @param horizontalAlignment  the horizontal alignment; one of
	 *             {@link SwingConstants#LEFT}, {@link SwingConstants#CENTER}
	 *             or {@link SwingConstants#RIGHT}
	 *                
	 * @param verticalAlignment  the vertical alignment; one of
	 *             {@link SwingConstants#TOP}, {@link SwingConstants#CENTER}
	 *             or {@link SwingConstants#BOTTOM}
	 * 
	 * @return the point at which text should be drawn so that it is aligned
	 *         appropriately with the given rectangle.
	 */
	private Point placeTextInRectangle(Graphics g,
	                                         String txt,
	                                         Rectangle rect,
	                                         int horizontalAlignment,
	                                         int verticalAlignment) 
	{
		final FontMetrics fm = g.getFontMetrics();
		final LineMetrics lm = fm.getLineMetrics(txt, g);
		final Rectangle2D r = fm.getStringBounds(txt, g);

		int x = rect.x;
		int y = rect.y;

		switch(horizontalAlignment) {
		case SwingConstants.CENTER:
			x += (rect.width - r.getWidth()) * 0.5;
			break;
		case SwingConstants.RIGHT:
			x += rect.width - r.getWidth();
			break;
		}

		switch(verticalAlignment) {
		case SwingConstants.TOP:
			y += lm.getAscent();
			break;
		case SwingConstants.CENTER:
			y += (rect.height - r.getHeight()) * 0.5 + lm.getAscent();
			break;
		case SwingConstants.BOTTOM:
			y += rect.height - lm.getDescent();
			break;
		}

		return new Point(x, y);
	}
	
}
