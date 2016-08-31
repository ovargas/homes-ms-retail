package com.tenx.ms.retail.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.rest.dto.Stock;
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class StockControllerTest extends AbstractIntegrationTest {


    private final RestTemplate template = new TestRestTemplate();
    private final String API_VERSION = RestConstants.VERSION_ONE;
    private final String REQUEST_URI = "%s" + API_VERSION + "/stock/";
    private static final Long INVALID_STORE_ID = 99999999L;
    private static final Long INVALID_PRODUCT_ID = 99999999L;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void testGetStockById() {

        Long productId = 2L;
        Long storeId = 1L;

        Stock product = getStock(storeId, productId);
        assertNotNull("Stock shouldn't be null", product);
        assertThat("productId value not match", product.getProductId(), is(2L));
        assertThat("storeId value not match", product.getStoreId(), is(1L));
        assertThat("count value not match", product.getCount(), is(7L));
    }

    @Test
    public void testGetStockNotfoundInvalidStoreId() {

        Long productId = 2L;

        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + INVALID_STORE_ID + "/" + productId, null, HttpMethod.GET);
        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetStockNotfoundInvalidProductId() {

        Long storeId = 1L;

        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + storeId + "/" + INVALID_PRODUCT_ID, null, HttpMethod.GET);
        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetStockNotfoundInvalidStoreIdAndProductId() {

        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + INVALID_STORE_ID + "/" + INVALID_PRODUCT_ID, null, HttpMethod.GET);
        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateStock() {

        Long storeId = 1L;
        Long productId = 1L;

        Stock stock = new Stock();
        stock.setCount(10L);

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + storeId + "/" + productId, mapper.writeValueAsString(stock), HttpMethod.POST);
            assertEquals("HTTP Status code incorrect", HttpStatus.NO_CONTENT, response.getStatusCode());

            Stock stockAfterUpdate = getStock(storeId, productId);

            assertThat("Product id not match", stockAfterUpdate.getProductId(), is(1L));
            assertThat("Store id not match", stockAfterUpdate.getStoreId(), is(1L));
            assertThat("Count not match", stockAfterUpdate.getCount(), is(10L));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateStockValidationError() {

        Long storeId = 1L;
        Long productId = 1L;

        Stock stock = new Stock();
        stock.setCount(-5L);

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + storeId + "/" + productId, mapper.writeValueAsString(stock), HttpMethod.POST);
            assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    private Stock getStock(Long storeId, Long productId) {
        Stock stock = null;
        try {
            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + storeId + "/" + productId, null, HttpMethod.GET);
            String received = response.getBody();
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            stock = mapper.readValue(received, Stock.class);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return stock;
    }

}

