package org.enmichuk.springignite.dao.repository;

import org.apache.ignite.springdata.repository.IgniteRepository;
import org.apache.ignite.springdata.repository.config.RepositoryConfig;
import org.enmichuk.springignite.model.Person;

import javax.cache.Cache;
import java.util.List;

@RepositoryConfig(cacheName = "PersonCache")
public interface PersonRepository extends IgniteRepository<Person, Long> {
    public List<Person> findByFirstName(String name);

    public Cache.Entry<Long, Person> findTopByLastNameLike(String name);
}
