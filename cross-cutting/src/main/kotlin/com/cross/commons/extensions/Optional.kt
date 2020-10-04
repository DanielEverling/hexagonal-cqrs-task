package com.cross.commons.extensions

import com.cross.domain.Notification
import java.util.Optional

fun <T> Optional<T>.getOrEmpty(): Optional<T> {
  return when (this.isPresent) {
      true -> Optional.of(this.get())
      else -> Optional.empty()
    }
}

fun  Optional<Notification>.and(value: Optional<Notification>): Optional<Notification> =
        Optional.of(Notification(notification = "${this.get().notification} - ${value.get().notification}"))
