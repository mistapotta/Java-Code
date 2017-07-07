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
public class ProjectsTest extends TestCase{
	
	private GradesDB db = null;
	private Projects projects = null;
	
	protected void setUp() throws Exception 
	{
		db = new GradesDB(Constants.GRADES_DB);
		
		projects = new Projects(db);
		
		super.setUp();
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}

	public void testGetProjectByNumber() 
	{
		try 
		{
			Project project1 = projects.getProjectByNumber(1);
			
			String description = project1.getProjectDescription();
			assertEquals("WordCount in Java", description);
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testGetProjectByName() 
	{
		try 
		{
			Project project2 = projects.getProjectByDescription("GroceryList App");
			
			int number = project2.getProjectNumber();
			assertEquals(2, number);
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testRefresh() 
	{
		try 
		{	
			// Switch out the existing file for a new one:
			projects.setFile(new File(Constants.GRADES_DB.replace(".xlsx", "_modified.xlsx")));
			
			// The refresh method updates the existing Projects object:
			projects.refresh();
			
			// Assert the size of the Projects List is now greater than 3:
			assertTrue((projects.getNumProjects() > 3));
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testGetNumProjects()
	{
		try
		{
			// 3 Projects Total:
			assertEquals(3, projects.getNumProjects());
		}
		catch (Exception e)
		{
			fail("Exception");
		}
		
	}
}
