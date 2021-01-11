package mx.jrra.proyectofinal

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //Variable para la URI de la foto que tomo el usuario
    var FotoCap: Uri? = null
    //Variable que nos indicara si el usuario dio permiso o no
    private val Respuesta = 1001
    //Para la camara creamos un codigo
    private val RespuestaCamara = 1002
    private var bandera = 0
    private lateinit var imgBit: Bitmap
    private lateinit var imgBit2: Bitmap
    private lateinit var imgBit3: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Para el spinner
        val adaptador = ArrayAdapter.createFromResource(
            this, R.array.filtros_array,
            android.R.layout.simple_spinner_item
        )
        val opciones: Spinner = findViewById(R.id.filtros)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        opciones.adapter = adaptador
        //Funciones para cuando se pulsen los botones
        btngaleria()
        btnCamara()
        btnRegresar()
        //Funciones para el zoom
        acercar()
        alejar()
        //Funcion para guardar la imagen con el filtro puesto
        guardarIm()
        opciones.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    applicationContext,
                    "No se ha seleccionado ningun filtro",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val pos = parent.getItemAtPosition(position)
                //Du acuerdo a la seleccion del spinner se applicara un filtro y se volvera a ingresar al ImagenView, ya con el filtro
                if(pos.toString() == "Inversión o Negativo"){
                    imagen.setImageBitmap(Filtros.invertido(imgBit))
                }else if(pos.toString() == "Escala de grises"){
                    imagen.setImageBitmap(Filtros.escalagris(imgBit))
                }else if(pos.toString() == "Brillo"){
                    imagen.setImageBitmap(Filtros.brillo(imgBit, 100))
                }else if(pos.toString() == "Contraste"){
                    imagen.setImageBitmap(Filtros.contraste(imgBit, 100.0))
                }else if(pos.toString() == "Gamma"){
                    imagen.setImageBitmap(Filtros.gamma(imgBit, 5.0))
                }else if (pos.toString() == "Separacion de canal: Rojo"){
                    imagen.setImageBitmap(Filtros.rojo(imgBit))
                }else if(pos.toString() == "Separacion de canal: Verde"){
                    imagen.setImageBitmap(Filtros.verde(imgBit))
                }else if(pos.toString() == "Separacion de canal: Azul"){
                    imagen.setImageBitmap(Filtros.azul(imgBit))
                }else if(pos.toString() == "Smoothing"){
                    imagen.setImageBitmap(Filtros.Smoothing(imgBit, 1))
                }else if(pos.toString() == "Gaussian Blur"){
                    imagen.setImageBitmap(Filtros.gaussian(imgBit))
                }else if(pos.toString() == "Sharpen"){
                    imagen.setImageBitmap(Filtros.sharpen(imgBit))
                }else if(pos.toString() == "Mean Removal"){
                    imagen.setImageBitmap(Filtros.MeanRemoval(imgBit))
                }else if(pos.toString() == "Embossing"){
                    imagen.setImageBitmap(Filtros.embossing(imgBit))
                }else if(pos.toString() == "Edge Detection"){
                    imagen.setImageBitmap(Filtros.edgeDetection(imgBit))
                }else if(pos.toString() == "Bosquejo"){
                    imagen.setImageBitmap(Filtros.Bosquejo(imgBit))
                }else if(pos.toString() == "Aumento"){
                    imagen.setImageBitmap(Filtros.Aumento(imgBit, 1, 70f))
                }else if(pos.toString() == "Matiz"){
                    imagen.setImageBitmap(Filtros.matiz(imgBit, 180f))
                }else if(pos.toString() == "Sepia"){
                    imagen.setImageBitmap(Filtros.sepia(imgBit))
                }else if(pos.toString() == "Noise"){
                    imagen.setImageBitmap(Filtros.noise(imgBit))
                }

                imgBit2 = (imagen.getDrawable() as BitmapDrawable).bitmap
                imgBit3 = (imagen.getDrawable() as BitmapDrawable).bitmap
            }
        }
    }
    private fun btnRegresar(){
        //En caso de que se quiera volver a capturar una nueva imagen, este boton vuelve a mostrar las opciones
        //De galeria y opcion para tomar foto y hace que desaparezca los filtros
        regresar.setOnClickListener{
            lay1.setVisibility(View.VISIBLE)
            lay2.setVisibility(View.INVISIBLE)
        }
    }
    private fun guardarIm(){
        guardar.setOnClickListener {
            //Creamos una instancia de la clase Guardar, para enviarle la imagen a guardar en el dispositivo
            val dispositivo = Guardar()
            dispositivo.guardar(this, imgBit3)
        }
    }
    private fun acercar(){
        zoomIm.setOnClickListener {
            //Para el zoom enviamos la imagen y esta sera procesada por la funcion Zoom
            imagen.setImageBitmap(Filtros.zoom(imgBit3))
        }
    }
    private fun alejar(){
        alejar.setOnClickListener {
            //Para alejar la imagen mandamos llamar a la funcion alejar
            imgBit2 = (imagen.getDrawable() as BitmapDrawable).bitmap
            imagen.setImageBitmap(Filtros.alejar(imgBit2))
        }
    }
    private fun btngaleria(){
       botonGaleria.setOnClickListener {
           //Version de android instalada
           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
               //Ver si tiene permiso dicho dispositivo
               if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                   //Si no tiene permiso, necesitamos que el usuario de el permiso pertinente
                   //Realizamos lo siguiente
                   val permisoUsuario = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                   //Hacemos que la aplicacion haga el requerimiento de permiso
                   requestPermissions(permisoUsuario, Respuesta)
               }else{
                   galeria()
               }
            //En caso de que sea una version menor a Marsh
           }else{
               galeria()
           }
       }
    }
    //Funcion que hace lo necesario para tomar una imagen de la galeria
    private fun galeria(){
        //Para abrir la galeria primero cree un intent, que nos ayude
        //Para ese intent hacemos que nos deje seleccionar
        val galeria = Intent(Intent.ACTION_PICK)
        //Le decimos al intent que solo queremos seleccionar imagenes
        galeria.type = "image/*"
        //Para extraer la imagen en la que dio clic
        startActivityForResult(galeria, Respuesta)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            //Para ver cual fue el resultado
            Respuesta -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galeria()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No diste el permiso para acceder a tus imagenes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            //Depende de si el usuario dio o no permisos hacemos que realice la opcion de abrir camara
            //Pero si no dio el permiso decirle que no dio los permisos para poder acceder a su camara
            RespuestaCamara -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Camara()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No diste el permiso para acceder a tu Camara",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Para la imagen seleccionada, checamos si selecciono algo
        //Y si la peticion la realizo el oton de galeria
        if(resultCode == Activity.RESULT_OK && requestCode == Respuesta){
          //SI entra aquie es por que selecciono una foto
            //Entonces la imagen seleccionada la ponemos en el Image View que tenemos
            //Le damos la direccion con setImagenUri y con la infromacion que trae data que es quien trae la url de la imagen seleccionada
            imagen.setImageURI(data?.data)
            lay1.setVisibility(View.INVISIBLE)
            lay2.setVisibility(View.VISIBLE)
            imgBit = (imagen.getDrawable() as BitmapDrawable).bitmap
            imgBit2 = (imagen.getDrawable() as BitmapDrawable).bitmap
            imgBit3 = (imagen.getDrawable() as BitmapDrawable).bitmap
        }

        //Checamos que se haya tomado la foto y la peticion venga de RespuestaCamara
        if(resultCode == Activity.RESULT_OK && requestCode == RespuestaCamara){
            //Hice lo mismo que en la parte anterior, solo le damos el contenido a nuestra ImagenView
            imagen.setImageURI(FotoCap)
            lay1.setVisibility(View.INVISIBLE)
            lay2.setVisibility(View.VISIBLE)
            imgBit = (imagen.getDrawable() as BitmapDrawable).bitmap
            imgBit2 = (imagen.getDrawable() as BitmapDrawable).bitmap
            imgBit3 = (imagen.getDrawable() as BitmapDrawable).bitmap
        }
    }

    //Para el boton que toma la foto
    private fun btnCamara(){
        botonFoto.setOnClickListener {
            //Para la version de android y ver que permisos tiene
            //O si el usuario ha denegado los permisos
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {
                    //Si el usuario no ha dado permisos o no los tiene la aplicacion
                    //Solicitamos el permiso para acceder a la camara y tomar la foto, o sea que se guarde en la mameoria del telefono
                    val permisoUsuario = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //Hice una request para saber que permisos dio y que no
                    requestPermissions(permisoUsuario, RespuestaCamara)
                } else {
                    Camara()
                }
            } else {
                Camara()
            }
        }
    }

    //Para abrir la camara del telefono
    private fun Camara(){
      //Recuperar la foto tomada
        val foto = ContentValues()
        //Se le indica que debe agregar
        foto.put(MediaStore.Images.Media.TITLE, "Foto Capturada")
        //A la uri creada al principio le damos el contenido
        FotoCap = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, foto)
        //Cree un nuevo intent para abrir la camara, igual que en la funcion galeria, pero indicandole otra accion
        //En este caso que capture la imagen
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FotoCap)
        startActivityForResult(intent, RespuestaCamara)
    }
}