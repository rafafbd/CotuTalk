public class PostagemRequest
{
    public int IdUsuario { get; set; }
    public int IdGrupo { get; set; }
    public required string Conteudo { get; set; }
}