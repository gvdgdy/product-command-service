package com.cqrs.command.controller;

import com.cqrs.command.dto.ProductEvent;
import com.cqrs.command.entity.Product;
import com.cqrs.command.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @Autowired
    private ProductCommandService productCommandService;


    @PostMapping
    public Product createProduct(@RequestBody ProductEvent productEvent)
    {
        return productCommandService.createPrduct(productEvent);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody ProductEvent productEvent)
    {
        return productCommandService.updateProduct(id,productEvent);
    }
}
