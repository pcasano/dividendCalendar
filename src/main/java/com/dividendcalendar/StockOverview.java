package com.dividendcalendar;

import com.dividendcalendar.reportContents.OpenDividendAccrual;
import com.dividendcalendar.reportContents.StatementOfFundsLine;
import io.restassured.response.Response;
import lombok.Getter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import static io.restassured.path.json.JsonPath.from;

@Getter
public class StockOverview {

    private String ticker;
    private String companyName;
    private Calendar dividendPaymentDate;

    public StockOverview(StatementOfFundsLine dividendEntry) {
        this.ticker = dividendEntry.getActivityDescription().split("\\(")[0];
        this.companyName = dividendEntry.getDescription();
        if(Optional.ofNullable(this.companyName).isPresent()) {
            this.dividendPaymentDate = Calendar.getInstance();
            this.dividendPaymentDate.setTime(dividendEntry.getDate());
        } else {
            this.dividendPaymentDate = null;
        }
    }

    public StockOverview(OpenDividendAccrual openDividendAccrual) {
        this.ticker = openDividendAccrual.getSymbol();
        this.companyName = openDividendAccrual.getDescription();
        if(Optional.ofNullable(this.companyName).isPresent()) {
            this.dividendPaymentDate = Calendar.getInstance();
            this.dividendPaymentDate.setTime(openDividendAccrual.getPayDate());
        } else {
            this.dividendPaymentDate = null;
        }
    }

    public StockOverview(String ticker) {
        this.ticker = ticker;
        this.companyName = ticker;
    }
}
