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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;

/**
 * Default UI implementation for {@link JBreadcrumb}
 *
 */
public class DefaultBreadcrumbUI extends BreadcrumbUI {
	
	private final static String BREADCRUMB_BACKGROUND = "Breadcrumb.background";
	
	private final static String CURRENT_STATE_BACKGROUND = "Breadcrumb.selectionBackground";
	
	private final static String CURRENT_STATE_FOREGROUND = "Breadcrumb.selectionForeground";
	
	private final static String STATE_BACKGROUND = "Breadcrumb.stateBackground";
	private final static String STATE_FOREGROUND = "Breadcrumb.stateForeground";
	
	static { installDefaults(); }
	
	private static void installDefaults() {
		UIManager.put(BREADCRUMB_BACKGROUND, UIManager.getColor("Panel.background"));
		UIManager.put(CURRENT_STATE_BACKGROUND, UIManager.getColor("List.selectionBackground"));
		UIManager.put(CURRENT_STATE_FOREGROUND, UIManager.getColor("List.selectionForeground"));
		UIManager.put(STATE_BACKGROUND, UIManager.getColor("Button.background"));
		UIManager.put(STATE_FOREGROUND, UIManager.getColor("Button.foreground"));
	}
		
	private JBreadcrumb<? super Object, ? super Object> jBreadcrumb;
	
	private final List<Rectangle> stateRects = new ArrayList<>();

	@SuppressWarnings("unchecked")
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		
		if(!(c instanceof JBreadcrumb))
			throw new IllegalArgumentException("Component must be of type JBreadcrumb");
		
		jBreadcrumb = (JBreadcrumb<? super Object, ? super Object>)c;
		if(jBreadcrumb.getStateRenderer() == null)
			jBreadcrumb.setStateRenderer(new DefaultBreadcrumbStateRenderer<>());
		
		jBreadcrumb.setBackground(UIManager.getColor(BREADCRUMB_BACKGROUND));
		
		jBreadcrumb.setStateBackground(UIManager.getColor(STATE_BACKGROUND));
		jBreadcrumb.setStateForeground(UIManager.getColor(STATE_FOREGROUND));
		
		jBreadcrumb.setCurrentStateForeground(UIManager.getColor(CURRENT_STATE_FOREGROUND));
		jBreadcrumb.setCurrentStateBackground(UIManager.getColor(CURRENT_STATE_BACKGROUND));
		
		jBreadcrumb.addMouseListener(mouseInputAdapter);
		jBreadcrumb.addPropertyChangeListener("model", (e) -> 
			jBreadcrumb.getBreadcrumb().addBreadcrumbListener(breadcrumbListener) );
		jBreadcrumb.getBreadcrumb().addBreadcrumbListener(breadcrumbListener);
	}
	
	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
		
		jBreadcrumb.removeMouseListener(mouseInputAdapter);
		jBreadcrumb.getBreadcrumb().removeBreadcrumbListener(breadcrumbListener);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		g.fillRect(0, 0, jBreadcrumb.getWidth(), jBreadcrumb.getHeight());
		
		final Breadcrumb<Object, Object> breadcrumb = jBreadcrumb.getBreadcrumb();
		synchronized(jBreadcrumb.getBreadcrumb()) {
			final BreadcrumbStateRenderer<Object, Object> stateRenderer = jBreadcrumb.getStateRenderer();
			
			int x = 0;
			stateRects.clear();
			for(int stateIndex = 0; stateIndex < breadcrumb.size(); stateIndex++) {
				final Object state = breadcrumb.getStates().get(stateIndex);
				final Object value = breadcrumb.getValue(state);
				
				final JComponent comp = stateRenderer.createStateComponent(jBreadcrumb, stateIndex, state, value, false);
				final Rectangle compRect = new Rectangle(x, 0, comp.getPreferredSize().width, comp.getPreferredSize().height);
				SwingUtilities.paintComponent(g, comp, jBreadcrumb, compRect);
				x += compRect.width + stateRenderer.getComponentOffset();
				
				stateRects.add(compRect);
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		int width = 0;
		int height = 0;
		
		final Breadcrumb<Object, Object> breadcrumb = jBreadcrumb.getBreadcrumb();
		for(int i = 0; i < breadcrumb.size(); i++) {
			final Object state = jBreadcrumb.getBreadcrumb().getStates().get(i);
			final JComponent comp = jBreadcrumb.getStateRenderer().createStateComponent(jBreadcrumb, i, state,
					jBreadcrumb.getBreadcrumb().getValue(breadcrumb.get(i)), false);
			width += comp.getPreferredSize().width;
			if(i < breadcrumb.size()-1) width += jBreadcrumb.getStateRenderer().getComponentOffset();
			
			height = Math.max(height, comp.getPreferredSize().height);
		}
		
		return new Dimension(width, height);
	}

	@Override
	public int locationToStateIndex(Point p) {
		int retVal = -1;
		
		for(int stateIndex = 0; stateIndex < stateRects.size(); stateIndex++) {
			final Rectangle rect = stateRects.get(stateIndex);
			if(rect.contains(p)) {
				retVal = stateIndex;
				break;
			}
		}
		
		return retVal;
	}
	
	private BreadcrumbListener<? super Object, ? super Object> breadcrumbListener = (evt) -> {
		jBreadcrumb.revalidate();
	};
	
	private MouseInputAdapter mouseInputAdapter = new MouseInputAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			final Point p = e.getPoint();
			final Breadcrumb<Object, Object> breadcrumb = jBreadcrumb.getBreadcrumb();
			for(int i = 0; i < stateRects.size(); i++) {
				if(stateRects.get(i).contains(p)) {
					breadcrumb.gotoState(breadcrumb.getStates().get(breadcrumb.size()-(i+1)));
					break;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseDragged(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseMoved(e);
		}
		
	};
	
}
