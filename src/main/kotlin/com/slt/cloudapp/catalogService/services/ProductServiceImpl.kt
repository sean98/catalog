package com.slt.cloudapp.catalogService.services

import com.slt.cloudapp.catalogService.dao.CategoriesDao
import com.slt.cloudapp.catalogService.dao.ProductDao
import com.slt.cloudapp.catalogService.dao.data.ProductEntity
import com.slt.cloudapp.catalogService.services.data.Product
import com.slt.cloudapp.catalogService.services.data.toProduct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.UnsupportedOperationException
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
        return productDao.addProduct(product.toEntity()).toProduct()
    }

    override fun getProductById(id: String): Product {
        return productDao.getProductById(id)?.toProduct()
                ?: throw NoSuchElementException("couldn't find element with id $id")
    }

    override fun getProducts(sortBy: String, page: Int, size: Int): List<Product> {
        require(page>=0) { "Page must be a non negative integer" }
        require(size>0) { "Size must be a positive integer" }
        require(sortBy in productMembers) { "Sort field must be one of class members" }

        return productDao
                .getProducts(sortBy, page, size)
                .map { it.toProduct() }
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
            else -> throw UnsupportedOperationException("filtering by $filterType is not supported")
        }.map { it.toProduct() }
    }

    private fun Product.toEntity() =
            ProductEntity(id, name, price, image, categoryDao.getCategory(category.name)
                    ?: throw NoSuchElementException("couldn't find matching category"))
}
