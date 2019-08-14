package com.example.docemomento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ConfeitariaFragment : Fragment(){

    companion object {
        fun newInstance(): ConfeitariaFragment {
            return ConfeitariaFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        return inflater.inflate(R.layout.fragment_confeitaria, container, false)
    }
}