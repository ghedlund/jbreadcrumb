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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Default {@link Border} for {@link Breadcrumb} states.  Border
 * will be drawn with arrow tail and head.  Border will be
 * painted using the components background color and outlined
 * with the foreground color.
 * 
 */
public class BreadcrumbStateBorder implements Border {

	/** Default top inset value */
	public final static int TOP_INSET = 5;
	private int topInset;
	
	/** Default bottom inset value */
	public final static int BOTTOM_INSET = 5;
	private int bottomInset;
	
	/** Default arrow width value */
	public final static int ARROW_WIDTH = 5;
	private int arrowWidth;
	
	private boolean drawTail;
	
	/**
	 * Creates a new breadcrumb state border with default settings.
	 */
	public BreadcrumbStateBorder() {
		this(TOP_INSET, BOTTOM_INSET, ARROW_WIDTH, true);
	}
	
	/**
	 * Creates a new breadcrumb state border.
	 * 
	 * @param drawTail  whether to draw the tail arrow
	 */
	public BreadcrumbStateBorder(boolean drawTail) {
		this(TOP_INSET, BOTTOM_INSET, ARROW_WIDTH, drawTail);
	}
	
	/**
	 * Creates a new breadcrumb state border with custom settings.
	 * 
	 * @param topInset  the top inset
	 * @param bottomInset  the bottom inset
	 * @param arrowWidth  the arrow width
	 * @param drawTail  whether to draw the tail arrow
	 */
	public BreadcrumbStateBorder(int topInset, int bottomInset, int arrowWidth, boolean drawTail) {
		super();
		this.topInset = topInset;
		this.bottomInset = bottomInset;
		this.arrowWidth = arrowWidth;
		this.drawTail = drawTail;
	}
	
	/**
	 * Gets the top inset.
	 * 
	 * @return the top inset
	 */
	public int getTopInset() {
		return topInset;
	}

	/**
	 * Sets the top inset.
	 * 
	 * @param topInset  the top inset to set
	 */
	public void setTopInset(int topInset) {
		this.topInset = topInset;
	}

	/**
	 * Gets the bottom inset.
	 * 
	 * @return the bottom inset
	 */
	public int getBottomInset() {
		return bottomInset;
	}

	/**
	 * Sets the bottom inset.
	 * 
	 * @param bottomInset  the bottom inset to set
	 */
	public void setBottomInset(int bottomInset) {
		this.bottomInset = bottomInset;
	}

	/**
	 * Gets the arrow width.
	 * 
	 * @return the arrow width
	 */
	public int getArrowWidth() {
		return arrowWidth;
	}

	/**
	 * Sets the arrow width.
	 * 
	 * @param arrowWidth  the arrow width to set
	 */
	public void setArrowWidth(int arrowWidth) {
		this.arrowWidth = arrowWidth;
	}

	/**
	 * Gets whether the tail arrow is drawn.
	 * 
	 * @return true if tail is drawn, false otherwise
	 */
	public boolean isDrawTail() {
		return drawTail;
	}

	/**
	 * Sets whether the tail arrow is drawn.
	 * 
	 * @param drawTail  true to draw tail, false otherwise
	 */
	public void setDrawTail(boolean drawTail) {
		this.drawTail = drawTail;
	}

	@Override
	public void paintBorder(Component c, Graphics gfx, int x, int y, int width, int height) {
		Graphics2D g = (Graphics2D)gfx;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final Insets insets = getBorderInsets(c);
		
		final GeneralPath arrowPath = new GeneralPath();
		arrowPath.moveTo(0, 0);
		arrowPath.lineTo(arrowWidth, c.getHeight()/2-1);
		arrowPath.lineTo(0, c.getHeight());
		arrowPath.closePath();
		
		final Color background = 
				(c.isEnabled() ? c.getBackground() : UIManager.getColor("Button.background"));
		final Color foreground =
				(c.isEnabled() ? c.getForeground() : UIManager.getColor("Button.disabledForeground"));
		
		if(insets.left == 2*arrowWidth) {
			final Rectangle2D rect = new Rectangle2D.Double(0, 0, 2*arrowWidth, c.getHeight());
			final Area area = new Area(rect);
			area.subtract(new Area(arrowPath));
			
			g.setColor(background);
			g.fill(area);
			
			// draw border line
//			g.setColor(foreground);
//			g.drawLine(0, 0, arrowWidth, c.getHeight()/2-1);
//			g.drawLine(arrowWidth, c.getHeight()/2-1, 0, c.getHeight());
		} else {
			g.setColor(background);
			g.fillRect(0, 0, arrowWidth, c.getHeight());
		}
		
		AffineTransform origTrans = g.getTransform();
		
		g.translate(c.getWidth()-insets.right, 0);
		g.setColor(background);
		
		g.fillRect(0, 0, arrowWidth-1, c.getHeight());
		
		g.setTransform(origTrans);
		g.translate(c.getWidth()-arrowWidth-1, 0);
		
		g.fill(arrowPath);
	
		// draw border line
		g.setColor(foreground);
		g.drawLine(0, 0, arrowWidth, c.getHeight()/2-1);
		g.drawLine(arrowWidth, c.getHeight()/2-1, 0, c.getHeight());
		
		if(c.hasFocus()) {
			g.setTransform(origTrans);

			final Rectangle rect = new Rectangle(insets.left - 2, 2, c.getWidth() - insets.left - insets.right + 4, c.getHeight() - 4);
			int vx, vy;

			g.setColor(UIManager.getDefaults().getColor("Button.focus"));

			// draw upper and lower horizontal dashes
			for (vx = rect.x; vx < (rect.x + rect.width); vx += 2) {
				g.fillRect(vx, rect.y, 1, 1);
				g.fillRect(vx, rect.y + rect.height - 1, 1, 1);
			}

			// draw left and right vertical dashes
			for (vy = rect.y; vy < (rect.y + rect.height); vy += 2) {
				g.fillRect(rect.x, vy, 1, 1);
				g.fillRect(rect.x + rect.width - 1, vy, 1, 1);
			}
		}
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(
				topInset,
				(drawTail ? 2*arrowWidth : arrowWidth),
				bottomInset,
				2*arrowWidth
			);
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

}
