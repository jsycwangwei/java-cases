package org.wangwei.nosql.cassandra;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Users, String> {

}
