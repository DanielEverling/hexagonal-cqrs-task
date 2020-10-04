package com.cross.commons.exception

import com.cross.domain.Notification
import com.google.gson.Gson
import java.lang.RuntimeException

class BusinessException : RuntimeException {

    constructor(notification: Notification) : super(notification.notification)
    constructor (notifications: List<Notification>) : super(Gson().toJson(notifications))

}