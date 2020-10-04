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