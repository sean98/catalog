package com.slt.cloudapp.catalogService.dao.crud

import com.slt.cloudapp.catalogService.dao.data.ProductEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param

interface ProductCrud: Neo4jRepository<ProductEntity,String> {

    fun findAllByNameLike(@Param("name") name: String, pageable: Pageable): List<ProductEntity>

    fun findAllByPriceLessThanEqual(@Param("price") price: Int, pageable: Pageable): List<ProductEntity>

    fun findAllByPriceGreaterThanEqual(@Param("price") price: Int, pageable: Pageable): List<ProductEntity>

    fun findAllByCategoryNameIn(@Param("id") id: List<String>, pageable: Pageable): List<ProductEntity>

}