package cn.com.gary.dataneo4j.model;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "DIRECTED")
@Data
public class Directed {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person startNode;

    @EndNode
    private Movie endNode;
}