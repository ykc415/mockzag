package com.app.ykc.zigzag_challenge.filter

import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.ShoppingMall
import java.lang.IllegalStateException

class FilterLogic {

    fun getFilteredData(list: List<ShoppingMall>, ageSet: Set<Ages>, styleSet: Set<String>)
            : List<ShoppingMall> {

        when {
            ageSet.isEmpty() && styleSet.isEmpty() -> {
                return list
            }

            ageSet.isNotEmpty() && styleSet.isEmpty() -> {
                return list.filter { data ->
                    data.ages.any { ageSet.contains(it) }
                }
            }

            ageSet.isEmpty() && styleSet.isNotEmpty() -> {
                when (styleSet.size) {
                    1 -> {
                        return list.filter { data ->
                            data.styles.any { styleSet.first() == it }
                        }.sortedByDescending { it.point }
                    }
                    else -> {
                        return list.filter { data ->
                            data.styles.any {
                                styleSet.any { s -> s == it }
                            }
                        }.groupBy {
                            styleSet.containsAll(it.styles)
                        }.map {
                            it.value.sortedByDescending { v -> v.point }
                        }.flatten()
                    }
                }
            }

            ageSet.isNotEmpty() && styleSet.isNotEmpty() -> {
                return list.filter { data ->
                    data.ages.any { ageSet.contains(it) }
                }.filter {
                    it.styles.toSet() == styleSet
                }.sortedByDescending { v -> v.point }
            }

            else -> throw IllegalStateException("""
                 FilterLogic에 정의되지 않은 상태가 들어옴
                    $list
                    $ageSet
                    $styleSet)
            """.trimIndent())
        }
    }
}