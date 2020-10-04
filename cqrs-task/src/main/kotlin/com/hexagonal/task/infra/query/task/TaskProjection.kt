package com.hexagonal.task.infra.query.task

import com.cross.domain.vo.Address
import com.cross.domain.vo.Period
import java.util.*

data class TaskProjection(val id: UUID,
                          val activity: String,
                          val agent: String,
                          val local: Address,
                          val period: Period,
                          val items: MutableList<String>)