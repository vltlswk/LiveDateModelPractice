package com.example.livedatatutorial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 타입을 명시적으로 관리하기 위해서 enum class생성
enum class ActionType{
    PLUS,MINUS
}

class MyViewModel : ViewModel() {

    val TAG: String = "로그"

    // private 이라서 외부에서 _currentValue를 사용할 수 없다.
    // 따라서 이 _currentValue를 수정해주는 변수가 필요하다.
    // private 으로 이 MyViewModel에서만 접근할 수 있는 _currentValue는 가변적으로 생성해서 보안성을 높인다.
    private val _currentValue = MutableLiveData<Int>()

    // currentValue는 외부에서 참조하는 변수다.
    // 이 변수는 LiveDate로 선언해서 외부에서 읽기전용으로 만들어 보안성을 높인다.
    // get을 수정해서 수정가능한 _currentValue를 가져오도록 수정한다.
    val currentValue:LiveData<Int>
        get() = _currentValue

    // 클래스가 만들어 졌을 때 init을 이용해서 value를 0으로 초기화해준다.
    init {
        Log.d(TAG, "init 호출")
        _currentValue.value=0
    }

    // _currentValue를 변경하게 할 수 있는 updateValue 함수를 만들어준다.
    // 여기서는 enum class에서 만들어준 actionType으로 변수를 만들어서 명시적으로 when문을 만들어준다.
    // 이때 _current는 초기부터 할당한게 아니여서 nullable한 상태이므로 "?" 를 표기해준다.
    // 변수로 받은 input을 _currentValue로 넣어주면서 후에 이 뷰모델을 사용하는곳에서 currentValue를 요청할 시
    // get() 함수로인해 변경된 _currentValue가 observe된다.
    fun updateValue(actionType: ActionType,input:Int){
        when(actionType){
            ActionType.PLUS ->
                _currentValue.value=_currentValue.value?.plus(input)
            ActionType.MINUS->
                _currentValue.value=_currentValue.value?.minus(input)
        }
    }


}