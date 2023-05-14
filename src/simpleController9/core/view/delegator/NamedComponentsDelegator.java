package simpleController9.core.view.delegator;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simpleController9.ViewContainer;
import simpleController9.api.view.delegator.Delegator;

public class NamedComponentsDelegator implements Delegator{

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.Delegator#clean(org.viewaframework.view.ViewContainer)
	 */
	public void clean(ViewContainer view)  {
		view.setNamedComponents(null);
	}

	/* (non-Javadoc)
	 * @see org.viewaframework.view.delegator.Delegator#inject(org.viewaframework.view.ViewContainer)
	 */
	public void inject(ViewContainer view)  {
		view.setNamedComponents(extractComponents(view.getRootPane(),new HashMap<String,List<Component>>()));
	}
	
	/**
	 * @param con
	 * @param namedComponents
	 * @return
	 */
	private Map<String,List<Component>> extractComponents(Component con,Map<String,List<Component>> namedComponents){		
		String componentName = con.getName();
		System.out.println("component_Name:"+componentName);
		if (componentName!=null && !componentName.equalsIgnoreCase("")){
			List<Component> components = namedComponents.get(componentName);
			if (components==null){
				namedComponents.put(componentName, new ArrayList<Component>());
			}
			namedComponents.get(componentName).add(con);
		}			
		if (con instanceof Container){
			
				for (Component c : ((Container)con).getComponents()){
					extractComponents(c,namedComponents);
				}
			
		}	
		if(null !=namedComponents ) {
			System.out.println("named_components_is_not_null:"+namedComponents.size());
		}else {
			System.out.println("named_components_is_null");
		}
		System.out.println("extractComponents_finished");
		return namedComponents;
	}
	
}