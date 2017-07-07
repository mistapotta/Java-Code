package edu.gatech;

/**
 * 
 * Rating
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Rating {
	
	private int teamNumber = 0;
	private int projectNumber = 0;
	private Student student;
	private double averageContributionRating;
	
	/**
	 * Creates a new instance of Rating.
	 * 
	 * @param teamNumber
	 * @param projectNumber
	 * @param averageContributionRating
	 * @param studentObject
	 */
	public Rating(int teamNumber, int projectNumber, Double averageContributionRating, Student studentObject) {
		this.teamNumber = teamNumber;
		this.projectNumber = projectNumber;
		this.averageContributionRating = averageContributionRating;
		this.student = studentObject;
	}

	/**
	 * Returns the Rating's Team Number
	 * 
	 * @return integer
	 */
	public int getTeamNumber() {
		return this.teamNumber;
	}
	
	/**
	 * Returns the Rating's Project Number
	 * 
	 * @return integer
	 */
	public int getProjectNumber() {
		return this.projectNumber;
	}
	
	/**
	 * Returns the Rating's Student object
	 * 
	 * @return Student
	 */
	public Student getStudent() {
		return this.student;
	}

	/**
	 * Returns the Average Contribution Rating
	 * 
	 * @return double
	 */
	public double getAverage() {
		return this.averageContributionRating;
	}

}
