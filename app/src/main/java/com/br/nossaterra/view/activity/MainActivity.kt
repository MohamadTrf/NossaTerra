package com.br.nossaterra.view.activity

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.view.ViewGroup
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.br.nossaterra.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnDragListener, View.OnTouchListener {
  @RequiresApi(Build.VERSION_CODES.N)
  override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
    var action = p1?.action

    if(action == MotionEvent.ACTION_DOWN){
      var data = ClipData.newPlainText("", "")
      var shadowBuilder = View.DragShadowBuilder(p0)

      p0?.startDragAndDrop(data, shadowBuilder, p0,0)
      p0?.visibility = View.INVISIBLE
      return true
    }
    return false
  }

  override fun onDrag(p0: View?, p1: DragEvent?): Boolean {
    when (p1?.getAction()) {
      DragEvent.ACTION_DRAG_ENTERED ->
        alvo.alpha = 0.75f
      DragEvent.ACTION_DRAG_EXITED ->
        alvo.alpha = 1f
      DragEvent.ACTION_DROP -> {
        val view = p1?.getLocalState() as View
        view.visibility = View.INVISIBLE
        val owner = view.parent as ViewGroup
        owner.removeView(view)
        val container = p0 as ConstraintLayout
        container.addView(view)
        view.visibility = View.VISIBLE
      }
      DragEvent.ACTION_DRAG_ENDED -> {
        cliqueArrasta.visibility = View.INVISIBLE
        p0?.background = getDrawable(R.drawable.ic_big_star)
        if(checkVisiblity()){
          startActivity(Intent(this, MenuActivity::class.java))
        }
      }
      else -> {
      }
    }
    return true
  }

  private lateinit var star1: ImageView
  private lateinit var star2: ImageView
  private lateinit var star3: ImageView
  private lateinit var star4: ImageView
  private lateinit var alvo: ConstraintLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    star1 = findViewById(R.id.estrela1)
    star2 = findViewById(R.id.estrela2)
    star3 = findViewById(R.id.estrela3)
    star4 = findViewById(R.id.estrela4)
    alvo = findViewById(R.id.alvoPoeira)

    implementEvents()

  }

  private fun implementEvents(){
    star1.setOnTouchListener(this)
    star2.setOnTouchListener(this)
    star3.setOnTouchListener(this)
    star4.setOnTouchListener(this)

    alvo.setOnDragListener(this)
  }

  private fun checkVisiblity() : Boolean{
    COUNT++
    return (COUNT > 3)
  }

  companion object{
    var COUNT = 0
  }

}
