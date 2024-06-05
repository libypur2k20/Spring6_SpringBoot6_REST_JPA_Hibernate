package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface AddressRepository extends CrudRepository<Address, Integer> {
}
