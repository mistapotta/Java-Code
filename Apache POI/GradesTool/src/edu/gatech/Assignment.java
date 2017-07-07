package edu.gatech;

/**
 * 
 * Assignment
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Assignment {

	private int assignmentNumber = 0;
	private Student student;
	private int assignmentGrade;
	
	/**
	 * Creates a new instance of Assignment
	 * 
	 * @param assignmentNumber
	 * @param assignmentGrade
	 * @param student
	 */
	public Assignment(int assignmentNumber, int assignmentGrade, Student student)
	{
		this.assignmentNumber = assignmentNumber;
		this.assignmentGrade = assignmentGrade;
		this.student = student;
	}
	
	/**
	 * Gets the Assignment number
	 * 
	 * @return integer
	 */
	public int getAssignmentNumber() {
		return this.assignmentNumber;
	}

	/**
	 * Gets a reference to the Student object
	 * 
	 * @return Student
	 */
	public Student getStudent() {
		return this.student;
	}

	/**
	 * Gets the Grade for the Assignment
	 * 
	 * @return integer
	 */
	public int getAssignmentGrade() {
		return this.assignmentGrade;
	}

}
