package com.slt.cloudapp.catalogService.dao

import com.slt.cloudapp.catalogService.dao.crud.ProductCrud
import com.slt.cloudapp.catalogService.dao.data.ProductEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
class ProductDaoImpl @Autowired constructor(val productCrud: ProductCrud) : ProductDao {

    @Transactional
    override fun deleteAll() {
        productCrud.deleteAll()
    }

    @Transactional
    override fun addProduct(product: ProductEntity): ProductEntity {
        require(product.id == null) { "product must have null id before insertion" }
        return productCrud.save(product)
    }


    override fun getProductById(id: String): ProductEntity? = productCrud.findById(id).orElse(null)


    override fun getProducts(sortBy: String, page: Int, size: Int) = productCrud
            .findAll(PageRequest.of(page, size, Sort.Direction.ASC, sortBy)).content


    override fun getProductByName(name: String, sortBy: String, page: Int, size: Int) = productCrud
            .findAllByNameLike(name, PageRequest.of(page, size, Sort.Direction.DESC, sortBy))


    override fun getProductByMinPrice(min: Int, sortBy: String, page: Int, size: Int) = productCrud
            .findAllByPriceGreaterThanEqual(min, PageRequest.of(page, size, Sort.Direction.DESC, sortBy))

    override fun getProductByMaxPrice(max: Int, sortBy: String, page: Int, size: Int) = productCrud
            .findAllByPriceLessThanEqual(max, PageRequest.of(page, size, Sort.Direction.ASC, sortBy))

    override fun getProductByCategory(category: List<String>, sortBy: String, page: Int, size: Int) = productCrud
            .findAllByCategoryNameIn(category, PageRequest.of(page, size, Sort.Direction.ASC, sortBy, sortBy))
}