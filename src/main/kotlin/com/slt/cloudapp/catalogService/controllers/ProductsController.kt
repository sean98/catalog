package com.slt.cloudapp.catalogService.controllers

import com.slt.cloudapp.catalogService.controllers.dto.ProductDTO
import com.slt.cloudapp.catalogService.controllers.dto.toDTO
import com.slt.cloudapp.catalogService.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.NoSuchElementException

//@RestController
//@RequestMapping("catalog/products")
class ProductsController @Autowired constructor(private val productService: ProductService) {

    @PostMapping
    fun postProducts(@RequestBody product: ProductDTO): ProductDTO {
        return productService.addProduct(product.toProduct()).toDTO()
    }

    @GetMapping("{productId}")
    fun getProduct(@PathVariable productId: String): ProductDTO {
        return productService.getProductById(productId).toDTO()
    }

    @GetMapping
    fun getProducts(@RequestParam(required = false) filterType: String?,
                    @RequestParam(required = false) filterValue: String?,
                    @RequestParam(required = false, defaultValue = "name") sortBy: String,
                    @RequestParam(required = false, defaultValue = "0") page: Int,
                    @RequestParam(required = false, defaultValue = "10") size: Int): Array<ProductDTO> {

        return if (!filterValue.isNullOrBlank() && !filterType.isNullOrBlank())
            productService.getProducts(filterType, filterValue, sortBy, page, size)
                    .map { it.toDTO() }
                    .toTypedArray()
        else if (filterValue.isNullOrBlank() && filterType.isNullOrBlank())
            productService.getProducts(sortBy, page, size)
                    .map { it.toDTO() }
                    .toTypedArray()
        else
            throw IllegalArgumentException("must give both parameter filterType and filterValue or none of them")
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    fun mapToHttpStatus(t: Throwable): ResponseEntity<Any> = when (t) {
        is java.lang.IllegalArgumentException -> ResponseEntity(t.message, HttpStatus.BAD_REQUEST)
        is NoSuchElementException -> ResponseEntity(t.message, HttpStatus.NOT_FOUND)
        else -> ResponseEntity(t.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}