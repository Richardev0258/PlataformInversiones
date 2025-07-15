package com.btg.pactual.service.subscription;

import com.btg.pactual.model.Transaction;

import java.util.List;

public interface ISubscriptionService {
    String subscribe(Long clientId, Long fundId);
    String cancelSubscription(Long clientId, Long fundId);
    List<Transaction> getTransactionHistory(Long clientId);
}
