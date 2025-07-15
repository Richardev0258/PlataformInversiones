package com.btg.pactual.service;

import com.btg.pactual.model.Fund;
import com.btg.pactual.repository.FundRepository;
import com.btg.pactual.service.fund.FundServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FundServiceImplTest {
    @Mock
    private FundRepository fundRepository;

    @InjectMocks
    private FundServiceImpl fundService;

    private Fund fund;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fund = new Fund(1L, "Fondo1", "FIC", 75_000.0);
    }

    @Test
    void testGetById_found() {
        when(fundRepository.findById(1L)).thenReturn(Optional.of(fund));
        Fund result = fundService.getById(1L);
        assertNotNull(result);
        assertEquals("Fondo1", result.getName());
    }

    @Test
    void testGetById_notFound() {
        when(fundRepository.findById(2L)).thenReturn(Optional.empty());
        assertNull(fundService.getById(2L));
    }
}
