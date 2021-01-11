package mx.jrra.proyectofinal

import android.graphics.Bitmap
import android.graphics.Color
class ConvolutionMatrix(size: Int) {
    var Matrix: Array<DoubleArray>
    var Factor = 1.0
    var Offset = 1.0
    fun setAll(value: Double) {
        for (x in 0 until SIZE) {
            for (y in 0 until SIZE) {
                Matrix[x][y] = value
            }
        }
    }

    fun applyConfig(config: Array<DoubleArray>) {
        for (x in 0 until SIZE) {
            for (y in 0 until SIZE) {
                Matrix[x][y] = config[x][y]
            }
        }
    }

    companion object {
        const val SIZE = 3
        fun computeConvolution3x3(bit: Bitmap, matrix: ConvolutionMatrix): Bitmap {
            //Obtenemos los valores del bitmap que se envio, tanto el ancho como el alto.
            var bitmap = bit
            val ancho = bitmap.width
            val alto = bitmap.height
            //Creamos un nuevo bitmap el cual nos servira para poder hacer los cambios necesarios
            val nuevo = Bitmap.createBitmap(ancho,alto, bitmap.config)
            //Declaramos las variables que nos ayudaran a ver y a procesar la imagen para el color
            var a: Int
            var r: Int
            var g: Int
            var b: Int
            //Variables para ver la intensidad de cada elemento
            var sumaR: Int
            var sumaG: Int
            var sumaB: Int
            val pixeles = Array(SIZE) { IntArray(SIZE) }
            for (y in 0 until alto - 2) {
                for (x in 0 until ancho - 2) {
                    for (i in 0 until SIZE) {
                        for (j in 0 until SIZE) {
                            pixeles[i][j] = bitmap.getPixel(x + i, y + j)
                        }
                    }

                    //Con la variable que se declaro anteriormente sacaamos el alfa
                    a= Color.alpha(pixeles[1][1])
                    sumaB = 0
                    sumaG = sumaB
                    sumaR = sumaG
                    // Con las variables de la suma y con ayuda de un for vamos creando el nuevo resultado del bitmap
                    for (i in 0 until SIZE) {
                        for (j in 0 until SIZE) {
                            sumaR += (Color.red(pixeles[i][j]) * matrix.Matrix[i][j]).toInt()
                            sumaG += (Color.green(pixeles[i][j]) * matrix.Matrix[i][j]).toInt()
                            sumaB += (Color.blue(pixeles[i][j]) * matrix.Matrix[i][j]).toInt()
                        }
                    }

                    //Realizamos varias condicionales para dalre el valor a RGB
                    //Empezando por el rojo
                    r = (sumaR / matrix.Factor + matrix.Offset).toInt()
                    if (r < 0) {
                        r = 0
                    } else if (r > 255) {
                        r = 255
                    }
                    // Para el color verde, también realizamos lo mismo que para el rojo
                    g = (sumaG / matrix.Factor + matrix.Offset).toInt()
                    if (g < 0) {
                        g = 0
                    } else if (g > 255) {
                        g = 255
                    }

                    // Y por ultimo realizamos lo mismo para el azul
                    b = (sumaB / matrix.Factor + matrix.Offset).toInt()
                    if (b < 0) {
                        b = 0
                    } else if (b > 255) {
                        b = 255
                    }

                    //Para el resultado final, nos ayudamos con el metodo setPixeles
                    //Y le enviamos las variables con las que hemos estado trabajando
                    nuevo.setPixel(x + 1, y + 1, Color.argb(a, r, g, b))
                }
            }
            //Retornamos el resultado final
            return nuevo
        }
    }

    init {
        Matrix = Array(size) { DoubleArray(size) }
    }
}