package com.anarock.cpsourcing.utilities

import android.util.Patterns
import java.util.regex.Pattern

class CommonUtilities{
    companion object {
        private const val indiaMobilePattern:String = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$"
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.toRegex().matches(email);
        }
        fun isValidPhoneNumber(number: String): Boolean {
            //TODO : Make condition to validate based on the country
            var pattern: Pattern = Pattern.compile(indiaMobilePattern)
            return pattern.matcher(number).find()
        }
    }
}