package com.slt.cloudapp.catalogService.services.data

import com.slt.cloudapp.catalogService.dao.data.CategoryEntity

class Category(val name: String,
               val description: String?,
               val parentCategory: String? = null) {

    constructor(category: CategoryEntity): this(category.name, category.description, category.parentCategory)

    fun toEntity() = CategoryEntity(name, description, parentCategory)
}