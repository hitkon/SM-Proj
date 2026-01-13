package com.example.dm_helper

import android.content.Context
import android.net.Uri
import android.util.Log
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import java.io.IOException
import java.io.InputStream

class PdfCharacterParser(private val context: Context) {

    fun parseCharacterBlueprintFromRaw(rawResourceId: Int): CharacterBlueprint? {
        return try {
            val inputStream: InputStream = context.resources.openRawResource(rawResourceId)
            val reader = PdfReader(inputStream)
            val n = reader.numberOfPages
            var parsedText = ""
            for (i in 0 until n) {
                parsedText += PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"
            }
            reader.close()
            parseText(parsedText)
        } catch (e: IOException) {
            Log.e("PdfParser", "Error loading PDF from raw resource", e)
            null
        }
    }

    fun parseCharacterBlueprint(uri: Uri): CharacterBlueprint? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val reader = PdfReader(inputStream)
            val n = reader.numberOfPages
            var parsedText = ""
            for (i in 0 until n) {
                parsedText += PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"
            }
            reader.close()
            parseText(parsedText)
        } catch (e: IOException) {
            Log.e("PdfParser", "Error loading PDF from URI", e)
            null
        }
    }

    private fun parseText(text: String): CharacterBlueprint? {
        return try {
            Log.d("PdfParser", "--- Full PDF Text ---")
            Log.d("PdfParser", text)
            Log.d("PdfParser", "--- End of Full PDF Text ---")

            // Parse character name with multiple fallbacks for different layouts
            var name = extractStat(text, """\nXP\s*\n([^\n]+)""")?.trim() ?: "Unknown"
            // Check for junk lines like "x" or "x x"
            if (name.matches(Regex("""^x(\s*x)?$"""))) {
                name = extractStat(text, """\nXP\s*\n[^\n]+\n([^\n]+)""")?.trim() ?: name
            }
            if (name == "Unknown" || name.matches(Regex("""^x(\s*x)?$"""))) {
                name = extractStat(text, """Player Name\s*\n([^\n]+)""") ?: "Unknown"
            }
            Log.d("PdfParser", "Name: $name")

            val lines = text.lines()
            val attributeHeaderIndex = lines.indexOfFirst { it.contains("Strength Dexterity Constitution Intelligence Wisdom Charisma") }

            var strMod = 0
            var dexMod = 0
            var conMod = 0
            var intMod = 0
            var wisMod = 0
            var chaMod = 0

            if (attributeHeaderIndex != -1) {
                // Find the next non-blank line which should contain the attribute values
                val valuesLine = lines.drop(attributeHeaderIndex + 1).firstOrNull { it.isNotBlank() }
                if (valuesLine != null) {
                    val values = valuesLine.trim().split(Regex("\\s+"))
                    if (values.size >= 6) {
                        strMod = values[0].toIntOrNull() ?: 0
                        dexMod = values[1].toIntOrNull() ?: 0
                        conMod = values[2].toIntOrNull() ?: 0
                        intMod = values[3].toIntOrNull() ?: 0
                        wisMod = values[4].toIntOrNull() ?: 0
                        chaMod = values[5].toIntOrNull() ?: 0
                    }
                }
            }

            val str = 10 + (strMod * 2)
            val dex = 10 + (dexMod * 2)
            val con = 10 + (conMod * 2)
            val intValue = 10 + (intMod * 2)
            val wis = 10 + (wisMod * 2)
            val cha = 10 + (chaMod * 2)
            Log.d("PdfParser", "Ability Scores: STR=$str, DEX=$dex, CON=$con, INT=$intValue, WIS=$wis, CHA=$cha")

            // More specific regex based on log analysis
            val ac = extractStat(text, """L\s+L\s+L\s*\n(\d+)""")?.toIntOrNull() ?: 10
            val maxHp = extractStat(text, """T\s+T\s+T\s*\n(\d+)""")?.toIntOrNull() ?: 1
            val fortitude = extractStat(text, """M\s*\+\s*(\d+)\s*M""")?.toIntOrNull() ?: 0
            val reflex = extractStat(text, """/\s*\+\s*(\d+)\s*M""")?.toIntOrNull() ?: 0
            val will = extractStat(text, """M\s*\+\s*\d+\s*M\s*\+\s*(\d+)\s*M""")?.toIntOrNull() ?: 0
            val perception = extractStat(text, """Perception[\s\S]*?\+(\d+)""")?.toIntOrNull() ?: 0
            val speed = extractStat(text, """Speed[\s\S]*?(\d+)\s+L\s+feet""")?.toIntOrNull() ?: 25

            // A more robust regex that finds the stat name, then non-greedily looks for the next signed number.
            fun extractFlexibleStat(statName: String): Int {
                val value = extractStat(text, """$statName[\s\S]*?([+-]?\d+)""")
                return value?.toIntOrNull() ?: 0
            }

            // Skills
            val acrobatics = extractFlexibleStat("Acrobatics")
            val arcana = extractFlexibleStat("Arcana")
            val athletics = extractFlexibleStat("Athletics")
            val crafting = extractFlexibleStat("Crafting")
            val deception = extractFlexibleStat("Deception")
            val diplomacy = extractFlexibleStat("Diplomacy")
            val intimidation = extractFlexibleStat("Intimidation")
            val medicine = extractFlexibleStat("Medicine")
            val nature = extractFlexibleStat("Nature")
            val occultism = extractFlexibleStat("Occultism")
            val performance = extractFlexibleStat("Performance")
            val religion = extractFlexibleStat("Religion")
            val society = extractFlexibleStat("Society")
            val stealth = extractFlexibleStat("Stealth")
            val survival = extractFlexibleStat("Survival")
            val thievery = extractFlexibleStat("Thievery")

            val blueprint = CharacterBlueprint(
                name = name,
                portrait = R.drawable.ic_launcher_background,
                maximumHP = maxHp,
                perception = perception,
                speed = speed,
                heroPoints = 1,
                str = str,
                dex = dex,
                con = con,
                wis = wis,
                `int` = intValue,
                cha = cha,
                reflex = reflex,
                fortitude = fortitude,
                will = will,
                ac = ac,
                acrobatics = acrobatics,
                arcana = arcana,
                athletics = athletics,
                crafting = crafting,
                deception = deception,
                diplomacy = diplomacy,
                intimidation = intimidation,
                medicine = medicine,
                nature = nature,
                occultism = occultism,
                performance = performance,
                religion = religion,
                society = society,
                stealth = stealth,
                survival = survival,
                thievery = thievery,
                resistances = emptyList(),
                weaknesses = emptyList(),
                traits = emptyList(),
                immunities = emptyList()
            )
            Log.d("PdfParser", "Created CharacterBlueprint: $blueprint")
            blueprint
        } catch (e: Exception) {
            Log.e("PdfParser", "Error parsing text from PDF", e)
            null
        }
    }

    private fun extractStat(text: String, regex: String): String? {
        val pattern = Regex(regex, setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL))
        val match = pattern.find(text)
        return match?.groups?.get(1)?.value?.trim()
    }
}