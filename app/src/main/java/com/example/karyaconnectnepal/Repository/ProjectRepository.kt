package com.example.karyaconnectnepal.Repository

import com.example.karyaconnectnepal.Model.ProjectModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class ProjectRepository {

    private val database = FirebaseDatabase.getInstance().reference.child("users")

    suspend fun addProject(userId: String, project: ProjectModel): Boolean {
        return try {
            val projectId = database.child(userId).child("projects").push().key ?: return false
            val projectData = mapOf(  // ✅ Convert ProjectModel to a Map<String, Any>
                "title" to project.title,
                "description" to project.description
            )
            database.child(userId).child("projects").child(projectId).setValue(project).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getProjects(userId: String): List<ProjectModel> {
        return try {
            val snapshot = database.child(userId).child("projects").get().await()
            snapshot.children.mapNotNull { it.getValue(ProjectModel::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
