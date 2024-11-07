package jemmanuel.romeror.virtuatienda.Vendedor.Nav_Fragments_Vendedor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jemmanuel.romeror.virtuatienda.R

class FragmentMiTiendaV : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_tienda_v, container, false)
    }
}