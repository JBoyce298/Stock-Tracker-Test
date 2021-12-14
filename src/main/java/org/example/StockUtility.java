package org.example;


import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 *
 * Utility class for Stonk information
 *
 * 2/15/2021 rainy day monday
 */
public class StockUtility {


    /**
     * Grab the current (latest) price of a stock given the ticker
     * @param ticker the stock ticker
     */
    public static double getPriceOfStock(String ticker) {
        double price = 0;
        try {
            Stock stock = YahooFinance.get(ticker);
            price = stock.getQuote().getPrice().doubleValue();
        } catch(IOException e) {
            throw new RuntimeException("Stock data could not be retrived!");
        } catch(NullPointerException e) {
            return 0; //indicate that Stock does not exist
        }
        return price;
    }
}
