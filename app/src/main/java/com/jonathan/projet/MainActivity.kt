package com.jonathan.projet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jonathan.projet.fragments.AddOrderFragment
import com.jonathan.projet.fragments.CollectionFragment
import com.jonathan.projet.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        //importe la bottomnavigationview

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.home_page -> {
                    loadFragment(HomeFragment(this),R.string.home_page_title)
                        return@setOnNavigationItemSelectedListener true

                }
                R.id.collection_recycler_list -> {
                    loadFragment(CollectionFragment(this), R.string.home_page_last_order)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_item_page -> {
                    loadFragment(AddOrderFragment(this), R.string.add_order_page)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {false}
            }
        }


    }

    private fun loadFragment(fragment: Fragment, string: Int) {


        // charger notre ItemRepository
        val repo=ItemRepository()

        findViewById<TextView>(R.id.page_title).text = resources.getString(string)

        //mettre a jour la liste des items
        repo.updateData {
            // Injecter le fragment dans notre boite
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

}

    // lorsqu'on va créer le home fragment n v lui donner l'activite principale en parametre
// ensuite  ce contexte va etre transferer a BurgerAdapter et BurgerADapter va recup le contexte pour transmettre a Glid qui va jouer son role de generateur d'image