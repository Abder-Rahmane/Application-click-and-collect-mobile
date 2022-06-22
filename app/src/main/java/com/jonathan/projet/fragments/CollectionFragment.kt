package com.jonathan.projet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonathan.projet.ItemRepository.Singleton.itemList
import com.jonathan.projet.MainActivity
import com.jonathan.projet.R
import com.jonathan.projet.adapter.BurgerAdapter
import com.jonathan.projet.adapter.BurgerItemDecoration

class CollectionFragment(
    private val context : MainActivity
    ) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        //recuperer ma recycler view

        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = BurgerAdapter(context, itemList.filter {it.liked },R.layout.item_vertical_burger)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(BurgerItemDecoration())

        return view
    }



}