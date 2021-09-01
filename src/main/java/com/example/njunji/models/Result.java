
package com.example.njunji.models;

import lombok.Data;

import javax.annotation.Generated;
@Data
@Generated("jsonschema2pojo")
public class Result {

    public Integer statusCode;
    public String statusDescription;
    public String payerTransactionID;
    public String beepTransactionID;
    public String receiptNumber;
    public String receiptNarration;
    public String payerClientCode;
    public String MSISDN;
    public String totalRecordsPendingQuery;
    public String totalRecordsPendingAck;
    public String paymentExtraData;
    public String receiverNarration;
    public String dueAmount;
    public String dueDate;
    public String customerName;
}
