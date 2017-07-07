package edu.gatech.coffeecart.model.cart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VIPCustomer extends Customer {
	String name;
	String phoneNumber;
	Date birthDate;
	String cardNumber;
	int pointsTotal;
	int points30Days;
	boolean goldLevel;
	boolean active;
	List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
/*	
	public VIPCustomer(){
		this.active=true;
	}
*/	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getPointsTotal() {
		return pointsTotal;
	}
	public void setPointsTotal(int pointsTotal) {
		this.pointsTotal = pointsTotal;
	}
	public int getPoints30Days() {
		return points30Days;
	}
	public void setPoints30Days(int points30Days) {
		this.points30Days = points30Days;
	}
	public boolean isGoldLevel() {
		return goldLevel;
	}
	public void setGoldLevel(boolean goldLevel) {
		this.goldLevel = goldLevel;
	}
	public List<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}
	public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}
	@Override
	public String toString() {
		return "VIPCustomer [name=" + name + ", phoneNumber=" + phoneNumber
				+ ", birthDate=" + birthDate + ", cardNumber=" + cardNumber
				+ ", pointsTotal=" + pointsTotal + ", points30Days="
				+ points30Days + ", goldLevel=" + goldLevel
				+ ", purchaseItems=" + purchaseItems + "]";
	}
	public boolean isActive(){
		return this.active;
	}
	public void deactivate(){
		this.active=false;
	}
	public void setActive(){
		this.active=true;
	}
}
