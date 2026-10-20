package com.laundry.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LaundryServiceRepository extends JpaRepository<LaundryService, Long> {

    boolean existsByServiceNameIgnoreCase(String serviceName);

    boolean existsByServiceNameIgnoreCaseAndIdNot(String serviceName, Long id);
}
