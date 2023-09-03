package com.common.compose14.view.car

import LogUtils
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.common.compose14.bean.CarBean
import com.common.compose14.view.home.HomeIntent
import com.common.compose14.view.home.HomeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Collections.addAll

class CarViewModel: ViewModel() {

    /**
     * 接收事件
     */
    private val userIntent = MutableSharedFlow<CarIntent>()

    /**
     * 分发用户事件
     * @param viewAction
     */
    fun dispatch(viewAction: CarIntent) {
        try {
            viewModelScope.launch {
                userIntent.emit(viewAction)
            }
        } catch (e: Exception) {
        }
    }

    init {
        viewModelScope.launch {
            userIntent.collect {
                when (it) {
                    is CarIntent.RequestCarList -> requestCarList()
                    is CarIntent.RequestChecked -> requestChecked(it.index)
                    is CarIntent.RequestAddOrRemoveNum -> requestAddOrRemoveNum(it.isAdd,it.index)
                    is CarIntent.RequestAllChecked -> requestAllChecked()
                    else -> {}
                }
            }
        }
    }

    private var _state = MutableStateFlow<CarState>(CarState.OnLoading)
    val state: StateFlow<CarState> = _state.asStateFlow()

    val carList = mutableStateListOf<CarBean>()
    var allPrice by mutableStateOf(0)
    var allChecked by mutableStateOf(false)

     private fun requestCarList(){
        viewModelScope.launch {
            val list = mutableListOf(CarBean("20"),CarBean("20"),CarBean("20"),CarBean("20"),CarBean("20"),)
            carList.addAll(list)
        }
    }

    private fun requestChecked(index:Int){
        viewModelScope.launch {
            carList[index] = carList[index].copy(isChecked = !carList[index].isChecked)
            requestComputePrice()

//            carList.contains()
            allChecked = isExist()
        }
    }

    private fun isExist(): Boolean {
        for (i in carList) {
            if (!i.isChecked) {
                return false
            }
        }
        return  true
    }


    private fun requestAllChecked(){
       allChecked = !allChecked
        for (index in carList.indices) {
            carList[index] = carList[index].copy(isChecked = allChecked)
        }
        requestComputePrice()
    }

    private fun requestAddOrRemoveNum(isAdd:Boolean,index:Int){
        if (isAdd) {
            carList[index] = carList[index].copy(num = carList[index].num+1)
        } else {
            carList[index] = carList[index].copy(num = carList[index].num-1)
        }
        requestComputePrice()
    }

    private fun requestComputePrice(){
       var allprice = 0
        for (item in carList) {
            allprice += if (item.isChecked) (item.price?.toInt() ?: 0)* item.num else 0
        }
        allPrice = allprice
    }

}