package com.bds.kotlinkzn_bds

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignLanguage : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var phraseList: List<Phrase>? = null
    private var signsList: List<Sign>? = null
    private lateinit var signProgBar:ProgressBar
    private lateinit var alphabetTxt: TextView
    private lateinit var phraseTxt: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sign_language, container, false)

        signProgBar = view.findViewById(R.id.signProgressBar);
        signProgBar.visibility = View.VISIBLE
        alphabetTxt = view.findViewById(R.id.alphabetSignsTxt)
        alphabetTxt.visibility = View.GONE
        phraseTxt = view.findViewById(R.id.phraseSignsTxt)
        phraseTxt.visibility = View.GONE

        getSignsData(view)
        getPhraseData(view)

        return view
    }
        private fun getPhraseData(view: View) {
        val apiService = ApiService()
        val call: Call<PhrasesResponse> = apiService.getPhrases()

        val alphabetRecycler = view.findViewById<RecyclerView>(R.id.phraseSignRecycler)
        alphabetRecycler.visibility = View.GONE

        call.enqueue(object : Callback<PhrasesResponse> {
            override fun onResponse(
                call: Call<PhrasesResponse>,
                response: Response<PhrasesResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    phraseList = response.body()?.phrases

                    alphabetRecycler.setHasFixedSize(true)
                    alphabetRecycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    val alphabetAdapter = PhraseSignLanguageRecyclerAdapter(phraseList!!)
                    alphabetRecycler.adapter = alphabetAdapter
                    alphabetRecycler.visibility = View.VISIBLE
                    signProgBar.visibility = View.GONE
                    alphabetTxt.visibility = View.VISIBLE
                } else {

                }
            }
            override fun onFailure(call: Call<PhrasesResponse>, t: Throwable) {

                Log.d(TAG, "onFailure e: + failed")

            }
        })
    }

    private fun getSignsData(view: View) {
        val apiService = ApiService()
        val call: Call<SignsResponse> = apiService.getSigns()
        val phraseRecycler = view.findViewById<RecyclerView>(R.id.alphabetSignRecycler)
        phraseRecycler.visibility = View.GONE


        call.enqueue(object : Callback<SignsResponse> {
            override fun onResponse(
                call: Call<SignsResponse>,
                response: Response<SignsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    signsList = response.body()?.signs

                    phraseRecycler.setHasFixedSize(true)
                    phraseRecycler.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    val Adapter = AlphabetSignLanguageRecyclerAdapter(signsList!!)
                    phraseRecycler.adapter = Adapter
                    phraseRecycler.visibility = View.VISIBLE
                    phraseTxt.visibility = View.VISIBLE
                } else {

                }
            }
            override fun onFailure(call: Call<SignsResponse>, t: Throwable) {

                Log.d(TAG, "onFailure e: + failed")

            }
        })
    }


}
