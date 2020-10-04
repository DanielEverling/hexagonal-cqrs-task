package com.hexagonal.task.domain.command

import com.cross.commands.BaseCommand
import java.util.*

data class InitializeTaskCommand(val taskId: UUID): BaseCommand()