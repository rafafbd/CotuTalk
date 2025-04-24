public class Resposta
{
    public int IdResposta { get; set; }
    public int IdPostagem { get; set; }
    public Postagem Postagem { get; set; }
    public int IdUsuario { get; set; }
    public Usuario Usuario { get; set; }
    public string Conteudo { get; set; }
    public DateTime DataComentario { get; set; }
}