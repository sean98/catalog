package com.slt.cloudapp.catalogService.services

import com.slt.cloudapp.catalogService.services.data.Product

interface ProductService {

    fun deleteAll()

    fun addProduct(product: Product): Product

    fun getProductById(id: String): Product

    fun getProducts(sortBy: String, page: Int, size: Int): List<Product>

    fun getProducts(filterType : String, filterValue: String,
                    sortBy: String, page: Int, size: Int): List<Product>
}
