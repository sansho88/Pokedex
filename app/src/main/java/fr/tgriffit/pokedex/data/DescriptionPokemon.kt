package fr.tgriffit.pokedex.data

import fr.tgriffit.pokedex.data.DescriptionPokemon
import fr.tgriffit.pokedex.data.model.Color
import fr.tgriffit.pokedex.data.model.DescriptionPkmnData
import fr.tgriffit.pokedex.data.model.EggGroup
import fr.tgriffit.pokedex.data.model.EvolutionChain
import fr.tgriffit.pokedex.data.model.FlavorTextEntry
import fr.tgriffit.pokedex.data.model.Generation
import fr.tgriffit.pokedex.data.model.Genus
import fr.tgriffit.pokedex.data.model.GrowthRate
import fr.tgriffit.pokedex.data.model.Habitat
import fr.tgriffit.pokedex.data.model.Name
import fr.tgriffit.pokedex.data.model.PalParkEncounter
import fr.tgriffit.pokedex.data.model.PokedexNumber
import fr.tgriffit.pokedex.data.model.Shape
import fr.tgriffit.pokedex.data.model.Variety

class DescriptionPokemon(
    base_happiness: Int,
    capture_rate: Int,
    color: Color,
    egg_groups: List<EggGroup>,
    evolution_chain: EvolutionChain,
    evolves_from_species: Any?,
    flavor_text_entries: List<FlavorTextEntry>,
    form_descriptions: List<Any>,
    forms_switchable: Boolean,
    gender_rate: Int,
    genera: List<Genus>,
    generation: Generation,
    growth_rate: GrowthRate,
    habitat: Habitat,
    has_gender_differences: Boolean,
    hatch_counter: Int,
    id: Int,
    is_baby: Boolean,
    is_legendary: Boolean,
    is_mythical: Boolean,
    name: String,
    names: List<Name>,
    order: Int,
    pal_park_encounters: List<PalParkEncounter>,
    pokedex_numbers: List<PokedexNumber>,
    shape: Shape,
    varieties: List<Variety>
): DescriptionPkmnData(
    base_happiness = base_happiness,
    capture_rate = capture_rate,
    color = color,
    egg_groups = egg_groups,
    evolution_chain = evolution_chain,
    evolves_from_species = evolves_from_species,
    flavor_text_entries = flavor_text_entries,
    form_descriptions = form_descriptions,
    forms_switchable = forms_switchable,
    gender_rate = gender_rate,
    genera = genera,
    generation = generation,
    growth_rate = growth_rate,
    habitat = habitat,
    has_gender_differences = has_gender_differences,
    hatch_counter = hatch_counter,
    id = id,
    is_baby = is_baby,
    is_legendary = is_legendary,
    is_mythical = is_mythical,
    name = name,
    names = names,
    order = order,
    pal_park_encounters = pal_park_encounters,
    pokedex_numbers = pokedex_numbers,
    shape = shape,
    varieties = varieties
) {
}