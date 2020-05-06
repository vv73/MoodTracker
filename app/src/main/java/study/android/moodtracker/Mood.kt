package study.android.moodtracker

class Mood {
    var mood = 0
    var timestamp: Long = 0
    var latitude = 0.0
    var longitude = 0.0
 companion object {
     /** Хорошее настроение  */
     val GOOD = 0
     /** Нормальное настроение  */
     val MODERATE = 1
     /** Плохое настроение  */
     val BAD = 2
 }
}
