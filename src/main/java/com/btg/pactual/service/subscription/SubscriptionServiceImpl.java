package com.btg.pactual.service.subscription;

import com.btg.pactual.model.*;
import com.btg.pactual.repository.SubscriptionRepository;
import com.btg.pactual.repository.TransactionRepository;
import com.btg.pactual.service.client.IClientService;
import com.btg.pactual.service.fund.IFundService;
import com.btg.pactual.service.notification.INotificationService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final IClientService clientService;
    private final IFundService fundService;
    private final INotificationService notificationService;
    private final TransactionRepository transactionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   IClientService clientService,
                                   IFundService fundService,
                                   INotificationService notificationService,
                                   TransactionRepository transactionRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.clientService = clientService;
        this.fundService = fundService;
        this.notificationService = notificationService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String subscribe(Long clientId, Long fundId) {
        Client client = clientService.getById(clientId);
        Fund fund = fundService.getById(fundId);

        if (client == null || fund == null)
            return "Client or fund not found";

        if (client.getAvailableBalance() < fund.getMinAmount())
            return "Insufficient balance to subscribe to fund " + fund.getName();

        Subscription subscription = new Subscription();
        subscription.setClient(client);
        subscription.setFund(fund);
        subscription.setAmount(fund.getMinAmount());
        subscription.setActive(true);
        subscriptionRepository.save(subscription);

        clientService.updateBalance(clientId, -fund.getMinAmount());
        registerTransaction(client, fund, "SUBSCRIBE", fund.getMinAmount());

        notificationService.sendNotify(client.getEmail(), client.getPhone(), client.getNotificationPreference(), fund.getName());

        return "Successfully subscribed to fund " + fund.getName();
    }

    @Override
    public String cancelSubscription(Long clientId, Long fundId) {
        List<Subscription> subscriptions = subscriptionRepository.findByClientIdAndActiveTrue(clientId);

        for (Subscription sub : subscriptions) {
            if (sub.getFund().getId().equals(fundId)) {
                sub.setActive(false);
                subscriptionRepository.save(sub);

                clientService.updateBalance(clientId, sub.getAmount());
                registerTransaction(sub.getClient(), sub.getFund(), "CANCEL", sub.getAmount());

                return "Successfully cancelled subscription to fund " + sub.getFund().getName();
            }
        }
        return "No active subscription found for cancellation.";
    }

    @Override
    public List<Transaction> getTransactionHistory(Long clientId) {
        return transactionRepository.findByClientIdOrderByDateDesc(clientId);
    }

    private void registerTransaction(Client client, Fund fund, String type, Double amount) {
        Transaction transaction = new Transaction();
        transaction.setClient(client);
        transaction.setFund(fund);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        transactionRepository.save(transaction);
    }
}
