package com.example.njunji.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TransactionEntity  {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    public String serviceCode;
    public String msisdn;
    public String invoiceNumber;
    public String accountNumber;
    public String payerTransactionID;
    public Integer amount;
    public String hubID;
    public String narration;
    public String datePaymentReceived;
    public String extraData;
    public String currencyCode;
    public String customerNames;
    public String paymentMode;
    public Integer statusCode;
    public String statusDescription;
    public String beepTransactionID;
    public String dueAmount;
    public String dueDate;
    public String customerName;
    public String expiryDate;
    /*public QueryBill queryBill;*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
