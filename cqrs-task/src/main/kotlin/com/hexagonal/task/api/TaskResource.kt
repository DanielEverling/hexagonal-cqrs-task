package com.hexagonal.task.api

import com.cross.infra.BaseLocation.Companion.location
import com.cross.infra.Resources
import com.hexagonal.task.domain.command.*
import com.hexagonal.task.infra.query.task.TaskProjection
import com.hexagonal.task.infra.query.task.TaskQueryRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskResource(private val taskQueryRepository: TaskQueryRepository,
                   private val createTaskCommandHandler: CreateTaskCommandHandler,
                   private val initializeTaskCommandHandler: InitializeTaskCommandHandler,
                   private val changeAgentCommandHandler: ChangeAgentCommandHandler) {


    @PostMapping
    fun create(createTaskCommand: CreateTaskCommand): ResponseEntity<String> {
        createTaskCommandHandler.handler(createTaskCommand)
        return ResponseEntity.created(location(Resources.TASK, createTaskCommand.id)).build()
    }

    @PutMapping
    fun initializeTask(initializeTaskCommand: InitializeTaskCommand): ResponseEntity<InitializeTaskCommand> {
        initializeTaskCommandHandler.handler(initializeTaskCommand)
        return ResponseEntity.ok(initializeTaskCommand)
    }

    @PutMapping("/agent")
    fun changeAgent(changeAgentCommand: ChangeAgentCommand): ResponseEntity<ChangeAgentCommand> {
        changeAgentCommandHandler.handler(changeAgentCommand)
        return ResponseEntity.ok(changeAgentCommand)
    }

    @GetMapping
    fun findAll(): List<TaskProjection> =  taskQueryRepository.findAllTask()

}