plugins {
    id(Plugins.JAVA_LIB)
    id(Plugins.KOTLIN_JVM)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    implementation(Libs.Kotlin.COROUTINES_CORE)
    implementation(Libs.JAVAX_INJECT)

    testImplementation(platform(Libs.JUnit5.BOM))
    testImplementation(Libs.JUnit5.JUPITER)
    testImplementation(Libs.MOCKK)
    testImplementation(Libs.KOTEST)
    testImplementation(Libs.Kotlin.COROUTINES_TEST)
}

tasks.test {
    useJUnitPlatform()
}