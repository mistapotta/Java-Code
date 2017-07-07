package edu.gatech;

import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

/**
 * 
 * GradesDBTest
 * 
 * This class tests the Team class.
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */

public class AssignmentTest extends TestCase {

	private GradesDB db = null;
	private Assignments assignments = null;

	protected void setUp() throws Exception
	{
		db = new GradesDB(Constants.GRADES_DB);
		assignments = db.getAllAssignments();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testGetAssignmentNumber()
	{
		// Initialize variable:
		boolean result = false;
		
		List<Assignment> christineSchaefferAssignments = assignments.getAssignmentsByStudentName("Christine Schaeffer");
		
		for (Assignment a: christineSchaefferAssignments)
		{			
			// We have to match all 3 assignment numbers for a positive result:
			if (
				a.getAssignmentNumber() == 1 ||
				a.getAssignmentNumber() == 2 ||
				a.getAssignmentNumber() == 3
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
	
	public void testGetStudent()
	{
		// Initialize variable:
		boolean result = false;
		
		List<Assignment> rastusKightAssignments = assignments.getAssignmentsByStudentName("Rastus Kight");
		
		for (Assignment a: rastusKightAssignments)
		{			
			// We have to match the student name in all 3 assignments for a positive result:
			if (a.getStudent().getName().equals("Rastus Kight"))
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
	
	public void testGetAssignmentGrade()
	{
		// Initialize variable:
		boolean result = false;
		
		List<Assignment> shereeGadowAssignments = assignments.getAssignmentsByStudentName("Sheree Gadow");
		
		for (Assignment a: shereeGadowAssignments)
		{			
			// We have to match the student name in all 3 assignments for a positive result:
			if (
				a.getAssignmentGrade() == 100 ||
				a.getAssignmentGrade() == 110 ||
				a.getAssignmentGrade() == 90
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
}
