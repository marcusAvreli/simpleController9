package simpleController9;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simpleController9.api.controller.ViewController;
import simpleController9.api.view.delegator.Delegator;
import simpleController9.core.view.delegator.NamedComponentsDelegator;
import simpleController9.core.view.delegator.ViewContainerControllerDelegator;

//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/core/src/main/java/org/viewaframework/view/AbstractViewContainer.java
public class AbstractViewContainer  implements ViewContainer {
	private static final Logger logger = LoggerFactory.getLogger(AbstractViewContainer.class);
	private JRootPane 								rootPane;
	Container contentPane;
	String id;
	String title;
	private List<ViewContainerEventController> viewContainerEventControllers;
	private Map<String,List<ViewController<? extends EventListener, ? extends EventObject>>> 	viewControllerMap;
	private List<Delegator>							delegators;
	private Map<String,List<Component>> 			namedComponents;

	public AbstractViewContainer(String id,String title,Component component){
		//super();
		this.setId(id);
		this.setTitle(title);
		this.getContentPane().add(component);
		this.viewContainerEventControllers = new ArrayList<ViewContainerEventController>();
		addViewContainerListener(new DefaultViewContainerEventController());
		viewControllerMap = new HashMap<String, List<ViewController<? extends EventListener, ? extends EventObject>>>();
	}
	@Override
	public void setContentPane(Container contentPane) {
		// TODO Auto-generated method stub
		System.out.println("set_content_pane_is_called");
		if(null != contentPane) {
		
			System.out.println("set_content_pane_is_not_null:"+contentPane.getName());
		}else {
			System.out.println("set_content_pane_is_null");
		}
		this.getRootPane().setContentPane(contentPane);
	}

	@Override
	public Container getContentPane() {
		// TODO Auto-generated method stub
		
		return this.getRootPane().getContentPane(); 
	
	}

	@Override
	public void setLayeredPane(JLayeredPane layeredPane) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JLayeredPane getLayeredPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGlassPane(Component glassPane) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getGlassPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JRootPane getRootPane() {
		// TODO Auto-generated method stub
		if (this.rootPane == null){
			this.rootPane = new JRootPane();
			this.rootPane.setName("ROOTPANE");
		}
		return this.rootPane;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public void viewInit() {
		System.out.println("view_init");
		if (this.getContentPane()!=null) { 
			System.out.println("content_pane_is_not_null. setting_name");
				this.getContentPane().setName("contentPane");
			}else {
				System.out.println("content_pane_is_null");
			}
		this.fireViewInit(new ViewContainerEvent(this));
		
		final ViewContainer thisContainer = this; 
		logger.info("Initializing_view "+this.getClass().getName());
		if (SwingUtilities.isEventDispatchThread()){
			System.out.println("view_init_event_dispatch_thread");
			for (Delegator delegator : this.getDelegators()){
				delegator.inject(thisContainer);
			}
			//thisContainer.viewInitUIState(); 
		} else {
			System.out.println("view_init_not_event_dispatch_thread");
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
								
						for (Delegator delegator : getDelegators()){
							delegator.inject(thisContainer);
						}	
						//thisContainer.viewInitUIState(); 
								
				}
			});
		}
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.DelegatorAware#getDelegators()
	 */
	public List<Delegator> getDelegators() {
		if (delegators == null){
			this.delegators = new ArrayList<Delegator>(Arrays.asList(
			 /* ActionDescriptor must always be the first delegator because once it has been injected
			  * all initial java.awt.Component are available, like the JToolBar and the JMenuBar */
					new ViewContainerControllerDelegator(),
					new NamedComponentsDelegator()
				
			));
		}
		return delegators;
	}
	
	/* (non-Javadoc)
	 * @see org.viewaframework.view.ViewContainerEventAware#fireViewInit(org.viewaframework.view.ViewContainerEvent)
	 */
	public void fireViewInit(ViewContainerEvent event) {
		System.out.println("fire_view_init");
		for (ViewContainerEventController listener: this.viewContainerEventControllers){
			listener.onViewInit(event);
		}
	}
	public void addViewContainerListener(ViewContainerEventController listener){
		this.viewContainerEventControllers.add(listener);
	}
	/* (non-Javadoc)
	 * @see org.viewaframework.controller.ViewControllerAware#setViewControllerMap(java.util.Map)
	 */
	public void setViewControllerMap(Map<String, List<ViewController<? extends EventListener, ? extends EventObject>>> viewControllerMap) {
		this.viewControllerMap = viewControllerMap;
	}
	/* (non-Javadoc)
	 * @see org.viewaframework.controller.ViewControllerAware#getViewControllerMap()
	 */
	public Map<String, List<ViewController<? extends EventListener, ? extends EventObject>>> getViewControllerMap() {
		return this.viewControllerMap;
	}
	public void setNamedComponents(Map<String, List<Component>> namedComponents) {
		this.namedComponents = namedComponents;
	}
	/* (non-Javadoc)
	 * @see org.viewaframework.view.ComponentAware#getNamedComponents()
	 */
	public Map<String, List<Component>> getNamedComponents() {
		return this.namedComponents;
	}
}
