package com.jonathan.projet

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.jonathan.projet.ItemRepository.Singleton.databaseRef
import com.jonathan.projet.ItemRepository.Singleton.downloadUri
import com.jonathan.projet.ItemRepository.Singleton.itemList
import com.jonathan.projet.ItemRepository.Singleton.storageReference
import java.net.URI
import java.util.*
import javax.security.auth.callback.Callback

class ItemRepository {

    object Singleton {

        //donner le lien bucket

        private val BUCKET_URL: String = "gs://clickeat-d6d5a.appspot.com"

        //se co a notre espace de stockage

        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

    // se co a la ref item
    val databaseRef= FirebaseDatabase.getInstance().getReference("items")
    // creer une liste qui va contenir nos items
    val itemList = arrayListOf<ItemModel>()

    //contenir le lien de l'image courante

     var downloadUri: Uri? = null
   }
    fun updateData(callback: ()-> Unit){
        // absorber les données depuis la databaseRef
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciennes

               itemList.clear()


                // recolter la liste
                for(ds in snapshot.children){
                    // construire un objet plante
                    val item= ds.getValue(ItemModel::class.java)

                    // verifier que item n'est pas nul
                    if (item != null){
                        // ajouter l'item à notre liste
                        itemList.add(item)
                    }

                }


              // actionner un callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}


        })
    }

    //creer un fonction pour envoyer des fichier sur le storage

    fun uploadImage(file : Uri, callback: () -> Unit){

        val fileName = UUID.randomUUID().toString() + "jpg"
        val ref = storageReference.child(fileName)
        val uploadTask = ref.putFile(file)


        //demar la tache

        uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>> { task ->

            //verifier unn probleme lors de l'envoi

            if(!task.isSuccessful){
                task.exception?.let{ throw it }
            }

            return@Continuation ref.downloadUrl


        }).addOnCompleteListener{ task ->

            //verif
            if(task.isSuccessful){
                //recup image

                downloadUri = task.result
                callback()


            }
        }

    }

    // maj un objet item en bdd
    fun udpateItem(item : ItemModel) =
        // besoin de recup un id dans la bdd donc on rajute un id dans la bdd firebase
        databaseRef.child(item.id).setValue(item)

    //insert new item en bdd

    fun insertItem(item : ItemModel) =
        databaseRef.child(item.id).setValue(item)




        //suppr un item de la base
        fun deleteItem(item:ItemModel)= databaseRef.child(item.id).removeValue()

    }



