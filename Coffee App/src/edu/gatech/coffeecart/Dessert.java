package edu.gatech.coffeecart;

import java.math.BigDecimal;

public class Dessert implements Item{

	private String name;
	private BigDecimal cost;
	private boolean bestSeller;
	
	public Dessert()
	{
		name = "EMPTY";
		cost = new BigDecimal("0.0");
		bestSeller = false;
	}
	
	public Dessert(String myName, BigDecimal myCost, boolean sellsWell)
	{
		name = myName;
		cost = myCost;
		bestSeller = sellsWell;
	}
	
	public String getName()
	{
		return name;
	}
	
	public BigDecimal getCost()
	{
		return cost;
	}
	
	public boolean isBestSeller()
	{
		return bestSeller;
	}
	
	public boolean equals(Object o)
	{
		if (!(o instanceof Dessert))
			return false;
		Item i = (Item)(o);
		return (name.equals(i.getName()) && cost.equals(i.getCost()) && bestSeller == i.isBestSeller());
	}
}
