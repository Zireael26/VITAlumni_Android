package com.dscvit.vitalumni.ui.main.fragment.bottomsheet

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import com.dscvit.vitalumni.R
import kotlinx.android.synthetic.main.fragment_aboutus.view.*

class AboutusBottomSheetFragment : RoundedBottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_aboutus, container, false)

        view.img_btn_about_dsc.setOnClickListener {
            openUrl("https://dscvit.com/")
        }

        view.img_btn_about_abhishek.setOnClickListener {
            openUrl("https://github.com/Zireael26")
        }

        view.img_btn_about_yaswant.setOnClickListener {
            openUrl("https://github.com/MINOSai")
        }

        view.img_btn_about_aritro.setOnClickListener {
            openUrl("https://github.com/notACoder69")
        }

        view.img_btn_about_samarth.setOnClickListener {
            openUrl("https://www.behance.net/samarthdesigns")
        }

        return view
    }

    private fun openUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context!!, Uri.parse(url))
    }
}