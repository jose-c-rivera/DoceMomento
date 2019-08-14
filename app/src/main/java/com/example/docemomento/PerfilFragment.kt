package com.example.docemomento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class PerfilFragment : Fragment(){

    companion object {
        fun newInstance(): PerfilFragment {
            return PerfilFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }
}