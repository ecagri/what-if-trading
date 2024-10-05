package com.ecagri.trading.util;

public class DataMaskingUtil {

    public static String maskAccountOwnerId(Long accountOwnerId){
        if(accountOwnerId == null){
            return null;
        }
        if(accountOwnerId.toString().length() < 7){
            return accountOwnerId.toString();
        }
        return "*******" + accountOwnerId.toString().substring(7);
    }
}
