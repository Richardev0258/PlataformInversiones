package com.btg.pactual.service;

import com.btg.pactual.model.*;
import com.btg.pactual.repository.SubscriptionRepository;
import com.btg.pactual.repository.TransactionRepository;
import com.btg.pactual.service.client.IClientService;
import com.btg.pactual.service.fund.IFundService;
import com.btg.pactual.service.notification.INotificationService;
import com.btg.pactual.service.subscription.SubscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubscriptionServiceImplTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private IClientService clientService;

    @Mock
    private IFundService fundService;

    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private Client client;
    private Fund fund;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client(1L, "Pedro", "pedro@mail.com", "3213213210", 300_000.0, "EMAIL");
        fund = new Fund(2L, "Fondo1", "FIC", 75_000.0);
    }

    @Test
    void testSubscribe_successful() {
        when(clientService.getById(1L)).thenReturn(client);
        when(fundService.getById(2L)).thenReturn(fund);

        String result = subscriptionService.subscribe(1L, 2L);

        assertEquals("Successfully subscribed to fund Fondo1", result);
        verify(subscriptionRepository).save(any());
        verify(clientService).updateBalance(1L, -75_000.0);
        verify(transactionRepository).save(any());
        verify(notificationService).sendNotify(eq(client.getEmail()), eq(client.getPhone()), eq(client.getNotificationPreference()), eq(fund.getName()));
    }

    @Test
    void testSubscribe_clientNotFound() {
        when(clientService.getById(1L)).thenReturn(null);
        when(fundService.getById(2L)).thenReturn(fund);

        String result = subscriptionService.subscribe(1L, 2L);
        assertEquals("Client or fund not found", result);
    }

    @Test
    void testCancelSubscription_successful() {
        Subscription subscription = new Subscription(1L, 75_000.0, true, client, fund);
        when(subscriptionRepository.findByClientIdAndActiveTrue(1L)).thenReturn(List.of(subscription));

        String result = subscriptionService.cancelSubscription(1L, 2L);

        assertEquals("Successfully cancelled subscription to fund Fondo1", result);
        verify(subscriptionRepository).save(subscription);
        verify(clientService).updateBalance(1L, 75_000.0);
        verify(transactionRepository).save(any());
    }

    @Test
    void testCancelSubscription_notFound() {
        when(subscriptionRepository.findByClientIdAndActiveTrue(1L)).thenReturn(Collections.emptyList());

        String result = subscriptionService.cancelSubscription(1L, 2L);
        assertEquals("No active subscription found for cancellation.", result);
    }

    @Test
    void testGetTransactionHistory() {
        when(transactionRepository.findByClientIdOrderByDateDesc(1L)).thenReturn(Collections.emptyList());
        List<Transaction> result = subscriptionService.getTransactionHistory(1L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
