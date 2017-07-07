package edu.gatech;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

/**
 * 
 * GradesDBTest
 * 
 * This class tests the Student class.
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class AssignmentsTest extends TestCase{
	
	private GradesDB db = null;
	private Assignments assignments = null;
	
	protected void setUp() throws Exception 
	{
		db = new GradesDB(Constants.GRADES_DB);
		
		assignments = new Assignments(db);
		
		super.setUp();
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}

	public void testGetAssignmentsByStudentName()
	{
		// Initialize variable:
		boolean result = false;
		
		List<Assignment> cynthiaFaastAssignments = assignments.getAssignmentsByStudentName("Cynthia Faast");
		
		for (Assignment a: cynthiaFaastAssignments)
		{			
			// We have to match the student name in all 3 assignments for a positive result:
			if (a.getStudent().getName().equals("Cynthia Faast"))
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
	
	public void testGetAssignmentsByGtid()
	{
		// Initialize variable:
		boolean result = false;
		
		List<Assignment> kymHilesAssignments = assignments.getAssignmentsByGtid("901234507");
		
		for (Assignment a: kymHilesAssignments)
		{			
			// We have to match the student name in all 3 assignments for a positive result:
			if (a.getStudent().getName().equals("Kym Hiles"))
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
	
	public void testRefresh() 
	{
		try 
		{	
			// Switch out the existing file for a new one:
			assignments.setFile(new File(Constants.GRADES_DB.replace(".xlsx", "_modified.xlsx")));
			
			// The refresh method updates the existing Assignments object:
			assignments.refresh();
			
			// Assert the size of the Assignments List is now bigger than
			// 14 students x 3 assignments each = 42 assignment records
			assertTrue((assignments.getNumAssignments() > 42));
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testGetNumAssignments()
	{
		try
		{
			// 14 students x 3 assignments each = 42 assignment records
			assertEquals(42, assignments.getNumAssignments());
		}
		catch (Exception e)
		{
			fail("Exception");
		}
		
	}
}
