package app.doggy.androidquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //問題文の配列。
    val questions: Array<String> = arrayOf(
        "Androidアプリを開発するときのプログラミング言語は何でしょう？",
        "Androidアプリを開発するときのソフトは何でしょう？",
        "画像を表示させるViewは何でしょう？",
        "文字を表示させるViewは何でしょう？",
        "値を変えられる変数を宣言するときに使う命令は何でしょう？",
        "文字列を扱うクラスは何でしょう？",
        "View間の制約をもとにViewを配置していくレイアウトは何でしょう？",
        "変数以外で、情報をAndroid端末に保存する仕組みは何でしょう？",
        "文字を入力することができるViewは何でしょう？",
        "LinearLayoutで縦方向にViewを並べるとき、orientationアトリビュートの値は何でしょう？"
    )

    //解答の配列。
    val answers: Array<String> = arrayOf(
        "Kotlin",
        "AndroidStudio",
        "ImageView",
        "TextView",
        "var",
        "String",
        "ConstraintLayout",
        "SharedPreferences",
        "EditText",
        "vertical",
    )

    //出題順を決める配列。
    val questionOrder: Array<Int?> = arrayOfNulls<Int>(questions.size)

    //今何問目かを管理する変数。
    var status = 0

    //正解数を管理する変数。
    var correctCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //questionOrderに初期値を代入する。
        for (i in questionOrder.indices) {
            questionOrder[i] = i
        }

        //ボタンをタップした時の処理。
        answerButton.setOnClickListener {

            //正誤判定
            if (answerTextView.text.toString() == answers[questionOrder[status-1] as Int]) {
                correctCount += 1
            }

            when(status) {
                in 1 until questions.size -> {

                    //次の問題を表示。
                    setQuestion()

                    //解答欄を空白にする。
                    answerTextView.setText("")

                }

                questions.size -> {

                    //画面遷移の準備。
                    val intent = Intent(applicationContext, ResultActivity::class.java)

                    //正解数を渡す準備。
                    intent.putExtra("correctCount", correctCount)

                    //問題を渡す準備。
                    intent.putExtra("questions", questions)

                    //解答を渡す準備。
                    intent.putExtra("answers", answers)

                    //出題順を決める配列を渡す準備。
                    intent.putExtra("questionOrder", questionOrder)

                    //結果を表示。
                    startActivity(intent)

                    //初期化。
                    status = 0
                    correctCount = 0
                    answerTextView.setText("")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //問題をシャッフルする。
        questionOrder.shuffle()

        //問題を表示。
        setQuestion()
    }

    //問題を表示する処理。
    fun setQuestion() {
        //次の問題を表示。
        questionTextView.text = questions[questionOrder[status] as Int]
        status += 1
        statusTextView.text = "問題$status/${questions.size}"
    }
}