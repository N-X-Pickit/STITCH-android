package com.seunggyu.stitch.ui.fragment.newmatch

import TimePickerBottomSheetDialog
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.CalendarDayBinding
import com.seunggyu.stitch.databinding.FragMatchDetailBinding
import com.seunggyu.stitch.viewModel.CreateNewMatchViewModel
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.util.*

@Suppress("UNREACHABLE_CODE")
class MatchDetailFragment : Fragment() {
    private val viewModel by activityViewModels<CreateNewMatchViewModel>()
    private val decimalFormat = DecimalFormat("#,###")

    private val binding by lazy {
        FragMatchDetailBinding.inflate(layoutInflater)
    }
    private val monthCalendarView: CalendarView get() = binding.exOneCalendar

    @RequiresApi(Build.VERSION_CODES.O)
    private val today = LocalDate.now()
    private lateinit var selectedDate: LocalDate

    private var feeInputValue = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        init()
        initObserve()

        return binding.root
    }

    fun init() {
        with(binding) {

            clMatchDetail.setOnClickListener {
                hideKeyboard()
            }

            btnMonthPrev.setOnClickListener {
                val month = monthCalendarView.findFirstVisibleMonth()?.yearMonth
                monthCalendarView.scrollToMonth(month!!.previousMonth)
            }
            btnMonthNext.setOnClickListener {
                val month = monthCalendarView.findFirstVisibleMonth()?.yearMonth
                monthCalendarView.scrollToMonth(month!!.nextMonth)
            }
            btnMatchStarttime.setOnClickListener {
                val bottomSheet = TimePickerBottomSheetDialog()
                fragmentManager?.let { it1 -> bottomSheet.show(it1, bottomSheet.tag) }
            }

            tvMatchStarttimeValue.setOnClickListener {
                hideKeyboard()
                if(clCalendar.visibility == View.GONE) {
                    TransitionManager.beginDelayedTransition(binding.cvStarttime,
                        AutoTransition()
                    )
                    binding.clCalendar.visibility = View.VISIBLE
                }
            }

            btnMatchFeeNo.setOnClickListener {
                hideKeyboard()

                with(binding.btnMatchFeeNo) {
                    setBackgroundResource(R.drawable.button_round)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_12))
                }
                with(binding.btnMatchFeeYes) {
                    setBackgroundResource(R.drawable.button_round_disabled)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_07))
                }
                binding.clMatchFeeValueInner.visibility = View.GONE
                binding.dividerMatchFee.visibility = View.GONE

            }
            btnMatchFeeYes.setOnClickListener {
                hideKeyboard()

                with(binding.btnMatchFeeNo) {
                    setBackgroundResource(R.drawable.button_round_disabled)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_07))
                }
                with(binding.btnMatchFeeYes) {
                    setBackgroundResource(R.drawable.button_round)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_12))
                }
                binding.clMatchFeeValueInner.visibility = View.VISIBLE
                binding.dividerMatchFee.visibility = View.VISIBLE
            }

            btnDetailDurationMinus.setOnClickListener {
                viewModel.removeDuration()
                hideKeyboard()
            }

            btnDetailDurationPlus.setOnClickListener {
                viewModel.addDuration()
                hideKeyboard()
            }

            btnDetailLocation.setOnClickListener {
                viewModel.setLocation("서울특별시 동작구")
                hideKeyboard()
            }

            btnDetailParticipantPlus.setOnClickListener {
                viewModel.addParticipant()
                hideKeyboard()
            }

            btnDetailParticipantMinus.setOnClickListener {
                viewModel.removeParticipant()
                hideKeyboard()
            }
        }

        binding.etMatchName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().length > 30) {
                    binding.tvMatchNameError.visibility = View.VISIBLE
                    binding.dividerMatchName.dividerColor = ContextCompat.getColor(requireContext(), R.color.error)
                    binding.tvMatchNameLenght.setTextColor(ContextCompat.getColor(requireContext(),R.color.error))
                    viewModel.setName("")
                } else {
                    binding.tvMatchNameError.visibility = View.GONE
                    binding.tvMatchNameLenght.visibility = View.VISIBLE
                    binding.tvMatchNameLenght.setTextColor(ContextCompat.getColor(requireContext(),R.color.gray_09))
                    binding.dividerMatchName.dividerColor = ContextCompat.getColor(requireContext(), R.color.gray_07)
                    viewModel.setName(s.toString())
                }

                if(s.toString().isEmpty())  binding.tvMatchNameError.visibility = View.GONE

                binding.tvMatchNameLenght.text = getString(R.string.newmatch_detail_input_length, s.toString().length.toString(), "30")
            }
        })

        binding.etMatchDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().length > 1000) {
                    binding.tvMatchDescriptionError.visibility = View.VISIBLE
                    binding.dividerMatchDescription.dividerColor = ContextCompat.getColor(requireContext(), R.color.error)
                    binding.tvMatchDescriptionLength.setTextColor(ContextCompat.getColor(requireContext(),R.color.error))
                    viewModel.setDetail("")
                } else {
                    binding.tvMatchDescriptionError.visibility = View.GONE
                    binding.tvMatchDescriptionLength.visibility = View.VISIBLE
                    binding.tvMatchDescriptionLength.setTextColor(ContextCompat.getColor(requireContext(),R.color.gray_09))
                    binding.dividerMatchDescription.dividerColor = ContextCompat.getColor(requireContext(), R.color.gray_07)
                    viewModel.setDetail(s.toString())
                }

                if(s.toString().isEmpty())  binding.tvMatchDescriptionLength.visibility = View.GONE

                binding.tvMatchDescriptionLength.text = getString(R.string.newmatch_detail_input_length, s.toString().length.toString(), "1000")
            }
        })

        binding.etMatchFeeValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!TextUtils.isEmpty(s.toString()) && s.toString() != feeInputValue){
                    feeInputValue = decimalFormat.format(s.toString().replace(",","").toDouble())
                    binding.etMatchFeeValue.setText(feeInputValue)
                    binding.etMatchFeeValue.setSelection(feeInputValue.length)
                    val withoutCommaValue = feeInputValue.replace(",", "").toInt()
                    viewModel.setFee(withoutCommaValue.toString())
                }
            }
        })
    }

    fun initObserve() {
        with(viewModel) {
            name.observe(requireActivity()) {
                viewModel.checkAllWritenFlow()
            }

            startTime.observe(requireActivity()) {_time ->
                if(startDate.value!!.isNotEmpty()) {
                    startTimeViewGone()
                    binding.clMatchStarttimeAlert.visibility = View.VISIBLE

                    val arrDate = startDate.value!!.split("-")
                    val arrTime = startTime.value!!.split(":")
                    var ampm = ""
                    var realTime = 0
                    var realMinute = ""
                    if(startAmpm.value == "0") {
                        ampm = "오전"
                        realTime = arrTime[0].toInt()
                    } else {
                        ampm = "오후"
                        realTime = if(arrTime[0].toInt() > 12) arrTime[0].toInt() - 12
                        else arrTime[0].toInt()
                    }
                    realMinute = if(arrTime[1].toInt() != 0) {
                        "${arrTime[1]}분"
                    } else ""
                    binding.tvMatchStarttimeValue.text = "${arrDate[1]}월 ${arrDate[2]}일 $ampm ${realTime}시 ${realMinute}"
                }
                if(_time == "") binding.btnMatchStarttime.text = getString(R.string.newmatch_detail_starttime_time)
                else{
                    binding.btnMatchStarttime.text = _time
                }

                viewModel.checkAllWritenFlow()
            }

            startDate.observe(requireActivity()) {
                if(startTime.value!!.isNotEmpty()) {
                    startTimeViewGone()
                    binding.clMatchStarttimeAlert.visibility = View.VISIBLE

                    val arrDate = startDate.value!!.split("-")
                    val arrTime = startTime.value!!.split(":")
                    var ampm = ""
                    var realTime = 0
                    var realMinute = ""
                    if(startAmpm.value == "0") {
                        ampm = "오전"
                        realTime = arrTime[0].toInt()
                    } else {
                        ampm = "오후"
                        realTime = if(arrTime[0].toInt() > 12) arrTime[0].toInt() - 12
                        else arrTime[0].toInt()
                    }
                    realMinute = if(arrTime[1].toInt() != 0) {
                        "${arrTime[1]}분"
                    } else ""
                    binding.tvMatchStarttimeValue.text = "${arrDate[1]}월 ${arrDate[2]}일 $ampm ${realTime}시 ${realMinute}"
                }
                if(it == "") binding.btnMatchStarttime.text = getString(R.string.newmatch_detail_starttime_time)
                viewModel.checkAllWritenFlow()

            }

            // 소요시간 observer
            duration.observe(requireActivity()) {
                Log.e("vvv", viewModel.duration.value.toString())
                if(viewModel.duration.value == "30분") {
                    binding.btnDetailDurationMinus.isEnabled = false
                    binding.btnDetailDurationMinus.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_11))
                } else {
                    binding.btnDetailDurationMinus.isEnabled = true
                    binding.btnDetailDurationMinus.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_09))
                }

                if(viewModel.duration.value == "5시간") {
                    binding.btnDetailDurationPlus.isEnabled = false
                    binding.btnDetailDurationPlus.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_11))
                } else {
                    binding.btnDetailDurationPlus.isEnabled = true
                    binding.btnDetailDurationPlus.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_09))
                }
                binding.tvMatchDurationValue.text = getString(R.string.newmatch_detail_duration_value, it)
                viewModel.checkAllWritenFlow()
            }

            // 매치장소 observer
            location.observe(requireActivity()) {
                binding.tvMatchLocationHint.text = it
                viewModel.checkAllWritenFlow()
            }

            // 참가인원 observer
            maxCapacity.observe(requireActivity()) {
                binding.tvMatchParticipantValue.text = getString(R.string.newmatch_detail_Participant_value, it)
                viewModel.checkAllWritenFlow()
                if(viewModel.maxCapacity.value == "1") {
                    binding.btnDetailParticipantMinus.isEnabled = false
                    binding.btnDetailParticipantMinus.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_11))
                } else {
                    binding.btnDetailParticipantMinus.isEnabled = true
                    binding.btnDetailParticipantMinus.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_09))
                }

                if(viewModel.maxCapacity.value == "25") {
                    binding.btnDetailParticipantPlus.isEnabled = false
                    binding.btnDetailParticipantPlus.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_11))
                } else {
                    binding.btnDetailParticipantPlus.isEnabled = true
                    binding.btnDetailParticipantPlus.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_09))
                }
            }

            // 참가비 observer
            fee.observe(requireActivity()) {
                if(it == "") {
                    binding.tvMatchFeeValueWon.visibility = View.GONE
                } else {
                    binding.tvMatchFeeValueWon.visibility = View.VISIBLE
                }
                Log.e("fee", it)
                viewModel.checkAllWritenFlow()
            }

        }
    }

    fun startTimeViewGone() {
        binding.clCalendar.visibility = View.GONE
        TransitionManager.beginDelayedTransition(binding.cvStarttime,
            AutoTransition()
        )

        Log.e("매치 날짜/시간","${viewModel.startDate.value} ${viewModel.startTime.value}")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val daysOfWeek = daysOfWeek()
        binding.legendLayout.root.children
            .map { it as TextView }
            .forEachIndexed { index, textView ->
                when (index) {
                    0 -> {
                        textView.text = "일"
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.error
                            )
                        )
                    }
                    1 -> {
                        textView.text = "월"
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_04
                            )
                        )
                    }
                    2 -> {
                        textView.text = "화"
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_04
                            )
                        )
                    }
                    3 -> {
                        textView.text = "수"
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_04
                            )
                        )
                    }
                    4 -> {
                        textView.text = "목"
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_04
                            )
                        )
                    }
                    5 -> {
                        textView.text = "금"
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_04
                            )
                        )
                    }
                    6 -> {
                        textView.text = "토"
                        textView.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.secondary
                            )
                        )
                    }

                }
            }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        setupMonthCalendar(startMonth, endMonth, currentMonth, daysOfWeek)

    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupMonthCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<java.time.DayOfWeek>,
    ) {
        val javaStartMonth = startMonth.monthValue

        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = CalendarDayBinding.bind(view).exOneDayText

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        dateClicked(date = day.date)
                    }
                }
            }
        }
        monthCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                bindDate(data.date, container.textView, data.position == DayPosition.MonthDate)
            }
        }
        monthCalendarView.monthScrollListener = { updateTitle() }
        monthCalendarView.setup(startMonth, endMonth, daysOfWeek.first())
        monthCalendarView.scrollToMonth(currentMonth)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindDate(date: LocalDate, textView: TextView, isSelectable: Boolean) {
        textView.text = date.dayOfMonth.toString()

        if (isSelectable.not()) {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_10))
            textView.background = null
            return
        }

        if (::selectedDate.isInitialized && selectedDate == date) {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_12))
            textView.setBackgroundResource(R.drawable.calendar_selected_background)
            return
        }

//        if(::selectedDate.isInitialized && selectedDate != date) {
//            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_04))
//            textView.background = null
//            Log.e("asdasd","asdasdasdas")
//        }
        if (date < today) {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_10))
            textView.background = null
            return
        }
        if (date == today) {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_12))
            textView.setBackgroundResource(R.drawable.calendar_today_background)
        } else if (date.dayOfWeek.equals(java.time.DayOfWeek.SUNDAY)) {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
            textView.background = null
        } else if (date.dayOfWeek.equals(java.time.DayOfWeek.SATURDAY)) {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary))
            textView.background = null
        } else {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_04))
            textView.background = null
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateClicked(date: LocalDate) {
        if (date < today) {
            Log.e("이전 날짜 선택됨", "")
            if (::selectedDate.isInitialized) Log.e("현재 선택된 날짜", "$selectedDate")
            return
        }
        if (::selectedDate.isInitialized) {
            // Remove the background for the previously selected date
            monthCalendarView.notifyDateChanged(selectedDate)
        }

        selectedDate = date
        // Refresh both calendar views
        monthCalendarView.notifyDateChanged(date)
        Log.e("seleted", selectedDate.toString())
        viewModel.setStartDate(selectedDate.toString())
        Log.e("ViewModel startDate", viewModel.startDate.value.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun updateTitle() {
        val month = monthCalendarView.findFirstVisibleMonth()?.yearMonth ?: return
        val koreanMonth = when (month.month) {
            Month.JANUARY -> "1월"
            Month.FEBRUARY -> "2월"
            Month.MARCH -> "3월"
            Month.APRIL -> "4월"
            Month.MAY -> "5월"
            Month.JUNE -> "6월"
            Month.JULY -> "7월"
            Month.AUGUST -> "8월"
            Month.SEPTEMBER -> "9월"
            Month.OCTOBER -> "10월"
            Month.NOVEMBER -> "11월"
            Month.DECEMBER -> "12월"
        }
        if(binding.tvCalendarIndicator.text == "") binding.clCalendar.visibility = View.GONE
        binding.tvCalendarIndicator.text = "$koreanMonth ${month.year}"
    }

    private fun hideKeyboard() {
        // 키보드 숨기기 메서드
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}