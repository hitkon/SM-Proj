package com.example.dm_helper

enum class ConditionField {
    BLINDED,
    CONCEALED,
    CONFUSED,
    CONTROLLED,
    DAZZLED,
    DEAFENED,
    ENCUMBERED,
    FASCINATED,
    FATIGUED,
    FLAT_FOOTED,
    FLEEING,
    GRABBED,
    IMMOBILIZED,
    INVISIBLE,
    PARALYZED,
    PETRIFIED,
    PRONE,
    QUICKENED,
    RESTRAINED,
    UNCONSCIOUS,

    CLUMSY,
    DOOMED,
    DRAINED,
    DYING,
    ENFEEBLED,
    FRIGHTENED,
    SICKENED,
    SLOWED,
    STUNNED,
    STUPEFIED,
    WOUNDED
}

enum class ConditionValueType {
    BOOLEAN,
    INT
}
data class ConditionUi(
    val field: ConditionField,
    val type: ConditionValueType,
    val iconRes: Int,
    val value: Int = 0
)
