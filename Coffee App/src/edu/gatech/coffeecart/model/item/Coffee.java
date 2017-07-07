package edu.gatech.coffeecart.model.item;

import edu.gatech.coffeecart.model.util.Money;


public class Coffee extends Item {

	public Coffee(String name, Money unitCost) {
		super(name, unitCost);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Coffee [name=" + name + ", unitCost=" + unitCost + "]";
	}
	

}
