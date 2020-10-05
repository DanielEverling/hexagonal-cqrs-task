package com.cross.domain


import com.cross.events.commons.DomainEvent
import java.util.*
import java.lang.RuntimeException

class ResultEntityException(override val message : String) : RuntimeException(message)

sealed class ResultEntity<out L, out R> {
    class Failure(val notifications: List<Notification>) : ResultEntity<List<Notification>, Nothing>()
    class Success<T : AggregateRoot>(val entity : T) : ResultEntity<Nothing, T>()

    fun notifications() : List<Notification> {
        return when(this) {
            is Failure -> notifications
            else -> throw ResultEntityException("Result Entity is with success")
        }
    }

    fun entity() : R {
        return when(this) {
            is Success -> entity
            is Failure -> throw ResultEntityException("Result Entity is with fail -> $notifications")
        }
    }

    override fun toString(): String {
        return when(this) {
            is Success -> entity.toString()
            is Failure -> notifications.toString()
        }
    }
}

open abstract class Entity : ValidatorsAware {

    private val _notifications: MutableCollection<Optional<Notification>> = mutableListOf()

    private val _domainEvents: MutableList<DomainEvent> = mutableListOf();

    val notifications : List<Notification>
        get() = _notifications.map { it.get() }

    val domainEvents: List<DomainEvent>
        get() = _domainEvents

    val hasDomainEvents: Boolean = _domainEvents.isNotEmpty()

    protected fun addDomainEvent(domainEvent: DomainEvent) {
        this._domainEvents.add(domainEvent)
    }

    protected fun validate() {
         validators()
                 .filter { it.isPresent }
                 .toCollection(_notifications)
    }

    protected fun isValid(): Boolean = _notifications.isEmpty()

}
