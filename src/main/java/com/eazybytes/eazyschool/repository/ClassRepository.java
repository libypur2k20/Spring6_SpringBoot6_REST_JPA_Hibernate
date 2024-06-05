package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.EazyClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface ClassRepository extends CrudRepository<EazyClass, Integer> {
}
