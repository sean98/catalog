package com.slt.cloudapp.catalogService.dao.data

import org.neo4j.driver.internal.shaded.reactor.util.annotation.NonNull
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.id.UuidStrategy

@NodeEntity
data class ProductEntity(
        @Id
        @GeneratedValue(strategy = UuidStrategy::class)
        var id: String?,
        var name: String,
        var price: Int,
        var image: String,
        @Relationship(type = "category", direction = Relationship.OUTGOING)
        var category: CategoryEntity?
)