package app.doggy.androidquiz

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    //ランキングのキーの配列。
    val rankingKeys: Array<String> = arrayOf("5位", "4位", "3位", "2位", "1位")

    //ランキングを更新するための変数。
    var rankingUpdate = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        /*
        今回の結果とランキングの表示。
         */

        //ランキングを記録するSharedPreferencesをインスタンス化。
        val rankingStore: SharedPreferences = getSharedPreferences("RankingStore", Context.MODE_PRIVATE)

        //書き込みを行う処理を変数に代入。
        val rankingEditor = rankingStore.edit()

        //正解数を受け取る。
        val correctCount = intent.getIntExtra("correctCount", 0)

        //正解数を表示する。
        answerDisplay.text = correctCount.toString()

        //ランキングを表示するTextViewの配列。
        val rankingTextViews: Array<TextView> = arrayOf(fifthPlace, forthPlace, thirdPlace, secondPlace, firstPlace)

        //今までのランキングを表示する。
        for (i in rankingTextViews.indices) {
            //更新したランキングを表示。
            rankingTextViews[i].text = "${rankingStore.getInt(rankingKeys[i], 0)}問"
        }

        //今回の順位を判定する処理。
        if (correctCount >= rankingStore.getInt(rankingKeys[4], 0)) {
            rankingUpdate(4, correctCount, rankingStore, rankingEditor, rankingTextViews)

        } else if (correctCount >= rankingStore.getInt(rankingKeys[3], 0)) {
            rankingUpdate(3, correctCount, rankingStore, rankingEditor, rankingTextViews)

        } else if (correctCount >= rankingStore.getInt(rankingKeys[2], 0)) {
            rankingUpdate(2, correctCount, rankingStore, rankingEditor, rankingTextViews)

        } else if (correctCount >= rankingStore.getInt(rankingKeys[1], 0)) {
            rankingUpdate(1, correctCount, rankingStore, rankingEditor, rankingTextViews)

        } else if (correctCount >= rankingStore.getInt(rankingKeys[0], 0)) {
            rankingUpdate(0, correctCount, rankingStore, rankingEditor, rankingTextViews)

        } else {
            //今回の順位を表示する。
            rankingDisplay.text = "圏外..."

        }

        /*
        問題と解答の表示。
         */

        //問題を受け取る。
        val questions = intent.getStringArrayExtra("questions")

        //解答を受け取る。
        val answers = intent.getStringArrayExtra("answers")

        //出題順を決める配列を受け取る。
        val questionOrder = intent.getIntArrayExtra("questionOrder")

        //RecyclerViewを使う。

    }

    //順位を反映する処理。
    fun rankingUpdate(index: Int,
                      correctCount: Int,
                      rankingStore: SharedPreferences,
                      rankingEditor: SharedPreferences.Editor,
                      rankingTextViews: Array<TextView>) {

        //保存されているランキングの値を更新する。
        for (i in 0 until index) {

            //値の更新。
            rankingUpdate = rankingStore.getInt(rankingKeys[i+1], 0)
            rankingEditor.putInt(rankingKeys[i], rankingUpdate)
            rankingEditor.commit()

            //更新したランキングを表示。
            rankingTextViews[i].text = "${rankingUpdate}問"
        }

        //今回の順位を記録する。
        rankingEditor.putInt(rankingKeys[index], correctCount)
        rankingEditor.commit()

        //今回の順位をランキングに表示。
        rankingTextViews[index].text = "あなた(${correctCount}問)"

        //今回の順位を表示する。
        rankingDisplay.text = rankingKeys[index]

    }
}