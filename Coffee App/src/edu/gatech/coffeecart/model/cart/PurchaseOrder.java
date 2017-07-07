package edu.gatech.coffeecart.model.cart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gatech.coffeecart.model.item.Item;
import edu.gatech.coffeecart.model.util.DateUtil;
import edu.gatech.coffeecart.model.util.Money;

public class PurchaseOrder {
	VIPCustomer customer;
	Date purchaseDate;
	List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();

	public PurchaseOrder(VIPCustomer customer) {
		this.customer = customer;
	}
	
	public void addItem(Item item) {
		purchaseItems.add(new PurchaseItem(item, item.getUnitCost(), DateUtil.getDate(0))); // TODO: add item etc
	}
	
	public void addRefill(Item item) {
		Money cost = new Money(item.getUnitCost().getValue());
		if(customer != null) {
			if(((VIPCustomer)customer).isGoldLevel()) {
				cost.setValue(0);
			}
			else {
				cost.setValue(cost.getValue()/2);
			}
		} 
		purchaseItems.add(new PurchaseItem(item, cost, DateUtil.getDate(0))); // TODO: add item etc
	}
	
	public void addPreOrderItem(Item item, Date preOrderDate) {
		purchaseItems.add(new PreOrderPurchaseItem(item, item.getUnitCost(), DateUtil.getDate(0), preOrderDate)); // TODO: add item etc
	}
	
	
	public VIPCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(VIPCustomer customer) {
		this.customer = customer;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public List<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}	

	@Override
	public String toString() {
		return "PurchaseOrder [customer=" + customer + ", purchaseDate="
				+ purchaseDate + ", purchaseItems=" + purchaseItems + "]";
	}

}
