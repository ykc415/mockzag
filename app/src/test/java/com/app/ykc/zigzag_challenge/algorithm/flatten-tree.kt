package com.app.ykc.zigzag_challenge.algorithm

import com.app.ykc.zigzag_challenge.algorithm.JsonParser.State.*
import org.junit.Before
import org.junit.Test
import java.io.File
import org.junit.Assert.*
import java.io.StringReader
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.LinkedHashMap

const val TEST_FILE_NAME = "test.json"
const val FILE_NAME = "categories.json"

class FileReader {
    fun readJsonFile(fileName: String): String {
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResource(fileName)
        val bufferedReader = File(resource.path).bufferedReader()
        return bufferedReader.use { it.readText() }
    }
}

class JsonConverter {

    val stackList = mutableListOf<List<String>>()

    fun convertToStringArray(json: String): Array<String> {
        val jsonObject = JsonParser().parse(json)

        obtain(jsonObject!!)

        val result = stackList.map {
            it.joinToString(separator = " > ")
        }

        stackList.clear()

        return result.toTypedArray()
    }

    fun obtain(map : JsonParser.JsonObject, stack: Stack<String> = Stack()) {

        if(map.members.isEmpty()) {
            stackList.add(stack.toList())
        }

        map.members.forEach { t, u ->
            stack.push(t)
            obtain(u, stack)
            stack.pop()
        }
    }
}


class JsonParser {
    enum class State {
        DEFAULT, READ_KEY
    }

    fun parse(json: String): JsonObject? {
        val reader = StringReader(json)

        var root: JsonObject? = null

        reader.use {
            var c: Int

            val stack = Stack<JsonObject>()

            var key = "root"

            val keyReader = StringBuilder()

            var state: State = DEFAULT

            loop@ while (true) {
                c = it.read()
                if (c == -1) break

                val char = c.toChar()

                when (char) {
                    '{' -> {
                        if (key != "root") {
                            val obj = JsonObject()
                            stack.peek().members[key] = obj
                            stack.push(obj)
                        } else {
                            root = JsonObject()
                            stack.push(root)
                        }
                    }
                    '}' -> {
                        stack.pop()
                    }
                    '"' -> {
                        state = if (state == READ_KEY) {
                            key = keyReader.toString()
                            keyReader.clear()
                            DEFAULT
                        } else {
                            READ_KEY
                        }
                    }
                    ':' -> Unit
                    ',' -> Unit
                    else -> {
                        when (state) {
                            READ_KEY -> {
                                keyReader.append(char)
                            }
                            DEFAULT -> Unit
                        }
                    }
                }
            }
        }

        return root
    }

    class JsonObject(
        val members: LinkedHashMap<String, JsonObject> = LinkedHashMap()
    ) {

        fun get(key: String): JsonObject? {
            return members[key]
        }

    }
}

class FlattenTreeTest {

    lateinit var jsonStr: String
    lateinit var testStr: String

    lateinit var jsonConverter: JsonConverter

    @Before
    fun initTest() {
        jsonConverter = JsonConverter()

        jsonStr = FileReader().readJsonFile(FILE_NAME)
        testStr = FileReader().readJsonFile(TEST_FILE_NAME)
    }

    @Test
    fun integrationTest() {
        val expected = jsonConverter.convertToStringArray(jsonStr)
        assertEquals(
            expected.toList().toString(),
            "[아우터 > 코트 > 트렌치코트, 아우터 > 코트 > 레인코트, 아우터 > 코트 > 싱글코트, 아우터 > 코트 > 더블코트, 아우터 > 자켓 > 베이직 자켓, 아우터 > 자켓 > 블레이저, 아우터 > 자켓 > 기타, 아우터 > 야상, 아우터 > 패딩 > 롱패딩, 아우터 > 패딩 > 숏패딩, 아우터 > 패딩 > 경량, 아우터 > 패딩 > 패딩 조끼, 아우터 > 패딩 > 기타, 아우터 > 가디건, 아우터 > 베스트, 아우터 > 기타, 상의 > 티셔츠 > 무지, 상의 > 티셔츠 > 그래픽, 상의 > 티셔츠 > 레터링, 상의 > 티셔츠 > 스트라이프, 상의 > 티셔츠 > 도트, 상의 > 티셔츠 > 캐릭터, 상의 > 티셔츠 > 기타, 상의 > 블라우스, 상의 > 셔츠/남방 > 무지, 상의 > 셔츠/남방 > 체크, 상의 > 셔츠/남방 > 도트, 상의 > 민소매/나시, 상의 > 기타, 바지 > 스키니, 바지 > 일자바지, 바지 > 슬랙스, 바지 > 조거팬츠, 바지 > 배기팬츠, 바지 > 기타, 스커트 > 미니스커트 > 머메이드, 스커트 > 미니스커트 > 랩, 스커트 > 미니스커트 > 기타, 스커트 > 미디스커트 > A라인, 스커트 > 미디스커트 > H라인, 스커트 > 롱스커트 > A라인, 스커트 > 롱스커트 > H라인, 스커트 > 롱스커트 > 플리츠, 스커트 > 롱스커트 > 플레어, 스커트 > 롱스커트 > 머메이드, 스커트 > 롱스커트 > 랩, 스커트 > 롱스커트 > 기타, 스커트 > 기타, 원피스 > 미니원피스 > 니트, 원피스 > 미니원피스 > 멜빵, 원피스 > 미니원피스 > 기타, 원피스 > 롱원피스 > 니트, 원피스 > 롱원피스 > 면, 원피스 > 롱원피스 > 멜빵, 원피스 > 롱원피스 > 기타, 원피스 > 점프수트, 원피스 > 기타, 투피스/세트 > 팬츠 세트, 투피스/세트 > 스커트 세트, 투피스/세트 > 원피스 세트, 투피스/세트 > 기타, 가방 > 숄더백, 가방 > 크로스백, 가방 > 토드백, 가방 > 클러치, 가방 > 미니백, 가방 > 백팩, 가방 > 파우치, 가방 > 지갑, 가방 > 에코백/캔버스, 가방 > 기타, 슈즈 > 힐 > 펌프스힐, 슈즈 > 힐 > 웨지힐, 슈즈 > 힐 > 슬링백힐, 슈즈 > 힐 > 기타, 슈즈 > 단화 > 로퍼, 슈즈 > 단화 > 모카신/털신, 슈즈 > 운동화 > 러닝화, 슈즈 > 운동화 > 스니커즈, 슈즈 > 운동화 > 슬립온, 슈즈 > 운동화 > 캔버스화, 슈즈 > 운동화 > 하이탑, 슈즈 > 운동화 > 아쿠아슈즈, 슈즈 > 운동화 > 기타, 슈즈 > 샌들 > 글래디에이터샌들, 슈즈 > 샌들 > 젤리샌들, 슈즈 > 샌들 > 기타, 슈즈 > 슬리퍼/쪼리, 슈즈 > 웨딩슈즈, 슈즈 > 기타, 액세서리 > 귀걸이 > 은귀걸이, 액세서리 > 귀걸이 > 진주귀걸이, 액세서리 > 귀걸이 > 패션귀걸이, 액세서리 > 귀걸이 > 피어싱귀걸이, 액세서리 > 귀걸이 > 기타, 액세서리 > 목걸이 > 14k목걸이, 액세서리 > 목걸이 > 패션목걸이, 액세서리 > 목걸이 > 써지컬 스틸, 액세서리 > 목걸이 > 기타, 액세서리 > 반지 > 14k반지, 액세서리 > 반지 > 18k반지, 액세서리 > 반지 > 써지컬 스틸, 액세서리 > 반지 > 기타, 액세서리 > 헤어 액세서리 > 가발, 액세서리 > 헤어 액세서리 > 헤어끈, 액세서리 > 헤어 액세서리 > 헤어밴드, 액세서리 > 헤어 액세서리 > 헤어핀, 액세서리 > 헤어 액세서리 > 기타, 액세서리 > 기타, 파자마/홈웨어 > 파자마, 파자마/홈웨어 > 로브/가운, 파자마/홈웨어 > 기타, 비치웨어 > 비키니 > 와이어 비키니, 비치웨어 > 비키니 > 노와이어 비키니, 비치웨어 > 비키니 > 하이웨스트 비키니, 비치웨어 > 비키니 > 기타, 비치웨어 > 모노키니, 비치웨어 > 래쉬가드, 비치웨어 > 비치원피스 > 롱 원피스, 비치웨어 > 비치원피스 > 미니 원피스, 비치웨어 > 비치원피스 > 웨딩 원피스, 비치웨어 > 비치원피스 > 기타, 비치웨어 > 비치상의, 비치웨어 > 비치팬츠 > 숏 팬츠, 비치웨어 > 비치팬츠 > 레깅스, 비치웨어 > 비치팬츠 > 스커트, 비치웨어 > 비치팬츠 > 점프수트, 비치웨어 > 비치팬츠 > 기타, 비치웨어 > 기타, 기타]"
        )
    }
    @Test
    fun jsonConverterTest() {
        val testJson = "{\"아우터\":{\"코트\":{\"트렌치코트\":{},\"레인코트\":{}}}}"
        val expected = jsonConverter.convertToStringArray(testJson)

        assertArrayEquals(expected, arrayOf("아우터 > 코트 > 트렌치코트", "아우터 > 코트 > 레인코트"))
    }

    @Test
    fun jsonParserTest() {
        val expected = JsonParser().parse(testStr)

        assertNotNull(expected)
        assertEquals(expected!!.members.keys.size, 2)
        assertEquals(expected.members.keys.first(), "a")

    }

}


