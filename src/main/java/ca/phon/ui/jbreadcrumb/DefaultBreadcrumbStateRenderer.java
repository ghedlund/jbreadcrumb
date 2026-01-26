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
 * @param <S>  the state type
 * @param <V>  the value type
 */
public class DefaultBreadcrumbStateRenderer<S, V> extends JLabel implements BreadcrumbStateRenderer<S, V> {
	
	private static final long serialVersionUID = 2519454994582489644L;
	
	/**
	 * Creates a new default breadcrumb state renderer.
	 */
	public DefaultBreadcrumbStateRenderer() {
		super();
		
		setOpaque(false);
	}

	@Override
	public JComponent createStateComponent(JBreadcrumb<S, V> viewer, int stateIdx, S state, V value,
			boolean hasFocus) {
		
		setFont(viewer.getFont());
		setText(value.toString());
		
		if(stateIdx == 0) {
			setBorder(new BreadcrumbStateBorder(false));
		} else {
			setBorder(new BreadcrumbStateBorder());
		}
		
		if(stateIdx == viewer.getBreadcrumb().size()-1) {
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
