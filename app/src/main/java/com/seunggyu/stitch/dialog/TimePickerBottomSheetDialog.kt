import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seunggyu.stitch.databinding.DialogBottomsheetMatchDetailTimeBinding
import com.seunggyu.stitch.viewModel.CreateNewMatchViewModel


class TimePickerBottomSheetDialog :
    BottomSheetDialogFragment() {
    private lateinit var binding: DialogBottomsheetMatchDetailTimeBinding
    private val viewModel by activityViewModels<CreateNewMatchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogBottomsheetMatchDetailTimeBinding.inflate(inflater, container, false)

        dialogInit()

        return binding.root
    }

    private fun dialogInit() {

        binding.npAmpm.maxValue = 1
        binding.npAmpm.minValue = 0
        binding.npAmpm.displayedValues = arrayOf("오전", "오후")
        binding.npHour.maxValue = 12
        binding.npHour.minValue = 1

        binding.npMinute.maxValue = 5
        binding.npMinute.minValue = 0
        binding.npMinute.displayedValues = arrayOf("00", "10", "20", "30", "40", "50")

        viewModel.startTime.value.apply {

        }
        viewModel.startTime.value?.let { _time ->
            if (viewModel.startTime.value == "") {
                binding.npHour.value = 12
                binding.npMinute.value = 0
                binding.npAmpm.value = 1
            } else {
                val (time, minute) = _time.split(":").map { it.toInt() }
                if (time > 11) binding.npAmpm.value = 1
                else binding.npAmpm.value = 0
                binding.npHour.value = time % 12
                binding.npMinute.value = minute / 10
            }
        }

        binding.tvClose.setOnClickListener {
            dismiss()
        }

        binding.tvConfirm.setOnClickListener {
            val hour = if (binding.npAmpm.value == 0 && binding.npHour.value == 12) 0
            else binding.npHour.value
            viewModel.setStartTime(
                binding.npAmpm.value,
                hour.toString(),
                binding.npMinute.value.toString()
            )
            dismiss()
        }

//        binding.npMinute.setOnValueChangedListener { _, _, newVal ->
//            Log.e("npM", newVal.toString())
//            if (newVal == 0) { // npMinute이 0으로 바뀌면
//                binding.npHour.value =
//                    (binding.npHour.value % 12) + 1 // npHour의 value를 1 증가시킨다. (1부터 12까지 순환)
//            }
//        }

        binding.npHour.setOnValueChangedListener { _, oldVal, newVal ->
            if (oldVal == 11 && newVal == 12) {
                when (binding.npAmpm.value) {
                    0 -> binding.npAmpm.value = 1
                    1 -> binding.npAmpm.value = 0
                }
            } else if (oldVal == 12 && newVal == 11) {
                when (binding.npAmpm.value) {
                    0 -> binding.npAmpm.value = 1
                    1 -> binding.npAmpm.value = 0
                }
            }
        }
    }
}
