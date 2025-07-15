package com.btg.pactual.service.fund;

import com.btg.pactual.model.Fund;
import com.btg.pactual.repository.FundRepository;
import org.springframework.stereotype.Service;

@Service
public class FundServiceImpl implements IFundService {
    private final FundRepository repository;

    public FundServiceImpl(FundRepository repository) {
        this.repository = repository;
    }

    @Override
    public Fund getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
