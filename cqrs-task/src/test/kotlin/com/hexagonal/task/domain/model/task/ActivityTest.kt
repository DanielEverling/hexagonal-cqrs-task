package com.hexagonal.task.domain.model.task

import com.cross.domain.Notification
import com.cross.domain.NotificationType
import com.cross.domain.toListNotification
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain same`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class ActivityTest {

    @Test
    fun `should create activity with success`() {
        val newActivity = Activity(description = "Realizar entrega do iphone", date = LocalDateTime.now().plusDays(7))
        newActivity.description `should be equal to` "Realizar entrega do iphone"
        newActivity.date `should not be` null
        newActivity.status `should be equal to` ActivityStatus.WAITING
        newActivity.initializeData `should be` null
    }

    @Test
    fun `should validate a creation of an activity when description in empty`() {
        val newActivity = Activity(description = "", date = LocalDateTime.now().plusDays(7))
        val expectedNotifications = listOf(Notification("Descrição é obrigatório."))
        newActivity.validators().toListNotification() `should contain same` expectedNotifications
    }

    @Test
    fun `should not initializing an activity when it is canceled`() {
        val newActivity = Activity(description = "Realizar entrega do iphone", date = LocalDateTime.now().plusDays(7))
        newActivity.status `should be equal to` ActivityStatus.WAITING
        newActivity.cancel()
        newActivity.status `should be equal to` ActivityStatus.CANCELED
        val notifications = newActivity.initialize()
        newActivity.status `should be equal to` ActivityStatus.CANCELED
        notifications.toListNotification().first() `should be equal to` Notification("Atividade não pode ser inicializada pois está CANCELED", NotificationType.BUSINESS_RULE)
    }

    @Test
    fun `should not initializing an activity`() {
        val newActivity = Activity(description = "Realizar entrega do iphone", date = LocalDateTime.now().plusDays(7))
        newActivity.status `should be equal to` ActivityStatus.WAITING
        newActivity.initialize()
        newActivity.status `should be equal to` ActivityStatus.STARTED
        newActivity.initializeData `should not be` null
    }

    @Test
    fun `should cancel an activity`() {
        val newActivity = Activity(description = "Realizar entrega do iphone", date = LocalDateTime.now().plusDays(7))
        newActivity.status `should be equal to` ActivityStatus.WAITING
        newActivity.cancel()
        newActivity.status `should be equal to` ActivityStatus.CANCELED
    }

    @Test
    fun `should complete an activity`() {
        val newActivity = Activity(description = "Realizar entrega do iphone", date = LocalDateTime.now().plusDays(7))
        newActivity.status `should be equal to` ActivityStatus.WAITING
        newActivity.ready()
        newActivity.status `should be equal to` ActivityStatus.READY
    }

    @Test
    fun `should not complete an activity when it is cancel`() {
        val newActivity = Activity(description = "Realizar entrega do iphone", date = LocalDateTime.now().plusDays(7))
        newActivity.status `should be equal to` ActivityStatus.WAITING
        newActivity.cancel()
        newActivity.status `should be equal to` ActivityStatus.CANCELED
        newActivity.ready()
        newActivity.status `should be equal to` ActivityStatus.CANCELED
    }

}