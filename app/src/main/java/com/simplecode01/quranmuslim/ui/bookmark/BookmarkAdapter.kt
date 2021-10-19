package com.simplecode01.quranmuslim.ui.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.l4digital.fastscroll.FastScroller
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.ItemBookmarkBinding
import com.simplecode01.quranmuslim.model.Bookmark
import java.security.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class BookmarkAdapter(val listBookmark: List<Bookmark>, val clickListener: ((bookmark: Bookmark)->Unit))
    : RecyclerView.Adapter<BookmarkAdapter.QuranBookmarkViewHolder>(),FastScroller.SectionIndexer {

    var deleteOnClickListener: ((Bookmark) -> Unit)? = null
    var layoutbookmark: ((Bookmark) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranBookmarkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        return QuranBookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuranBookmarkViewHolder, position: Int) {
        val bookmark = listBookmark[position]
        holder.bindView(bookmark)
        holder.itemView.setOnClickListener{
            clickListener.invoke(bookmark)
        }
        holder.binding.deleteBookmark.setOnClickListener {
            deleteOnClickListener?.invoke(bookmark)
        }
        holder.binding.layoutBookmark.setOnClickListener {
            layoutbookmark?.invoke(bookmark)
        }
    }

    override fun getItemCount(): Int {
        return listBookmark.size
    }

    class QuranBookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemBookmarkBinding by viewBinding()

        fun bindView(bookmark: Bookmark) {
            binding.textSurahNumber.text = bookmark.ayahNumber.toString()
            binding.textSurahName.text = "Qs. " + bookmark.nameSurah
            binding.textTanggal.text = getDateTime(bookmark.timestamp)

        }
        fun getDateTime(timestamp: Long): String? {
            return try{
                val sdf = SimpleDateFormat("dd/MMM/yyyy hh:mm")
                val netDate = Date().apply {
                    time = timestamp
                }
                sdf.format(netDate)
            }catch (e: Exception){
                e.toString()
            }
        }
    }
    override fun getSectionText(position: Int): CharSequence {
        val bookmark = listBookmark[position]
        val popuptext = "${bookmark.surahNumber} : ${bookmark.nameSurah}"
        return popuptext
    }
}