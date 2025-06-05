using System.Text.Json.Serialization;

public class Resposta
{
    public int IdResposta { get; set; }
    public int IdPostagem { get; set; }
    public required Postagem Postagem { get; set; }
    public int IdUsuario { get; set; }
    public required Usuario Usuario { get; set; }
    public string Conteudo { get; set; } = string.Empty;
    public DateTime DataComentario { get; set; }
}