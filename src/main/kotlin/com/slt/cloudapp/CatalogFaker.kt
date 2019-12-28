package com.slt.cloudapp

import com.github.javafaker.Faker
import com.slt.cloudapp.catalogService.dao.CategoriesDao
import com.slt.cloudapp.catalogService.dao.ProductDao
import com.slt.cloudapp.catalogService.dao.data.CategoryEntity
import com.slt.cloudapp.catalogService.dao.data.ProductEntity
import net.bytebuddy.utility.RandomString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import kotlin.random.Random

@Component
class CatalogFaker @Autowired constructor(private val categoriesDao: CategoriesDao,
                                          private val productsDao: ProductDao) {

    @PostConstruct
    fun fakeCatalog() {

        val faker = Faker()
        if (categoriesDao.getCategory("Food")==null) {
            val category = categoriesDao.addCategory(CategoryEntity("Food", "food"))
            repeat(5) {
                productsDao.addProduct(ProductEntity(
                        null,
                        faker.food().dish(),
                        Random.nextInt(1000),
                        faker.food().measurement(),
                        category))
            }
        }

        if (categoriesDao.getCategory("Fruits") == null) {
            val category = categoriesDao.addCategory(CategoryEntity("Fruits", "fruit", categoriesDao.getCategory("Food")))
            repeat(5) {
                productsDao.addProduct(ProductEntity(
                        null,
                        faker.food().fruit(),
                        Random.nextInt(1000),
                        faker.food().measurement(),
                        category))
            }
        }

        if (categoriesDao.getCategory("Vegetables") == null) {
            val category = categoriesDao.addCategory(CategoryEntity("Vegetables", "egetables", categoriesDao.getCategory("Food")))
            repeat(5) {
                productsDao.addProduct(ProductEntity(
                        null,
                        faker.food().vegetable(),
                        Random.nextInt(1000),
                        faker.food().measurement(),
                        category))
            }
        }

        if (categoriesDao.getCategory("Animals")==null) {
            val category = categoriesDao.addCategory(CategoryEntity("Animals", "animals"))
            repeat(5) {
                productsDao.addProduct(ProductEntity(
                        null,
                        faker.animal().name(),
                        Random.nextInt(1000),
                        faker.address().country(),
                        category))
            }
        }

        if (categoriesDao.getCategory("Titans")==null) {
            val category = categoriesDao.addCategory(CategoryEntity("Titans", "titans", categoriesDao.getCategory("Animals")))
            repeat(5) {
                productsDao.addProduct(ProductEntity(
                        null,
                        faker.ancient().titan(),
                        Random.nextInt(1000),
                        faker.rickAndMorty().location(),
                        category))
            }
        }

        if (categoriesDao.getCategory("Dogs")==null) {
            val category = categoriesDao.addCategory(CategoryEntity("Dogs", "dogs", categoriesDao.getCategory("Animals")))
            repeat(5) {
                productsDao.addProduct(ProductEntity(
                        null,
                        faker.dog().name(),
                        Random.nextInt(1000),
                        faker.dog().breed(),
                        category))
            }
        }

        if (categoriesDao.getCategory("Pokemons")==null) {
            val category = categoriesDao.addCategory(CategoryEntity("Pokemons", "pokemons", categoriesDao.getCategory("Animals")))
            repeat(5) {
                productsDao.addProduct(ProductEntity(
                        null,
                        faker.pokemon().name(),
                        faker.number().numberBetween(0, 1000),
                        faker.pokemon().location(),
                        category))
            }
        }
    }
}