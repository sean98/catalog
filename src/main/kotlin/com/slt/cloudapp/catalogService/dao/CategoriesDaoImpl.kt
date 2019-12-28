package com.slt.cloudapp.catalogService.dao

import com.slt.cloudapp.catalogService.dao.crud.CategoryCrud
import com.slt.cloudapp.catalogService.dao.data.CategoryEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class CategoriesDaoImpl @Autowired constructor(val categoryCrud: CategoryCrud) : CategoriesDao {

    override fun deleteAll() {
        categoryCrud.deleteAll()
    }

    override fun addCategory(category: CategoryEntity): CategoryEntity {
        require(!categoryCrud.existsById(category.name)) { "product already exists ${category.name}" }
        return categoryCrud.save(category)
    }

    override fun getCategory(name: String): CategoryEntity? = categoryCrud.findById(name).orElse(null)

    override fun getCategories(sortBy: String, page: Int, size: Int): List<CategoryEntity> =
            categoryCrud.findAll(PageRequest.of(page, size, Sort.Direction.ASC, sortBy)).content

    override fun getByParentName(name: String) =
            categoryCrud.getAllByParentCategoryNameLike(name)

}