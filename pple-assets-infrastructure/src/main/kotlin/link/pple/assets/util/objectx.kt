package link.pple.assets.util

/**
 * @Author Heli
 */

@Suppress("UNCHECKED_CAST")
fun <T> lateInit(): T = null as T

fun <T : Any> T?.notNull(): T = requireNotNull(this)
inline fun <T : Any> T?.notNull(lazyMessage: () -> Any): T = requireNotNull(this, lazyMessage)
