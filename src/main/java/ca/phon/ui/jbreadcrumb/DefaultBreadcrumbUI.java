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
		UIManager.getDefaults().put(BREADCRUMB_BACKGROUND, UIManager.getColor("Panel.background"));
		UIManager.getDefaults().put(CURRENT_STATE_BACKGROUND, UIManager.getColor("List.selectionBackground"));
		UIManager.getDefaults().put(CURRENT_STATE_FOREGROUND, UIManager.getColor("List.selectionForeground"));
		UIManager.getDefaults().put(STATE_BACKGROUND, UIManager.getColor("Button.background"));
		UIManager.getDefaults().put(STATE_FOREGROUND, UIManager.getColor("Button.foreground"));
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
		synchronized(breadcrumb) {
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
					breadcrumb.gotoState(breadcrumb.getStates().get(i));
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
