package link.pple.assets.infrastructure.util

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.SimpleExpression

infix fun <T> SimpleExpression<T>.eqFilterNull(right: T?): BooleanExpression? =
    if (right != null) eq(right) else null

infix fun SimpleExpression<String>.eqFilterEmpty(right: String?): BooleanExpression? =
    if (right.isNullOrEmpty()) null else eq(right)

infix fun <T> SimpleExpression<T>.inFilterEmpty(right: List<T>?): BooleanExpression? =
    if (right.isNullOrEmpty()) null else `in`(right)
