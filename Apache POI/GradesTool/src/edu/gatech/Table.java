package edu.gatech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Table {
	
    HashMap<String, Integer> columnMap = new HashMap<String, Integer>();
    HashMap<String, Integer> rowMap = new HashMap<String, Integer>();
    HashMap<Integer, Row> rows = new HashMap<Integer, Row>();
    
    int startColumn = 0;
    int numberOfColumns = 0;

    public Table(Sheet sheet, String uniqueColumnName) {
    	
    	// Populate the Column Map from the Header Row:
    	this.setColumnMap(sheet.getRow(0));
    	this.setRows(sheet);
    	this.setRowMap(uniqueColumnName);
	}
    
    public Table(Sheet sheet, String uniqueColumnName, int startColumn, int numberOfColumns) {
    	this.startColumn = startColumn;
    	this.numberOfColumns = numberOfColumns;
    	
    	// Populate the Column Map from the Header Row:
    	this.setColumnMap(sheet.getRow(0));
    	this.setRows(sheet);
    	this.setRowMap(uniqueColumnName);
	}
    
    private void setColumnMap(Row headerRow)
    {   	
    	for (Cell cell: headerRow)
    	{
    		if (numberOfColumns > 0)
    		{
    			if (cell.getColumnIndex() >= startColumn && cell.getColumnIndex() < (startColumn + numberOfColumns))
        		{
    				columnMap.put(cell.getStringCellValue(), cell.getColumnIndex());
        		}
    		}
    		else
    		{
    			columnMap.put(cell.getStringCellValue(), cell.getColumnIndex());
    		}
    	}
    }
    
    private void setRows(Sheet sheet)
    {
    	for (Row row: sheet)
    	{	
    		// Skip the header row:
    		if (row.getRowNum() == 0)
    		{
    			continue;
    		}
    		
    		if (containsValue(row, row.getFirstCellNum(), row.getLastCellNum()))
    		{
    			rows.put(row.getRowNum(), row);
    		}
    	}
    }
    
    private boolean containsValue(Row row, int firstCell, int lastCell) 
    {
        boolean flag = false;
        
        for (int i = firstCell; i < lastCell; i++) 
        {
        	String cellValue = String.valueOf(row.getCell(i));
	        if (cellValue.replaceAll("\\s+", "").replace("null", "").isEmpty() != true) 
	        {
	        	flag = true;
            }
        }
        
        return flag;
    }
    
    private void setRowMap(String uniqueColumnName)
    {
    	int columnNumber = columnMap.get(uniqueColumnName);
    	Iterator<Map.Entry<Integer, Row>> iterator = rows.entrySet().iterator();
    	
    	while (iterator.hasNext()) {
    	
            Map.Entry<Integer, Row> pairs = iterator.next();
            Integer rowNumber = pairs.getKey();
            String uniqueColumnValue = pairs.getValue().getCell(columnNumber).getStringCellValue();
            
            if (!uniqueColumnValue.isEmpty())
            {
            	this.rowMap.put(uniqueColumnValue, rowNumber);
            }
            
        }
    }
	
	public HashMap<Integer, Row> getRows()
	{
		return this.rows;
	}
	
	public int getColumnNumber(String value)
	{
		return this.columnMap.get(value);
	}
	
	public int getRowNumber(String value)
	{
		return this.rowMap.get(value);
	}
	
	public Set<String> getColumnNames() {
		return this.columnMap.keySet();
	}
	
	public Row findUniqueRow(String uniqueValue)
	{
		Integer uniqueRow = rowMap.get(uniqueValue);
		return rows.get(uniqueRow);
	}
	
	public HashMap<String, List<Row>> rowsGroupedByColumnValue(String columnName)
	{
		HashMap<String, List<Row>> groupedRows = new HashMap<String, List<Row>>();;
		
		int columnNumber = columnMap.get(columnName);
    	Iterator<Map.Entry<Integer, Row>> iterator = rows.entrySet().iterator();
    	
    	String currentGroup = "";
    	List<Row> currentGroupRows = new ArrayList<Row>();
    	while (iterator.hasNext()) {
    		
            Map.Entry<Integer, Row> pairs = iterator.next();
            Integer rowNumber = pairs.getKey();
            Row row = pairs.getValue();
            
            String columnValue = pairs.getValue().getCell(columnNumber).getStringCellValue();
            
            if (currentGroup.isEmpty())
            {
            	currentGroup = columnValue;
            }
            
            if (columnValue.isEmpty())
            {
            	// Extra check to help exclude empty rows at the end of the sheet:
            	if (this.rowMap.containsValue(rowNumber))
            	{
            		currentGroupRows.add(row);
            	}
            }
            
            boolean isLastRow = !iterator.hasNext();
            
            if ((!columnValue.isEmpty() && !currentGroup.equals(columnValue)) || isLastRow)
            {
            	groupedRows.put(currentGroup, currentGroupRows);
            	
            	currentGroup = columnValue;
            	
            	currentGroupRows = new ArrayList<Row>();
            }
        }
    	
    	return groupedRows;
	}
}
