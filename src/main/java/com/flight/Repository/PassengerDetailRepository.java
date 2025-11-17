package com.flight.Repository;

import com.flight.Entity.PassengerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerDetailRepository extends JpaRepository<PassengerDetail, Long> {
}
