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
 * Projects
 * 
 * This class holds a flat list of all projects
 * in the course.
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Projects implements ProjectsInterface {
	
	private File file;
	private HashSet<Project> projects;
	private GradesDB db;
	
	/**
	 * Creates a new Projects instance
	 * 
	 * @param db
	 */
	public Projects(GradesDB db) {
		this.db = db;
		initialize(db.getDatabase());
		
	}

	/**
	 * Gets a specific Project by its Project Number.
	 * 
	 * @param number
	 * @return Project
	 */
	public Project getProjectByNumber(int number) {
		for (Project p: projects)
		{
			if (p.getProjectNumber() == number)
			{
				return p;
			}
		}

		return null;
	}

	/**
	 * Gets a specific Project by its Project Description
	 * 
	 * @param description
	 * @return Project
	 */
	public Project getProjectByDescription(String description) {
		for (Project p: projects)
		{
			if (p.getProjectDescription().compareTo(description) == 0)
			{
				return p;
			}
		}

		return null;
	}
	
	/**
	 * Gets the Total Number of Projects
	 * 
	 * @return integer
	 */
	public int getNumProjects() {
		return this.projects.size();
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
		this.projects = new HashSet<Project>();
		
		try {
			FileInputStream inp = new FileInputStream(this.file);
			Workbook wb = WorkbookFactory.create(inp);
			
			Table dataTable = new Table(wb.getSheet(GradesDB.DATA_SHEET_NAME), "Projects", 0, 2);
			
			Iterator<Map.Entry<Integer, Row>> iterator = dataTable.getRows().entrySet().iterator();
	    	
	    	while (iterator.hasNext()) {
	    	
	    		// Get the Basic Student Info:
	            Map.Entry<Integer, Row> pairs = iterator.next();
	            //Integer rowNumber = pairs.getKey();
	            Row row = pairs.getValue();
	            
	            String projectName = row.getCell(dataTable.getColumnNumber("Projects")).getStringCellValue();
	            int projectNumber = Integer.parseInt(projectName.replace("P", ""));
	            String projectDescription = row.getCell(dataTable.getColumnNumber("Description")).getStringCellValue();
	            
	            // Get the Average Grade for the Project:
	            String projectSheetName = projectName + " Grades";
	            Table projectTable = new Table(wb.getSheet(projectSheetName), "Criteria");
	            
	            Row totalRow = projectTable.findUniqueRow("TOTAL:");
	            
	            // Collect the Project Grades into a List:
	            List<Double> projectGrades = new ArrayList<Double>();
	            for (Cell cell: totalRow)
	            {
	            	if (cell.getColumnIndex() == 0 || cell.getColumnIndex() == 1)
	            	{
	            		continue;
	            	}
	            	else
	            	{
	            		if (cell.getNumericCellValue() > 0)
	            		{
	            			projectGrades.add(cell.getNumericCellValue());
	            		}
	            	}
	            }
	            
	            int projectAverageGrade = 0;
	            
	            // Calculate the Project Average Grade:
	            if (projectGrades.size() > 0)
	            {
	            	Double projectGradesSum = 0.0;
		            for (Double projectGrade: projectGrades)
		            {
		            	projectGradesSum += projectGrade;
		            }
		            
		            projectAverageGrade = (int) (projectGradesSum / projectGrades.size());	
	            }
	            
	            // Get Teams List for the Project:
	            List<Team> teams = this.db.getAllTeams().getTeamsByProjectNumber(projectNumber);
	            
	            // Create and Add the Project to the list:
	            Project project = new Project(projectNumber, projectDescription, teams, projectAverageGrade);
	            this.projects.add(project);
	        }
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Returns the internal list of Project objects.
	 * 
	 * @return HashSet<Project>
	 */
	@Override
	public HashSet<Project> getProjects() {
		return this.projects;
	}
	
}
