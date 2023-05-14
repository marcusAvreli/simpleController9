package simpleController9.core.view.delegator;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simpleController9.Application;
import simpleController9.ViewContainer;
import simpleController9.api.controller.ViewController;
import simpleController9.api.view.delegator.Delegator;

/**
 * This is the default implementation for a ViewContainerControllerDelegator. It injects the given controllers
 * to the view when the view is initialized and it removes the controllers from the view once the viewClose
 * method from the view has been called.
 * 
 * @author Mario Garcia
 * @since 1.0
 *
 */

///https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/core/src/main/java/org/viewaframework/view/delegator/ViewContainerControllerDelegator.java
public class ViewContainerControllerDelegator implements Delegator {
	private static final Logger logger = LoggerFactory.getLogger(ViewContainerControllerDelegator.class);
	private static final String ADD_LISTENER_PREFIX = "add";
	private static final String POINT_ASTERISK = ".*";
	private static final String ASTERISK = "*";
	private static final String POINT = ".";
	private static final String EMPTY = "";
	private static final String REMOVE_LISTENER_PREFIX = "remove";
	@Override
	public void inject(ViewContainer view) {
		// TODO Auto-generated method stub
		logger.info("inject_view_container");
		logger.info("view_id:"+view.getId());
		
		
		Map<String, List<ViewController<? extends EventListener, ? extends EventObject>>>  controllerMap = view.getViewControllerMap();	
		logger.info("controller_map:"+controllerMap);
		injectListeners(view,view.getRootPane(),controllerMap,ADD_LISTENER_PREFIX);
	}

	@Override
	public void clean(ViewContainer viewContainer) {
		// TODO Auto-generated method stub
		logger.info("clean_view_container");
	}
	private void injectListeners(ViewContainer view,Component component,
			Map<String,List<ViewController<? extends EventListener,? extends EventObject>>> viewControllers,
			String prefixAction)  {
		
	//	List<ViewController> vcl = viewControllers.get("ControllerTestViewId");
		Set<String> viewControllersKeys 	= viewControllers.keySet();
		String 		componentName 			= component!=null ? component.getName() : null;
		
		logger.info("viewControllersKeys:"+viewControllersKeys);
		List<ViewController<
		? extends EventListener,
		? extends EventObject>> vcl 	= null;
		String 		viewId 					= view.getId();
		Iterator<String> 			it 		= viewControllersKeys.iterator();
		Boolean 	controllerListFound 	= false;
		while (it.hasNext() && !controllerListFound && componentName != null){
			String controllerKey = it.next();
			String workingKey = controllerKey.replace(viewId + POINT , EMPTY);
			logger.info("component_name:"+componentName);
			logger.info("working_Key:"+workingKey);
			if (componentName!= null && componentName.matches(workingKey.replace(ASTERISK,POINT_ASTERISK))){
				logger.info("controllerKey:"+controllerKey);
				vcl = viewControllers.get(controllerKey);
				controllerListFound = true;
				if (vcl!=null){
					logger.info("should_call_execute_injection:");
					for (ViewController<? extends EventListener,? extends EventObject> vc: vcl){
						logger.info("prefixAction:"+prefixAction);
						if (vc!=null){
							if (prefixAction.equalsIgnoreCase(ADD_LISTENER_PREFIX)){
								logger.info("before_execute_injection");
								try {
									executeInjection(view, component, prefixAction, vc);
								} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							} else if (prefixAction.equalsIgnoreCase(REMOVE_LISTENER_PREFIX)){
								//executeRemoving(component, prefixAction, vc);
							}
						}
					}
				}else {
					logger.info("vcl_is_null:");
				}
					/*
				try {
					//executeInjection(view, component, prefixAction, vcl.get(0));
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}
		}
		if (component instanceof Container){			
			/*	Menu components should be treated specially */
				if (component instanceof JMenu){			
					for (Component c : JMenu.class.cast(component).getMenuComponents()){
						injectListeners(view,c,viewControllers,prefixAction);
					}						
				} else {		
					if (component instanceof JComponent){
							injectListeners(view,JComponent.class.cast(component).getComponentPopupMenu(),viewControllers,prefixAction);
					}				
					for (Component c : Container.class.cast(component).getComponents()){
						injectListeners(view,c,viewControllers,prefixAction);
					}
				}
			} 
		
	}
	@SuppressWarnings("unchecked")
	private void executeInjection(ViewContainer view, Component component,String prefixAction,ViewController vc)  throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		
		Class 	parameterClass 	= vc.getSupportedClass();
		logger.info("component_class:"+view.getClass().getSimpleName());
		logger.info("view:"+view.getId());
		String viewId = view.getId();
		if(null != viewId && viewId.equals("ControllerTestViewId")) {
			String 							methodName 		= prefixAction + parameterClass.getSimpleName();
			logger.info("method_name:"+methodName+" view:"+view+ " component_name:"+component.getName());
			Method 							method 			= component.getClass().getMethod(methodName, parameterClass);
		}
		if(null != component.getName() && !component.getName().equals("ROOTPANE")) {
			
		String 							methodName 		= prefixAction + parameterClass.getSimpleName();
		logger.info("method_name:"+methodName+" view:"+view+ " component_name:"+component.getName());
		Method 							method 			= component.getClass().getMethod(methodName, parameterClass);
		}
	//method.getParameterTypes()[0]
		//method.invoke(component,Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{method.getParameterTypes()[0],ListenerProxy.class},new GenericListenerHandler(view,vc)));
	}
}