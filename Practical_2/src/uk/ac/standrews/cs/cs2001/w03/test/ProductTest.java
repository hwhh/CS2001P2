package uk.ac.standrews.cs.cs2001.w03.test;

import uk.ac.standrews.cs.cs2001.w03.common.AbstractFactoryClient;
import uk.ac.standrews.cs.cs2001.w03.interfaces.IProduct;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Class that tests the Product class.
 */
public class ProductTest extends AbstractFactoryClient {

    /**
     * IProduct global variables.
     */
    private IProduct iProduct1, iProduct2, iProduct3;

    /**
     * Setup varibales.
     * @throws Exception exception
     */
    @Before
    public void setUp() throws Exception {
        iProduct1 = getFactory().makeProduct("123", "bread");
        iProduct2 = getFactory().makeProduct("234", "toast");
        iProduct3 = getFactory().makeProduct("345", "cooked bread");
    }

    /**
     * This tests checks the barcode for the products are correct.
     * @throws Exception exception
     */
    @Test
    public void getBarCode() throws Exception {
        assertEquals("123", iProduct1.getBarCode());
        assertEquals("234", iProduct2.getBarCode());
        assertEquals("345", iProduct3.getBarCode());

    }
    /**
     * This tests checks the description for the products are correct.
     * @throws Exception exception
     */
    @Test
    public void getDescription() throws Exception {
        assertEquals("bread", iProduct1.getDescription());
        assertEquals("toast", iProduct2.getDescription());
        assertEquals("cooked bread", iProduct3.getDescription());

    }

}
