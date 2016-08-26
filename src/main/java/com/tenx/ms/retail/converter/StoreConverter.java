package com.tenx.ms.retail.converter;


import com.tenx.ms.retail.domain.StoreEntity;
import com.tenx.ms.retail.rest.dto.Store;

public final class StoreConverter {

    private StoreConverter() {}

    public static Store entityToDto(StoreEntity entity){
        Store store = new Store();
        store.setName(entity.getName());
        store.setStoreId(entity.getId());
        return store;
    }

    public static StoreEntity dtoToEntity(Store dto){
        StoreEntity store = new StoreEntity();
        store.setName(dto.getName());
        store.setId(dto.getStoreId());
        return store;
    }
}
