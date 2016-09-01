package com.tenx.ms.retail.stock.rest;


import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.services.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "stores", description = "Stock Management API")
@RestController("stockControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stock")
public class StockController {

    @Autowired
    private StockService stockService;


    @ApiOperation(value = "Find stock by product id")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Stock"),
            @ApiResponse(code = 404, message = "Stock can't be found"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = RequestMethod.GET)
    public Stock getStockByProductId(
        @ApiParam(name = "storeId", value = "The store id") @PathVariable() Long storeId,
        @ApiParam(name = "productId", value = "The product id") @PathVariable() Long productId) {
        return stockService.getStock(storeId, productId);
    }


    @ApiOperation(value = "Update the stock for a product")
    @ApiResponses(
        value = {
            @ApiResponse(code = 200, message = "Successful update of Stock"),
            @ApiResponse(code = 404, message = "Stock can't be found"),
            @ApiResponse(code = 412, message = "Validation error"),
            @ApiResponse(code = 500, message = "Internal server error"),
        }
    )
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStock(
        @ApiParam(name = "storeId", value = "The store id") @PathVariable() Long storeId,
        @ApiParam(name = "productId", value = "The product id") @PathVariable() Long productId,
        @Validated @RequestBody Stock stock) {
        stock.setStoreId(storeId);
        stock.setProductId(productId);
        stockService.updateStock(stock);
    }

}
