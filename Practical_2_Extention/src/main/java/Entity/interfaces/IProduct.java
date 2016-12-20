package Entity.interfaces;

/**
 * Interface for a shop product ADT.
 * 
 */
public interface IProduct {

    /**
     * @return the ID of the product
     */
    int getProductID();

    /**
     * This method returns the product's bar code.
     * @return the bar code for this product
     */
    String getBarCode();

    /**
     * This method returns the product description.
     * @return the description of the product
     */
    String getDescription();

}
