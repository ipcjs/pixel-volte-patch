package dev.bluehouse.enablevolte

import android.app.IActivityManager
import android.system.Os
import org.lsposed.hiddenapibypass.HiddenApiBypass
import java.lang.reflect.Method


private fun Class<*>.getDeclaredMethodOrNull(
    methodName: String,
    vararg parameterTypes: Class<*>
): Method? =
    try {
        HiddenApiBypass.getDeclaredMethod(this, methodName, *parameterTypes)
    } catch (e: NoSuchMethodException) {
        null
    }

private val stopDelegateShellPermissionIdentityMethod by lazy {
    IActivityManager::class.java.getDeclaredMethodOrNull(
        "stopDelegateShellPermissionIdentity",
        Int::class.javaPrimitiveType!!
    )
}

fun IActivityManager.stopDelegateShellPermissionIdentityCompat() {
    val method = stopDelegateShellPermissionIdentityMethod
    if (method != null) {
        method.invoke(this, Os.getuid())
    } else {
        stopDelegateShellPermissionIdentity()
    }
}
