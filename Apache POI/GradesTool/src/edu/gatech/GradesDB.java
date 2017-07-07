package edu.gatech;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
 

/**
 * 
 * GradesDB
 * 
 * This class reads information from an Excel file and imports it into a Java class.
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class GradesDB implements OverallGradeCalculatorInterface {

	private Students students;
	private Projects projects;
	private Teams teams;
	private Assignments assignments;
	private Ratings ratings;
	
	private File file;
	private String filename;
	private String formula;
	
	public static String DETAILS_SHEET_NAME = "Details";
	public static String ATTENDANCE_SHEET_NAME = "Attendance";
	public static String DATA_SHEET_NAME = "Data";
	public static String GRADES_SHEET_NAME = "Grades";
	public static String OUTPUT_SPACING = "        ";
	
	
	/**
	 * Constructor for GradesDB
	 * 
	 * @param filename The file to be read.
	 */
	public GradesDB(String filename)
	{
		this.filename = filename;
		refresh();
	}
	
	/**
	 * Gets the number of students in the database
	 * 
	 * @return Number of students in database
	 */
	public int getNumStudents()
	{
		return this.students.getNumStudents();
	}
	
	/**
	 * Gets the number of assignments in the database
	 * 
	 * @return Number of assignments in the database
	 */
	public int getNumAssignments()
	{
		return this.getAllAssignments().getAssignmentNames().size();
	}
	
	/**
	 * Gets the number of projects in the database
	 * 
	 * @return Number of Projects in the database
	 * 
	 */
	public int getNumProjects()
	{
		return this.getAllProjects().getProjects().size();
	}
	
	/**
	 * Gets a HashSet of students in the database
	 * 
	 * @return a HashSet of students in the database
	 */
	public HashSet<Student> getStudents()
	{
		return this.students.getStudents();
	}
	
	/**
	 * Returns a student who matches the name parameter
	 * 
	 * @param name The name to be searched for
	 * @return The student who matches the name, or null.
	 */
	public Student getStudentByName(String name)
	{
		return this.students.getStudentByName(name);
	}
	
	/**
	 * Returns a student who matches the ID parameter
	 * 
	 * @param name The ID to be searched for
	 * @return The student who matches the ID, or null.
	 */
	public Student getStudentByID(String id)
	{
		return this.students.getStudentByID(id);
	}

	/**
	 * Generates a specific output string given a Student's Name.
	 * 
	 * @param name
	 * @return String
	 */
	public String getStudentInfo(String name) 
	{
		//refresh();
		Student student = getStudentByName(name);
		
		String output = "";
		
		if (student != null)
		{
			// Output:
			output = "Name: " + student.getName() + "\n" 
						  + "GTID: " + student.getGtid() + "\n" 
						  + "E-mail: " + student.getEmail() + "\n"
						  + "Attendance: " + student.getAttendancePercentage() + "%" + "\n";
			
			output += "\nProject Outcomes:\n";
			
			List<Team> studentTeams = this.getAllTeams().getTeamsByStudentName(student.getName());
			
			TreeMap<Integer, Integer> projectGrades = new TreeMap<Integer, Integer>();
			for (Team t: studentTeams)
			{
				projectGrades.put(t.getProjectNumber(), t.getTeamGrade());
			}
			
			for (Integer projectNumber : projectGrades.keySet()) {
			    int projectGrade = projectGrades.get(projectNumber);
			    output += OUTPUT_SPACING + "Project " + (projectNumber) + ": " + projectGrade + "\n";
			}
			
			output += "\nAvg. Project Contribution Ratings:\n";
			
			List<Rating> studentRatings = this.getAllRatings().getRatingsByStudentName(student.getName());
			
			TreeMap<Integer, Double> projectRatings = new TreeMap<Integer, Double>();
			for (Rating r: studentRatings)
			{
				projectRatings.put(r.getProjectNumber(), r.getAverage());
			}
			
			for (Integer ratingProjectNumber : projectRatings.keySet()) {
			    double averageRating = projectRatings.get(ratingProjectNumber);
			    output += OUTPUT_SPACING + "Project " + (ratingProjectNumber) + " Avg. Rating: " + averageRating + "\n";
			}

			output += "\nAssignments:\n";
			
			List<Assignment> studentAssignments = this.getAllAssignments().getAssignmentsByStudentName(student.getName());
			
			TreeMap<Integer, Integer> assignmentGrades = new TreeMap<Integer, Integer>();
			for (Assignment a: studentAssignments)
			{
				assignmentGrades.put(a.getAssignmentNumber(), a.getAssignmentGrade());
			}
			
			for (Integer assignmentNumber : assignmentGrades.keySet()) {
			    int assignmentGrade = assignmentGrades.get(assignmentNumber);
			    output += OUTPUT_SPACING + "Assignment " + (assignmentNumber) + ": " + assignmentGrade + "\n";
			}					 
		}
		
		return output;
	}

	/**
	 * Returns a reference to the internal Students object
	 * 
	 * @return Students
	 */
	public Students getAllStudents() {
		return this.students;
	}
	
	/**
	 * Returns a reference to the internal Projects object
	 * 
	 * @return Projects
	 */
	public Projects getAllProjects() {
		return this.projects;
	}

	/**
	 * Returns a reference to the internal Teams object
	 * 
	 * @return Teams
	 */
	public Teams getAllTeams() {
		return this.teams;
	}

	/**
	 * Returns a reference to the internal Ratings object
	 * 
	 * @return Ratings
	 */
	public Ratings getAllRatings() {
		return this.ratings;
	}
	
	/**
	 * Returns a reference to the internal Assignments object
	 * 
	 * @return Assignments
	 */
	public Assignments getAllAssignments() {
		return this.assignments;
	}
	
	/**
	 * Loads (or reloads) all of the internal data objects
	 * for the class.
	 * 
	 */
	public void refresh() {
		
		this.file = new File(filename);
		
		try {
			// Start with the Students:
			this.students = new Students(this);
			
			// Next do the Ratings (since Teams use them):
			this.ratings = new Ratings(this);
			
			// Next do the Teams (since Projects use them):
			this.teams = new Teams(this);
			
			// We can populate the Student Teams at this point:
			this.students.populateTeams();
	    	
			// Now Create the Projects:
	    	this.projects = new Projects(this);
	    	
	    	// And the Assignments:
	    	this.assignments = new Assignments(this);
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Allows the other classes to get a reference to the internal
	 * File object for GradesDB.
	 * 
	 * @return File
	 */
	public File getDatabase()
	{
		return this.file;
	}

	@Override
	public String getFormula() {
		return this.formula;
	}
	
	@Override
	public void setFormula(String formula) {
		this.formula = formula;
	}

	@Override
	public double getStudentGrade(Student student) {
		Double grade = 0.0;
		String formula = this.getFormula();
		
		if (!formula.isEmpty())
		{
			// Process the Formula and return a grade:
			ScriptEngineManager mgr = new ScriptEngineManager();
		    ScriptEngine engine = mgr.getEngineByName("JavaScript");
		    
		    formula = this.replaceOperands(formula, student);
		    try {
				grade = (Double) engine.eval(formula);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return grade;
	}
	
	private String replaceOperands(String formula, Student student)
	{
		// Get the Student's Average Grade:
		int sum = 0;
		Double average = 0.0;
		List<Assignment> studentsAssignments = this.getAllAssignments().getAssignmentsByStudentName(student.getName());
		
		for (Assignment a: studentsAssignments)
		{
			sum += a.getAssignmentGrade();
		}
		
		if (sum > 0)
		{
			average = (double) (sum / studentsAssignments.size());
		}
		
		formula = formula.replace("AS", String.valueOf(average));
		formula = formula.replace("AT", String.valueOf(student.getAttendancePercentage()));
		
		// Replace Project Grades for each Team the Student was on:
		List<Team> teams = this.getAllTeams().getTeamsByStudentName(student.getName());
		
		for (Team t: teams)
		{
			String projectOperand = "PR" + String.valueOf(t.getProjectNumber());
			formula = formula.replace(projectOperand, String.valueOf(t.getTeamGrade()));
		}
		
		// Replace Average Contribution Ratings for each Team the Student was on:
		List<Rating> ratings = this.getAllRatings().getRatingsByStudentName(student.getName());
		
		for (Rating r: ratings)
		{
			
			String contributionOperand = "IC" + String.valueOf(r.getProjectNumber());
			formula = formula.replace(contributionOperand, String.valueOf(r.getAverage()));
		}
		
		return formula;
	}

	@Override
	public Map<Student, Double> getAllGrades() {
		HashMap<Student, Double> allGrades = new HashMap<Student, Double>();
		
		for (Student s: this.getAllStudents().getStudents())
		{
			double grade = this.getStudentGrade(s);
			
			allGrades.put(s, grade);
		}
		
		return allGrades;
	}

	@Override
	public double getAverageGrade() {
		Double sum = 0.0;
		Double average = 0.0;
		Map<Student, Double> grades = this.getAllGrades();
		
		// Retrieve the Iterator:
		Iterator<Map.Entry<Student, Double>> iterator = grades.entrySet().iterator();
    	
		// Loop through the list:
    	while (iterator.hasNext()) {
    	
            Map.Entry<Student, Double> pairs = iterator.next();
            Double grade = pairs.getValue();
            sum += grade;
    	}
		
		if (sum > 0)
		{
			average = (double) (sum / grades.size());
		}
		
		return average;
	}

	@Override
	public double getMedianGrade() {
		Double median = 0.0;
		
		Map<Student, Double> grades = this.getAllGrades();
		int numberOfGrades = grades.size();
		Double gradesList[] = new Double[numberOfGrades];
		
		// Retrieve the Iterator:
		Iterator<Map.Entry<Student, Double>> iterator = grades.entrySet().iterator();
    	
		// Loop through the list:
		int index = 0;
    	while (iterator.hasNext()) {
    	
            Map.Entry<Student, Double> pairs = iterator.next();
            Double grade = pairs.getValue();
            gradesList[index] = grade;
            index++;
    	}
		
		// Sort array by value:
		Arrays.sort(gradesList);
		
		// Calculate Median:
		if (numberOfGrades % 2 == 0) 
		{ 
			// Even # of Assignments:
			int middle = (numberOfGrades / 2);
			Double middleValue = gradesList[middle - 1];
			Double middlePlusOneValue = gradesList[middle];
			
			median = (double) (middleValue + middlePlusOneValue) / 2;
		} 
		else 
		{
			// Odd # of Assignments:
			int middle = ((numberOfGrades + 1) / 2);
			median = (double) gradesList[middle];
		}
		
		return median;
	}
}
