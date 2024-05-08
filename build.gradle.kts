plugins {
    kotlin("jvm") version "1.9.24"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.7"
    }
}
