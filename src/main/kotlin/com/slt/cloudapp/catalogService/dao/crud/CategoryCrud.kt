package com.slt.cloudapp.catalogService.dao.crud

import com.slt.cloudapp.catalogService.dao.data.CategoryEntity
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param

interface CategoryCrud: Neo4jRepository<CategoryEntity, String> {

    fun getAllByParentCategoryNameLike(@Param("parentName") id: String): List<CategoryEntity>

}