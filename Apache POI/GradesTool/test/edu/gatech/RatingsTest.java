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
public class RatingsTest extends TestCase{
	
	private GradesDB db = null;
	private Ratings ratings = null;
	
	protected void setUp() throws Exception 
	{
		db = new GradesDB(Constants.GRADES_DB);
		
		ratings = new Ratings(db);
		
		super.setUp();
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}

	public void testGetRatingsByTeamAndProjectNumber() 
	{
		try 
		{
			List<Rating> team2Project3Ratings = ratings.getRatingsByTeamAndProjectNumber(2, 3);
			
			// Initialize variable:
			boolean result = false;
			
			for (Rating r: team2Project3Ratings)
			{			
				// We have to match all 5 student averages for a positive result:
				if (
					r.getAverage() == 9.25 ||
					r.getAverage() == 8.25 ||
					r.getAverage() == 9.25 ||
					r.getAverage() == 8.00 ||
					r.getAverage() == 6.75
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
	
	public void testGetRatingsByStudentName() 
	{
		try 
		{
			List<Rating> wilfridEastwoodRatings = ratings.getRatingsByStudentName("Wilfrid Eastwood");
			
			// Initialize variable:
			boolean result = false;
			
			for (Rating r: wilfridEastwoodRatings)
			{			
				// We have to match all 5 student averages for a positive result:
				if (
					r.getAverage() == 5.50 ||
					r.getAverage() == 8.25 ||
					r.getAverage() == 8.50
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
	
	public void testRefresh() 
	{
		try 
		{	
			// Switch out the existing file for a new one:
			ratings.setFile(new File(Constants.GRADES_DB.replace(".xlsx", "_modified.xlsx")));
			
			// The refresh method updates the existing Projects object:
			ratings.refresh();
			
			// Assert the size of the Projects List is now greater than 3:
			assertTrue((ratings.getNumRatings() > 42));
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testGetNumRatings()
	{
		try
		{
			// 3 Projects with 3 Teams (2 Teams of 5 Students, 1 Team of 4 Students)
			// (3 x 2 x 5) + (3 x 1 x 4) = 30 + 12 = 42 Total Ratings
			assertEquals(42, ratings.getNumRatings());
		}
		catch (Exception e)
		{
			fail("Exception");
		}
		
	}
}
