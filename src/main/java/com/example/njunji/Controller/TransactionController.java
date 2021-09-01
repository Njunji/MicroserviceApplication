package com.example.njunji.Controller;

import com.example.njunji.Repository.TransactionRepository;
import com.example.njunji.Service.TransactionInterface;
import com.example.njunji.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@Slf4j
public class TransactionController {
    @Autowired
    TransactionInterface transactionService;
    @Autowired
    TransactionRepository transactionRepository;
    @PostMapping("/postPayment")
    public Mono<ResponseData> postPayment(@RequestBody MainRequest requestData){
        return transactionService.save(requestData);
    }
    @PostMapping("/callback")
    public Model callback(@RequestBody Callback callback){
    return transactionService.processCallback(callback);
    }
    @PostMapping("/queryPaymentStatus")
    public Mono<ResponseData> queryPaymentStatus(@RequestBody MainRequest queryPaymentStatus){
    return transactionService.queryPaymentStatus(queryPaymentStatus);
    }
    @PostMapping("/queryBill")
    public Mono<ResponseData> queryBill(@RequestBody MainRequest queryBill){
        return transactionService.queryBill(queryBill);
    }
    @PostMapping("/raiseInvoice")
    public Mono<ResponseData> raiseInvoice(@RequestBody RaiseInvoice raiseInvoice){
        return  transactionService.raiseInvoice(raiseInvoice);
    }

}
