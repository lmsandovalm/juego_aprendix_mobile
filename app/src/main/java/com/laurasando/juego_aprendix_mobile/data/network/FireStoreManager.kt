package com.laurasando.juego_aprendix_mobile.data.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.laurasando.juego_aprendix_mobile.data.models.UserTopicCompleteResponse
import com.laurasando.juego_aprendix_mobile.data.models.user.UserLifeModel
import com.google.firebase.firestore.ktx.toObject

class FireStoreManager {
    fun getLifeByUserId(userId: String, callback: (UserLifeModel?) -> Unit) {
        val fs = FirebaseFirestore.getInstance()
        fs.collection("user_life")
            .whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result?.documents?.firstOrNull()
                    if (document != null) {
                        val response = UserLifeModel(
                            userId = document.getString("userId"),
                            life = document.getString("life").toString(),
                        )
                        callback(response)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun getQuestionComplete(userId: String, callback: (List<UserTopicCompleteResponse>?) -> Unit) {
        val fs = FirebaseFirestore.getInstance()
        fs.collection("user_topics_complete")
            .whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("resultadooo", "getQuestionComplete: ${task.result}")
                    val documents = task.result?.documents

                    if (documents != null) {
                        val listTopicsCompleted = documents.mapNotNull { document ->
                            val data = document.data
                            if (data != null) {
                                try {
                                    UserTopicCompleteResponse(
                                        userId = data["userId"] as? String ?: "",
                                        topicId = data["topicId"] as? String ?: "",
                                    )
                                } catch (e: Exception) {
                                    Log.e(
                                        "DeserializationError",
                                        "Error deserializing document ${document.id}",
                                        e
                                    )
                                    null
                                }
                            } else {
                                null
                            }
                        }
                        Log.e("LauraTopics111", "getQuestionComplete:${listTopicsCompleted} ")
                        callback(listTopicsCompleted)
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }


    fun setInfoUser(userId: String): Boolean {

        val fs = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "userId" to userId,
            "life" to "5",
            "time_elapsed" to "",
        )
        fs.collection("user_life")
            .add(user).addOnCompleteListener { task ->
                task.isSuccessful
            }.addOnFailureListener {
                false
            }
        return false
    }

    fun setQuestionComplete(userId: String, topicId: String, callback: (Boolean) -> Unit) {

        val fs = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "userId" to userId,
            "topicId" to topicId
        )
        fs.collection("user_topics_complete")
            .add(user).addOnCompleteListener { task ->
                callback(task.isSuccessful)

            }.addOnFailureListener {
                callback(false)
            }
    }

    fun subtractUserLives(userId: String) {
        val fs = FirebaseFirestore.getInstance()
        fs.collection("user_life")
            .whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result?.documents?.firstOrNull()
                    if (document != null) {
                        val currentLife = document.getString("life") ?: "5"
                        val timeStamps =
                            document.get("timeStamps") as? MutableList<Long> ?: mutableListOf()

                        if (currentLife.toInt() > 0) {
                            val newLife = currentLife.toInt() - 1
                            val currentTime = System.currentTimeMillis()
                            timeStamps.add(currentTime)

                            document.reference.update(
                                mapOf(
                                    "life" to "$newLife",
                                    "timeStamps" to timeStamps
                                )
                            ).addOnSuccessListener {
                                println("Life updated successfully")
                            }.addOnFailureListener { e ->
                                println("Error updating life: $e")
                            }
                        }
                    } else {
                        println("No document found for userId: $userId")
                    }
                } else {
                    println("Error getting documents: ${task.exception}")
                }
            }
            .addOnFailureListener { e ->
                println("Error getting documents: $e")
            }
    }


}
