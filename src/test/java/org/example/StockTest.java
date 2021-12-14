package org.example;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StockTest {

    @Test
    public void testStock() {
        Stock gamespot = new Stock();
        Assert.assertNotNull(gamespot);
        Stock gme = new Stock("GME", 49.99, 20);
        Assert.assertEquals("GME", gme.getTicker());
        Assert.assertEquals(49.99, gme.getPrice(), 0);
        Assert.assertEquals(20, gme.getShares());
    }

    @Test
    public void testStockPrice() {
        String ticker = "GME";
        double price = StockUtility.getPriceOfStock(ticker);
        Assert.assertTrue(price > 0);
        System.out.println(price);
    }

    @Test
    public void testStockValue() {
        Stock gme = new Stock("GME", 800, 20);   // arrange
        double total = gme.getTotalValue();   //  act
        Assert.assertEquals(16000, total, 0.1);  // assert
    }

    @Test
    public void testStockWriting() throws IOException {
        Stock gme = new Stock("GME", 49.99, 20);
        String str = gme.toString();  // should be "GME:20"
        FileUtils.writeStringToFile(new File("gme.txt"), str, "UTF-8");
        String str2 = FileUtils.readFileToString(new File("gme.txt"),"UTF-8");
        Assert.assertEquals(str,str2);
    }


}
