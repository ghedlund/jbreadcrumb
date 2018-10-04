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

import javax.swing.JScrollPane;

public class JBreadcrumbScrollPane<S, V> extends JScrollPane {

	private static final long serialVersionUID = -5100228671645259582L;
	
	public JBreadcrumbScrollPane(JBreadcrumb<S, V> jbreadcrumb) {
		super(jbreadcrumb);
		
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		getViewport().setBackground(jbreadcrumb.getBackground());
	}

}
