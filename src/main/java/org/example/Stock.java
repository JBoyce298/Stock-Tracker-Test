package org.example;

import java.util.Objects;

public class Stock {

    private String ticker;
    private double price;
    private int shares;

    /**
     * Keeping default constructor because we want to serialize it in json
     */
    public Stock() {
    }

    /**
     * Construct Stock with ticker, price and number of shares
     * @param ticker symbol of the stock
     * @param price value of the stock
     * @param shares number of purchased shares
     */
    public Stock(String ticker, double price, int shares) {
        this.ticker = ticker;
        this.price = price;
        this.shares = shares;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    /**
     * Returns the current value (price * shares) of the stock
     * @return value of "investment"
     */
    public double getTotalValue() {
        return shares * price;
    }

    /**
     * Return string representation of stock which is "ticker:shares"
     * @return string representation of stock
     */
    @Override
    public String toString() {
        return ticker + ":" + shares;
    }

    /**
     * Equality is based on ticker
     * @param o any object
     * @return true if ticker is equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return ticker.equals(stock.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker);
    }
}
