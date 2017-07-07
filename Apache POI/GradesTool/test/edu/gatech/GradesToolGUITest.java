package edu.gatech;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;

import org.junit.Test;

import junit.framework.TestCase;



public class GradesToolGUITest extends TestCase {

	static GradesToolGUI gui = new GradesToolGUI();

	protected void setUp() throws Exception {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				gui.setVisible(true);
			}
		});
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public Component findComponentByName(Container container, String componentName) {
		for (Component component: container.getComponents()) {
			if (componentName.equals(component.getName())) {
				return component;
			}
			if (component instanceof JRootPane) {
				JRootPane nestedJRootPane = (JRootPane)component;
				return findComponentByName(nestedJRootPane.getContentPane(), componentName);
			}
			if (component instanceof JPanel) {
				JPanel nestedJPanel = (JPanel)component;
				return findComponentByName(nestedJPanel, componentName);
			}
		}
		return null;
	}

	@Test
	public void testComboBox() {
		JComboBox comboBox = null;
		try {
			comboBox = (JComboBox)findComponentByName(gui,"combo");		
		} catch (Exception e) {
			fail("Exception");
		}
		int nItems=comboBox.getItemCount();
		int nStudents=gui.studentCount();
		
		assertEquals(nItems-1,nStudents);
		
		for (int i = 1; i < nItems; i++) {  
			assertTrue(gui.studentSet().contains(gui.getStudentByName((String) comboBox.getItemAt(i))));
		}

	}


	@Test
	public void testTextBox() {
		JTextArea textBox = null;
		JComboBox comboBox = null;
		try {
			//textBox = (JTextArea)findComponentByName(gui,"text");
			textBox = gui.getStudentInformationTextArea();
			comboBox = (JComboBox)findComponentByName(gui,"combo");
		} catch (Exception e) {
			fail("Exception");
			
		}
		int nItems=comboBox.getItemCount();
		
		for (int i = 1; i < nItems; i++) {  
			comboBox.setSelectedIndex(i);
			assertEquals(gui.getStudentInfo((String)comboBox.getSelectedItem()),textBox.getText());
		}


	}


	@Test
	public void testSaveButton() {

		JButton saveButton = null;
		JComboBox comboBox = null;
		JTextArea textBox = null;
		String studentName = null;
		try {
			comboBox = (JComboBox)findComponentByName(gui,"combo");
			textBox = gui.getStudentInformationTextArea();
			comboBox.setSelectedIndex(1);

			studentName = (String)comboBox.getSelectedItem();
			saveButton=(JButton)findComponentByName(gui,"save");
			saveButton.doClick();
		} catch (Exception e) {
			fail("Exception");
		}
		
		String saveFileName = studentName.replaceAll(" ", "_")+".txt";
		assertTrue(new File(saveFileName).exists());
		String studentInfoFromDb = gui.getStudentInfo(studentName);
		
		String studentInfoFromFile = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(saveFileName));
			StringBuffer sb = new StringBuffer();
			char [] data = new char[1024];
			int len = 0;
			while((len = reader.read(data,0,data.length)) > 0) {
				sb.append(data,0, len);
			}
			reader.close();
			studentInfoFromFile = sb.toString();
		} catch(IOException e) {
			fail("Exception");
		} finally {
			(new File(saveFileName)).delete();
		}
		
		assertEquals(studentInfoFromDb, studentInfoFromFile);
		
	}
	
	@Test
	public void testTitle() {
		
		assertEquals("CS6300 GRADING TOOL",gui.getTitle());

	}





}
