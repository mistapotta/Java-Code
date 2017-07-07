package edu.gatech;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * Teams
 * 
 * This class holds a flat list of all teams
 * for all projects in the course.
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Teams implements TeamsInterface{
	
	private HashSet<Team> teams;
	private File file;
	private GradesDB db;

	/**
	 * Creates a new Teams instance
	 * 
	 * @param db
	 */
	public Teams(GradesDB db) {
		this.db = db;
		initialize(db.getDatabase());
	}

	/**
	 * Retrieves a Team provided a specific Team and Project Number combination
	 *  
	 * @param teamNumber
	 * @param projectNumber
	 * @return Team
	 */
	public Team getTeamByTeamAndProjectNumber(int teamNumber, int projectNumber) {
		for (Team t: teams)
		{
			if (t.getTeamNumber() == teamNumber && t.getProjectNumber() == projectNumber)
			{
				return t;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets a list of Team objects provided a specific Student's Name
	 * 
	 * @param name
	 * @return List<Team>
	 */
	public List<Team> getTeamsByStudentName(String name) {

		List<Team> studentTeams = new ArrayList<Team>();
		
		for (Team t: teams)
		{
			for (Student s: t.getStudents())
			{
				if (s.getName().compareTo(name) == 0)
				{
					studentTeams.add(t);
					break;
				}	
			}
		}
		
		return studentTeams;
	}

	/**
	 * Gets a list of Team objects provided a specific Project Number.
	 * 
	 * @param projectNumber
	 * @return List<Team>
	 */
	public List<Team> getTeamsByProjectNumber(int projectNumber) {
		List<Team> projectTeams = new ArrayList<Team>();
		
		for (Team t: teams)
		{
			if (t.getProjectNumber() == projectNumber)
			{
				projectTeams.add(t);
				break;
			}	
		}
		
		return projectTeams;
	}
	
	/**
	 * Retrieves a list of Student objects provided a specific Team and Project
	 * Number combination.
	 * 
	 * @param teamNumber
	 * @param projectNumber
	 * @return List<Student>
	 */
	public List<Student> getStudentsByTeamAndProjectNumber(int teamNumber, int projectNumber) 
	{
		for (Team t: teams)
		{
			if (t.getTeamNumber() == teamNumber && t.getProjectNumber() == projectNumber)
			{
				return t.getStudents();
			}
		}		
		
		return null;
	}

	/**
	 * Gets the total number of Teams for all Projects in the course.
	 * 
	 * @return integer
	 */
	public int getNumTeams() {
		return this.teams.size();
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
		this.teams = new HashSet<Team>();
		
		try {
			FileInputStream inp = new FileInputStream(this.file);
			Workbook wb = WorkbookFactory.create(inp);
			
			Table dataTable = new Table(wb.getSheet(GradesDB.DATA_SHEET_NAME), "Projects", 0, 2);
			
			Iterator<Map.Entry<Integer, Row>> iterator = dataTable.getRows().entrySet().iterator();
	    	
	    	while (iterator.hasNext()) {
	    	
	            Map.Entry<Integer, Row> pairs = iterator.next();
	            Row row = pairs.getValue();
	            
	            String projectName = row.getCell(dataTable.getColumnNumber("Projects")).getStringCellValue();
	            int projectNumber = Integer.parseInt(projectName.replace("P", ""));

	            // Get the Teams for the Project:
	            String projectTeamsSheetName = projectName + " Teams";
	            Table projectTeamsTable = new Table(wb.getSheet(projectTeamsSheetName), "Team Name");
	            
	            Iterator<Map.Entry<Integer, Row>> teamsIterator = projectTeamsTable.getRows().entrySet().iterator();
		    	
		    	while (teamsIterator.hasNext()) {

		            Map.Entry<Integer, Row> teamPairs = teamsIterator.next();
		            Row teamRow = teamPairs.getValue();
		            
		            int teamNumber = 0;
		            int teamGrade = 0;
		            List<Student> studentList = new ArrayList<Student>();
		            for (Cell cell: teamRow)
		            {
		            	if (cell.getColumnIndex() == 0)
		            	{
		            		String teamName = teamRow.getCell(projectTeamsTable.getColumnNumber("Team Name")).getStringCellValue();
		            		teamNumber = Integer.parseInt(teamName.replace("Team", "").replaceAll("\\s+", ""));
		            		
		            		// Get the Team Grade:
		            		String projectGradesSheetName = projectName + " Grades";
		            		Table projectGradesTable = new Table(wb.getSheet(projectGradesSheetName), "Criteria");
		    	            
		            		Row projectGradesRow = projectGradesTable.findUniqueRow("TOTAL:");
		            		teamGrade = (int) projectGradesRow.getCell(projectGradesTable.getColumnNumber(teamName)).getNumericCellValue();
		            	}
		            	else
		            	{
		            		String studentName = cell.getStringCellValue();
		            		if (!studentName.isEmpty())
		            		{
		            			Student studentObject = this.db.getAllStudents().getStudentByName(studentName);
			            		studentList.add(studentObject);
		            		}
		            	}
		            }
		    		
		            // Get Ratings:
		            List<Rating> ratings = this.db.getAllRatings().getRatingsByTeamAndProjectNumber(teamNumber, projectNumber);
		            	    		
		            // Create and Add the Team to the list:
		            Team team = new Team(teamNumber, projectNumber, teamGrade, ratings, studentList);
		            this.teams.add(team);
		    	}
	        }
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns the internal list of Team objects.
	 * 
	 * @return HashSet<Team>
	 */
	@Override
	public HashSet<Team> getTeams() {
		return this.teams;
	}

}
