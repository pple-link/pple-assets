package link.pple.assets.util

import java.io.File

/**
 * @Author Heli
 */

fun String.getOnlyFileName() = this.substringAfterLast(File.separator)
