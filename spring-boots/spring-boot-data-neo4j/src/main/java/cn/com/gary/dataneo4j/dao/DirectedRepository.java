package cn.com.gary.dataneo4j.dao;

import cn.com.gary.dataneo4j.model.Directed;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DirectedRepository extends Neo4jRepository<Directed, Long> {
}
