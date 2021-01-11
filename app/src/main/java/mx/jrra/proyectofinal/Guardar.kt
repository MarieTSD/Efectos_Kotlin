package mx.jrra.proyectofinal

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Guardar {
    //Para guardar las imagenes en el dispositivo, necesitamos crear las variables en donde se guardaran las rutas de las imagenes
    private val carpeta = "/appfiltros"
    private var contextoIntent: Context? = null
    //Funcion que nos permitira guardar la imagen
    fun guardar(context: Context, img: Bitmap) {
        contextoIntent = context
        //Con las variables creadas y el Enviroment creamos la ruta de la imagen
        val ruta: String = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + carpeta
        //Para que las imagenes se puedan diferenciar unas de otras, le agregamos la fecha en la que se guardo y asi se pueda
        //diferenciar de las demas
        val fechayhorai = fechayhora()
        //Comprabamos que exista la carpeta
        val direccion = File(ruta)
        if (!direccion.exists()) {
            direccion.mkdirs()
        }
        //Creamos un archivo temporal para guardar la imagen
        val archivoImg = File(direccion, "$carpeta$fechayhorai.jpg")
        try {
            val imgguardar = FileOutputStream(archivoImg)
            //Guardamos la imagen como tipo JPEG
            img.compress(Bitmap.CompressFormat.JPEG, 85, imgguardar)
            imgguardar.flush()
            imgguardar.close()
            confirmacion(archivoImg)
            Toast.makeText(contextoIntent, "¡IMAGEN GUARDADA EN EL DISPOSITIVO", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Toast.makeText(contextoIntent, "¡ERROR! No se guardo la imagen", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(contextoIntent, "¡ERROR! No se guardo la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmacion(file: File) {
        //Para guardar tenemos que ver si el archivo se guardo correctamente en la galeria
        MediaScannerConnection.scanFile(
            contextoIntent, arrayOf(file.toString()), null
        ) { path, uri -> }
    }

    private fun fechayhora(): String {
        //Obtenemos la fecha actual y la enviamos como string
        val calenadrio: Calendar = Calendar.getInstance()
        val formato = SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss")
        return formato.format(calenadrio.getTime())
    }

}