package com.tenx.ms.retail.order.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.rest.dto.OrderItem;
import com.tenx.ms.retail.order.rest.dto.OrderResult;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class OrderControllerTest extends AbstractIntegrationTest {

    private static final Long INVALID_PRODUCT_ID = 99999999L;
    private final RestTemplate template = new TestRestTemplate();
    private final String API_VERSION = RestConstants.VERSION_ONE;
    private final String REQUEST_URI = "%s" + API_VERSION + "/orders/";
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreateNewOrder() {

        Long storeId = 1L;
        Order order = new Order();

        order.setFirstName("Petter");
        order.setLastName("Parker");
        order.setStoreId(1L);
        order.setEmail("pparker@marvel.com");
        order.setPhone("3051234567");

        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(5L, 1L));
        items.add(new OrderItem(6L, 1L));

        order.setProducts(items);

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + storeId, mapper.writeValueAsString(order), HttpMethod.POST);
            assertEquals("HTTP Status code incorrect", HttpStatus.CREATED, response.getStatusCode());

            String responseBody = response.getBody();

            assertNotNull("Null response", responseBody);

            OrderResult result = mapper.readValue(responseBody, OrderResult.class);

            assertNotNull("Result shouldn't be null", result);

            Long orderId = result.getOrderId();
            String status = result.getStatus();

            assertThat("New order id not match", orderId, is(1L));
            assertThat("New order status not match", status, is("ORDERED"));

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testCreateNewOrderNotFoundInvalidStock() {

        Long storeId = 1L;
        Order order = new Order();

        order.setFirstName("Petter");
        order.setLastName("Parker");
        order.setStoreId(1L);
        order.setEmail("pparker@marvel.com");
        order.setPhone("3051234567");

        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(INVALID_PRODUCT_ID, 1L));

        order.setProducts(items);

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + storeId, mapper.writeValueAsString(order), HttpMethod.POST);
            assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }


    @Test
    public void testCreateNewOrderFailInsufficientStock() {

        Long storeId = 1L;
        Order order = new Order();

        order.setFirstName("Petter");
        order.setLastName("Parker");
        order.setStoreId(1L);
        order.setEmail("pparker@marvel.com");
        order.setPhone("1234567890");

        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(6L, 100L));

        order.setProducts(items);

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + storeId, mapper.writeValueAsString(order), HttpMethod.POST);
            assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testCreateNewOrderValidationErrors() {

        Long storeId = 1L;
        Order order = new Order();

        order.setFirstName("Petter12");
        order.setLastName("Parke$r8");
        order.setStoreId(1L);
        order.setEmail("pparker@marvel");
        order.setPhone("123456789012");

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + storeId, mapper.writeValueAsString(order), HttpMethod.POST);
            assertEquals("HTTP Status code incorrect", HttpStatus.PRECONDITION_FAILED, response.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }
}
