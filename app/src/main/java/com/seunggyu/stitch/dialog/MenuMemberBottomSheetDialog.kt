import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seunggyu.stitch.GlobalApplication
import com.seunggyu.stitch.databinding.DialogBottomsheetMatchDetailTimeBinding
import com.seunggyu.stitch.databinding.DialogBottomsheetMenuMemberBinding
import com.seunggyu.stitch.ui.ReportMatchActivity
import com.seunggyu.stitch.viewModel.CreateNewMatchViewModel

// accessType
// 0 : 매치에서 참여자인경우 (신고하기, 매치취소하기, 취소)
// 1 : 매치에서 참여자가 아닌경우 (신고하기, 취소)
// 2 : 프로필 상대방 신고하는 경우 (신고하기, 취소)

class MenuMemberBottomSheetDialog(accessType: Int) :
    BottomSheetDialogFragment() {
    private lateinit var binding: DialogBottomsheetMenuMemberBinding
    private val viewModel by activityViewModels<MatchDetailPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogBottomsheetMenuMemberBinding.inflate(inflater, container, false)

        dialogInit()

        return binding.root
    }

    private fun dialogInit() {
        binding.btnMenuReport?.setOnClickListener {
            val intent = Intent(requireContext(), ReportMatchActivity::class.java)
            intent.putExtra("matchId", viewModel.passedMatchId.toString())
            intent.putExtra("memberId", "0")
            intent.putExtra("reporterId", GlobalApplication.prefs.getString("userId"))
            startActivity(intent)
            dismiss()
        }
        binding.btnMenuQuit?.setOnClickListener {
            viewModel.quitMatch()
            dismiss()
        }
        binding.btnMenuCancel?.setOnClickListener {
            dismiss()
        }
    }
}
