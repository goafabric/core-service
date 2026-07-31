package org.goafabric.core.architecture

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.goafabric.core.Application

@AnalyzeClasses(packagesOf = [Application::class], importOptions = [DoNotIncludeTests::class])
class PersistenceRulesTest {
    @ArchTest
    val entitiesShouldBeInEntityPackage: ArchRule = ArchRuleDefinition.classes()
        .that().haveSimpleNameEndingWith("Eo")
        .should().resideInAPackage("..persistence..entity..")
        .allowEmptyShould(true)

    @ArchTest
    val repositoriesShouldResideInPersistencePackage: ArchRule = ArchRuleDefinition.classes()
        .that().haveSimpleNameEndingWith("Repository")
        .and().areInterfaces()
        .should().resideInAPackage("..persistence..")
        .allowEmptyShould(true)
}
