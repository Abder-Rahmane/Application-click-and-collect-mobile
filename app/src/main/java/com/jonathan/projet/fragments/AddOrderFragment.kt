package com.jonathan.projet.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.jonathan.projet.ItemModel
import com.jonathan.projet.ItemRepository
import com.jonathan.projet.ItemRepository.Singleton.downloadUri
import com.jonathan.projet.MainActivity
import com.jonathan.projet.R
import java.util.*

class AddOrderFragment(
    private val context : MainActivity

) : Fragment(){

    private var file: Uri? = null
    private var uploadedImage: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_item, container, false)

        //recup upp image

        uploadedImage =  view.findViewById(R.id.preview_image)

        //recup le button pour charger l'image

        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //lorsque qu'on clique ouvre l'image

        pickupImageButton.setOnClickListener{ pickupImage() }


        //recup le boutton confirmer

        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener { sendForm(view) }

        return  view
    }

    private fun sendForm(view: View){
        val repo = ItemRepository()
        repo.uploadImage(file!!){

            val itemOrder = view.findViewById<EditText>(R.id.order_input).text.toString()
            val itemDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val downloadImageUrl = downloadUri

                //céer un nouvel objet

            val item = ItemModel(

                UUID.randomUUID().toString(),
                itemOrder,
                itemDescription,
                downloadImageUrl.toString(),
            )

            repo.insertItem(item)

        }


    }

    private fun pickupImage(){
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 47)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 &&  resultCode== Activity.RESULT_OK){

            if(data == null || data.data == null) return

           file = data.data

            uploadedImage?.setImageURI(file)
        }
    }
}