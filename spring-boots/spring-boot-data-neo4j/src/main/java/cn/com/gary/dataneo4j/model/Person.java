package cn.com.gary.dataneo4j.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Person")
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long nodeId;

    @Property(name = "name")
    private String name;

    @Property(name = "born")
    private int born;
}