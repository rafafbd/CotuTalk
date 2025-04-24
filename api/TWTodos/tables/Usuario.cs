public class Usuario
{
    public int IdUsuario { get; set; }
    public string Nome { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string Senha { get; set; } = string.Empty;

    public ICollection<Membro> Membros { get; set; } = new List<Membro>();
    public ICollection<Postagem> Postagens { get; set; } = new List<Postagem>();
    public ICollection<Curtida> Curtidas { get; set; } = new List<Curtida>();
}
