package com.waterreminder.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DateUtilsTest{

    @Test
    fun `leap year returns true`(){
        val result = DateUtils.isLeapYear(2024)
        assertThat(result).isTrue()
    }
}