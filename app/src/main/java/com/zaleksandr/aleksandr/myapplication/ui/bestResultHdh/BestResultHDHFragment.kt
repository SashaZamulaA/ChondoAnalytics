package com.zaleksandr.aleksandr.myapplication.ui.bestResultHdh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.BottomNavigationViewBehavior
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.CityHDH
import com.zaleksandr.aleksandr.myapplication.ui.bestResultHdh.Adapter.BestHDHResultAdapter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.endOfMonth
import com.zaleksandr.aleksandr.myapplication.util.endOfWeek
import com.zaleksandr.aleksandr.myapplication.util.startOfMonth
import com.zaleksandr.aleksandr.myapplication.util.startOfWeek
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_hdh_best_result.view.*
import java.text.DecimalFormat


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BestResultHDHFragment : Fragment() {


    var toolbar: Toolbar? = null
    var adapter: BestHDHResultAdapter? = null
    private val noteRefCollection = firestoreInstance.collection("ResultHDH")
    var city: CityHDH? = null
    private var pairList: List<Pair<String?, Float>>? = ArrayList()
    var period = 3

    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    private var mLastQueriedDocument: DocumentSnapshot? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_hdh_best_result, container, false)
        setUpRecyclerView(rootView)

        bottomMenuInitHDH(rootView)
        return rootView
    }

    fun perioSelected(periodSelected: ClickByFilter) {

        when (periodSelected) {

            ClickByFilter.WEEK -> {
                period = 1
                adapter?.notifyDataSetChanged()

            }
            ClickByFilter.MONTH -> {
                period = 2
                adapter?.notifyDataSetChanged()

            }
            ClickByFilter.YEAR -> {
                period = 3
                adapter?.notifyDataSetChanged()


            }
        }
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).visibility = View.VISIBLE
        (this.activity!!.toolbar as Toolbar).title = "Best result"
    }

    private fun setUpRecyclerView(rootView: View) {

        rootView.list_best_hdh_result_adapter.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)
        rootView.list_best_hdh_result_adapter.setHasFixedSize(false)
        rootView.list_best_hdh_result_adapter.layoutManager = LinearLayoutManager(context)
        adapter = BestHDHResultAdapter(context!!, pairList!!)
        rootView.list_best_hdh_result_adapter.adapter = adapter
        var num = 0
        val df = DecimalFormat("#.##")
        noteRefCollection
                .orderBy("time", Query.Direction.DESCENDING)
                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {

                        val items = querydocumentSnapshot.result!!
                                .map { it.toObject(CityHDH::class.java) }


                        val itemsToRv = items.groupBy {
                            it.id
                        }.mapValues { it ->
                            it.value.sumBy {

                                num++
                                ((if (it.hdhOne) {1} else 0)
                                        .plus(if (it.hdhTwo) {2} else 0)
                                        .plus(if (it.hdhThree){3} else 0)
                                        .plus(if (it.hdhFour) {2} else 0)
                                        .plus(if (it.hdhFive){3} else 0)
                                        .plus(if (it.hdhSix) {2} else 0)
                                        .plus(if (it.hdhSeven){3} else 0))
                            }.toFloat().div(num).let { df.format(it) }.toFloat()
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


    private fun bottomMenuInitHDH(rootView: View) {
        val layoutParams = rootView.bottom_navigation_best_result_person_hdh.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()
        rootView.bottom_navigation_best_result_person_hdh.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.menu_hdh -> {
                    var num = 0
                    val df = DecimalFormat("#.##")
                    noteRefCollection
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject(CityHDH::class.java) }


                                    val itemsToRv = items.groupBy {
                                        it.id
                                    }.mapValues { it ->
                                        it.value.sumBy {

                                            num++
                                            ((if (it.hdhOne) {
                                                1
                                            } else 0).plus(if (it.hdhSeven) {
                                                7
                                            } else 0))

                                        }.toFloat().div(num).let { df.format(it) }.toFloat()
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
                R.id.menu_month -> {

                    var num = 0
                    val df = DecimalFormat("#.##")
                    noteRefCollection
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject(CityHDH::class.java) }


                                    val itemsToRv = items.groupBy {
                                        it.id
                                    }.mapValues { it ->
                                        it.value.sumBy {

                                            num++
                                            ((if (it.hdhOne) {
                                                1
                                            } else 0).plus(if (it.hdhSeven) {
                                                7
                                            } else 0))

                                        }.toFloat().div(num).let { df.format(it) }.toFloat()
                                    }
                                            .toList()
                                            .sortedByDescending { it.second }



                                    adapter?.setList(itemsToRv)
                                }
                                }
                }
                R.id.menu_week -> {
                    var num = 0
                    val df = DecimalFormat("#.##")
                    noteRefCollection
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject(CityHDH::class.java) }


                                    val itemsToRv = items.groupBy {
                                        it.id
                                    }.mapValues { it ->
                                        it.value.sumBy {

                                            num++
                                            ((if (it.hdhOne) {
                                                1
                                            } else 0).plus(if (it.hdhSeven) {
                                                7
                                            } else 0))

                                        }.toFloat().div(num).let { df.format(it) }.toFloat()
                                    }
                                            .toList()
                                            .sortedByDescending { it.second }



                                    adapter?.setList(itemsToRv)
                                }
                            }
                }
                else -> {
                }
            }
            true
        }
    }
}