package com.slt.cloudapp.catalogService.services

import com.slt.cloudapp.catalogService.dao.CategoriesDao
import com.slt.cloudapp.catalogService.dao.ProductDao
import com.slt.cloudapp.catalogService.dao.data.ProductEntity
import com.slt.cloudapp.catalogService.services.data.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.NotSupportedException
import kotlin.reflect.full.memberProperties

@Service
class ProductServiceImpl @Autowired constructor(
        private val productDao: ProductDao,
        private val categoryDao: CategoriesDao): ProductService {

    val productMembers = ProductEntity::class.memberProperties.map { it.name }

    override fun deleteAll() {
        productDao.deleteAll()
    }

    override fun addProduct(product: Product): Product {
        val productEntity = productDao.addProduct(product.toEntity())
        val categoryEntity = categoryDao.getCategory(productEntity.categoryId)
                ?: throw NoSuchElementException("couldn't find matching category")
        return Product(productEntity, categoryEntity)
    }

    override fun getProductById(id: String): Product {
        val productEntity = productDao.getProductById(id) ?: throw NoSuchElementException("couldn't find element with id $id")
        val categoryEntity = categoryDao.getCategory(productEntity.categoryId)
                ?: throw NoSuchElementException("couldn't find matching category")
        return Product(productEntity, categoryEntity)
    }

    override fun getProducts(sortBy: String, page: Int, size: Int): List<Product> {
        require(page>=0) { "Page must be a non negative integer" }
        require(size>0) { "Size must be a positive integer" }
        require(sortBy in productMembers) { "Sort field must be one of class members" }

        return productDao
                .getProducts(sortBy, page, size)
                .map { Product(it, categoryDao.getCategory(it.categoryId)
                        ?: throw NoSuchElementException("couldn't find matching category")) }
    }

    private fun getCategorySubTree(name: String): List<String> {
        return arrayListOf(name).apply {
            addAll(categoryDao.getByParentName(name)
                    .flatMap { getCategorySubTree(it.name) })
        }
    }

    override fun getProducts(filterType: String, filterValue: String, sortBy: String, page: Int, size: Int): List<Product> {
        require(page>=0) { "Page must be a non negative integer" }
        require(size>0) { "Size must be a positive integer" }
        require(sortBy in productMembers) { "Sort field must be one of class members" }

        return when(filterType) {
            "byName" -> productDao.getProductByName(filterValue, sortBy, page, size)
            "byMinPrice" -> productDao.getProductByMinPrice(filterValue.toInt(), sortBy, page, size)
            "byMaxPrice" -> productDao.getProductByMaxPrice(filterValue.toInt(), sortBy, page, size)
            "byCategoryName" -> productDao.getProductByCategory(getCategorySubTree(filterValue), sortBy, page, size)
            else -> throw NotSupportedException("filtering by $filterType is not supported")
        }.map { Product(it, categoryDao.getCategory(it.categoryId)
                ?: throw NoSuchElementException("couldn't find matching category")) }
    }
}