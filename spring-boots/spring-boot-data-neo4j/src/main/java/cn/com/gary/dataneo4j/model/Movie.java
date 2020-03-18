package cn.com.gary.dataneo4j.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Movie")
@Data
public class Movie {
    @Id
    @GeneratedValue
    private Long nodeId;

    @Property(name = "title")
    private String title;

    @Property(name = "released")
    private int released;
}