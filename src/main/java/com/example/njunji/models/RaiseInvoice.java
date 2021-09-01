package com.example.njunji.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RaiseInvoice {
    //Uploads an invoice from a payment for services rendered.
    public String customerName;
    public Integer amount;
    public String datePaymentReceived;
    @JsonProperty("MSISDN")
    public String msisdn;
    public String countryCode;
    public String expiryDate;
}
