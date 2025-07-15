package com.btg.pactual.controller;

import com.btg.pactual.model.Transaction;
import com.btg.pactual.service.subscription.ISubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suscripciones")
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;

    public SubscriptionController(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam Long clientId, @RequestParam Long fundId) {
        return subscriptionService.subscribe(clientId, fundId);
    }

    @PostMapping("/cancel")
    public String cancel(@RequestParam Long clientId, @RequestParam Long fundId) {
        return subscriptionService.cancelSubscription(clientId, fundId);
    }

    @GetMapping("/history")
    public List<Transaction> history(@RequestParam Long clientId) {
        return subscriptionService.getTransactionHistory(clientId);
    }
}
