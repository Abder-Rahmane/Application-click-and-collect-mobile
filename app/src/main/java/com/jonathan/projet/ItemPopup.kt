package com.jonathan.projet

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jonathan.projet.adapter.BurgerAdapter

class ItemPopup(
    private val adapter: BurgerAdapter,
    private val currentItem : ItemModel
    ) : Dialog(adapter.context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_items_details)
        setupComponents()
        setupCloseButton()


    }

    private fun updateStar(button: ImageView){
        if(currentItem.liked){
            button.setImageResource(R.drawable.ic_like)
        }
        else {
            button.setImageResource(R.drawable.ic_unlike)
        }
    }




    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.ic_close).setOnClickListener{
            // fermer la fenetre
            dismiss()
        }
    }


    private fun setupComponents(){
      // actualiser l'image de l'item
         val itemImage= findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentItem.imageUrl)).into(itemImage)

        // actualiser le nom de l'item
        findViewById<TextView>(R.id.pop_up_order_category_title).text = currentItem.name

        //actualiser la desc de l'item
        findViewById<TextView>(R.id.pop_up_order_description_subtitle).text= currentItem.description


      }
    }