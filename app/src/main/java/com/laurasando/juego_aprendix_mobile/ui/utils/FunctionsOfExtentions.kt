import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.laurasando.juego_aprendix_mobile.R

fun View.showCustomSnackBar(message: String, iconResId: Int) {
    val snackbar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
    val snackbarView = snackbar.view

    // Inflar el diseño personalizado
    val customView = LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null)

    // Configurar el texto y el icono
    val textView = customView.findViewById<TextView>(R.id.snackbar_text)
    textView.text = message

    val imageView = customView.findViewById<ImageView>(R.id.snackbar_icon)
    imageView.setImageResource(iconResId)

    // Ajustar el layoutParams del Snackbar para añadir la vista personalizada
    val snackbarLayout = snackbarView as Snackbar.SnackbarLayout
    snackbarLayout.setPadding(0, 0, 0, 0)
    snackbarLayout.addView(customView, 0)

    snackbar.show()
}
