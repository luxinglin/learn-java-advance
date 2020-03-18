package cn.com.gary.dataneo4j.dao;

import cn.com.gary.dataneo4j.model.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MovieRepository extends Neo4jRepository<Movie, Long> {
}
