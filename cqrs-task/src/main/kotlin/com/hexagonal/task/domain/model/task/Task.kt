package com.hexagonal.task.domain.model.task

import com.cross.domain.*
import com.cross.domain.vo.*
import java.time.LocalDateTime
import java.util.*

data class Task private constructor(
        val id: UUID,
        val activity: Activity,
        private var _agent: Agent,
        val local: Address,
        val period: Period,
        private var _items: MutableList<String>): Entity(), AggregateRoot {

    val createdAt: LocalDateTime = LocalDateTime.now()

    init {
        validate()
    }

    override fun validators() = activity.validators() + agent.validators() + local.validators() + period.validators()

    val agent: Agent
        get() = _agent

    val items: List<String>
        get() = _items

    fun changeAgent(newAgent: Agent) {
        this._agent = newAgent
    }

    fun addItem(newItem: String) {
        _items.add(newItem)
    }

    fun removeItem(newItem: String) {
        _items.remove(newItem)
    }

    fun initializeActivity() {
        activity.initialize()
    }

    companion object {
        inline fun build(specification: Specification<Task> = Specification(), block: Task.Builder.() -> Unit) = Builder(specification).apply(block).build()
    }

    class Builder (private val specification: Specification<Task>) {

        var id: UUID = UUID.randomUUID()
        lateinit var activity: Activity
        lateinit var agent: Agent
        lateinit var local: Address
        lateinit var period: Period
        lateinit var items: MutableList<String>

        fun build() : ResultEntity<List<Notification>, Task> {
            val newTask = Task(id = id, _agent = agent, activity = activity, local = local, _items = items, period = period)

            return  when(newTask.isValid()) {
                true -> ResultEntity.Success<Task>(newTask)
                else -> ResultEntity.Failure(newTask.notifications)
            }
        }
    }

}