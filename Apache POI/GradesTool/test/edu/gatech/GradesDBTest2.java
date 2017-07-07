package edu.gatech;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import junit.framework.TestCase;
import edu.gatech.GradesDB;
import edu.gatech.Student;

/**
 * 
 * GradesDBTest
 * 
 * This class tests the GradesDB class.
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class GradesDBTest2 extends TestCase {
	GradesDB db = null;
	
	protected void setUp() throws Exception {
		db = new GradesDB(Constants.GRADES_DB);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetNumStudents() {
		try {
			int numStudents = db.getNumStudents();
			assertEquals(14, numStudents);
		} catch (Exception e) {
			fail("Exception");
		}
	}

	public void testGetNumAssignments() {
		try {
			int numAssignments = db.getNumAssignments();
			assertEquals(3, numAssignments);
		} catch (Exception e) {
			fail("Exception");
		}
	}

	public void testGetNumProjects() {
		int numProjects;
		try {
			numProjects = db.getNumProjects();
			assertEquals(3, numProjects);
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	public void testGetStudents1() {
		HashSet<Student> students = null;
		try {
			students = db.getStudents();
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(14, students.size());
	}

	public void testGetStudents2() {
		HashSet<Student> students = null;
		try {
			students = db.getStudents();
		} catch (Exception e) {
			fail("Exception");
		}
		boolean found = false;
		for (Student s : students) {
			if ((s.getName().compareTo("Cynthia Faast") == 0) && (s.getGtid().compareTo("901234514") == 0)) {
				found = true;
				break;
			}
		}
		assertTrue(found);
	}
	
	public void testGetStudentsByName1(){
		Student student = null;
		try {
			student = db.getStudentByName("Rastus Kight");
		} catch (Exception e) {
			fail("Exception");
		}
		assertTrue(student.getGtid().compareTo("901234512") == 0);
	}

	public void testGetStudentsByName2(){
		Student student = null;
		try {
			student = db.getStudentByName("Grier Nehling");
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(96, student.getAttendance());
	}

	public void testGetStudentsByID(){
		try {
			Student student = db.getStudentByID("901234504");
			assertTrue(student.getName().compareTo("Shevon Wise") == 0);
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	public void testGetStudentInfo()
	{
		try
		{
			String output = db.getStudentInfo("Josepha Jube");
			String properOutput = 	"Name: Josepha Jube\n" +
									"GTID: 901234502\n" +
									"E-mail: jj@gatech.edu\n" +
									"Attendance: 80%\n" +
									"\nProject Outcomes:\n" +
										GradesDB.OUTPUT_SPACING + "Project 1: 96\n" +
										GradesDB.OUTPUT_SPACING + "Project 2: 96\n" +
										GradesDB.OUTPUT_SPACING + "Project 3: 96\n" +
									"\nAvg. Project Contribution Ratings:\n" +
										GradesDB.OUTPUT_SPACING + "Project 1 Avg. Rating: 9.5\n" +
										GradesDB.OUTPUT_SPACING + "Project 2 Avg. Rating: 7.75\n" +
										GradesDB.OUTPUT_SPACING + "Project 3 Avg. Rating: 9.25\n" +
									"\nAssignments:\n" +
										GradesDB.OUTPUT_SPACING + "Assignment 1: 100\n" +
										GradesDB.OUTPUT_SPACING + "Assignment 2: 100\n" +
										GradesDB.OUTPUT_SPACING + "Assignment 3: 71\n";
			
			assertTrue(output.equals(properOutput));
		}
		catch (Exception e)
		{
			fail ("Exception");
		}
	}
	
	public void testRefreshMethod()
	{
		try
		{
			db.refresh();
			Projects projects = db.getAllProjects();
			String newProjectOne = projects.getProjectByNumber(1).getProjectDescription();
			assertTrue(newProjectOne.equals("WordCount in Java"));
			
		}
		catch (Exception e)
		{
			fail("Exception");
		}
	}
		
	public void testGetAllGrades()
	{
		try
		{
			// Set the Test formula:
			this.db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
			
			// Get the Calculated Grades List:
			Map<Student, Double> grades = this.db.getAllGrades();
			
			// Initialize variable:
			boolean result = false;
			
			// Retrieve the Iterator:
			Iterator<Map.Entry<Student, Double>> iterator = grades.entrySet().iterator();
	    	
			// Loop through the list:
	    	while (iterator.hasNext()) {
	    	
	            Map.Entry<Student, Double> pairs = iterator.next();
	            Student student = pairs.getKey();
	            Double grade = pairs.getValue();
	            
				// We have to match all student names and their calculated grades for a positive result:
				if (
						student.getName().equals("Wilfrid Eastwood") && grade == 93.6 ||
						student.getName().equals("Grier Nehling") && grade == 93.4 ||
						student.getName().equals("Genista Parrish") && grade == 91.19999999999999 ||
						student.getName().equals("Caileigh Raybould") && grade == 93.4 ||
						student.getName().equals("Josepha Jube") && grade == 91.6 ||
						student.getName().equals("Kym Hiles") && grade == 95.2 ||
						student.getName().equals("Shevon Wise") && grade == 91.0 ||
						student.getName().equals("Ernesta Anderson") && grade == 93.4 ||
						student.getName().equals("Christine Schaeffer") && grade == 93.39999999999999 ||
						student.getName().equals("Laraine Smith") && grade == 98.6 ||
						student.getName().equals("Cynthia Faast") && grade == 95.2 ||
						student.getName().equals("Sheree Gadow") && grade == 95.2 ||
						student.getName().equals("Rastus Kight") && grade == 91.4 ||
						student.getName().equals("Freddie Catlay") && grade == 94.19999999999999
					)
				{
					result = true;
				}
				else
				{
					result = false;
				}
			}
			
			assertTrue(result);
		}
		catch (Exception e)
		{
			fail("Exception");
		}
	}
	
	public void testGetAverageGrade() {
		try {
			// Set the Test formula:
			this.db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
			
			Double expectedAverage = 93.62857142857142;
			
			// Get the Calculated Average Grade for the Class:
			Double averageGrade = this.db.getAverageGrade();
			
			assertEquals(expectedAverage, averageGrade, 0.01);
			
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	public void testGetMedianGrade() {
		try {
			// Set the Test formula:
			this.db.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6");
			
			Double expectedMedian = 93.4;
			
			// Get the Calculated Grades List:
			Double medianGrade = this.db.getMedianGrade();
			
			assertEquals(expectedMedian, medianGrade);
			
		} catch (Exception e) {
			fail("Exception");
		}
	}
}
