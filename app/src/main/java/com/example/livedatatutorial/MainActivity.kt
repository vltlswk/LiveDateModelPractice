package com.example.livedatatutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

// 클래스에 View.OnClickListener를 상속해준다. 이때 OnClickListener는 Interface여서 상속이 가능한것이다.
class MainActivity : AppCompatActivity(), View.OnClickListener {

    val TAG: String = "로그"
    // 클래스 전체에서 변수를 사용할꺼기 때문에 lateinit var 으로 지연 초기화를 한다.
    // 이때 lateinit var로 변수를 선언 했을때 초기화 하지 않고 add 와같은 함수를 사용하면
    // 초기화하지 않은 객체 오류가 나타나니 유의하자.
    lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //이때 myViewModel이 ViewModelProvider로 할당된다.
        myViewModel=ViewModelProvider(this).get(MyViewModel::class.java)


        // MyViewModel에서 observe를 하고 Observer를 사용해서 current.value를 텍스트뷰에 할당해준다.
        // 이때 currentValue를 가져오면 get()을 수정해서 _currentValue를 가져오게된다.
        myViewModel.currentValue.observe(this, Observer {
            Log.d(TAG, "currentValue 라이브 데이터 값 변경 : $it")
            current_value_text_view.text=it.toString()
        })

        // Onclick으로 제어하기 위해서 소괄호() 안에 this를 넣어서 할당한다.
        plus_btn.setOnClickListener(this)
        minus_btn.setOnClickListener(this)


    }

    // onClick 함수를 오버라이드 하고 view를 기준으로 when문을 실행해서 가독성을 높인다.
    override fun onClick(view: View?) {
        val userInput=user_input_edit_text.text.toString().toInt()

        when(view){
            plus_btn->myViewModel.updateValue(actionType = ActionType.PLUS,userInput)
            minus_btn->myViewModel.updateValue(actionType = ActionType.MINUS,userInput)
        }
    }


}