package tw.edu.pu.csim.s1120326.cc1204

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import tw.edu.pu.csim.s1120326.cc1204.ui.theme.Cc1204Theme
import kotlin.random.Random
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cc1204Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Start(m = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// 主頁面組件
@Composable
fun Start(m: Modifier) {
    var showQuizPage by remember { mutableStateOf(false) }
    var showLearningPage by remember { mutableStateOf(false) }
    var showStartPage by remember { mutableStateOf(true) }
    var showScorePage by remember { mutableStateOf(false) }
    var showStampCollectionPage by remember { mutableStateOf(false) }
    var finalScore by remember { mutableStateOf(0) }
    var medalCount by remember { mutableStateOf(0) }  // 新增計數器記錄獎牌數量

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "背景圖",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                showStartPage -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = m
                    ) {
                        Button(
                            onClick = { showQuizPage = true; showStartPage = false },
                            modifier = Modifier
                                .padding(16.dp)
                                .height(60.dp)
                                .width(200.dp)
                        ) {
                            Text("測驗",style = TextStyle(
                                fontSize = 24.sp, // 設定字體大小
                                fontWeight = FontWeight.Bold)

                            )
                        }

                        Button(
                            onClick = { showLearningPage = true; showStartPage = false },
                            modifier = Modifier
                                .padding(16.dp)
                                .height(60.dp)
                                .width(200.dp)
                        ) {
                            Text("學習",
                                style = TextStyle(
                                fontSize = 24.sp, // 設定字體大小
                                fontWeight = FontWeight.Bold)
                            )
                        }

                        Button(
                            onClick = { showStampCollectionPage = true; showStartPage = false },
                            modifier = Modifier
                                .padding(16.dp)
                                .height(60.dp)
                                .width(200.dp)
                        ) {
                            Text("集章",
                                style = TextStyle(
                                    fontSize = 24.sp, // 設定字體大小
                                    fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
                showQuizPage -> {
                    QuizPage(onFinishQuiz = { score ->
                        finalScore = score
                        showQuizPage = false
                        showScorePage = true
                    })
                }
                showScorePage -> {
                    // 傳遞 onBackToStart 以增加獎牌
                    ScorePage(score = finalScore, onBackToStart = {
                        medalCount++  // 每次返回主頁時增加獎牌數量
                        showScorePage = false
                        showStartPage = true
                    })
                }
                showLearningPage -> {
                    LearningPage(onFinish = {
                        showLearningPage = false
                        medalCount++  // 每次學習結束後增加一個獎牌
                        showStartPage = true
                    })
                }
                showStampCollectionPage -> {
                    // 在這裡傳遞 medalCount 和 onRedeem
                    StampCollectionPage(
                        onBackToStart = { showStampCollectionPage = false; showStartPage = true },
                        medalCount = medalCount,
                        onRedeem = {
                            if (medalCount >= 10) {
                                medalCount -= 10  // 減少 10 個獎牌
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun ScorePage(score: Int, onBackToStart: () -> Unit) {
    val message = when {
        score > 79 -> "太厲害了!"
        score in 60..79 -> "不錯喔!"
        else -> "再加油~"
    }

    // 顯示背景圖片
    Image(
        painter = painterResource(id = R.drawable.congratulation1),
        contentDescription = "背景圖",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("得分: $score", style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(16.dp))
            Text(message, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium))

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onBackToStart) {  // 返回主頁時呼叫 onBackToStart
                Text("返回主頁", style = TextStyle(fontSize = 20.sp))
            }
        }
    }
}



@Composable
fun LearningPage(onFinish: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cnmb),
            contentDescription = "背景圖",
            contentScale = if (expanded) ContentScale.Crop else ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }

    val context = LocalContext.current
    val fruits = listOf(
        Triple(R.drawable.durian, listOf(R.raw.durianc, R.raw.duriane, R.raw.duriant), "榴蓮"),
        Triple(R.drawable.apple, listOf(R.raw.applec, R.raw.applee, R.raw.applet), "蘋果"),
        Triple(R.drawable.avocado, listOf(R.raw.avocadoc, R.raw.avocadoe, R.raw.avocadot), "酪梨"),
        Triple(R.drawable.banana, listOf(R.raw.bananac, R.raw.bananae, R.raw.bananat), "香蕉"),
        Triple(R.drawable.cantaloupe, listOf(R.raw.cantaloupec, R.raw.cantaloupee, R.raw.cantaloupet), "哈密瓜"),
        Triple(R.drawable.cherries, listOf(R.raw.cherriesc, R.raw.cherriese, R.raw.cherriest), "櫻桃"),
        Triple(R.drawable.dragon, listOf(R.raw.dragonc, R.raw.dragone, R.raw.dragont), "火龍果"),
        Triple(R.drawable.grape, listOf(R.raw.grapec, R.raw.grapee, R.raw.grapet), "葡萄"),
        Triple(R.drawable.grapefruit, listOf(R.raw.pomeloc, R.raw.pomeloe, R.raw.pomelot), "柚子"),
        Triple(R.drawable.lemon, listOf(R.raw.lemonc, R.raw.lemone, R.raw.lemont), "檸檬"),
        Triple(R.drawable.mango, listOf(R.raw.mangoc, R.raw.mangoe, R.raw.mangot), "芒果"),
        Triple(R.drawable.orange, listOf(R.raw.orangec, R.raw.orangee, R.raw.oranget), "橘子"),
        Triple(R.drawable.papaya, listOf(R.raw.papayac, R.raw.papayae, R.raw.papayat), "木瓜"),
        Triple(R.drawable.passion, listOf(R.raw.passionc, R.raw.passione, R.raw.passiont), "百香果"),
        Triple(R.drawable.peach, listOf(R.raw.peachc, R.raw.peache, R.raw.peacht), "水蜜桃"),
        Triple(R.drawable.persimmon, listOf(R.raw.persimmonc, R.raw.persimmone, R.raw.persimmont), "柿子"),
        Triple(R.drawable.pineapple, listOf(R.raw.pineapplec, R.raw.pineapplee, R.raw.pineapplet), "鳳梨"),
        Triple(R.drawable.strawberry, listOf(R.raw.strawberryc, R.raw.strawberrye, R.raw.strawberryt), "草莓"),
        Triple(R.drawable.watermelon, listOf(R.raw.watermelonc, R.raw.watermelone, R.raw.watermelont), "西瓜"),
        Triple(R.drawable.tomato, listOf(R.raw.tomatoc, R.raw.tomatoe, R.raw.tomatot), "番茄")
    )
    val success = Triple(R.drawable.success, listOf(R.raw.success), "完成")
    val allItems = fruits + success
    var currentIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            val currentFruit = allItems[currentIndex]
            val languages = if (currentFruit == success) listOf("完成") else listOf("中文", "英文", "台語")

            languages.forEachIndexed { langIndex, language ->
                Button(
                    onClick = {
                        val mediaPlayer = MediaPlayer.create(
                            context,
                            currentFruit.second.getOrElse(langIndex) { currentFruit.second[0] }
                        )
                        mediaPlayer.start()  // 播放語音
                        mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = if (language == "完成") language else "$language ${currentIndex + 1}",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Image(
                painter = painterResource(id = currentFruit.first),
                contentDescription = "${currentFruit.third} 圖片",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 20.dp)
            )
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                onClick = { if (currentIndex > 0) currentIndex-- },
                enabled = currentIndex > 0
            ) {
                Text("上一個")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { if (currentIndex < allItems.size - 1) currentIndex++ },
                enabled = currentIndex < allItems.size - 1
            ) {
                Text("下一個")
            }

            if (currentIndex == allItems.size - 1) {
                Button(
                    onClick = { onFinish() }, // 呼叫 onFinish 回到主頁
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text("結束")
                }
            }
        }
    }
}

@Composable
fun StampCollectionPage(onBackToStart: () -> Unit, medalCount: Int, onRedeem: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "我的獎牌",
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 將獎牌數量按每行顯示最多5個進行分組
            val medalRows = (1..medalCount).chunked(5)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                items(medalRows) { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        row.forEach {
                            Image(
                                painter = painterResource(id = R.drawable.medal),
                                contentDescription = "Medal",
                                modifier = Modifier.size(60.dp) // 可以調整大小
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 當獎牌數量達到 10 個或更多時顯示「兌換」按鈕
            if (medalCount >= 10) {
                Button(
                    onClick = onRedeem,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("兌換", style = TextStyle(fontSize = 20.sp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBackToStart) {
                Text("返回主頁", style = TextStyle(fontSize = 20.sp))
            }
        }
    }
}

// 測驗頁面組件
@Composable
fun QuizPage(onFinishQuiz: (Int) -> Unit) {
    val fruits = listOf(
        Pair(R.drawable.durian, "榴蓮"),
        Pair(R.drawable.apple, "蘋果"),
        Pair(R.drawable.avocado, "酪梨"),
        Pair(R.drawable.banana, "香蕉"),
        Pair(R.drawable.cantaloupe, "哈密瓜"),
        Pair(R.drawable.cherries, "櫻桃"),
        Pair(R.drawable.dragon, "火龍果"),
        Pair(R.drawable.grape, "葡萄"),
        Pair(R.drawable.grapefruit, "柚子"),
        Pair(R.drawable.lemon, "檸檬"),
        Pair(R.drawable.mango, "芒果"),
        Pair(R.drawable.orange, "橘子"),
        Pair(R.drawable.papaya, "木瓜"),
        Pair(R.drawable.passion, "百香果"),
        Pair(R.drawable.peach, "水蜜桃"),
        Pair(R.drawable.persimmon, "柿子"),
        Pair(R.drawable.pineapple, "鳳梨"),
        Pair(R.drawable.strawberry, "草莓"),
        Pair(R.drawable.watermelon, "西瓜"),
        Pair(R.drawable.tomato, "番茄")
    )

    val questions = remember { generateQuestions(fruits) }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf("") }
    val buttonColors = remember { mutableStateMapOf<String, Color>() }
    var score by remember { mutableStateOf(0) } // 分數追蹤

    val currentQuestion = questions[currentQuestionIndex]

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "${currentQuestionIndex + 1}/${questions.size}",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold  // 加粗題數
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = currentQuestion.imageId),
                contentDescription = "Fruit Image",
                modifier = Modifier.size(200.dp).padding(bottom = 20.dp)
            )

            currentQuestion.answers.forEach { answer ->
                val color = buttonColors[answer] ?: Color.Gray
                Button(
                    onClick = {
                        selectedAnswer = answer
                        buttonColors.clear()

                        currentQuestion.answers.forEach { option ->
                            buttonColors[option] = if (option == currentQuestion.correctAnswer) {
                                Color.Green
                            } else {
                                Color.Red
                            }
                        }

                        if (answer == currentQuestion.correctAnswer) {
                            score += 10
                        }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp),

                    colors = ButtonDefaults.buttonColors(containerColor = color)
                ) {
                    Text(answer, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                        selectedAnswer = ""
                        buttonColors.clear()
                    } else {
                        onFinishQuiz(score)
                    }
                },
                enabled = selectedAnswer.isNotEmpty()
            ) {
                Text(if (currentQuestionIndex < questions.size - 1) "下一頁" else "完成測驗")
            }
        }
    }
}


// 題目資料類別
data class Question(
    val imageId: Int, // 圖片資源 ID
    val correctAnswer: String, // 正確答案
    val answers: List<String> // 答案選項
)

// 生成隨機題目的函數
fun generateQuestions(fruits: List<Pair<Int, String>>): List<Question> {
    val questions = mutableListOf<Question>()

    repeat(10) {
        // 隨機選擇正確答案
        val correctAnswerIndex = Random.nextInt(fruits.size)
        val correctAnswer = fruits[correctAnswerIndex].second

        // 隨機選擇兩個錯誤答案
        val incorrectAnswers = fruits.filter { it.second != correctAnswer }
            .shuffled()
            .take(2)
            .map { it.second }

        // 將答案洗牌
        val answers = (incorrectAnswers + correctAnswer).shuffled()

        // 創建新的問題
        questions.add(
            Question(
                imageId = fruits[correctAnswerIndex].first,
                correctAnswer = correctAnswer,
                answers = answers
            )
        )
    }

    return questions
}

// 預覽函數
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Cc1204Theme {
        Start(m = Modifier) // 預覽主頁面
    }
}
