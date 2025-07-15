package com.btg.pactual.service;

import com.btg.pactual.model.Client;
import com.btg.pactual.repository.ClientRepository;
import com.btg.pactual.service.client.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client(1L, "Pedro", "pedro@mail.com", "3213213210", 300_000.0, "EMAIL");
    }

    @Test
    void testGetById_found() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        Client found = clientService.getById(1L);
        assertNotNull(found);
        assertEquals("Pedro", found.getName());
    }

    @Test
    void testGetById_notFound() {
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());
        assertNull(clientService.getById(2L));
    }

    @Test
    void testUpdateBalance() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        clientService.updateBalance(1L, 50000.0);
        verify(clientRepository).save(client);
        assertEquals(350_000.0, client.getAvailableBalance());
    }
}
