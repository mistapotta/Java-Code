package edu.gatech.coffeecart;

import java.math.BigDecimal;

public class Coffee implements Item
{
	private String name;
	private BigDecimal cost;
	
	public Coffee()
	{
		name = "EMPTY";
		cost = new BigDecimal("0.0");
	}
	
	public Coffee(String myName, BigDecimal myCost)
	{
		name = myName;
		cost = myCost;
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
		return false; //coffee never sells well.
	}
	
	public boolean equals(Object o)
	{
		if (!(o instanceof Coffee))
			return false;
		Item i = (Item)(o);
		return (name.equals(i.getName()) && cost.equals(i.getCost()));
	}
	
	
}
