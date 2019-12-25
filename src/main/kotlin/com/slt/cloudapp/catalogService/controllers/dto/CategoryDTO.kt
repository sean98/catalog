package com.slt.cloudapp.catalogService.controllers.dto

import com.slt.cloudapp.catalogService.services.data.Category

data class CategoryDTO(
    val name: String,
    val description: String?,
    val parentCategory: String? = null) {


    fun toCategory() = Category(name, description, parentCategory)
}

fun Category.toDTO() = CategoryDTO(name, description, parentCategory)