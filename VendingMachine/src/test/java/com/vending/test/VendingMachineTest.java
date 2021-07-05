package com.vending.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vending.VendingMachine;
import com.vending.VendingMachineImpl;
import com.vending.exception.VendingMachineException;
import com.vending.utility.Bucket;
import com.vending.utility.Coin;
import com.vending.utility.Product;

/**
 * Test class to validate the vending machine functionalities
 * 
 * @author Kavitha Munusamy
 *
 */
public class VendingMachineTest {

	private static VendingMachine vendingMachine;

	@BeforeClass
	public static void setUp() {
		vendingMachine = new VendingMachineImpl();
	}

	/**
	 * to test buying a product with exact price
	 * 
	 * @throws VendingMachineException
	 */
	@Test
	public void buyProductWithExactPrice() throws VendingMachineException {
		long price = vendingMachine.selectProduct(Product.COKE);
		assertEquals(Product.COKE.getPrice(), price);
		vendingMachine.acceptCoins(Coin.QUARTER);

		Bucket<Product, List<Coin>> bucket = vendingMachine.dispenseProductAndChange();
		Product product = bucket.getFirst();
		List<Coin> change = bucket.getSecond();
		//validate the product
		assertEquals(Product.COKE, product);
		// validate the change
		assertTrue(change.isEmpty());
	}

	/**
	 * to test buying a product with excess price
	 * 
	 * @throws VendingMachineException
	 */
	@Test
	public void buyProductWithExcessPrice() throws VendingMachineException {
		long price = vendingMachine.selectProduct(Product.SODA);
		assertEquals(Product.SODA.getPrice(), price);

		vendingMachine.acceptCoins(Coin.QUARTER);
		vendingMachine.acceptCoins(Coin.QUARTER);

		Bucket<Product, List<Coin>> bucket = vendingMachine.dispenseProductAndChange();
		Product item = bucket.getFirst();
		List<Coin> change = bucket.getSecond();

		// Validate the Product
		assertEquals(Product.SODA, item);
		// Validate the change
		assertTrue(!change.isEmpty());
		// Validates the remaining balance;
		assertEquals(50 - Product.SODA.getPrice(), getTotal(change));
		

	}

	/**
	 * To test the refund of amount after cancellation
	 * 
	 * @throws VendingMachineException
	 */
	@Test
	public void refund() throws VendingMachineException {
		long price = vendingMachine.selectProduct(Product.PEPSI);
		// validates the product price
		assertEquals(Product.PEPSI.getPrice(), price);
		// gets coins from buyer
		vendingMachine.acceptCoins(Coin.DIME);
		vendingMachine.acceptCoins(Coin.NICKLE);
		vendingMachine.acceptCoins(Coin.PENNY);
		vendingMachine.acceptCoins(Coin.QUARTER);
		//Validate the amount that is refunded
		assertEquals(41, getTotal(vendingMachine.refundAmount()));
	}

	/**
	 * To test the product not available 
	 * @throws VendingMachineException
	 */
	@Test(expected = VendingMachineException.class)
	public void productUnavailable() throws VendingMachineException {
		for (int i = 0; i < 5; i++) {
			vendingMachine.selectProduct(Product.COKE);
			vendingMachine.acceptCoins(Coin.QUARTER);
			vendingMachine.dispenseProductAndChange();
		}

	}

	/**
	 * To test the insufficient balance in the account
	 * 
	 * @throws VendingMachineException
	 */
	@Test(expected = VendingMachineException.class)
	public void inSufficientChangeException() throws VendingMachineException {
		for (int i = 0; i < 5; i++) {
			vendingMachine.selectProduct(Product.SODA);
			vendingMachine.acceptCoins(Coin.QUARTER);
			vendingMachine.acceptCoins(Coin.QUARTER);
			vendingMachine.dispenseProductAndChange();

			vendingMachine.selectProduct(Product.PEPSI);
			vendingMachine.acceptCoins(Coin.QUARTER);
			vendingMachine.acceptCoins(Coin.QUARTER);
			vendingMachine.dispenseProductAndChange();
		}
	}

	/**
	 * To test the reset of vending machine
	 * @throws VendingMachineException
	 */
	@Test(expected = VendingMachineException.class)
	public void resetMachine() throws VendingMachineException {
		VendingMachine vmachine = new VendingMachineImpl();
		vmachine.resetMachine();
		vmachine.selectProduct(Product.COKE);

	}


	private long getTotal(List<Coin> change) {
		long total = 0;
		for (Coin coin : change) {
			total = total + coin.getDenomination();
		}
		return total;
	}

	@AfterClass
	public static void tearDown() {
		vendingMachine = null;
	}
}
