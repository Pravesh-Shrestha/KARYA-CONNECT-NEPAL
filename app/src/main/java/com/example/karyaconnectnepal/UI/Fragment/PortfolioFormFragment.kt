package com.example.karyaconnectnepal.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.karyaconnectnepal.Adapter.ProjectAdapter
import com.example.karyaconnectnepal.Model.PortfolioModel
import com.example.karyaconnectnepal.Model.ProjectModel
import com.example.karyaconnectnepal.ViewModel.PortfolioViewModel
import com.example.karyaconnectnepal.Viewmodel.ProjectViewModel
import com.example.karyaconnectnepal.databinding.DialogAddProjectBinding
import com.example.karyaconnectnepal.databinding.FragmentPortfolioFormBinding
import com.google.firebase.auth.FirebaseAuth

class PortfolioFormFragment : Fragment() {

    private var _binding: FragmentPortfolioFormBinding? = null
    private val binding get() = _binding!!


    private lateinit var portfolioViewModel: PortfolioViewModel
    private lateinit var selectedJobCategory: String
    private lateinit var projectViewModel: ProjectViewModel
    private lateinit var projectAdapter: ProjectAdapter
    private val projectList = mutableListOf<ProjectModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        portfolioViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[PortfolioViewModel::class.java]
        setupJobSpinner()

        // Manually initializing ViewModel without ViewModelFactory
//        val repository = ProjectRepository()
        projectViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ProjectViewModel::class.java]

        projectAdapter = ProjectAdapter(projectList)
        binding.projectsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.projectsRecyclerView.adapter = projectAdapter

        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            portfolioViewModel.loadPortfolio(it.uid)
            projectViewModel.getProjects(it.uid)
        }

        // Observe Portfolio Data
        portfolioViewModel.portfolio.observe(viewLifecycleOwner) { portfolio ->
            portfolio?.let {
                binding.fullNameInput.setText(it.personalInfo["fullName"] as? String ?: "")
                binding.emailInput.setText(it.personalInfo["email"] as? String ?: "")
                binding.contactInput.setText(it.personalInfo["contact"] as? String ?: "")
                binding.educationInput.setText(it.education)
                binding.skillsInput.setText(it.skills.joinToString(", "))
                binding.certificationsInput.setText(it.certifications.joinToString(", "))
                binding.experienceInput.setText(it.experience)
                binding.portfolioDescriptionInput.setText(it.portfolioDescription)
            }
        }

        projectViewModel.projects.observe(viewLifecycleOwner) { projects ->
            projectList.clear()
            projectList.addAll(projects)
            projectAdapter.notifyDataSetChanged()
        }

        binding.addProjectButton.setOnClickListener { showAddProjectDialog() }
        binding.submitPortfolioButton.setOnClickListener { submitPortfolio() }
    }

    private fun setupJobSpinner() {
        val jobCategories = listOf("Select Job", "Android Developer", "Web Developer", "Graphic Designer", "Data Analyst", "Marketing Specialist")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, jobCategories)
        binding.jobSelectionSpinner.adapter = adapter

        binding.jobSelectionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedJobCategory = if (position > 0) jobCategories[position] else ""
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedJobCategory = ""
            }
        }
    }

    private fun submitPortfolio() {

        if (selectedJobCategory.isEmpty()) {
            Toast.makeText(requireContext(), "Please select a job category before submitting.", Toast.LENGTH_SHORT).show()
            return  // Stop submission if no job is selected
        }

        val personalInfo = mapOf(
            "fullName" to binding.fullNameInput.text.toString().trim(),
            "email" to binding.emailInput.text.toString().trim(),
            "contact" to binding.contactInput.text.toString().trim()
        )

        val portfolio = PortfolioModel(
            personalInfo = personalInfo,
            education = binding.educationInput.text.toString().trim(),
            skills = binding.skillsInput.text.toString().split(",").map { it.trim() },
            certifications = binding.certificationsInput.text.toString().split(",").map { it.trim() },
            experience = binding.experienceInput.text.toString().trim(),
            portfolioDescription = binding.portfolioDescriptionInput.text.toString().trim(),
            jobCategory = selectedJobCategory
        )

        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            portfolioViewModel.savePortfolio(it.uid, portfolio) { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Portfolio Updated Successfully!", Toast.LENGTH_SHORT).show()

                    // ✅ Clear Inputs
//                    clearInputs()

                    // ✅ Navigate Back to Home
//                    navigateToHome()

                } else {
                    Toast.makeText(requireContext(), "Error saving portfolio", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun showAddProjectDialog() {
        val dialogBinding = DialogAddProjectBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Project")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val title = dialogBinding.projectTitleInput.text.toString().trim()
                val description = dialogBinding.projectDescriptionInput.text.toString().trim()

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    currentUser?.let {
                        val newProject = ProjectModel(title = title, description = description)
                        projectViewModel.addProject(it.uid, newProject) { success ->
                            if (success) {
                                Toast.makeText(requireContext(), "Project Added!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(), "Failed to add project", Toast.LENGTH_SHORT).show()
                            }
                        }
//                        projectViewModel.addProject(ProjectModel(title, description), it.uid)
                    }
                } else {
                    Toast.makeText(requireContext(), "Please enter project details", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
