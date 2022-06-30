package api.affiliate.api.affiliate.business;


import api.affiliate.api.affiliate.entity.ProductTable;
import api.affiliate.api.affiliate.entity.StoreTable;
import api.affiliate.api.affiliate.entity.UserTable;
import api.affiliate.api.affiliate.exception.BaseException;
import api.affiliate.api.affiliate.exception.ProductException;
import api.affiliate.api.affiliate.model.Response;
import api.affiliate.api.affiliate.model.product.ProductCreateRequest;
import api.affiliate.api.affiliate.service.ProductService;
import api.affiliate.api.affiliate.service.StoreService;
import api.affiliate.api.affiliate.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBusiness {


    private final ProductService productService;
    private final TokenService tokenService;
    private final StoreService storeService;


    public ProductBusiness(ProductService productService, TokenService tokenService, StoreService storeService) {
        this.productService = productService;
        this.tokenService = tokenService;
        this.storeService = storeService;
    }


    public List<ProductTable> finAllProduct() {
        List<ProductTable> product = productService.findAllProduct();
        return product;
    }

    public List<ProductTable> findAllByStatusIsTrue() {
        List<ProductTable> product = productService.findAllByStatusIsTrue();
        return product;
    }




    public Object createProduct(ProductCreateRequest request) throws BaseException {
        UserTable user = tokenService.getUserByToken();
        checkRoleIsStore(user);
        StoreTable store = storeService.findByUserId(user);
        productService.createProduct(store.getStoreId(), request.getProductName(), request.getProductDetail(), request.getProductPrice());
        return new Response().success("create product success");

    }



    public Object updateProduct(ProductCreateRequest request, Integer productId) throws BaseException {
        UserTable user = tokenService.getUserByToken();
        checkRoleIsStore(user);
        StoreTable store = storeService.findByUserId2(user);
        ProductTable product = productService.getByProductIdAndStore(store, productId);
        productService.updateProduct(product, request.getProductName(), request.getProductDetail(), request.getProductPrice());
        return new Response().success("update product success");
    }


    public void checkRoleIsStore(UserTable user) throws BaseException {
        UserTable.Role role = user.getRole();
        boolean checkRole = role.equals(UserTable.Role.USER)
                || role.equals(UserTable.Role.CUSTOMER)
                || role.equals(UserTable.Role.ADMIN);
        if (checkRole){
            throw ProductException.roleUserNotAllowed();
        }
    }







}
