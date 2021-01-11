package mx.jrra.proyectofinal
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
class ControlPersonalizado : View {
    constructor(ctx: Context) : super(ctx) {}
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {}
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int) : super(ctx,    attrs,        defStyleAttr    ) {    }

    override fun onDraw(canvas: Canvas) {
        //para obtener las dimensiones del control
        val alto = measuredHeight
        val ancho = measuredWidth
        val STROKE_WIDTH = 30f
        val paint: Paint = Paint().apply {
            color = Color.rgb(54, 158, 241)
            isAntiAlias = true
            //Dithering effects how colors with higher-precision than the device are down-sampled
            isDither = true
            style = Paint.Style.FILL // por default es FILL
            strokeJoin = Paint.Join.ROUND // Por default es MITER
            strokeCap = Paint.Cap.ROUND //Por default es BUTT
            strokeWidth = STROKE_WIDTH //por default es Hairline-width
        }
        val fijo: Paint = Paint().apply {
            val tipoFuente = Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)
            typeface = tipoFuente
            textSize = 70f
        }
        canvas.drawLine(0f, 0f, ancho.toFloat(), 0f, paint)
        canvas.drawText("FILTROS", ((ancho.toFloat() / 3) + 20), (alto.toFloat() / 2) + 20, fijo)
        canvas.drawLine(0f, alto.toFloat() - 5, ancho.toFloat(), alto.toFloat() - 5, paint)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val ancho = calcularAncho(widthMeasureSpec)
        val alto = calcularAlto(heightMeasureSpec)
        //Para la dimension
        setMeasuredDimension(ancho, alto)
    }
    private fun calcularAncho(limitesSpec: Int): Int {
        var res = 100 //Alto por defecto
        val modo = MeasureSpec.getMode(limitesSpec)
        val limite = MeasureSpec.getSize(limitesSpec)

        if (modo == MeasureSpec.AT_MOST) {
            res = limite
        } else if (modo == MeasureSpec.EXACTLY) {
            res = limite
        }
        return res
    }
    private fun calcularAlto(limitesSpec: Int): Int {
        var res = 200 //Ancho por defecto
        val modo = MeasureSpec.getMode(limitesSpec)
        val limite = MeasureSpec.getSize(limitesSpec)

        if (modo == MeasureSpec.AT_MOST) {
            res = limite
        } else if (modo == MeasureSpec.EXACTLY) {
            res = limite
        }
        return res
    }


}