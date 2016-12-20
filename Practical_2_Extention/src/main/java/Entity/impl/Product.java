package Entity.impl;

import Entity.interfaces.IProduct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents products that can be stocked and sold in a shop and a table in the database
 */
@Entity
public class Product implements IProduct {

    private static int ID = 1;
    @Id //Represents the primary key field
    private int productID;
    private String barcode, description;

    public static void resetID (){
        ID=1;
    }

    public Product() {
    }

    public Product(String barcode, String description) {
        this.barcode = barcode;
        this.description = description;
        this.productID = ID++;
    }

    /**
     * @return an integer representing the product ID
     */
    @Override
    public int getProductID() {
        return productID;
    }

    /**
     * @return a string representing the products barcode
     */
    @Override
    public String getBarCode() {
       return barcode;
    }

    /**
     * @return an string representing the products description
     */
    @Override
    public String getDescription() {
       return description;
    }

}
