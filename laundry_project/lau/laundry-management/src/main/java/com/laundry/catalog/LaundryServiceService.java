package com.laundry.catalog;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laundry.common.BadRequestException;
import com.laundry.common.ResourceNotFoundException;

@Service
public class LaundryServiceService {

    private final LaundryServiceRepository repository;

    public LaundryServiceService(LaundryServiceRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public LaundryService create(LaundryService service) {
        if (repository.existsByServiceNameIgnoreCase(service.getServiceName())) {
            throw new BadRequestException("Service '" + service.getServiceName() + "' already exists");
        }
        service.setId(null);
        return repository.save(service);
    }

    @Transactional(readOnly = true)
    public List<LaundryService> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public LaundryService findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laundry service not found with id " + id));
    }

    @Transactional
    public LaundryService update(Long id, LaundryService updated) {
        LaundryService existing = findById(id);
        if (repository.existsByServiceNameIgnoreCaseAndIdNot(updated.getServiceName(), id)) {
            throw new BadRequestException("Service '" + updated.getServiceName() + "' already exists");
        }
        existing.setServiceName(updated.getServiceName());
        existing.setPrice(updated.getPrice());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
