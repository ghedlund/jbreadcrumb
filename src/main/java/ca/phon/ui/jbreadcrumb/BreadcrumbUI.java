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
import java.awt.Point;

import javax.swing.plaf.ComponentUI;

/**
 * Base component UI for {@link JBreadcrumb}. This class provides the foundation
 * for implementing custom UI delegates for breadcrumb components.
 */
public abstract class BreadcrumbUI extends ComponentUI {
	
	/**
	 * Creates a new BreadcrumbUI.
	 */
	protected BreadcrumbUI() {
		super();
	}
	
	/**
	 * Gets the preferred size for the breadcrumb component.
	 * 
	 * @return the preferred size
	 */
	public abstract Dimension getPreferredSize();
	
	/**
	 * Converts a point location to a state index.
	 * 
	 * @param p  the point to convert
	 * @return the state index at the given point, or -1 if none
	 */
	public abstract int locationToStateIndex(Point p);
	
}
