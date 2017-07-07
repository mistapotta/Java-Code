package edu.gatech;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * Students
 * 
 * This class will hold a flat list of all students
 * in the course.
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */

public class Students implements StudentsInterface {
	
	private File file;
	private HashSet<Student> students;
	private GradesDB db;

	/**
	 * Creates a new Students instance.
	 * 
	 * @param db
	 */
	public Students(GradesDB db) {
		this.db = db;
		initialize(db.getDatabase());
	}
	
	/**
	 * Gets the number of students in the database
	 * 
	 * @return Number of students in database
	 */
	public int getNumStudents()
	{
		return this.students.size();
	}
	
	/**
	 * Gets a HashSet of students in the database
	 * 
	 * @return a HashSet of students in the database
	 */
	public HashSet<Student> getStudents()
	{
		return this.students;
	}
	
	/**
	 * Returns a student who matches the name parameter
	 * 
	 * @param name The name to be searched for
	 * @return The student who matches the name, or null.
	 */
	public Student getStudentByName(String name)
	{
		for (Student s: students)
		{
			if (s.getName().compareTo(name) == 0)
			{
				return s;
			}			
		}
	
		return null;
	}
	
	/**
	 * Returns a student who matches the ID parameter
	 * 
	 * @param name The ID to be searched for
	 * @return The student who matches the ID, or null.
	 */
	public Student getStudentByID(String id)
	{
		for (Student s: students)
		{
			if (s.getGtid().compareTo(id) == 0)
			{
				return s;
			}
		}

		return null;
	}

	/**
	 * Refreshes the data contained within the class.
	 * 
	 */
	public void refresh() {
		this.initialize(file);
	}
	
	/**
	 * Sets the internal File object for the class
	 * 
	 * @param database
	 */
	public void setFile(File database)
	{
		this.file = database;
	}

	/**
	 * Initializes all of the internal data for the class
	 * 
	 * @param database
	 */
	@Override
	public void initialize(File database) {
		
		this.file = database;
		this.students = new HashSet<Student>();
		
		try {
			FileInputStream inp = new FileInputStream(this.file);
			Workbook wb = WorkbookFactory.create(inp);
			
			Table studentTable = new Table(wb.getSheet(GradesDB.DETAILS_SHEET_NAME), "NAME");
			Table attendanceTable = new Table(wb.getSheet(GradesDB.ATTENDANCE_SHEET_NAME), "Student Name");
			
			Iterator<Map.Entry<Integer, Row>> iterator = studentTable.getRows().entrySet().iterator();
	    	
	    	while (iterator.hasNext()) {
	    	
	    		// Get the Basic Student Info:
				Map.Entry<Integer, Row> pairs = iterator.next();
	            //Integer rowNumber = pairs.getKey();
	            Row row = pairs.getValue();
	            
	            String studentName = row.getCell(studentTable.getColumnNumber("NAME")).getStringCellValue();
	            String studentGtid = String.valueOf((long) row.getCell(studentTable.getColumnNumber("GTID")).getNumericCellValue());
	            String studentEmail = row.getCell(studentTable.getColumnNumber("EMAIL")).getStringCellValue();
	            
	            // Get the Attendance Percentage:
	            Row studentAttendanceRow = attendanceTable.findUniqueRow(studentName);
	            int studentAttendancePercentage = (int) (studentAttendanceRow.getCell(attendanceTable.getColumnNumber("Total")).getNumericCellValue() * 100);
	            
	            // Create and Add the Student to the list:
	            Student student = new Student(studentName, studentGtid, studentEmail, studentAttendancePercentage);
	            this.students.add(student);
	        }
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Populates the internal list of Team objects for each student
	 * 
	 * Must be run after GradesDB has created it's internal Teams object.
	 * 
	 */
	public void populateTeams() {
		for (Student s: students)
		{
			String studentName = s.getName();
			List<Team> studentTeams = this.db.getAllTeams().getTeamsByStudentName(studentName);
			
			s.setTeams(studentTeams);
		}
	}	
}
