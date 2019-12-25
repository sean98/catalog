package com.slt.cloudapp.catalogService.controllers

import com.slt.cloudapp.catalogService.services.CategoriesService
import com.slt.cloudapp.catalogService.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.NoSuchElementException

@RestController
@RequestMapping("catalog/categories")
class CatalogController @Autowired constructor(
        private val productService: ProductService,
        private val categoriesService: CategoriesService) {

    @DeleteMapping
    fun deleteAll() {
        productService.deleteAll()
        categoriesService.deleteAll()
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    fun mapToHttpStatus(t: Throwable): ResponseEntity<Any> = when (t) {
        is IllegalArgumentException -> ResponseEntity(t.message, HttpStatus.BAD_REQUEST)
        is NoSuchElementException -> ResponseEntity(t.message, HttpStatus.NOT_FOUND)
        else -> ResponseEntity(t.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}