package com.tenx.ms.retail.store.rest;


import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.Store;
import com.tenx.ms.retail.store.services.StoreService;
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

import java.util.List;

@Api(value = "stores", description = "Store Management API")
@RestController("storeControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "Find store by id")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Store"),
            @ApiResponse(code = 404, message = "Store can't be found"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.GET)
    public Store getStoreById(@ApiParam(name = "storeId", value = "The store id") @PathVariable() Long id) {
        return storeService.getStoreById(id);
    }

    @ApiOperation(value = "Fetch all stores")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Store"),
            @ApiResponse(code = 404, message = "Store can't be found"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(method = RequestMethod.GET)
    public List<Store> getStores() {
        return storeService.getStores();
    }

    @ApiOperation(value = "Create a new store")
    @ApiResponses(
        value = {
            @ApiResponse(code = 201, message = "Successful creation of Store"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceCreated<Long> createStore(@RequestBody Store store) {
        return new ResourceCreated<>(storeService.createStore(store));
    }

    @ApiOperation(value = "Update an existing store")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful update of Store"),
            @ApiResponse(code = 404, message = "Store can't be found"),
            @ApiResponse(code = 412, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStore(
        @ApiParam(name = "storeId", value = "The store id") @PathVariable() Long id,
        @RequestBody Store store) {
        store.setStoreId(id);
        storeService.updateStore(store);
    }

    @ApiOperation(value = "Delete a store")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful delete of Store"),
            @ApiResponse(code = 404, message = "Store can't be found"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@ApiParam(name = "storeId", value = "The store id") @PathVariable() Long id) {
        storeService.deleteStore(id);
    }

}
