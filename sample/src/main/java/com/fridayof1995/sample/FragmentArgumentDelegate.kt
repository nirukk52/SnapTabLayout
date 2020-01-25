package com.fridayof1995.sample

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Depression on 14-08-2018.
 */
class FragmentArgumentDelegate<T : Any> : kotlin.properties.ReadWriteProperty<androidx.fragment.app.Fragment, T> {

    var value: T? = null

    override operator fun getValue(thisRef: androidx.fragment.app.Fragment, property: kotlin.reflect.KProperty<*>): T {
        if (value == null) {
            val args = thisRef.arguments ?: throw IllegalStateException("Cannot read property ${property.name} if no arguments have been set")
            @Suppress("UNCHECKED_CAST")
            value = args.get(property.name) as T
        }
        return value ?: throw IllegalStateException("Property ${property.name} could not be read")
    }

    override operator fun setValue(thisRef: androidx.fragment.app.Fragment, property: kotlin.reflect.KProperty<*>, value: T) {
        if (thisRef.arguments == null) thisRef.arguments = android.os.Bundle()

        val args = thisRef.arguments
        val key = property.name

        when (value) {
            is String -> args?.putString(key, value)
            is Int -> args?.putInt(key, value)
            is Short -> args?.putShort(key, value)
            is Long -> args?.putLong(key, value)
            is Byte -> args?.putByte(key, value)
            is ByteArray -> args?.putByteArray(key, value)
            is Char -> args?.putChar(key, value)
            is CharArray -> args?.putCharArray(key, value)
            is CharSequence -> args?.putCharSequence(key, value)
            is Float -> args?.putFloat(key, value)
            is Bundle -> args?.putBundle(key, value)
//            is Binder -> BundleCompat.putBinder(args, key, value)
            is android.os.Parcelable -> args?.putParcelable(key, value)
            is java.io.Serializable -> args?.putSerializable(key, value)
            else -> throw IllegalStateException("Type ${value.javaClass.canonicalName} of property ${property.name} is not supported")
        }
    }
}