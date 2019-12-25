package com.slt.cloudapp.catalogService.services.data

import com.slt.cloudapp.catalogService.dao.data.CategoryEntity
import com.slt.cloudapp.catalogService.dao.data.ProductEntity

class Product(val id: String?,
              val name: String,
              val price: Int,
              val image: String,
              val category: Category) {

    constructor(product: ProductEntity, category: CategoryEntity):
            this(product.id, product.name, product.price, product.image, Category(category))

    fun toEntity() = ProductEntity(id, name, price, image, category.toEntity().name)
}