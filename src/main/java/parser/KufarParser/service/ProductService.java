package parser.KufarParser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parser.KufarParser.model.Product;
import parser.KufarParser.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> check(List<Product> products){
        List<Product> res = new ArrayList<>();
        for (var product : products){
            if (!productRepository.existsByLink(product.getLink())){
                res.add(product);
                productRepository.save(product);
            }
        }
        return res;
    }


}