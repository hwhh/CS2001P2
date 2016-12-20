package Entity.impl;

import Entity.common.StockUnavailableException;
import Entity.interfaces.IProduct;
import Entity.interfaces.IStockRecord;

import javax.persistence.*;


/**
 * This class represents a record held by the shop for a particular product and a table in the databse
 */
@Entity
public class StockRecord implements IStockRecord {

    private static int ID = 1;
    @Id //Represents the primary key field
    private int stockRecordID;
    private int stockCount, noOfSales;

    @OneToOne //unidirectional One-to-One relationship between Product and StockRecord
    @JoinColumn(name ="PRODUCT_ID")
    private Product product;

    public static void resetID (){
        ID=1;
    }

    public StockRecord() {}

    public StockRecord(IProduct product) {
        this.product = (Product)product;
        this.stockRecordID = ID++;
    }

    /**
     * @return the product the stock record belongs too
     */
    @Override
    public IProduct getProduct() {
        return product;
    }

    /**
     * @return an integer representing the quantity in stock of a product
     */
    @Override
    public int getStockCount() {
        return stockCount;
    }

    /**
     * @return an integer representing the number of sales of a product
     */
    @Override
    public int getNumberOfSales() {
        return noOfSales;
    }

    /**
     * @param amount the number to update the stock of a product by
     */
    @Override
    public void addStock(int amount) {
       stockCount +=amount;
    }

    /**
     * @param amount the number to reduce the stock and increase the number of sales of a product by
     * @throws StockUnavailableException
     */
    @Override
    public void buyProduct(int amount) throws StockUnavailableException {
        if ((stockCount -amount) < 0) {
            throw new StockUnavailableException();
        } else{
            stockCount -= amount;
            noOfSales += amount;
        }
    }

}
