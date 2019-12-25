package com.slt.cloudapp.catalogService.controllers.dto

import com.slt.cloudapp.catalogService.dao.data.ProductEntity
import com.slt.cloudapp.catalogService.services.data.Product

data class ProductDTO(
    val id: String?,
    val name: String,
    val image: String,
    val price: Int,
    val category: CategoryDTO) {

    fun toProduct() = Product(id, name, price, image, category.toCategory())
}

fun Product.toDTO() = ProductDTO(id, name, image, price, category.toDTO())