package org.example.tables.Controllers;

import org.example.tables.Entities.Category;
import org.example.tables.Entities.Warehouse;
import org.example.tables.Repositories.CategoryRepository;
import org.example.tables.Repositories.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OtherController {
    @Autowired
    WareHouseRepository wareHouseRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("/equipment")
    public List<Warehouse> addEquipment(@RequestBody Warehouse warehouse) {
        if (warehouse.getCategory() != null) {
            warehouse.setCategory(categoryRepository.findById(warehouse.getCategory().getId()));
            wareHouseRepository.save(warehouse);
        }
        return wareHouseRepository.findAll();
    }


    @PostMapping("/category")
    public List<Category> addCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }

    @PostMapping("/equipment/{id}")
    public List<Warehouse> updateWareHouse(@PathVariable Integer id, @RequestBody Warehouse warehouse) {
        System.out.println(warehouse);
        warehouse.setId(id);
        wareHouseRepository.save(warehouse);
        return wareHouseRepository.findAll();
    }
}