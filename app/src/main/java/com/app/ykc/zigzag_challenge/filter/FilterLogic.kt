package com.app.ykc.zigzag_challenge.filter

import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import java.lang.IllegalStateException

class FilterLogic {

    fun getFilteredData(list: List<ShoppingMall>, age: List<Ages>, style: List<String>)
            : List<ShoppingMall> {

        when {
            age.isEmpty() && style.isEmpty() -> {
                return list
            }

            age.isNotEmpty() && style.isEmpty() -> {
                return list.filter { data ->
                    data.ages.any { age.contains(it) }
                }
            }

            age.isEmpty() && style.isNotEmpty() -> {
                when (style.size) {
                    1 -> {
                        return list.filter { data ->
                            data.styles.any { style.first() == it }
                        }.sortedByDescending { it.point }
                    }
                    else -> {
                        return list.filter { data ->
                            data.styles.any {
                                style.contains(it)
                            }
                        }.groupBy {
                            style.toSet() == it.styles
                        }.map {
                            it.key to it.value.sortedByDescending{ v -> v.point }
                        }.reversed().flatMap {
                            it.second
                        }
                    }
                }
            }

            age.isNotEmpty() && style.isNotEmpty() -> {
                return list.filter { data ->
                    data.ages.any { age.contains(it) }
                }.filter { data ->
                    data.styles.any { style.contains(it) }
                }.groupBy {
                    style.containsAll(it.styles)
                }.map {
                    it.value.sortedByDescending { v -> v.point }
                }.reversed().flatten()
            }

            else -> throw IllegalStateException("""
                 FilterLogic에 정의되지 않은 상태가 들어옴
                    $list
                    $age
                    $style)
            """.trimIndent())
        }
    }
}