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
        base_experience = base_experience,
        height = height,
        isDefault = is_default,
        order = order,
        weight = weight,
        abilities = abilities,
        forms = forms,
        game_indices = game_indices,
        heldItems = held_items,
        location_area_encounters = location_area_encounters,
        moves = moves,
        species = species,
        sprites = sprites,
        stats = stats,
        types = types,
        past_types = past_types
){
    override fun toString(): String {
        return "Pokemon(id=$id, name='$name', types=$types, base_experience=$base_experience,\n" +
                " height=$height," +
                " is_default=$isDefault, order=$order, weight=$weight, abilities=$abilities,\n" +
                " forms=$forms, game_indices=$game_indices, held_items=$heldItems,\n" +
                " location_area_encounters='$location_area_encounters', moves=$moves,\n" +
                " species=$species, sprites=$sprites, stats=$stats, \n" +
                " past_types=$past_types)"
    }
}