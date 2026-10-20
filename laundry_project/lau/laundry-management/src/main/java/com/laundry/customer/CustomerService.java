package com.laundry.customer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laundry.common.BadRequestException;
import com.laundry.common.ResourceNotFoundException;

@Service
public class CustomerService {

    // Dependency Injection (constructor injection)
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer create(Customer customer) {
        if (customerRepository.existsByPhone(customer.getPhone())) {
            throw new BadRequestException("A customer with phone " + customer.getPhone() + " already exists");
        }
        customer.setId(null);
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    @Transactional
    public Customer update(Long id, Customer updated) {
        Customer existing = findById(id);
        if (customerRepository.existsByPhoneAndIdNot(updated.getPhone(), id)) {
            throw new BadRequestException("Another customer already uses phone " + updated.getPhone());
        }
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setEmail(updated.getEmail());
        existing.setAddress(updated.getAddress());
        return customerRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Customer existing = findById(id);
        customerRepository.delete(existing);
    }
}
