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
public class StudentTest extends TestCase{
	
	private GradesDB db = null;
	private Student stu = null;
	private Student stu2 = null;

	protected void setUp() throws Exception 
	{
		db = new GradesDB(Constants.GRADES_DB);
		stu = db.getStudentByID("901234502");
		stu2 = db.getStudentByName("Josepha Jube");
		super.setUp();
	}

	protected void tearDown() throws Exception 
	{
		super.tearDown();
	}

	public void testGetName() 
	{
		try 
		{
			String name = stu.getName();
			assertEquals("Josepha Jube", name);
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
	
	public void testGetID()
	{
		try 
		{
			String id = stu2.getGtid();
			assertEquals("901234502", id);
		}
		catch (Exception e)
		{
			fail ("Exception");
		}
	}
	public void testGetEmail() 
	{
		try {
			String email = stu.getEmail();
			assertEquals("jj@gatech.edu", email);
		} 
		catch (Exception e) 
		{
			fail("Exception");
		}
	}
		
	public void testGetAttendance() 
	{
		try 
		{
			int attendance = stu.getAttendance();
			assertEquals(80, attendance);
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
			List<Team> teams = stu.getTeams();
			
			// Initialize variable:
			boolean result = false;
			
			for (Team t: teams)
			{				
				// We have to verify all Teams have the student's name:
				if (t.hasTeamMember(stu.getName()))
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
	

}
