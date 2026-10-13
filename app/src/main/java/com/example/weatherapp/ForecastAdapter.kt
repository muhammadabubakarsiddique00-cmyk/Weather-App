package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ForecastAdapter(
    private val forecastList: ArrayList<ForecastModel>
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    class ForecastViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtTime: TextView =
            itemView.findViewById(R.id.txtTime)

        val txtTemp: TextView =
            itemView.findViewById(R.id.txtTemp)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)

        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ForecastViewHolder,
        position: Int
    ) {

        val forecast = forecastList[position]

        holder.txtTime.text = forecast.time
        holder.txtTemp.text = "${forecast.temperature}°C"
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}