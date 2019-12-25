package com.slt.cloudapp.catalogService.dao

import com.slt.cloudapp.catalogService.dao.data.ProductEntity

interface ProductDao {

    fun deleteAll()

    fun addProduct(product: ProductEntity): ProductEntity

    fun getProductById(id: String): ProductEntity?

    fun getProducts(sortBy: String, page: Int, size: Int): List<ProductEntity>

    fun getProductByName(name: String, sortBy: String, page: Int, size: Int): List<ProductEntity>

    fun getProductByMinPrice(min: Int, sortBy: String, page: Int, size: Int): List<ProductEntity>

    fun getProductByMaxPrice(max: Int, sortBy: String, page: Int, size: Int): List<ProductEntity>

    fun getProductByCategory(category: List<String>, sortBy: String, page: Int, size: Int): List<ProductEntity>
}