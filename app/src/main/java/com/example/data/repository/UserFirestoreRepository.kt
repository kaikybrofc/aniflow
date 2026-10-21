package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.domain.model.NewsArticle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class UserFirestoreRepository(
    private val firestore: FirebaseFirestore
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("Usuário precisa estar autenticado com o Google.")
    }

    suspend fun syncUserProfile() {
        val user = auth.currentUser ?: return
        val uid = user.uid
        val profileData = mapOf(
            "userId" to uid,
            "displayName" to (user.displayName ?: "Fã de Anime"),
            "email" to (user.email ?: ""),
            "photoUrl" to (user.photoUrl?.toString() ?: ""),
            "updatedAt" to FieldValue.serverTimestamp()
        )
        firestore.collection("users").document(uid).set(profileData).await()
    }

    suspend fun saveBookmark(article: NewsArticle) {
        val uid = requireUserId()
        val bookmarkData = mapOf(
            "id" to article.id,
            "userId" to uid,
            "title" to article.title,
            "source" to article.source,
            "category" to article.category,
            "imageUrl" to article.imageUrl,
            "articleUrl" to article.articleUrl,
            "savedAt" to FieldValue.serverTimestamp()
        )
        firestore.collection("users").document(uid)
            .collection("bookmarks").document(article.id)
            .set(bookmarkData).await()
    }

    suspend fun removeBookmark(articleId: String) {
        val uid = requireUserId()
        firestore.collection("users").document(uid)
            .collection("bookmarks").document(articleId)
            .delete().await()
    }

    fun observeCloudBookmarks(): Flow<List<String>> {
        val uid = auth.currentUser?.uid ?: return emptyFlow()
        return firestore.collection("users").document(uid)
            .collection("bookmarks")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { it.id }
            }
    }
}
