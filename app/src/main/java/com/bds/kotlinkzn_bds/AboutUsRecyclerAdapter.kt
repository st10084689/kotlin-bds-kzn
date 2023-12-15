package com.bds.kotlinkzn_bds

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AboutUsRecyclerAdapter(_statements: List<Statement>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var typeRecycler = 0
    private var Statements: List<Statement> = ArrayList()

    init {
        Statements = _statements
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_recycler_card, parent, false)

        // Checking the type of RecyclerView and inflating the corresponding layout
        if (typeRecycler == STATEMENT_REC) {
            // If the type is a statement, inflate the statement layout
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.statement_recycler_card, parent, false)
            return StatementItemHolder(view)
        }

        // If the type is a member or any other type, inflate the default member layout
        return MemberItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (typeRecycler == STATEMENT_REC) {
            val statement = Statements[position]
            val statementHolder = holder as StatementItemHolder // Cast to StatementItemHolder

            // Sets the title and label of the statement.
            statementHolder.statementTitle.setText(statement.title)
            statementHolder.statementLabel.setText(statement.label)

            // Loads the image into the ImageView.
            Glide.with(statementHolder.statementBadge.context)
                .load(statement.image)
                .centerCrop()
                .into(statementHolder.statementBadge)
        } else if (typeRecycler == MEMBER_REC) {
            val memberHolder = holder as MemberItemHolder // Cast to MemberItemHolder
            val statement = Statements[position]
            // Sets the title and label of the statement.
            memberHolder.memberName.setText(statement.title)
            memberHolder.memberDescription.setText(statement.label)
            // Load the image into the ImageView.
            Glide.with(memberHolder.memberImage.context)
                .load(statement.image)
                .centerCrop()
                .into(memberHolder.memberImage)
        }
        if (position == Statements.size - 1) {
            val params = holder.itemView.layoutParams as MarginLayoutParams
            params.rightMargin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                15f,
                holder.itemView.resources.displayMetrics
            ).toInt()
            holder.itemView.layoutParams = params
        }
    }

    fun RecyclerType(_typeRecycler: Int) {
        typeRecycler = _typeRecycler
    }

    override fun getItemCount(): Int {
        return Statements.size
    }

    inner class StatementItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val statementBadge: ImageView
         val statementTitle: TextView
         val statementLabel: TextView

        init {
            statementBadge = itemView.findViewById(R.id.statement_badge)
            statementTitle = itemView.findViewById(R.id.statement_title)
            statementLabel = itemView.findViewById(R.id.statement_label)
        }
    }

    inner class MemberItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val memberName: TextView
         val memberDescription: TextView
         val memberImage: ImageView

        init {
            memberImage = itemView.findViewById(R.id.member_image)
            memberName = itemView.findViewById(R.id.member_name)
            memberDescription = itemView.findViewById(R.id.member_description)
        }
    }

    companion object {
        private const val TAG = "AboutPageRecyclerAdapter"
        private const val STATEMENT_REC = 1
        private const val MEMBER_REC = 0
    }
}



