package edu.gatech.coffeecart.model.item;

import edu.gatech.coffeecart.model.util.Money;

public class Item {
	String name;
	Money unitCost;
	public Item(String name, Money unitCost) {
		this.name = name;
		this.unitCost = unitCost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Money getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Money unitCost) {
		this.unitCost = unitCost;
	}

}

