package com.bds.kotlinkzn_bds
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AboutUsFragment : Fragment() {

    // RecyclerViews for different lists
    private lateinit var membersRecView: RecyclerView
    private lateinit var statementRecView: RecyclerView

    // Lists to store data for RecyclerViews
    private val statements = ArrayList<Statement>()
    private val services = ArrayList<Service>()
    private val members = ArrayList<Statement>()

    companion object {
        private const val TAG = "AboutUsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_us, container, false)
        init(view)// init method for Initializing the views and data
        return view
    }

    private fun init(view: View) {
        // Adding data to lists
        addStatementList()
        addMembersList()
        addServicesList()


        statementRecView = view.findViewById(R.id.statementRecycler)// Setting up RecyclerView for statements
        statementRecView.setHasFixedSize(true)
        statementRecView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val statementAdapter = AboutUsRecyclerAdapter(statements)
        statementAdapter.RecyclerType(0)
        statementRecView.adapter = statementAdapter

        membersRecView = view.findViewById(R.id.memberRecycler) // Setting up RecyclerView for members
        membersRecView.setHasFixedSize(true)
        membersRecView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val memberAdapter = AboutUsRecyclerAdapter(members)
        statementAdapter.RecyclerType(1)
        membersRecView.adapter = memberAdapter



        statementRecView = view.findViewById(R.id.ServicesRecycler) // Setting up RecyclerView for services
        statementRecView.setHasFixedSize(true)
        statementRecView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val serviceAdapter = ServiceRecyclerAdapter(services)
        statementRecView.adapter = serviceAdapter
    }


    // method to populate the statements list
    private fun addStatementList() {
        val statementOne = Statement(
            "Vision Statement",
            "To be the premier provider of services and opportunities that ultimately improve the quality of life of Blind, Deaf, Deafblind persons",
            R.drawable.vision_statement
        )
        statements.add(statementOne)

        val statementTwo = Statement(
            "Mission Statement",
            "Our Mission statement is to ensure that all Blind, Partially-Sighted, Deaf, Hard of Hearing and Deafblind people are integrated into the community, as members who contribute and benefit from society alongside their able- bodied counterparts who do not have these disabilities.",
            R.drawable.mission_statement
        )
        statements.add(statementTwo)

        val statementThree = Statement(
            "V N Naik School For The Deaf",
            "Established in 1983, the school is located in Inanda (Outer Durban), educating Hard of Hearing and Deaf learners and hosting children in the school's boarding establishment.",
            R.drawable.v_n_naik_school_for_the_deaf
        )
        statements.add(statementThree)

        val statementFour = Statement(
            "Arthur Blaxall School For The Blind",
            "The school was established in 1954, and is currently situated in Pietermaritzburg, educating Partially Sighted and Blind learners.",
            R.drawable.blaxall_school_for_the_blind
        )
        statements.add(statementFour)

        for (i in statements.indices) {
            Log.d(TAG, "addStatementList: " + statements[i].title)
        }
    }

    //metod to populate the members
    private fun addMembersList() {
        val memberOne = Statement(
            "Mr. Vinesh Gokool",
            "Diploma in Business Management and Masters of Administration Degree. He is currently the Managing Director for Atlas Printers, Dynamic Digital Print, Atlas Calenders and Diaries and Atlas Packaging.",
            R.drawable.mr_vinesh_gokool
        )
        members.add(memberOne)

        val memberTwo = Statement(
            "Mr. Ajit Valjee",
            "Mr Ajit Valjee is the managing director of JMV Textilescialist.",
            R.drawable.mr_ajit_valjee
        )
        members.add(memberTwo)

        val memberThree = Statement(
            "Mr. Raven Harkoo",
            "Bachelor of Law of Degree. Long standing attorney in KwaZulu-Natal and Litigation and Labour Specialist.",
            R.drawable.mr_raven_harkoo
        )
        members.add(memberThree)

        val memberFour = Statement(
            "Mrs A Ramsumer (Treasurer)",
            "Anita Ramsumer is a chartered accountant, with 13 years in the NGO sector.",
            R.drawable.mrs_a_ramsumer
        )
        members.add(memberFour)

        val memberFive = Statement(
            "Mrs A Sewkuran",
            "Diploma in Cost and Management Accounting, A certificate in Adult Education and Training and a BCOM Law Degree.",
            R.drawable.mrs_a_sewkuran
        )
        members.add(memberFive)

        val memberSix = Statement(
            "Advocate Ramesh Ramdass",
            "Bachelor of Paedagogics, Bachelor of Medicine and Bachelor of Surgery, Master of Medicine, Diploma in Family Medicine and Certificate in Medical Law. Currently practices as an Advocate, American Board of Independent Medical Examiner and is a member of the Bar Council.",
            R.drawable.advocate_ramesh_ramdass
        )
        members.add(memberSix)

        val memberSeven = Statement(
            "Mrs Anuradha Kallideen",
            "Bachelor of Law of Degree. Currently serving as an attorney in the Private Sector. Sits on the Rental Housing Tribunal.",
            R.drawable.mrs_anuradha_kallideen
        )
        members.add(memberSeven)

        val memberEight = Statement(
            "Mrs Anoosha Hanuman",
            "Diploma in Library and information Technology and Diploma in Education.",
            R.drawable.mrs_anoosha_hanuman
        )
        members.add(memberEight)
    }

    //method to populate the services list
    private fun addServicesList() {
        val serviceOne = Service(
            "DEAFBLIND/MULITDISABLED SERVICES",
            R.drawable.multidisabled_services
        )
        services.add(serviceOne)

        val serviceTwo = Service(
            "EDUCATION AND TRAINING",
            R.drawable.education
        )
        services.add(serviceTwo)

        val serviceThree = Service(
            "SPORT AND DEVELOPMENT",
            R.drawable.sports
        )
        services.add(serviceThree)

        val serviceFour = Service(
            "OPTOMETRY ASSESSMENTS",
            R.drawable.optometric_low_vision
        )
        services.add(serviceFour)

        val serviceFive = Service(
            "ADVOCACY",
            R.drawable.advocacy
        )
        services.add(serviceFive)

        val serviceSix = Service(
            "COMPUTER LITERACY TRAINING",
            R.drawable.computer_literacy
        )
        services.add(serviceSix)

        val ServiceSeven = Service(
            "BRAILLE LITERACY",
            R.drawable.braille_literacy
        )
        services.add(ServiceSeven)

        val serviceEight = Service(
            "SOUTH AFRICAN SIGN LANGUAGE",
            R.drawable.sasl
        )
        services.add(serviceEight)

        val serviceNine = Service(
            "HYDROPONICS (PIETERMARITZBURG REGIONAL COMMITEE)",
            R.drawable.hyponics
        )
        services.add(serviceNine)

        val serviceTen = Service(
            "PROFESSIONAL SOCIAL WORK SERVICES",
            R.drawable.social_work_services
        )
        services.add(serviceTen)
    }
}
