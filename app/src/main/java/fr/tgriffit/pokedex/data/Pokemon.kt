package fr.tgriffit.pokedex.data

import fr.tgriffit.pokedex.data.model.Ability
import fr.tgriffit.pokedex.data.model.Form
import fr.tgriffit.pokedex.data.model.GameIndex
import fr.tgriffit.pokedex.data.model.HeldItem
import fr.tgriffit.pokedex.data.model.Move
import fr.tgriffit.pokedex.data.model.PastType
import fr.tgriffit.pokedex.data.model.PokemonData
import fr.tgriffit.pokedex.data.model.Species
import fr.tgriffit.pokedex.data.model.Sprites
import fr.tgriffit.pokedex.data.model.Stat
import fr.tgriffit.pokedex.data.model.Type

class Pokemon (
    id: Int,
    name: String,
    base_experience: Int,
    height: Int,
    is_default: Boolean,
    order: Int,
    weight: Int,
    abilities: List<Ability>,
    forms: List<Form>,
    game_indices: List<GameIndex>,
    held_items: List<HeldItem>,
    location_area_encounters: String,
    moves: List<Move>,
    species: Species,
    sprites: Sprites,
    stats: List<Stat>,
    types: List<Type>,
    past_types: List<PastType>
    ) : PokemonData(
        id = id,
        name = name,
        baseExperience = base_experience,
        height = height,
        isDefault = is_default,
        order = order,
        weight = weight,
        abilities = abilities,
        forms = forms,
        gameIndices = game_indices,
        heldItems = held_items,
        locationAreaEncounters = location_area_encounters,
        moves = moves,
        species = species,
        sprites = sprites,
        stats = stats,
        types = types,
        pastTypes = past_types
){
    override fun toString(): String {
        return "Pokemon: ${name} (id ${id})\nType: $types"
    }
}