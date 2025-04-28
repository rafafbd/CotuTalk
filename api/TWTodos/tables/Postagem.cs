public class Postagem
{
    public int IdPostagem { get; set; }
    public int IdUsuario { get; set; }
    public required Usuario Usuario { get; set; }
    public required string Conteudo { get; set; }
    public ICollection<Curtida> Curtidas { get; set; } = new List<Curtida>();
    public ICollection<Resposta> Respostas { get; set; } = new List<Resposta>();
}