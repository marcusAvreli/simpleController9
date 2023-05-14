package simpleController9.core.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import simpleController9.ViewManager;

/**
 * @author Mario Garcia
 * @since 1.0.2
 * 
 */
public class TestController extends  AbstractViewController<ActionListener, ActionEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.viewaframework.controller.ViewController#getSupportedClass()
	 */
	public Class<ActionListener> getSupportedClass() {
		return ActionListener.class;
	}
	@Override
	public ViewManager getViewManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractViewController getTargetController() {
		// TODO Auto-generated method stub
		return null;
	}
public void addActionListener(ActionListener al) {
	
}

	
}