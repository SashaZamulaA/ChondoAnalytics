package no.hyper.demos.recipes.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_is_word_list.view.*

class WordAdapter : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private val firebaseRef = FirebaseDatabase.getInstance().getReference("recipes")

    private val recipes = arrayListOf<HDHModel>()

    init {
        firebaseRef.orderByChild("name").addChildEventListener(object : ChildEventListener {

            override fun onChildMoved(snapshot: DataSnapshot, previousKey: String?) {
                val previousIndex = recipes.indexOfFirst { it.id == snapshot.key }
                val recipe = buildRecipe(snapshot)

                recipes.removeAt(previousIndex)

                val newIndex = recipes.indexOfFirst { it.id == previousKey } + 1
                recipes.add(newIndex, recipe)

                notifyItemMoved(previousIndex, newIndex)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousKey: String?) {
                val index = recipes.indexOfFirst { it.id == previousKey } + 1
                val recipe = buildRecipe(snapshot)

                recipes[index] = recipe
                notifyItemChanged(index)
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousKey: String?) {
                val previousIndex = recipes.indexOfFirst { it.id == previousKey }
                val recipe = buildRecipe(snapshot)
                val index = previousIndex + 1

                recipes.add(index, recipe)
                notifyItemInserted(index)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val recipe = findRecipe(snapshot)
                val index = recipes.indexOf(recipe)

                recipes.remove(recipe)
                notifyItemRemoved(index)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(this.javaClass.simpleName, "Firebase child event cancelled", error.toException())
            }

            private fun buildRecipe(snapshot: DataSnapshot): HDHModel {
                val key = snapshot.key
                val word = snapshot.child("name").getValue(String::class.java)


                return HDHModel(key!!, word!!)
            }

            private fun findRecipe(snapshot: DataSnapshot) = recipes.find { it.id == snapshot.key }

        })
    }

    override fun getItemCount() = recipes.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_is_word_list, parent, false)
        return ViewHolder(view)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(hdhModel: HDHModel) {
            itemView.apply {
                name.text = hdhModel.name
                category.text = hdhModel.category
            }
        }
    }
}