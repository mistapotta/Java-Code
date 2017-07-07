package edu.gatech;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * Assignments
 * 
 * This class holds a flat list of all assignments
 * for all students in the course.
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Assignments implements AssignmentsInterface {

	private File file;
	private HashSet<Assignment> assignments;
	private GradesDB db;
	private ArrayList<String> assignmentNames;
	
	/**
	 * Creates a new Assignments instance
	 * 
	 * @param db
	 */
	public Assignments(GradesDB db) {
		this.db = db;
		initialize(db.getDatabase());
	}

	/**
	 * Gets list of Assignment objects provided a Student's Name
	 * 
	 * @param name
	 * @return List<Assignment>
	 */
	public List<Assignment> getAssignmentsByStudentName(String name) {
		List<Assignment> studentAssignments = new ArrayList<Assignment>();
		
		for (Assignment a: assignments)
		{
			if (a.getStudent().getName().compareTo(name) == 0)
			{
				studentAssignments.add(a);
			}	
		}
		
		return studentAssignments;
	}

	/**
	 * Gets list of Assignment objects provided a Student's Gtid
	 * 
	 * @param gtid
	 * @return List<Assignment>
	 */
	public List<Assignment> getAssignmentsByGtid(String gtid) {
		List<Assignment> studentAssignments = new ArrayList<Assignment>();
		
		for (Assignment a: assignments)
		{
			if (a.getStudent().getGtid().compareTo(gtid) == 0)
			{
				studentAssignments.add(a);
			}	
		}
		
		return studentAssignments;
	}

	/**
	 * Returns the overall number of assignment records.
	 * 
	 * (# of Students * # of Assignments = Overall # of Assignment Records)
	 * 
	 * @return integer
	 */
	public int getNumAssignments() {
		return this.assignments.size();
	}
	
	/**
	 * Returns a simple list containing the unique assignments names.
	 * 
	 * If there are 3 assignments in the course the size of this list
	 * will be 3 as well.
	 * 
	 * @return List<String>
	 */
	public List<String> getAssignmentNames() {
		return this.assignmentNames;
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
		this.assignments = new HashSet<Assignment>();
		this.assignmentNames = new ArrayList<String>();
		
		try {
			FileInputStream inp = new FileInputStream(this.file);
			Workbook wb = WorkbookFactory.create(inp);
			
			Table dataTable = new Table(wb.getSheet(GradesDB.DATA_SHEET_NAME), "Assignments", 3, 2);
			
			// Get the Grades Data:
            Table gradesTable = new Table(wb.getSheet(GradesDB.GRADES_SHEET_NAME), "NAME");
            
			Iterator<Map.Entry<Integer, Row>> iterator = dataTable.getRows().entrySet().iterator();
	    	
	    	while (iterator.hasNext()) {
	    	
	            Map.Entry<Integer, Row> pairs = iterator.next();
	            Row row = pairs.getValue();
	            
	            String assignmentName = row.getCell(dataTable.getColumnNumber("Assignments")).getStringCellValue();
	            int assignmentNumber = Integer.parseInt(assignmentName.replace("Assignment", "").replaceAll("\\s+", ""));
	            
	            // For this class we're maintaining a separate list of unique assignment names (so we can use it for the tests):
	            this.assignmentNames.add(assignmentName);
               
	            // Loop through the Students in the Grades Sheet for each Assignment:
	            Iterator<Map.Entry<Integer, Row>> gradesIterator = gradesTable.getRows().entrySet().iterator();
		    	
		    	while (gradesIterator.hasNext()) {

		            Map.Entry<Integer, Row> gradePairs = gradesIterator.next();
		            Row gradeRow = gradePairs.getValue();
		            
		            // Retrieve the Student Name and Assignment Grade for the current Assignment:
		            int assignmentGrade = (int) gradeRow.getCell(gradesTable.getColumnNumber(assignmentName)).getNumericCellValue();
            		String studentName = gradeRow.getCell(gradesTable.getColumnNumber("NAME")).getStringCellValue();
            		
            		if (!studentName.isEmpty())
            		{
            			Student studentObject = this.db.getAllStudents().getStudentByName(studentName);
            			
            			// Create and Add the Assignment Record to the list:
    		            Assignment assignment = new Assignment(assignmentNumber, assignmentGrade, studentObject);
    		            this.assignments.add(assignment);
            		}
		    	}
	        }
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the internal list of all assignments.
	 * 
	 * @return HashSet<Assignment>
	 */
	@Override
	public HashSet<Assignment> getAssignments() {
		return this.assignments;
	}
}
