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

public class TeamTest extends TestCase {

	private GradesDB db = null;
	private Team team1Project1 = null;
	private Team team2Project2 = null;
	private Team team3Project3 = null;
	private List<Student> team1Project1Students = null;
	private List<Rating> team3Project3Ratings = null;

	protected void setUp() throws Exception
	{
		db = new GradesDB(Constants.GRADES_DB);
		team1Project1 = db.getAllTeams().getTeamByTeamAndProjectNumber(1, 1);
		team2Project2 = db.getAllTeams().getTeamByTeamAndProjectNumber(2, 2);
		team3Project3 = db.getAllTeams().getTeamByTeamAndProjectNumber(3, 3);
		team1Project1Students = db.getAllTeams().getStudentsByTeamAndProjectNumber(1, 1);
		team3Project3Ratings = db.getAllRatings().getRatingsByTeamAndProjectNumber(3, 3);
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testGetStudents()
	{
		// Initialize variables:
		String studentName = null;
		boolean result = false;
		
		for (Student s: team1Project1Students)
		{
			studentName = s.getName();
			
			// We have to match all 5 student names for a positive result:
			if (
				studentName.equals("Freddie Catlay") || 
				studentName.equals("Shevon Wise") || 
				studentName.equals("Kym Hiles") || 
				studentName.equals("Ernesta Anderson") ||
				studentName.equals("Sheree Gadow")
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
	
	public void testGetTeamNumber()
	{
		try
		{
			int teamNumber = team2Project2.getTeamNumber();
			assertEquals(2, teamNumber);
		}
		catch (Exception e)
		{
			fail("Exception");
		}
	}
	
	public void testGetProjectNumber()
	{
		try
		{
			int projectNumber = team3Project3.getProjectNumber();
			assertEquals(3, projectNumber);
		}
		catch (Exception e)
		{
			fail("Exception");
		}
	}
	
	public void testGetTeamGrade()
	{
		try
		{
			boolean result = false;
			
			if (
				team1Project1.getTeamGrade() == 93 ||
				team2Project2.getTeamGrade() == 96 ||
				team3Project3.getTeamGrade() == 102
			)
			{
				result = true;
			}
			
			assertTrue(result);
		}
		catch (Exception e)
		{
			fail("Exception");
		}
	}
	
	public void testGetRatings()
	{
		try
		{
			// Initialize variables:
			boolean result = false;
			
			for (Rating r: team3Project3Ratings)
			{				
				// We have to match all 5 student names for a positive result:
				if (
					(r.getStudent().getName().equals("Grier Nehling") && r.getAverage() >= 7.33 && r.getStudent().getName().equals("Grier Nehling") && r.getAverage() <= 7.34) ||
					 r.getStudent().getName().equals("Laraine Smith") && r.getAverage() == 7.00 ||
					 r.getStudent().getName().equals("Sheree Gadow") && r.getAverage() == 9.00 ||
					(r.getStudent().getName().equals("Ernesta Anderson") && r.getAverage() >= 8.66 && r.getStudent().getName().equals("Ernesta Anderson") && r.getAverage() <= 8.67)
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
	
	public void testHasTeamMember()
	{
		// Initialize variable:
		boolean result = false;
		
		// We have to match all 5 student names for a positive result:
		if (
			team1Project1.hasTeamMember("Freddie Catlay") &&
			team1Project1.hasTeamMember("Shevon Wise") &&
			team1Project1.hasTeamMember("Kym Hiles") &&
			team1Project1.hasTeamMember("Ernesta Anderson") &&
			team1Project1.hasTeamMember("Sheree Gadow") 
		)
		{
			result = true;
		}
		
		assertTrue(result);
	}
}
