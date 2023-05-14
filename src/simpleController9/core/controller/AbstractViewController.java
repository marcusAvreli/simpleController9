package simpleController9.core.controller;

import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.EventObject;

import simpleController9.ViewContainer;
import simpleController9.api.controller.ViewController;

//https://github.com/mariogarcia/viewa/blob/c39f7f46dc39908bd23cd4ded0b60c5f555617b8/core/src/main/java/org/viewaframework/controller/AbstractViewController.java
public abstract class AbstractViewController <EL extends EventListener,EO extends EventObject> 
implements ViewController<EL,EO>{
	public void executeHandler(final ViewContainer view) {
	//	this.currentView = view;
		//this.viewManager = view.getApplication().getViewManager();
	}
	public Object invoke(Object proxy, Method method, Object[] args){
		return null;
	}
}
