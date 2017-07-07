package edu.gatech.coffeecart;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;

/**
 * The purchase object can keep track of a collection of items that comprise a coffee cart purchase.
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 *
 */
public class Purchase 
{
	//the customer vip number who made the purchase
	private long custNum;
	
	//a list of items in the purchase
	private LinkedList<Item> items;
	
	//a cost of the purchase
	private BigDecimal cost;
	
	//the date of the purchase
	private Date date;

	/**
	 * Creates an empty purchase, which may be added to later.
	 */
	public Purchase()
	{
		custNum = 10001;
		items = new LinkedList<Item>();
		cost = new BigDecimal(0.0);
		date = new Date();
	}
	
	/**
	 * Creates a purchase with one object, and adjusts variables accordingly.
	 * 
	 * @param firstItem The first item purchased.
	 */
	public Purchase(long vip, Item firstItem)
	{
		custNum = vip;
		items = new LinkedList<Item>();
		items.add(firstItem);
		cost = new BigDecimal(firstItem.getCost().toString());
		date = new Date();
	}
	
	/**
	 * Creates a purchase from a list of objects.
	 * 
	 * @param newItems The items in the purchase
	 */
	public Purchase(long vip, List<Item> newItems)
	{
		custNum = vip;
		cost = new BigDecimal(0);
		for (Object o: newItems)
			if (o instanceof Item)
			{
				Item i = (Item)(o);
				items.add(i);
				cost.add(i.getCost());
			}
	}
	
	/**
	 * Retrieve the customer's VIP number
	 * @return the customer's VIP number
	 */
	public long getVIP()
	{
		return custNum;
	}
	
	/**
	 * Add an item to a purchase.
	 * 
	 * @param item
	 */
	public void addItem(Item item)
	{
		items.add(item);
		cost = cost.add(item.getCost());
	}
	
	/**
	 * Removes all occurrences of an item
	 * 
	 * @param item The item to be removed
	 * @return Whether at least one such item was successfully removed from the purchase
	 */
	public boolean removeItem(Item item)
	{
		boolean removed = false;
		for (Item i: items)
		{
			if (i.equals(item))
			{
				items.remove(i);
				cost.subtract(i.getCost());
				removed = true;
			}
		}
		return removed;
	}
	
	/**
	 * Get the list of items (used for db interaction)
	 * @return List of Item in the purchase
	 */
	protected List<Item> getItems()
	{
		return items;
	}
	
	/**
	 * Sets date of purchase to given date (used for retrival from db
	 * 
	 * @param newDate The original date of the purchase.
	 */
	public void setDate(Date newDate)
	{
		date = newDate;
	}
	
	/**
	 * Get the date of the purchase
	 * 
	 * @return Date of the Purchase
	 */
	public Date getDate()
	{
		return date;
	}
	
	/**
	 * Get the cost of the purchase
	 * @return A BigDecimal Representation of the cost.
	 * 
	 */
	
	public BigDecimal getCost()
	{
		return cost;
	}
	
	/**
	 * Gets the points the current purchase object is worth
	 * 
	 * @return The number of points the object is worth
	 */
	public int getPoints()
	{
		return (new BigDecimal(cost.toString())).add(new BigDecimal(0.5)).toBigInteger().intValue();
	}
	
	/**
	 * Determine whether a purchase has occured in the last 30 days.
	 * @return If purchase object is less than 30 days old.
	 */
	public boolean withinPast30Days()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar oldCal = Calendar.getInstance();
		Calendar newCal = Calendar.getInstance();
		oldCal.setTime(date);
		newCal.setTime(new Date());
		

		int days = 0;
		while (true) 
		{
			days++;
		    oldCal.add(Calendar.DAY_OF_MONTH, 1);
		    if (dateFormat.format(oldCal.getTime()).equals(dateFormat.format(newCal.getTime())))
		    	break;
		}
		return days <= 30;
	}
	

	/** 
	 * convert a given String to a purchase for database storage purposes.
	 * 
	 * @param str The purchase stored as a String
	 * @return The Purchase object represented by the String
	 */
	public static Purchase stringToPurchase(String str) 
	{
		String[] strsplit = str.split("#");
		Date purchaseDate = null;
		try{
		purchaseDate = (DateFormat.getInstance()).parse(strsplit[0]);
		} catch (Exception e){/* bad dates */}
		long customerVIPnumber = Long.parseLong(strsplit[1]);
		//BigDecimal purchaseCost = new BigDecimal(strsplit[2]);
		List<Item> theseItems = new LinkedList<Item>();
		for (int i = 3; i < strsplit.length; i += 4)
		{
			String classtype = strsplit[i];
			String name = strsplit[i+1];
			BigDecimal cost = new BigDecimal(strsplit[i+2]);
			boolean bestseller = strsplit[i+3].equals(true);
			
			Item item = null;
			if (classtype.equals("Coffee"))
				item = new Coffee(name, cost);
			else if (classtype.equals("Dessert"))
				item = new Dessert(name, cost, bestseller);
			
			theseItems.add(item);
		}
		Purchase newPurchase = new Purchase(customerVIPnumber, theseItems);
		newPurchase.setDate(purchaseDate);
		return newPurchase;	
	}
}
