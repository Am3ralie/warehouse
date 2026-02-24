package org.example.tables.Repositories;

import org.example.tables.Entities.Category;
import org.example.tables.Entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<Warehouse,Integer> {
    void deleteByCategory(Category byId);
}
