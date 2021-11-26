package br.com.zup.edu.mercadolivrekotlin.validators

import javax.persistence.EntityManager
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.CONSTRUCTOR)
@Constraint(validatedBy = [ValorUnicoValidator::class])
annotation class ValorUnico (
    val message: String = "O valor precisa ser único no Banco de Dados",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Any>> = [],
    val nomeDaClasse: KClass<*>,
    val campo: String,
)

class ValorUnicoValidator(
    private val entityManager: EntityManager,
    private var campo: String? = null,
    private var nomeDaClasse: KClass<*>? = null
): ConstraintValidator<ValorUnico, Any> {

    override fun initialize(constraintAnnotation: ValorUnico?) {
        campo = constraintAnnotation?.campo
        nomeDaClasse = constraintAnnotation?.nomeDaClasse
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        val sql: String = "SELECT 1 FROM ${nomeDaClasse?.simpleName} WHERE $campo = :value"

        return entityManager.createQuery(sql)
                .setParameter("value", value)
                .resultList
                .isEmpty()
    }
}