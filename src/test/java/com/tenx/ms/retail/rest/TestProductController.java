package com.tenx.ms.retail.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.rest.dto.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class TestProductController extends AbstractIntegrationTest {


    private final RestTemplate template = new TestRestTemplate();
    private final String API_VERSION = RestConstants.VERSION_ONE;
    private final String REQUEST_URI = "%s" + API_VERSION + "/products/";
    private static final Long INVALID_PRODUCT_ID = 99999999L;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getProductById() {

        Long productId = 1L;

        Product product = getProduct(productId);
        assertNotNull("Product shouldn't be null", product);
        assertThat("productId value not match", product.getProductId(), is(1L));
        assertThat("name value not match", product.getName(), is("Product One"));
    }

    @Test
    public void getProductByIdNotfound() {

        Long productId = INVALID_PRODUCT_ID;
        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + productId, null, HttpMethod.GET);
        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getProducts() {
        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()), null, HttpMethod.GET);
            String result = response.getBody();
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            List<Product> products = mapper.readValue(result, new TypeReference<List<Product>>() {
            });

            assertThat("Product list shouldn't be null", products, is(notNullValue()));
            assertThat("Product count not match", products.size(), greaterThanOrEqualTo(2));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void createProduct() {

        Product product = new Product();
        product.setName("Product created");
        product.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
        product.setSku("XYZ00");
        product.setStoreId(1L);
        product.setPrice(new BigDecimal("15.86"));

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()), mapper.writeValueAsString(product), HttpMethod.POST);
            String result = response.getBody();

            assertEquals("HTTP Status code incorrect", HttpStatus.CREATED, response.getStatusCode());

            ResourceCreated<Long> resourceResult = mapper.readValue(result, new TypeReference<ResourceCreated<Long>>() {
            });

            Long productId = resourceResult.getId();
            assertThat("New product id not match", productId, is(7L));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void createProductValidationError() {

        Product product = new Product();
        product.setName("Product created");
        product.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
        product.setSku("XY77");
        product.setStoreId(1L);
        product.setPrice(new BigDecimal("15.86"));

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()), mapper.writeValueAsString(product), HttpMethod.POST);
            String result = response.getBody();

            assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

            ResourceCreated<Long> resourceResult = mapper.readValue(result, new TypeReference<ResourceCreated<Long>>() {
            });

            Long productId = resourceResult.getId();
            assertNull("Product Id should be null", productId);

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void updateProduct() {

        Product product = new Product();
        product.setName("Product Updated");
        product.setPrice(new BigDecimal("90.15"));
        Long productId = 2L;

        try {
            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + productId, mapper.writeValueAsString(product), HttpMethod.PUT);
            assertEquals("HTTP Status code incorrect", HttpStatus.NO_CONTENT, response.getStatusCode());

            Product productUpdated = getProduct(productId);

            assertThat("Product name not match", product.getName(), is(productUpdated.getName()));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void updateProductValidationError() {

        Product product = new Product();
        product.setName("Product Updated");
        product.setSku("XYZ1");
        Long productId = 3L;

        try {
            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + productId, mapper.writeValueAsString(product), HttpMethod.PUT);
            assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

            Product productUpdated = getProduct(productId);
            assertThat("Product name not match", productUpdated.getName(), is("Product Three"));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void updateProductNotFound() {
        Product product = new Product();
        product.setName("Product Updated");
        Long productId = INVALID_PRODUCT_ID;

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + productId, mapper.writeValueAsString(product), HttpMethod.PUT);
            assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void deleteProduct() {

        Long productId = 4L;

        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + productId, null, HttpMethod.DELETE);
        assertEquals("HTTP Status code incorrect", HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    public void deleteProductNotFound() {

        Long productId = INVALID_PRODUCT_ID;

        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + productId, null, HttpMethod.DELETE);
        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    private Product getProduct(Long productId) {
        Product product = null;
        try {
            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + productId, null, HttpMethod.GET);
            String received = response.getBody();
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            product = mapper.readValue(received, Product.class);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return product;
    }

}

