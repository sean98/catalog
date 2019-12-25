package com.slt.cloudapp.catalogService.dao.crud

import com.slt.cloudapp.catalogService.dao.data.CategoryEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param

interface CategoryCrud: PagingAndSortingRepository<CategoryEntity, String> {

    fun getAllByParentCategoryLike(@Param("id") id: String): List<CategoryEntity>
}