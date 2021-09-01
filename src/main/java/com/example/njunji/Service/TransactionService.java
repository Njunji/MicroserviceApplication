package com.example.njunji.Service;

import com.example.njunji.models.Credentials;
import com.example.njunji.models.RaiseInvoice;
import com.example.njunji.Entity.TransactionEntity;
import com.example.njunji.Repository.TransactionRepository;
import com.example.njunji.models.*;
import com.example.njunji.models.Callback;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransactionService implements TransactionInterface {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    WebClient webclient;

    @Override
    public Mono<ResponseData> save(MainRequest data) {

        //CREATING A UNIQUE PAYER TRANSACTION ID.
        data.payload.getPacket().get(0).setPayerTransactionID(UUID.randomUUID().toString());

        data.payload.getPacket().forEach(packet -> {
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setPayerTransactionID(packet.getPayerTransactionID());
            transactionEntity.setAmount(packet.getAmount());
            transactionEntity.setDatePaymentReceived(packet.getDatePaymentReceived());
            transactionEntity.setCustomerNames(packet.getCustomerNames());
            transactionEntity.setInvoiceNumber(packet.getInvoiceNumber());
            transactionEntity.setAccountNumber(packet.getAccountNumber());
            transactionEntity.setNarration(packet.getNarration());
            transactionEntity.setCurrencyCode(packet.getCurrencyCode());
            transactionEntity.setMsisdn(packet.getMsisdn());
            transactionEntity.setHubID(packet.getHubID());
            transactionEntity.setExtraData(packet.getExtraData());
            transactionEntity.setPaymentMode(packet.getPaymentMode());
            TransactionEntity save = transactionRepository.save(transactionEntity);
        });
        return postBody(data).map(responseData -> {

            log.info("this is response {}", responseData.toString());
            TransactionEntity byPayerTransactionID = transactionRepository.findByPayerTransactionID(responseData.results.get(0).getPayerTransactionID());
            log.info("record fetched in {}", byPayerTransactionID.toString());
            byPayerTransactionID.setStatusCode(responseData.results.get(0).statusCode);
            byPayerTransactionID.setBeepTransactionID(responseData.results.get(0).beepTransactionID);
            byPayerTransactionID.setStatusDescription(responseData.results.get(0).statusDescription);
            transactionRepository.save(byPayerTransactionID);
            return responseData;
        });
    }

    @Override
    public Model processCallback(Callback callback) {
        log.info("CallBack received {}", callback.toString());
        TransactionEntity byPayerTransactionID = transactionRepository.findByPayerTransactionID(callback.payload.getPacket().payerTransactionID);
        byPayerTransactionID.setStatusDescription(callback.payload.getPacket().getStatusDescription());
        byPayerTransactionID.setStatusCode(callback.payload.getPacket().getStatusCode());
        transactionRepository.save(byPayerTransactionID);

        //MAKE ACKNOWLEDGEMENT

        AuthStatus authStatus = new AuthStatus();
        authStatus.setAuthStatusCode(131);
        authStatus.setAuthStatusDescription("API Call doesn't need Authentication");

        Result result = new Result();
        result.setBeepTransactionID(callback.payload.getPacket().beepTransactionID);
        result.setPayerTransactionID(callback.payload.getPacket().payerTransactionID);
        result.setStatusCode(188);
        result.setStatusDescription("Response was Received");

        Model model = new Model();

        model.setAuthStatus(authStatus);
        model.setResult(result);
        return model;

    }

    @Override
    public Mono<ResponseData> queryPaymentStatus(MainRequest queryPaymentStatus) {
        return postBody(queryPaymentStatus);
    }


    @Override
    public Mono<ResponseData> raiseInvoice(RaiseInvoice raiseInvoice) {
        log.info("Upload an Invoice from Payment {}", raiseInvoice.toString());
        TransactionEntity raiseInvoiceTransaction =  new TransactionEntity();
        raiseInvoiceTransaction.setCustomerName(raiseInvoice.getCustomerName());
        raiseInvoiceTransaction.setMsisdn(raiseInvoice.getMsisdn());
        raiseInvoiceTransaction.setAmount(raiseInvoice.getAmount());
        raiseInvoiceTransaction.setDatePaymentReceived(raiseInvoice.getDatePaymentReceived());
        raiseInvoiceTransaction.setExpiryDate(raiseInvoice.getExpiryDate());
        raiseInvoiceTransaction.setStatusCode(131);
        raiseInvoiceTransaction.setCurrencyCode("KES");
        raiseInvoiceTransaction.setStatusDescription("Invoice Raised");
        raiseInvoiceTransaction.setServiceCode("KES-AIRTIME");
        raiseInvoiceTransaction.setPaymentMode("Online Transaction");
        raiseInvoiceTransaction.setNarration("Success");
        raiseInvoiceTransaction.setExpiryDate(raiseInvoice.getExpiryDate());

        raiseInvoiceTransaction.setPayerTransactionID(UUID.randomUUID().toString());


        transactionRepository.save(raiseInvoiceTransaction);

        Packet packet= new Packet();
        packet.setAmount(raiseInvoice.getAmount());
        packet.setCustomerName(raiseInvoice.getCustomerName());
        packet.setDatePaymentReceived(raiseInvoice.getDatePaymentReceived());
        packet.setExpiryDate(raiseInvoice.getExpiryDate());
        packet.setMsisdn(raiseInvoice.getMsisdn());
        packet.setAmount(raiseInvoice.getAmount());
        packet.setStatusCode(131);
        packet.setCurrencyCode("KES");
        packet.setStatusDescription("Invoice Raised");
        packet.setServiceCode("KES-AIRTIME");
        packet.setPaymentMode("Online Transaction");
        packet.setReceiptNarration("Success");


        Credentials credentials = new Credentials();
        credentials.setPassword("sandboxuser!");
        credentials.setUsername("SANDBOXUSER");

        Payload payload = new Payload();
        payload.setCredentials(credentials);
        payload.setPacket(List.of(packet));


        MainRequest mainRequest = new MainRequest();
        mainRequest.setFunction("BEEP.raiseInvoice");
        mainRequest.setCountryCode("KE");
        mainRequest.setPayload(payload);
        return postBody(mainRequest);
    }


    @Override
    public Mono<ResponseData> queryBill(MainRequest queryBill) {

        return null;
    }


    public Mono<ResponseData> postBody(MainRequest payments) {

        OkHttpClient okHttpClient = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(mediaType, objectMapper.writeValueAsString(payments));
            log.info("Response Body ===> {}", objectMapper.writeValueAsString(payments));


// Build request
            Request request = new Request.Builder()
                    .url("https://beep2.cellulant.africa:9001/paymentRouter/JSON/")
                    .method("POST", requestBody)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();

            ResponseData responseData = objectMapper.readValue(body.string(), ResponseData.class);
            log.info("Status Body ===> {}", responseData.toString());
            return Mono.just(responseData);


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}