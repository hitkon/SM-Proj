package com.example.dm_helper

object ConditionHelper {

    fun getConditionIcons(character: Character): List<Int> {
        val icons = mutableListOf<Int>()

        if (character.blinded) icons.add(R.drawable.blinded)
        if (character.clumsy > 0) icons.add(R.drawable.clumsy)
        if (character.concealed) icons.add(R.drawable.concealed)
        if (character.confused) icons.add(R.drawable.confused)
        if (character.controlled) icons.add(R.drawable.controlled)
        if (character.dazzled) icons.add(R.drawable.dazzled)
        if (character.deafened) icons.add(R.drawable.deafened)
        if (character.doomed > 0) icons.add(R.drawable.doomed)
        if (character.drained > 0) icons.add(R.drawable.drained)
        if (character.dying > 0) icons.add(R.drawable.dying)
        if (character.encumbered) icons.add(R.drawable.encumbered)
        if (character.enfeebled > 0) icons.add(R.drawable.enfeebled)
        if (character.fascinated) icons.add(R.drawable.fascinated)
        if (character.fatigued) icons.add(R.drawable.fatigued)
        if (character.flatFooted) icons.add(R.drawable.flat_footed)
        if (character.fleeing) icons.add(R.drawable.fleeing)
        if (character.frightened > 0) icons.add(R.drawable.frightened)
        if (character.grabbed) icons.add(R.drawable.grabbed)
        if (character.immobilized) icons.add(R.drawable.immobilized)
        if (character.invisible) icons.add(R.drawable.invisible)
        if (character.paralyzed) icons.add(R.drawable.paralyzed)
        if (character.petrified) icons.add(R.drawable.petrified)
        if (character.prone) icons.add(R.drawable.prone)
        if (character.quickened) icons.add(R.drawable.quickened)
        if (character.restrained) icons.add(R.drawable.restrained)
        if (character.sickened > 0) icons.add(R.drawable.sickened)
        if (character.slowed > 0) icons.add(R.drawable.slowed)
        if (character.stunned > 0) icons.add(R.drawable.stunned)
        if (character.stupefied > 0) icons.add(R.drawable.stupefied)
        if (character.unconscious) icons.add(R.drawable.unconscious)
        if (character.wounded > 0) icons.add(R.drawable.wounded)
        if (character.persistentDamage.isNotEmpty()) icons.add(R.drawable.persistent_damage)

        return icons
    }
}