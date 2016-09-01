package com.tenx.ms.retail.store.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.store.rest.dto.Store;
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
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class StoreControllerTest extends AbstractIntegrationTest {


    private final RestTemplate template = new TestRestTemplate();
    private final String API_VERSION = RestConstants.VERSION_ONE;
    private final String REQUEST_URI = "%s" + API_VERSION + "/stores/";
    private static final Long INVALID_STORE_ID = 99999999L;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGetStoreById() {

        Long storeId = 1L;

        Store store = getStore(storeId);
        assertNotNull("Store shouldn't be null", store);
        assertThat("storeId value not match", store.getStoreId(), is(1L));
        assertThat("name value not match", store.getName(), is("Store One"));
    }

    @Test
    public void testGetStoreByIdNotfound() {

        Long storeId = INVALID_STORE_ID;
        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + storeId, null, HttpMethod.GET);
        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetStores() {
        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()), null, HttpMethod.GET);
            String result = response.getBody();
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());

            List<Store> stores = mapper.readValue(result, new TypeReference<List<Store>>() {
            });

            assertThat("Store list shouldn't be null", stores, is(notNullValue()));
            assertThat("Store count not match", stores.size(), greaterThanOrEqualTo(2));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateStore() {

        Store store = new Store();
        store.setName("Store created");

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()), mapper.writeValueAsString(store), HttpMethod.POST);
            String result = response.getBody();

            assertEquals("HTTP Status code incorrect", HttpStatus.CREATED, response.getStatusCode());

            ResourceCreated<Long> resourceResult = mapper.readValue(result, new TypeReference<ResourceCreated<Long>>() {
            });

            Long storeId = resourceResult.getId();
            assertThat("New store id not match", storeId, is(4L));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateStore() {

        Store store = new Store();
        store.setName("Store Updated");
        Long storeId = 2L;

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + storeId, mapper.writeValueAsString(store), HttpMethod.PUT);
            assertEquals("HTTP Status code incorrect", HttpStatus.NO_CONTENT, response.getStatusCode());

            Store storeUpdated = getStore(storeId);

            assertThat("Store name not match", store.getName(), is(storeUpdated.getName()));

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateStoreNotFound() {
        Store store = new Store();
        store.setName("Store Updated");
        Long storeId = INVALID_STORE_ID;

        try {

            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + storeId, mapper.writeValueAsString(store), HttpMethod.PUT);
            assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testDeleteStore() {

        Long storeId = 3L;

        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + storeId, null, HttpMethod.DELETE);
        assertEquals("HTTP Status code incorrect", HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    public void testDeleteStoreNotFound() {

        Long storeId = INVALID_STORE_ID;

        ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + "/" + storeId, null, HttpMethod.DELETE);
        assertEquals("HTTP Status code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    private Store getStore(Long storeId) {
        Store store = null;
        try {
            ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI, basePath()) + storeId, null, HttpMethod.GET);
            String received = response.getBody();
            assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
            store = mapper.readValue(received, Store.class);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return store;
    }

}

