package cn.com.gary.dataneo4j.dao;

import cn.com.gary.dataneo4j.model.ActedIn;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ActedInRepository extends Neo4jRepository<ActedIn, Long> {
}
