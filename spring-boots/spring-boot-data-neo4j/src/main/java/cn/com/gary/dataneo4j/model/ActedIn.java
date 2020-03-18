package cn.com.gary.dataneo4j.model;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "ACTED_IN")
@Data
public class ActedIn {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "roles")
    private String roles;

    @StartNode
    private Person startNode;

    @EndNode
    private Movie endNode;
}