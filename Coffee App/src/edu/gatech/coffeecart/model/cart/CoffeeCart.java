package edu.gatech.coffeecart.model.cart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.gatech.coffeecart.model.item.Coffee;
import edu.gatech.coffeecart.model.item.Dessert;
import edu.gatech.coffeecart.model.item.Item;
import edu.gatech.coffeecart.model.data.CCDatabase;
import edu.gatech.coffeecart.model.util.DateUtil;
import edu.gatech.coffeecart.model.util.Money;

public class CoffeeCart {
	CCDatabase database = new CCDatabase();
	List<Coffee> coffeeList = new ArrayList<Coffee>();
	List<Dessert> dessertList = new ArrayList<Dessert>();
	
	PurchaseOrder activePurchaseOrder;
	
	public CoffeeCart() {	
		coffeeList.add(new Coffee("Caramel Macchiato", new Money(4)));
		coffeeList.add(new Coffee("Cinnamon Dolce Latte", new Money(3)));
		coffeeList.add(new Coffee("Cafe Latte", new Money(2)));
		coffeeList.add(new Coffee("Cafe Americano", new Money(1)));
		coffeeList.add(new Coffee("Regular", new Money(1)));
		
		dessertList.add(new Dessert("Carrot Cake Muffin", new Money(5)));
		dessertList.add(new Dessert("Blueberry Scone", new Money(2)));
		dessertList.add(new Dessert("Bannana Nut Bread", new Money(4)));
		dessertList.add(new Dessert("Classic Coffee Cake", new Money(3)));
		dessertList.add(new Dessert("Donut", new Money(1)));
		
		addVIPCustomer("Non VIP","10000");
		addVIPCustomer("Lionel Messi","10001");
		addVIPCustomer("Cristiano Ronaldo","10002");
		addVIPCustomer("Xavi Hernandez","10003");
		addVIPCustomer("Luis Suarez","10004");
		
		

	}
	public void addVIPCustomer(String name, String cardNumber) {
		VIPCustomer customer = new VIPCustomer();
		customer.setName(name);
		customer.setCardNumber(cardNumber);
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		try {
			customer.setBirthDate((new SimpleDateFormat("MM-dd-yy")).parse("12-12-12"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.setActive();
		database.addVIPCustomer(customer);
	}
	public void addVIPCustomer(VIPCustomer vip) {
		
		database.addVIPCustomer(vip);
	}
	public void upadteVIPCustomer(VIPCustomer vip) {
		
		database.updateVIPCustomer(vip);
	}
	

	public void startPurchaseOrder(String cardNumber)  {
		this.activePurchaseOrder = new PurchaseOrder(database.getVIPCustomer(cardNumber));
	}

	public PurchaseOrder getActivePurchaseOrder() {
		return activePurchaseOrder;
	}

	public void completePurchaseOrder() {
		for(PurchaseItem item : activePurchaseOrder.getPurchaseItems()) {
			if(item instanceof PreOrderPurchaseItem) {
				if(database.preOrderSlotAvailable(((PreOrderPurchaseItem) item).getPreOrderDate(), item.getItem()))
					database.addPreOrderPurchaseItem((PreOrderPurchaseItem)item);
			} else {
				database.addPurchaseItem(item);
			}
			if(activePurchaseOrder.getCustomer() != null && activePurchaseOrder.getCustomer() instanceof VIPCustomer) {
				VIPCustomer customer = (VIPCustomer) activePurchaseOrder.getCustomer();
				customer.getPurchaseItems().add(item);				
			}
		}
		activePurchaseOrder = null;
	}
	

	public void cancelPurchaseOrder() {
		activePurchaseOrder = null;
	}
	public VIPCustomer getActiveCustomer() {
		return getActivePurchaseOrder().getCustomer();
	}
	public String[] getDailyReportOfPurchasedItems() {
		List<PurchaseItem> pList = CoffeeCartManager.getCoffeeCart().database.getPurchaseItems(DateUtil.getDate(0));
		int size=CoffeeCartManager.getCoffeeCart().database.getPurchaseItems(DateUtil.getDate(0)).size();
		String[] purchaseItems = new String[size];
		int i=0;
		for(PurchaseItem pItem : pList){
			   purchaseItems[i]=pItem.getItem().getName()+":"+ pItem.getCost().toString()+":"+pItem.getPurchaseDate().toString(); 
			   i++;
			}
		return purchaseItems;
	}
	public String[] getDailyReportOfPreOrderedItems() {
		List<PreOrderPurchaseItem> pList = CoffeeCartManager.getCoffeeCart().database.getPreOrderedItems(DateUtil.getDate(0));
		int size=CoffeeCartManager.getCoffeeCart().database.getPreOrderedItems(DateUtil.getDate(0)).size();
		String[] preorderItems = new String[size];
		int i=0;
		for(PreOrderPurchaseItem pItem : pList){
			   preorderItems[i]=pItem.getItem().getName()+":"+ pItem.getCost().toString()+":"+pItem.getPurchaseDate().toString(); 
			   i++;
			}
		return preorderItems;
	}
	public void getCompleteReport() {
		System.out.println(database);
	}

	public List<Coffee> getCoffeeList() {
		return coffeeList;
	}

	public List<Dessert> getDessertList() {
		return dessertList;
	}
	
	public String[] getVips() {


		int size=database.getVipList().size();
		String[] vipCus = new String[size];
		int i=0;
		for(VIPCustomer vip : database.getVipList()){
			if(vip.isActive())
				vipCus[i]=vip.getName();
			else
				vipCus[i]=vip.getName()+"[INACTIVE]";
			i++;
		}
		return vipCus;

	}
	
	public VIPCustomer getVip(int position){
		return database.getVIPCustomer(position);
	}
	public VIPCustomer getVip(String number){
		return database.getVIPCustomer(number);
	}
	
	public boolean orderActive(){
		if(activePurchaseOrder==null)
				return false;
		else
			return true;
	}
	public String nextCardNumber(){
		return database.nextVipNumber();
	}
	
	public void createPurchases(){
		for(int i=1;i<3;i++)
		{
		System.out.println(i);
		CoffeeCartManager.getCoffeeCart().startPurchaseOrder(Integer.toString(10000+i));
		PurchaseOrder tempOrder=CoffeeCartManager.getCoffeeCart().getActivePurchaseOrder();
		tempOrder.addPreOrderItem(CoffeeCartManager.getCoffeeCart().getDessertList().get(i+2),DateUtil.getDate(7+i));
		tempOrder.addItem(CoffeeCartManager.getCoffeeCart().getDessertList().get(i));
		tempOrder.addItem(CoffeeCartManager.getCoffeeCart().getCoffeeList().get(i));	
		CoffeeCartManager.getCoffeeCart().completePurchaseOrder();
		}
	
	}
	public boolean empty(){
		return database.empty();
	}
	public boolean preOrderSlotAvailable(Date dateTime, Item item){
		return database.preOrderSlotAvailable(dateTime, item);
	}
	public boolean vipExists(String vipNum){
		
		return database.vipExists(vipNum);
	}
	public boolean vipIsActive(String vipNum){
		
		return getVip(vipNum).isActive();
	}
}
