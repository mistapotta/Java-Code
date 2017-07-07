package edu.gatech.coffeecart.model.cart;

import java.util.Date;

import edu.gatech.coffeecart.model.item.Item;
import edu.gatech.coffeecart.model.util.DateUtil;
import edu.gatech.coffeecart.model.util.Money;

public class PreOrderPurchaseItem extends PurchaseItem {

	Date preOrderDate;
	public PreOrderPurchaseItem(Item item, Money cost, Date purchaseDate, Date preOrderDate) {
		super(item, cost, purchaseDate);
		this.preOrderDate = preOrderDate;
	}
	

	public Date getPreOrderDate() {
		return DateUtil.getDateWithoutTime(preOrderDate);
	}

	public void setPreOrderDate(Date preOrderDate) {
		this.preOrderDate = preOrderDate;
	}
	

	@Override
	public String toString() {
		return "PreOrderPurchaseItem [preOrderDate=" + preOrderDate + ", item="
				+ item + ", cost=" + cost + ", purchaseDate=" + purchaseDate
				+ "]";
	}

	
}
