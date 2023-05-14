package simpleController9.api.view.delegator;

import simpleController9.ViewContainer;

public interface Delegator {
	
	public void inject(ViewContainer viewContainer);
	public void clean(ViewContainer viewContainer);

}