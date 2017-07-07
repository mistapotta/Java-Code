package edu.gatech;

import java.util.List;

/**
 * 
 * Project
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Project {

	private int projectNumber = 0;
	private String projectDescription = "";
	private List<Team> teams;
	private int averageGrade = 0;

	/**
	 * Creates a new instance of Project 
	 * 
	 * @param projectNumber
	 * @param projectDescription
	 * @param averageGrade
	 */
	public Project(int projectNumber, String projectDescription, int averageGrade) {
		this.projectNumber = projectNumber;
		this.projectDescription = projectDescription;
		this.averageGrade = averageGrade;
	}
	
	/**
	 * Creates a new instance of Project
	 * 
	 * Additionally, requires a List of Team objects to be provided.
	 * 
	 * @param projectNumber
	 * @param projectDescription
	 * @param teams
	 * @param averageGrade
	 */
	public Project(int projectNumber, String projectDescription, List<Team> teams, int averageGrade) {
		this.projectNumber = projectNumber;
		this.projectDescription = projectDescription;
		this.teams = teams;
		this.averageGrade = averageGrade;
	}
	
	/**
	 * Returns the Project Number
	 * 
	 * @return integer
	 */
	public int getProjectNumber() {
		return this.projectNumber;
	}
	
	/**
	 * Sets the Project Number
	 * @param number
	 */
	public void setProjectNumber(int number) {
		this.projectNumber = number;
	}
	
	/**
	 * Gets the Project Description
	 * @return
	 */
	public String getProjectDescription() {
		return this.projectDescription;
	}

	/**
	 * Sets the Project Description
	 * 
	 * @param description
	 */
	public void setProjectDescription(String description) {
		this.projectDescription = description;
	}

	/**
	 * Returns the list of Team objects for the Project.
	 * 
	 * @return List<Team>
	 */
	public List<Team> getTeams() {
		return this.teams;
	}
	
	/**
	 * Sets the list of Team objects for the Project.
	 * 
	 * @param teams
	 */
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	/**
	 * Gets the Project's Average Grade.
	 * 
	 * @return integer
	 */
	public int getAverageGrade() {
		return this.averageGrade;
	}
	
	/**
	 * Sets the Project's Average Grade.
	 * 
	 * @param grade
	 */
	public void setAverageGrade(int grade) {
		this.averageGrade = grade;
	}
}
