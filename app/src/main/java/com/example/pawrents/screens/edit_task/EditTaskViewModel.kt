package com.example.pawrents.screens.edit_task

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.pawrents.PawrentsViewModel
import com.example.pawrents.services.login.LogService
import com.example.pawrents.classes.Task
import com.example.pawrents.common.TASK_ID
import com.example.pawrents.common.textvalidity.idFromParameter
import com.example.pawrents.services.storage.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService
) : PawrentsViewModel (logService){
    val task = mutableStateOf(Task())

    init {
        val taskId = savedStateHandle.get<String>(TASK_ID)
        if (taskId != null) {
            launchCatching {
                task.value = storageService.getTask(taskId.idFromParameter())?: Task()
            }
        }
    }

    fun onTitleChange (newValue: String){
        task.value = task.value.copy(title = newValue)
    }

    fun onDescriptionChange (newValue: String){
        task.value = task.value.copy(description = newValue)
    }

    fun onUrlChange (newValue: String){
        task.value = task.value.copy(url = newValue)
    }
    fun onDateChange (newValue: Long){
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = newValue
        val newDueDate = SimpleDateFormat("dd MMM yyyy", java.util.Locale.ENGLISH).format(calendar.time)
        task.value = task.value.copy(dueDate = newDueDate)
    }
    fun onTimeChange (hour: Int, minute: Int){
        val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
        task.value = task.value.copy(dueTime = newDueTime)
    }

    fun onFlagToggle (newValue: String){
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        task.value = task.value.copy(flag = newFlagOption)
    }

    fun onDoneClick(popUpScreen: () ->Unit) {
        launchCatching {
            val editedTask = task.value
            if(editedTask.id.isBlank()){
                storageService.save(editedTask)
            } else {
                storageService.update(editedTask)
            }
            popUpScreen()
        }
    }

}
private fun Int.toClockPattern(): String {
    return if (this < 10) "0$this" else "$this"
}