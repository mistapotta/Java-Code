package edu.gatech.coffeecart.model.cart;

import java.util.Date;

import edu.gatech.coffeecart.model.item.Item;
import edu.gatech.coffeecart.model.util.Money;

public class PurchaseItem {
	Item item;
	Money cost;
	Date purchaseDate;
	
	public PurchaseItem(Item item, Money cost, Date purchaseDate) {
		this.item = item;
		this.cost = cost;
		this.purchaseDate = purchaseDate;
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Money getCost() {
		return cost;
	}
	public void setCost(Money cost) {
		this.cost = cost;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Override
	public String toString() {
		return "PurchaseItem [item=" + item + ", cost=" + cost
				+ ", purchaseDate=" + purchaseDate + "]";
	}
	
}
