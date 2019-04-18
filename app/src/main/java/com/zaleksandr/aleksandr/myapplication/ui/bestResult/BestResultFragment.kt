package com.zaleksandr.aleksandr.myapplication.ui.bestResult

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.BottomNavigationViewBehavior
import com.zaleksandr.aleksandr.myapplication.model.City
import com.zaleksandr.aleksandr.myapplication.ui.bestResult.adapter.BestResultAdapter
import com.zaleksandr.aleksandr.myapplication.util.*
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_best_result.view.*
import android.view.MenuInflater
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import com.zaleksandr.aleksandr.myapplication.R


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BestResultFragment : Fragment(){


    var toolbar: Toolbar? = null
    var adapter: BestResultAdapter? = null
    private val noteRefCollection = firestoreInstance.collection("NewCity")
    var city: City? = null
    private var pairList: List<Pair<String?, Int>>? = ArrayList()
    var period = 3
    enum class ClickByFilter {
        MONTH, WEEK, YEAR
    }

    private var mLastQueriedDocument: DocumentSnapshot? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_best_result, container, false)
        setUpRecyclerView(rootView)

        bottomMenuInit(rootView)
        return rootView
    }



//   fun onMenuItemSelect(item: MenuItem): Boolean {
//        showPopup(view!!.findViewById(item.itemId))
//        return true
//    }

//
//    private fun showPopup(view: View) {
//        val popup = PopupMenu(context, view)
//        try {
//            // Reflection apis to enforce show icon
//            val fields = popup.javaClass.declaredFields
//            for (field in fields) {
//                if (field.name == POPUP_CONSTANT) {
//                    field.isAccessible = true
//                    val menuPopupHelper = field.get(popup)
//                    val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
//                    val setForceIcons = classPopupHelper.getMethod(POPUP_FORCE_SHOW_ICON, Boolean::class.javaPrimitiveType)
//                    setForceIcons.invoke(menuPopupHelper, true)
//                    break
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        popup.menuInflater.inflate(com.zaleksandr.aleksandr.myapplication.R.menu.velue, popup.menu)
//        popup.setOnMenuItemClickListener(this@BestResultFragment)
//        popup.show()
//    }
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

                        val items = querydocumentSnapshot.result!!
                                .map { it.toObject<City>(City::class.java) }
                                .filterNot {
                                    (it.name == "Kyiv Chondoso" || it.name == "Daniela Aldasoro ")

                                }

                        val itemsToRv = items.groupBy {
                            it.id
                        }
                                .mapValues {
                                    it.value.sumBy {

                                        Integer.parseInt(if (it.intro.isNullOrEmpty() || it.intro.isNullOrBlank() || it.intro == "") {
                                            ("0").toString()
                                        } else it.intro.toString())
                                                .plus(Integer.parseInt(if (it.onedayWS.isNullOrEmpty() || it.onedayWS.isNullOrBlank() || it.onedayWS == "") {
                                                    ("0").toString()
                                                } else it.onedayWS) * 3)
                                                .plus(Integer.parseInt(if (it.twoDayWS.isNullOrEmpty() || it.twoDayWS.isNullOrBlank() || it.twoDayWS == "") {
                                                    ("0").toString()
                                                } else it.twoDayWS) * 12)
                                                .plus(Integer.parseInt(if (it.twOneDay.isNullOrEmpty() || it.twOneDay.isNullOrBlank() || it.twOneDay == "") {
                                                    ("0").toString()
                                                } else it.twOneDay) * 40)
                                                .plus(Integer.parseInt(if (it.nwet.isNullOrEmpty() || it.nwet.isNullOrBlank() || it.nwet == "") {
                                                    ("0").toString()
                                                } else it.nwet) * 80)
                                    }
                                }
                                .toList()
                                .filterNot { it.second == 0 }
                                .sortedByDescending { it.second }

                        val itemsToRvIntro = items.groupBy {
                            it.name

                        }

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


    private fun bottomMenuInit(rootView: View) {
        val layoutParams = rootView.bottom_navigation_best_result_person.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()
        rootView.bottom_navigation_best_result_person.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                com.zaleksandr.aleksandr.myapplication.R.id.menu_year -> {
                    adapter?.perioSelected(BestResultAdapter.ClickByFilter.YEAR)
                    perioSelected(ClickByFilter.YEAR)
                    noteRefCollection
                            .whereGreaterThanOrEqualTo("time", startOfYear())
                            .whereLessThanOrEqualTo("time", endOfYear())
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject<City>(City::class.java) }
                                            .filterNot {
                                                (it.name == "Kyiv Chondoso" || it.name == "Daniela Aldasoro ")
                                            }

                                    val itemsToRv = items.groupBy {
                                        it.id
                                    }
                                            .mapValues {
                                                it.value.sumBy {

                                                    Integer.parseInt(if (it.intro.isNullOrEmpty() || it.intro.isNullOrBlank() || it.intro == "") {
                                                        ("0").toString()
                                                    } else it.intro.toString())
                                                            .plus(Integer.parseInt(if (it.onedayWS.isNullOrEmpty() || it.onedayWS.isNullOrBlank() || it.onedayWS == "") {
                                                                ("0").toString()
                                                            } else it.onedayWS) * 3)
                                                            .plus(Integer.parseInt(if (it.twoDayWS.isNullOrEmpty() || it.twoDayWS.isNullOrBlank() || it.twoDayWS == "") {
                                                                ("0").toString()
                                                            } else it.twoDayWS) * 12)
                                                            .plus(Integer.parseInt(if (it.twOneDay.isNullOrEmpty() || it.twOneDay.isNullOrBlank() || it.twOneDay == "") {
                                                                ("0").toString()
                                                            } else it.twOneDay) * 40)
                                                            .plus(Integer.parseInt(if (it.nwet.isNullOrEmpty() || it.nwet.isNullOrBlank() || it.nwet == "") {
                                                                ("0").toString()
                                                            } else it.nwet) * 80)
                                                }
                                            }
                                            .toList()
                                            .filterNot { it.second == 0 }
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
                com.zaleksandr.aleksandr.myapplication.R.id.menu_month -> {
                    adapter?.perioSelected(BestResultAdapter.ClickByFilter.MONTH)
                    perioSelected(ClickByFilter.MONTH)
                    noteRefCollection
                            .whereGreaterThanOrEqualTo("time", startOfMonth())
                            .whereLessThanOrEqualTo("time", endOfMonth())
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject<City>(City::class.java) }
                                            .filterNot {
                                                (it.name == "Kyiv Chondoso" || it.name == "Daniela Aldasoro ")

                                            }

                                    val itemsToRv = items.groupBy {
                                        it.id
                                    }
                                            .mapValues {
                                                it.value.sumBy {

                                                    Integer.parseInt(if (it.intro.isNullOrEmpty() || it.intro.isNullOrBlank() || it.intro == "") {
                                                        ("0").toString()
                                                    } else it.intro.toString())
                                                            .plus(Integer.parseInt(if (it.onedayWS.isNullOrEmpty() || it.onedayWS.isNullOrBlank() || it.onedayWS == "") {
                                                                ("0").toString()
                                                            } else it.onedayWS) * 3)
                                                            .plus(Integer.parseInt(if (it.twoDayWS.isNullOrEmpty() || it.twoDayWS.isNullOrBlank() || it.twoDayWS == "") {
                                                                ("0").toString()
                                                            } else it.twoDayWS) * 12)
                                                            .plus(Integer.parseInt(if (it.twOneDay.isNullOrEmpty() || it.twOneDay.isNullOrBlank() || it.twOneDay == "") {
                                                                ("0").toString()
                                                            } else it.twOneDay) * 40)
                                                            .plus(Integer.parseInt(if (it.nwet.isNullOrEmpty() || it.nwet.isNullOrBlank() || it.nwet == "") {
                                                                ("0").toString()
                                                            } else it.nwet) * 80)
                                                }
                                            }
                                            .toList()
                                            .filterNot { it.second == 0 }
                                            .sortedByDescending { it.second }

                                    val itemsToRvIntro = items.groupBy {
                                        it.id

                                    }

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
                com.zaleksandr.aleksandr.myapplication.R.id.menu_week -> {
                    adapter?.perioSelected(BestResultAdapter.ClickByFilter.WEEK)
                    perioSelected(ClickByFilter.WEEK)
                    noteRefCollection
                            .whereGreaterThanOrEqualTo("time", startOfWeek())
                            .whereLessThanOrEqualTo("time", endOfWeek())
                            .orderBy("time", Query.Direction.DESCENDING)
                            .get().addOnCompleteListener { querydocumentSnapshot ->
                                if (querydocumentSnapshot.isSuccessful) {

                                    val items = querydocumentSnapshot.result!!
                                            .map { it.toObject<City>(City::class.java) }
                                            .filterNot {
                                                (it.name == "Kyiv Chondoso" || it.name == "Daniela Aldasoro ")

                                            }

                                    val itemsToRv = items.groupBy {
                                        it.id
                                    }
                                            .mapValues {
                                                it.value.sumBy {

                                                    Integer.parseInt(if (it.intro.isNullOrEmpty() || it.intro.isNullOrBlank() || it.intro == "") {
                                                        ("0").toString()
                                                    } else it.intro.toString())
                                                            .plus(Integer.parseInt(if (it.onedayWS.isNullOrEmpty() || it.onedayWS.isNullOrBlank() || it.onedayWS == "") {
                                                                ("0").toString()
                                                            } else it.onedayWS) * 3)
                                                            .plus(Integer.parseInt(if (it.twoDayWS.isNullOrEmpty() || it.twoDayWS.isNullOrBlank() || it.twoDayWS == "") {
                                                                ("0").toString()
                                                            } else it.twoDayWS) * 12)
                                                            .plus(Integer.parseInt(if (it.twOneDay.isNullOrEmpty() || it.twOneDay.isNullOrBlank() || it.twOneDay == "") {
                                                                ("0").toString()
                                                            } else it.twOneDay) * 40)
                                                            .plus(Integer.parseInt(if (it.nwet.isNullOrEmpty() || it.nwet.isNullOrBlank() || it.nwet == "") {
                                                                ("0").toString()
                                                            } else it.nwet) * 80)
                                                }
                                            }
                                            .toList()
                                            .filterNot { it.second == 0 }
                                            .sortedByDescending { it.second }

                                    val itemsToRvIntro = items.groupBy {
                                        it.name

                                    }

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
                else -> {
                }
            }
            true
        }
    }
}