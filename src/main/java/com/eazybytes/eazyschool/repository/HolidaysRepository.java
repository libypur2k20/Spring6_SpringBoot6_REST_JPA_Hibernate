package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path="holidays")
public interface HolidaysRepository extends CrudRepository<Holiday, String> {


}
