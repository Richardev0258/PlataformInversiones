package com.btg.pactual.service.client;

import com.btg.pactual.model.Client;
import com.btg.pactual.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements IClientService {
    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void updateBalance(Long id, Double amount) {
        Client client = repository.findById(id).orElse(null);
        if (client != null) {
            client.setAvailableBalance(client.getAvailableBalance() + amount);
            repository.save(client);
        }
    }
}
