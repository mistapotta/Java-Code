package edu.gatech;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
 * Ratings
 * 
 * This class holds a flat list of all ratings
 * for all projects and teams in the course.
 *  
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */

public class Ratings implements RatingsInterface {

	private HashSet<Rating> ratings;
	private File file;
	private GradesDB db;
	
	/**
	 * Creates a new Ratings instance
	 * 
	 * @param db
	 */
	public Ratings(GradesDB db) {
		this.db = db;
		initialize(db.getDatabase());
	}

	/**
	 * Returns the list of Rating objects associated with a
	 * particular Team and Project Number combination.
	 * 
	 * @param teamNumber
	 * @param projectNumber
	 * @return List<Rating>
	 */
	public List<Rating> getRatingsByTeamAndProjectNumber(int teamNumber, int projectNumber) {
		List<Rating> ratingsList = new ArrayList<Rating>();

		for (Rating r: ratings)
		{
			if (r.getTeamNumber() == teamNumber && r.getProjectNumber() == projectNumber)
			{
				ratingsList.add(r);
			}
		}		
		
		return ratingsList;
	}
	
	/**
	 * Returns the list of Rating objects associated with a 
	 * particular Student's Name.
	 * 
	 * @param name
	 * @return List<Rating>
	 */
	public List<Rating> getRatingsByStudentName(String name) {
		List<Rating> studentRatings = new ArrayList<Rating>();
		
		for (Rating r: ratings)
		{
			if (r.getStudent().getName().compareTo(name) == 0)
			{
				studentRatings.add(r);
			}	
		}
		
		return studentRatings;
	}

	/**
	 * Returns the overall number of rating records.
	 * 
	 * @return integer
	 */
	public int getNumRatings() {
		return this.ratings.size();
	}
	
	/**
	 * Refreshes the data contained within the class.
	 * 
	 */
	public void refresh() {
		this.initialize(this.file);
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
		this.ratings = new HashSet<Rating>();
		
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
	            String projectContributionsSheetName = projectName + " Contri";
	            Table projectContributionsTable = new Table(wb.getSheet(projectContributionsSheetName), "Students");
	            
	            HashMap<String, List<Row>> groupedContributions = projectContributionsTable.rowsGroupedByColumnValue("Team Name");
	            Iterator<Map.Entry<String, List<Row>>> contributionsIterator = groupedContributions.entrySet().iterator();
		    	
		    	while (contributionsIterator.hasNext()) {

		            Map.Entry<String, List<Row>> contributionGroups = contributionsIterator.next();
		            String teamName = contributionGroups.getKey();
		            int teamNumber = Integer.parseInt(teamName.replace("Team", "").replaceAll("\\s+", ""));
		            List<Row> teamContributions = contributionGroups.getValue();
		            
		            for (Row contributionRow: teamContributions)
		            {
		            	String studentName = contributionRow.getCell(projectContributionsTable.getColumnNumber("Students")).getStringCellValue();
		            	
		            	if (!studentName.isEmpty())
	            		{
	            			Student studentObject = this.db.getAllStudents().getStudentByName(studentName);
	            			Double averageContributionRating = contributionRow.getCell(projectContributionsTable.getColumnNumber("AVERAGE")).getNumericCellValue();

	            			// Create and Add the Rating to the list:
	    		            Rating rating = new Rating(teamNumber, projectNumber, averageContributionRating, studentObject);
	    		            this.ratings.add(rating);
	            		}		            	
		            }
		    	}
	        }
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns in internal list of Rating objects.
	 * 
	 * @return HashSet<Rating>
	 */
	@Override
	public HashSet<Rating> getRatings() {
		return this.ratings;
	}
}
