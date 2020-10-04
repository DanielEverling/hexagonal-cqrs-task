package com.hexagonal.task.infra.query.task

import org.springframework.stereotype.Repository

@Repository
class TaskQueryRepository {

    fun findAllTask(): List<TaskProjection> {
        TODO("Not yet implemented")
    }

    fun findLateTask (): List<TaskLateProjection> {
        TODO("Not yet implemented")
    }

}