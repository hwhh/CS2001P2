package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;



/**
 * This class represents a record held by the shop for a particular product.
 *
 */
public class StockRecord implements IStockRecord {

    /**
     * The product the stock record is associated with.
     */
    private IProduct product;
    /**
     * The quantity in stock of a product.
     */
    private int stockCont;
    /**
     * The quantity of sales for a a product.
     */
    private int noOfSales;

    /**
     * Returns StockRecord object.
     * @param product the product the stock record is associated with
     */
    public StockRecord(IProduct product) {
        this.product = product;

    }

    /**
     * Returns product.
     * @return the product the stock record belongs too
     */
    @Override
    public IProduct getProduct() {
        return product;
    }


    /**
     * Quantity in stock of a product.
     * @return an integer representing the quantity in stock of a product
     */
    @Override
    public int getStockCount() {
        return stockCont;
    }


    /**
     * Number of sales of a product.
     * @return an integer representing the number of sales of a product
     */
    @Override
    public int getNumberOfSales() {
        return noOfSales;
    }

    /**
     * Add one to the stock count.
     */
    @Override
    public void addStock() {
       stockCont++;
    }

    /**
     *  Add one to the number of sales and deduct one from stock count.
     * @throws StockUnavailableException
     */
    @Override
    public void buyProduct() throws StockUnavailableException {
        if (stockCont <= 0) {
            throw new StockUnavailableException();
        }
        stockCont--;
        noOfSales++;
    }

}
