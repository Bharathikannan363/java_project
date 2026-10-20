package com.laundry.catalog;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/services")
public class LaundryServiceController {

    private final LaundryServiceService serviceLogic;

    public LaundryServiceController(LaundryServiceService serviceLogic) {
        this.serviceLogic = serviceLogic;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LaundryService create(@Valid @RequestBody LaundryService service) {
        return serviceLogic.create(service);
    }

    @GetMapping
    public List<LaundryService> getAll() {
        return serviceLogic.findAll();
    }

    @GetMapping("/{id}")
    public LaundryService getById(@PathVariable Long id) {
        return serviceLogic.findById(id);
    }

    @PutMapping("/{id}")
    public LaundryService update(@PathVariable Long id, @Valid @RequestBody LaundryService service) {
        return serviceLogic.update(id, service);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        serviceLogic.delete(id);
    }
}
