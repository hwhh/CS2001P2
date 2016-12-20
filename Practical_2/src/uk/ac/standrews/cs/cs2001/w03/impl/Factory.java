package uk.ac.standrews.cs.cs2001.w03.impl;

import uk.ac.standrews.cs.cs2001.w03.interfaces.IFactory;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IShop;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IStockRecord;


/**
 * This class implements a singleton factory.
 *
 */
public final class Factory implements IFactory {

    private static IFactory factoryInstance = null;

    /**
     * Method which returns an instance of the singleton Factory class.
     * @return the instance of the Factory
     */
    public static IFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new Factory();
        }
        return factoryInstance;
    }

    /**
     * Returns a product object.
     * @param barCode the bar code of the product
     * @param description the description of the product
     * @return IProduct
     */

    @Override
    public IProduct makeProduct(String barCode, String description) {
        return new Product(barCode, description);
    }

    /**
     * Returns a stock record object.
     * @param product the product to use for this stock record
     * @return IStockRecord
     */
    @Override
    public IStockRecord makeStockRecord(IProduct product) {
        return new StockRecord(product);
    }

    /**
     * Returns a shop object.
     * @return IShop
     */
    @Override
    public IShop makeShop() {
        return new Shop();
    }

}
