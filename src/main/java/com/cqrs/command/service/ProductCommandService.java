package com.cqrs.command.service;


import com.cqrs.command.dto.ProductEvent;
import com.cqrs.command.entity.Product;
import com.cqrs.command.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductCommandService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public Product createPrduct(ProductEvent productEvent)
    {

        Product productDo = repository.save(productEvent.getProduct());
        ProductEvent event = new ProductEvent("createProduct",productDo);
        kafkaTemplate.send("productEventTopic",event);

        return productDo;
    }


    public Product updateProduct(long id ,ProductEvent productEvent)
    {
        Product existingProduct = repository.findById(id).orElseThrow(()->
                new RuntimeException("Product not found " + id));
        Product newProduct = productEvent.getProduct();


        existingProduct.setName(newProduct.getName());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setDescription(newProduct.getDescription());

        Product productDo = repository.save(existingProduct);
        ProductEvent event = new ProductEvent("updateProduct",productDo);
        kafkaTemplate.send("productEventTopic",event);

        return productDo;
    }
}
