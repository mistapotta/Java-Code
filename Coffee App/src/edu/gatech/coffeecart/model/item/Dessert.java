package edu.gatech.coffeecart.model.item;

import edu.gatech.coffeecart.model.util.Money;


public class Dessert extends Item {

	boolean bestSeller;

	public Dessert(String name, Money unitCost) {
		super(name, unitCost);
	}
	

	public Dessert(String name, Money unitCost, boolean bestSeller) {
		super(name, unitCost);
		this.bestSeller = bestSeller;
	}


	@Override
	public String toString() {
		return "Dessert [bestSeller=" + bestSeller + ", name=" + name
				+ ", unitCost=" + unitCost + "]";
	}

}
