plugins {
    kotlin("jvm") version "1.9.23"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.6"
    }
}
