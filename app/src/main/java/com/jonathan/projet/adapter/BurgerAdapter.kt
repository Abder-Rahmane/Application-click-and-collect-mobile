package com.jonathan.projet.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonathan.projet.*

class BurgerAdapter(
    val context: MainActivity,
    private val itemList: List<ItemModel>,
    private val layoutId: Int) : RecyclerView.Adapter<BurgerAdapter.ViewHolder>(){

    // boite pour ranger tout les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val burgerImage = view.findViewById<ImageView>(R.id.image_item)
        val burgerName:TextView? = view.findViewById(R.id.name_item)
        val burgerDescription: TextView?= view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // recuper les info de burger

        val currentItem = itemList[position]

        // recup le repository

        val repo = ItemRepository()

        // utiliser glide pur recuperer l'image a partir de son lien
        // into permet de l'inclure dans un composant existant
        Glide.with(context).load(Uri.parse(currentItem.imageUrl)).into(holder.burgerImage)


        // MAJ le nom numero nom de l'item
        holder.burgerName?.text = currentItem.name

        //MAJ La desc de l'item
        holder.burgerDescription?.text = currentItem.description


        //MAJ La desc de l'item
        holder.burgerDescription?.text = currentItem.description

        // verif si l'item a ete liké
        if ( currentItem.liked){
            holder.starIcon.setImageResource(R.drawable.ic_like)

       }
        else{
           holder.starIcon.setImageResource(R.drawable.ic_unlike)
        }

        // rajouter une interaction sur cette etoile
        holder.starIcon.setOnClickListener{

            // inverse si le bouton est like ou nn
            currentItem.liked = !currentItem.liked
            // maj l'objet item
            repo.udpateItem(currentItem)

        }
       // INTERACTION LORS DU CLIC SUR UN ITEM

        holder.itemView.setOnClickListener{
            //afficher la popup
            ItemPopup(this,currentItem).show()
        }




    }

    override fun getItemCount(): Int = itemList.size
    }
