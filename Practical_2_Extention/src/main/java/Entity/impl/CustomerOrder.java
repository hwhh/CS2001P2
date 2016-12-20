package Entity.impl;

import Entity.interfaces.ICustomer;
import Entity.interfaces.ICustomerOrder;

import javax.persistence.*;

/**
 * This class represents a customer and a table in a database
 */
@Entity
public class CustomerOrder implements ICustomerOrder {

    private static int ID = 1;
    @Id//Represents the primary key field
    private int orderID;
    private String date;
    @ManyToOne(cascade = {CascadeType.ALL})//Allow an order entity to be added to the database before the customer
    @JoinColumn(name="CUSTOMER_ID")//unidirectional One-to-Many relationship between CustomerOrder and Customer
    private Customer customer;

    public CustomerOrder() {
    }

    public static void resetID (){
        ID=1;
    }

    public CustomerOrder(ICustomer customer, String date ) {
        this.orderID = ID++;
        this.date = date;
        this.customer = (Customer) customer;
    }

    /**
     * @return the customer that made the order
     */
    @Override
    public ICustomer getCustomer() {
        return customer;
    }

    /**
     * @return a string representing the date the order was made
     */
    @Override
    public String getDate() {
        return date;
    }



}
