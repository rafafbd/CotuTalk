using System.Text.Json.Serialization;
public class Postagem
{
    public int IdPostagem { get; set; }
    public int IdUsuario { get; set; }
    public int IdGrupo { get; set; }
    public required Usuario Usuario { get; set; }
    public required Grupo Grupo { get; set; }
    public required string Conteudo { get; set; }
    [JsonIgnore]
    public ICollection<Curtida> Curtidas { get; set; } = new List<Curtida>();
    [JsonIgnore]
    public ICollection<Resposta> Respostas { get; set; } = new List<Resposta>();
}