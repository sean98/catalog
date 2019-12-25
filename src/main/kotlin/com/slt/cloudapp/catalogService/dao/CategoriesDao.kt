package com.slt.cloudapp.catalogService.dao

import com.slt.cloudapp.catalogService.dao.data.CategoryEntity

interface CategoriesDao {

    fun deleteAll()

    fun addCategory(category: CategoryEntity): CategoryEntity

    fun getCategory(name: String): CategoryEntity?

    fun getCategories(sortBy: String, page: Int, size: Int): List<CategoryEntity>

    fun getByParentName(name: String): List<CategoryEntity>
}