package com.hexagonal.task.domain.command

import com.cross.commands.BaseCommand
import com.cross.domain.vo.Address
import com.cross.domain.vo.Period
import com.hexagonal.task.domain.model.task.Activity
import com.hexagonal.task.domain.model.task.Agent

data class CreateTaskCommand (val activity: Activity,
                              val agent: Agent,
                              val local: Address,
                              val period: Period,
                              val items: MutableList<String>) : BaseCommand()