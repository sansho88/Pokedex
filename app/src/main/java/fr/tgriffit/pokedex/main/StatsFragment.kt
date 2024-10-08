package fr.tgriffit.pokedex.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import fr.tgriffit.pokedex.data.model.PkmnSharedViewModel
import fr.tgriffit.pokedex.data.model.Stat
import fr.tgriffit.pokedex.databinding.FragmentSkillsBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatsFragment : Fragment() {

    private val sharedViewModel: PkmnSharedViewModel by activityViewModels()
    private var _binding: FragmentSkillsBinding? = null
    private lateinit var radarChart: RadarChart

    private var stats: List<Stat>? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSkillsBinding.inflate(inflater, container, false)
        val root = binding.root

        radarChart = binding.radarChart
        stats = sharedViewModel.stats.value!!

        setupRadarChart()

        sharedViewModel.stats.observe(viewLifecycleOwner) {
            if (it != null)
                setData(it)
        }
        return root
    }

    private fun setupRadarChart() {
        radarChart.description.isEnabled = false
        radarChart.webLineWidth = 1f
        radarChart.webColor = Color.LTGRAY
        radarChart.webLineWidthInner = 1f
        radarChart.webColorInner = Color.LTGRAY
        radarChart.webAlpha = 100
        radarChart.y = 42f
    }

    private fun setData(statsList: List<Stat>) {
        if (statsList.isEmpty()) {
            radarChart.clear()
            return
        }
        val entries = ArrayList<RadarEntry>()

        for (stat in statsList) {
            val entry = RadarEntry(stat.base_stat.toFloat())
            entry.data = stat.stat.name
                .replace('-', ' ')
                .replace("special", "spec.")
                .replace("defense", "Def.")
                .replace("attack", "Att.")
                .replaceFirstChar { it.uppercase() }
            entry.icon = null
            entries.add(entry)
        }

        val dataSet = RadarDataSet(entries, "Skills")
        dataSet.color = Color.rgb(103, 110, 129)
        dataSet.fillColor = Color.parseColor("#ff0099cc")
        dataSet.setDrawFilled(true)
        dataSet.fillAlpha = 180
        dataSet.lineWidth = 2f
        dataSet.isDrawHighlightCircleEnabled = true
        dataSet.setDrawHighlightIndicators(false)
        dataSet.valueTextColor = Color.GREEN

        val data = RadarData(dataSet)
        data.setValueTextSize(24f)
        data.setDrawValues(true)
        data.setValueTextColor(Color.WHITE)

        radarChart.data = data
        radarChart.invalidate()
        radarChart.webColorInner = Color.argb(50,50,50,150)
        radarChart.skipWebLineCount = 1

        val xAxis = radarChart.xAxis
        xAxis.textSize = 24f
        xAxis.textColor = Color.WHITE
        xAxis.yOffset = 0f
        xAxis.xOffset = 0f
        xAxis.valueFormatter = object : ValueFormatter() {
            private val activities = entries.map { it.data.toString() }
            override fun getFormattedValue(value: Float): String {
                return activities[value.toInt() % activities.size]
            }
        }

        val yAxis = radarChart.yAxis
        val highestStat = (statsList.maxOf { it.base_stat } * 1.2).toInt()
        yAxis.setLabelCount(highestStat, false)

        yAxis.textSize = 9f
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = highestStat.toFloat()
        yAxis.textColor = Color.WHITE
        yAxis.setDrawLabels(false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatsFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) = StatsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }


}