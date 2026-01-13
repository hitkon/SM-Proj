package com.example.dm_helper

import android.util.Log

object ConditionHelper {

    fun getConditions(character: Character): List<ConditionUi> {
        val conditions = mutableListOf<ConditionUi>()

        if (character.blinded)
            conditions.add(bool(ConditionField.BLINDED, R.drawable.blinded))

        if (character.concealed)
            conditions.add(bool(ConditionField.CONCEALED, R.drawable.concealed))

        if (character.confused)
            conditions.add(bool(ConditionField.CONFUSED, R.drawable.confused))

        if (character.controlled)
            conditions.add(bool(ConditionField.CONTROLLED, R.drawable.controlled))

        if (character.dazzled)
            conditions.add(bool(ConditionField.DAZZLED, R.drawable.dazzled))

        if (character.deafened)
            conditions.add(bool(ConditionField.DEAFENED, R.drawable.deafened))

        if (character.encumbered)
            conditions.add(bool(ConditionField.ENCUMBERED, R.drawable.encumbered))

        if (character.fascinated)
            conditions.add(bool(ConditionField.FASCINATED, R.drawable.fascinated))

        if (character.fatigued)
            conditions.add(bool(ConditionField.FATIGUED, R.drawable.fatigued))

        if (character.flatFooted)
            conditions.add(bool(ConditionField.FLAT_FOOTED, R.drawable.flat_footed))

        if (character.fleeing)
            conditions.add(bool(ConditionField.FLEEING, R.drawable.fleeing))

        if (character.grabbed)
            conditions.add(bool(ConditionField.GRABBED, R.drawable.grabbed))

        if (character.immobilized)
            conditions.add(bool(ConditionField.IMMOBILIZED, R.drawable.immobilized))

        if (character.invisible)
            conditions.add(bool(ConditionField.INVISIBLE, R.drawable.invisible))

        if (character.paralyzed)
            conditions.add(bool(ConditionField.PARALYZED, R.drawable.paralyzed))

        if (character.petrified)
            conditions.add(bool(ConditionField.PETRIFIED, R.drawable.petrified))

        if (character.prone)
            conditions.add(bool(ConditionField.PRONE, R.drawable.prone))

        if (character.quickened)
            conditions.add(bool(ConditionField.QUICKENED, R.drawable.quickened))

        if (character.restrained)
            conditions.add(bool(ConditionField.RESTRAINED, R.drawable.restrained))

        if (character.unconscious)
            conditions.add(bool(ConditionField.UNCONSCIOUS, R.drawable.unconscious))

        addInt(conditions, ConditionField.CLUMSY, R.drawable.clumsy, character.clumsy)
        addInt(conditions, ConditionField.DOOMED, R.drawable.doomed, character.doomed)
        addInt(conditions, ConditionField.DRAINED, R.drawable.drained, character.drained)
        addInt(conditions, ConditionField.DYING, R.drawable.dying, character.dying)
        addInt(conditions, ConditionField.ENFEEBLED, R.drawable.enfeebled, character.enfeebled)
        addInt(conditions, ConditionField.FRIGHTENED, R.drawable.frightened, character.frightened)
        addInt(conditions, ConditionField.SICKENED, R.drawable.sickened, character.sickened)
        addInt(conditions, ConditionField.SLOWED, R.drawable.slowed, character.slowed)
        addInt(conditions, ConditionField.STUNNED, R.drawable.stunned, character.stunned)
        addInt(conditions, ConditionField.STUPEFIED, R.drawable.stupefied, character.stupefied)
        addInt(conditions, ConditionField.WOUNDED, R.drawable.wounded, character.wounded)

        if (character.persistentDamage.isNotEmpty()) {
            conditions.add(
                bool(
                    ConditionField.PERSISTENT_DAMAGE,
                    R.drawable.persistent_damage
                )
            )
        }

        return conditions
    }

    private fun bool(
        field: ConditionField,
        icon: Int
    ): ConditionUi =        ConditionUi(
            field = field,
            type = ConditionValueType.BOOLEAN,
            iconRes = icon
        )
    private fun addInt(
        list: MutableList<ConditionUi>,
        field: ConditionField,
        icon: Int,
        value: Int
    ) {
        if (value > 0) {
            list.add(
                ConditionUi(
                    field = field,
                    type = ConditionValueType.INT,
                    iconRes = icon,
                    value = value
                )
            )
        }
    }
}
