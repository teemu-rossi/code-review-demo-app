package com.example.demoapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.databinding.ActivityListBinding
import com.example.demoapp.databinding.ListItemBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    private val viewModel = ListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    override fun onResume() {
        super.onResume()

        binding.list.adapter =
            CustomAdapter(viewModel.datesHours.map {
                it.format(DateTimeFormatter.ISO_DATE) + " klo ${it.hour}:00"
            }
                .toTypedArray())
    }
}

/*
Here's a typical example of a simple adapter with a nested ViewHolder that displays a list of data. In this case, the RecyclerView displays a simple list of text elements. The adapter is passed an array of strings containing the text for the ViewHolder elements.
 */
class CustomAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View, val textView: TextView) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding.root, binding.listItemText.also {
            binding.listItemButton.setOnClickListener {
                DetailActivity.date =
                    LocalDate.parse(binding.listItemText.text.take(10))
                DetailActivity.hour =
                    binding.listItemText.text.dropLast(3).takeLast(2).trim().toString().toInt()

                it.context.startActivity(Intent(it.context, DetailActivity::class.java))
            }
        })
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size

}
