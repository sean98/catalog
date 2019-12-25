package com.slt.cloudapp.catalogService.services

import com.slt.cloudapp.catalogService.services.data.Category

interface CategoriesService {

    fun deleteAll()

    fun addCategory(category: Category): Category

    fun getCategories(sortBy: String, page: Int, size: Int): List<Category>
}