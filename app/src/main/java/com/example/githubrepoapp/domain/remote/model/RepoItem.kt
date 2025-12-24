package com.example.githubrepoapp.domain.remote.model

data class RepoItem(
    val description: String,
    val id: Int,
    val name: String,
    val fullName: String,
    val owner: Owner,
)

// Fake Objects
val repo1 = RepoItem(
    description = "**Grit is no longer maintained. Check out libgit2/rugged.** Grit gives you object oriented read/write access to Git repositories via Ruby.",
    id = 1,
    name =  "grit",
    fullName = "mojombo/grit",
    owner = Owner(
        profilePhoto = "https://avatars.githubusercontent.com/u/1?v=4",
        id = 1,
        name = "mojombo"
    ),
)

val repo2 = RepoItem(
    description = "Merb Core: All you need. None you don't.",
    id = 2,
    name =  "merb-core",
    fullName = "wycats/merb-core",
    owner = Owner(
        profilePhoto = "https://avatars.githubusercontent.com/u/4?v=4",
        id = 2,
        name = "wycats"
    ),
)

val repo3 = RepoItem(
    description = "The Rubinius Language Platform",
    id = 3,
    name =  "rubinius",
    fullName = "rubinius/rubinius",
    owner = Owner(
        profilePhoto = "https://avatars.githubusercontent.com/u/317747?v=4",
        id = 3,
        name = "rubinius"
    ),
)