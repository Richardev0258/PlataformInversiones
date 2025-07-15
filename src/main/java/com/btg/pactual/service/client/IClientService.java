package com.btg.pactual.service.client;

import com.btg.pactual.model.Client;

public interface IClientService {
    Client getById(Long id);
    void updateBalance(Long id, Double amount);
}
