package Entity.impl;

import Entity.interfaces.ICustomer;

import javax.persistence.*;

/**
 * This class represents a customer and a table in a database
 */
@Entity
public class Customer implements ICustomer {


    private static int ID = 1;
    @Id//Represents the primary key field
    private int customerID;
    private String name;

    
    public static void resetID (){
        ID=1;
    }

    public Customer() {}

    public Customer(String name) {
        this.name = name;
        this.customerID = ID++;
    }

    /**
     * @return an integer representing the customer ID
     */
    @Override
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return a string representing the customer name
     */
    @Override
    public String getName() {
        return name;
    }
}
