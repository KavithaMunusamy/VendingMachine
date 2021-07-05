package com.vending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vending.exception.VendingMachineException;
import com.vending.utility.Bucket;
import com.vending.utility.Coin;
import com.vending.utility.Constants;
import com.vending.utility.Inventory;
import com.vending.utility.Product;

/**
 * Implementation class to define the functionalities of Vending Machine
 * 
 * @author Kavitha Munusamy
 *
 */
public class VendingMachineImpl implements VendingMachine {

	
	private long totalCost;
	private Product currentProduct;
	private long currentBalance;
	private Inventory<Coin> cashInventory = new Inventory<Coin>();
	private Inventory<Product> productInventory = new Inventory<Product>();

	/**
	 * Constructor to define the products and coins in the inventory
	 */
	public VendingMachineImpl() {

		for (Product product : Product.values()) {
			productInventory.put(product, 10);
		}
		for (Coin coin : Coin.values()) {
			cashInventory.put(coin, 10);
		}

	}

	/**
	 * To add to the inserted coins to cash inventory
	 */
	@Override
	public void acceptCoins(Coin coin) {
		currentBalance = currentBalance + coin.getDenomination();
		cashInventory.add(coin);
	}

	/**
	 * selects product and returns the price of the product
	 * 
	 * @throws VendingMachineException
	 */
	@Override
	public long selectProduct(Product product) throws VendingMachineException {

		long price = 0;
		// checks product is available and provides the price of the product
		if (productInventory.hasItem(product)) {
			currentProduct = product;
			price = currentProduct.getPrice();
		} else {
			throw new VendingMachineException(product + Constants.PRODUCT_NOT_AVALIABLE);
		}
		return price;
	}

	/**
	 * To dispense product and remaining change if any
	 * 
	 * @throws VendingMachineException
	 */
	@Override
	public Bucket<Product, List<Coin>> dispenseProductAndChange() throws VendingMachineException {

		// gets the product to dispense
		Product item = dispenseProduct();
		totalCost = totalCost + currentProduct.getPrice();
		// gets the remaining the change
		List<Coin> change = collectChange();
		return new Bucket<Product, List<Coin>>(item, change);
	}

	/**
	 * To refund the amount , if the order is cancelled
	 */
	@Override
	public List<Coin> refundAmount() throws VendingMachineException {
		List<Coin> refund = getChange(currentBalance);
		updateCashInventory(refund);
		currentBalance = 0;
		currentProduct = null;
		return refund;
	}

	/**
	 * To reset the vending machine properties like inventory.
	 */
	@Override
	public void resetMachine() {
		cashInventory.clear();
		productInventory.clear();
		totalCost = 0;
		currentProduct = null;
		currentBalance = 0;
	}

	/**
	 * To dispense the product
	 * 
	 * @return
	 * @throws VendingMachineException
	 */
	private Product dispenseProduct() throws VendingMachineException {
		if (isFullyPaid()) {
			if (hasSufficientChange()) {
				productInventory.deduct(currentProduct);
				return currentProduct;
			}
			throw new VendingMachineException(Constants.NO_SUFFICIENT_BALANCE_IN_INVENTORY);

		}
		long remainingBalance = currentProduct.getPrice() - currentBalance;
		throw new VendingMachineException(Constants.REMAINING_AMOUNT_TO_BE_PAID + remainingBalance);
	}

	/**
	 * To gets the remaining change
	 * 
	 * @return
	 */
	private List<Coin> collectChange() throws VendingMachineException {

		long changeAmount = currentBalance - currentProduct.getPrice();
		List<Coin> change = getChange(changeAmount);
		updateCashInventory(change);
		currentBalance = 0;
		currentProduct = null;
		return change;
	}

	/**
	 * To check enough balance available for buy the product
	 * 
	 * @return
	 */
	private boolean isFullyPaid() {
		if (currentBalance >= currentProduct.getPrice()) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the remaining balance
	 * 
	 * @param amount
	 * @return
	 * @throws NotSufficientChangeException
	 */
	private List<Coin> getChange(long amount) throws VendingMachineException {

		List<Coin> changes = Collections.EMPTY_LIST;

		if (amount > 0) {
			changes = new ArrayList<Coin>();
			long balance = amount;
			while (balance > 0) {
				if (balance >= Coin.QUARTER.getDenomination() && cashInventory.hasItem(Coin.QUARTER)) {
					changes.add(Coin.QUARTER);
					balance = balance - Coin.QUARTER.getDenomination();
					continue;

				} else if (balance >= Coin.DIME.getDenomination() && cashInventory.hasItem(Coin.DIME)) {
					changes.add(Coin.DIME);
					balance = balance - Coin.DIME.getDenomination();
					continue;

				} else if (balance >= Coin.NICKLE.getDenomination() && cashInventory.hasItem(Coin.NICKLE)) {
					changes.add(Coin.NICKLE);
					balance = balance - Coin.NICKLE.getDenomination();
					continue;

				} else if (balance >= Coin.PENNY.getDenomination() && cashInventory.hasItem(Coin.PENNY)) {
					changes.add(Coin.PENNY);
					balance = balance - Coin.PENNY.getDenomination();
					continue;

				} else {
					throw new VendingMachineException(Constants.NO_SUFFICIENT_BALANCE_PLEASE_TRY_ANOTHER_PRODUCT);
				}
			}
		}

		return changes;
	}

	/**
	 * To prepare the bill
	 */
	public void printStats() {
		System.out.println("Total Sales : " + totalCost);
		System.out.println("Current Item Inventory : " + productInventory);
		System.out.println("Current Cash Inventory : " + cashInventory);
	}

	/**
	 * To check for sufficient balance
	 * 
	 * @return
	 */
	private boolean hasSufficientChange() {
		long amount = currentBalance - currentProduct.getPrice();
		boolean hasChange = true;
		try {
			getChange(amount);
		} catch (VendingMachineException e) {
			return hasChange = false;
		}

		return hasChange;
	}

	/**
	 * To update the cash Inventory
	 * 
	 * @param change
	 */
	private void updateCashInventory(List<Coin> change) {
		for (Coin c : change) {
			cashInventory.deduct(c);
		}
	}

	/**
	 * Gets total cost of products bought
	 * 
	 * @return
	 */
	public long getTotalCost() {
		return totalCost;
	}

}
