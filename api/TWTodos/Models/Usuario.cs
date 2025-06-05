using System.Text.Json.Serialization;

public class Usuario
{
    [JsonPropertyName("idUsuario")]
    public int IdUsuario { get; set; }

    [JsonPropertyName("nome")]
    public string Nome { get; set; }

    [JsonPropertyName("email")]
    public string Email { get; set; }

    [JsonPropertyName("senha")]
    public string Senha { get; set; }

    [JsonPropertyName("biografia")]
    public string Biografia { get; set; }

    [JsonPropertyName("imagePath")]
    public string ImagePath { get; set; }
    [JsonIgnore]
    public ICollection<Membro> Membros { get; set; } = new List<Membro>();
    [JsonIgnore]
    public ICollection<Postagem> Postagens { get; set; } = new List<Postagem>();
    [JsonIgnore]
    public ICollection<Curtida> Curtidas { get; set; } = new List<Curtida>();
    [JsonIgnore]
    public ICollection<Resposta> Respostas { get; set; } = new List<Resposta>();
}
