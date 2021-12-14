package org.example;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the portfolio of Stocks
 */
public class Portfolio {

    private List<Stock> stocks = new ArrayList<>();

    /**
     * Need default constructor for serialization
     */
    public Portfolio() {
    }


    /**
     * Adds Stock to the portfolio
     *
     * @param stock the stock to add
     */
    public void add(Stock stock) {
        int shares = stock.getShares();
        String ticker = stock.getTicker();
        System.out.println(shares);
        boolean addition = true;
        for(int i = 0; i < stocks.size() || !addition; i++)
        {
            if(ticker.equals(stocks.get(i).getTicker())) {
                System.out.println(stocks.get(i).getShares());
                int newshare = stocks.get(i).getShares() + shares;
                System.out.println(newshare);
                Stock newStock = stock;
                newStock.setShares(newshare);
                stocks.remove(stock);
                stocks.add(newStock);
                addition = false;
            }
        }
        if(addition)
        {
            stocks.add(stock);
        }
    }


    /**
     * Returns the size of the portfolio
     *
     * @return size of portfolio
     */
    public int size() {
        return stocks.size();
    }

    /**
     * Removes given stock from portfolio
     *
     * @param stock stock to be removed
     */
    public void remove(Stock stock) {
        int shares = stock.getShares();
        String ticker = stock.getTicker();
        for(int i = 0; i < stocks.size(); i++)
        {
            if(ticker.equals(stocks.get(i).getTicker())) {
                if(shares >= stocks.get(i).getShares()){
                    stocks.remove(stock);
                }
                else{
                    int shareDif = stocks.get(i).getShares() - shares;
                    Stock newStock = stock;
                    newStock.setShares(shareDif);
                    stocks.remove(stock);
                    stocks.add(newStock);
                }
            }
        }

    }

    /**
     * Updates the portfolio with the latest price of each stock
     */
    public void update() {
        for (int i = 0; i < stocks.size(); i++) {
            Stock current = stocks.get(i);
            current.setPrice(StockUtility.getPriceOfStock(current.getTicker()));
        }
    }

    /**
     * Returns the total dollar value of the portfolio ( shares * price )
     *
     * @return dollar amount of portfolio value
     */
    public double getTotalValue() {
        double total = 0;
        for (int i = 0; i < stocks.size(); i++) {
            total += stocks.get(i).getPrice() * stocks.get(i).getShares();
        }
        return total;
    }

    /**
     * Write the portfolio as a string to file
     *
     * @param filename file to write the portfolio
     */
    public void saveToDisk(String filename) throws IOException {
        FileUtils.writeStringToFile(new File(filename), this.toString(), "UTF-8");
    }

    @Override
    public String toString() {
        String line = "";
        for (int i = 0; i < stocks.size(); i++) {
            if (i == stocks.size() - 1) {
                String entry = stocks.get(i).getTicker() + ":" + stocks.get(i).getShares();
                line += entry;
            } else {
                String entry = stocks.get(i).getTicker() + ":" + stocks.get(i).getShares() + ",";
                line += entry;
            }
        }
        return line;
    }
}
