package com.zaleksandr.aleksandr.myapplication.ui.bestResult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.model.itemsToRv
import com.zaleksandr.aleksandr.myapplication.ui.bestResult.adapter.BestResultAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_best_result.view.*


class BestResultFragment : Fragment() {

    var toolbar: Toolbar? = null
    var adapter: BestResultAdapter? = null
    private val noteRefCollection = firestoreInstance.collection("NewCity")
    var city: City? = null
    private val items: ArrayList<City> = ArrayList()
    private var pairList: List<Pair<String?, Int>>? = ArrayList()

    private var mLastQueriedDocument: DocumentSnapshot? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_best_result, container, false)

        setUpRecyclerView(rootView)

//        bottomMenuInit(rootView)

        return rootView
    }


    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Best result"
    }

    private fun setUpRecyclerView(rootView: View) {

        rootView.list_best_result_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_best_result_adapter.setHasFixedSize(false)
        rootView.list_best_result_adapter.layoutManager = LinearLayoutManager(context)
        adapter = BestResultAdapter(context!!, pairList!!)
        rootView.list_best_result_adapter.adapter = adapter

        noteRefCollection
                .orderBy("time", Query.Direction.DESCENDING)
                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {

                        val items= querydocumentSnapshot.result!!
                                .map { it.toObject<City>(City::class.java) }
                                .filterNot {
                                    it.intro.isNullOrEmpty()
                                    it.onedayWS.isNullOrEmpty()

                                }

                        val itemsToRv = items.groupBy {
                            it.id
                        }.mapValues {
                            it.value.sumBy {
                                Integer.parseInt(it.intro?:"0")
                            }
                        }
                                .toList()
                                .sortedByDescending { it.second }

                        adapter?.setList(itemsToRv)
                    }

                    if (querydocumentSnapshot.result!!.size() != 0) {
//                            empty_individual_result_fragment.visibility = View.GONE
                        mLastQueriedDocument = querydocumentSnapshot.result!!.documents[querydocumentSnapshot.result!!.size() - 1]
                        adapter?.notifyDataSetChanged()
                    } else {
//                            empty_individual_result_fragment.visibility = View.VISIBLE
                        adapter?.notifyDataSetChanged()
                    }
                }
    }
}


//    private fun bottomMenuInit(rootView: View) {
//        val layoutParams = rootView.bottom_navigation_person.layoutParams as CoordinatorLayout.LayoutParams
//        layoutParams.behavior = BottomNavigationViewBehavior()
//        rootView.bottom_navigation_person.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_year -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.YEAR)
//                }
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_month -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.MONTH)
//                }
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_week -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.WEEK)
//                }
//
//                else -> {
//                }
//            }
//            true
//        }
//    }
//
//}