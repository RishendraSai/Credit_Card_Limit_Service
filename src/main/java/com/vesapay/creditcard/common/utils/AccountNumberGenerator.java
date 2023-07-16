package com.vesapay.creditcard.common.utils;

import java.util.Random;

public class AccountNumberGenerator {

    public static Integer generator(){
        Random rnd = new Random();
        Integer number = rnd.nextInt(999999);
        return number;
    }
}
