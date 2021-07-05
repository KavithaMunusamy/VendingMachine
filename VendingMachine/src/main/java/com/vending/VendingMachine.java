package com.vending;

import java.util.List;

import com.vending.exception.VendingMachineException;
import com.vending.utility.Bucket;
import com.vending.utility.Coin;
import com.vending.utility.Product;

/**
 * Interface class to declare the functionalities of Vending Machine
 * 
 * @author Kavitha Munusamy
 *
 */
public interface VendingMachine {

	public void acceptCoins(Coin coin);

	public long selectProduct(Product item) throws VendingMachineException;
	
	public Bucket<Product, List<Coin>> dispenseProductAndChange() throws VendingMachineException;

	public List<Coin> refundAmount() throws VendingMachineException;	

	public void resetMachine();

}
