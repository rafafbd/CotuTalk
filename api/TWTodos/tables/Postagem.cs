public class Postagem
{
    public int IdPostagem { get; set; }
    public int IdUsuario { get; set; }
    public Usuario Usuario { get; set; }
    public string Conteudo { get; set; }
}