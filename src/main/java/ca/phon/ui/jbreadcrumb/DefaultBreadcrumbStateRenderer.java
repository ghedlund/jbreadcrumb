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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * Default {@link Breadcrumb} state renderer.
 *
 * @param <S>
 * @param <V>
 */
public class DefaultBreadcrumbStateRenderer<S, V> extends JLabel implements BreadcrumbStateRenderer<S, V> {
	
	private static final long serialVersionUID = 2519454994582489644L;
	
	public DefaultBreadcrumbStateRenderer() {
		super();
		
		setOpaque(false);
	}

	@Override
	public JComponent createStateComponent(JBreadcrumb<S, V> viewer, int stateIdx, S state, V value,
			boolean hasFocus) {
		
		setFont(viewer.getFont());
		setText(value.toString());
		
		if(stateIdx == viewer.getBreadcrumb().size()-1) {
			setBorder(new BreadcrumbStateBorder(false));
		} else {
			setBorder(new BreadcrumbStateBorder());
		}
		
		if(stateIdx == 0) {
			setBackground(viewer.getCurrentStateBackground());
			setForeground(viewer.getCurrentStateForeground());
		} else {
			setBackground(viewer.getStateBackground());
			setForeground(viewer.getStateForeground());
		}
		
		return this;
	}
	
	@Override
	public int getComponentOffset() {
		final Border border = getBorder();
		int retVal = 0;
		if(border instanceof BreadcrumbStateBorder) {
			retVal = -((BreadcrumbStateBorder)border).getArrowWidth();
		}
		return retVal;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		final Insets insets = getInsets();
		final Rectangle2D rect = new Rectangle2D.Double(insets.left, 0, getWidth()-insets.right-insets.left, getHeight());
		
		g2.setColor(getBackground());
		g2.fill(rect);
		
		super.paintComponent(g2);
	}

}
