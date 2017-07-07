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

public class RatingTest extends TestCase {

	private GradesDB db = null;
	private List<Rating> team2Project2Ratings = null;

	protected void setUp() throws Exception
	{
		db = new GradesDB(Constants.GRADES_DB);
		team2Project2Ratings = db.getAllRatings().getRatingsByTeamAndProjectNumber(2, 2);
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testGetTeam()
	{
		// Initialize variables:
		boolean result = false;
		
		for (Rating r: team2Project2Ratings)
		{			
			// We have to match all 5 ratings teams/project numbers for a positive result:
			if (r.getTeamNumber() == 2 || r.getProjectNumber() == 2)
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
		// Initialize variables:
		boolean result = false;
		
		for (Rating r: team2Project2Ratings)
		{			
			// We have to match all 5 student names for a positive result:
			if (
				r.getStudent().getName().equals("Josepha Jube") ||
				r.getStudent().getName().equals("Caileigh Raybould") ||
				r.getStudent().getName().equals("Christine Schaeffer") ||
				r.getStudent().getName().equals("Ernesta Anderson") ||
				r.getStudent().getName().equals("Kym Hiles")
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
	
	public void testGetAverage()
	{
		// Initialize variable:
		boolean result = false;
		
		for (Rating r: team2Project2Ratings)
		{			
			// We have to match all 5 student averages for a positive result:
			if (
				r.getAverage() == 7.75 ||
				r.getAverage() == 8.50 ||
				r.getAverage() == 8.00 ||
				r.getAverage() == 6.75 ||
				r.getAverage() == 7.00
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
