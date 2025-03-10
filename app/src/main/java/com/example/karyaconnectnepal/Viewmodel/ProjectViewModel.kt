

//package com.example.karyaconnectnepal.ViewModel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.karyaconnectnepal.Model.ProjectModel
//import com.example.karyaconnectnepal.Repository.ProjectRepository
//import kotlinx.coroutines.launch
//
//class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {
//
//    private val _projects = MutableLiveData<List<ProjectModel>>()
//    val projects: LiveData<List<ProjectModel>> get() = _projects
//
//    fun addProject(project: ProjectModel, userId: String) {
//        viewModelScope.launch {
//            val isSuccess = repository.addProject(project, userId)
//            if (isSuccess) {
//                getProjects(userId) // Refresh the list after adding
//            }
//        }
//    }
//
//    fun getProjects(userId: String) {
//        viewModelScope.launch {
//            val projectList = repository.getProjects(userId)
//            _projects.value = projectList
//        }
//    }
//}


package com.example.karyaconnectnepal.Viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karyaconnectnepal.Model.ProjectModel
import com.example.karyaconnectnepal.Repository.ProjectRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class ProjectViewModel() : ViewModel() {

    private val repository = ProjectRepository()  // Initialize inside ViewModel

    val _projectList = MutableLiveData<List<ProjectModel>>()
    val projects: LiveData<List<ProjectModel>> get() = _projectList

    fun addProject(userId: String, project: ProjectModel, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.addProject(userId, project)
            callback(success)
        }
    }

    fun getProjects(userId: String) {
        val projectsRef = FirebaseDatabase.getInstance().reference.child("users").child(userId).child("projects")

        projectsRef.get().addOnSuccessListener { snapshot ->
            val projects = mutableListOf<ProjectModel>()
            for (projectSnapshot in snapshot.children) {
                val project = projectSnapshot.getValue(ProjectModel::class.java)
                if (project != null) {
                    projects.add(project)
                }
            }
            _projectList.value = projects
        }.addOnFailureListener {
            _projectList.value = emptyList() // Handle Failure
        }
    }
}