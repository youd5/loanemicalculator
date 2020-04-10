package com.release.loanemicalculator.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonHelper {
    /**
     * Adds INR symbol and adds comma separation as per IN convention + decimal for better viewing
     * @param amount
     * @return
     */
    public static String formatAmount(String amount){
        amount = new DecimalFormat("##,##,##0").format(Integer.valueOf(amount));
        return Constants.rupeeSymbol + amount + ".00";
    }

    public static Date getDateFromString(String strDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.datePattern);
        return simpleDateFormat.parse(strDate);
    }

    public static String getStringFromDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.datePattern);
        return formatter.format(date);
    }

    public static Double formatDouble(Double dbl){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        return Double.valueOf(df.format(dbl));
    }
}
