package com.giroux.kevin.androidhttprequestlibrairy.constants;

/**
 * Created by kevin on 17/06/2016. KevinGirouxPortfolio
 */

public class MethodDatabase {
    public static final int INSERT = 0;
    public static final int DELETE = 1;
    public static final int BULK_INSERT =2;
    public static final int UPDATE = 3;
    public static final int QUERY = 4;

    public static String convertValue(int evaluation){

        if(evaluation == 0){
            return "INSERT";
        }else if(evaluation == 1){
            return "DELETE";
        }else if(evaluation == 2){
            return "BULK_INSERT";
        }else if(evaluation == 3){
            return "UPDATE";
        }else if(evaluation == 4){
            return "QUERY";
        }else {
            return "UNKNOW OPERATION";
        }
    }


}
