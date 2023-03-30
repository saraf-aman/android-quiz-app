package edu.vt.cs5254.multiquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import edu.vt.cs5254.multiquiz.ResultActivity.Companion.newIntent
import edu.vt.cs5254.multiquiz.databinding.ActivityResultBinding

private const val EXTRA_CA = "edu.vt.cs5254.multiquiz.correct_answers"
private const val EXTRA_TQ = "edu.vt.cs5254.multiquiz.total_questions"
private const val EXTRA_HU = "edu.vt.cs5254.multiquiz.hints_used"
const val EXTRA_RESET_ALL = "edu.vt.cs5254.multiquiz.reset_all"

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val resultVM: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonResetAll.setOnClickListener() {
            resultVM.resetClicked()
            updateView()
        }
        updateView()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_RESET_ALL, resultVM.reset)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun updateView() {
        binding.correctAnswersValue.text = intent.getStringExtra(EXTRA_CA)
        binding.totalQuestionsValue.text = intent.getStringExtra(EXTRA_TQ)
        binding.hintsUsedValue.text = intent.getStringExtra(EXTRA_HU)
        binding.buttonResetAll.isEnabled = !resultVM.reset
    }

    companion object {
        fun newIntent(context: Context, correctAns: Int, totalQus: Int, hintsUsed: Int): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(EXTRA_CA, correctAns.toString())
                putExtra(EXTRA_TQ, totalQus.toString())
                putExtra(EXTRA_HU, hintsUsed.toString())
            }
        }
    }

}