package com.slt.cloudapp.catalogService.dao.data

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
 data class CategoryEntity(
        @Id
        var name: String,
        var description: String,
        @Relationship(type = "parent", direction = Relationship.OUTGOING)
        var parentCategory: CategoryEntity? = null
)

