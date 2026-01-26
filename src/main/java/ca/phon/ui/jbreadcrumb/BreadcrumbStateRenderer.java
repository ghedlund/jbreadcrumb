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

import javax.swing.JComponent;

/**
 * Renderer for {@link Breadcrumb} states.
 * 
 * @param <S> state type
 * @param <V> value type
 */
public interface BreadcrumbStateRenderer<S, V> {

	/**
	 * Creates a component to render the state in the breadcrumb.
	 * 
	 * @param viewer  the breadcrumb viewer
	 * @param stateIdx  the index of the state
	 * @param state  the state to render
	 * @param value  the value associated with the state
	 * @param hasFocus  whether the state has focus
	 * @return the component for rendering the state
	 */
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
