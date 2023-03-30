package edu.vt.cs5254.multiquiz
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    var questionIndex = 0

    private val questionBank = listOf(
        Question(R.string.question_0_text, listOf(
            Answer(R.string.q_0_a_0, true),
            Answer(R.string.q_0_a_1, false),
            Answer(R.string.q_0_a_2, false),
            Answer(R.string.q_0_a_3, false)
            )
        ),
        Question(R.string.question_1_text, listOf(
            Answer(R.string.q_1_a_0, false),
            Answer(R.string.q_1_a_1, true),
            Answer(R.string.q_1_a_2, false),
            Answer(R.string.q_1_a_3, false)
        )
        ),
        Question(R.string.question_2_text, listOf(
            Answer(R.string.q_2_a_0, false),
            Answer(R.string.q_2_a_1, false),
            Answer(R.string.q_2_a_2, true),
            Answer(R.string.q_2_a_3, false)
        )
        ),
        Question(R.string.question_3_text, listOf(
            Answer(R.string.q_3_a_0, false),
            Answer(R.string.q_3_a_1, false),
            Answer(R.string.q_3_a_2, false),
            Answer(R.string.q_3_a_3, true)
        )
        ),
    )

    val answerList get() = questionBank[questionIndex].answerList
    val questionTextResId get() = questionBank[questionIndex].questionResId
    val totalQues = questionBank.size
    var correctAns: Int = 0
    var hintsUsed = 0

    fun moveToNextQuestion() {
        questionIndex = (questionIndex + 1) % questionBank.size
    }

    fun hasMoreQuestions(): Boolean {
        return questionIndex < questionBank.size - 1
    }

    fun hintClicked() {
        hintsUsed += 1
    }

    fun correctAnsCount() {
        correctAns += 1
    }

    fun resetAll(){
        correctAns = 0
        hintsUsed = 0
    }

    fun resetAnswers() {
        val resetQB = questionBank
            .flatMap() { (questions, answers) ->
                answers.toList()
            }
        resetQB.forEach() {
            it.isEnabled = true
            it.isSelected = false
        }
    }
}