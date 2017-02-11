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
