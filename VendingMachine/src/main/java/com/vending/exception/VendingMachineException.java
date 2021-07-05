package com.vending.exception;
/**
 * Exception to class to catch custom errors in the vending machine
 * @author Kavitha Munusamy
 *
 */
public class VendingMachineException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

    public VendingMachineException(String string) {
        this.message = string;
    }

    public String getErrorMessage() {
        return message;
    }

}