package com.example.belttools.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.belttools.R
import com.example.belttools.databinding.FragmentStartBinding
import com.example.belttools.ui.viewmodel.SharedViewModel
import com.example.belttools.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val TAG = "StartFrag__TAG"

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        Log.d(TAG, "onCreateView: called")
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        Log.d(TAG, "onViewCreated1: called")
        sharedViewModel.setUpDatabase(requireActivity().application)
        setUpClickListeners()
        displayMagnetLocation()
        Log.d(TAG, "onViewCreated2: called")
        setUpAppBar()
        populateNavList()
        if (sharedViewModel.getStoreNumberValue(requireContext()) == null) {
            makeStoreNumInputDialog()
        }
        Log.d(TAG, "onViewCreated3: called")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        Log.d(TAG, "onDestroy: called")
    }

    // SET UP //
    private fun setUpAppBar() {
        binding?.apply {
            startTopAppbar.title = "Functions"
            startTopAppbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.clear_texts_btn -> {
                        resetAllClicked()
                        true
                    }
                    R.id.list_option_change_store_num -> {
                        makeStoreNumInputDialog()
                        true
                    }
                    else -> false
                }
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val moreOptionsDrawable = R.drawable.ic_more_options_24
                startTopAppbar.overflowIcon =
                    ContextCompat.getDrawable(requireContext(), moreOptionsDrawable)
            }
            startTopAppbar.setNavigationOnClickListener {
                drawerLayout.open()
            }
            navigationView.setNavigationItemSelectedListener { menuItem ->
                val navController =
                    Navigation.findNavController(requireParentFragment().requireView())
                when (menuItem.title) {
                    sharedViewModel.navDestinationsList[1] -> navController
                        .navigate(R.id.action_startFragment_to_departmentExtensionFragment)
                    sharedViewModel.navDestinationsList[2] -> navController
                        .navigate(R.id.action_startFragment_to_specialtyOrdersFragment)
                    sharedViewModel.navDestinationsList[3] -> navController
                        .navigate(R.id.action_startFragment_to_departmentNotesFragment)
                    sharedViewModel.navDestinationsList[4] -> {
                        sharedViewModel.itemsListToDisplay = FLOOR_SKUS_LIST
                        navController.navigate(R.id.action_startFragment_to_itemsToWorkOnFragment)
                    }
                    sharedViewModel.navDestinationsList[5] -> {
                        sharedViewModel.itemsListToDisplay = PALLET_SKUS_LIST
                        navController.navigate(R.id.action_startFragment_to_itemsToWorkOnFragment)
                    }
                }
                menuItem.isChecked = true
                drawerLayout.close()
                true
            }
        }
    }

    private fun setUpClickListeners() {
        binding?.apply {
            //1 Get the square feet
            sqrPerRoomBtn.setOnClickListener {
                if (sqrtWidthEt.text.isNotEmpty() && sqrtLengthEt.text.isNotEmpty()) {
                    val squaresList = sharedViewModel.getSqrPerRoom(
                        sqrtWidthEt.text.toString().toDouble(),
                        sqrtLengthEt.text.toString().toDouble(),
                        sqrPerRoomBtn.text.toString(),
                        getString(R.string.sqr_per_room_btn)
                    )
                    if (squaresList.isNullOrEmpty()) {
                        displayToast(requireContext(), "Limit Reached")
                    } else sqrPerRoomBtn.text = squaresList
                }
            }
            sqrPerRoomBtn.setOnLongClickListener {
                if (sqrPerRoomBtn.text.toString() != getString(R.string.sqr_per_room_btn)) {
                    homeSqrFt.setText(
                        offExtraZeros(
                            "%.2f",
                            sumListOfString(sqrPerRoomBtn.text.split(PLUS_JOIN))
                        )
                    )
                }
                return@setOnLongClickListener true
            }
            sqrtWidthEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(sqrPerRoomBtn, keyCode, keyEvent)
            }
            sqrtLengthEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(sqrPerRoomBtn, keyCode, keyEvent)
            }
            //2 Get number of boxes to buy
            tileBoxResultsBtn.setOnClickListener {
                if (homeSqrFt.text.isNotEmpty() && boxSqrFt.text.isNotEmpty()) {
                    val boxesList = sharedViewModel.getNumberOfBoxes(
                        homeSqrFt.text.toString().toDouble(),
                        boxSqrFt.text.toString().toDouble(),
                        tileBoxResultsBtn.text.toString(),
                        getString(R.string.tile_box_results_btn)
                    )
                    if (boxesList.isNullOrEmpty()) {
                        displayToast(requireContext(), "Limit Reached")
                    } else tileBoxResultsBtn.text = boxesList
                }
            }
            tileBoxResultsBtn.setOnLongClickListener {
                if (tileBoxResultsBtn.text.toString() != getString(R.string.tile_box_results_btn)) {
                    tileBoxResultsBtn.text =
                        offExtraZeros(
                            "%.4f",
                            sumListOfString(tileBoxResultsBtn.text.split(PLUS_JOIN))
                        )
                }
                return@setOnLongClickListener true
            }
            homeSqrFt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(tileBoxResultsBtn, keyCode, keyEvent)
            }
            boxSqrFt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(tileBoxResultsBtn, keyCode, keyEvent)
            }
            //3 sqr foot to sqr in sqrInBox
            sqrFootToSqrInBtn.setOnClickListener {
                if (sqrFootEt.text.isNotEmpty() && sqrInEt.text.isNotEmpty()) {
                    sqrFootEt.text.clear(); sqrInEt.text.clear()
                } else if (sqrFootEt.text.isEmpty() && sqrInEt.text.isEmpty()) {
                    sqrFootToSqrInBtn.text = sharedViewModel.getSqrFtOrSqrIn(
                        sqrFootEt.text.toString(),
                        sqrInEt.text.toString()
                    )
                }
            }
            sqrFootEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(sqrFootToSqrInBtn, keyCode, keyEvent)
            }
            sqrInEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(sqrFootToSqrInBtn, keyCode, keyEvent)
            }
            //4 get blind width
            blindWidthEqualsBtn.setOnClickListener {
                if (windowWidthEt.text.isEmpty() && blindWidthEt.text.isEmpty()) {
                    blindWidthResultTxt.text = offExtraZeros(
                        "%.4f",
                        getNewBlindWidth(
                            windowWidthEt.text.toString().toDouble(),
                            blindWidthEt.text.toString().toDouble()
                        )
                    )
                }
            }
            windowWidthEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(blindWidthEqualsBtn, keyCode, keyEvent)
            }
            blindWidthEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(blindWidthEqualsBtn, keyCode, keyEvent)
            }
            //5 decimal to fraction
            decimalToFractionBtn.setOnClickListener {
                if (decimalEt.text.isNotEmpty() && fractionEt.text.isNotEmpty()) {
                    decimalEt.text.clear(); fractionEt.text.clear()
                } else if (decimalEt.text.isNotEmpty()) {
                    fractionEt.setText(decimalToFraction(decimalEt.text.toString().toDouble()))
                } else if (fractionEt.text.isNotEmpty()) {
                    val rawDecimal =
                        sharedViewModel.turnFractionToDecimal(fractionEt.text.toString())
                    if (rawDecimal != null) {
                        decimalEt.setText(offExtraZeros("%.3f", rawDecimal))
                    } else {
                        displayToast(requireContext(), "Write a fraction with /.")
                    }
                }
            }
            decimalEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(decimalToFractionBtn, keyCode, keyEvent)
            }
            fractionEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(decimalToFractionBtn, keyCode, keyEvent)
            }
            //6 lineal ft to square yard
            linealFtToSqrYardBtn.setOnClickListener {
                if (linealFtEt.text.isNotEmpty() && sqrYardEt.text.isNotEmpty()) {
                    linealFtEt.text.clear(); sqrYardEt.text.clear()
                } else if (linealFtEt.text.isNotEmpty()) {
                    sqrYardEt.setText(
                        offExtraZeros(
                            "%.3f",
                            linealFeetToSqrYard(linealFtEt.text.toString().toDouble())
                        )
                    )
                } else if (sqrYardEt.text.isNotEmpty()) {
                    linealFtEt.setText(
                        offExtraZeros(
                            "%.3f",
                            sqrYardToLinealFeet(sqrYardEt.text.toString().toDouble())
                        )
                    )
                }
            }
            linealFtEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(linealFtToSqrYardBtn, keyCode, keyEvent)
            }
            sqrYardEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(linealFtToSqrYardBtn, keyCode, keyEvent)
            }
            //7 Price per Sqr Ft
            sqrFtPriceBtn.setOnClickListener {
                if (boxPriceEt.text.isNotEmpty() && boxSqrFtEt.text.isNotEmpty()) {
                    sqrFtPriceTxt.text = offExtraZeros(
                        "",
                        getPricePerSqrFoot(
                            boxPriceEt.text.toString().toDouble(),
                            boxSqrFtEt.text.toString().toDouble()
                        )
                    )
                } else {
                    displayToast(requireContext(), "Fill both boxes")
                }
            }
            boxPriceEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(sqrFtPriceBtn, keyCode, keyEvent)
            }
            boxSqrFtEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(sqrFtPriceBtn, keyCode, keyEvent)
            }
            //8 number of vertical louvers
            numOfLouversEqualsBtn.setOnClickListener {
                if (vBlindWidthEt.text.isNotEmpty() && numOfLouversTxt.text.isNotEmpty()) {
                    vBlindWidthEt.text.clear(); numOfLouversTxt.text = "0"
                } else if (vBlindWidthEt.text.isNotEmpty()) {
                    numOfLouversTxt.text = offExtraZeros(
                        "%.1f",
                        getNumberOfLouvers(vBlindWidthEt.text.toString().toDouble())
                    )
                }
            }
            vBlindWidthEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(numOfLouversEqualsBtn, keyCode, keyEvent)
            }
            //9 Lineal Backsplash
            bakShEqualsBtn.setOnClickListener {
                if (bkShLinealSpaceEt.text.isNotEmpty() && cutOutsEt.text
                        .isNotEmpty() && bakShResultsTxt.text.toString() != "0"
                ) {
                    bakShWidthEt.text.clear(); bkShLinealSpaceEt.text.clear()
                    cutOutsEt.text.clear(); bakShResultsTxt.text = "0"
                } else {
                    bakShResultsTxt.text = offExtraZeros(
                        "%.4f",
                        sharedViewModel.getLinealBacksplash(
                            bakShWidthEt.text.toString(),
                            bkShLinealSpaceEt.text.toString().toDouble(),
                            !bakShToggleBtn.isChecked, // in feet
                            cutOutsEt.text.toString().toInt()
                        )
                    )
                }
            }
            bkShLinealSpaceEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(bakShEqualsBtn, keyCode, keyEvent)
            }
            cutOutsEt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(bakShEqualsBtn, keyCode, keyEvent)
            }
            bakShResultsTxt.setOnKeyListener { _, keyCode, keyEvent ->
                pressedEnter(bakShEqualsBtn, keyCode, keyEvent)
            }
            //10 Magnet location
            magnetLocationSaveBtn.setOnClickListener {
                sharedViewModel.sendDataToSP(
                    MAGNET_LOCATION_TAG,
                    MAGNET_LOCATION,
                    requireContext(),
                    newMagnetLocationEt.text.toString()
                )
                lastMagnetLocationTxt.text = newMagnetLocationEt.text.toString()
            }
        }
    }

    private fun makeStoreNumInputDialog() {
        val inputDialog = MaterialAlertDialogBuilder(requireContext())
        val customAlertDialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.store_number_box, null, false)
        val inputNumberDialog: EditText = customAlertDialogView.findViewById(R.id.store_number_et)
        inputDialog.setView(customAlertDialogView)
            .setTitle("Store number")
            .setPositiveButton("Accept") { _, _ ->
                sharedViewModel.storeNumber = inputNumberDialog.text.toString()
                sharedViewModel.sendDataToSP(
                    STORE_NUMBER_TAG,
                    STORE_NUMBER,
                    requireContext(),
                    sharedViewModel.storeNumber!!
                )
            }
            .setNegativeButton("Cancel") { _, _ ->
                Log.i(TAG, "No store number input")
            }
            .show()
    }
    // SET UP //

    // HELPERS //
    private fun resetAllClicked() {
        binding?.apply {
            sqrtWidthEt.text.clear(); sqrtLengthEt.text.clear(); sqrPerRoomBtn.text =
            getString(R.string.sqr_per_room_btn)
            homeSqrFt.text.clear(); boxSqrFt.text.clear(); tileBoxResultsBtn.text =
            getString(R.string.tile_box_results_btn)
            sqrFootEt.text.clear(); sqrInEt.text.clear()
            windowWidthEt.text.clear(); blindWidthEt.text.clear(); blindWidthResultTxt.text = "0"
            decimalEt.text.clear(); fractionEt.text.clear() // 5
            linealFtEt.text.clear(); sqrYardEt.text.clear()
            boxPriceEt.text.clear(); boxSqrFtEt.text.clear(); sqrFtPriceTxt.text = "0"
            vBlindWidthEt.text.clear(); numOfLouversTxt.text = "0"
            newMagnetLocationEt.text.clear(); bkShLinealSpaceEt.text.clear(); cutOutsEt.text.clear()
            bakShResultsTxt.text = "0"
        }
    }

    private fun pressedEnter(itemToClick: Button, keycode: Int, theEvent: KeyEvent): Boolean {
        return if (keycode == KeyEvent.KEYCODE_ENTER && theEvent.action == KeyEvent.ACTION_UP) {
            itemToClick.performClick()
            true
        } else {
            false
        }
    }

    private fun displayMagnetLocation() {
        val magnetLocation = sharedViewModel.getDataFromSP(
            MAGNET_LOCATION_TAG,
            MAGNET_LOCATION,
            requireContext()
        )
        if (!magnetLocation.isNullOrEmpty()) binding!!.lastMagnetLocationTxt.text = magnetLocation
    }

    private fun populateNavList() {
        val destinationsList = listOf(
            getString(R.string.functions),
            getString(R.string.extensions),
            getString(R.string.orders),
            getString(R.string.notes),
            getString(R.string.skus),
            getString(R.string.pallets)
        )
        sharedViewModel.populateNavList(destinationsList)
    }
    // HELPERS //
}