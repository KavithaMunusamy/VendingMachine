package com.vending.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to maintain the product and price in the inventory.
 *
 * @author Kavitha Munusamy
 */
public class Inventory<T> {
    private Map<T, Integer> inventory = new HashMap<T, Integer>();

    /**
     * To get the quanitity of an item
     * @param item
     * @return
     */
    public int getQuantity(T item) {
        Integer value = inventory.get(item);
        return value == null ? 0 : value;
    }

   /**
    * To add the item to the inventory
    * @param item
    */
    public void add(T item) {
        int count = inventory.get(item);
        inventory.put(item, count + 1);
    }

  /**
   * To remove the item from the inventory
   * @param item
   */
    public void deduct(T item) {
        if (hasItem(item)) {
            int count = inventory.get(item);
            inventory.put(item, count - 1);
        }
    }

    /**
     * To check if the item available in the inventory
     * @param item
     * @return
     */
    public boolean hasItem(T item) {
        return getQuantity(item) > 0;
    }

    /**
     * to empty the inventory
     */
    public void clear() {
        inventory.clear();
    }

    /**
     * to add the items and its quantity.
     * @param item
     * @param quantity
     */
    public void put(T item, int quantity) {
        inventory.put(item, quantity);
    }
}



