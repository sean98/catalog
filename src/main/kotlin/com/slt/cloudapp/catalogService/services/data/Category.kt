package com.slt.cloudapp.catalogService.services.data

import com.slt.cloudapp.catalogService.dao.data.CategoryEntity

class Category(val name: String,
               val description: String?,
               val parentCategory: String? = null)

fun CategoryEntity.toCategory() = Category(name, description, parentCategory?.name)