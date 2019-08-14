package com.example.docemomento

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ProdutosFragment : Fragment(){

    companion object {
        fun newInstance(): ProdutosFragment {
            return ProdutosFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        return inflater.inflate(R.layout.fragment_produtos, container, false)
    }
}