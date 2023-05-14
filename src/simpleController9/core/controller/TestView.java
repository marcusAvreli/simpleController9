package simpleController9.core.controller;

import java.awt.event.ActionListener;

import simpleController9.DefaultView;

public class TestView extends DefaultView{
	public static final String ID = "ControllerTestViewId";	
	/**
	 * Default constructor
	 */
	public TestView(){
		super(ID,"testTitle",new TestPanel());
		
	}
	public void addActionListener(ActionListener al) {
		
	}
}