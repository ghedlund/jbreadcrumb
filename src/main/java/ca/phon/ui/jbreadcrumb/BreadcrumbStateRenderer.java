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

import javax.swing.JComponent;

/**
 * Renderer for {@link Breadcrumb} states.
 * 
 * @param <S> state type
 * @param <V> value type
 */
public interface BreadcrumbStateRenderer<S, V> {

	public JComponent createStateComponent(JBreadcrumb<S, V> viewer, int stateIdx,
			S state, V value, boolean hasFocus);
	
	/**
	 * Returns the amount of space (may be negative) that should
	 * exist between this state component and the last.
	 * 
	 * @return component horizontal offset
	 */
	public int getComponentOffset();
	
}
