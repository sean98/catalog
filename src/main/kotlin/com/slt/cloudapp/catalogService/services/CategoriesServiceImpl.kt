package com.slt.cloudapp.catalogService.services

import com.slt.cloudapp.catalogService.dao.CategoriesDao
import com.slt.cloudapp.catalogService.dao.data.CategoryEntity
import com.slt.cloudapp.catalogService.services.data.Category
import com.slt.cloudapp.catalogService.services.data.toCategory
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

        return categoryDao.addCategory(category.toEntity()).toCategory()
    }

    override fun getCategories(sortBy: String, page: Int, size: Int): List<Category> {
        require(page>=0) { "Page must be a non negative integer" }
        require(size>0) { "Size must be a positive integer" }
        require(sortBy in categoryMembers) { "Sort field must be one of class members $categoryMembers" }

        return categoryDao.getCategories(sortBy, page, size)
                .map { it.toCategory() }
    }

    private fun getParent(parent: String?): CategoryEntity? {
        return if (parent != null)
            categoryDao.getCategory(parent) ?: throw NoSuchElementException("couldn't find parent category")
        else
            null
    }

    private fun Category.toEntity(): CategoryEntity {
        require(description!=null) { "category must have description" }
        return CategoryEntity(name, description, getParent(parentCategory))
    }
}