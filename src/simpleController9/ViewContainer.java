package simpleController9;

import java.awt.Component;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.RootPaneContainer;

import simpleController9.api.controller.ViewController;

public interface ViewContainer extends RootPaneContainer{
	// public JComponent getView();
	public abstract void viewInit();
	public abstract String getId();
	/**
	 * Sets the name view.
	 * 
	 * @param name the id to set
	 */
	public abstract void setId(String name);
	public void setViewControllerMap(Map<String, List<ViewController<? extends EventListener, ? extends EventObject>>> viewControllerMap);
	
	/* (non-Javadoc)
	 * @see org.viewaframework.controller.ViewControllerAware#getViewControllerMap()
	 */
	public Map<String, List<ViewController<? extends EventListener, ? extends EventObject>>> getViewControllerMap();
	
	
	public void setNamedComponents(Map<String, List<Component>> namedComponents);
	public Map<String, List<Component>> getNamedComponents();
	
}
