package com.example.cotutalk_program

class Post(val nome : String, val foto : String, val dataHorario : String, val message : String){
    var curtidas = 0;
    val comentarios: MutableList<Post> = mutableListOf();

    fun adicionarCurtida() {
        curtidas++
    }

    fun removerCurtida() {
        curtidas--
    }

    fun adicionarComentarios(post : Post) {
        comentarios.add(post);
    }

    fun obterDadosJson() : Map<String, String> {
        var dados = mapOf(
            "nome" to nome,
            "foto" to foto.toString(),
            "data" to dataHorario,
            "message" to message,
            "curtidas" to curtidas.toString(),
            "comentarios" to comentarios.joinToString(";")
        )
        return dados
    }
}