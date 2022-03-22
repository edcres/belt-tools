package com.example.belttools.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.belttools.R
import com.example.belttools.databinding.FragmentStartBinding
import com.example.belttools.ui.viewmodel.SharedViewModel
import com.example.belttools.util.*

class StartFragment : Fragment() {

    private val fragmentTAG = "StartFrag_TAG"
    private var binding: FragmentStartBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        setUpClickListeners()
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
                        offExtraZeros("%.2f", sumListOfString(sqrPerRoomBtn.text.split(PLUS_JOIN)))
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

        }
    }

    // HELPERS //
    private fun resetAllClicked() {
        // todo: make a btn to call this function
        // todo: popup a dialog box (material) and ask: "Are you sure?"
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
            newMagnetLocationEt.text.clear(); linealSpaceEt.text.clear(); cutOutsEt.text.clear();
            bakShResultsTxt.text = "0"
        }
    }
    private fun pressedEnter(itemToClick: Button, keycode: Int, theEvent: KeyEvent): Boolean {
        return if (keycode == KeyEvent.KEYCODE_ENTER && theEvent.action == KeyEvent.ACTION_UP){
            itemToClick.performClick()
            true
        } else {
            false
        }
    }
    // HELPERS //
}