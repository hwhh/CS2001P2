package Entity.impl;

import Entity.interfaces.ICustomerOrder;
import Entity.interfaces.IOrderLine;
import Entity.interfaces.IProduct;
import javax.persistence.*;

/**
 * This class is used to link the order table and the products table
 */
@Entity
public class OrderLine implements IOrderLine {

    private static int ID = 1;

    @Id//Represents the primary key field
    private int ordersID;

    @ManyToOne//unidirectional Many-to-One relationship between OrderLine and Product
    @JoinColumn(name="PRODUCT_ID")
    private Product product;

    @ManyToOne//unidirectional Many-to-one relationship between OrderLine and Order
    @JoinColumn(name="ORDER_ID")
    private CustomerOrder customerOrder;

    public static void resetID (){
        ID=1;
    }

    public OrderLine(IProduct product, ICustomerOrder order) {
        this.ordersID = ID++;
        this.product = (Product)product;
        this.customerOrder = (CustomerOrder)order;
    }

    public OrderLine() {
    }

    /**
     * @return an integer representing the order ID
     */
    @Override
    public int getOrderID() {
        return ordersID;
    }

    /**
     * @return a product in the order
     */
    @Override
    public IProduct getProduct() {
        return product;
    }

    /**
     * @return the customer whom made the order
     */
    @Override
    public ICustomerOrder getCustomerOrder() {
        return customerOrder;
    }

}
