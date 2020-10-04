package com.cross.domain

import com.cross.commons.extensions.and
import java.util.Optional

open class Specification<in T:AggregateRoot> {
     open fun isSatisfiedBy(entity:T): Optional<Notification> = Optional.empty()
}

abstract class AndSpecification<in T:AggregateRoot>: Specification<T>() {

     abstract fun left() : Specification<T>
     abstract fun right() : Specification<T>

     override fun isSatisfiedBy(entity: T): Optional<Notification> =
             left().isSatisfiedBy(entity).and(right().isSatisfiedBy(entity))

}

abstract class OrSpecification<in T:AggregateRoot>: Specification<T>() {

     abstract fun left() : Specification<T>
     abstract fun right() : Specification<T>

     override fun isSatisfiedBy(entity: T): Optional<Notification> =
             left().isSatisfiedBy(entity).or { right().isSatisfiedBy(entity) }

}