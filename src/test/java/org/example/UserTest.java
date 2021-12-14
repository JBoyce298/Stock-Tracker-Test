package org.example;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void testUserPortfolio() {
        User user = new User();
        Assert.assertNotNull(user);
        boolean isEmpty = user.isPortfolioEmpty();
        Assert.assertEquals(false, isEmpty);
        user.loadPortfolio();
        Assert.assertEquals(3, user.getPortfolio().size());
    }

    @Test
    public void testSavePortfolio() {
        User user = new User();
        Portfolio p = user.getPortfolio();
        p.add(new Stock("GME",50,100));
        p.add(new Stock("AMC",10,100));
        p.add(new Stock("NOK",5,100));
        user.savePortfolio();

    }
}
