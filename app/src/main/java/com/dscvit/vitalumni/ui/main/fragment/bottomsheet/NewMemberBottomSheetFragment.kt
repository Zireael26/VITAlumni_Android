package com.dscvit.vitalumni.ui.main.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dscvit.vitalumni.R
import com.dscvit.vitalumni.model.api.GuestHelper
import kotlinx.android.synthetic.main.fragment_bottom_sheet_new_member.view.*

class NewMemberBottomSheetFragment : RoundedBottomSheetDialogFragment() {

    var listener: NewMemberListener? = null

    lateinit var rootView: View
    var isValid = false

    var name = ""
    var relation = ""
    var age = ""
    var food = ""

    fun init(listener: NewMemberListener) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_bottom_sheet_new_member, container, false)

        rootView.chip_group_new_member_food.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chip_new_member_veg -> food = "Veg"
                R.id.chip_new_member_nonveg -> food = "Non-Veg"
            }
        }

        rootView.chip_group_new_member_relation.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chip_relation_child -> relation = "Child"
                R.id.chip_relation_spouse -> relation = "Spouse"
            }
        }

        rootView.button_new_member_add.setOnClickListener {
            isValid = validate()
            if (isValid) {
                // TODO: pass data to fragment using interface
                listener?.onAddNewMember(GuestHelper(
                        Integer.parseInt(age), food, relation, name
                ))
                dismiss()
            }
        }

        return rootView
    }

    fun validate(): Boolean {
        var valid = true

        name = rootView.input_new_member_name.editText?.text?.toString() ?: ""
        if (name.isEmpty()) {
            rootView.input_new_member_name.error = "Name should not be empty"
            valid = false
        } else {
            rootView.input_new_member_name.error = null
        }

        if (relation.isEmpty()) {
            rootView.text_error_guest_relation.visibility = View.VISIBLE
            valid = false
        } else {
            rootView.text_error_guest_relation.visibility = View.GONE
        }

        age = rootView.input_new_member_age.editText?.text?.toString() ?: ""
        if (age.isEmpty()) {
            rootView.input_new_member_age.error = "Age should not be empty"
            valid = false
        } else {
            rootView.input_new_member_age.error = null
        }

        if (food.isEmpty()) {
            rootView.text_error_guest_food.visibility = View.VISIBLE
            valid = false
        } else {
            rootView.text_error_guest_food.visibility = View.GONE
        }

        return valid
    }
}