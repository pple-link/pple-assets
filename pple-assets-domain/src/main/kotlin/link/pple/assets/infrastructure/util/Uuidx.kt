package link.pple.assets.infrastructure.util

import java.util.*

/**
 * @Author Heli
 */

fun String.toUUID(): UUID = UUID.fromString(this)
