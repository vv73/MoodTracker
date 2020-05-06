package study.android.moodtracker

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mood_moderate.setOnClickListener { registerMood(it) }
        mood_good.setOnClickListener { registerMood(it) }
        mood_bad.setOnClickListener { registerMood(it) }
    }

    fun registerMood(view: View) { // Создаём объект для добавления в базу данных
        val mood = Mood()
        when (view) {
            mood_moderate -> mood.mood = Mood.MODERATE
            mood_good -> mood.mood = Mood.GOOD
            mood_bad -> mood.mood = Mood.BAD
        }
        addMood(mood)
    }

    /** Создает и добавляет в базу объект настроения  */
    fun addMood(m: Mood) {
        // Создаём объект для добавления в базу данных
        // Выставляем текущее время
        m.timestamp = System.currentTimeMillis()
        // Вставляем нашу запись в базу данных без координат сохраняем id
        val id = DBManager.getInstance(this).insert(m)
        getLocationAndUpdate(m, id)
    }

    // declare a global variable of FusedLocationProviderClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun getLocationAndUpdate(mood: Mood, id: Long) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    // Добавляем к настроению координаты.
                    // И записываем в базу
                    mood.latitude = location.getLatitude();
                    mood.longitude = location.getLongitude();
                    DBManager.getInstance(this).update(mood, id);
                    val myLocation = Geocoder(this, Locale.getDefault())
                    val myList: List<Address> =
                        myLocation.getFromLocation(location.latitude, location.longitude, 1)
                    val address: Address = myList[0]
                    val addressStr :String = address.getAddressLine(0).toString() + ", "

                    Toast.makeText(
                        this,
                        "Гелокация к последней записи добавлена, похоже вы здесь: " + addressStr,
                        Toast.LENGTH_SHORT
                    ).show();
                }
                else{
                    Toast.makeText(
                        this,
                        "Не удалось получить данные геолокации",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Не удалось получить данные геолокации" + it.message,
                    Toast.LENGTH_SHORT
                ).show();
            }
    }
}
