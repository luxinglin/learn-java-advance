package cn.com.gary.dataneo4j.dao;

import cn.com.gary.dataneo4j.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * person repository interface
 *
 * @author root
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {
}
