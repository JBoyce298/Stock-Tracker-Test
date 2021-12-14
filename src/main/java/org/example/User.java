package org.example;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Scanner;

/**
 * Represents the user of the app
 * The user will be able to
 * 1. View current price of a Stonk
 * 2. View portfolio and get current value
 * 3. Add to portfolio
 * 4. Remove from portfolio
 * 5. Save portfolio
 */
public class User {
    // design error! multiple users should not share same text file!!!
    private static String PORTFOLIO_FILE = "portfolio.txt";
    private Portfolio portfolio = new Portfolio();

    /**
     * When a User is instantiated, the portfolio is loaded from a text file
     */
    public User() {
        if(!isPortfolioEmpty()) loadPortfolio();
    }

    /**
     * Returns true if the "portfolio.txt" file is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean isPortfolioEmpty() {
        String str = "";
        try {
            str = FileUtils.readFileToString(new File(PORTFOLIO_FILE), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("can't read portfolio file");
        }
        return str.equals("") ? true : false;
    }

    /**
     * Loads stocks from the "portfolio.txt" file
     * The file "portfolio.txt" simply displays the ticker the number of shares in
     * this format
     * <p>
     * TICKER:NUM_SHARES,TICKER:NUM_SHARES,TICKER:NUM_SHARES
     * <p>
     * TODO: probably update to use JSON or XML
     */
    public void loadPortfolio() {
        String line = "";
        try {
            line = FileUtils.readFileToString(new File(PORTFOLIO_FILE), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("unable to read portfolio read");
        }
        String[] array = line.split(",");
        for (int i = 0; i < array.length; i++) {
            String ticker = array[i].split(":")[0];
            int shares = Integer.parseInt(array[i].split(":")[1]);
            double price = StockUtility.getPriceOfStock(ticker);
            Stock stock = new Stock(ticker, price, shares);
            portfolio.add(stock);
        }
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * THIS METHOD WILL BE TESTED MANUALLY
     * Given a ticker, print out the price of the Stock
     * @param ticker Ticker of the Stock
     */
    public void printStockPrice(String ticker) {
        double price = StockUtility.getPriceOfStock(ticker);
        System.out.println(price);
    }

    /**
     * Given a ticker, return the price of the Stock
     * @param ticker Ticker of the Stock
     * @return the price of the stock
     */
    public double getPriceOfStock(String ticker){
        double price = StockUtility.getPriceOfStock(ticker);
        return price;
    }

    /**
     * print out instructions for the user
     */
    public void showMenu() {
        System.out.println("1. Get price of individual Stock");
        System.out.println("2. Get price of portfolio");
        System.out.println("3. Add Stock to portfolio");
        System.out.println("4. Remove Stock from portfolio");
    }

    /**
     * prints out instructions for the user
     * @return the options for the user to choose from
     */
    public String getMenu()
    {
        String menu = "1. Get price of individual Stock\n2. Get price of portfolio\n3. Add Stock to portfolio\n4. Remove Stock from portfolio\nQuit";

        return menu;
    }

    /**
     * Save the portfolio of the user to the text file "portfolio.txt"
     * in the following format:  GME:100,AMC:100,NOK:100
     */
    public void savePortfolio() {
        try {
            this.getPortfolio().saveToDisk(User.PORTFOLIO_FILE);
        } catch(IOException e) {
            throw new RuntimeException("sorry can't save to file");
        }
    }

    /**
     *Adds a stock to the portfolio file
     */
   /* public void addStockToPortfolio(String ticker, String shares) throws IOException {
        FileWriter w = new FileWriter("portfolio.txt");
        PrintWriter writer = new PrintWriter(w);
        FileReader r = new FileReader("portfolio.txt");
        Scanner reader = new Scanner(r);
        String current = reader.toString();
        writer.print(current + "," + ticker + ":" + shares);
        reader.close();
        r.close();
        writer.close();
        w.close();
    }*/
}
