package com.hexagonal.task.domain.model.task

import com.cross.domain.ValidatorsAware
import com.cross.domain.vo.FullName
import java.util.*

data class Agent (val id: UUID, val name: FullName): ValidatorsAware {

    override fun validators() = name.validators()

}