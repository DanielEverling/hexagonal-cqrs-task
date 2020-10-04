package com.hexagonal.agent.api

import com.hexagonal.agent.application.dto.agent.AgentDTO
import com.hexagonal.agent.application.service.agent.AgentService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/agent")
class AgentResource constructor(val agentService: AgentService) {

    @ApiOperation(value = "Create an agent", response = ResponseEntity::class, notes = "This operation creates an agent with the data passed", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Returns success message", response = ResponseEntity::class),
        ApiResponse(code = 400, message = "If you have an error regarding a business validation", response = ResponseEntity::class),
        ApiResponse(code = 500, message = "If we have any error not handled by the application", response = ResponseEntity::class)
    ])
    @PostMapping
    fun create(agentDTO: AgentDTO): ResponseEntity<AgentDTO> {
        agentService.insert(agentDTO)
        return ResponseEntity.ok(agentDTO)
    }

    @PutMapping
    fun update(agentDTO: AgentDTO): ResponseEntity<AgentDTO> {
        agentService.update(agentDTO)
        return ResponseEntity.ok(agentDTO)
    }

    @GetMapping("{id}")
    fun findByID(id: UUID): ResponseEntity<AgentDTO> {
        return ResponseEntity.ok(agentService.findById(id))
    }

}