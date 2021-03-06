package com.app.ykc.zigzag_challenge.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.lang.IllegalArgumentException

@JsonClass(generateAdapter = true)
data class ShoppingMallData(
    @Json(name = "week") val week: String,
    @Json(name = "list") val list: List<ShoppingMall>
)

@JsonClass(generateAdapter = true)
data class ShoppingMall(
        @Json(name = "n") val name: String,
        @Json(name = "u") val url: String,
        @Json(name = "S") val rawStyle: String,
        @Json(name = "0") val point: Long,
        /**
         * '10대', '20대초반', '20대중반', '20대후반', '30대초반', '30대중반', '30대후반'
         * [0, 0, 1, 1, 1, 0, 0]
         * */
        @Json(name = "A") val rawAges: List<Int>,

        var styles: Set<String> = emptySet(),
        var ages: Set<Ages> = emptySet()
) {
    init {
        styles = rawStyle.split(",").map { it.trim() }.toSet()
        ages = Ages.createSetFromRaw(rawAges)
    }
}

enum class Range {
     Early, Mid, Late;

    companion object {
        fun create(index: Int): Range {
            return when(index % 3) {
                1 -> Early
                2 -> Mid
                0 -> Late
                else -> throw IllegalArgumentException("잘못된 index가 들어왔음 $index")
            }
        }
    }
}

fun Range.getString(context: Context) : String {
    return when(this) {
        Range.Early -> "초반"
        Range.Mid -> "중반"
        Range.Late -> "후반"
    }
}

sealed class Ages {
    object Teens: Ages()
    data class Twenties(val range: Range): Ages()
    data class Thirties(val range: Range): Ages()

    companion object {
        fun createSetFromRaw(raw: List<Int>) : Set<Ages> {
            return raw.mapIndexed { index, i ->
                index to i
            }.filter {
                it.second == 1
            }.map {
                Ages.create(it.first)
            }.toSet()
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        fun create(index: Int) : Ages {
            return when(index) {
                0 -> Teens
                in 1 .. 3 -> Twenties(range = Range.create(index))
                in 4 .. 6 -> Thirties(range = Range.create(index))
                else -> throw IllegalArgumentException("잘못된 index가 들어왔음 $index")
            }
        }
    }
}

fun Ages.getString(context: Context) : String {
    val age = this
    return when (age) {
        is Ages.Teens -> "10대"
        is Ages.Twenties -> {
            "20대 ${age.range.getString(context)}"
        }
        is Ages.Thirties -> {
            "30대 ${age.range.getString(context)}"
        }
    }
}