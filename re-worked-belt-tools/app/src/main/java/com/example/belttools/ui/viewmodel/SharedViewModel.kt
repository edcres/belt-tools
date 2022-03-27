package com.example.belttools.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.belttools.data.Repository
import com.example.belttools.data.model.MainRoomDatabase
import com.example.belttools.data.model.entities.Department
import com.example.belttools.data.model.entities.SKU
import com.example.belttools.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class SharedViewModel: ViewModel() {

    lateinit var navDestinationsList: List<String>
    private lateinit var roomDb: MainRoomDatabase
    private lateinit var repository: Repository

    private val _departments = MutableLiveData<MutableList<Department>>()
    val departments: LiveData<MutableList<Department>> get() = _departments
    private val _skus = MutableLiveData<MutableList<SKU>>()
    val skus: LiveData<MutableList<SKU>> get() = _skus

    private var _menuEditIsOn = MutableLiveData(false)
    val menuEditIsOn: LiveData<Boolean> get() = _menuEditIsOn

    var departmentToEdit: Department? = null
    var itemsListToDisplay = ""

    // HELPERS //
    fun toggleEditBtn(): Boolean {
        //todo: toggle this off when an item is clicked to be edited
        val newValue = !_menuEditIsOn.value!!
        _menuEditIsOn.value = newValue
        return newValue
    }
    fun showNewSKU(skuNum: Long): Boolean {
       if (doesSKUContainId(_skus.value!!.toList(), skuNum)) {
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
    fun updateSKUType(skuId: Long) {
        if (itemsListToDisplay == PALLET_SKUS_LIST){
            // todo: update sku pallet to true
        } else if (itemsListToDisplay == FLOOR_SKUS_LIST) {
            // todo: update sku floor to true
        }
    }
    fun populateNavList(navList: List<String>) {
        navDestinationsList = navList
    }
    fun saveMagnetLocation(context: Context, location: String) {
        val magnetLocationSharedPref = context
            .getSharedPreferences(MAGNET_LOCATION_TAG, Context.MODE_PRIVATE) ?: return
        with(magnetLocationSharedPref.edit()) {
            putString(MAGNET_LOCATION, location)
            apply()
            displayToast(context, "Saved")
        }
    }
    fun getMagnetLocation(context: Context): String? {
        val magnetLocationSharedPref = context
            .getSharedPreferences(MAGNET_LOCATION_TAG, Context.MODE_PRIVATE)
        return magnetLocationSharedPref.getString(MAGNET_LOCATION, null)
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
        } else {
            offExtraZeros("%.2f", result)
        }
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
        } else {
            offExtraZeros("%.4f", result)
        }
    }

    fun getSqrFtOrSqrIn(
        sqrFt: String,
        sqrIn: String
    ): String {
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
    fun collectEntities() {
        // todo: call this
        CoroutineScope(Dispatchers.IO).launch {
            repository.allDepartments.collect {
                _departments.postValue(it.toMutableList())
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            repository.allSKUs.collect {
                _skus.postValue(it.toMutableList())
            }
        }
    }
    fun insertDepartment(department: Department) {
        // todo:
    }
    fun insertSKU(sku: SKU) {
        // todo: call this, user types their own primary id
        // todo:
    }
    fun updateDepartment(department: Department) {
        // todo:
    }
    fun updateExtensions(department: Department) {
        // todo:
    }
    fun updateSKU(sku: SKU) {
        // todo:
    }
    fun removeSKU(sku: SKU) {
        // todo:
    }
    fun removeDepartment(department: Department) {
        // todo:
    }
    fun deletePalletOrFloorSku(sku: SKU) {
        if (itemsListToDisplay == PALLET_SKUS_LIST) {
            // delete pallet
            if (sku.isOfFloor) {
                sku.isOfPallet = false
                updateSKU(sku)
            } else {
                removeSKU(sku)
            }
        } else if (itemsListToDisplay == FLOOR_SKUS_LIST) {
            // delete floor
            if (sku.isOfPallet) {
                sku.isOfFloor = false
                updateSKU(sku)
            } else {
                removeSKU(sku)
            }
        }
    }
    fun deleteExtensions(department: Department) {
        // todo: send this logic to the viewModel
        if (department.notes.isEmpty()) {
            // todo: delete
        } else {
            // todo: update, don't delete
        }
    }
    fun deleteNotes(department: Department) {
        // todo: send this logic to the viewModel
        if (department.extensions.isEmpty()) {
            // todo: delete
        } else {
            // todo: update, don't delete
        }
    }
    fun getFilteredItemsList(): MutableLiveData<List<SKU>> {
        val filteredSKUs = MutableLiveData<List<SKU>>()
        if (itemsListToDisplay == PALLET_SKUS_LIST){
            // todo: get sku where pallet = true
        } else if (itemsListToDisplay == FLOOR_SKUS_LIST) {
            // todo: get sku where floor = true
        }
        return filteredSKUs
    }
    // DATABASE QUERIES //

    // SET UP //
    fun setUpDatabase(application: Application) {
        roomDb = MainRoomDatabase.getInstance(application)
        repository = Repository(roomDb)
        collectEntities()
    }
}