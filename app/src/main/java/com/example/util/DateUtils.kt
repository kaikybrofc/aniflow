package com.example.util

import java.util.Calendar
import java.util.concurrent.TimeUnit

object DateUtils {
    /**
     * Formata um timestamp em milissegundos para tempo relativo em português.
     * Exemplo: "há 15 min", "há 2 horas", "há 1 dia".
     */
    fun formatRelativeTime(timestampMillis: Long): String {
        val now = System.currentTimeMillis()
        val diff = (now - timestampMillis).coerceAtLeast(0)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)

        return when {
            minutes < 1 -> "agora mesmo"
            minutes < 60 -> "há ${minutes}m"
            hours < 24 -> "há ${hours}h"
            days == 1L -> "ontem"
            days < 7 -> "há ${days} dias"
            else -> "há ${days / 7} sem"
        }
    }

    /**
     * Retorna o dia atual da semana mapeado para ISO-8601 (1 = Segunda, 7 = Domingo).
     */
    fun getCurrentIsoDayOfWeek(): Int {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 1
        }
    }

    /**
     * Retorna o nome curto do dia da semana (Seg, Ter, Qua, Qui, Sex, Sáb, Dom).
     */
    fun getShortDayName(isoDay: Int): String {
        return when (isoDay) {
            1 -> "Seg"
            2 -> "Ter"
            3 -> "Qua"
            4 -> "Qui"
            5 -> "Sex"
            6 -> "Sáb"
            7 -> "Dom"
            else -> ""
        }
    }

    /**
     * Retorna o nome completo do dia da semana.
     */
    fun getFullDayName(isoDay: Int): String {
        return when (isoDay) {
            1 -> "Segunda-feira"
            2 -> "Terça-feira"
            3 -> "Quarta-feira"
            4 -> "Quinta-feira"
            5 -> "Sexta-feira"
            6 -> "Sábado"
            7 -> "Domingo"
            else -> ""
        }
    }
}
