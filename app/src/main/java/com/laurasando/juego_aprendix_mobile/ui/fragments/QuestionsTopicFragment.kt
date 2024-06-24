package com.laurasando.juego_aprendix_mobile.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.navigateUp
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.data.ApiClient
import com.laurasando.juego_aprendix_mobile.data.interfaces.ApiServices
import com.laurasando.juego_aprendix_mobile.data.local.SharePreferencesManager
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.questions.MovilQuestion
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.questions.QuestionAnswer
import com.laurasando.juego_aprendix_mobile.data.models.courses.topics.questions.QuestionsTopicResponse
import com.laurasando.juego_aprendix_mobile.data.models.ranking.UserScoreModel
import com.laurasando.juego_aprendix_mobile.data.models.ranking.request.AddPointsRequest
import com.laurasando.juego_aprendix_mobile.data.network.FireStoreManager
import com.laurasando.juego_aprendix_mobile.databinding.AlertGameQuestionBinding
import com.laurasando.juego_aprendix_mobile.databinding.AlertsLoginBinding
import com.laurasando.juego_aprendix_mobile.databinding.FragmentQuestionsTopicBinding
import com.laurasando.juego_aprendix_mobile.ui.dialogs.SuccessResultDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class QuestionsTopicFragment : Fragment() {
    private var idTopicParam = ""
    private val args: QuestionsTopicFragmentArgs by navArgs()
    private lateinit var binding: FragmentQuestionsTopicBinding
    private var questions: List<MovilQuestion>? = null
    private var currentQuestionIndex = 0
    private var currentProgress = 0
    private var currentLife = 0
    private var pointsBase = 0
    private lateinit var shrManager: SharePreferencesManager
    private lateinit var fsManager: FireStoreManager
    private var userId = ""
    private lateinit var sharedPrefs: SharePreferencesManager

    private val arrayImages = arrayListOf<Int>(
        R.drawable.pregunta0,
        R.drawable.pregunta1,
        R.drawable.pregunta2,
        R.drawable.pregunta3,
        R.drawable.pregunta4,
        R.drawable.pregunta5,
        R.drawable.pregunta6,
        R.drawable.pregunta7,
        R.drawable.pregunta8,
        R.drawable.pregunta9,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionsTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idTopicParam = args.keyIdTopic
        shrManager = SharePreferencesManager(requireContext())
        fsManager = FireStoreManager()
        sharedPrefs = SharePreferencesManager(requireContext())
        initUI()
    }

    private fun initUI() {
        initGetDetailTopic()
        initGetLifeUser()
        binding.btnValidateCurrentQuestion.setOnClickListener {
            checkAnswer()
        }
        binding.idBtnBackX.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initGetLifeUser() {
        userId = shrManager.getPref("userId", "Empty").toString()

        fsManager.getLifeByUserId(userId) { data ->
            if (data == null) {
                Toast.makeText(requireContext(), "User life not found", Toast.LENGTH_SHORT).show()
            } else {
                binding.idTxtLife.text = data.life
                currentLife = data.life.toInt()
            }
        }
    }

    private fun initGetDetailTopic() {
        val apiServices: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        apiServices.getDetailTopic(idTopicParam).enqueue(object : Callback<QuestionsTopicResponse> {
            override fun onResponse(
                call: Call<QuestionsTopicResponse>,
                response: Response<QuestionsTopicResponse>
            ) {
                binding.idProgressBarQuestions.visibility = View.GONE
                binding.linearLayout.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    questions = response.body()?.data?.movil_questions
                    showQuestion()
                } else {
                    Toast.makeText(requireContext(), "Failed to load questions", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<QuestionsTopicResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Algo salio mal", Toast.LENGTH_SHORT).show()
                binding.idProgressBarQuestions.visibility = View.GONE
            }
        })
    }

    private fun showQuestion() {
        questions?.let {
            if (currentQuestionIndex < it.size) {
                val question = it[currentQuestionIndex]
                binding.idQuestion.text = question.question_text
                val randomNumber = Random.nextInt(0, 10)
                val imageRandom = arrayImages[randomNumber]
                binding.idImgQuestion.setImageResource(imageRandom)
                binding.explicacionpreguntas.text = question.description_text
                binding.idResponseOne.text = question.question_answers.getOrNull(0)?.answer ?: ""
                binding.idResponseTwo.text = question.question_answers.getOrNull(1)?.answer ?: ""
                binding.idResponseThree.text = question.question_answers.getOrNull(2)?.answer ?: ""
            } else {
                Toast.makeText(requireContext(), "No more questions", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAnswer() {
        val selectedAnswer = when {
            binding.idResponseOne.isChecked ->
                binding.idResponseOne.text.toString()

            binding.idResponseTwo.isChecked ->
                binding.idResponseTwo.text.toString()

            binding.idResponseThree.isChecked ->
                binding.idResponseThree.text.toString()

            else -> null
        }

        if (selectedAnswer != null) {
            val currentQuestion = questions?.getOrNull(currentQuestionIndex)
            val correctAnswer =
                currentQuestion?.question_answers?.firstOrNull { it.is_correct }?.answer
            if (selectedAnswer == correctAnswer) {
                binding.progressBar.max = questions!!.size
                incrementProgress()
                Toast.makeText(requireContext(), "Correct answer!", Toast.LENGTH_SHORT).show()
                currentQuestionIndex++
                showQuestion()

            } else {
                if (currentLife > 0) {
                    currentLife -= 1
                    fsManager.subtractUserLives(userId)

                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        initGetLifeUser()
                    }, 6000)

                }
                binding.idTxtLife.text = currentLife.toString()
                Toast.makeText(requireContext(), "Wrong answer, try again!", Toast.LENGTH_SHORT)
                    .show()
                if (currentLife == 0) {
                    showAlertDialog(
                        "Oh no!",
                        "Te has quedado sin vidas, regresa más tarde y sigue intentandolo",
                        R.drawable.bad_r
                    )
                }
            }


        } else {
            Toast.makeText(requireContext(), "No selected answer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun incrementProgress() {
        if (currentProgress < questions!!.size) {
            currentProgress++
            pointsBase += 5
            binding.progressBar.progress = currentProgress
        }

        if (currentProgress == questions!!.size) {
            addPointsUser(pointsBase)
        }
    }

    private fun showAlertDialog(title: String, message: String, image: Int) {
        val layoutBinding =
            AlertGameQuestionBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(layoutBinding.root)
            setCancelable(false)
        }.create()

        layoutBinding.idTxtTitle.text = title
        layoutBinding.idTxtMessage.text = message
        layoutBinding.idImg.setImageResource(image)

        layoutBinding.idBtnContinue.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
        }

        dialog.window?.setBackgroundDrawableResource(R.color.transparente)
        dialog.show()

    }

    private fun addPointsUser(score: Int) {
        val userId = sharedPrefs.getPref("userId", "empty").toString()
        val apiServices: ApiServices = ApiClient.retrofitHelper().create(ApiServices::class.java)
        val body = AddPointsRequest(
            userId,
            score
        )
        apiServices.addPoints(body).enqueue(object : Callback<UserScoreModel?> {
            override fun onResponse(
                call: Call<UserScoreModel?>,
                response: Response<UserScoreModel?>
            ) {
                if (response.isSuccessful) {
                    val dialog = SuccessResultDialog()
                    dialog.show(childFragmentManager, "SuccessResultDialog")
                }
            }

            override fun onFailure(call: Call<UserScoreModel?>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Algo salio mal guardando el score",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}
