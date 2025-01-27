package com.example.pawrents.screens.edit_task

enum class EditFlagOption {
    On,
    Off;

    companion object {
        fun getByCheckedState(checkedState: Boolean): EditFlagOption {
            val hasFlag = checkedState ?: false
            return if (hasFlag) On else Off
        }

        fun getBooleanValue(flagOption: String): Boolean {
            return flagOption == On.name
        }

        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            entries.forEach { flagOption ->options.add(flagOption.name)}
            return options
        }
    }
}