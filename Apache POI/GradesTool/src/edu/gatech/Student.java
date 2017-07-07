package edu.gatech;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 * Student
 * 
 * A class to hold the primary information about a student.
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Student {

	private String name;
	private String gtid;
	private String email;
	private int attendancePercentage;
	private List<Team> teams;

	/**
	 * Creates a new instance of Student.
	 * 
	 * @param studentName
	 * @param studentGtid
	 * @param studentEmail
	 */
	public Student(String studentName, String studentGtid, String studentEmail) {
		this.name = studentName;
		this.gtid = studentGtid;
		this.email = studentEmail;
	}
	
	/**
	 * Creates a new instance of Student.
	 * 
	 * Additionally, it takes in the attendance percentage value for the student.
	 * 
	 * @param name
	 * @param gtid
	 * @param email
	 * @param attendancePercentage
	 */
	public Student(String name, String gtid, String email, int attendancePercentage)
	{
		this.name = name;
		this.gtid = gtid;
		this.email = email;
		this.attendancePercentage = attendancePercentage;
	}

	/**
	 * Gets the Student's Name
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the Student's Name
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets the Student's Gtid
	 * 
	 * @return String
	 */
	public String getGtid()
	{
		return gtid;
	}
	
	/**
	 * Sets the Student's Gtid
	 * 
	 * @param gtid
	 */
	public void setGtid(String gtid)
	{
		this.gtid = gtid;
	}
	
	/**
	 * Gets the Student's Email
	 * 
	 * @return String
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * Sets the Student's Email
	 * 
	 * @param email
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * Gets the Student's Attendance Percentage
	 * 
	 * This is a proxy to getAttendancePercentage().
	 * 
	 * @return integer
	 */
	public int getAttendance()
	{
		return this.getAttendancePercentage();
	}
	
	/**
	 * Gets the Student's Attendance Percentage
	 * 
	 * @return integer
	 */
	public int getAttendancePercentage()
	{
		return attendancePercentage;
	}
	
	/**
	 * Sets the Student's Attendance Percentage.
	 * @param pct
	 */
	public void setAttendance(int pct)
	{
		this.attendancePercentage = pct;
	}
	
	/**
	 * Get's the Student's List of Team objects.
	 * 
	 * @return List<Team>
	 */
	public List<Team> getTeams()
	{
		return this.teams;
	}
	
	/**
	 * Sets the Student's List of Team objects.
	 * 
	 * @param teams
	 */
	public void setTeams(List<Team> teams)
	{
		this.teams = teams;
	}
}
