package fr.tgriffit.pokedex.data


import fr.tgriffit.pokedex.data.model.UserData

class User(
    id: Int,
    email: String,
    login: String,
    first_name: String,
    last_name: String,
    usual_full_name: String,
    usual_first_name: String,
    url: String,
    phone: String?,
    displayname: String,
    kind: String,
    image: Image,
    staff: Boolean,
    correction_point: Int,
    pool_month: String,
    pool_year: String,
    location: String?,
    wallet: Int,
    anonymize_date: String,
    data_erasure_date: String?,
    alumni: Boolean,
    active: Boolean,
    groups: List<Any>,
    cursus_users: List<CursusUser>,
    projects_users: List<ProjectsUsers>,
    languages_users: List<LanguagesUser>,
    achievements: List<Any>,
    titles: List<Any>,
    titles_users: List<Any>,
    partnerships: List<Any>,
    patroned: List<Patroned>,
    patroning: List<Any>,
    expertises_users: List<ExpertisesUser>,
    roles: List<Any>,
    campus: List<Campus>,
    campus_users: List<CampusUser>
) : UserData(
    id = id,
    email = email,
    login = login,
    first_name = first_name,
    last_name = last_name,
    usual_full_name = usual_full_name,
    usual_first_name = usual_first_name,
    url = url,
    phone = phone,
    displayname = displayname,
    kind = kind,
    image = image,
    staff = staff,
    correction_point = correction_point,
    pool_month = pool_month,
    pool_year = pool_year,
    location = location,
    wallet = wallet,
    anonymize_date = anonymize_date,
    data_erasure_date = data_erasure_date,
    alumni = alumni,
    active = active,
    groups = groups,
    cursus_users = cursus_users,
    projects_users = projects_users,
    languages_users = languages_users,
    achievements = achievements,
    titles = titles,
    titles_users = titles_users,
    partnerships = partnerships,
    patroned = patroned,
    patroning = patroning,
    expertises_users = expertises_users,
    roles = roles,
    campus = campus,
    campus_users = campus_users
)

class UserPlaceHolder(){
    val id: Int = 0
    val email: String = ""
    val login: String = ""
    val first_name: String = ""
    val last_name: String = ""
    val usual_full_name: String = ""
    val usual_first_name: String = ""
    val url: String = ""
    val phone: String? = null
    val displayname: String = ""
    val kind: String = ""
    val image: Image = Image()
    val staff: Boolean = false

    val correction_point: Int = 0
    val pool_month: String = ""
    val pool_year: String = ""
    val location: String? = null

    val wallet: Int = 0
    val anonymize_date: String = ""
    val data_erasure_date: String? = null
    val alumni: Boolean = false
    val active: Boolean = false
    val groups: List<Any> = listOf()

    val cursus_users: List<CursusUser> = listOf()

    val projects_users: List<ProjectsUsers> = listOf()
    val languages_users: List<LanguagesUser> = listOf()

    val achievements: List<Any> = listOf()
    val titles: List<Any> = listOf()
    val titles_users: List<Any> = listOf()
    val partnerships: List<Any> = listOf()
    val patroned: List<Patroned> = listOf()
    val patroning: List<Any> = listOf()
    val expertises_users: List<ExpertisesUser> = listOf()
    val roles: List<Any> = listOf()

    val campus: List<Campus> = listOf()
    val campus_users: List<CampusUser> = listOf()
    class Image()
    class CursusUser()
    class ProjectsUsers()
    class LanguagesUser()
    class Patroned()
    class ExpertisesUser()
    class Campus()
    class CampusUser()

}
