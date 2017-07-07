package edu.gatech;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Team
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Team {

	private List<Student> studentList;
	private int teamNumber = 0;
	private int projectNumber = 0;
	private int teamGrade = 0;
	private List<Rating> ratings;
	
	/**
	 * Creates a new Team object.
	 * 
	 * @param teamNumber
	 * @param projectNumber
	 * @param teamGrade
	 * @param ratings
	 * @param studentList
	 */
	public Team(int teamNumber, int projectNumber, int teamGrade, List<Rating> ratings, List<Student> studentList) {
		this.teamNumber = teamNumber;
		this.projectNumber = projectNumber;
		this.teamGrade = teamGrade;
		this.ratings = ratings;
		this.studentList = studentList;
	}

	/**
	 * Gets the internal Student list for the Team.
	 * 
	 * @return List<Student>
	 */
	public List<Student> getStudents() {
		return this.studentList;
	}
	
	/**
	 * Gets the Team Number
	 * 
	 * @return integer
	 */
	public int getTeamNumber() {
		return this.teamNumber;
	}
	
	/**
	 * Sets the Team Number
	 * 
	 * @param number
	 */
	public void setTeamNumber(int number){
		this.teamNumber = number;
	}
	
	/**
	 * Gets the Project Number for the Team.
	 * 
	 * @return integer
	 */
	public int getProjectNumber() {
		return this.projectNumber;
	}

	/**
	 * Sets the Project Number for the Team.
	 * 
	 * @param number
	 */
	public void setProjectNumber(int number){
		this.teamGrade = number;
	}

	/**
	 * Gets the Team Grade
	 * 
	 * @return integer
	 */
	public int getTeamGrade() {
		return this.teamGrade;
	}

	/**
	 * Sets the Team Grade
	 * @param grade
	 */
	public void setTeamGrade(int grade){
		this.teamGrade = grade;
	}

	/**
	 * Gets the list of Rating objects for the Team
	 * 
	 * @return List<Rating>
	 */
	public List<Rating> getRatings() {
		return this.ratings;
	}

	/**
	 * Sets the list of Rating objects for the Team
	 * 
	 * @param ratings
	 */
	public void setRatings (List<Rating> ratings){
		this.ratings = ratings;
	}

	/**
	 * Adds a Student to the internal Student list for the Team
	 * 
	 * @param student
	 */
	public void addTeamMember(Student student) {
		this.studentList.add(student);
	}

	/**
	 * Checks to see if the Team has a Student object
	 * with the provided Student Name.
	 * 
	 * @param name
	 * @return boolean
	 */
	public boolean hasTeamMember(String name) 
	{
		for (Student s: this.studentList)
		{
			if (s.getName().compareTo(name) == 0)
			{
				return true;
			}
		}
		
		return false;
	}
}
