package edu.gatech;

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
public class ProjectTest extends TestCase{
	
	private GradesDB db = null;
	private Project project = null;
	private Project project2 = null;
	private Project project3 = null;
	private List<Team> teams = null;
	
	protected void setUp() throws Exception 
	{
		db = new GradesDB(Constants.GRADES_DB);
		
		project = db.getAllProjects().getProjectByNumber(1);
		project2 = db.getAllProjects().getProjectByDescription("GroceryList App");
		project3 = db.getAllProjects().getProjectByDescription("GroceryList Manager");
		
		teams = db.getAllTeams().getTeamsByProjectNumber(1);
		
		super.setUp();
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}

	public void testGetDescription() 
	{
		try 
		{
			String description = project.getProjectDescription();
			assertEquals("WordCount in Java", description);
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testGetNumber() 
	{
		try 
		{
			int number = project2.getProjectNumber();
			assertEquals(2, number);
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testGetTeams() 
	{
		try 
		{
			assertEquals(teams, project.getTeams());
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testGetAverageProjectGrade() 
	{
		try 
		{
			assertEquals(99, project3.getAverageGrade());
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
}
