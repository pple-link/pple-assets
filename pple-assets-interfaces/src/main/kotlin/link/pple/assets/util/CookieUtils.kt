package link.pple.assets.util

import org.springframework.util.SerializationUtils
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Author Heli
 */
object CookieUtils {

    fun getCookie(request: HttpServletRequest?, name: String): Cookie? {
        val cookies = request?.cookies ?: emptyArray()
        if (cookies.isNotEmpty()) {
            for (cookie in cookies) {
                if (cookie.name.equals(name)) {
                    return cookie
                }
            }
        }
        return null
    }

    fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = maxAge
        response.addCookie(cookie)
    }

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
        val cookies: Array<Cookie> = request.cookies
        if (cookies.isNotEmpty()) {
            for (cookie in cookies) {
                if (cookie.name.equals(name)) {
                    cookie.value = ""
                    cookie.path = "/"
                    cookie.maxAge = 0
                    response.addCookie(cookie)
                }
            }
        }
    }

    fun serialize(item: Any): String {
        return Base64.getUrlEncoder()
            .encodeToString(SerializationUtils.serialize(item))
    }

    fun <T> deserialize(cookie: Cookie, cls: Class<T>): T {
        return cls.cast(
            SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.value)
            )
        )
    }
}
