package com.example.njunji.Service;

import com.example.njunji.models.RaiseInvoice;
import com.example.njunji.models.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface TransactionInterface {
    public Mono<ResponseData> save(MainRequest data);
    Model processCallback(Callback callback);

    Mono<ResponseData> queryPaymentStatus(MainRequest queryPaymentStatus);

    Mono<ResponseData> raiseInvoice(RaiseInvoice raiseInvoice);

    Mono<ResponseData> queryBill(MainRequest queryBill);
}
