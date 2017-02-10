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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * A {@link JBreadcrumb} is a specialized list view using
 * a {@link Breadcrumb} for the data model.
 * 
 * @param <S>  the type of state in the breadcrumb
 * @param <V>  the type of value in the breadcrumb
 */
public class JBreadcrumb<S, V> extends JComponent implements Scrollable {
	
	private static final long serialVersionUID = 4673118307259766794L;

	public final static String uiClassId = "BreadcrumbUI";

	/** The {@link Breadcrumb} this component is viewing */
	private Breadcrumb<S, V> breadcrumb;
	
	private Color stateBackground;
	private Color stateForeground;
	private Color currentStateBackground;
	private Color currentStateForeground;
	
	/** State renderer */
	private BreadcrumbStateRenderer<? super S, ? super V> stateRenderer;

	/**
	 * Default constructor
	 * 
	 * @param breadcrumb  the breadcrumb to display
	 */
	public JBreadcrumb(Breadcrumb<S, V> breadcrumb) {
		super();
		setLayout(null);
		setBreadcrumb(breadcrumb);
		
		setUI(new DefaultBreadcrumbUI());
		setOpaque(true);
	}

	public BreadcrumbUI getBreadcrumbViewerUI() {
		return (BreadcrumbUI) ui;
	}

	public void setUI(BreadcrumbUI ui) {
        super.setUI(ui);
    }

	@Override
	public void updateUI() {
		setUI((BreadcrumbUI) UIManager.getUI(this));

		BreadcrumbStateRenderer<? super S, ? super V> renderer = getStateRenderer();
		if (renderer instanceof Component) {
			SwingUtilities.updateComponentTreeUI((Component) renderer);
		}
	}
	
	public Color getCurrentStateBackground() {
		return currentStateBackground;
	}
	
	public Color getStateBackground() {
		return stateBackground;
	}

	public void setStateBackground(Color stateBackground) {
		final Color oldBackground = this.stateBackground;
		this.stateBackground = stateBackground;
		firePropertyChange("stateBackground", oldBackground, stateBackground);
	}

	public Color getStateForeground() {
		return stateForeground;
	}

	public void setStateForeground(Color stateForeground) {
		final Color oldForeground = this.stateForeground;
		this.stateForeground = stateForeground;
		firePropertyChange("stateForeground", oldForeground, stateForeground);
	}

	public void setCurrentStateBackground(Color selectionBackground) {
		final Color oldBackground = this.currentStateBackground;
		this.currentStateBackground = selectionBackground;
		firePropertyChange("currentStateBackround", oldBackground, selectionBackground);
	}

	public Color getCurrentStateForeground() {
		return currentStateForeground;
	}

	public void setCurrentStateForeground(Color selectionForeground) {
		final Color oldForeground = this.currentStateForeground;
		this.currentStateForeground = selectionForeground;
		firePropertyChange("currentStateForeground", oldForeground, selectionForeground);
	}

	/**
	 * Gets the breadcrumb this component is viewing.
	 * 
	 * @return  the breadcrumb, or <code>null</code> if no breadcrumb being viewed
	 */
	public Breadcrumb<S, V> getBreadcrumb() {
		return breadcrumb;
	}
	
	public BreadcrumbStateRenderer<? super S, ? super V> getStateRenderer() {
		return this.stateRenderer;
	}
	
	public void setStateRenderer(BreadcrumbStateRenderer<? super S, ? super V> renderer) {
		final BreadcrumbStateRenderer<? super S, ? super V> oldRenderer = this.stateRenderer;
		this.stateRenderer = renderer;
		
		firePropertyChange("stateRenderer", oldRenderer, renderer);
	}

	/**
	 * Sets the breadcrumb this component is viewing.
	 * 
	 * @param breadcrumb  the breadcrumb
	 */
	public void setBreadcrumb(Breadcrumb<S, V> breadcrumb) {
		final Breadcrumb<S, V> oldBreadcrumb = this.breadcrumb;
		this.breadcrumb = breadcrumb;
		
		firePropertyChange("breadcrumb", oldBreadcrumb, breadcrumb);
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension retVal = getBreadcrumbViewerUI().getPreferredSize();
		
		for(int i = 0; i < getComponentCount(); i++) {
			final Rectangle compBounds = getComponent(i).getBounds();
			retVal.width = Math.max(retVal.width, compBounds.x+compBounds.width);
			retVal.height = Math.max(retVal.height, compBounds.y+compBounds.height);
		}
		
		return retVal;
	}
	
	/* Scrollable */
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		final Dimension dim = getPreferredSize();
		dim.width = Integer.MAX_VALUE;
		return dim;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 20;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return 100;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return true;
	}
	
}
