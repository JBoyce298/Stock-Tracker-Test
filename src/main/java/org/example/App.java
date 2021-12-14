package org.example;

import java.util.Scanner;

/**
 * Handles the main method of asking the user to enter a certain command number 1-4.
 * They should enter 'quit' to stop the program.
 * Starts by waiting for an initial message from the user int order to grab the user's chat id and converse further
 */
public class App 
{
    public static void main(String[] args) throws Exception {
        // ask for user for a ticker and print out the price
        BotHandler bot = new BotHandler();
        User wsbPerson = new User();
        //Scanner keyboard = new Scanner(System.in);

        //wait for user to enter a message so chat id can be grabbed
        while (bot.getChatID() == null) {
            bot.lookForNewUser();
        }

        //used in tandem with the 'quit' command
        boolean wait = true;

        //loop keeps running and asking user for commands until they enter 'quit' or the program is stopped
        while(wait) {
            bot.sendMessage(wsbPerson.getMenu());
            //String answer = keyboard.nextLine();
            bot.waitToReceiveNext();
            if(bot.lookForSpecificMessage("1")) {
                //System.out.println("Please enter ticker: ");
                bot.sendMessage("Please enter ticker: ");
                //String ticker = keyboard.nextLine();
                String ticker = bot.waitToReceiveNext();
                bot.sendMessage("$" + wsbPerson.getPriceOfStock(ticker));
            }
            else if(bot.lookForSpecificMessage("2")) {
                // print out portfolio total value
                double totalValue = wsbPerson.getPortfolio().getTotalValue();
                //System.out.println(wsbPerson.getPortfolio());
                bot.sendMessage(wsbPerson.getPortfolio().toString());
                //System.out.println("The value of your portfolio is: " + totalValue);
                bot.sendMessage("The value of your portfolio is: $" + totalValue);
            }
            else if(bot.lookForSpecificMessage("3")) {
                bot.sendMessage("Please enter ticker: ");
                String ticker = bot.waitToReceiveNext();
                bot.sendMessage("Please enter shares: ");
                String shares = bot.waitToReceiveNext();
                //wsbPerson.addStockToPortfolio(ticker, shares);
                bot.sendMessage(shares + " shares of " + ticker + " were added to your portfolio.");
                Portfolio p = wsbPerson.getPortfolio();
                p.add(new Stock(ticker,wsbPerson.getPriceOfStock(ticker),Integer.parseInt(shares)));
                wsbPerson.savePortfolio();
            }
            else if(bot.lookForSpecificMessage("4")) {
                bot.sendMessage("Please enter ticker: ");
                String ticker = bot.waitToReceiveNext();
                bot.sendMessage("Please enter shares: ");
                String shares = bot.waitToReceiveNext();
                //wsbPerson.addStockToPortfolio(ticker, shares);
                bot.sendMessage(shares + " shares of " + ticker + " were removed from your portfolio.");
                Portfolio p = wsbPerson.getPortfolio();
                p.remove(new Stock(ticker,wsbPerson.getPriceOfStock(ticker), Integer.parseInt(shares)));
                wsbPerson.savePortfolio();
            }
            //Stops the program from within the bot
            else if(bot.lookForSpecificMessage("quit")){
                wait = false;
            }
            else {
                bot.sendMessage("Not a command!\nTry another");
            }
        }
    }
}
