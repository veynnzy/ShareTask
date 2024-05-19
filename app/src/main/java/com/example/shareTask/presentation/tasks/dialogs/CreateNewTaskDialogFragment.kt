package com.example.shareTask.presentation.tasks.dialogs

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.shareTask.app.ShareTask
import com.example.shareTask.databinding.DialogFragmentCreateNewTaskBinding
import com.example.shareTask.presentation.tasks.TasksViewModel
import com.example.shareTask.presentation.tasks.TasksViewModelFactory
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateNewTaskDialogFragment(val taskCreate : TaskCreate) : DialogFragment(){

    interface TaskCreate{
        fun taskCreate(title : String, priority : String, date: Date)
    }

    private var _binding: DialogFragmentCreateNewTaskBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity?.applicationContext as ShareTask).appComponent.inject(this)

        _binding = DialogFragmentCreateNewTaskBinding.inflate(layoutInflater)

        setEventListener()

        return binding.root
    }

    private fun setEventListener(){

        binding.buttonDatePicker.setOnClickListener{

            val calendar = Calendar.getInstance()
            val thisYear = calendar.get(Calendar.YEAR)
            val thisMonth = calendar.get(Calendar.MONTH)
            val thisDay = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    binding.dateText.text = "$dayOfMonth.$monthOfYear.$year" },
                thisYear, thisMonth, thisDay)

            datePickerDialog.show()
        }

        binding.buttonCreate.setOnClickListener{
            val title = binding.titleNewTask.editText?.text.toString()
            val priority = binding.spinnerPriority.editText?.text.toString()
            val textDate = binding.dateText.text.toString()
            val date : Date = SimpleDateFormat("dd.MM.yyyy",Locale.US).parse(textDate) as Date

            taskCreate.taskCreate(title, priority, date)

            dialog?.dismiss()
        }

        binding.buttonCancel.setOnClickListener{
            dialog?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}