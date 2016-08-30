package com.tenx.ms.retail.rest.dto;

import com.tenx.ms.commons.validation.constraints.Email;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ApiModel("Order")
public class Order {

    @ApiModelProperty(value = "Store Id", required = true)
    private Long storeId;

    @ApiModelProperty(value = "Purchaser first name", required = true)
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must be only letters")
    @Valid
    private String firstName;

    @ApiModelProperty(value = "Purchaser last name", required = true)
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must be only letters")
    @Valid
    private String lastName;

    @ApiModelProperty(value = "Purchaser email", required = true)
    @Email
    @Valid
    private String email;

    @ApiModelProperty(value = "Purchaser phone number", required = true)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Valid
    private String phone;

    @ApiModelProperty(value = "Order status")
    private String status;

    @ApiModelProperty(value = "Order date/time")
    private Date orderDate;

    @ApiModelProperty(value = "Order purchase products", required = true)
    @Size(min = 1)
    @Valid
    @NotNull
    private List<OrderItem> products;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItem> products) {
        this.products = products;
    }
}

