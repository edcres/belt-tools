package com.example.belttools.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.belttools.data.Repository
import com.example.belttools.data.model.MainRoomDatabase
import com.example.belttools.data.model.entities.Department
import com.example.belttools.data.model.entities.SKU
import com.example.belttools.data.model.entities.SpecialtyOrder
import com.example.belttools.util.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.sqrt

private const val TAG = "VM__TAG"

class SharedViewModel : ViewModel() {

    lateinit var navDestinationsList: List<String>
    private lateinit var roomDb: MainRoomDatabase
    private lateinit var repository: Repository
    var storeNumber: String? = null

    var applicationNotStarted = true
    private val _departments = MutableLiveData<List<Department>>()
    val departments: LiveData<List<Department>> get() = _departments
    private val _skus = MutableLiveData<List<SKU>>()
    val skus: LiveData<List<SKU>> get() = _skus
    private val _specOrders = MutableLiveData<List<SpecialtyOrder>>()
    val specOrders: LiveData<List<SpecialtyOrder>> get() = _specOrders

    private var _menuEditIsOn = MutableLiveData(false)
    val menuEditIsOn: LiveData<Boolean> get() = _menuEditIsOn

    var departmentToEdit: Department? = null
    var itemsListToDisplay = ""

    // HELPERS //
    fun toggleEditBtn(): Boolean {
        val newValue = !_menuEditIsOn.value!!
        _menuEditIsOn.value = newValue
        return newValue
    }

    fun toggleEditBtnOff() {
        _menuEditIsOn.postValue(false)
    }

    fun getStoreNumberValue(context: Context): String? {
        storeNumber = getDataFromSP(STORE_NUMBER_TAG, STORE_NUMBER, context)
        return storeNumber
    }

    fun showNewSKU(skuNum: Long): Boolean {
        if (doesSKUContainId(_skus.value!!, skuNum)) {
            return false
        } else {
            var pallet = false
            var floor = false
            if (itemsListToDisplay == PALLET_SKUS_LIST) pallet = true
            else floor = true
            insertSKU(
                SKU(
                    id = skuNum,
                    isOfPallet = pallet,
                    isOfFloor = floor
                )
            )
            return true
        }
    }

    fun updateSKUType(skuId: Long) = viewModelScope.launch {
        if (itemsListToDisplay == PALLET_SKUS_LIST) repository.updateSKUPallet(skuId)
        else if (itemsListToDisplay == FLOOR_SKUS_LIST) repository.updateSKUFloor(skuId)
    }

    fun populateNavList(navList: List<String>) {
        navDestinationsList = navList
    }

    fun sendDataToSP(tag: String, key: String, context: Context, value: String) {
        val stringSharedPref = context
            .getSharedPreferences(tag, Context.MODE_PRIVATE) ?: return
        with(stringSharedPref.edit()) {
            putString(key, value)
            apply()
            displayToast(context, "Saved")
        }
    }

    fun getDataFromSP(tag: String, key: String, context: Context): String? {
        val stringSharedPref = context
            .getSharedPreferences(tag, Context.MODE_PRIVATE)
        return stringSharedPref.getString(key, null)
    }


    fun getSqrPerRoom(width: Double, length: Double, btnTxt: String, btnDefault: String): String? {
        val result = width * length
        return if (btnTxt != btnDefault) {
            joinMaterialsList(
                "%.2f",
                addRoomSquaresOrBoxes(
                    btnTxt.split(PLUS_JOIN).toMutableList(),
                    offExtraZeros("%.2f", result)
                )
            )
        } else offExtraZeros("%.2f", result)
    }

    fun getNumberOfBoxes(
        width: Double, length: Double, btnTxt: String, btnDefault: String
    ): String? {
        val result = width / length
        return if (btnTxt != btnDefault) {
            joinMaterialsList(
                "%.4f",
                addRoomSquaresOrBoxes(
                    btnTxt.split(PLUS_JOIN).toMutableList(),
                    offExtraZeros("%.4f", result)
                )
            )
        } else offExtraZeros("%.4f", result)
    }

    fun getSqrFtOrSqrIn(sqrFt: String, sqrIn: String): String {
        return when {
            sqrFt.isNotEmpty() -> {
                val inches = sqrt(sqrFt.toDouble()) * 12
                offExtraZeros("%.4f", inches * inches)
            }
            sqrIn.isNotEmpty() -> {
                val feet = sqrt(sqrIn.toDouble()) / 12
                offExtraZeros("%.4f", feet * feet)
            }
            else -> {
                return "0"
            }
        }
    }

    fun turnFractionToDecimal(fraction: String): Double? {
        return if (fraction.contains("/")) {
            val numerator = fraction.substringBeforeLast("/").toDouble()
            val denominator = fraction.substringAfterLast("/").toDouble()
            numerator / denominator
        } else null
    }

    fun getLinealBacksplash(
        widthInp: String, linealSpace: Double,
        inFeet: Boolean, cutOuts: Int
    ): Double {
        // If it's not in feet, it's in inches
        // width = 12 by default
        val bakShWidth: Double = if (widthInp.isEmpty()) 12.0 else widthInp.toDouble()
        val convertedLinealSpace = if (inFeet) linealSpace * 12 else linealSpace
        return convertedLinealSpace / (bakShWidth * cutOuts)
    }
    // HELPERS //

    // DATABASE QUERIES //
    private fun collectEntities() {
        viewModelScope.launch { repository.allDepartments.collect { _departments.postValue(it) } }
        viewModelScope.launch { repository.allSKUs.collect { _skus.postValue(it) } }
        viewModelScope.launch { repository.allSecOrders.collect { _specOrders.postValue(it) } }
    }

    fun insertDepartment(department: Department) = viewModelScope.launch {
        repository.insertDepartment(department)
    }

    fun insertSpecOrder(specialtyOrder: SpecialtyOrder) = viewModelScope.launch {
        repository.insertSpecOrder(specialtyOrder)
    }

    private fun insertSKU(sku: SKU) = viewModelScope.launch {
        repository.insertSKU(sku)
    }

    fun updateDepartment(department: Department) = viewModelScope.launch {
        repository.updateDepartment(department)
    }

    fun updateSpecOrder(specialtyOrder: SpecialtyOrder) = viewModelScope.launch {
        repository.updateSpecOrder(specialtyOrder)
    }

    fun updateSKU(sku: SKU) = viewModelScope.launch {
        repository.updateSKU(sku)
    }

    private fun removeSKU(sku: SKU) = viewModelScope.launch {
        repository.deleteSKU(sku)
    }

    fun removeSpecOrder(specialtyOrder: SpecialtyOrder) = viewModelScope.launch {
        repository.deleteSpecOrder(specialtyOrder)
    }

    private fun removeDepartment(department: Department) = viewModelScope.launch {
        repository.deleteDepartment(department)
    }

    fun deletePalletOrFloorSku(sku: SKU) {
        if (itemsListToDisplay == PALLET_SKUS_LIST) {
            // delete pallet
            if (sku.isOfFloor) {
                sku.isOfPallet = false
                updateSKU(sku)
            } else removeSKU(sku)
        } else if (itemsListToDisplay == FLOOR_SKUS_LIST) {
            // delete floor
            if (sku.isOfPallet) {
                sku.isOfFloor = false
                updateSKU(sku)
            } else removeSKU(sku)
        }
    }

    fun deleteExtensions(department: Department) {
        if (department.notes.isNullOrEmpty()) {
            removeDepartment(department)
        } else {
            department.extensions = null
            updateDepartment(department)
        }
    }

    fun deleteNotes(department: Department) {
        if (department.extensions.isNullOrEmpty()) {
            removeDepartment(department)
        } else {
            department.notes = null
            updateDepartment(department)
        }
    }

    fun getFilteredItemsList(): LiveData<List<SKU>> {
        val filteredSKUs = MutableLiveData<List<SKU>>()
        viewModelScope.launch {
            if (itemsListToDisplay == PALLET_SKUS_LIST) {
                filteredSKUs.postValue(repository.getSKUsOfPallet())
            } else if (itemsListToDisplay == FLOOR_SKUS_LIST) {
                filteredSKUs.postValue(repository.getSKUsOfFloor())
            }
        }
        return filteredSKUs
    }
    // DATABASE QUERIES //

    // SET UP //
    fun setUpDatabase(application: Application) {
        Log.d(TAG, "setUpDatabase1: called")
        if (applicationNotStarted) {
            roomDb = MainRoomDatabase.getInstance(application)
            repository = Repository(roomDb)
            collectEntities()
            applicationNotStarted = false
        }
        Log.d(TAG, "setUpDatabase2: called")
    }
}