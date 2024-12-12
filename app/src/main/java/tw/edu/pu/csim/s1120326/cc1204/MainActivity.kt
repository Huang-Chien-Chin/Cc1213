package tw.edu.pu.csim.s1120326.cc1204

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun Start(m: Modifier) {
    var showQuizPage by remember { mutableStateOf(false) }
    var showLearningPage by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }  // Control background expansion
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray) // Default background color
    ) {
        // Full-screen background image
        Image(
            painter = painterResource(id = R.drawable.background), // Replace with your background resource
            contentDescription = "背景圖",
            contentScale = if (expanded) ContentScale.Crop else ContentScale.FillBounds, // Full-screen display mode
            modifier = Modifier.fillMaxSize() // Fill the entire screen
        )
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    )
    {
        // If showQuizPage is true, display the quiz page
        if (showQuizPage) {
            QuizPage()
        }
        // If showLearningPage is true, display the learning page
        else if (showLearningPage) {
            LearningPage()
        } else {
            // Main screen with two buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = m
            ) {
                Button(
                    onClick = {
                        showQuizPage = true // Show quiz page
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("測驗")
                }

                Button(
                    onClick = {
                        showLearningPage = true // Show learning page
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("學習")

                }
            }
        }
    }
}

@Composable
fun LearningPage() {
    var expanded by remember { mutableStateOf(false) }  // Control background expansion

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray) // Default background color
    ) {
        // Full-screen background image
        Image(
            painter = painterResource(id = R.drawable.cnmb), // Replace with your background resource
            contentDescription = "背景圖",
            contentScale = if (expanded) ContentScale.Crop else ContentScale.FillBounds, // Full-screen display mode
            modifier = Modifier.fillMaxSize() // Fill the entire screen
        )
    }

    // Fruit images and corresponding audio resources
    val fruit = listOf(
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
        Triple(R.drawable.tomato, listOf(R.raw.tomatoc, R.raw.tomatoe, R.raw.tomatot), "番茄"),
    )
    val success = Triple(R.drawable.success, listOf(R.raw.success), "完成") // Handle success separately
    val context = LocalContext.current
    val allItems = fruit + success
    var currentIndex by remember { mutableStateOf(0) } // Current page index

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Current fruit data
            val currentFruit = allItems[currentIndex]
            val languages = if (currentFruit == success) listOf("完成") else listOf("中文", "英文", "台語")

            // Button area
            languages.forEachIndexed { langIndex, language ->
                Button(
                    onClick = {
                        val mediaPlayer = MediaPlayer.create(
                            context,
                            currentFruit.second.getOrElse(langIndex) { currentFruit.second[0] }
                        )
                        mediaPlayer.start()
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

            // Image of current fruit
            Image(
                painter = painterResource(id = currentFruit.first),
                contentDescription = "${currentFruit.third} 圖片",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 20.dp)
            )
        }

        // Page navigation buttons
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (currentIndex > 0) currentIndex--
                },
                enabled = currentIndex > 0
            ) {
                Text("上一個")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (currentIndex < allItems.size - 1) currentIndex++
                },
                enabled = currentIndex < allItems.size - 1
            ) {
                Text("下一個")
            }
        }
    }
}

@Composable
fun Quiz(m: Modifier) {
    var showQuizPage by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.LightGray)
    ) {
        // Main screen with buttons
        if (showQuizPage) {
            QuizPage()
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = m
            ) {
                Button(
                    onClick = { showQuizPage = true },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("開始測驗")
                }
            }
        }
    }
}
@Composable
fun QuizPage() {
    // List of fruits and their images
    val fruits = listOf(
        Pair(R.drawable.durian,"榴蓮"),
        Pair(R.drawable.apple,"蘋果"),
        Pair(R.drawable.avocado,"酪梨"),
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

    val questions = generateQuestions(fruits)

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Gray) }

    val context = LocalContext.current

    // Current question data
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
            // Show the current image
            Image(
                painter = painterResource(id = currentQuestion.imageId),
                contentDescription = "Fruit Image",
                modifier = Modifier.size(200.dp).padding(bottom = 20.dp)
            )

            // Display the fruit name buttons
            currentQuestion.answers.forEach { answer ->
                Button(
                    onClick = {
                        selectedAnswer = answer
                        buttonColor = if (answer == currentQuestion.correctAnswer) Color.Green else Color.Red
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text(
                        text = answer,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                }
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth() // 确保 Row 填满宽度
                    .padding(16.dp), // 设置 Row 的 padding
                horizontalArrangement = Arrangement.SpaceBetween, // 在水平方向上均匀分布按钮
                verticalAlignment = Alignment.CenterVertically // 垂直方向居中对齐
            ) {

            Button(
                    onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            selectedAnswer = ""
                            buttonColor = Color.Gray
                        }
                    },
                    enabled = selectedAnswer.isNotEmpty()
                ) {
                    Text("下一頁")
                }
            }
        }
    }
}


data class Question(
    val imageId: Int,
    val correctAnswer: String,
    val answers: List<String>
)

fun generateQuestions(fruits: List<Pair<Int, String>>): List<Question> {
    val questions = mutableListOf<Question>()

    repeat(10) {
        // Randomly choose a correct answer
        val correctAnswerIndex = Random.nextInt(fruits.size)
        val correctAnswer = fruits[correctAnswerIndex].second

        // Randomly select two other incorrect answers
        val incorrectAnswers = fruits.filter { it.second != correctAnswer }
            .shuffled()
            .take(2)
            .map { it.second }

        // Shuffle the answers
        val answers = (incorrectAnswers + correctAnswer).shuffled()

        // Create a new question
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Cc1204Theme {
        Start(m = Modifier)
    }
}