package com.epam.ta.reportportal.commons;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SendCaseTest {


    @Test
    public void testNotContains() {
        String value = "not contains in enum";
        Assert.assertFalse(SendCase.isPresent(value));
    }

    @Test
    public void testContains() {
        Random random = new Random();
        int randomIndex = random.nextInt(SendCase.values().length);
        String randomSendCaseName = SendCase.values()[randomIndex].name();
        Assert.assertTrue(SendCase.isPresent(randomSendCaseName));
    }
}
