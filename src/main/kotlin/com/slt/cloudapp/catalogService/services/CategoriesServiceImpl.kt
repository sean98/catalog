package com.slt.cloudapp.catalogService.services

import com.slt.cloudapp.catalogService.dao.CategoriesDao
import com.slt.cloudapp.catalogService.dao.data.CategoryEntity
import com.slt.cloudapp.catalogService.services.data.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.full.memberProperties

@Service
class CategoriesServiceImpl @Autowired constructor(private val categoryDao: CategoriesDao) : CategoriesService{

    val categoryMembers = CategoryEntity::class.memberProperties.map { it.name }

    override fun deleteAll() {
        categoryDao.deleteAll()
    }

    override fun addCategory(category: Category): Category {
        if (!category.parentCategory.isNullOrBlank())
            require(categoryDao.getCategory(category.parentCategory) != null) { "can't add category because parent category does'nt exist" }

        return Category(categoryDao.addCategory(category.toEntity()))
    }

    override fun getCategories(sortBy: String, page: Int, size: Int): List<Category> {
        require(page>=0) { "Page must be a non negative integer" }
        require(size>0) { "Size must be a positive integer" }
        require(sortBy in categoryMembers) { "Sort field must be one of class members $categoryMembers" }

        return categoryDao.getCategories(sortBy, page, size).map { Category(it) }
    }
}