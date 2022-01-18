package org.example.repository;

import org.example.model.SimpleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleEntityRepository extends JpaRepository<SimpleEntity, Long> {

}
