package com.jonathan.projet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jonathan.projet.ItemModel
import com.jonathan.projet.ItemRepository.Singleton.itemList
import com.jonathan.projet.MainActivity
import com.jonathan.projet.R
import com.jonathan.projet.adapter.BurgerAdapter
import com.jonathan.projet.adapter.BurgerItemDecoration

class HomeFragment(
    private val context : MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container,false)



        // recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = BurgerAdapter(context, itemList.filter { !it.liked },R.layout.item_horizontal_burger)

        //recuperer le second recyclerview
        val verticalRecyclerView= view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter= BurgerAdapter(context, itemList.filter { it.liked },R.layout.item_vertical_burger)
        verticalRecyclerView.addItemDecoration(BurgerItemDecoration())

        return view
    }
}