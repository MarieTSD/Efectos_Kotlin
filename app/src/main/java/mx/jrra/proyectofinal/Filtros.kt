package mx.jrra.proyectofinal

import android.graphics.*
import java.util.*
import kotlin.math.min
import kotlin.math.pow


object Filtros {

    //Para el filtro gamma creamos una funcion como la siguiente
    fun gamma(bit: Bitmap, color: Double): Bitmap {
        //Creamos las variables para darle un color y manejar a traves de RGB
        val verde: Double = (color + 2) / 10.0
        val rojo: Double = (color + 2) / 10.0
        val azul: Double = (color + 2) / 10.0
        // Creamos un nuevo bitmap con el cual procesaremos los cambios
        //para eso con sacamos las medidas del bitmao que enviamos en el MainActivity
        val resultado = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), bit.getConfig())
        // Obtenemos el alto y ancho del bitmap que recibimos
        val ancho: Int = bit.getWidth()
        val alto: Int = bit.getHeight()
        //Declaramos las variables que nos ayudaran a gurdar el valor para cada uno de los valores
        //de rojo, verde y azul RGB
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        //Variable que nos ayuda para darle valor al cada uno de los pixeles del nuevo bitmap
        var pixel: Int
        val MAX_SIZE = 256
        val MAX_VALUE_DBL = 255.0
        val MAX_VALUE_INT = 255
        val REVERSE = 1.0

        val gamma1 = IntArray(MAX_SIZE)
        val gamma2 = IntArray(MAX_SIZE)
        val gamma3 = IntArray(MAX_SIZE)

        for (i in 0 until MAX_SIZE) {
            gamma1[i] = min(
                MAX_VALUE_INT,(MAX_VALUE_DBL * (i / MAX_VALUE_DBL).pow(REVERSE / rojo) + 0.5).toInt()
            )
            gamma2[i] = min(
                MAX_VALUE_INT,(MAX_VALUE_DBL * (i / MAX_VALUE_DBL).pow(REVERSE / verde) + 0.5).toInt()
            )
            gamma3[i] = min(
                MAX_VALUE_INT, (MAX_VALUE_DBL * (i / MAX_VALUE_DBL).pow(REVERSE / azul) + 0.5).toInt()
            )
        }

        //Aplicando los valores a las variables que declaramos al principio
        for (x in 0 until ancho) {
            for (y in 0 until alto) {
                //Aplicaremos la opcion pixel por pixel, por eso nos ayufamos del metodo getPisel
                    //Para obtener cada uno de los pixeles del bitmap que recibe la funcion
                pixel = bit.getPixel(x, y)
                a = Color.alpha(pixel)
                // look up gamma
                r = gamma1[Color.red(pixel)]
                g = gamma2[Color.green(pixel)]
                b = gamma3[Color.blue(pixel)]
                // Los valores que guardamos en cada variable para RGB, ahora se los iremos agregando
                //Al nuevo bitmap
                resultado.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }
        // Retornamos el resultado final
        return resultado
    }

    fun contraste(bit: Bitmap, valor: Double): Bitmap {
        // Sacamos todos los datos del bitmap que recibimos para crear uno nuevo y asi poder procesar el filtro
        var bitmap = bit
        val ancho = bitmap.width
        val alto = bitmap.height
        // Creamos un nuevo bitmap con los datos del bitmap que recibimos, para poder aplicar las operaciones para el filtro
        val resultado = Bitmap.createBitmap(ancho, alto, bitmap.config)
        //Variables para realizar las opreaciones de color
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        var pixel: Int = 0
        var contraste = (100.0 + 50.0) / 100.0
        contraste *= contraste
        // Apliamos las operaciones de color pixel por pixel y lo asignamos al nuevo bitmap
        for (x in 0 until ancho) {
            for (y in 0 until alto) {
                pixel = bitmap.getPixel(x, y)
                a = Color.alpha(pixel)
                //Aplicamos el color y condiciones necesarias para el color rojo, verde y azul RGB
                r = Color.red(pixel)
                r = (((r / 255.0 - 0.5) * contraste + 0.5) * 255.0).toInt()
                if (r < 0) {
                    r = 0
                } else if (r > 255) {
                    r = 255
                }
                g = Color.green(pixel)
                g = (((g / 255.0 - 0.5) * contraste + 0.5) * 255.0).toInt()
                if (g < 0) {
                    g = 0
                } else if (g > 255) {
                    g = 255
                }
                b = Color.blue(pixel)
                b = (((b / 255.0 - 0.5) * contraste + 0.5) * 255.0).toInt()
                if (b < 0) {
                    b = 0
                } else if (b > 255) {
                    b = 255
                }
                //Aplicamos los resultados de cada pixel al nuevo bitmap
                resultado.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }
        return resultado
    }

    fun invertido(bitmap: Bitmap): Bitmap {
        var bitmap2 = bitmap
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        val alto = bitmap2.height
        val ancho = bitmap2.width
        var pixel: Int
        //Creamos un nuevo bitmap
        val resultado = Bitmap.createBitmap(bitmap2.width, bitmap.height, bitmap2.config)

        for (y in 0 until alto) {
            for (x in 0 until ancho) {
                //Dandole los colores con RGB
                pixel = bitmap2.getPixel(x, y)
                a = Color.alpha(pixel)
                r = 255 - Color.red(pixel)
                g = 255 - Color.green(pixel)
                b = 255 - Color.blue(pixel)
                resultado.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }
        return resultado
    }

    fun rojo(bitmap: Bitmap): Bitmap {
        var bitmap2 = bitmap
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        val alto = bitmap2.height
        val ancho = bitmap2.width
        var pixel: Int
        //Creamos un nuevo bitmap
        val resultado = Bitmap.createBitmap(bitmap2.width, bitmap.height, bitmap2.config)

        for (y in 0 until alto) {
            for (x in 0 until ancho) {
                //Dandole los colores con RGB
                pixel = bitmap2.getPixel(x, y)
                a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = 0
                b = 0
                //Las operaciones hechas las damos a cada pixel
                resultado.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }
        return resultado
    }

    fun azul(bitmap: Bitmap): Bitmap {
        var bitmap2 = bitmap
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        val alto = bitmap2.height
        val ancho = bitmap2.width
        var pixel: Int
        //Creamos un nuevo bitmap
        val resultado = Bitmap.createBitmap(bitmap2.width, bitmap.height, bitmap2.config)

        for (y in 0 until alto) {
            for (x in 0 until ancho) {
                //Dandole los colores con RGB
                pixel = bitmap2.getPixel(x, y)
                a = Color.alpha(pixel)
                r = 0
                g = 0
                b = Color.blue(pixel)
                resultado.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }
        return resultado
    }

    fun verde(bitmap: Bitmap): Bitmap {
        var bitmap2 = bitmap
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        val alto = bitmap2.height
        val ancho = bitmap2.width
        var pixel: Int
        //Creamos un nuevo bitmap
        val resultado = Bitmap.createBitmap(bitmap2.width, bitmap.height, bitmap2.config)

        for (y in 0 until alto) {
            for (x in 0 until ancho) {
                //Dandole los colores con RGB
                pixel = bitmap2.getPixel(x, y)
                a = Color.alpha(pixel)
                r = 0
                g = Color.green(pixel)
                b = 0
                resultado.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }
        return resultado
    }

    fun brillo(imagenB: Bitmap, valor: Int): Bitmap {
        // Tamaño de la imagen
        var imagen = imagenB
        val ancho = imagen.width
        val alto = imagen.height
        //Crear el bitmap a enviar
        val salida = Bitmap.createBitmap(ancho, alto, imagen.config)
        // Color rgb
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        var pixel: Int
        // Darle valor Pixel por pixel
        for (x in 0 until ancho) {
            for (y in 0 until alto) {
                // get pixel color
                pixel = imagen.getPixel(x, y)
                a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)
                //Realizamos las condiciones para darle el valor a la variable Rojo, verde y azul
                r += valor
                if (r > 255) {
                    r = 255
                } else if (r < 0) {
                    r = 0
                }
                g += valor
                if (g > 255) {
                    g = 255
                } else if (g < 0) {
                    g = 0
                }
                b += valor
                if (b > 255) {
                    b = 255
                } else if (b < 0) {
                    b = 0
                }
                salida.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }
        return salida
    }

    fun escalagris(imagenB: Bitmap): Bitmap {
        //Arreglo, en lugar del for
        var imagen = imagenB
        val colorgris = floatArrayOf(
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        )
        val colorMatrixGray = ColorMatrix(colorgris)
        val w = imagen.width
        val h = imagen.height
        val bitmapResultado = Bitmap
            .createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvasResult = Canvas(bitmapResultado)
        val paint = Paint()
        val filter = ColorMatrixColorFilter(colorMatrixGray)
        paint.colorFilter = filter
        canvasResult.drawBitmap(imagen, 0f, 0f, paint)
        return bitmapResultado
    }

    //funciones para los filtros de convolucion
    var size = 3

    fun Smoothing(b: Bitmap, n: Int): Bitmap {
        val m = ConvolutionMatrix(3)
        m.setAll(1.0)
        m.Offset = n + 0.0
        m.Factor = n + 8.0
        return ConvolutionMatrix.computeConvolution3x3(b, m)
    }

    fun gaussian(bitmap: Bitmap): Bitmap {
        val GaussianBlurConfig = arrayOf(
            doubleArrayOf(1.0, 2.0, 1.0),
            doubleArrayOf(2.0, 4.0, 2.0),
            doubleArrayOf(1.0, 2.0, 1.0)
        )
        val matriz = applyConfig(GaussianBlurConfig)
        return computeConvolution3x3(bitmap, GaussianBlurConfig, 16.0, 0.0)
    }

    fun sharpen(bitmp: Bitmap): Bitmap {
        val arregloSharpen = arrayOf(
            doubleArrayOf(0.0, -2.0, 0.0),
            doubleArrayOf(-2.0, 11.0, -2.0),
            doubleArrayOf(0.0, -2.0, 0.0)
        )
        val matriz = applyConfig(arregloSharpen)
        return computeConvolution3x3(bitmp, matriz, 3.0, 0.0)
    }

    fun MeanRemoval(bit: Bitmap): Bitmap {
        val arregloMeanRemoval = arrayOf(doubleArrayOf(-1.0, -1.0, -1.0), doubleArrayOf(-1.0, 9.0, -1.0), doubleArrayOf(-1.0, -1.0, -1.0))
        val matriz = applyConfig(arregloMeanRemoval)
        return computeConvolution3x3(bit, matriz, 1.0, 0.0)
    }

    fun embossing(bit: Bitmap): Bitmap {
        val arregloembossing = arrayOf(doubleArrayOf(-1.0, 0.0, -1.0), doubleArrayOf(0.0, 4.0, 0.0), doubleArrayOf(-1.0, 0.0, -1.0))
        val matriz = applyConfig(arregloembossing)
        return computeConvolution3x3(bit, matriz, 1.0, 127.0)
    }

    fun edgeDetection(bit: Bitmap): Bitmap {
        val arregloedgeDetection = arrayOf(
            doubleArrayOf(1.0, 1.0, 1.0),
            doubleArrayOf(0.0, 0.0, 0.0),
            doubleArrayOf(-1.0, -1.0, -1.0)
        )
        val matriz = applyConfig(arregloedgeDetection)
        return computeConvolution3x3(bit, matriz, 1.0, 127.0)
    }


    fun applyConfig(config: Array<DoubleArray>): Array<DoubleArray> {
        val matriz = Array(size) { DoubleArray(size) }
        for (x in 0 until ConvolutionMatrix.SIZE) {
            for (y in 0 until ConvolutionMatrix.SIZE) {
                matriz[x][y] = config[x][y]
            }
        }
        return matriz
    }

    const val SIZE = 3
    fun computeConvolution3x3(
        bit: Bitmap,
        Matrix: Array<DoubleArray>,
        Factor: Double,
        Offset: Double
    ): Bitmap {
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
        val pixeles = Array(ConvolutionMatrix.SIZE) { IntArray(ConvolutionMatrix.SIZE) }
        for (y in 0 until alto - 2) {
            for (x in 0 until ancho - 2) {
                for (i in 0 until ConvolutionMatrix.SIZE) {
                    for (j in 0 until ConvolutionMatrix.SIZE) {
                        pixeles[i][j] = bitmap.getPixel(x + i, y + j)
                    }
                }
                //Con la variable que se declaro anteriormente sacaamos el alfa
                a= Color.alpha(pixeles[1][1])
                sumaB = 0
                sumaG = sumaB
                sumaR = sumaG
                // Con las variables de la suma y con ayuda de un for vamos creando el nuevo resultado del bitmap
                for (i in 0 until ConvolutionMatrix.SIZE) {
                    for (j in 0 until ConvolutionMatrix.SIZE) {
                        sumaR += (Color.red(pixeles[i][j]) * Matrix[i][j]).toInt()
                        sumaG += (Color.green(pixeles[i][j]) * Matrix[i][j]).toInt()
                        sumaB += (Color.blue(pixeles[i][j]) * Matrix[i][j]).toInt()
                    }
                }
                //Realizamos varias condicionales para dalre el valor a RGB
                //Empezando por el rojo
                r = (sumaR / Factor + Offset).toInt()
                if (r < 0) {
                    r = 0
                } else if (r > 255) {
                    r = 255
                }
                // Para el color verde, también realizamos lo mismo que para el rojo
                g = (sumaG / Factor + Offset).toInt()
                if (g < 0) {
                    g = 0
                } else if (g > 255) {
                    g = 255
                }
                // Y por ultimo realizamos lo mismo para el azul
                b = (sumaB / Factor + Offset).toInt()
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


    fun Aumento(bit: Bitmap, tipo: Int, porcentaje: Float): Bitmap {
        var bitmap = bit
        var porcentaje = porcentaje
        porcentaje /= 100
        //Sacamos los valores del bitmap que recibimos
        val ancho = bitmap.width
        val alto = bitmap.height
        val resultado = Bitmap.createBitmap(ancho, alto, bitmap.config)
        //Declaramos las variables a utilizar para realizar las operaciones de color
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        var pixel: Int
        //Recorremos pixel por pixel el bitmap
        for (x in 0 until ancho) {
            for (y in 0 until alto) {
                pixel = bitmap.getPixel(x, y)
                a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)
                if (tipo == 1) {
                    r = (r * (1 + porcentaje)).toInt()
                    if (r > 255) r = 255
                } else if (tipo == 2) {
                    g = (g * (1 + porcentaje)).toInt()
                    if (g > 255) g = 255
                } else if (tipo == 3) {
                    b = (b * (1 + porcentaje)).toInt()
                    if (b > 255) b = 255
                }
                resultado.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }

        return resultado
    }

    fun Bosquejo(bit: Bitmap): Bitmap {
        var bitmap = bit
        //Tipo de bosquejo
        val tipo = 6
        //Limite
        val limite = 130
        val ancho = bitmap.width
        val alto = bitmap.height
        //Crear un nuevo bitmap para poder procesar las operaciones
        val resultado = Bitmap.createBitmap(ancho, alto, bitmap.config)
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        //Variables para sumar todas las operaciones de cada tono de color
        var sumaR: Int
        var sumaG: Int
        var sumaB: Int
        val pixeles = Array(3) {
            IntArray(3)
        }
        for (y in 0 until alto - 2) {
            for (x in 0 until ancho - 2) {
                for (i in 0..2) {
                    for (j in 0..2) {
                        pixeles[i][j] = bitmap.getPixel(x + i, y + j)
                    }
                }
                a = Color.alpha(pixeles[1][1])
                //
                sumaB = 0
                sumaG = sumaB
                sumaR = sumaG
                sumaR = tipo * Color.red(pixeles[1][1]) - Color.red(pixeles[0][0]) - Color.red(pixeles[0][2]) - Color.red(
                        pixeles[2][0]
                    ) - Color.red(pixeles[2][2])
                sumaG = tipo * Color.green(pixeles[1][1]) - Color.green(pixeles[0][0]) - Color.green(
                    pixeles[0][2]
                ) - Color.green(pixeles[2][0]) - Color.green(pixeles[2][2])
                sumaB = tipo * Color.blue(pixeles[1][1]) - Color.blue(pixeles[0][0]) - Color.blue(pixeles[0][2]) - Color.blue(
                        pixeles[2][0]
                    ) - Color.blue(pixeles[2][2])
                r = (sumaR + limite)
                if (r < 0) {
                    r = 0
                } else if (r > 255) {
                    r = 255
                }
                g = (sumaG + limite)
                if (g < 0) {
                    g = 0
                } else if (g > 255) {
                    g = 255
                }
                b = (sumaB + limite)
                if (b < 0) {
                    b = 0
                } else if (b > 255) {
                    b = 255
                }
                resultado.setPixel(x + 1, y + 1, Color.argb(a, r, g, b))
            }
        }

        return resultado
    }

    fun matiz(bitmap: Bitmap, matiz: Float): Bitmap {
        var bitmap = bitmap
        val resultado = bitmap.copy(bitmap.config, true)
        val ancho = resultado.width
        val alto = resultado.height
        val hsv = FloatArray(3)
        for (y in 0 until alto) {
            for (x in 0 until ancho) {
                val pixel = resultado.getPixel(x, y)
                Color.colorToHSV(pixel, hsv)
                hsv[0] = matiz
                resultado.setPixel(x, y, Color.HSVToColor(Color.alpha(pixel), hsv))
            }
        }

        return resultado
    }

    fun sepia(bit: Bitmap): Bitmap {
        var bitmap = bit
        //Ancho y alto del bitmap que recibimos
        val ancho = bitmap.width
        val alto = bitmap.height
        // Creamos un nuevo bitmap para realizar las operaciones
        val resultado = Bitmap.createBitmap(ancho, alto, bitmap.config)
        val GS_RED = 0.3
        val GS_GREEN = 0.59
        val GS_BLUE = 0.11
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        var pixel: Int

        for (x in 0 until ancho) {
            for (y in 0 until alto) {
                // Obtenemos pixel por pixel del bitmap que recibimos
                pixel = bitmap.getPixel(x, y)
                a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)
                r = (GS_RED * r + GS_GREEN * g + GS_BLUE * b).toInt()
                g = r
                b = g
                //Aplicamos la intensidad a cada color
                r += 110
                if (r > 255) {
                    r = 255
                }
                g += 65
                if (g > 255) {
                    g = 255
                }
                b += 20
                if (b > 255) {
                    b = 255
                }

                //Aplicamos a cada pixel
                resultado.setPixel(x, y, Color.argb(a, r, g, b))
            }
        }

        return resultado
    }

    fun noise(bit: Bitmap): Bitmap {
        var bitmap = bit
        val COLOR_MAX = 0xFF

        // Obtenemos los valores del bitmap que recibimos
        val ancho = bitmap.width
        val alto = bitmap.height
        val pixeles = IntArray(ancho * alto)
        bitmap.getPixels(pixeles, 0, ancho, 0, 0, ancho, alto)
        // Creamos un objeto random
        val random = Random()
        var index = 0
        //Creamos un for para ir pixel por pixel
        for (y in 0 until alto) {
            for (x in 0 until ancho) {
                index = y * ancho + x
                //Obtenemos un color random
                val randmColor: Int = Color.rgb(
                    random.nextInt(COLOR_MAX),
                    random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX)
                )
                //
                pixeles[index] = pixeles[index] or randmColor
            }
        }
        // Le damos los valores al bitmap que vamos a retornar
        val resultado = Bitmap.createBitmap(ancho, alto, bitmap.config)
        resultado.setPixels(pixeles, 0, ancho, 0, 0, ancho, alto)
        return resultado
    }

    //Para el zoom
    fun  zoom(bitmap: Bitmap): Bitmap {
        var bitmap2 = bitmap
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        val alto = bitmap2.height
        val ancho = bitmap2.width
        var pixel: Int
        var bandera = 1
        //Creamos un nuevo bitmap
        var resultado = Bitmap.createBitmap((bitmap2.width)*2, (bitmap.height)*2, bitmap2.config)

        for (y in 0 until alto) {
            for (x in 0 until ancho) {
                //Dandole los colores con RGB
                pixel = bitmap.getPixel(x, y)
                a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)

                    var ejex: Int = x
                    var ejey: Int = y
                    ejex *= 2
                    ejey *= 2
                    for(y2 in ejey until (ejey+2)){
                        for(x2 in ejex until (ejex+2)){
                            resultado.setPixel(x2, y2, Color.argb(a, r, g, b))
                        }
                    }
                //resultado = agrandar(x,y,a,r,g,b, resultado)
            }
        }

        return resultado
    }
    fun  alejar(bitmap: Bitmap): Bitmap {
        var bitmap2 = bitmap
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        val alto = bitmap2.height
        val ancho = bitmap2.width
        var pixel: Int
        var bandera = 1
        //Creamos un nuevo bitmap
        var resultado = Bitmap.createBitmap((bitmap2.width)*2, (bitmap.height)*2, bitmap2.config)

        for (y in 0 until alto) {
            for (x in 0 until ancho) {
                //Dandole los colores con RGB
                pixel = bitmap.getPixel(x, y)
                a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)

                var ejex: Int = x
                var ejey: Int = y
                if((x ==0) && (y ==0)){
                    ejex = ejex
                    ejey = ejey
                }else if(y==0){
                    ejex +=1
                }else if(x==0){
                    ejey +=1
                }else {
                    ejex +=1
                    ejey +=1
                }
                for(y in ejey until (ejey+2)){
                    for(x in ejex until (ejex+2)){
                        resultado.setPixel(x, y, Color.argb(a, r, g, b))
                    }
                }
                //resultado = agrandar(x,y,a,r,g,b, resultado)
            }
        }

        return resultado
    }
    private fun agrandar(x2: Int, y2: Int, a2: Int, r2: Int, g2: Int, b2: Int, res: Bitmap): Bitmap {
        var ejex: Int = x2
        var ejey: Int = y2
        if((x2 ==0) && (y2 ==0)){
           ejex = ejex
           ejey = ejey
        }else if(y2==0 && x2!=0){
            ejex *=2
        }else if(x2==0 && y2!=0){
            ejey *=2
        }else if(x2!=0 && y2!=0){
            ejex *=2
            ejey *=2
        }
        for(y in ejey until (ejey+2)){
            for(x in ejex until (ejex+2)){
                res.setPixel(x, y, Color.argb(a2, r2, g2, b2))
            }
        }
        return res
    }
}