package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
/**
 * This class represents products that can be stocked and sold in a shop.
 *
 */
public class Product implements IProduct {

    private String barcode, description;

    /**
     * Returns product.
     * @param barcode of product
     * @param description of product
     */
    public Product(String barcode, String description) {
        this.barcode = barcode;
        this.description = description;
    }

    /**
     * The products barcode.
     * @return a string representing the products barcode
     */
    @Override
    public String getBarCode() {
       return barcode;
    }


    /**
     * The products description.
     * @return an string representing the products description
     */
   @Override
    public String getDescription() {
       return description;
    }

}
