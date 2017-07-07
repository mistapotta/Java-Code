package edu.gatech.coffeecart.model.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gatech.coffeecart.model.item.Item;
import edu.gatech.coffeecart.model.cart.Customer;
import edu.gatech.coffeecart.model.cart.PreOrderPurchaseItem;
import edu.gatech.coffeecart.model.cart.PurchaseItem;
import edu.gatech.coffeecart.model.cart.VIPCustomer;
import edu.gatech.coffeecart.model.util.DateUtil;

public class CCDatabase {
	
	int MAX_PREORDERS=3;
	
	List<VIPCustomer> vipCustomers = new ArrayList<VIPCustomer>();
	List<PreOrderPurchaseItem> preOrders = new ArrayList<PreOrderPurchaseItem>();
	List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();

	
	public Customer getCustomer() {
		return new Customer();
	}
	
	public VIPCustomer getVIPCustomer(String cardNumber) {
		int pos=Integer.parseInt(cardNumber);
		pos=pos-10000;
		VIPCustomer customer =  vipCustomers.get(pos); //TODO: re-implement by lookup
		Date day30 = DateUtil.getDate(-30);
		int pointsTotal = 0;
		int points30days = 0;
		
		for(PurchaseItem item: customer.getPurchaseItems()) {
			if(item.getPurchaseDate().compareTo(day30) > 0) {
				points30days+=item.getCost().getValue();
			}
			pointsTotal+= item.getCost().getValue();
		}
		customer.setPointsTotal(pointsTotal);
		customer.setPoints30Days(points30days);
		if(pointsTotal > 5000) {
			customer.setGoldLevel(true);
		}
		return customer;
	}
	public VIPCustomer getVIPCustomer(int position){
	
		return vipCustomers.get(position);
	}
	
	public void addVIPCustomer(VIPCustomer customer) {
		vipCustomers.add(customer);
	}
	
	public void updateVIPCustomer(VIPCustomer customer) {
		VIPCustomer updateCustomer =  this.vipCustomers.get(Integer.parseInt(customer.getCardNumber())-10000);
		updateCustomer.setName(customer.getName());
		updateCustomer.setPhoneNumber(customer.getPhoneNumber());
		updateCustomer.setBirthDate(customer.getBirthDate());
		
		
		
	}
	
	public List<PreOrderPurchaseItem> getPreOrderedItems(Date date) {
		return preOrders;
	}
	
	public List<PreOrderPurchaseItem> getPreOrderItems(Date dateTime, Item item) {
		Date date = DateUtil.getDateWithoutTime(dateTime);
		List<PreOrderPurchaseItem> items = new ArrayList<PreOrderPurchaseItem>();
		for(PreOrderPurchaseItem preOrder: preOrders) {
			if(preOrder.getPreOrderDate().equals(date)) {
				if( item == null ||
						(item != null && preOrder.getItem().getName().equals(item.getName()))) {
					items.add(preOrder);
				}
			}
		}
		return items;
	}
	
	public boolean preOrderSlotAvailable(Date dateTime, Item item){
		if(getPreOrderItems(dateTime, item).size()< MAX_PREORDERS)
			return true;
		else
			return false;
	}
	
	public void addPreOrderPurchaseItem(PreOrderPurchaseItem item) {
		preOrders.add(item);
		//purchaseItems.add(item);
	}
	
	
	public List<PurchaseItem> getPurchaseItems(Date date) {
		return purchaseItems;
	}
	public List<VIPCustomer> getVipList() {
		
		return vipCustomers; 
	}
	
	public void addPurchaseItem(PurchaseItem item) {
		purchaseItems.add(item);
	}

	@Override
	public String toString() {
		return "CCDatabase [vipCustomers=" + vipCustomers + ", \npreOrders="
				+ preOrders + ", \npurchaseItems=" + purchaseItems + "]";
	}
	public String nextVipNumber(){
		return Integer.toString(10000+vipCustomers.size());
	}
	public boolean empty(){
		if(purchaseItems.size()==0)
			return true;
		else
			return false;
	}
	public boolean vipExists(String vipNum){
		
		for(VIPCustomer vip: vipCustomers) {
			System.out.println(vipNum+":"+vip.getCardNumber());
			if(vip.getCardNumber().equals(vipNum)) 
				return true;
		}
		return false;
	
	}
}
