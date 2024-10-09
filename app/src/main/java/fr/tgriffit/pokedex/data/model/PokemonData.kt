package fr.tgriffit.pokedex.data.model

import com.google.gson.annotations.SerializedName

open class PokemonData(
    val id: Int,
    val name: String,
    val base_experience: Int,
    val height: Int,
    val isDefault: Boolean,
    val order: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val forms: List<Form>,
    val game_indices: List<GameIndex>,
    val heldItems: List<HeldItem>,
    val location_area_encounters: String,
    val moves: List<Move>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val past_types: List<PastType>
)

data class Ability(
    val is_hidden: Boolean,
    val slot: Int,
    val ability: NamedApiResource
)

data class Form(
    val name: String,
    val url: String
)

data class GameIndex(
    val game_index: Int,
    val version: NamedApiResource
)

data class HeldItem(
    val item: NamedApiResource,
    val versionD_details: List<VersionDetail>
)

data class VersionDetail(
    val rarity: Int,
    val version: NamedApiResource
)

data class Move(
    val move: NamedApiResource,
    val version_group_details: List<VersionGroupDetail>
)

data class VersionGroupDetail(
    val level_learned_at: Int,
    val version_group: NamedApiResource,
    val move_learn_method: NamedApiResource
)

data class Species(
    val name: String,
    val url: String
)

data class Sprites(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?,
    val other: OtherSprites,
    val versions: VersionSprites
){
    override fun toString(): String {
        return "Sprites(backDefault=$back_default," +
                "\nbackFemale=$back_female, " +
                "\nbackShiny=$back_shiny, " +
                "\nbackShinyFemale=$back_shiny_female, " +
                "\nfrontDefault=$front_default, " +
                "\n=$front_female, " +
                "\nfrontShiny=$front_shiny, " +
                "\nfrontShinyFemale=$front_shiny_female, " +
                "\nother=$other, " +
                "\nversions=$versions)"
    }
}

data class OtherSprites(
    val dream_world: DreamWorldSprites,
    val home: HomeSprites,
    val official_artwork: OfficialArtworkSprites,
    val showdown: ShowdownSprites
)

data class DreamWorldSprites(
    val frontDefault: String?,
    val frontFemale: String?
)

data class HomeSprites(
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class OfficialArtworkSprites(
    val frontDefault: String?,
    val frontShiny: String?
)

data class ShowdownSprites(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class VersionSprites(
    @SerializedName("generation-i")     val generationI: GenerationISprites,
    @SerializedName("generation-ii")    val generationii: GenerationIiSprites,
    @SerializedName("generation-iii")   val generation_iii: GenerationIiiSprites,
    @SerializedName("generation-iv")    val generation_iv: GenerationIvSprites,
    @SerializedName("generation-v")     val generation_v: GenerationVSprites,
    @SerializedName("generation-vi")    val generation_vi: GenerationViSprites,
    @SerializedName("generation-vii")   val generation_vii: GenerationViiSprites,
    @SerializedName("generation-viii")  val generation_viii: GenerationViiiSprites
)

data class GenerationISprites(
    @SerializedName("red-blue")    val red_blue: RedBlueSprites,
    val yellow: YellowSprites
)

data class RedBlueSprites(
    val back_default: String?,
    val back_gray: String?,
    val front_default: String?,
    val front_gray: String?
)

data class YellowSprites(
    val back_default: String?,
    val back_gray: String?,
    val front_default: String?,
    val front_gray: String?
)

data class GenerationIiSprites(
    val crystal: CrystalSprites,
    val gold: GoldSprites,
    val silver: SilverSprites
)

data class CrystalSprites(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class GoldSprites(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class SilverSprites(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class GenerationIiiSprites(
    val emerald: EmeraldSprites,
    @SerializedName("firered-leafgreen") val firered_leafgreen: FireredLeafgreenSprites,
    @SerializedName("ruby-sapphire") val ruby_sapphire: RubySapphireSprites
)

data class EmeraldSprites(
    val front_default: String?,
    val front_shiny: String?
)

data class FireredLeafgreenSprites(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class RubySapphireSprites(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class GenerationIvSprites(
    @SerializedName("diamond-pearl") val diamond_pearl: DiamondPearlSprites,
    @SerializedName("heartgold-soulsilver") val heartgold_soulsilver: HeartgoldSoulsilverSprites,
    val platinum: PlatinumSprites
)

data class DiamondPearlSprites(
    val back_default: String?,
    val backFemale: String?,
    val back_shiny: String?,
    val back_shinyFemale: String?,
    val front_default: String?,
    val frontFemale: String?,
    val front_shiny: String?,
    val front_shinyFemale: String?
)

data class HeartgoldSoulsilverSprites(
    val back_default: String?,
    val backFemale: String?,
    val back_shiny: String?,
    val back_shinyFemale: String?,
    val front_default: String?,
    val frontFemale: String?,
    val front_shiny: String?,
    val front_shinyFemale: String?
)

data class PlatinumSprites(
    val back_default: String?,
    val backFemale: String?,
    val back_shiny: String?,
    val back_shinyFemale: String?,
    val front_default: String?,
    val frontFemale: String?,
    val front_shiny: String?,
    val front_shinyFemale: String?
)

data class GenerationVSprites(
    @SerializedName("black-white") val black_white: BlackWhiteSprites
)

data class BlackWhiteSprites(
    val animated: AnimatedSprites,
    val back_default: String?,
    val backFemale: String?,
    val back_shiny: String?,
    val back_shinyFemale: String?,
    val front_default: String?,
    val frontFemale: String?,
    val front_shiny: String?,
    val front_shinyFemale: String?
)

data class AnimatedSprites(
    val back_default: String?,
    val backFemale: String?,
    val back_shiny: String?,
    val back_shinyFemale: String?,
    val front_default: String?,
    val frontFemale: String?,
    val front_shiny: String?,
    val front_shinyFemale: String?
)

data class GenerationViSprites(
    @SerializedName("omegaruby-alphasapphire")  val omegaruby_alphasapphire: OmegarubyAlphasapphireSprites,
    @SerializedName("x-y")                      val xY: XYSprites
)

data class OmegarubyAlphasapphireSprites(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class XYSprites(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class GenerationViiSprites(
    val icons: IconSprites,
    @SerializedName("ultra-sun-ultra-moon") val ultra_sun_ultra_moon: UltraSunUltraMoonSprites
)

data class IconSprites(
    val front_default: String?,
    val front_female: String?
)

data class UltraSunUltraMoonSprites(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class GenerationViiiSprites(
    val icons: IconSprites
)

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: NamedApiResource
)

data class Type(
    val slot: Int,
    val type: NamedApiResource
)

data class PastType(
    val generation: NamedApiResource,
    val types: List<Type>
)

data class NamedApiResource(
    val name: String,
    val url: String
)
