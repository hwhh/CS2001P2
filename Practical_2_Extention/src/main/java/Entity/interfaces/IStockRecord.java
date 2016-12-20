package Entity.interfaces;


import Entity.common.StockUnavailableException;

/**
 * This is the interface for a StockRecord.
 * 
 */
public interface IStockRecord {



    /**
     * Returns the product associated with this IStockRecord.
     * @return the product related to this stock record
     */
    IProduct getProduct();


    /**
     * Returns the stock count for this product.
     * @return the stock level for the product
     */
    int getStockCount();


    /**
     * Returns the number of times this product has been bought.
     * @return the number of sales for the product
     */
    int getNumberOfSales();


    /**
     * Adds stock for the product.
     */
    void addStock(int amount);


    /**
     * Processes the purchase of this product from the stock.
     * @throws StockUnavailableException when the product is not currently in stock
     */
    void buyProduct(int amount) throws StockUnavailableException;


}
