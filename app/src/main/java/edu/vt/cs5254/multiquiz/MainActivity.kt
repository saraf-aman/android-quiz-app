package edu.vt.cs5254.multiquiz

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import edu.vt.cs5254.multiquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Name: Aman Saraf
    //PID : amansaraf28

    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonList: List<Button>
    private val quizVM: QuizViewModel by viewModels()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data?.getBooleanExtra(EXTRA_RESET_ALL, false) == true) {
                quizVM.resetAll()
                quizVM.resetAnswers()
                updateView()
            }
        }
        quizVM.moveToNextQuestion()
        updateQuestion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        buttonList = listOf(
            binding.answer0Button,
            binding.answer1Button,
            binding.answer2Button,
            binding.answer3Button
        )

        binding.hintButton.setOnClickListener() {
            quizVM.answerList
                .filter {
                    it.isEnabled &&
                    !it.isCorrect
                }
                .random()
                .let {
                    it.isEnabled = false
                    it.isSelected = false
                }
            quizVM.hintClicked()
            updateView()
        }

        binding.submitButton.setOnClickListener() {
            quizVM.answerList.zip(buttonList)
                .forEach() { (answer, button) ->
                    if(button.isSelected && answer.isCorrect) {
                        quizVM.correctAnsCount()
                    }
                }
            if(quizVM.hasMoreQuestions()) {
                quizVM.moveToNextQuestion()
                updateQuestion()
            } else {
                val intent = ResultActivity.newIntent(this, quizVM.correctAns, quizVM.totalQues, quizVM.hintsUsed)
                quizVM.correctAns = 0
                resultLauncher.launch(intent)
            }
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        binding.questionText.setText(quizVM.questionTextResId)
        quizVM.answerList.zip(buttonList)
            .forEach() { (answer, button) ->
                button.setText(answer.textResId)
                button.setOnClickListener {
                    answer.isSelected = !answer.isSelected
                    quizVM.answerList.filter {it != answer}
                        .forEach() { deselectedAnswer ->
                            deselectedAnswer.isSelected = false
                        }
                    updateView()
                }
            }
        updateView()
    }

    private fun updateView() {
        quizVM.answerList.zip(buttonList)
            .forEach { (answer, button) ->
                button.isSelected = answer.isSelected
                button.isEnabled = answer.isEnabled
                button.updateColor()
            }
        binding.hintButton.isEnabled = quizVM.answerList.any { it.isEnabled && !it.isCorrect }
        binding.submitButton.isEnabled = quizVM.answerList.any { it.isSelected }
    }
}