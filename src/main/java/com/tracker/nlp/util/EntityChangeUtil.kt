package cn.edu.njnu.collection.backend.util

import org.springframework.beans.BeanUtils
import java.util.function.Consumer

/**
 * Copyright (c) 2021
 *
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: EntityChangeUtil.java
 * @LastModified: 2021/11/16 20:36:16
 */
object EntityChangeUtil {

    fun <SRC, DST> transform(clazz: Class<DST>, dao: SRC): DST {
        val dto = clazz.newInstance()
        BeanUtils.copyProperties(dao!!, dto)
        return dto
    }

    fun <SRC, DST> transform(clazz: Class<DST>, daos: List<SRC>): List<DST> {
        val dtos: MutableList<DST> = ArrayList()
        daos.forEach(Consumer { dao: SRC ->
            try {
                dtos.add(transform(clazz, dao))
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
        })
        return dtos
    }
}