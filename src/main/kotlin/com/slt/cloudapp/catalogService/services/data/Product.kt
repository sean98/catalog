package com.slt.cloudapp.catalogService.services.data

import com.slt.cloudapp.catalogService.dao.data.ProductEntity

class Product(val id: String?,
              val name: String,
              val price: Int,
              val image: String,
              val category: Category)

fun ProductEntity.toProduct() = Product(id, name, price, image, category!!.toCategory())