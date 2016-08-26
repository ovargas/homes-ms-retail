package com.tenx.ms.retail.services;

import com.tenx.ms.retail.converter.StoreConverter;
import com.tenx.ms.retail.domain.StoreEntity;
import com.tenx.ms.retail.repositories.StoreRepository;
import com.tenx.ms.retail.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tenx.ms.retail.converter.StoreConverter.dtoToEntity;

@Service
public class StoreService {


    @Autowired
    private StoreRepository repository;

    public Store getStoreById(Long id) {
        return repository.findById(id).map(StoreConverter::entityToDto).get();
    }

    public List<Store> getStores() {

        return repository.findAll().stream().map(StoreConverter::entityToDto).collect(Collectors.toList());
    }

    public Long createStore(Store store) {

        StoreEntity entity = repository.save(dtoToEntity(store));
        return entity.getId();
    }

    public void updateStore(Store store) {

        StoreEntity entity = repository.findById(store.getStoreId()).get();
        if (entity != null) {
            repository.save(dtoToEntity(store));
        }
    }

    public void deleteStore(Long id) {
        StoreEntity entity = repository.findById(id).get();
        if (entity != null) {
            repository.delete(id);
        }
    }
}
