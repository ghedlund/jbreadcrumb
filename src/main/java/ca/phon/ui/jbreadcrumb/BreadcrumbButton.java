/*
 * Copyright (C) 2012-2018 Gregory Hedlund
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *    http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

	/**
	 * Creates a new BreadcrumbButton.
	 */
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
