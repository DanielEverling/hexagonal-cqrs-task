package com.hexagonal.task.domain.command

import com.cross.commands.BaseCommand
import com.hexagonal.task.domain.model.task.Agent
import java.util.*

data class ChangeAgentCommand(val taskId: UUID, val newAgent: Agent): BaseCommand()