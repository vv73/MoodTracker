package study.android.moodtracker


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stat.*


class StatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat)

        val dbManager = DBManager.getInstance(this)

        val bad = dbManager.moods(Mood.BAD)
        val moderate = dbManager.moods(Mood.MODERATE)
        val good = dbManager.moods(Mood.GOOD)

        badPercent.append(""+ bad)
        moderatePercent.append("" + moderate)
        goodPercent.append("" + good)
    }
}
