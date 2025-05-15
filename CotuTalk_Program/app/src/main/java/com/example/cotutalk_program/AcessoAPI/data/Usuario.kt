package com.example.cotutalk_program.AcessoAPI.data

data class Usuario(  // class "nome da tabela"
    val IdUsuario : Int, // val "nome da coluna": "tipo dele"
    val Nome : String,
    val Email : String,
    val Senha : String
//    val Membros: List<Membro> = emptyList(),
//    val Postagens: List<Postagem> = emptyList(),
//    val Curtidas: List<Curtida> = emptyList(),
//    val Respostas: List<Resposta> = emptyList()

)

// precisa de um desses para cada tabela