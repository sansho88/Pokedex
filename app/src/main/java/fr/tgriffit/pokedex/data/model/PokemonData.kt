package fr.tgriffit.pokedex.data.model

open class PokemonData(
    val id: Int,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val order: Int,
    val weight: Int,
    val abilities: List<Ability>,
    val forms: List<Form>,
    val gameIndices: List<GameIndex>,
    val heldItems: List<HeldItem>,
    val locationAreaEncounters: String,
    val moves: List<Move>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val pastTypes: List<PastType>
)

data class Ability(
    val isHidden: Boolean,
    val slot: Int,
    val ability: NamedApiResource
)

data class Form(
    val name: String,
    val url: String
)

data class GameIndex(
    val gameIndex: Int,
    val version: NamedApiResource
)

data class HeldItem(
    val item: NamedApiResource,
    val versionDetails: List<VersionDetail>
)

data class VersionDetail(
    val rarity: Int,
    val version: NamedApiResource
)

data class Move(
    val move: NamedApiResource,
    val versionGroupDetails: List<VersionGroupDetail>
)

data class VersionGroupDetail(
    val levelLearnedAt: Int,
    val versionGroup: NamedApiResource,
    val moveLearnMethod: NamedApiResource
)

data class Species(
    val name: String,
    val url: String
)

data class Sprites(
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?,
    val other: OtherSprites,
    val versions: VersionSprites
)

data class OtherSprites(
    val dreamWorld: DreamWorldSprites,
    val home: HomeSprites,
    val officialArtwork: OfficialArtworkSprites
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

data class VersionSprites(
    val generationI: GenerationISprites,
    val generationIi: GenerationIiSprites,
    val generationIii: GenerationIiiSprites,
    val generationIv: GenerationIvSprites,
    val generationV: GenerationVSprites,
    val generationVi: GenerationViSprites,
    val generationVii: GenerationViiSprites,
    val generationViii: GenerationViiiSprites
)

data class GenerationISprites(
    val redBlue: RedBlueSprites,
    val yellow: YellowSprites
)

data class RedBlueSprites(
    val backDefault: String?,
    val backGray: String?,
    val frontDefault: String?,
    val frontGray: String?
)

data class YellowSprites(
    val backDefault: String?,
    val backGray: String?,
    val frontDefault: String?,
    val frontGray: String?
)

data class GenerationIiSprites(
    val crystal: CrystalSprites,
    val gold: GoldSprites,
    val silver: SilverSprites
)

data class CrystalSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?
)

data class GoldSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?
)

data class SilverSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?
)

data class GenerationIiiSprites(
    val emerald: EmeraldSprites,
    val fireredLeafgreen: FireredLeafgreenSprites,
    val rubySapphire: RubySapphireSprites
)

data class EmeraldSprites(
    val frontDefault: String?,
    val frontShiny: String?
)

data class FireredLeafgreenSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?
)

data class RubySapphireSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?
)

data class GenerationIvSprites(
    val diamondPearl: DiamondPearlSprites,
    val heartgoldSoulsilver: HeartgoldSoulsilverSprites,
    val platinum: PlatinumSprites
)

data class DiamondPearlSprites(
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class HeartgoldSoulsilverSprites(
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class PlatinumSprites(
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class GenerationVSprites(
    val blackWhite: BlackWhiteSprites
)

data class BlackWhiteSprites(
    val animated: AnimatedSprites,
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class AnimatedSprites(
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class GenerationViSprites(
    val omegarubyAlphasapphire: OmegarubyAlphasapphireSprites,
    val xY: XYSprites
)

data class OmegarubyAlphasapphireSprites(
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class XYSprites(
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class GenerationViiSprites(
    val icons: IconSprites,
    val ultraSunUltraMoon: UltraSunUltraMoonSprites
)

data class IconSprites(
    val frontDefault: String?,
    val frontFemale: String?
)

data class UltraSunUltraMoonSprites(
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class GenerationViiiSprites(
    val icons: IconSprites
)

data class Stat(
    val baseStat: Int,
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
