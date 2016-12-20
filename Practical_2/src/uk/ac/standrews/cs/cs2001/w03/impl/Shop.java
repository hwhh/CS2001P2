package uk.ac.standrews.cs.cs2001.w03.impl;


import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import uk.ac.standrews.cs.cs2001.w03.common.BarCodeAlreadyInUseException;
import uk.ac.standrews.cs.cs2001.w03.common.ProductNotRegisteredException;
import uk.ac.standrews.cs.cs2001.w03.common.StockUnavailableException;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a simple shop which can stok and sell products.
 *
 */
public class Shop extends AbstractFactoryClient implements IShop {

    /**
     * List containing all the products.
     */
    public List<IProduct> products  = new ArrayList<>();
    /**
     * List containing all the stock records.
     */
    public List<IStockRecord> stockRecords = new ArrayList<>();


    /**
     * Checks if product valid, then adds new product and stock record to correct lists.
     * @param product the product to register
     * @throws BarCodeAlreadyInUseException
     *
     */
    @Override
    public void registerProduct(IProduct product) throws BarCodeAlreadyInUseException {
        if (!product.getBarCode().equals("") && product.getBarCode() != null) {
            for (IProduct iProduct : products) {
                if (iProduct.getBarCode().equals(product.getBarCode())) {
                    throw new BarCodeAlreadyInUseException();
                }
            }
            products.add(product);
            stockRecords.add(getFactory().makeStockRecord(product));
        } else {
            throw new BarCodeAlreadyInUseException();
        }
    }

    /**
     * Checks if product valid, then removes the product and stock record from correct lists.
     * @param product the product to remove
     * @throws ProductNotRegisteredException
     */
    @Override
    public void unregisterProduct(IProduct product) throws ProductNotRegisteredException {
        if (!products.contains(product)) {
            throw new ProductNotRegisteredException();
        } else {
            products.remove(product);
            for (Iterator<IStockRecord> iter = stockRecords.listIterator(); iter.hasNext();) {
                if (iter.next().getProduct().getBarCode().equals(product.getBarCode())) {
                    iter.remove();
                }
            }
        }
    }

    /**
     * Increment stock count for correct stock record.
     * @param barCode the bar code of the product
     * @throws ProductNotRegisteredException
     */
    @Override
    public void addStock(String barCode) throws ProductNotRegisteredException {
        //find product from Stock Record and add more
        for (IStockRecord stockRecord : stockRecords) {
            if (stockRecord.getProduct().getBarCode().equals(barCode)) {
                stockRecord.addStock();
                return;
            }
        }
        throw new ProductNotRegisteredException();
    }


    /**
     * Increment number of sales and decrements stock count  for correct stock record.
     * @param barCode the bar code of the product to be bought
     * @throws StockUnavailableException
     * @throws ProductNotRegisteredException
     */
    @Override
    public void buyProduct(String barCode) throws StockUnavailableException, ProductNotRegisteredException {
        // minus x amount from products quantity
        for (IStockRecord stockRecord : stockRecords) {
            if (stockRecord.getProduct().getBarCode().equals(barCode) && stockRecord.getStockCount() > 0) {
                stockRecord.buyProduct();
                return;
            } else if (stockRecord.getProduct().getBarCode().equals(barCode) && stockRecord.getStockCount() <= 0) {
                throw new StockUnavailableException();
            }
        }
        throw new ProductNotRegisteredException();
    }

    /**
     * Return the size of the products list.
     * @return size of the products list
     */
    @Override
    public int getNumberOfProducts() {
        //return size of list containing all products
        return products.size();
    }

    /**
     * Total the stock count for each product and return it.
     * @return total stock count
     */
    @Override
    public int getTotalStockCount() {
        //return the sum off all the products quantity
        int quantity = 0;
        for (IStockRecord stockRecord : stockRecords) {
            quantity += stockRecord.getStockCount();
        }
        return quantity;
    }

    /**
     * Return the stock count for a given product.
     * @param barCode the bar code of the product
     * @return int - quantity of stock for given product
     * @throws ProductNotRegisteredException
     */
    @Override
    public int getStockCount(String barCode) throws ProductNotRegisteredException {
        //Get quantity of given product
        for (IStockRecord stockRecord : stockRecords) {
            if (stockRecord.getProduct().getBarCode().equals(barCode)) {
                return stockRecord.getStockCount();
            }
        }
        throw new ProductNotRegisteredException();
    }

    /**
     * Return the number of sales for a given product.
     * @param barCode the bar code of the product
     * @return int - quantity of sales for given product
     * @throws ProductNotRegisteredException
     */
    @Override
    public int getNumberOfSales(String barCode) throws ProductNotRegisteredException {
        //Get sum of sales for given product
        for (IStockRecord stockRecord : stockRecords) {
            if (stockRecord.getProduct().getBarCode().equals(barCode)) {
                return stockRecord.getNumberOfSales();
            }
        }
        throw new ProductNotRegisteredException();
    }

    /**
     * Return the product with the largest number of sales.
     * @return IProduct - the product with the most sales
     * @throws ProductNotRegisteredException
     */
    @Override
    public IProduct getMostPopular() throws ProductNotRegisteredException  {
        //Find product with largest number of sales
        if (stockRecords.size() > 0) {
            IStockRecord mostPopularStockRecord = stockRecords.get(0);
            for (IStockRecord stockRecord : stockRecords) {
                if (stockRecord.getNumberOfSales() > mostPopularStockRecord.getNumberOfSales()) {
                    mostPopularStockRecord = stockRecord;
                }
            }
            return mostPopularStockRecord.getProduct();
        } else {
            throw new ProductNotRegisteredException();
        }
    }

}
