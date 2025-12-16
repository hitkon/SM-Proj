package com.example.dm_helper

import android.util.Log

object ConditionHelper {

    fun getConditions(character: Character): List<ConditionUi> {
        val conditions = mutableListOf<ConditionUi>()

        if (character.blinded) conditions.add(bool(R.drawable.blinded))
        if (character.concealed) conditions.add(bool(R.drawable.concealed))
        if (character.confused) conditions.add(bool(R.drawable.confused))
        if (character.controlled) conditions.add(bool(R.drawable.controlled))
        if (character.dazzled) conditions.add(bool(R.drawable.dazzled))
        if (character.deafened) conditions.add(bool(R.drawable.deafened))
        if (character.encumbered) conditions.add(bool(R.drawable.encumbered))
        if (character.fascinated) conditions.add(bool(R.drawable.fascinated))
        if (character.fatigued) conditions.add(bool(R.drawable.fatigued))
        if (character.flatFooted) conditions.add(bool(R.drawable.flat_footed))
        if (character.fleeing) conditions.add(bool(R.drawable.fleeing))
        if (character.grabbed) conditions.add(bool(R.drawable.grabbed))
        if (character.immobilized) conditions.add(bool(R.drawable.immobilized))
        if (character.invisible) conditions.add(bool(R.drawable.invisible))
        if (character.paralyzed) conditions.add(bool(R.drawable.paralyzed))
        if (character.petrified) conditions.add(bool(R.drawable.petrified))
        if (character.prone) conditions.add(bool(R.drawable.prone))
        if (character.quickened) conditions.add(bool(R.drawable.quickened))
        if (character.restrained) conditions.add(bool(R.drawable.restrained))
        if (character.unconscious) conditions.add(bool(R.drawable.unconscious))
        addInt(conditions, R.drawable.clumsy, character.clumsy)
        addInt(conditions, R.drawable.doomed, character.doomed)
        addInt(conditions, R.drawable.drained, character.drained)
        addInt(conditions, R.drawable.dying, character.dying)
        addInt(conditions, R.drawable.enfeebled, character.enfeebled)
        addInt(conditions, R.drawable.frightened, character.frightened)
        addInt(conditions, R.drawable.sickened, character.sickened)
        addInt(conditions, R.drawable.slowed, character.slowed)
        addInt(conditions, R.drawable.stunned, character.stunned)
        addInt(conditions, R.drawable.stupefied, character.stupefied)
        addInt(conditions, R.drawable.wounded, character.wounded)

        if (character.persistentDamage.isNotEmpty()) {
            conditions.add(bool(R.drawable.persistent_damage))
        }

        return conditions
    }

    private fun bool(icon: Int): ConditionUi =
        ConditionUi(icon, 0)

    private fun addInt(
        list: MutableList<ConditionUi>,
        icon: Int,
        value: Int
    ) {
        if (value > 0) {
            Log.i("helper", value.toString())
            list.add(ConditionUi(icon, value.coerceIn(1, 4)))
        }
    }
}