package edu.gatech.coffeecart;

import java.math.BigDecimal;

/**
 * The Item interface is used to create Coffee and Dessert items
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public interface Item 
{
	public String getName();
	public BigDecimal getCost();
	public boolean isBestSeller();
	public boolean equals(Object o);
}
