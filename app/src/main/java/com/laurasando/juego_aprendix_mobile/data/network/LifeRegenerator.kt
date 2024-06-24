package com.laurasando.juego_aprendix_mobile.data.network

import com.google.firebase.firestore.FirebaseFirestore

class LifeRegenerator(private val userId: String) : Runnable {
    private val fs = FirebaseFirestore.getInstance()

    override fun run() {
        fs.collection("user_life")
            .whereEqualTo("userId", userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result?.documents?.firstOrNull()
                    if (document != null) {
                        val currentLife = document.getString("life") ?: "5"
                        val timeStamps = document.get("timeStamps") as? MutableList<Long> ?: mutableListOf()
                        val currentTime = System.currentTimeMillis()

                        if (currentLife.toInt() < 5) {
                            val newTimeStamps = timeStamps.filter { currentTime - it < 5000  }.toMutableList()
                            val livesToRegenerate = timeStamps.size - newTimeStamps.size

                            if (livesToRegenerate > 0) {
                                val newLife = currentLife.toInt() + livesToRegenerate
                                document.reference.update(mapOf(
                                    "life" to "$newLife",
                                    "timeStamps" to newTimeStamps
                                )).addOnSuccessListener {
                                    println("Life regenerated successfully")
                                }.addOnFailureListener { e ->
                                    println("Error regenerating life: $e")
                                }
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                println("Error getting documents: $e")
            }
    }
}
