import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.3.61")
    }
}

apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

plugins {
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
//    id ("org.jetbrains.kotlin.plugin.noarg") version "1.3.61"
//    id ("org.jetbrains.kotlin.plugin.jpa") version "1.3.61"
}

group = "com.slt"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()

}

dependencies {
    //Kotlin libraries
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    //spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    //swagger
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

    //validator
    implementation("commons-validator:commons-validator:1.6")
    implementation("org.webjars.npm:i18n-iso-countries:3.7.8")

    //faker
    implementation("com.github.javafaker:javafaker:1.0.1")

    //dao
    implementation("org.hibernate:hibernate-gradle-plugin:5.4.10.Final")

    implementation("com.h2database:h2:1.4.200")

    implementation("org.springframework.data:spring-data-jpa:2.2.3.RELEASE")


}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
