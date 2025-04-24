public class Curtida
{
    public int IdCurtida { get; set; }
    public int IdUsuario { get; set; }
    public Usuario Usuario { get; set; }
    public int IdPostagem { get; set; }
    public Postagem Postagem { get; set; }
}
