package api.affiliate.api.affiliate.service;

import api.affiliate.api.affiliate.entity.ProductTable;
import api.affiliate.api.affiliate.entity.StoreTable;
import api.affiliate.api.affiliate.exception.BaseException;
import api.affiliate.api.affiliate.exception.ProductException;
import api.affiliate.api.affiliate.exception.StoreException;
import api.affiliate.api.affiliate.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    public final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public List<ProductTable> findAllProduct() {
        List<ProductTable> product = productRepository.findAll();
        return product;
    }

    public List<ProductTable> findAllByStatusIsTrue() {
        List<ProductTable> product = productRepository.findAll();
        return product;
    }


    public void createProduct(Integer store, String productName, String productDetail, String productPrice) throws BaseException{
        ProductTable product = new ProductTable();
        product.setStoreId(store);
        product.setProductName(productName);
        product.setProductDetail(productDetail);
        product.setProductPrice(productPrice);
        if (productRepository.existsByProductName(product.getProductName())){
            throw ProductException.createProductNameDuplicated();
        }
        try {
            productRepository.save(product);
        }catch (Exception e){
         throw ProductException.createProductFail();
        }

    }


    public void updateProduct(ProductTable product, String productName, String productDetail, String productPrice) throws BaseException{
        product.setProductName(productName);
        product.setProductDetail(productDetail);
        product.setProductPrice(productPrice);
        product.setUpdated(new Date());
        try {
            productRepository.save(product);
        }catch (Exception e){
            throw ProductException.productNull();
        }
    }



    public ProductTable getByProductIdAndStore(StoreTable store, Integer id) throws BaseException {
        Optional <ProductTable> product = productRepository.findByProductIdAndStoreId(id, store.getStoreId());
        if (product.isEmpty()){
            throw ProductException.productNull();
        }
        return product.get();
    }



}
