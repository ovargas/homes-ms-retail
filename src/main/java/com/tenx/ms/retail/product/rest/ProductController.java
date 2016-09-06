package com.tenx.ms.retail.product.rest;


import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(value = "products", description = "Product Management API")
@RestController("productControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Find product by id")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Product"),
            @ApiResponse(code = 404, message = "Product can't be found"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.GET)
    public Product getProductById(@ApiParam(name = "productId", value = "The product id") @PathVariable() Long id) {
        return productService.getProductById(id);
    }

    @ApiOperation(value = "Fetch all products")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Product"),
            @ApiResponse(code = 404, message = "Product can't be found"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @ApiOperation(value = "Create a new product")
    @ApiResponses(
        value = {
            @ApiResponse(code = 201, message = "Successful creation of Product"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceCreated<Long> createProduct(@Valid @RequestBody Product product) {
        return new ResourceCreated<>(productService.createProduct(product));
    }

    @ApiOperation(value = "Update an existing product")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful update of Product"),
            @ApiResponse(code = 404, message = "Product can't be found"),
            @ApiResponse(code = 412, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(
        @ApiParam(name = "productId", value = "The product id") @PathVariable() Long id,
        @Valid @RequestBody Product product) {
        product.setProductId(id);
        productService.updateProduct(product);
    }

    @ApiOperation(value = "Delete a product")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful delete of Product"),
            @ApiResponse(code = 404, message = "Product can't be found"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@ApiParam(name = "productId", value = "The product id") @PathVariable() Long id) {
        productService.deleteProduct(id);
    }

}
