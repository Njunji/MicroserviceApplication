
package com.example.njunji.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.annotation.Generated;

@Data
@Generated("jsonschema2pojo")
public class Packet {

    public String serviceCode;
    @JsonProperty("MSISDN")
    public String msisdn;
    public String invoiceNumber;
    public String accountNumber;
    public String beepTransactionID;
    public String payerTransactionID;
    public Integer amount;
    public String hubID;
    public String narration;
    public String countryCode;
    public String function;
    public String paymentMode;
    public String customerNames;
    public String currencyCode;
    public String extraData;
    public String statusDescription;
    public String datePaymentReceived;
    public Integer statusCode;
    public String receiptNarration;
    public String dueAmount;
    public String dueDate;
    public String customerName;
    public String expiryDate;
    //public QueryBill queryBill;
}
