package com.slt.cloudapp.catalogService.controllers

import com.slt.cloudapp.catalogService.controllers.dto.CategoryDTO
import com.slt.cloudapp.catalogService.controllers.dto.toDTO
import com.slt.cloudapp.catalogService.services.CategoriesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.NoSuchElementException

//@RestController
//@RequestMapping("catalog/categories")
class CategoriesController @Autowired constructor(private val categoriesService: CategoriesService) {

    @PostMapping
    fun postCategory(@RequestBody category: CategoryDTO): CategoryDTO {
        return categoriesService.addCategory(category.toCategory()).toDTO()
    }

    @GetMapping
    fun getCategories(@RequestParam(required = false, defaultValue = "name") sortBy: String,
                      @RequestParam(required = false, defaultValue = "0") page: Int,
                      @RequestParam(required = false, defaultValue = "10") size: Int): Array<CategoryDTO> {
        return categoriesService.getCategories(sortBy, page, size)
                .map{ it.toDTO() }
                .toTypedArray()
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    fun mapToHttpStatus(t: Throwable): ResponseEntity<Any> = when (t) {
        is IllegalArgumentException -> ResponseEntity(t.message, HttpStatus.BAD_REQUEST)
        is NoSuchElementException -> ResponseEntity(t.message, HttpStatus.NOT_FOUND)
        else -> ResponseEntity(t.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}