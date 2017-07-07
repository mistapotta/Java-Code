package edu.gatech;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

/**
 * 
 * GradesDBTest
 * 
 * This class tests the Teams class.
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */


public class TeamsTest extends TestCase {
	
	private GradesDB db = null;
	private Teams teams = null;
	
	protected void setUp() throws Exception
	{
		db = new GradesDB(Constants.GRADES_DB);
		teams = new Teams(db);
		super.setUp();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testGetTeamsByProjectNumber()
	{
		try
		{
			List<Team> project2Teams = teams.getTeamsByProjectNumber(2);
			
			// Initialize variable:
			boolean result = false;
			
			for (Team t: project2Teams)
			{				
				// We have to verify all 3 Teams belong to Project 2 for a positive result:
				if (t.getProjectNumber() == 2)
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
	
	public void testGetTeamsByStudentName()
	{
		try
		{
			List<Team> studentsTeams = teams.getTeamsByStudentName("Shevon Wise");
			
			// Initialize variable:
			boolean result = false;
			
			for (Team t: studentsTeams)
			{				
				// We have to match all 3 Teams and Projects Shevon is on for a positive result:
				if (
					(t.getTeamNumber() == 1 && t.getProjectNumber() == 1) ||
					(t.getTeamNumber() == 3 && t.getProjectNumber() == 2) ||
					(t.getTeamNumber() == 2 && t.getProjectNumber() == 3)
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
	
	public void testGetTeamByTeamAndProjectNumber()
	{
		try
		{
			Team team1 = teams.getTeamByTeamAndProjectNumber(2, 3);
			assertTrue(team1.hasTeamMember("Josepha Jube"));
		}
		catch (Exception e)
		{
			fail("Exception");
		}
	}

	public void testGetStudentsByTeamAndProjectNumber()
	{
		try
		{
			List<Student> team2Project2Students = teams.getStudentsByTeamAndProjectNumber(2, 2);
			
			// Initialize variables:
			String studentName = null;
			boolean result = false;
			
			for (Student s: team2Project2Students)
			{
				studentName = s.getName();
				
				// We have to match all 5 student names for a positive result:
				if (
					studentName.equals("Josepha Jube") || 
					studentName.equals("Caileigh Raybould") || 
					studentName.equals("Christine Schaeffer") || 
					studentName.equals("Ernesta Anderson") ||
					studentName.equals("Kym Hiles")
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
			fail ("Exception");
		}
	}
	
	public void testRefresh()
	{
		try
		{
			// Switch out the existing file for a new one:
			teams.setFile(new File(Constants.GRADES_DB.replace(".xlsx", "_modified.xlsx")));
			
			// The refresh method updates the existing Teams object:
			teams.refresh();
			
			// 3 Projects with 3 Teams each = 9 Total Teams by default
			assertTrue(teams.getNumTeams() > 9);
		}
		catch (Exception e)
		{
			fail("Exception");
		}
	}
	
	public void testGetNumTeams()
	{
		try
		{
			// 3 Projects with 3 Teams each = 9 Total Teams
			assertEquals(9, teams.getNumTeams());
		}
		catch (Exception e)
		{
			fail("Exception");
		}
		
	}

}
